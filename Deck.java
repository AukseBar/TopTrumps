import java.util.Random;

/**
 * creates and manages an array of Card objects
 */

public class Deck {

	private final int deckCapacity;
	private Card[] mainDeck;
	private int deckTopPointer; // points at the next available slot
	private int dealPointer;
	private String[] category;
	
	
	public Deck(int deckSize, String[] category){
		this.deckCapacity = deckSize;
		this.category = category;
		
		mainDeck = new Card[deckSize];
		deckTopPointer = 0;
	}
		
	/**
	 * adds a card to the top of the deck array
	 * @param String containing card info*/
	public void addCardToTop(String cardInfo){
		//if (deckTopPointer<40){    // make sure no more than 40 cards total
			Card newCard= new Card(cardInfo);
			this.mainDeck[deckTopPointer]=newCard;
			this.deckTopPointer++;
		//}
	}
	
	
	//////////////// might not need this one, will possibly delete later
	/**
	 * adds a card to the top of the deck array
	 * @param a Card object*/
	public void addCardToTop(Card cardIn){
		this.mainDeck[deckTopPointer]=cardIn;
		this.deckTopPointer++;
		//System.out.println(deckTopPointer);
	}
	
	/**
	 * adds a card to the bottom of the deck
	 * @param Card that is to be added*/
	public void addCardToBottom(Card cardIn){
		for (int i=deckTopPointer-1; i>=0; i--) {
			mainDeck[i+1]=mainDeck[i];
		}
		mainDeck[0]=cardIn;
		deckTopPointer++;
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
		for (int i=0; i<deckCapacity-2;i++){
			Random rNr= new Random();
			int randomNr= i + rNr.nextInt((deckCapacity-i));  // random needs to be between i and deckSize
			
			Card temporal= mainDeck[i];    // swap cards at positions [i] and [randomNr]
			mainDeck[i]=mainDeck[randomNr];
			mainDeck[randomNr]=temporal;	
		}
		dealPointer=deckTopPointer; //prepared to deal cards
	}
	
	/**
	 * fetches the Card at the top of the deck array
	 * and removes that card from the top of the array
	 **/
	public Card getTopCard(){
		Card returnThisOne= mainDeck[deckTopPointer-1];
		removeCard();
		return returnThisOne;
	}
	
	/**
	 * fetches the Card at the top of the deck array
	 * but does not remove that card from the top of the array
	 **/
	public Card seeTopCard(){
		return mainDeck[deckTopPointer-1];
	}
	
	/**
	 * method for dealing cards
	 * returns one card at a time
	 * leaves the original deck intact*/
	public Card dealCard(){
		Card returnThisOne= mainDeck[dealPointer-1]; 
		dealPointer--;
		return returnThisOne;
	}
	
	/**
	 * returns the size of the deck,
	 * that is, counts the non-null slots in the deck
	 * returns an integer*/
	public int getSize(){
		int size=0;
		for (int i=0; i<deckCapacity;i++){
			if (!(mainDeck[i]==null))
				size++;
		}
		return size;
		
		//alternatively:
		//return topDeckPointer;
	}
	
	public int getCapacity() {
		return deckCapacity;
	}
	
	public String getCategoryName(int categoryIndex) {
		return category[categoryIndex - 1];
	}
	
	public String[] getCategories() {
		return category;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < getSize(); i++) {
			sb.append(mainDeck[i].toString() + "\n");
		}
		sb.append("\n---------------------------------\n");
		return sb.toString();
	}
	
	/**
	 * checks if deck has any cards left
	 * returns a boolean*/
	public Boolean hasCard(){
		if (getSize()==0)
			//alternatively:
			// if (topDeckPointer==0)
			return false;
		else
			return true;
	}
	
}
