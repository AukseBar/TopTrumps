
/* This class indicates whether the player is human or not, 
 * selects the card category for game-play and increments the number of games won. 
 * 
 */


public class Player {
	
	//Instance variables
	private final int numOfAttributes = 5;
	private Deck playerHand;
	private int playerNumber;
	private int roundsWon;

	public Player(int playerNumber) {
		playerHand = new Deck();
		this.playerNumber = playerNumber;
	}

	//Method selects highest attribute for computer player and returns its category index.
	public int chooseCategory() {  
		Card c = playerHand.seeTopCard();
		
		int index = 1;
		for(int max = 0, i = 1; i < numOfAttributes; i++) {
			int categoryValue = c.getCategoryValue(i); 
			if(categoryValue > max) {
				max = categoryValue;
				index = i;
			}
		}
		return index;
	}
	
	// Method transfers cards from this deck to players' decks/communal deck upon rounds being completed. 
	public void transferCardTo(Deck deck) {   
		deck.addCardToBottom(playerHand.getTopCard());
	}

	//Method increments rounds won.
	public void wonRound() {
				roundsWon++;
	}
	
	//Returns rounds won. 
	public int getRoundsWon() {
		return roundsWon;
	}

	
	//Returns players' hand.
	public Deck getDeck(){   
		return playerHand;
	}
	
	//Returns number of players. 
	public int getPlayerNumber() {
		return playerNumber;
	}
}
