public class Card {

    public enum Type {
        Person, Waffe, Raum;
    }
    
    public static final Card[] cards = {
            new Card(Type.Person,"Prof. Bloom","bloom"),
            new Card(Type.Person,"Baronin von Porz","porz"),
            new Card(Type.Person,"Frl. Ming","ming"),
            new Card(Type.Person,"Frau Weiß","weiß"),
            new Card(Type.Person,"Oberst von Gatow","gatow"),
            new Card(Type.Person,"Herr Dir. Grün","grün"),
            new Card(Type.Waffe,"Heizungsrohr","heizungsrohr"),
            new Card(Type.Waffe,"Leuchter","leuchter"),
            new Card(Type.Waffe,"Pistole","pistole"),
            new Card(Type.Waffe,"Seil","seil"),
            new Card(Type.Waffe,"Dolch","dolch"),
            new Card(Type.Waffe,"Rohrzange","rohrzange"),
            new Card(Type.Raum,"Küche","küche"),
            new Card(Type.Raum,"Bibliothek","bib"),
            new Card(Type.Raum,"Salon","salon"),
            new Card(Type.Raum,"Speisezimmer","speise"),
            new Card(Type.Raum,"Billiardzimmer","billiard"),
            new Card(Type.Raum,"Eingangshalle","eingang"),
            new Card(Type.Raum,"Veranda","veranda"),
            new Card(Type.Raum,"Arbeitszimmer","arbeit"),
            new Card(Type.Raum,"Musikzimmer","musik"),
    };

    public final Type type;
    public final String name;
    public final String id;

    private Card(Type type, String name, String id) {
        this.type = type;
        this.name = name;
        this.id = id;
    }

    public static Card findCard(String id) {
        for(Card card : cards) {
            if(card.id.equals(id.toLowerCase())) {
                return card;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object card) {
        return card instanceof Card c && this.id.equals(c.id);
    }
}
