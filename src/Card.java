public class Card {
    public final int type;
    public final String name;
    public final String id;

    public Card(int type, String name, String id) {
        this.type = type;
        this.name = name;
        this.id = id;
    }

    boolean equals(Card card) {
        if(this.id.equals(card.id)) {
            return true;
        }
        return false;
    }
}
