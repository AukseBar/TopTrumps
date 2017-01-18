
public class Deck {

	private Card[] mainDeck;
	private int deckTopPointer=0;
	
	public Deck(){
		this.mainDeck= new Card[40];
	}
	
	public void addCard(String cardInfo){
		Card newCard= new Card(cardInfo);
		this.mainDeck[deckTopPointer]=newCard;
		this.deckTopPointer++;
	}
	
	public void removeCard(){
		deckTopPointer--;
		mainDeck[deckTopPointer]=null;
	}
	
	public void shuffleDeck(){
		
	}
	
	public Card getTopCard(){
		
		return mainDeck[deckTopPointer-1];
	}
	
	
}
