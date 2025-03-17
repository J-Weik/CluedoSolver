import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);
        final CardInput cI = new CardInput(sc);

        double guessingChance = 0.0;

        Player Case = new Player("Case",-1);
        Case.setAmountCards(3);

        final ArrayList<Card> Persons = new ArrayList<>(6);
        final ArrayList<Card> Weapons = new ArrayList<>(6);
        final ArrayList<Card> Rooms = new ArrayList<>(9);

        for(Card c : Card.cards) {
            switch (c.type) {
                case Person -> Persons.add(c);
                case Waffe -> Weapons.add(c);
                case Raum -> Rooms.add(c);
            }
        }

        System.out.print("Anzahl der Spieler: ");
        final int playerAmount = sc.nextInt();

        Player[] players = new Player[playerAmount];
        for(int i=0; i<playerAmount; i++){
            players[i] = new Player("Player"+(i+1), i);
        }

        System.out.print("Möchten sie die Spieler umbennen?(y/n): ");
        if(sc.next().equals("y")){
            for(Player p: players){
                System.out.print("Name für " + p.getName() + ": ");
                p.setName(sc.next());
            }
        }

        if(18 % playerAmount != 0){
            for(Player p : players){
                System.out.print("Wieviel Karten hat " + p.getName() + ": ");
                p.setAmountCards(sc.nextInt());
            }
        }
        else{
            final int amount = 18 / playerAmount;
            for(Player p:players){
                System.out.println("Der Spieler " + p.getName() + " hat " + amount+" Karten");
                p.setAmountCards(amount);
            }
        }

        System.out.print("Welcher der Spieler bist du in der Reinfolge?: ");
        final int playerController = sc.nextInt() - 1;

        // User wird nach seinen Karten gefragt, welche dann kein anderer Spieler hat
        for(int i=0; i < players[playerController].getAmountCards(); i++){
            System.out.print("Was ist deine "+(i+1)+"te Karte?: ");
            final Card k = cI.nextCard();
            players[playerController].addKnownCard(k);
            Case.addCardNotOwned(k);

            // Sagt anderen spielern das sie die Karte nicht besitzen
            for(Player p : players){
                if(p != players[playerController]){
                    p.addCardNotOwned(k);
                }
            }
        }

        //Alle Karten von User sind bekannt, daher hat er keine der anderen Karten
        for (Card c : Card.cards) {
            if(!players[playerController].getCardsOwned().contains(c)){
                players[playerController].addCardNotOwned(c);
            }
        }

        Card accusedPerson;
        Card accusedWeapon;
        Card accusedRoom;

        //Hier beginnt der Main-Loop
        while(guessingChance < 1){
            ArrayList<Card> susPersons = Persons;
            ArrayList<Card> susWeapons = Weapons;
            ArrayList<Card> susRooms = Rooms;

            //MainLoop player durchlauf
            for(Player p:players){
                System.out.print("Hat "+p.getName()+" eine Anklage gemacht?(y/n): ");
                if(sc.next().equals("n"))
                    continue;
                else{
                    System.out.print("Gib die verdächtigte Person ein: ");
                    accusedPerson = cI.nextCard();
                    System.out.print("Gib die verdächtigte Waffe ein: ");
                    accusedWeapon = cI.nextCard();
                    System.out.print("Gib den verdächtigten Raum ein: ");
                    accusedRoom = cI.nextCard();

                    String playerInput = "";
                    int playerIdWhoGaveCard= -1;

                    while(true){
                    System.out.print("Welcher der Spieler hat eine Karte gegeben?(Keiner falls niemand Karte gegeben hat: ");

                    playerInput = sc.next();
                    if(playerInput.equalsIgnoreCase("keiner")) {
                        for (Player p2 : players) {
                            if (p2 != p) {
                                p2.addCardNotOwned(accusedPerson);
                                p2.addCardNotOwned(accusedWeapon);
                                p2.addCardNotOwned(accusedRoom);
                            }
                        }
                        break;
                    }
                    boolean playerFound = false;
                    for(Player p2:players) {
                        if(p2.getName().equalsIgnoreCase(playerInput)){
                            playerIdWhoGaveCard = p2.getId();
                            playerFound = true;
                        }
                    }
                    if(!playerFound){
                        System.out.println("Spieler nicht gefunden!");
                        System.out.println("Spieler sind:");
                        for(Player p2:players){
                            System.out.print(p2.getName()+", ");
                        }
                        System.out.print("Keiner");
                        System.out.println("\n");
                    }
                    else
                        break;
                    }
                    //Hier endet Player Name Input

                    if(p.getId()==playerController&&!playerInput.equalsIgnoreCase("keiner")) {
                        System.out.print("Welche Karte wurde gezeigt?: ");
                        Card shownCard = cI.nextCard();
                        players[playerIdWhoGaveCard].addKnownCard(shownCard);
                        for (Player p2 : players) {
                            if(p2.getId()!=playerIdWhoGaveCard){
                                p2.addCardNotOwned(shownCard);
                            }
                        }
                        Case.addCardNotOwned(shownCard);
                    }
                    // Alle Spieler die keine Karte gegeben haben haben keine der Karten
                    int iterator = (p.getId()+1)%playerAmount;
                    while(iterator!=playerIdWhoGaveCard&&iterator!=p.getId()){
                        players[iterator].addCardNotOwned(accusedPerson);
                        players[iterator].addCardNotOwned(accusedWeapon);
                        players[iterator].addCardNotOwned(accusedRoom);

                        iterator = (iterator+1)%playerAmount;
                    }
                    if(!playerInput.equalsIgnoreCase("keiner")) {
                        players[playerIdWhoGaveCard].addAccusation(new Accusation(accusedPerson, accusedWeapon, accusedRoom));
                    }
                }
                // Läuft durch alle Spieler und wenn die Schnittmenge von 2 Accusations = 1 ist wird diese Karte zu den KnownCards hinzugefügt
                /* TODO überleg mal ob das wirklich so ist du dullie
                for(Player p2:players){
                    for(Accusation a:p2.getAccusations()){
                        for(int i = p2.getAccusations().indexOf(a);i<=(p2.getAccusations().size()-1);i++){
                            if(a.getIntersectingCards(p2.getAccusations().get(i)).size()==1) {
                                p2.addKnownCard(a.getIntersectingCards(p2.getAccusations().get(i)).get(0));
                                for (Player p3 : players) {
                                    if(p3!=p2)
                                        p3.addCardNotOwned(a.getIntersectingCards(p2.getAccusations().get(i)).get(0));
                                    Case.addCardNotOwned(a.getIntersectingCards(p2.getAccusations().get(i)).get(0));
                                }
                            }
                        }
                    }
                }
                 */
                // Läuft durch alle Spieler und wenn Accusations ohne die Karten die der Spieler nicht   hat nur eine restkarte hat wird diese zu den knownCards hinzugefügt
                for(Player p2:players){
                    for(Accusation a:p2.getAccusations()){
                        ArrayList<Card> remaining = new ArrayList<>();
                        if(!p2.getCardsNotOwned().contains(a.Person()))
                            remaining.add(a.Person());
                        if(!p2.getCardsNotOwned().contains(a.Weapon()))
                            remaining.add(a.Weapon());
                        if(!p2.getCardsNotOwned().contains(a.Room()))
                            remaining.add(a.Room());

                        if(remaining.size()==1) {
                            p2.addKnownCard(remaining.getFirst());
                            for (Player p3 : players) {
                                p3.addCardNotOwned(remaining.getFirst());
                            }
                            Case.addCardNotOwned(remaining.getFirst());
                        }
                    }
                }
                // Wenn von Spieler alle CardsNotOwned bekannt sind hat er jene Karten, die nicht in CardsNotOwned sind.
                final List<Card> kartenListe= Arrays.stream(Card.cards).toList();
                for(Player p2:players){
                    if((21-p2.getAmountCards())==p2.getCardsNotOwned().size()) {
                        for (Card c : kartenListe) {
                            if (!p2.getCardsNotOwned().contains(c)) {
                                p2.addKnownCard(c);
                                for (Player p3 : players) {
                                    if(p3!=p2)
                                        p3.addCardNotOwned(c);
                                    Case.addCardNotOwned(c);
                                }
                            }
                        }
                    }
                }

                // Wenn alle karten von spieler bekannt hat er keine anderen karten
                for(Player p2:players){
                    if(p2.getCardsOwned().size()==p2.getAmountCards()) {
                        for (Card c : kartenListe) {
                            if (!p2.getCardsOwned().contains(c)) {
                                p2.addCardNotOwned(c);
                            }
                        }
                    }
                }

                //Wenn alle Spieler eine Karte nicht haben ist diese in der Mitte
                for(Card c2: Card.cards) {
                    boolean karteIstMitte = true;
                    for(Player p2:players) {
                        if (!p2.getCardsNotOwned().contains(c2)) {
                            karteIstMitte = false;
                            break;
                        }
                    }
                    if(karteIstMitte) {
                        switch (c2.type) {
                            case Person: {
                                susPersons.clear();
                                susPersons.add(c2);
                                Case.addKnownCard(c2);
                                break;
                            }
                            case Raum: {
                                susRooms.clear();
                                susRooms.add(c2);
                                Case.addKnownCard(c2);
                                break;
                            }
                            case Waffe: {
                                susWeapons.clear();
                                susWeapons.add(c2);
                                Case.addKnownCard(c2);
                                break;
                            }

                        }

                    }
                }


                //Update the sus Lists
                susPersons.removeAll(Case.getCardsNotOwned());
                susWeapons.removeAll(Case.getCardsNotOwned());
                susRooms.removeAll(Case.getCardsNotOwned());

                if(susPersons.size()==1)
                    Case.addKnownCard(susPersons.get(0));
                if(susWeapons.size()==1)
                    Case.addKnownCard(susWeapons.get(0));
                if(susRooms.size()==1)
                    Case.addKnownCard(susRooms.get(0));

                guessingChance=1/(double)(susPersons.size()*susWeapons.size()*susRooms.size());

                System.out.println("######################################################");
                System.out.println("Guessing Chance= "+guessingChance*100+"%");
                System.out.println("Verdächtigte Personen sind:");
                for(Card c:susPersons){
                    System.out.print(c.name+", ");
                }
                System.out.println();
                System.out.println("Verdächtigte Waffen sind:");
                for(Card c:susWeapons){
                    System.out.print(c.name+", ");
                }
                System.out.println();
                System.out.println("Verdächtigte Räume sind:");
                for(Card c:susRooms){
                    System.out.print(c.name+", ");
                }
                System.out.println();
                System.out.println("######################################################");
                for(Player p2:players){
                    System.out.println("------------------");
                    System.out.println(p2.getName());
                    System.out.println("Bessesene Karten: ");
                    for(Card c:p2.getCardsOwned()) {
                        if(c!=null)
                            System.out.print(c.name+", ");
                        else
                            System.out.print("None"+", ");
                    }
                    System.out.println();
                    System.out.println("Nicht Bessesene Karten: ");
                    for(Card c:p2.getCardsNotOwned()) {
                        if(c!=null)
                            System.out.print(c.name+", ");
                        else
                            System.out.print("None"+", ");
                    }
                    System.out.println();
                    System.out.println("Karten, die noch nicht bekannt sind: "+(p2.getAmountCards()-p2.getCardsOwned().size()));
                }
                if(susPersons.size()==1&&susWeapons.size()==1&&susRooms.size()==1) {
                    System.out.println("Der Morfall wurde gelöst!");
                    System.out.println("Der Mörder ist:"+susPersons.get(0).name);
                    System.out.println("Die Waffe ist:"+susWeapons.get(0).name);
                    System.out.println("Der Raum ist:"+susRooms.get(0).name);
                    break;
                }
            }
        }
    }
}

// ┘
// ┐
// ┌
// └
// ┼
// ─
// ├
// ┤
// ┴
// ┬
// │

/*
TODO: Change the info table to look like this


#####################################################
Guessing Chance= 0.5714285714285714%
Verdächtigte Personen sind:
Baronin von Porz, Gloria, Frau Weiß, Oberst von Gatow, Herr Dir. Grün,
Verdächtigte Waffen sind:
Heizungsrohr, Leuchter, Pistole, Dolch, Rohrzange,
Verdächtigte Räume sind:
Küche, Salon, Speisezimmer, Billiardzimmer, Halle, Wintergarten, Arbeitszimmer,
######################################################
┌─────────────┬───┬───┬───┬───┬───┬───┐
│             │   │   │ P │   │   │   │
│             │   │   │ l │   │   │   │
│             │   │   │ a │   │   │   │
│             │   │   │ y │   │   │ C │
│             │   │   │ e │   │   │ a │
│             │   │   │ r │   │   │ s │
│             │   │   │ 3 │   │   │ e │
├─────────────┼───┼───┼───┼───┼───┼───┤
│ Bloom       │ X │ X │ O │ X │ X │ X │
├─────────────┼───┼───┼───┼───┼───┼───┤
│ Porz        │   │   │ X │   │   │   │
├─────────────┼───┼───┼───┼───┼───┼───┤
│ Weiß        │   │   │ X │   │   │   │
├─────────────┼───┼───┼───┼───┼───┼───┤
│ Gloria      │   │   │ X │   │   │   │
├─────────────┼───┼───┼───┼───┼───┼───┤
│ Grün        │   │ X │ X │   │   │   │
├─────────────┼───┼───┼───┼───┼───┼───┤
│ Leuchter    │   │   │ X │   │   │   │
├─────────────┼───┼───┼───┼───┼───┼───┤
│ Dolch       │   │ X │ X │   │   │   │
├─────────────┼───┼───┼───┼───┼───┼───┤
│ Heizugsrohr │   │   │ X │   │   │   │
├─────────────┼───┼───┼───┼───┼───┼───┤
│ Pistole     │   │   │ X │   │   │   │
├─────────────┼───┼───┼───┼───┼───┼───┤
│ Seil        │ X │ X │ O │ X │ X │ X │
├─────────────┼───┼───┼───┼───┼───┼───┤
│ Rohrzange   │   │   │ X │   │   │   │
├─────────────┼───┼───┼───┼───┼───┼───┤
│ Musik       │ X │ X │ O │ X │ X │ X │
├─────────────┼───┼───┼───┼───┼───┼───┤
│ Billiard    │   │ X │ X │   │   │   │
├─────────────┼───┼───┼───┼───┼───┼───┤
│ Winter      │   │   │ X │   │   │   │
├─────────────┼───┼───┼───┼───┼───┼───┤
│ Speise      │   │   │ X │   │   │   │
├─────────────┼───┼───┼───┼───┼───┼───┤
│ Halle       │   │   │ X │   │   │   │
├─────────────┼───┼───┼───┼───┼───┼───┤
│ Küche       │   │   │ X │   │   │   │
├─────────────┼───┼───┼───┼───┼───┼───┤
│ Bib         │ X │ X │ O │ X │ X │ X │
├─────────────┼───┼───┼───┼───┼───┼───┤
│ Salon       │   │   │ X │   │   │   │
├─────────────┼───┼───┼───┼───┼───┼───┤
│ Arbeit      │   │   │ X │   │   │   │
└─────────────┴───┴───┴───┴───┴───┴───┘
 */