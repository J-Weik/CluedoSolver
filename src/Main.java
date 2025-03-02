import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int playerAmount;
        double guessingChance = 0.0;
        CardInput cI = new CardInput();
        Player Case = new Player("Case",-1);
        Case.setAmountCards(3);

        ArrayList<Card> Karten = new ArrayList<>();
        Karten.add(new Card(1,"Prof. Bloom","bloom"));
        Karten.add(new Card(1,"Baronin von Porz","porz"));
        Karten.add(new Card(1,"Frl. Ming","ming"));
        Karten.add(new Card(1,"Frau Weiß","weiß"));
        Karten.add(new Card(1,"Oberst von Gatow","gatow"));
        Karten.add(new Card(1,"Herr Dir. Grün","grün"));
        Karten.add(new Card(2,"Heizungsrohr","heizungsrohr"));
        Karten.add(new Card(2,"Leuchter","leuchter"));
        Karten.add(new Card(2,"Pistole","pistole"));
        Karten.add(new Card(2,"Seil","seil"));
        Karten.add(new Card(2,"Dolch","dolch"));
        Karten.add(new Card(2,"Rohrzange","rohrzange"));
        Karten.add(new Card(3,"Küche","küche"));
        Karten.add(new Card(3,"Bibliothek","bib"));
        Karten.add(new Card(3,"Salon","salon"));
        Karten.add(new Card(3,"Speisezimmer","speise"));
        Karten.add(new Card(3,"Billiardzimmer","billiard"));
        Karten.add(new Card(3,"Eingangshalle","eingang"));
        Karten.add(new Card(3,"Veranda","veranda"));
        Karten.add(new Card(3,"Arbeitszimmer","arbeit"));
        Karten.add(new Card(3,"Musikzimmer","musik"));

        System.out.print("Anzahl der Spieler: ");
        playerAmount = sc.nextInt();

        Player[] players = new Player[playerAmount];
            for(int i=0;i<playerAmount;i++){
                players[i] = new Player("Player"+(i+1),i);
            }

        System.out.print("Möchten sie die Spieler umbennen?(y/n): ");
        if(sc.next().equals("y")){
            for(Player p:players){
                System.out.print("Name für "+p.getName()+": ");
                p.setName(sc.next());
            }
        }
        if(18%playerAmount!=0){
            for(Player p:players){
                System.out.print("Wieviel Karten hat "+p.getName()+": ");
                p.setAmountCards(sc.nextInt());
            }
        }
        else{
            for(Player p:players){
                p.setAmountCards(18/playerAmount);
            }
        }
        System.out.print("Welcher der Spieler bist du in der Reinfolge?: ");
        int playerController = sc.nextInt()-1;

        for(int i=0;i<players[playerController].getAmountCards();i++){
            System.out.print("Was ist deine "+(i+1)+"te Karte?: ");
            Card kartenInput = cI.sCard(sc.next());
            players[playerController].addKnownCard(kartenInput);
            Case.addCardNotOwned(kartenInput);
            for(Player p:players){
                if(p!=players[playerController]){
                    p.addCardNotOwned(kartenInput);
                }
            }
        }

        Card accusedPerson;
        Card accusedWeapon;
        Card accusedRoom;

        //Hier beginnt der Main-Loop
        while(guessingChance<1){
            LinkedList<Card> susPersons = new LinkedList<>();
            LinkedList<Card> susWeapons = new LinkedList<>();
            LinkedList<Card> susRooms = new LinkedList<>();

            for(Player p:players){
                System.out.print("Hat "+p.getName()+" eine Anklage gemacht?(y/n): ");
                if(sc.next().equals("n"))
                    continue;
                else{
                    System.out.print("Gib die verdächtigte Person ein: ");
                    accusedPerson = cI.sCard(sc.next());
                    System.out.print("Gib die verdächtigte Waffe ein: ");
                    accusedWeapon = cI.sCard(sc.next());
                    System.out.print("Gib den verdächtigten Raum ein: ");
                    accusedRoom = cI.sCard(sc.next());

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
                    for(int i= p.getId()+1;i!=playerIdWhoGaveCard;i++){
                        players[i].addCardNotOwned(accusedPerson);
                        players[i].addCardNotOwned(accusedWeapon);
                        players[i].addCardNotOwned(accusedRoom);
                    }
                    players[playerIdWhoGaveCard].addAccusation(new Accusation(accusedPerson,accusedWeapon,accusedRoom));
                }
                // Läuft durch alle Spieler und wenn die Schnittmenge von 2 Accusations = 1 ist wird diese Karte zu den KnownCards hinzugefügt
                for(Player p2:players){
                    for(Accusation a:p2.getAccusations()){
                        for(int i = p2.getAccusations().indexOf(a);i<p2.getAccusations().size();i++){
                            if(a.getIntersectingCards(p2.getAccusations().get(i)).equals(1)) {
                                p2.addKnownCard(a.getIntersectingCards(p2.getAccusations().get(i)).get(1));
                                for (Player p3 : players) {
                                    if(p3!=p2)
                                        p3.addCardNotOwned(a.getIntersectingCards(p2.getAccusations().get(i)).get(1));
                                }
                            }
                        }
                    }
                }
                System.out.println("######################################################");
                System.out.println("Guessing Chance= "+guessingChance);
                System.out.println("Verdächtigte Personen sind:");
                for(Card c:susPersons){
                    System.out.print(c.name+", ");
                }
                System.out.println();
                for(Card c:susWeapons){
                    System.out.print(c.name+", ");
                }
                System.out.println();
                for(Card c:susRooms){
                    System.out.print(c.name+", ");
                }
                System.out.println();
                System.out.println("######################################################");
                for(Player p2:players){
                    System.out.println();
                    System.out.println(p2.getName());
                    System.out.println("Bessesene Karten: ");
                    for(Card c:p2.getCardsOwned()) {
                        System.out.print(c.name+", ");
                    }
                }
            }
        }
    }
}
