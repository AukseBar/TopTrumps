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
	 * @param mainDeck a Deck instance containing the full deck of cards as read in from a separate file */
	public Game(Deck mainDeck) {
		this.mainDeck = mainDeck;
	}

	/**
	 * Initialises a new game state and begins the first round of a new game
	 * @param numOfCompPlayers the number of opponents chosen by the human player when they start the game */
	public void startGame(int numOfCompPlayers) {
		this.numOfCompPlayers = numOfCompPlayers;
		
		player = new Player[numOfCompPlayers + 1];
		for(int i = 0; i < numOfCompPlayers + 1; i++) {
			player[i] = new Player(i);
		}
		
		initPlayerDecks();
		//currentPlayer = player[randomiseFirstPlayer()];
		currentPlayer = player[0];									// **** For testing
		totalRounds = 0;
		draws = 0;
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
		for(int i = 0, j = 0; i < mainDeck.getSize(); i++) {
			System.out.println("i: " + i);
			System.out.println("j: " + j);
			System.out.println(mainDeck.getSize());
			player[j].getDeck().addCardToTop(mainDeck.dealCard());
			
			if(j < numOfCompPlayers) {
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
	 * @return the game's state at the end of the round as a static int value defined within the Game class */
	protected int calculateRoundResult(int chosenCategory) {
		totalRounds++;
		
		/*// Computer player must choose a category if they are the current player
		if(currentPlayer != player[HUMAN_PLAYER]) {
			chosenCategory = currentPlayer.chooseCategory();
		}*/
		
		// Assume current player will win most of the time, so set initial highest value to their choice
		// Specification does not require to check if they are out of cards due to multiple consecutive draws
		int highestValue = currentPlayer.getDeck().seeTopCard().getCategoryValue(chosenCategory);
		Player roundWinner = currentPlayer;
		
		int comparedPlayerValue;
		int drawValue = 0;
		
		// Iterate through each player that has a card; compare values, store highest, record any draw
		for(int i = 0; i < numOfCompPlayers + 1; i++) {

			if(player[i] != currentPlayer && player[i].getDeck().hasCard()) {

				comparedPlayerValue = player[i].getDeck().seeTopCard().getCategoryValue(chosenCategory);

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
			// winState() return depends on whether the game has been won in addition to the round
			if(winState(roundWinner) == STATE_ROUND_WON) {
				return STATE_ROUND_WON;
			}
			else {
				return STATE_GAME_WON;
			}
		}
	}

	/**
	 * Transfers the top cards from all players participating in the round into the communal pile
	 */
	private void roundDraw() {
		draws++;
		for(int i = 0; i <= numOfCompPlayers; i++) {
			if(player[i].getDeck().hasCard()) {
				player[i].transferCardTo(communalPile);
			}
		}
	}
	
	/**
	 * Sets the currentPlayer to the roundWinner and transfers to them any cards in the communal pile from previous draws and cards
	 * played in the previous round. Checks to see if the roundWinner has won the game.
	 * @param roundWinner the winning player of the round
	 */
	private int winState(Player roundWinner) {
		if(roundWinner != currentPlayer) {
			currentPlayer = roundWinner;
		}

		currentPlayer.wonRound();
		
		// Transfer cards in communal pile
		while(communalPile.hasCard()) {
			currentPlayer.getDeck().addCardToBottom(communalPile.getTopCard());			//**** Bit messy? Possibly needs a method written
		}
		
		// Give currentPlayer everyone's played card (including their own as it goes to bottom) & check for winner
		boolean gameWon = true;
		for(int i = 0; i <= numOfCompPlayers; i++) {
			if(player[i].getDeck().hasCard()) {
				player[i].transferCardTo(currentPlayer.getDeck());
				
				// Does at least one player other than the current have a card left after transfer
				if(player[i] != currentPlayer && player[i].getDeck().hasCard()) {
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

	public int getTotalRounds() {
		return totalRounds;
	}

	public int getDraws() {
		return draws;
	}
}
