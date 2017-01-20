import java.util.Random;

/**
 * creates and manages an array of Card objects
 */

public class Deck {

	private final int deckSize=40;
	private Card[] mainDeck;
	private int deckTopPointer; // points at the next available slot
	
	public Deck(){
		this.mainDeck= new Card[deckSize];
		deckTopPointer=0;
	}
	
	/**
	 * adds a card to the top of the deck array
	 * @param String containing card info*/
	public void addCard(String cardInfo){
		if (deckTopPointer<40){    // make sure no more than 40 cards total
			Card newCard= new Card(cardInfo);
			this.mainDeck[deckTopPointer]=newCard;
			this.deckTopPointer++;
		}
	}
	
	/**
	 * adds a card to the top of the deck array
	 * @param a Card object*/
	public void addCard(Card cardIn){
		this.mainDeck[deckTopPointer]=cardIn;
		this.deckTopPointer++;
	}
	
	/**
	 * removes a card from the top of the deck array
	 * only works with a non-empty deck*/
	public void removeCard(){
		if (deckTopPointer>0){
			deckTopPointer--;
			mainDeck[deckTopPointer]=null;
		}
	}
	
	/**
	 * (pseudo)randomly shuffles the Card objects in the deck array
	 * -based on Fisher-Yates array shuffle algorithm*/
	public void shuffleDeck(){
		for (int i=0; i<deckSize-2;i++){
			Random rNr= new Random();
			int randomNr= i + rNr.nextInt((deckSize-i)+1);  // random needs to be between i and deckSize
			
			Card temporal= mainDeck[i];    // swap cards at positions [i] and [randomNr]
			mainDeck[i]=mainDeck[randomNr];
			mainDeck[randomNr]=temporal;	
		}
	}
	
	/**
	 * fetches the Card at the top of the deck array.
	 **/
	public Card getTopCard(){	     //////// Game Mechanics dependent---- need to deal with end of deck? -reset deckTopPointer?
		return mainDeck[deckTopPointer-1];
	}
	
	
	
	///////////////////////////////////// for testing- return the slot of the TOP CARD !!
	public int getDeckTop(){ return deckTopPointer-1;}
	
}
