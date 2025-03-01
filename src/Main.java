import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int playerAmount;
        int GuessingChance;

        Card C1 = new Card(1,"Prof. Bloom",1);
        Card C2 = new Card(1,"Baronin von Porz",2);
        Card C3 = new Card(1,"Frl. Ming",3);
        Card C4 = new Card(1,"Frau Weiß",4);
        Card C5 = new Card(1,"Oberst von Gatow",5);
        Card C6 = new Card(1,"Herr Dir. Grün",6);
        Card C7 = new Card(2,"Heizungsrohr",7);
        Card C8 = new Card(2,"Leuchter",8);
        Card C9 = new Card(2,"Pistole",9);
        Card C10 = new Card(2,"Seil",10);
        Card C11 = new Card(2,"Dolch",11);
        Card C12 = new Card(2,"Rohrzange",12);
        Card C13 = new Card(3,"Küche",13);
        Card C14 = new Card(3,"Bibliothek",14);
        Card C15 = new Card(3,"Salon",15);
        Card C16 = new Card(3,"Speisezimmer",16);
        Card C17 = new Card(3,"Billiardzimmer",17);
        Card C18 = new Card(3,"Eingangshalle",18);
        Card C19 = new Card(3,"Veranda",19);
        Card C20 = new Card(3,"Arbeitszimmer",20);
        Card C21 = new Card(3,"Musikzimmer",21);

        System.out.println("Anzahl der Spieler: ");
        playerAmount = sc.nextInt();

        Player[] players = new Player[playerAmount];
        if(18%playerAmount!=0){
            for(int i=0;i<playerAmount;i++){
                players[i] = new Player("Player"+i,i);
            }
        }

        System.out.print("\nMöchten sie die Spieler umbennen?(y/n): ");
        if(sc.next().equals("y")){
            for(Player p:players){
                System.out.print("\nName für "+p.getName()+":");
                p.setName(sc.next());
            }
        }
        if(18%playerAmount!=0){
            for(Player p:players){
                p.setAmountCards(sc.nextInt());
            }
        }
        else{
            for(Player p:players){
                p.setAmountCards(18/playerAmount);
            }
        }
    }
}
