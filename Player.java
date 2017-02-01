
/* Indicates if player is human or not, selects category for players (human or computer).
 * Returns int for roundsWon by each player. 
 * 
 */


public class Player {

	private final int numOfAttributes = 5;
	private Deck playerHand;
	private int playerNumber;
	private int roundsWon;

	public Player(int playerNumber) {
		playerHand = new Deck();
		this.playerNumber = playerNumber;
	}

	public int chooseCategory() {  //selects highest attribute for computer player and returns its category index
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

	public void transferCardTo(Deck deck) {   // Method transfers cards from this deck to players' decks/communal deck upon rounds being completed. 
		deck.addCardToBottom(playerHand.getTopCard());
	}

	public void wonRound() {
		// increments rounds won
		roundsWon++;
	}
	
	public int getRoundsWon() {
		return roundsWon;
	}

	public Deck getDeck(){   // Makes Player & Deck completely dependent.
		return playerHand;
	}
	
	public int getPlayerNumber() {
		return playerNumber;
	}
}
