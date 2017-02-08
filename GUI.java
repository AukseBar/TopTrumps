import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//for deck:
import java.io.FileReader;

import java.util.Scanner;

public class GUI extends JFrame implements ActionListener {

	private JPanel contentPane, panel_top,panel_middle, panel_bottom;
	private JPanel panel_bottom_left, panel_bottom_right, subpanel_current_stats, subpanel_show_attributes, subpanel_categories;
	private JButton btnNewGame,btnPlayContinue,btnViewStats,btnSaveStats;
	private JTextArea areaCurrentStats,areaGameMessages,areaPlayerListing;
	private JComboBox comboBoxPlayers;
	private JLabel lblNumberOfCpu,lblName,lblPlayerListing,lblCardsLeft,lblCommunalPile;

	private JScrollPane scrollPane, scrollPane_2;
	
	private JTextField tfCardsLeft;
	private JTextField tfCommunal;

	private JRadioButton[] radioButton;
	private JTextField[] tfAttrib;
	private ButtonGroup radiogroup;
	
	//for deck
	private Deck mainDeck;
	private static final int DECK_SIZE = 40;
	private static final int NUM_CATEGORIES = 5;
	
	private final Game game;
	
	private int numOfCompPlayers;
	
	
	/**
	 * Create the frame.
	 */
	public GUI() {
		// JFrame settings
		
		setTitle("Top Trumps: Dinosaurs!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		// Main Content Pane
		contentPane = new JPanel();
		contentPane.setBackground(new Color(102, 153, 153));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		initMainDeck();
		
		// Construct the top, middle and bottom with some helpers:
		
		layoutTop();
		layoutMiddle();
		layoutBottom();
		// At bottom as it changes values in middle layout
		
		game = new Game(mainDeck);
	}
	
	public void layoutTop(){
		// Top 'menu' panel
		this.panel_top = new JPanel();
		//Layout and colour
		panel_top.setBackground(new Color(77, 132, 96));
		contentPane.add(panel_top, BorderLayout.NORTH);
		// Buttons and their names
		this.btnNewGame = new JButton("New Game");
		this.lblNumberOfCpu = new JLabel("Number of CPU Players:");
		String [] NoOfPlayers = { "1", "2", "3", "4"};
		this.comboBoxPlayers = new JComboBox(NoOfPlayers);
		this.btnViewStats = new JButton("View Stats");
		this.btnSaveStats = new JButton("Save Stats");
		// Button Colour settings
		this.btnNewGame.setBackground(new Color(134, 199, 156));
		this.btnViewStats.setBackground(new Color(134, 199, 156));
		this.btnSaveStats.setBackground(new Color(134, 199, 156));
		this.btnSaveStats.setEnabled(false);
		// Add all onto layout (in order)
		panel_top.add(btnNewGame);
		panel_top.add(lblNumberOfCpu);
		panel_top.add(comboBoxPlayers);
		panel_top.add(btnViewStats);
		panel_top.add(btnSaveStats);
		// Listeners for button presses
		this.btnNewGame.addActionListener(this);
		this.btnViewStats.addActionListener(this);
		this.btnSaveStats.addActionListener(this);
		}
	
	public void layoutMiddle(){
		// Middle 'views' panel
		this.panel_middle = new JPanel();
		contentPane.add(panel_middle, BorderLayout.CENTER);
		panel_middle.setLayout(new GridLayout());
		
		// Left side sub-panel for current game statistics
		// Panel properties
		this.subpanel_current_stats = new JPanel();
		subpanel_current_stats.setBackground(new Color(134, 199, 156));
		panel_middle.add(subpanel_current_stats);
		subpanel_current_stats.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		//Current Statistics box properties
		this.areaCurrentStats = new JTextArea();
		
		this.areaCurrentStats.setBackground(new Color(134, 199, 156));
		this.areaCurrentStats.setEditable(false); // Toggle to allow editing
		this.areaCurrentStats.setRows(8);
		this.areaCurrentStats.setColumns(8);
		// Silliness HERE
		this.areaCurrentStats.setFont(new Font("Monospaced", Font.BOLD, 13));
		String dino1 = "            __\n           / _)\n    .-^^^-/ /\n __/       /\n<__.|_|-|_|";
		String dino2 = "             /\\\r\n           /\\  /\\\r\n       /\\/\\      /\\\r\n |||..^            ^^o\r\n ------__\\ /---\\ /--~\r\n          | |    ||\r\n          --*    -*";
		this.areaCurrentStats.setText(dino1 + "\n\n" + dino2);
		
		//Add all to panel (ordered)
		
		subpanel_current_stats.add(areaCurrentStats);
		
	
		//Sub-panel for showing the attributes read from a card
		this.subpanel_show_attributes = new JPanel();
		subpanel_show_attributes.setBackground(new Color(134, 199, 156));
		subpanel_show_attributes.setLayout(new GridLayout(0,1,0,0));
		panel_middle.add(subpanel_show_attributes);
		
		
		this.radioButton = new JRadioButton[NUM_CATEGORIES];
		for(int i = 0; i < NUM_CATEGORIES; i++) {
			radioButton[i] = new JRadioButton(mainDeck.getCategoryName(i + 1));
			radioButton[i].setEnabled(false);
		}
		
		this.lblName = new JLabel("Description");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);

		
		// Add all in order
		subpanel_show_attributes.add(lblName);

		
		this.radiogroup = new ButtonGroup();

		for (int i =0; i <radioButton.length ; i++){
			radioButton[i].setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			radioButton[i].setBackground(new Color(134, 199, 156));
			subpanel_show_attributes.add(radioButton[i]);
			radiogroup.add(radioButton[i]);
		}
		
		
		
		radioButton[0].setSelected(true);

		/////////////////////////////////////////////////
		
		
		//Sub-panel for the actual data
		this.subpanel_categories = new JPanel();
		subpanel_categories.setBackground(new Color(134, 199, 156));
		subpanel_categories.setLayout(new GridLayout(0,1,0,0));
		panel_middle.add(subpanel_categories);
		

		this.tfAttrib = new JTextField[]{
				new JTextField(),
				new JTextField(),
				new JTextField(),
				new JTextField(),
				new JTextField(),
				new JTextField()
				
		};
		
		for (int i =0; i <tfAttrib.length ; i++){
			tfAttrib[i].setFont(new Font("Showcard Gothic", Font.PLAIN, 13));
			tfAttrib[i].setBorder(null);
			tfAttrib[i].setBackground(new Color(134, 199, 156));
			subpanel_categories.add(tfAttrib[i]);
			}
		
		}
	
	public void layoutBottom(){
		
		// Bottom panel for in-game messages and save game button
		this.panel_bottom = new JPanel();
		panel_bottom.setBackground(new Color(77, 132, 96));
		contentPane.add(panel_bottom, BorderLayout.SOUTH);
		panel_bottom.setLayout(new BoxLayout(panel_bottom, BoxLayout.X_AXIS));
		
		//////////////////////////////////
		//      Player Listing Area    //
		
		this.panel_bottom_left = new JPanel();
		this.lblPlayerListing = new JLabel("Player Listing");
		this.scrollPane_2 = new JScrollPane();
		this.areaPlayerListing = new JTextArea();
		
		panel_bottom_left.setBackground(new Color(134, 199, 156));
		panel_bottom_left.setLayout(new BoxLayout(panel_bottom_left, BoxLayout.Y_AXIS));
		areaPlayerListing.setBackground(new Color(134, 199, 156));
		scrollPane_2.setViewportView(areaPlayerListing);
		areaPlayerListing.setRows(4);
		areaPlayerListing.setColumns(10);
		areaPlayerListing.setEditable(false);
		
		panel_bottom.add(panel_bottom_left);
		panel_bottom_left.add(lblPlayerListing);
		panel_bottom_left.add(scrollPane_2);
		
		
		////////////////////////////////////
		//       GAME MESSAGES           //
		
		// Add a scrollpane
		this.scrollPane = new JScrollPane();
		panel_bottom.add(scrollPane);
		
		this.areaGameMessages = new JTextArea();
		areaGameMessages.setBackground(new Color(134, 199, 156));
		areaGameMessages.setEditable(false); // Toggle to allow editing
		areaGameMessages.setRows(5);
		areaGameMessages.setColumns(25);
		areaGameMessages.setLineWrap(true);
		areaGameMessages.setWrapStyleWord(true);
		scrollPane.setViewportView(areaGameMessages);
		
		
		////////////////////////////////////
		//      Play card and other stats //		
		this.panel_bottom_right = new JPanel();
		panel_bottom_right.setBackground(new Color(134, 199, 156));
		panel_bottom.add(panel_bottom_right);
		panel_bottom_right.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		this.btnPlayContinue = new JButton("Play Card!");
		this.lblCardsLeft = new JLabel("Your Cards Left");
		this.tfCardsLeft = new JTextField();
		this.lblCommunalPile = new JLabel("Communal Pile");
		this.tfCommunal = new JTextField();
		
		tfCardsLeft.setBackground(new Color(134, 199, 156));
		tfCommunal.setBackground(new Color(134, 199, 156));
		btnPlayContinue.setBackground(new Color(134, 199, 156));
		btnPlayContinue.setEnabled(false);
		tfCardsLeft.setEnabled(false);
		tfCardsLeft.setDisabledTextColor(Color.BLACK);		
		tfCommunal.setEnabled(false);
		tfCommunal.setDisabledTextColor(Color.BLACK);
		
		panel_bottom_right.add(btnPlayContinue);
		panel_bottom_right.add(lblCardsLeft);
		panel_bottom_right.add(tfCardsLeft);
		panel_bottom_right.add(lblCommunalPile);
		panel_bottom_right.add(tfCommunal);
		
		// Action listeners on the bottom
		this.btnPlayContinue.addActionListener(this);
		
	}
	
	public void initMainDeck(){

		// Read file for card info
		try {
			Scanner scanner = new Scanner(new FileReader("deck.txt"));
			scanner.useDelimiter("\\s+");

			// Skip past first column  - always: "Description"
			scanner.next();
			
			// Category names
			String[] cat = new String[NUM_CATEGORIES];
			for (int i = 0; i < NUM_CATEGORIES ; i++){
				cat[i] = scanner.next();
			}
			// Capitalise
			for (int i =0; i <cat.length ; i++){
				cat[i] = cat[i].substring(0,1).toUpperCase() + cat[i].substring(1).toLowerCase();
			}

			// Can now create the main deck
			mainDeck = new Deck(DECK_SIZE, cat);

			///////READ THE REST AND MAKE A NEW CARD FOR EACH NEXTLINE\\\\\\\\\\\
			scanner.nextLine();
			while (scanner.hasNextLine())
			{
				String info = scanner.nextLine();
				mainDeck.addCardToTop(info);
			}
			scanner.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		// For assessment testing
		System.out.println("INITIAL DECK:");
		System.out.println(mainDeck.toString());
	}

	private void playCard() {
		for (int i = 1; i <= radioButton.length; i++){
			if (radioButton[i-1].isSelected()){
				switch(game.calculateRoundResult(i)){
				
					case Game.STATE_ROUND_WON:
						if(game.checkGameWon()) {
							displayGameWonInfo();
						}
						else {
							displayRoundWonInfo(i);
							game.transferCardsToWinner();
							displayNewRoundInfo();
						}
						break;		// From switch

					case Game.STATE_ROUND_DRAW:
						displayRoundDrawInfo();
						game.transferCardsToCommunal();
						displayNewRoundInfo();
						break;		// From switch
				}
				break;				// From for loop
			}
		}
	}
	
	private void continueAction() {
		int chosenCategory = game.getCurrentPlayer().chooseCategory();
		switch(game.calculateRoundResult(chosenCategory)) {
		
			case Game.STATE_ROUND_WON:
				if(game.checkGameWon()) {
					displayGameWonInfo();
				}
				else {
					displayRoundWonInfo(chosenCategory);
					game.transferCardsToWinner();
					displayNewRoundInfo();
				}
				break;
				
			case Game.STATE_ROUND_DRAW:
				displayRoundDrawInfo();
				game.transferCardsToCommunal();
				displayNewRoundInfo();
				break;
		}
	}
	
	public void displayNewRoundInfo(){
		
		// For assessment testing
		for(int i = 0; i < numOfCompPlayers + 1; i++) {
			if(i > 0) {
				System.out.println("PLAYER " + i + "'S DECK:");
				System.out.println(game.getPlayer(i).getDeck().toString());
			}
			else {
				System.out.println("HUMAN PLAYER'S DECK:");
				System.out.println(game.getHumanPlayer().getDeck().toString());
			}
		}
		
		// Update displayed card for human player
		if(game.getHumanPlayer().getDeck().hasCard()) {
			tfAttrib[0].setText(game.getHumanPlayer().getDeck().seeTopCard().getTitle());
			for (int i =1; i <tfAttrib.length ; i++){
				tfAttrib[i].setText(Integer.toString(game.getHumanPlayer().getDeck().seeTopCard().getCategoryValue(i)));
			}
		}
		
		// Set radio buttons enabled if the player has choice, or disabled otherwise
		if(game.getCurrentPlayer() == game.getHumanPlayer()) {
			for(JRadioButton b : radioButton) {
				b.setEnabled(true);
			}
		}
		else {
			for(JRadioButton b : radioButton) {
				b.setEnabled(false);
			}
		}
		
		// Update the game messages area for the context of the new round
		if(game.getCurrentPlayer() == game.getHumanPlayer()) {
			btnPlayContinue.setText("Play Card!");
			areaGameMessages.append("\nIt is your go! Select a category then Play Card!\n");
		}
		else {
			btnPlayContinue.setText("Continue");
			areaGameMessages.append("\nIt is Player " + game.getCurrentPlayer().getPlayerNumber() + "'s go! Are you ready?\nContinue...\n");
		}

		// Update other GUI information areas
		updateCommunalPile();
		updateCardsLeft();
		updatePlayerList();
		
		repaint();
		revalidate();
		
		// For assessment testing
		System.out.print("CARDS IN PLAY:\n(choice) ");
		if(game.getCurrentPlayer() != game.getHumanPlayer()) {
			System.out.println("PLAYER " + game.getCurrentPlayer().getPlayerNumber() + "'S CARD: "
					+ game.getCurrentPlayer().getDeck().seeTopCard().toString());
		}
		else System.out.println("HUMAN PLAYER'S CARD: " + game.getCurrentPlayer().getDeck().seeTopCard().toString());
		for(int i = 0; i < numOfCompPlayers + 1; i++) {
			if(game.getPlayer(i) != game.getCurrentPlayer() && game.getPlayer(i).getDeck().hasCard()) {
				if(i > 0) System.out.println("PLAYER " + i + "'S CARD: " + game.getPlayer(i).getDeck().seeTopCard().toString());
				else System.out.println("HUMAN PLAYER'S CARD: " + game.getPlayer(i).getDeck().seeTopCard().toString());
			}
		}
		System.out.println("---------------------------------\n");
		
	}
	
	private void updateCommunalPile(){
		tfCommunal.setText(String.format("%d",game.getCommunalPile().getSize()));
	}
	
	private void updateCardsLeft(){
    	tfCardsLeft.setText(String.format("%d",game.getHumanPlayer().getDeck().getSize()));
	}
	
	private void updatePlayerList(){
		areaPlayerListing.setText("Computer Players:\n");
		for (int i=1; i<= comboBoxPlayers.getSelectedIndex()+1; i++)
		areaPlayerListing.append("Player "+i+ ". Cards left: " + game.getPlayer(i).getDeck().getSize()+"\n");
	}
	
	private void displayGameWonInfo() {
		
		// Set buttons for the current game context
		btnPlayContinue.setEnabled(false);
		btnViewStats.setEnabled(true);
		btnSaveStats.setEnabled(true);

		//  Update game messages for player
		if(game.getCurrentPlayer() == game.getHumanPlayer()) {
			areaGameMessages.append("\nYOU WON THE GAME with the " + game.getCurrentPlayer().getDeck().seeTopCard().getTitle() + "!!!\n"
				+ "\nWould you like to save the statistics from this game to the database?\n");
		}
		else {
			areaGameMessages.append("\nPLAYER " + game.getCurrentPlayer().getPlayerNumber() + " WON THE GAME with the "
					+ game.getCurrentPlayer().getDeck().seeTopCard().getTitle() + "!!!\n"
					+ "\nWould you like to save the statistics from this game to the database?\n");
		}
		
		// For assessment testing
		if(game.getCurrentPlayer() == game.getHumanPlayer()) System.out.println("The winner of the game is: YOU!");
		else System.out.println("The winner of the game is: PLAYER " + game.getCurrentPlayer().getPlayerNumber() + "!\n YOU SUCK!");
	}
	
	private void displayRoundWonInfo(int chosenAttributeIndex) {
		//updateGameMessages
		Card winningCard = game.getCurrentPlayer().getDeck().seeTopCard();
		Card losingCard;
		
		// Human player wins the round
		if(game.getCurrentPlayer() == game.getHumanPlayer()) {
			areaGameMessages.append("\nYou won the round with the " + winningCard.getTitle() + "'s "
					+ mainDeck.getCategoryName(chosenAttributeIndex) + " (" + winningCard.getCategoryValue(chosenAttributeIndex)
					+ ") against:\n");
			// Iterate through all computer players who played the round
			for (int i = 1; i < numOfCompPlayers + 1; i++) {
				if(game.getPlayer(i).getDeck().hasCard()) {
					losingCard = game.getPlayer(i).getDeck().seeTopCard();
					areaGameMessages.append(" Player " + i + "'s " + losingCard.getTitle()
					+ "(" + losingCard.getCategoryValue(chosenAttributeIndex) + ")\n");
				}
			}
		}
		// A computer player wins the round
		else {
			// Winning computer player's details
			areaGameMessages.append("Player " + game.getCurrentPlayer().getPlayerNumber() + " won the round with the "
					+ winningCard.getTitle() + "'s " + mainDeck.getCategoryName(chosenAttributeIndex)
					+ " (" + winningCard.getCategoryValue(chosenAttributeIndex)+") against:\n");
			
			// Iterate through all the players
			for (int i = 0; i < numOfCompPlayers + 1; i++){
				// Skipping the winner of the round and anyone who did not play in the round
				if (game.getPlayer(i) == game.getCurrentPlayer() || !game.getPlayer(i).getDeck().hasCard()) {
					continue;
				}
				// Human player's card
				else if(game.getPlayer(i) == game.getHumanPlayer()) {
					losingCard = game.getHumanPlayer().getDeck().seeTopCard();
					areaGameMessages.append("Your " + losingCard.getTitle()
							+ " (" + losingCard.getCategoryValue(chosenAttributeIndex) + ")\n");
				}   
				// Rest of the computer players' cards
				else{
					losingCard = game.getPlayer(i).getDeck().seeTopCard();
					areaGameMessages.append("Player " + i + "'s " + losingCard.getTitle()+
							" ("+losingCard.getCategoryValue(chosenAttributeIndex)+")\n");
				}
			}
		}	
	}

	private void displayRoundDrawInfo() {
		areaGameMessages.append("\nIt's a draw!\n");
	}
	
	public void actionPerformed(ActionEvent ae) {
		
		// New Game
	    if (ae.getSource() == this.btnNewGame) {
	    	numOfCompPlayers = Integer.parseInt(comboBoxPlayers.getSelectedItem().toString());
			game.startGame(numOfCompPlayers);
			displayNewRoundInfo();
			btnPlayContinue.setEnabled(true);
			btnViewStats.setEnabled(false);
		}
	    
	    // Play/Continue
	    else if (ae.getSource() == this.btnPlayContinue) {
	    	if(game.getCurrentPlayer() == game.getHumanPlayer()) {
		    	playCard();	
	    	}
	    	else {
		    	continueAction();
	    	}
	    }
	    
	    // View Stats
	    else if (ae.getSource() == this.btnViewStats) {
	    	System.out.println("You pressed View Stats");
	    	StatsReportFrame statsFrame = new StatsReportFrame();
	    }
	    
	    // Save Stats
	    else if (ae.getSource() == this.btnSaveStats) {
	    	System.out.println("You pressed Save Stats");
	    	System.out.println(game.getCurrentPlayer().getPlayerNumber());
	    }
	    
	}
}
