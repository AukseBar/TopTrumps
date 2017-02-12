
/** 
 * This class model a player,
 * indicates whether the player is human or not, 
 * selects the card category for game-play and increments the number of games won. 
 */


public class Player {
	
	//Instance variables
	private final int numOfCategories;
	private Deck playerHand;
	private int playerNumber;
	private int roundsWon;

	public Player(int playerNumber, Deck playerHand) {
		this.playerNumber = playerNumber;
		this.playerHand = playerHand;
		numOfCategories = playerHand.getCategories().length;
	}

	/**
	 * Method selects highest attribute for computer player and returns its category index.
	 * @return index of the category chosen
	 */
	public int chooseCategory() {  
		Card c = playerHand.seeTopCard();
		
		int index = 1;
		for(int max = 0, i = 0; i < numOfCategories; i++) {
			int categoryValue = c.getCategoryValue(i + 1);			// Categories count from 1
			if(categoryValue > max) {
				max = categoryValue;
				index = i + 1;			// Categories count from 1
			}
		}
		return index;
	}
	
	/**
	 * Method transfers card from this player's deck to
	 * other players' decks/communal deck upon rounds being completed.
	 */ 
	public void transferCardTo(Deck deck) {   
		deck.addCardToBottom(playerHand.getTopCard());
	}

	/**
	 * Method increments rounds won.
	 */
	public void wonRound() {
				roundsWon++;
	}
	
	public int getRoundsWon() {
		return roundsWon;
	}

	public Deck getDeck(){   
		return playerHand;
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}
}
