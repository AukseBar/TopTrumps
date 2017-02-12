/**
 * Defines the central game model for the Top Trumps card game implementation
 * Initialises the Players and Decks then runs the main game logic responding to
 * actions through the GUI.
 *
 * Written by Team HACKT for MSc SD Team Project
 */

import java.util.Random;

public class Game {

	// Human player is always first position in player array
	protected static final int HUMAN_PLAYER = 0;
	
	// Static game states
	protected static final int STATE_ROUND_WON = 1;
	protected static final int STATE_ROUND_DRAW = 2;
	
	// Instance variables
	private Deck communalPile;
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
		communalPile = new Deck(mainDeck.getCapacity(), mainDeck.getCategories());
	}

	/**
	 * Initialises a new game state and begins the first round of a new game
	 * @param numOfCompPlayers the number of opponents chosen by the human player when they start the game */
	public void startGame(int numOfCompPlayers) {
		this.numOfCompPlayers = numOfCompPlayers;
		
		player = new Player[numOfCompPlayers + 1];
		for(int playerNumber = 0; playerNumber < numOfCompPlayers + 1; playerNumber++) {
			player[playerNumber] = new Player(playerNumber, new Deck(mainDeck.getCapacity(), mainDeck.getCategories()));
		}
		
		initPlayerDecks();
		currentPlayer = player[randomiseFirstPlayer()];
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
			
			player[j].getDeck().addCardToTop(mainDeck.dealCard());
			
			if(j < numOfCompPlayers) {
				j++;
			}
			else {
				j = 0;
			}
		}
		
		// For assessment testing
		System.out.println("SHUFFLED DECK:");
		System.out.println(mainDeck.toString());

	}

	/**
	 * Finds the player with the highest value in the category chosen by the current player and returns the game state.
	 * In the result of a win the current player variable is set to the round winner
	 * @param chosenCategory an int which represents the array position of the chosen category on the card instance 
	 * @return the game's state at the end of the round as a static int value defined within the Game class */
	protected int calculateRoundResult(int chosenCategory) {
		
		// For assessment testing
		System.out.println("THIS ROUND:");
		if(currentPlayer == getHumanPlayer()) {
			System.out.print("YOU have selected: ");
		}
		else {
			System.out.print("PLAYER " + currentPlayer.getPlayerNumber() + " has selected: ");
		}
		System.out.println(currentPlayer.getDeck().getCategoryName(chosenCategory)
		+ ", on the " + currentPlayer.getDeck().seeTopCard().getTitle() + " ("
		+ currentPlayer.getDeck().seeTopCard().getCategoryValue(chosenCategory) + ")");
		
		totalRounds++;
		
		// Assume current player will win most of the time, so set initial highest value to their choice
		int highestValue = currentPlayer.getDeck().seeTopCard().getCategoryValue(chosenCategory);
		Player roundWinner = currentPlayer;
		
		int comparedPlayerValue = 0;
		int drawValue = 0;
		
		// Iterate through each player that has a card; compare values, store highest, record any draws
		for(int i = 0; i < numOfCompPlayers + 1; i++) {

			if(player[i] != currentPlayer && player[i].getDeck().hasCard()) {
				
				// For assessment testing
				if(player[i] == getHumanPlayer()) {
					System.out.print("YOU have: ");
				}
				else {
					System.out.print("PLAYER " + i + " has: ");
				}
				System.out.println("the " + player[i].getDeck().seeTopCard().getTitle() + " ("
				+ player[i].getDeck().seeTopCard().getCategoryValue(chosenCategory) + ")");
				
				
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
		// For assessment testing
		System.out.println("---------------------------------\n");

		// Work out round result and return as static int representing game state
		if(highestValue == drawValue) {
			draws++;
			return STATE_ROUND_DRAW;
		}
		else {
			currentPlayer = roundWinner;
			currentPlayer.wonRound();
			return STATE_ROUND_WON;
		}

	}
	
	/**
	 * Returns true if the game has been won by checking if anyone except the winner of the round
	 * will have cards to play in the next round after their current card is transferred 
	 * @return boolean representing whether the game has been won */
	public boolean checkGameWon() {
		for(int i = 0; i < numOfCompPlayers + 1; i++) {
			if(player[i] != currentPlayer && player[i].getDeck().getSize() > 1) {				
				return false;		// At least two players will have cards to play in the next round
			}
		}
		return true;				// Only the currentPlayer will have cards in the next round
	}
	
	/**
	 * Transfers to the round winner any cards in the communal pile from previous round draws
	 * and all the cards played in this round */
	public void transferCardsToWinner() {
		
		// Transfer cards from communal pile if there are any
		if(communalPile.hasCard()) {
			do{
				currentPlayer.getDeck().addCardToBottom(communalPile.getTopCard());
			} while(communalPile.hasCard());
			
			// For assessment testing
			System.out.println("COMMUNAL PILE:\n EMPTY!");
			System.out.println(communalPile.toString());
		}
		
		// Give currentPlayer everyone's played card (including their own as it goes to bottom)
		for(int i = 0; i < numOfCompPlayers + 1; i++) {
			if(player[i].getDeck().hasCard()) {
				player[i].transferCardTo(currentPlayer.getDeck());
			}
		}
	}

	/**
	 * Transfers the top cards from all players participating in the round into the communal pile */
	public void transferCardsToCommunal() {
		for(int i = 0; i <= numOfCompPlayers; i++) {
			if(player[i].getDeck().hasCard()) {			// Player has at least one card and thus has participated in the round
				player[i].transferCardTo(communalPile);
			}
		}
		// For assessment testing
		System.out.println("COMMUNAL PILE:");
		System.out.println(communalPile.toString());
		
		// Check that the current player still has a card - in case of consecutive draws: pass to any other player with a card
		// Specification notes that the case where no players have cards does not need to be dealt with
		if(!currentPlayer.getDeck().hasCard()) {
			for(int i = 0; i <= numOfCompPlayers; i++) {
				if(player[i].getDeck().hasCard()) {
					currentPlayer = player[i];
				}
			}
		}		
	}

	// Accessors
	
	public Deck getCommunalPile(){
		return communalPile;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public Player getHumanPlayer() {
		return player[HUMAN_PLAYER];
	}

	public int getTotalRounds() {
		return totalRounds;
	}

	public int getDraws() {
		return draws;
	}
	
	public Player getPlayer(int i){
		return player[i];
	}
	
	public int getNumOfPlayers() {
		return numOfCompPlayers + 1;
	}
}
