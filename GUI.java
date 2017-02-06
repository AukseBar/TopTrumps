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
	private final int deckSize=40;
	
	private Game game;
	
	
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
		
		
		// Construct the top, middle and bottom with some helpers:
		
		layoutTop();
		layoutMiddle();
		layoutBottom();
		// At bottom as it changes values in middle layout
		
		game = new Game(initDeck());
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
		
		
		this.radioButton = new JRadioButton[]{
				new JRadioButton("*Height"),
				new JRadioButton("*Weight"),
				new JRadioButton("*Length"),
				new JRadioButton("*Ferocity"),
				new JRadioButton("*Intelligence")
		};
		
		this.lblName = new JLabel("*Name:    ");
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
		scrollPane.setViewportView(areaGameMessages);
		
		
		////////////////////////////////////
		//      Play card and other stats //		
		this.panel_bottom_right = new JPanel();
		panel_bottom_right.setBackground(new Color(134, 199, 156));
		panel_bottom.add(panel_bottom_right);
		panel_bottom_right.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		this.btnPlayContinue = new JButton("Play Card!");
		this.lblCardsLeft = new JLabel("Cards Left");
		this.tfCardsLeft = new JTextField();
		this.lblCommunalPile = new JLabel("Communal Pile");
		this.tfCommunal = new JTextField();
		
		tfCardsLeft.setBackground(new Color(134, 199, 156));
		tfCommunal.setBackground(new Color(134, 199, 156));
		btnPlayContinue.setBackground(new Color(134, 199, 156));
		btnPlayContinue.setEnabled(false);
		tfCardsLeft.setEnabled(false);
		tfCommunal.setEnabled(false);
		
		panel_bottom_right.add(btnPlayContinue);
		panel_bottom_right.add(lblCardsLeft);
		panel_bottom_right.add(tfCardsLeft);
		panel_bottom_right.add(lblCommunalPile);
		panel_bottom_right.add(tfCommunal);
		
		// Action listeners on the bottom
		this.btnPlayContinue.addActionListener(this);
		
	}
	
	public Deck initDeck(){	
		
		Deck gameDeck=new Deck(); //create a deck
		String name = "";

		String[] cat = {"","","","",""};
		
		try {   					  //read file for card info
			Scanner scanner = new Scanner(new FileReader("deck.txt"));

			name = scanner.next();

			for (int i =0; i <cat.length ; i++){
				cat[i] = scanner.next();
			}
			
			scanner.nextLine();
			
					while (scanner.hasNextLine())
					{
							///////READ THE REST AND MAKE A NEW CARD FOR EACH NEXTLINE\\\\\\\\\\\
							String info=scanner.nextLine();
						    gameDeck.addCardToTop(info);
						    System.out.println(info);
						 
						}
					scanner.close();
					//Test that deck is filled to 40
					System.err.println(gameDeck.getSize() + " cards added from file");
					
					
					
		  }
		catch(Exception e) {
			e.printStackTrace();
		}

		// Caps on first letter
		name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase() + ":   ";

		for (int i =0; i <cat.length ; i++){
			cat[i] = cat[i].substring(0,1).toUpperCase() + cat[i].substring(1).toLowerCase();
		}

		
		this.lblName.setText(name);
		
		for (int i =0; i <radioButton.length ; i++){
			this.radioButton[i].setText(cat[i]);
			this.radioButton[i].setActionCommand(cat[i]);
		}
		return gameDeck;
	}

	public void displayNewRoundInfo(){
		tfAttrib[0].setText(game.getHumanPlayer().getDeck().seeTopCard().getTitle());
		
		for (int i =1; i <tfAttrib.length ; i++){
			tfAttrib[i].setText(Integer.toString(game.getHumanPlayer().getDeck().seeTopCard().getCategoryValue(i)));
		}
		

		if(game.getCurrentPlayer() == game.getHumanPlayer()) {
			btnPlayContinue.setText("Play Card!");
			areaGameMessages.append("\nIt is your go! Select a category then Play Card!\n");
		}
		else {
			btnPlayContinue.setText("Continue");
			areaGameMessages.append("\nIt is Player " + game.getCurrentPlayer().getPlayerNumber() + "'s go! Are you ready?\nContinue...\n");
		}

		// update player list

		// update cards left
		
		repaint();
		revalidate();

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
							displayRoundWonInfo();
							game.transferCardsToWinner();
						}
						break;		// From switch

					case Game.STATE_ROUND_DRAW:
						displayRoundDrawInfo();
						game.transferCardsToCommunal();
						break;		// From switch
				}
				break;				// From for loop
			}
		}
		displayNewRoundInfo();
	}
	
	private void continueAction() {
		switch(game.calculateRoundResult(game.getCurrentPlayer().chooseCategory())) {
		
			case Game.STATE_ROUND_WON:
				if(game.checkGameWon()) {
					displayGameWonInfo();
				}
				else {
					displayRoundWonInfo();
					game.transferCardsToWinner();
				}
				break;
				
			case Game.STATE_ROUND_DRAW:
				displayRoundDrawInfo();
				game.transferCardsToCommunal();
				break;
		}
		displayNewRoundInfo();
	}
	
	private void displayGameWonInfo() {
		btnPlayContinue.setEnabled(false);

		//updateGameMessages
		if(game.getCurrentPlayer() == game.getHumanPlayer()) {
			areaGameMessages.append("YOU WON THE GAME with the " + game.getCurrentPlayer().getDeck().seeTopCard().getTitle() + "!!!"
				+ "\n\nWould you like to save the statistics from this game to the database?\n");
		}
		else {
			areaGameMessages.append("PLAYER " + game.getCurrentPlayer().getPlayerNumber() + " WON THE GAME with the "
					+ game.getCurrentPlayer().getDeck().seeTopCard().getTitle() + "!!!"
					+ "\n\nWould you like to save the statistics from this game to the database?\n");
		}
		btnViewStats.setEnabled(true);
		btnSaveStats.setEnabled(true);
	}
	
	private void displayRoundWonInfo() {
		//updateGameMessages
		if(game.getCurrentPlayer() == game.getHumanPlayer()) {
			areaGameMessages.append("You won the round with the " + game.getCurrentPlayer().getDeck().seeTopCard().getTitle() + "!!!\n");
		}
		else {
			areaGameMessages.append("Player " + game.getCurrentPlayer().getPlayerNumber() + " won the round with the "
					+ game.getCurrentPlayer().getDeck().seeTopCard().getTitle() + "!!!\n");
		}	
		updateCommunalPile();
	}
	
	private void displayRoundDrawInfo() {
		areaGameMessages.append("It's a draw!\n");
		updateCommunalPile();
	}
	
	private void updateCommunalPile(){
		tfCommunal.setText(String.format("%d",game.getCommunalPile().getSize()));
	}
	
	public void actionPerformed(ActionEvent ae) {
	    if (ae.getSource() == this.btnNewGame) {
			// Adds 1 to the position index to pass the number of computer players chosen
			game.startGame(comboBoxPlayers.getSelectedIndex() + 1);
			displayNewRoundInfo();
			btnPlayContinue.setEnabled(true);
			btnViewStats.setEnabled(false);
		}
	    else if (ae.getSource() == this.btnPlayContinue) {
	    	if(game.getCurrentPlayer() == game.getHumanPlayer()) {
		    	playCard();	
	    	}
	    	else {
		    	continueAction();
	    	}
	    }
	    else if (ae.getSource() == this.btnViewStats) {
	    	System.out.println("You pressed View Stats");
	    	StatsReportFrame statsFrame = new StatsReportFrame();
	    }
	    else if (ae.getSource() == this.btnSaveStats) {
	    	System.out.println("You pressed Save Stats");
	    	System.out.println(game.getCurrentPlayer().getPlayerNumber());
	    }
	}
}
