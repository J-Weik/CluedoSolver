import java.util.ArrayList;

public record Accusation(Card Person, Card Weapon, Card Room) {
    public ArrayList<Card> getIntersectingCards(Accusation a) {
        ArrayList<Card> intersectingCards = new ArrayList<>();

        if(Person.equals(a.Person)) intersectingCards.add(Person);
        if(Weapon.equals(a.Weapon)) intersectingCards.add(Weapon);
        if(Room.equals(a.Room)) intersectingCards.add(Room);

        return intersectingCards;
    }
}
