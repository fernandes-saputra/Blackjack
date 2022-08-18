import java.math.*;
import java.util.*;

public class Main {

	ArrayList<String> deck = new ArrayList<String>();
	
	String[] suit = {"Spade","Heart","Club","Diamond"}; 
	String[] card = {"2","3","4","5",
					"6","7","8","9","10",
					"J","Q","K","A"};
	ArrayList<String> playerHand = new ArrayList<String>();
	ArrayList<String> dealerHand = new ArrayList<String>();
	
	enum GameState {
		Win,
		Lose,
		Busted,
		Draw,
		None
	}
	
	GameState gameState = GameState.None;
	boolean playerIsDone = false;
	
	public Main() {
		InitializeDeck();
		DealInitialCard();
		while(gameState == GameState.None) {
			PrintCard();
			CheckGameState();
			if(gameState==GameState.None) {
				Menu();
			}
		}
	}
	
	public void Menu() {
		String[] menu = {"Hit","Stand"};
		Scanner sc = new Scanner(System.in);
		System.out.println("========================");
		for(int i=0; i < menu.length;i++) {
			System.out.println(i+1 + ". " + menu[i]);
		}
		System.out.print("Enter your choice: ");
		int choice = sc.nextInt();
		
		switch(choice) {
		case 1:
			DealCard(playerHand);
			break;
		case 2:
			playerIsDone = true;
			DealerMoves();
			break;
		}
	}
	
	public void InitializeDeck() {
		for (int i = 0;i < card.length;i++) {
			for (int j = 0; j < suit.length;j++) {
				deck.add( card[i] + " " + suit[j]);
			}
		}
	}
	
	public String GetCard() {
		int randomNumber = (int) (Math.random() * (deck.size()-1 + 1)) ;
		String cardDealt = deck.get(randomNumber);
		deck.remove(randomNumber);
		return cardDealt;
	}
	public void DealCard(ArrayList<String> hand) {
		hand.add(GetCard());
	}
	public void DealInitialCard() {
		for (int i = 0; i < 2; i++) {
			playerHand.add(GetCard());
			dealerHand.add(GetCard());
		}
	}
	
	public void PrintCard() {
		System.out.println("Dealer's Hand: " + "(" + Calculate(dealerHand) + ")");
		for(int i=0; i < dealerHand.size();i++) {
			System.out.println(dealerHand.get(i));
		}
		System.out.println("Your Hand: " + "(" + Calculate(playerHand) + ")");
		for(int i=0; i < playerHand.size();i++) {
			System.out.println(playerHand.get(i));
		}
	}
	
	public void CheckGameState() {
		if(Calculate(dealerHand)>21 && playerIsDone == true) {
			System.out.println("Dealer Busted");
			gameState = GameState.Win;
		}
		else if(Calculate(dealerHand)>Calculate(playerHand) && playerIsDone == true) {
			System.out.println("Dealer Win");
			gameState = GameState.Lose;
		}
		else if(Calculate(playerHand)==Calculate(dealerHand) && playerIsDone == true) {
			System.out.println("Draw");
			gameState = GameState.Draw;
		}
		else if(Calculate(playerHand)>21) {
			System.out.println("Player Busted");
			gameState = GameState.Busted;
		}
		
	}
	
	public int Calculate(ArrayList<String> cards) {
		int total = 0;
		int aceCounter = 0;
		for (int i = 0; i < cards.size(); i++) {
			if(cards.get(i).split(" ",2)[0].equals("J")|| 
					cards.get(i).split(" ",2)[0].equals("Q") ||
							cards.get(i).split(" ",2)[0].equals("K")) {
				total = total + 10;
			}
			else if(cards.get(i).split(" ",2)[0].equals("A")) {
				total = total + 11;
				aceCounter++;
			}
			else {
				total = total + Integer.parseInt(cards.get(i).split(" ",2)[0]);
			}
		}
		for(int j = 0; j < aceCounter;j++) {
			if(cards.get(j).split(" ",2)[0].contains("A") && total > 21) {
				total = total - 10;
			}
		}
		return total;
	}
	
	public void DealerMoves() {
		while(gameState == GameState.None) {
			DealCard(dealerHand);
			PrintCard();
			CheckGameState();
		}
	}
	
	public static void main(String[] args) {
		Main main = new Main();
	}
}
