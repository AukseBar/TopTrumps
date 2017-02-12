/**
 * Accesses SQL database to save and return stat values from previous games then display them in a frame
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;

public class StatsReportFrame extends JFrame implements ActionListener {

	private Connection connection = null;
	private String dbpath = "postgresql://yacata.dcs.gla.ac.uk:5432/";
	private String dbname = "m_16_2036032b";
	private String tableName = "TopTrumpsData";
	private String username = "m_16_2036032b";
	private String password = "2036032b";
	private JButton saveToFile;

	// DB Columns
	private int gamesPlayed;
	private int computerWins;
	private int humanWins;
	private double avgDraws;
	private int mostRounds;
	private final int maxPlayers=5; 	// cannot have more than 5

	// Game instance
	private Game game;


	public StatsReportFrame (Game game, boolean saveStats) throws SQLException {
		this.game = game;

		if(!newConnection()) throw new SQLException("Connection failed!");
		closeConnection();

		if(saveStats) saveStats();

		setTitle("Game Statistics");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);		// Don't want to exit program
		setLocationRelativeTo(null);					// Centre

		JTextArea display = new JTextArea();
		display.setFont(new Font("Courier", Font.PLAIN, 14));
		display.setEditable(false);
		display.setText(buildReport());
		add(display, BorderLayout.CENTER);
		if(checkForData()) {
			this.saveToFile = new JButton("Save to File");
			saveToFile.addActionListener(this);
			add(saveToFile, BorderLayout.SOUTH);
		}

		pack();
		setVisible(true);
	}

	/*
	 * Sets up the database connection */
	public boolean newConnection()
	{
		try
		{
			connection = DriverManager.getConnection("jdbc:" + dbpath + dbname, username, password);
			if (connection != null) 
			{
				System.out.println("Connection successful");
				return true;
			} 
			else
			{
				System.err.println("Failed to make connection!");
				return false;
			}
		}
		catch(SQLException e) 
		{
			System.err.println("Connection Failed!");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Closes the database connection */
	public void closeConnection ()
	{
		try 
		{
			connection.close();
			System.out.println("Connection closed");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Connection could not be closed - SQL exception");
		}
	}

	/**
	 * Checks that the database holds at least one record before other queries are performed
	 * @return true if at least one record was found */
	private boolean checkForData() {
		newConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM " + tableName + ";";

		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			if(!rs.next()) {
				return false;		// Database is empty; no first row
			}
		}
		catch(SQLException ex) {
			ex.printStackTrace();
			return false;
		}
		finally {
			closeConnection();
		}
		return true;				// Data found
	}

	/**
	 * Saves the stats from the last played game to the database */
	public void saveStats ()
	{
		int winPlayer=game.getCurrentPlayer().getPlayerNumber();
		int nrRoundsPlayed=game.getTotalRounds();
		int nrOfPlayers= game.getNumOfPlayers();
		int [] player= new int[maxPlayers];

		for (int i=0; i<nrOfPlayers; i++){
			player[i]=game.getPlayer(i).getRoundsWon(); // player[0] is human player
		}
		int draws = game.getDraws();


		int nextGameNumber = -1;
		// If database is empty, set to 1
		if(!checkForData()) {
			nextGameNumber = 1;
		}

		newConnection();
		Statement stmt = null;
		try {
			stmt = connection.createStatement();

			// If database wasn't empty variable will still be initialised at -1
			if(nextGameNumber == -1) {
				String sql = "SELECT MAX(game_number) FROM " + tableName + ";";
				ResultSet resultSet = stmt.executeQuery(sql);
				resultSet.next();			// Get the row returned by the MAX query
				nextGameNumber = resultSet.getInt("max") + 1; // Get latest game number in database as it will be the 'max' and add one for the current game to be save
			}

			String sql2 = "INSERT INTO " + tableName + "(game_number) VALUES(" + nextGameNumber + ")";
			stmt.executeUpdate(sql2);

			// statements to insert new data
			int i=0;
			String updateDraws = "UPDATE " + tableName + " SET draws="+draws+" WHERE game_number= " + nextGameNumber + ";";
			String updateWinPlayer = "UPDATE " + tableName + " SET winning_player="+winPlayer+" WHERE game_number= " + nextGameNumber + ";";
			String roundsPlayed = "UPDATE " + tableName + " SET rounds_played="+nrRoundsPlayed+" WHERE game_number= " + nextGameNumber + ";";			
			String  humanRounds= "UPDATE " + tableName + " SET player_zero_rounds="+player[0]+" WHERE game_number= " + nextGameNumber + ";";
			String  player1String= "UPDATE " + tableName + " SET player_one_rounds="+player[1]+" WHERE game_number= " + nextGameNumber + ";";
			String  player2String= "UPDATE " + tableName + " SET player_two_rounds="+player[2]+" WHERE game_number= " + nextGameNumber + ";";	
			String  player3String= "UPDATE " + tableName + " SET player_three_rounds="+player[3]+" WHERE game_number= " + nextGameNumber + ";";	
			String  player4String= "UPDATE " + tableName + " SET player_four_rounds="+player[4]+" WHERE game_number= " + nextGameNumber + ";";		
			String [] updateDataArray= {updateDraws, updateWinPlayer, roundsPlayed, humanRounds, player1String, player2String, player3String, player4String, };	

			for (i=0; i<3+nrOfPlayers; i++) 
				stmt.executeUpdate(updateDataArray[i]);
		}	
		catch (SQLException e1) {
			e1.printStackTrace ();
			System.out.print("SQL statement not found");
		}
		finally {
			closeConnection();
		}
	}

	/**
	 * Creates and returns a String of the formatted games data
	 * @return a String of formatted game data or information saying there is none if applicable */
	private String buildReport() {
		// Check there is data before trying to access it
		if(!checkForData()) {
			return "There is not currently any saved data in the database";
		}

		// Load variables from database
		getStats();

		// Create and format the text to be returned to the display
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("\n %-18s%d %n", "Games Played:", gamesPlayed));
		sb.append(String.format(" %-18s%d %n", "Computer Wins:", computerWins));
		sb.append(String.format(" %-18s%d %n", "Human Wins:", humanWins));
		sb.append(String.format(" %-18s%.2f %n", "Average Draws:", avgDraws));
		sb.append(String.format(" %-18s%d \n", "Most Rounds:", mostRounds));

		return sb.toString();
	}

	/**
	 * Loads the instance variables for display with results of database queries */
	public void getStats()
	{
		newConnection();
		Statement stmt = null;

		String gamesPlayedSQL = "SELECT MAX(game_number) FROM " + tableName + ";";
		String computerWinsSQL = "SELECT COUNT(winning_player) FROM " + tableName + " WHERE winning_player>0;";
		String humanWinsSQL = "SELECT COUNT(winning_player) FROM " + tableName + " WHERE winning_player=0;";
		String avgDrawsSQL = "SELECT AVG(draws) FROM " + tableName + ";";
		String maxRoundsSQL = "SELECT MAX(rounds_played) FROM " + tableName + ";";

		try {
			stmt = connection.createStatement();

			ResultSet rs1 = stmt.executeQuery(gamesPlayedSQL);
			if(rs1.next()) gamesPlayed = rs1.getInt("max");

			ResultSet rs2 = stmt.executeQuery(computerWinsSQL);
			if(rs2.next()) computerWins = rs2.getInt("count"); 

			ResultSet rs3 = stmt.executeQuery(humanWinsSQL);
			if(rs3.next()) humanWins = rs3.getInt("count"); 

			ResultSet rs4 = stmt.executeQuery(avgDrawsSQL);
			if(rs4.next()) avgDraws = rs4.getDouble("avg");

			ResultSet rs5 = stmt.executeQuery(maxRoundsSQL);
			if(rs5.next()) mostRounds = rs5.getInt("max");
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		finally {
			closeConnection();
		}
	}

	/**
	 * Writes the game stats to a text file */
	public void writeStatsFile ()
	{

		try {
			File gameStatsFile = new File("gameStatsFile.txt");
			gameStatsFile.createNewFile();
			FileWriter writeStats = new FileWriter(gameStatsFile);
			writeStats.write(buildReport());
			writeStats.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.saveToFile)
		{
			writeStatsFile();
		}
	}

}
