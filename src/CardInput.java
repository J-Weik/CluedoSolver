import java.util.ArrayList;
import java.util.Scanner;

public class CardInput {
    private static ArrayList<Card> Karten;
    public CardInput() {
        this.Karten = new ArrayList<>();
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
    }
    public ArrayList<Card> getKarten() {
        return Karten;
    }

    public Card sCard(String input){
        while(true){
            for (Card c : Karten) {
                if(c.id.equalsIgnoreCase(input)){
                    System.out.println("Eingegebene Karte: "+c.name);
                    return c;
                }
            }
            System.out.println("Karte nicht gefunden!");
            System.out.println("Karten sind:");
            System.out.println(" Personen:    | Tatwaffen:   | Räume:       ");
            System.out.println("--------------+--------------+--------------");
            System.out.println(" Bloom        | Heizungsrohr | Küche        ");
            System.out.println(" Porz         | Leuchter     | Bib          ");
            System.out.println(" Ming         | Pistole      | Salon        ");
            System.out.println(" Weiß         | Seil         | Speise       ");
            System.out.println(" Gatow        | Dolch        | Billiard     ");
            System.out.println(" Grün         | Rohrzange    | Eingang      ");
            System.out.println("              |              | Veranda      ");
            System.out.println("              |              | Arbeit       ");
            System.out.println("              |              | Musik        ");
            input = new Scanner(System.in).nextLine();
        }
    }
}