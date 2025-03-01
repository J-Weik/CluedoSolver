public class Accusation {
    private Card Person;
    private Card Weapon;
    private Card Room;
    private boolean gaveCard;

    public Accusation(Card Person, Card Weapon, Card Room, boolean gaveCard) {
        this.Person = Person;
        this.Weapon = Weapon;
        this.Room = Room;
        this.gaveCard = gaveCard;
    }

    public Card getPerson() {
        return Person;
    }

    public Card getWeapon() {
        return Weapon;
    }

    public Card getRoom() {
        return Room;
    }

    public boolean isGaveCard() {
        return gaveCard;
    }
}
