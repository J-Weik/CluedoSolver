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
            c = Card.findCard(input);
            if(c == null) {
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
            }
        } while (c == null);
        return c;
    }
}