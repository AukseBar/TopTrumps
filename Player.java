import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.JFrame;

/* Indicates if player is human or not, selects category for players (human or computer).
 * Returns int for roundsWon by each player. 
 * 
 * 
 * Maybe create additional class to manage array of players? As with Card/Deck classes.
 */

/*If player array position == 0, then player is human. Human is always first player in array layout 
/* of the class Game.
 * 
 *  isHuman == true if human is first instance in array.
 *  all players afterwards are computer players.*/


public class Player {

	private Deck playerHand;
	boolean human = false;
	private int roundsWon;


	public Player() {
		playerHand = new Deck();
	}

	public int chooseCategory(){  //selects category for computer player

		int compCategory  = Math.max(cards.getCategoryValue(Card.attrib1), cards.getCategoryValue(Card.attrib2), cards.getCategoryValue(Card.attrib3), cards.getCategoryValue(Card.attrib4), cards.getCategoryValue(Card.attrib5));
		// Math.max selects highest value. 

		return compCategory;		//****** This would return the highest value, what is actually wanted to be returned though is the attribute number (1-5) that this value belongs to
	}

	public void transferCardTo(Deck deck) {   // Method transfers cards from this deck to players' decks/communal deck upon rounds being completed. 

		// get top card and transfer to the bottom of the deck passed as the argument
	}

	public void wonRound() {
		//
	}
	
	public int getRoundsWon() {
		return roundsWon;
	}

	public Deck getDeck(){   // Makes Player & Deck completely dependent.
		return playerHand;
	}

	public boolean isHuman(){
		return human;
	}

	public void setHuman(boolean b) {
		human = b;
	}
}
