import java.util.ArrayList;
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
                System.out.println("Der Spieler " + p.getName() + " hat: " + amount);
                p.setAmountCards(amount);
            }
        }

        System.out.print("Welcher der Spieler bist du in der Reinfolge?: ");
        final int playerController = sc.nextInt() - 1; // FIXME: Input validation -> >0 oder Exceptions

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
                    if(playerInput.equalsIgnoreCase("keiner"))
                        break;
                    boolean playerFound = false;
                    for(Player p2:players) {
                        if(p2.getName().equals(playerInput)){
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

                    if(p.getId()==playerController) {
                        System.out.println("Welche Karte wurde gezeigt?");
                        Card shownCard = cI.nextCard();
                        players[playerIdWhoGaveCard].addKnownCard(shownCard);
                        for (Player p2 : players) {
                            if(p2.getId()!=playerIdWhoGaveCard){
                                p2.addCardNotOwned(shownCard);
                            }
                        }
                        Case.addCardNotOwned(shownCard);
                    }
                    // Alle Spieler die keine Karte gegeben haben haben keine der Card.cards
                    //TODO: Testen ob alles gut funktioniert
                    int iterator = (p.getId()+1)%playerAmount;
                    while(iterator!=playerIdWhoGaveCard&&iterator!=p.getId()){
                        players[iterator].addCardNotOwned(accusedPerson);
                        players[iterator].addCardNotOwned(accusedWeapon);
                        players[iterator].addCardNotOwned(accusedRoom);

                        iterator = (iterator+1)%playerAmount;
                    }
                    players[playerIdWhoGaveCard].addAccusation(new Accusation(accusedPerson,accusedWeapon,accusedRoom));
                }
                // Läuft durch alle Spieler und wenn die Schnittmenge von 2 Accusations = 1 ist wird diese Karte zu den KnownCards hinzugefügt
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
                // Läuft durch alle Spieler und wenn Accusations ohne die Card.cards die der Spieler nicht hat nur eine restkarte hat wird diese zu den knownCard.cards hinzugefügt
                //TODO:testen ob das funktioniert
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

                //Update the sus Lists
                susPersons.removeAll(Case.getCardsNotOwned());
                susWeapons.removeAll(Case.getCardsNotOwned());
                susRooms.removeAll(Case.getCardsNotOwned());
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
                    System.out.println("Bessesene Card.cards: ");
                    for(Card c:p2.getCardsOwned()) {
                        if(c!=null)
                            System.out.print(c.name+", ");
                        else
                            System.out.print("None"+", ");
                    }
                    System.out.println();
                    System.out.println("Nicht Bessesene Card.cards: ");
                    for(Card c:p2.getCardsNotOwned()) {
                        if(c!=null)
                            System.out.print(c.name+", ");
                        else
                            System.out.print("None"+", ");
                    }
                    System.out.println();
                    System.out.println("Card.cards, die noch nicht bekannt sind: "+(p2.getAmountCards()-p2.getCardsOwned().size()));
                }
            }
        }
    }
}
