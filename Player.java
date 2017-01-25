import java.awt.event.ActionEvent;

import javax.swing.JFrame;

/* Indicates if player is human or not (uses boolean. how?), selects category for players (human or computer).
 * Returns int for roundsWon by each player. 
 * 
 * 
 * Maybe create additional class to manage array of players? As with Card/Deck classes.
 */


public class Player extends JFrame {   /* extend JFrame due to button pressed for roundsWon?
 											& selection of category in GUI. */
	
	boolean human = false;
	private int roundsWon;
	private int hCategory;
	private int CompCategory;
	private Deck playerHand = new Deck();

	
	
	
	private void chooseCategory() {   
		
		if (human = false) { /* Automatically elects category on card with highest value?  */
			Deck cards = new Deck();
			int compCategory  = Math.max(cards.getAttrib1, cards.getAttrib2, cards.getAttrib3, cards.getAttrib4, cards.getAttrib5);
			/* Math.max selects highest value. */
		}
		
		else if (human = true) { 
			
		
			public void actionPerformed(ActionEvent event) {
				
				if (event.getSource() == btnPlayCard)
			
			int hCategory = getJscrollpane; /* Needs category box in GUI to select from for HPlayer. */
			
		}}
		
	}
	

	public boolean setHuman(boolean b) {
		boolean human = true;
		return human;
		
	}
	
	public getDeck(){  /* Method gets deck for each individual player. Makes Player & Deck completely dependent.
					*/
	
		
	}
	
	private void transferCard(){   /* Method transfers cards between players/communal deck upon rounds being completed. */
		
	}
	
	
	/* Returns int of roundsWon */
	
	public int getRoundsWon() {
		
		
		if (player = getRoundWinner){ /* Relies on external class/method */
		
		this.roundsWon++; /* increments roundsWon */
		}
		return roundsWon;
	}
	

}
