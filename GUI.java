import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//for deck:
import java.io.FileReader;
import java.util.Scanner;

public class GUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnNewGame,btnPlayCard,btnViewStats,btnSaveStats;
	private JTextArea areaCurrentStats,areaGameMessages;
	private JComboBox comboBoxPlayers;
	private JLabel lblNumberOfCpu,lblCurrentPlayers,lblBlank;
	private JRadioButton radioButton_1, radioButton_2, radioButton_3, radioButton_4, radioButton_5;
	private JTextField tfAttrib1,tfAttrib2,tfAttrib3,tfAttrib4,tfAttrib5,tfAttrib6;
	private ButtonGroup radiogroup;
	
	//for deck
	private Deck gameDeck;
	private final int deckSize=40;
	
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
		
		this.lblBlank = new JLabel("Name:    ");
		lblBlank.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
		this.radioButton_1 = new JRadioButton("Height");
		this.radioButton_2 = new JRadioButton("Weight");
		this.radioButton_3 = new JRadioButton("Length");
		this.radioButton_4 = new JRadioButton("Ferocity");
		this.radioButton_5 = new JRadioButton("Intelligence");
		
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
		subpanel_show_attributes.add(lblBlank);
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
		
		// TESTING BUTTON ACTIONS
		this.radioButton_1.setActionCommand("Height");
		this.radioButton_2.setActionCommand("Weight");
		this.radioButton_3.setActionCommand("Length");
		this.radioButton_4.setActionCommand("Ferocity");
		this.radioButton_5.setActionCommand("Intelligence");
		/////////////////////////////////////////////////
		
		
		//Sub-panel for the actual data
		JPanel subpanel_categories = new JPanel();
		subpanel_categories.setBackground(new Color(134, 199, 156));
		subpanel_categories.setLayout(new GridLayout(0,1,0,0));
		panel_middle.add(subpanel_categories);
		
		this.tfAttrib1 = new JTextField();
		this.tfAttrib2 = new JTextField();
		this.tfAttrib3 = new JTextField();
		this.tfAttrib4 = new JTextField();
		this.tfAttrib5 = new JTextField();
		this.tfAttrib6 = new JTextField();
		// Makes it blend into background colour
		tfAttrib1.setBorder(null);
		tfAttrib2.setBorder(null);
		tfAttrib3.setBorder(null);
		tfAttrib4.setBorder(null);
		tfAttrib5.setBorder(null);
		tfAttrib6.setBorder(null);
		// Set box colours
		tfAttrib1.setBackground(new Color(134, 199, 156));
		tfAttrib2.setBackground(new Color(134, 199, 156));
		tfAttrib3.setBackground(new Color(134, 199, 156));
		tfAttrib4.setBackground(new Color(134, 199, 156));
		tfAttrib5.setBackground(new Color(134, 199, 156));
		tfAttrib6.setBackground(new Color(134, 199, 156));
		// Add all in order
		subpanel_categories.add(tfAttrib1);
		subpanel_categories.add(tfAttrib2);
		subpanel_categories.add(tfAttrib3);
		subpanel_categories.add(tfAttrib4);
		subpanel_categories.add(tfAttrib5);
		subpanel_categories.add(tfAttrib6);
		
		
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
		panel_bottom.add(btnPlayCard);
		
		// Action listeners on the bottom
		this.btnPlayCard.addActionListener(this);
		
	}
	
	public void initDeck(){	
		int deckSize=40;
		Deck gameDeck=new Deck(); //create a deck
		try{   					  //read file for card info
			Scanner scanner = new Scanner(new FileReader("deck.txt"));
			for (int i=0; i<deckSize+1; i++){
					while (scanner.hasNextLine()){
						if (i==0){}   // first line contains category names
						// might want to work this first line into displaying category names in GUI
						else{String info=scanner.nextLine();
						     gameDeck.addCardToTop(info);}
					}
				}
			scanner.close();
		  }
		catch(Exception e){}
	}
		
	public void updatePlayerlist(){
			//What for again??
			
	}
	
	public void actionPerformed(ActionEvent ae)
	  {
	    if (ae.getSource() == this.btnNewGame) {
	      System.out.println("You pressed New Game");
	      
	    } else if (ae.getSource() == this.btnPlayCard) {
	    	
	    	
	    	// TESTING FOR SELECTIoN OF A BUTTON
	    	ButtonModel buttonModel = this.radiogroup.getSelection();
	    	String actionCommand = buttonModel.getActionCommand();
            System.out.println("Selected Button: " + actionCommand);
            ////////////////////////////////////////////////
	    	System.out.println("You pressed Play Card");
	    
	    } else if (ae.getSource() == this.btnViewStats) {
	    	System.out.println("You pressed View Stats");
	    	
	    } else if (ae.getSource() == this.btnSaveStats) {
	    	System.out.println("You pressed Save Stats");
	   	    }
	  }

}
