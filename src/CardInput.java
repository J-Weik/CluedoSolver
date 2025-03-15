import java.util.Scanner;

public class CardInput {
    private final Scanner scanner;

    public CardInput(Scanner scanner) {
        this.scanner = scanner;
    }
    public CardInput() {
        this.scanner = new Scanner(System.in);
    }

    public Card nextCard() {
        Card c = null;
        do {
            String input = scanner.nextLine();
            if (input.equals("")) continue; // Damit wenn die Methode aufgerufen wird die Tabelle nicht sofort geprintet wird
            c = Card.findCard(input);
            if(c == null) {
                System.out.println("Karte nicht gefunden!");
                System.out.println("Karten sind:");
                System.out.println(" Personen:    | Tatwaffen:   | Räume:       ");
                System.out.println("--------------+--------------+--------------");
                System.out.println(" Bloom        | Heizungsrohr | Küche        ");
                System.out.println(" Porz         | Leuchter     | Bib          ");
                System.out.println(" Gloria       | Pistole      | Salon        ");
                System.out.println(" Weiß         | Seil         | Speise       ");
                System.out.println(" Gatow        | Dolch        | Billiard     ");
                System.out.println(" Grün         | Rohrzange    | Halle        ");
                System.out.println("              |              | Winter       ");
                System.out.println("              |              | Arbeit       ");
                System.out.println("              |              | Musik        ");
            }
        } while (c == null);
        System.out.println("Eingegebene Karte: "+c.name);
        return c;
    }
}