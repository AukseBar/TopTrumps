
/* Indicates if player is human or not, selects category for players (human or computer).
 * Returns int for roundsWon by each player. 
 * 
 */


public class Player {

	private Deck playerHand;
	boolean human = false;
	private int roundsWon;
	private final int numOfAttributes = 5;

	public Player() {
		playerHand = new Deck();
	}

	public int chooseCategory(){  //selects category for computer player
		
		Card c = playerHand.seeTopCard();
		
		int max = 0;
		int index =0;
		for (int i = 1; i < numOfAttributes; i++)
		{	 int curr = c.getCategoryValue(i); 
		     if (curr > max)
		     {
		      max = curr;
		      index=i;
		     }
		}

		return index;
	}

	public void transferCardTo(Deck deck) {   // Method transfers cards from this deck to players' decks/communal deck upon rounds being completed. 

		deck.addCardToBottom(playerHand.getTopCard());
	}

	public void wonRound() {
		// increments rounds won.
		
		roundsWon++;
	}
	
	public int getRoundsWon() {
		return roundsWon;
	}

	public Deck getDeck(){   // Makes Player & Deck completely dependent.
		return playerHand;
	}
}
