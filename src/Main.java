import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int playerAmount;
        int GuessingChance;
        CardInput cI = new CardInput();

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
        System.out.print("Welcher der Spieler bist du in der Reinfolge? :");
        int playerController = sc.nextInt()-1;

        for(int i=0;i<players[playerController].getAmountCards();i++){
            System.out.print("Was ist deine "+(i+1)+"te Karte?: ");
            players[playerController].addKnownCard(cI.sCard(sc.next()));
        }
    }
}
