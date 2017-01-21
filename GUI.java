import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

import java.awt.GridLayout;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;
import java.awt.FlowLayout;
// @ TODO use * to shorten imports

public class GUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnNewGame,btnPlayCard,btnViewStats,btnSaveStats;
	private JTextArea areaCurrentStats,areaGameMessages,areaAttributes;
	private JComboBox comboBoxPlayers;
	private JLabel lblNumberOfCpu,lblCurrentPlayers,lblName,lblHeight,lblWeight,lblLength,lblFerocity,lblIntelligence,lblBlank;
	private JPanel panel_choice;
	private JRadioButton radioButton_0;
	private JRadioButton radioButton_1;
	private JRadioButton radioButton_2;
	private JRadioButton radioButton_3;
	private JRadioButton radioButton_4;
	//private JLabel label;
	private ButtonGroup group;


	// @ TODO Add in all field declarations ie private...thingy and this.thingy
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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
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
		panel_top.setBackground(new Color(102, 153, 153));
		contentPane.add(panel_top, BorderLayout.NORTH);
		
		this.btnNewGame = new JButton("New Game");
		btnNewGame.setBackground(new Color(153, 204, 204));
		panel_top.add(btnNewGame);
		
		this.lblNumberOfCpu = new JLabel("CPU Players:");
		panel_top.add(lblNumberOfCpu);
		
		this.comboBoxPlayers = new JComboBox();
		//TEST DATA FOR CPU PLAYER NUMBERS NEEDS ALTERED////
		this.comboBoxPlayers.addItem(new String("1"));
		this.comboBoxPlayers.addItem(new String("2"));
		this.comboBoxPlayers.addItem(new String("3"));
		this.comboBoxPlayers.addItem(new String("4"));
		///////////////////////////////
		panel_top.add(comboBoxPlayers);
		
		this.btnViewStats = new JButton("View Stats");
		btnViewStats.setBackground(new Color(153, 204, 204));
		panel_top.add(btnViewStats);
		
		this.btnNewGame.addActionListener(this);
		this.btnViewStats.addActionListener(this);
		}
	
	public void layoutMiddle(){
		// Middle 'views' panel
		JPanel panel_middle = new JPanel();
		contentPane.add(panel_middle, BorderLayout.CENTER);
		panel_middle.setLayout(new GridLayout());
		
		// Left side sub-panel for current game statistics
		JPanel subpanel_current_stats = new JPanel();
		subpanel_current_stats.setBackground(new Color(102, 153, 153));
		panel_middle.add(subpanel_current_stats);
		subpanel_current_stats.setLayout(new BoxLayout(subpanel_current_stats, BoxLayout.Y_AXIS));
		
		this.lblCurrentPlayers = new JLabel("      Current Game Statistics:");
		lblCurrentPlayers.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentPlayers.setBackground(new Color(102, 153, 153));
		lblCurrentPlayers.setFont(new Font("Tahoma", Font.PLAIN, 14));
		subpanel_current_stats.add(lblCurrentPlayers);
		
		this.areaCurrentStats = new JTextArea();
		areaCurrentStats.setBackground(new Color(153, 204, 204));
		areaCurrentStats.setEditable(false); // Toggle to allow editing
		areaCurrentStats.setRows(8);
		areaCurrentStats.setColumns(8);
		subpanel_current_stats.add(areaCurrentStats);
		
		// Middle sub-panel for the fixed categories
		JPanel subpanel_categories = new JPanel();
		subpanel_categories.setBackground(new Color(102, 153, 153));
		panel_middle.add(subpanel_categories);
		GridLayout gl_subpanel_categories = new GridLayout();
		gl_subpanel_categories.setColumns(1);
		gl_subpanel_categories.setRows(0);
		subpanel_categories.setLayout(gl_subpanel_categories);
		
		this.lblName = new JLabel("Name:");
		lblName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		subpanel_categories.add(lblName);
		
		this.lblHeight = new JLabel("Height:");
		lblHeight.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHeight.setHorizontalAlignment(SwingConstants.TRAILING);
		subpanel_categories.add(lblHeight);
		
		this.lblWeight = new JLabel("Weight:");
		lblWeight.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblWeight.setHorizontalAlignment(SwingConstants.TRAILING);
		subpanel_categories.add(lblWeight);
		
		this.lblLength = new JLabel("Length:");
		lblLength.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLength.setHorizontalAlignment(SwingConstants.TRAILING);
		subpanel_categories.add(lblLength);
		
		this.lblFerocity = new JLabel("Ferocity:");
		lblFerocity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFerocity.setHorizontalAlignment(SwingConstants.TRAILING);
		subpanel_categories.add(lblFerocity);
		
		this.lblIntelligence = new JLabel("Intelligence:");
		lblIntelligence.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIntelligence.setHorizontalAlignment(SwingConstants.TRAILING);
		subpanel_categories.add(lblIntelligence);
		
		// Right sub-panel for showing the attributes read from a card
		JPanel subpanel_show_attributes = new JPanel();
		subpanel_show_attributes.setBackground(new Color(153, 204, 204));
		panel_middle.add(subpanel_show_attributes);
		subpanel_show_attributes.setLayout(new BoxLayout(subpanel_show_attributes, BoxLayout.X_AXIS));
		
		this.areaAttributes = new JTextArea();
		areaAttributes.setBackground(new Color(153, 204, 204));
		areaAttributes.setEditable(false); // Toggle to allow editing
		areaAttributes.setRows(17);
		areaAttributes.setColumns(15);
		subpanel_show_attributes.add(areaAttributes);
		
		
		// PoPULATE CARD AREA WITH TEST STRING
		String testing = "\r\n TRex \r\n\r\n\r\n\r\n 6 \r\n\r\n\r\n 6 \r\n\r\n\r\n 12 \r\n\r\n\r\n\r\n 9 \r\n\r\n\r\n\r\n 9";
		areaAttributes.setText(testing);
		////////////////////////////////////////////
		
		// NEEDS REFORMATED WITH THISES AND PRIVATES
		panel_choice = new JPanel();
		panel_choice.setBackground(new Color(153, 204, 204));
		panel_choice.setForeground(new Color(153, 204, 204));
		subpanel_show_attributes.add(panel_choice);
		panel_choice.setLayout(new GridLayout(0, 1, 0, 0));
		
		this.lblBlank = new JLabel("");
		lblBlank.setForeground(new Color(153, 204, 204));
		panel_choice.add(lblBlank);
		
		this.radioButton_0 = new JRadioButton("");
		radioButton_0.setBackground(new Color(153, 204, 204));
		panel_choice.add(radioButton_0);
		
		this.radioButton_1 = new JRadioButton("");
		radioButton_1.setBackground(new Color(153, 204, 204));
		panel_choice.add(radioButton_1);
		
		this.radioButton_2 = new JRadioButton("");
		radioButton_2.setBackground(new Color(153, 204, 204));
		panel_choice.add(radioButton_2);
		
		this.radioButton_3 = new JRadioButton("");
		radioButton_3.setBackground(new Color(153, 204, 204));
		panel_choice.add(radioButton_3);
		
		this.radioButton_4 = new JRadioButton("");
		radioButton_4.setBackground(new Color(153, 204, 204));
		panel_choice.add(radioButton_4);
		
		this.group = new ButtonGroup();
		group.add(radioButton_0);
		group.add(radioButton_1);
		group.add(radioButton_2);
		group.add(radioButton_3);
		group.add(radioButton_4);
		
		// TESTING BUTTON ACTIONS
		this.radioButton_0.setActionCommand("Height");
		this.radioButton_1.setActionCommand("Weight");
		this.radioButton_2.setActionCommand("Length");
		this.radioButton_3.setActionCommand("Ferocity");
		this.radioButton_4.setActionCommand("Intelligence");
		/////////////////////////////////////////////////
		}
	
	public void layoutBottom(){
		
		// Bottom panel for in-game messages and save game button
		JPanel panel_bottom = new JPanel();
		panel_bottom.setBackground(new Color(102, 153, 153));
		contentPane.add(panel_bottom, BorderLayout.SOUTH);
		panel_bottom.setLayout(new BoxLayout(panel_bottom, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_bottom.add(scrollPane);
		
		this.areaGameMessages = new JTextArea();
		areaGameMessages.setBackground(new Color(153, 204, 204));
		areaGameMessages.setEditable(false); // Toggle to allow editing
		areaGameMessages.setRows(5);
		areaGameMessages.setColumns(30);
		scrollPane.setViewportView(areaGameMessages);
		
		this.btnPlayCard = new JButton("Play Card!");
		btnPlayCard.setBackground(new Color(153, 204, 204));
		panel_bottom.add(btnPlayCard);
		
		this.btnSaveStats = new JButton("Save Stats");
		btnSaveStats.setBackground(new Color(153, 204, 204));
		panel_bottom.add(btnSaveStats);
		
		
		// Action listeners on the bottom
		this.btnPlayCard.addActionListener(this);
		this.btnSaveStats.addActionListener(this);
		
	}
	
	public void initDeck(){
			// This is for sorting the deck on app launch.. will be near the start of methods called.
			
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
	    	ButtonModel buttonModel = this.group.getSelection();
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
