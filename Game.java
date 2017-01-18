/**
 * Defines the central game model for the Top Trumps card game implementation
 * Initialises the Players and Decks then runs the main game logic responding to
 * actions through the GUI.
 *
 * Written by Team HACKT for MSc SD Team Project
 */

public class Game {

	protected static final int HUMAN_PLAYER = 0;
	private boolean gameInProgress = false;
	private Deck communalPile = new Deck();
	private Deck initialDeck;
	private Player[] player;
	private int totalRounds;
	private int draws;
	private int currentPlayer;

	/**
	 * Constructor for a Game instance
	 * @param initialDeck a Deck instance containing the full deck of cards as read in
	 * from a seperate file */
	public Game(Deck initialDeck) {
		this.initialDeck = initialDeck;
	}

	private void startGame(int numOfCompPlayers) {
		gameInProgress = true;
		totalRounds = 0;
		draws = 0;
		player = new player[numOfCompPlayers + 1];
		initPlayerDecks();
		currentPlayer = randomiseFirstPlayer();
		nextRound();
	}

	/**
	 * @Return a randomised int within the range of active players */
	private int randomiseFirstPlayer() {
		Random r = new Random();
		return r.nextInt(numOfCompPlayers + 1);
	}

	private void initPlayerDecks() {
		initialDeck.shuffleDeck();
		for(int i = 0, int j = 0; i < initialDeck.length; i++) {
			player[j].getDeck().addCard(initialDeck.getTopCard());
			if(j <= numOfCompPlayers) {
				j++;
			}
			else {
				j = 0;
			}
		}
	}

	private void nextRound() {
		if(currentPlayer != HUMAN_PLAYER) {
			player[currentPlayer].chooseCategory();
		}
		else {
			// Do nothing, wait for human player to make choice and activate compareCards()
		}
	}

	private void compareCards() {

	}

	private void roundWon() {

	}

	private void roundDraw() {

	}

	private void endGame() {

	}

	public boolean gameInProgress() {
		return gameInProgress;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}
}
