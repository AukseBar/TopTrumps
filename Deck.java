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
	 * @param String containing card info
	 **/
	public void addCardToTop(String cardInfo){
			Card newCard = new Card(cardInfo);
			this.mainDeck[deckTopPointer] = newCard;
			this.deckTopPointer++;
	}
	

	/**
	 * adds a card to the top of the deck array
	 * @param a Card object
	 **/
	public void addCardToTop(Card cardIn){
		this.mainDeck[deckTopPointer] = cardIn;
		this.deckTopPointer++;
	}
	
	/**
	 * adds a card to the bottom of the deck
	 * @param Card that is to be added
	 **/
	public void addCardToBottom(Card cardIn){
		for (int i=deckTopPointer-1; i>=0; i--) {
			mainDeck[i+1] = mainDeck[i];
		}
		mainDeck[0] = cardIn;
		deckTopPointer++;
	}
	
	/**
	 * removes a card from the top of the deck array
	 * only works with a non-empty deck
	 **/
	public void removeCard(){
		if (deckTopPointer>0){
			deckTopPointer--;
			mainDeck[deckTopPointer] = null;
		}
	}
	
	/**
	 * (pseudo)randomly shuffles the Card objects in the deck array
	 * -based on Fisher-Yates array shuffle algorithm
	 **/
	public void shuffleDeck(){
		for (int i=0; i<deckCapacity-2;i++){
			Random rNr= new Random();
			int randomNr= i + rNr.nextInt((deckCapacity-i));  // random needs to be between i and deckSize
			
			Card temporal = mainDeck[i];    // swap cards at positions [i] and [randomNr]
			mainDeck[i] = mainDeck[randomNr];
			mainDeck[randomNr] = temporal;	
		}
		dealPointer = deckTopPointer; //prepare to deal cards
	}
	
	/**
	 * fetches the Card at the top of the deck array
	 * and removes that card from the top of the array
	 * @return top Card
	 **/
	public Card getTopCard(){
		Card returnThisOne = mainDeck[deckTopPointer-1];
		removeCard();
		return returnThisOne;
	}
	
	/**
	 * fetches the Card at the top of the deck array
	 * but does not remove that card from the top of the array
	 * @return top Card
	 **/
	public Card seeTopCard(){
		return mainDeck[deckTopPointer-1];  //because pointer is at next empty slot
	}
	
	/**
	 * method for dealing cards
	 * returns one card at a time
	 * leaves the original deck intact
	 **/
	public Card dealCard(){
		Card returnThisOne = mainDeck[dealPointer-1]; 
		dealPointer--;
		return returnThisOne;
	}
	
	/**
	 * accessor methods
	 **/
	
	public int getSize(){
		return deckTopPointer;
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
	
	
	/**
	 * for printing out a deck
	 * for testing
	 **/
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
		if (deckTopPointer==0)
			return false;
		else
			return true;
	}
	
}
