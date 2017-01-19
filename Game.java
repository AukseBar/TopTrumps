import java.util.Random;

import javax.print.attribute.standard.NumberOfDocuments;

/**
 * Defines the central game model for the Top Trumps card game implementation
 * Initialises the Players and Decks then runs the main game logic responding to
 * actions through the GUI.
 *
 * Written by Team HACKT for MSc SD Team Project
 */

public class Game {

	protected static final int HUMAN_PLAYER = 0;	// Human player is always first position in player array
	private boolean gameInProgress = false;
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
	 * from a seperate file */
	public Game(Deck mainDeck) {
		this.mainDeck = mainDeck;
	}

	/**
	 * Initialises a new game state and begins the first round of a new game
	 * @param numOfCompPlayers the number of opponents chosen by the human player when they start the game */
	private void startGame(int numOfCompPlayers) {
		this.numOfCompPlayers = numOfCompPlayers;
		gameInProgress = true;
		totalRounds = 0;
		draws = 0;
		player = new Player[numOfCompPlayers + 1];
		player[HUMAN_PLAYER].setHuman(true);		//**** Relies on an external unimplemented or changeable design
		initPlayerDecks();
		currentPlayer = player[randomiseFirstPlayer()];
		nextRound();
	}

	/**
	 * @Return a randomised int within the range of active players */
	private int randomiseFirstPlayer() {
		Random r = new Random();
		return r.nextInt(numOfCompPlayers + 1);
	}

	/**
	 * Iterates through each player dealing them the next top card from the deck until there are no cards left */
	private void initPlayerDecks() {
		mainDeck.shuffleDeck();
		for(int i = 0, j = 0; i < mainDeck.getSize(); i++) {				//**** Relies on an external unimplemented or changeable design 
			player[j].getDeck().addCard(mainDeck.getNextCard());			//**** Relies on an external unimplemented or changeable design 
			if(j <= numOfCompPlayers) {
				j++;
			}
			else {
				j = 0;
			}
		}
	}

	/**
	 * Begins the next round; if currentPlayer is human it will wait for input, otherwise it will call for the computer 
	 * player to choose a category then initiate the comparison between players  */
	private void nextRound() {
		totalRounds++;
		if(!currentPlayer.isHuman()) {			//**** Relies on an external unimplemented or changeable design
			calculateRoundResult(currentPlayer.chooseCategory());		// If currentPlayer is computer		//**** Relies on an external unimplemented or changeable design
		}
		else {
			// Do nothing, wait for human player to confirm choice and thereby activate calculateRoundResult() directly
		}
	}

	/**
	 * NOT FINISHED */
	private void calculateRoundResult(int chosenCategory) {

		int roundWinner = 0;		// Be optimistic, assume human player will win most of time
		int highestValue = player[0].getDeck.getTopCard().getCategoryValue(chosenCategory);		//**** Relies on an external unimplemented or changeable design
		int comparedPlayerValue;
		int drawValue = 0;
		
		for(int i = 1; i <= numOfCompPlayers; i++) {
			comparedPlayerValue = player[i].getDeck.getTopCard().getCategoryValue(chosenCategory);

			if(comparedPlayerValue > highestValue) {
				highestValue = comparedPlayerValue;
				roundWinner = i;
			}
			else if(comparedPlayerValue == highestValue) {
				drawValue = highestValue;
			}
			// Else compared value is < highest value so continue loop
		}
		
		if(highestValue == drawValue) {
			roundDraw();
		}
		else {
			roundWon(player[roundWinner]);
		}
	}

	private void roundDraw() {

	}
	
	/**
	 * NOT FINISHED
	 * @param winner
	 */
	private void roundWon(Player winner) {
		if(winner != currentPlayer) {
			currentPlayer = winner;
			// Give new currentPlayer everyone's top card (including their own as it goes to bottom)
		}
		else {
			// Check if game has been won
		}
	}

	private void endGame() {

	}

	public boolean gameInProgress() {
		return gameInProgress;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
}
