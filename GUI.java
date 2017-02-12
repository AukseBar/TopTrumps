import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//for deck:
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
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
	
	private StatsReportFrame statsFrame;
	
	private int numOfCompPlayers;
	
	
	/**
	 * Create the frame.
	 */
	public GUI() {
		// JFrame settings
		setTitle("Top Trumps: Dinosaurs!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 600, 530);
		
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
		
		pack();
		setVisible(true);
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
		this.areaCurrentStats.setFont(new Font("Monospaced", Font.BOLD, 3));
		String dino1 = "$$$$$$$$$$$$$$$$$$$$$$$$$$$**\"\"\"\"`` ````\"\"\"#*R$$$$$$$$$$$$$$$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$$$$$$$$$*\"\"      ..........      `\"#$$$$$$$$$$$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$$$$$$#\"    .ue@$$$********$$$$Weu.   `\"*$$$$$$$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$$$#\"   ue$$*#\"\"              `\"\"*$$No.   \"R$$$$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$P\"   u@$*\"`                         \"#$$o.  ^*$$$$$$$$$$$$$$\r\n$$$$$$$$$$$P\"  .o$R\"               . .WN.           \"#$Nu  `#$$$$$$$$$$$$\r\n$$$$$$$$$$\"  .@$#`       'ou  .oeW$$$$$$$$W            \"$$u  \"$$$$$$$$$$$\r\n$$$$$$$$#   o$#`      ueL  $$$$$$$$$$$$$$$$ku.           \"$$u  \"$$$$$$$$$\r\n$$$$$$$\"  x$P`        `\"$$u$$$$$$$$$$$$$$\"#$$$L            \"$o   *$$$$$$$\r\n$$$$$$\"  d$\"        #$u.2$$$$$$$$$$$$$$$$  #$$$Nu            $$.  #$$$$$$\r\n$$$$$\"  @$\"          $$$$$$$$$$$$$$$$$$$$k  $$#*$$u           #$L  #$$$$$\r\n$$$$\"  d$         #Nu@$$$$$$$$$$$$$$$$$$\"  x$$L #$$$o.         #$c  #$$$$\r\n$$$F  d$          .$$$$$$$$$$$$$$$$$$$$N  d$$$$  \"$$$$$u        #$L  #$$$\r\n$$$  :$F        ..`$$$$$$$$$$$$$$$$$$$$$$$$$$$`    R$$$$$eu.     $$   $$$\r\n$$!  $$        . R$$$$$$$$$$$$$$$$$$$$$$$$$$$$$.   @$$$$$$$$Nu   '$N  `$$\r\n$$  x$\"        Re$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$uu@\"``\"$$$$$$$i   #$:  $$\r\n$E  $$       c 8$$$$$$$$$$$$$$$$$$$$$G(   ``^*$$$$$$$WW$$$$$$$$N   $$  4$\r\n$~ :$$N. tL i)$$$$$$$$$$$$$$$$$$$$$$$$$N       ^#R$$$$$$$$$$$$$$$  9$  '$\r\n$  t$$$$u$$W$$$$$$$$$$$$$$!$$$$$$$$$$$$$&       . c?\"*$$$R$$$$$$$  '$k  $\r\n$  @$$$$$$$$$$$$$$$$$$$$\"E F!$$$$$$$$$$.\"        +.\"@\\* x .\"\"*$$\"   $B  $\r\n$  $$$$$$$$$$$$$$$$\"$)#F     $$$$$$$$$$$           `  -d>x\"*=.\"`    $$  $\r\n$  $$$$$$$$$$?$$R'$ `#d$\"\"    #$$$$$$$$$ > .                \"       $$  $\r\n$  $$$$$$$($$@$\"` P *@$.@#\"!    \"*$$$$$$$L!.                        $$  $\r\n$  9$$$$$$$L#$L  ! \" <$$`          \"*$$$$$NL:\"z  f                  $E  $\r\n$> ?$$$$ $$$b$^      .$c .ueu.        `\"$$$$b\"x\"#  \"               x$!  $\r\n$k  $$$$N$ \"$$L:$oud$$$` d$ .u.         \"$$$$$o.\" #f.              $$   $\r\n$$  R$\"\"$$o.$\"$$$$\"\"\" ue$$$P\"`\"c          \"$$$$$$Wo'              :$F  t$\r\n$$: '$&  $*$$u$$$$u.ud$R\" `    ^            \"#*****               @$   $$\r\n$$N  #$: E 3$$$$$$$$$\"                                           d$\"  x$$\r\n$$$k  $$   F *$$$$*\"                                            :$P   $$$\r\n$$$$  '$b                                                      .$P   $$$$\r\n$$$$b  `$b                                                    .$$   @$$$$\r\n$$$$$N  \"$N                                                  .$P   @$$$$$\r\n$$$$$$N  '*$c                                               u$#  .$$$$$$$\r\n$$$$$$$$.  \"$N.                                           .@$\"  x$$$$$$$$\r\n$$$$$$$$$o   #$N.                                       .@$#  .@$$$$$$$$$\r\n$$$$$$$$$$$u  `#$Nu                                   u@$#   u$$$$$$$$$$$\r\n$$$$$$$$$$$$$u   \"R$o.                             ue$R\"   u$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$$o.  ^#$$bu.                     .uW$P\"`  .u$$$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$$$$$u   `\"#R$$Wou..... ....uueW$$*#\"   .u@$$$$$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$$$$$$$Nu.    `\"\"#***$$$$$***\"\"\"`    .o$$$$$$$$$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$$$$$$$$$$$$eu..               ...ed$$$$$$$$$$$$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$NWWeeeeedW@$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\r\n";
		String dino2 = "\n\n\n888888888888                     \r\n     88                          \r\n     88                          \r\n     88  ,adPPYba,  8b,dPPYba,   \r\n     88 a8\"     \"8a 88P'    \"8a  \r\n     88 8b       d8 88       d8  \r\n     88 \"8a,   ,a8\" 88b,   ,a8\"  \r\n     88  `\"YbbdP\"'  88`YbbdP\"'   \r\n                    88           \r\n                    88           \r\n                                                                         \r\n888888888888                                                             \r\n     88                                                                  \r\n     88                                                                  \r\n     88 8b,dPPYba, 88       88 88,dPYba,,adPYba,  8b,dPPYba,  ,adPPYba,  \r\n     88 88P'   \"Y8 88       88 88P'   \"88\"    \"8a 88P'    \"8a I8[    \"\"  \r\n     88 88         88       88 88      88      88 88       d8  `\"Y8ba,   \r\n     88 88         \"8a,   ,a88 88      88      88 88b,   ,a8\" aa    ]8I  \r\n     88 88          `\"YbbdP'Y8 88      88      88 88`YbbdP\"'  `\"YbbdP\"'  \r\n                                                  88                     \r\n                                                  88                     \r\n                                                                                                \r\n88888888ba,   88                                                                                \r\n88      `\"8b  \"\"                                                                                \r\n88        `8b                                                                                   \r\n88         88 88 8b,dPPYba,   ,adPPYba,  ,adPPYba, ,adPPYYba, 88       88 8b,dPPYba, ,adPPYba,  \r\n88         88 88 88P'   `\"8a a8\"     \"8a I8[    \"\" \"\"     `Y8 88       88 88P'   \"Y8 I8[    \"\"  \r\n88         8P 88 88       88 8b       d8  `\"Y8ba,  ,adPPPPP88 88       88 88          `\"Y8ba,   \r\n88      .a8P  88 88       88 \"8a,   ,a8\" aa    ]8I 88,    ,88 \"8a,   ,a88 88         aa    ]8I  \r\n88888888Y\"'   88 88       88  `\"YbbdP\"'  `\"YbbdP\"' `\"8bbdP\"Y8  `\"YbbdP'Y8 88         `\"YbbdP\"'  ";
		this.areaCurrentStats.setText(dino2 + "\n\n" + dino1);
		
		this.areaCurrentStats.setForeground(Color.WHITE);
		
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
			tfAttrib[i].setEditable(false);
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
			for(int i = 0; scanner.hasNextLine() && i < DECK_SIZE; i++) {
				String info = scanner.nextLine();
				mainDeck.addCardToTop(info);
			}
			System.out.println(mainDeck.getSize());
			scanner.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		// For assessment testing
		System.out.println("INITIAL DECK:");
		System.out.println(mainDeck.toString());
	}

	private void playContinueAction() {
		int chosenCategory = -1;
		
		// Play button pressed
		if(game.getCurrentPlayer() == game.getHumanPlayer()) {
			for (int i = 1; i <= radioButton.length; i++) {
				if (radioButton[i-1].isSelected()) {
					chosenCategory = i;
					break;
				}
			}
		}
		// Continue button pressed
		else {
			chosenCategory = game.getCurrentPlayer().chooseCategory();
		}
		
		// Switch to game updates based on round result
		switch(game.calculateRoundResult(chosenCategory)) {
			case Game.STATE_ROUND_WON:
				if(game.checkGameWon()) {		// Entire game won
					displayGameWonInfo();
					game.transferCardsToWinner();
					updateCommunalPile();
					updateCardsLeft();
					updatePlayerList();
				}
				else {							// Just round won
					displayRoundWonInfo(chosenCategory);
					game.transferCardsToWinner();
					displayNewRoundInfo();
				}
				break;
			case Game.STATE_ROUND_DRAW:			// Round draw
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
			areaGameMessages.append("\nIt is your go! Select a category then Play Card!");
		}
		else {
			btnPlayContinue.setText("Continue");
			areaGameMessages.append("\nIt is Player " + game.getCurrentPlayer().getPlayerNumber() + "'s go! Are you ready? Continue...");
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
		for (int i=1; i<= numOfCompPlayers; i++)
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
		else System.out.println("The winner of the game is: PLAYER " + game.getCurrentPlayer().getPlayerNumber() + "!\n Better luck next time pal!");
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
					areaGameMessages.append("Player " + i + "'s " + losingCard.getTitle()
					+ "(" + losingCard.getCategoryValue(chosenAttributeIndex) + ")\n");
				}
			}
		}
		// A computer player wins the round
		else {
			// Winning computer player's details
			areaGameMessages.append("\nPlayer " + game.getCurrentPlayer().getPlayerNumber() + " won the round with the "
					+ winningCard.getTitle() + "'s " + mainDeck.getCategoryName(chosenAttributeIndex)
					+ " (" + winningCard.getCategoryValue(chosenAttributeIndex)+") against:\n");
			
			// Iterate through all the players
			for (int i = 0; i < numOfCompPlayers + 1; i++) {
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
			btnSaveStats.setEnabled(false);
		}
	    
	    // Play/Continue
	    else if (ae.getSource() == this.btnPlayContinue) {
	    	playContinueAction();	
	    }
	    
	    // View Stats
	    else if (ae.getSource() == this.btnViewStats) {
	    	try {
	    		statsFrame = new StatsReportFrame(game, false);
	    	}
	    	catch(SQLException ex) {
	    		ex.printStackTrace();
	    		JOptionPane.showMessageDialog(null, "Databse connection attempt failed.", "Error", JOptionPane.ERROR_MESSAGE);
	    	}
	    	
	    	/*System.out.println("You pressed View Stats");*/
	    }
	    
	    // Save Stats
	    else if (ae.getSource() == this.btnSaveStats) {
	    	try {
	    		statsFrame = new StatsReportFrame(game, true);
	    	}
	    	catch(SQLException ex) {
	    		ex.printStackTrace();
	    		JOptionPane.showMessageDialog(null, "Database connection attempt failed.", "Error", JOptionPane.ERROR_MESSAGE);
	    	}
	    	
	    	/*System.out.println("You pressed Save Stats");
	    	System.err.println("Total rounds " + game.getTotalRounds());
	    	if(game.getCurrentPlayer() == game.getHumanPlayer()) {
	    		System.err.println("Human Won");
	    	}
	    	else{
	    		System.err.println("PLAYER " + game.getCurrentPlayer().getPlayerNumber() + " WON");
	    	}
	    	// ADD how many round each won.
	    	*/
	    }
	    
	}
}
