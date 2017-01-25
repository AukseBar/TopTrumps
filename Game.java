import java.util.Random;

/**
 * Defines the central game model for the Top Trumps card game implementation
 * Initialises the Players and Decks then runs the main game logic responding to
 * actions through the GUI.
 *
 * Written by Team HACKT for MSc SD Team Project
 */

public class Game {

	// Human player is always first position in player array
	protected static final int HUMAN_PLAYER = 0;
	
	// Static game states
	protected static final int STATE_GAME_WON = 1;
	protected static final int STATE_ROUND_WON = 2;
	protected static final int STATE_ROUND_DRAW = 3;
	
	// Instance variables
	private Deck communalPile = new Deck();
	private Deck mainDeck;
	private Player[] player;
	private Player currentPlayer;
	private int numOfCompPlayers;
	private int totalRounds;
	private int draws;

	/**
	 * Constructor for a Game instance
	 * @param mainDeck a Deck instance containing the full deck of cards as read in
	 * from a separate file */
	public Game(Deck mainDeck) {
		this.mainDeck = mainDeck;
	}

	/**
	 * Initialises a new game state and begins the first round of a new game
	 * @param numOfCompPlayers the number of opponents chosen by the human player when they start the game */
	private void startGame(int numOfCompPlayers) {
		this.numOfCompPlayers = numOfCompPlayers;
		totalRounds = 0;
		draws = 0;
		player = new Player[numOfCompPlayers + 1];
		player[HUMAN_PLAYER].setHuman(true);		//**** Relies on an external unimplemented or changeable design
		initPlayerDecks();
		currentPlayer = player[randomiseFirstPlayer()];
	}

	/**
	 * @return a randomised int within the range of active players */
	private int randomiseFirstPlayer() {
		return new Random().nextInt(numOfCompPlayers + 1);
	}

	/**
	 * Iterates through each player dealing them the next top card from the deck until there are no cards left */
	private void initPlayerDecks() {
		mainDeck.shuffleDeck();
		for(int i = 0, j = 0; i < mainDeck.getLength(); i++) {				//**** Relies on an external unimplemented or changeable design 
			player[j].getDeck().addCard(mainDeck.dealCard());			//**** Relies on an external unimplemented or changeable design 
			if(j <= numOfCompPlayers) {
				j++;
			}
			else {
				j = 0;
			}
		}
	}

	/**
	 * Finds the player with the highest value in the category as chosen by the current player then advances to roundWon(),
	 * or roundDraw() if there is more than one player sharing the same highest value
	 * @param chosenCategory an int which represents the array position of the chosen category on the card instance 
	 * @return the static Game.STATE_* at the end of the round */
	private int calculateRoundResult(int chosenCategory) {
		totalRounds++;
		
		// Computer player must choose a category if they are the current player
		if(!currentPlayer.isHuman()) {
			chosenCategory = currentPlayer.chooseCategory();
		}
		
		// Assume current player will win most of the time, so set initial highest value to their choice
		// Specification does not require to check if they are out of cards due to multiple consecutive draws
		int highestValue = currentPlayer.getDeck().getTopCard().getCategoryValue(chosenCategory);		//**** Relies on an external unimplemented or changeable design
		Player roundWinner = currentPlayer;
		
		int comparedPlayerValue;
		int drawValue = 0;
		
		// Iterate through each player that has a card; compare values, store highest, record any draw
		for(int i = 0; i <= numOfCompPlayers; i++) {

			if(player[i] != currentPlayer && player[i].getDeck().hasCard()) {		//**** Relies on an external unimplemented or changeable design

				comparedPlayerValue = player[i].getDeck().getTopCard().getCategoryValue(chosenCategory);		//**** Relies on an external unimplemented or changeable design

				if(comparedPlayerValue > highestValue) {
					highestValue = comparedPlayerValue;
					roundWinner = player[i];
				}
				else if(comparedPlayerValue == highestValue) {
					drawValue = highestValue;
				}
				else {
					continue;	// Compared value is < highest value
				}

			}
		}

		// Work out round result and return as static game state
		if(highestValue == drawValue) {
			roundDraw();
			return STATE_ROUND_DRAW;
		}
		else {
			// roundWon() also returns whether the game has been won as well as the round
			switch(roundWon(roundWinner)) {
				case STATE_ROUND_WON: return STATE_ROUND_WON;
				case STATE_GAME_WON: return STATE_GAME_WON;
			}
		}
	}

	/**
	 * Transfers all players' cards in play into the communal pile
	 */
	private void roundDraw() {
		draws++;
		for(int i = 0; i <= numOfCompPlayers; i++) {
			if(player[i].getDeck().hasCard()) {						//**** Relies on an external unimplemented or changeable design
				player[i].getDeck().transferCardTo(communalPile);			//**** Relies on an external unimplemented or changeable design
			}
		}
	}
	
	/**
	 * Sets the currentPlayer to the roundWinner and transfers to them any cards in the communal pile from previous draws and cards
	 * played in the previous round. Checks to see if the roundWinner has won the game.
	 * @param roundWinner the winning player of the round
	 */
	private int roundWon(Player roundWinner) {
		if(roundWinner != currentPlayer) {
			currentPlayer = roundWinner;
		}

		currentPlayer.wonRound();										//**** Relies on an external unimplemented or changeable design
		
		// Transfer cards in communal pile
		while(communalPile.hasCard()) {									//**** Relies on an external unimplemented or changeable design
			communalPile.transferCardTo(currentPlayer.getDeck());		//**** Relies on an external unimplemented or changeable design
		}
		
		// Give currentPlayer everyone's played card (including their own as it goes to bottom) & check for winner
		boolean gameWon = true;
		for(int i = 0; i <= numOfCompPlayers; i++) {
			if(player[i].getDeck().hasCard()) {						//**** Relies on an external unimplemented or changeable design
				player[i].getDeck().transferCardTo(currentPlayer.getDeck());			//**** Relies on an external unimplemented or changeable design
				
				// Does at least one player other than the current have a card left after transfer
				if(player[i] != currentPlayer && player[i].getDeck().hasCard()) {		//**** Relies on an external unimplemented or changeable design
					gameWon = false;		// Thus gameWon will only stay true if no one except current player has cards left
				}
			}
		}
		
		// Return end result of round
		if(gameWon) {
			return STATE_GAME_WON;
		}
		else {
			return STATE_ROUND_WON;
		}
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

}
