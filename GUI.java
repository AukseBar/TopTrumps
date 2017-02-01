import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//for deck:
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class GUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnNewGame,btnPlayCard,btnViewStats,btnSaveStats;
	private JTextArea areaCurrentStats,areaGameMessages;
	private JComboBox comboBoxPlayers;
	private JLabel lblNumberOfCpu,lblCurrentPlayers,lblName;
	private JRadioButton radioButton_1, radioButton_2, radioButton_3, radioButton_4, radioButton_5;
	private JTextField tfName,tfAttrib1,tfAttrib2,tfAttrib3,tfAttrib4,tfAttrib5;
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
		JPanel panel_top = new JPanel();
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
		JPanel panel_middle = new JPanel();
		contentPane.add(panel_middle, BorderLayout.CENTER);
		panel_middle.setLayout(new GridLayout());
		
		// Left side sub-panel for current game statistics
		// Panel properties
		JPanel subpanel_current_stats = new JPanel();
		subpanel_current_stats.setBackground(new Color(134, 199, 156));
		panel_middle.add(subpanel_current_stats);
		subpanel_current_stats.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		// Label
		this.lblCurrentPlayers = new JLabel("Current Game Statistics:");
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
		subpanel_current_stats.add(lblCurrentPlayers);
		subpanel_current_stats.add(areaCurrentStats);
		
	
		//Sub-panel for showing the attributes read from a card
		JPanel subpanel_show_attributes = new JPanel();
		subpanel_show_attributes.setBackground(new Color(134, 199, 156));
		subpanel_show_attributes.setLayout(new GridLayout(0,1,0,0));
		panel_middle.add(subpanel_show_attributes);
		
		this.lblName = new JLabel("*Name:    ");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		this.radioButton_1 = new JRadioButton("*Height");
		this.radioButton_2 = new JRadioButton("*Weight");
		this.radioButton_3 = new JRadioButton("*Length");
		this.radioButton_4 = new JRadioButton("*Ferocity");
		this.radioButton_5 = new JRadioButton("*Intelligence");
		
		radioButton_1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		radioButton_2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		radioButton_3.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		radioButton_4.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		radioButton_5.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		radioButton_1.setBackground(new Color(134, 199, 156));
		radioButton_2.setBackground(new Color(134, 199, 156));
		radioButton_3.setBackground(new Color(134, 199, 156));
		radioButton_4.setBackground(new Color(134, 199, 156));
		radioButton_5.setBackground(new Color(134, 199, 156));
		
		// Add all in order
		subpanel_show_attributes.add(lblName);
		subpanel_show_attributes.add(radioButton_1);
		subpanel_show_attributes.add(radioButton_2);
		subpanel_show_attributes.add(radioButton_3);
		subpanel_show_attributes.add(radioButton_4);
		subpanel_show_attributes.add(radioButton_5);
		
		this.radiogroup = new ButtonGroup();
		radiogroup.add(radioButton_1);
		radiogroup.add(radioButton_2);
		radiogroup.add(radioButton_3);
		radiogroup.add(radioButton_4);
		radiogroup.add(radioButton_5);
		radioButton_1.setSelected(true);

		/////////////////////////////////////////////////
		
		
		//Sub-panel for the actual data
		JPanel subpanel_categories = new JPanel();
		subpanel_categories.setBackground(new Color(134, 199, 156));
		subpanel_categories.setLayout(new GridLayout(0,1,0,0));
		panel_middle.add(subpanel_categories);
		
		this.tfName = new JTextField();
		this.tfAttrib1 = new JTextField();
		this.tfAttrib2 = new JTextField();
		this.tfAttrib3 = new JTextField();
		this.tfAttrib4 = new JTextField();
		this.tfAttrib5 = new JTextField();
		// Makes it blend into background colour
		tfName.setBorder(null);
		tfAttrib1.setBorder(null);
		tfAttrib2.setBorder(null);
		tfAttrib3.setBorder(null);
		tfAttrib4.setBorder(null);
		tfAttrib5.setBorder(null);
		// Set box colours
		tfName.setBackground(new Color(134, 199, 156));
		tfAttrib1.setBackground(new Color(134, 199, 156));
		tfAttrib2.setBackground(new Color(134, 199, 156));
		tfAttrib3.setBackground(new Color(134, 199, 156));
		tfAttrib4.setBackground(new Color(134, 199, 156));
		tfAttrib5.setBackground(new Color(134, 199, 156));
		// Add all in order
		subpanel_categories.add(tfName);
		subpanel_categories.add(tfAttrib1);
		subpanel_categories.add(tfAttrib2);
		subpanel_categories.add(tfAttrib3);
		subpanel_categories.add(tfAttrib4);
		subpanel_categories.add(tfAttrib5);
		
		
		}
	
	public void layoutBottom(){
		
		// Bottom panel for in-game messages and save game button
		JPanel panel_bottom = new JPanel();
		panel_bottom.setBackground(new Color(77, 132, 96));
		contentPane.add(panel_bottom, BorderLayout.SOUTH);
		panel_bottom.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		// Add a scrollpane
		JScrollPane scrollPane = new JScrollPane();
		panel_bottom.add(scrollPane);
		
		this.areaGameMessages = new JTextArea();
		areaGameMessages.setBackground(new Color(134, 199, 156));
		areaGameMessages.setEditable(false); // Toggle to allow editing
		areaGameMessages.setRows(5);
		areaGameMessages.setColumns(25);
		scrollPane.setViewportView(areaGameMessages);
		
		this.btnPlayCard = new JButton("Play Card!");
		btnPlayCard.setPreferredSize(new Dimension(160, 50));
		btnPlayCard.setBackground(new Color(134, 199, 156));
		btnPlayCard.setEnabled(false);
		panel_bottom.add(btnPlayCard);
		
		// Action listeners on the bottom
		this.btnPlayCard.addActionListener(this);
		
	}
	
	public Deck initDeck(){	
		
		Deck gameDeck=new Deck(); //create a deck
		String name = "";
		String cat1 = "";
		String cat2 = "";
		String cat3 = "";
		String cat4 = "";
		String cat5 = "";
		
		try{   					  //read file for card info
			Scanner scanner = new Scanner(new FileReader("deck.txt"));
//			for (int i=0; i<deckSize; i++){
			//scanner.nextLine();
			name = scanner.next();
			cat1 = scanner.next();
			cat2 = scanner.next();
			cat3 = scanner.next();
			cat4 = scanner.next();
			cat5 = scanner.next();	
			scanner.nextLine();
			
					while (scanner.hasNextLine())
					{
//						if (i==0){
//							System.out.println(scanner.nextLine());
//						}   // first line contains category names
//						// might want to work this first line into displaying category names in GUI
//						else{
							///////READ THE REST AND MAKE A NEW CARD FOR EACH NEXTLINE\\\\\\\\\\\
							String info=scanner.nextLine();
						    gameDeck.addCardToTop(info);
						    System.out.println(info);
						 
						}
					scanner.close();
					//Test that deck is filled to 40
					System.err.println(gameDeck.getSize() + " cards added from file");
					
					
					
		  }
		catch(Exception e){ e.printStackTrace();}
		// Caps on first letter
		name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase() + ":   ";
		cat1 = cat1.substring(0,1).toUpperCase() + cat1.substring(1).toLowerCase();
		cat2 = cat2.substring(0,1).toUpperCase() + cat2.substring(1).toLowerCase();
		cat3 = cat3.substring(0,1).toUpperCase() + cat3.substring(1).toLowerCase();
		cat4 = cat4.substring(0,1).toUpperCase() + cat4.substring(1).toLowerCase();
		cat5 = cat5.substring(0,1).toUpperCase() + cat5.substring(1).toLowerCase();
		// Setup descriptions
		this.lblName.setText(name);
		this.radioButton_1.setText(cat1);
        this.radioButton_2.setText(cat2);
        this.radioButton_3.setText(cat3);
        this.radioButton_4.setText(cat4);
        this.radioButton_5.setText(cat5);
		// TESTING BUTTON ACTIONS
		this.radioButton_1.setActionCommand(cat1);
		this.radioButton_2.setActionCommand(cat2);
		this.radioButton_3.setActionCommand(cat3);
		this.radioButton_4.setActionCommand(cat4);
		this.radioButton_5.setActionCommand(cat5);
		
		return gameDeck;
	}
		
	public void updatePlayerlist(){
			
			
	}
	
	private void newGame() {
		 game.startGame(comboBoxPlayers.getSelectedIndex()+1);			// ******** better way to do the drop box??
		 tfName.setText(game.getCurrentPlayer().getDeck().seeTopCard().getTitle());
		 tfAttrib1.setText(Integer.toString(game.getCurrentPlayer().getDeck().seeTopCard().getCategoryValue(1)));
		 tfAttrib2.setText(Integer.toString(game.getCurrentPlayer().getDeck().seeTopCard().getCategoryValue(2)));
		 tfAttrib3.setText(Integer.toString(game.getCurrentPlayer().getDeck().seeTopCard().getCategoryValue(3)));
		 tfAttrib4.setText(Integer.toString(game.getCurrentPlayer().getDeck().seeTopCard().getCategoryValue(4)));
		 tfAttrib5.setText(Integer.toString(game.getCurrentPlayer().getDeck().seeTopCard().getCategoryValue(5)));
		 btnPlayCard.setEnabled(true);
	}
	
	private void playCard() {
		if(radioButton_1.isSelected()) {
			switch(game.calculateRoundResult(1)) {
			case 1: 
			case 2: roundWon();
			case 3: 
			}
		}
		if(radioButton_2.isSelected()) {
			game.calculateRoundResult(2);
		}
		if(radioButton_3.isSelected()) {
			game.calculateRoundResult(3);
		}
		if(radioButton_4.isSelected()) {
			game.calculateRoundResult(4);
		}
		if(radioButton_5.isSelected()) {
			game.calculateRoundResult(5);
		}
//		
//		for(int i = 1; i < 5; i++) {
//			
//		}
	}
	
	private void roundWon() {
		btnPlayCard.setEnabled(false);
		//updateGameMessages
		areaGameMessages.setText("You won the round with blah blah");
		//updatePlayerlist();
	}
	
	public void actionPerformed(ActionEvent ae)
	{
	    if (ae.getSource() == this.btnNewGame) {
	    	//System.out.println("You pressed New Game");
	    	newGame();
	    	
	    } else if (ae.getSource() == this.btnPlayCard) {
	    	
	    	playCard();
	    	
//	    	// TESTING FOR SELECTIoN OF A BUTTON
//	    	ButtonModel buttonModel = this.radiogroup.getSelection();
//	    	String actionCommand = buttonModel.getActionCommand();
//            System.out.println("Selected Button: " + actionCommand);
//            ////////////////////////////////////////////////
//	    	System.out.println("You pressed Play Card");
	    
	    } else if (ae.getSource() == this.btnViewStats) {
	    	System.out.println("You pressed View Stats");
	    	
	    } else if (ae.getSource() == this.btnSaveStats) {
	    	System.out.println("You pressed Save Stats");
	   	    }
	  }

}
