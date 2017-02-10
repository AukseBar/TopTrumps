import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;

import javax.swing.*;

/* Accesses SQL database and returns stat values - depending on Jbuttons pressed in GUI.
saveStatsToDatabase()
getStatsFromDatabase()
viewStats()
 *
 */

public class StatsReportFrame extends JFrame implements ActionListener {

	// Database connection
	private Connection connection = null;
	private String dbpath = "postgresql://yacata.dcs.gla.ac.uk:5432/";
	private String dbname = "m_16_1105443p";
	private String tableName = "TopTrumpsData";
	private String username = "1105443p";
	private String password = "1105443p";
	
	// DB Columns
	private int gamesPlayed;
	private int computerWins;
	private int humanWins;
	private double avgDraws;
	private int mostRounds;
	
	// Output file
	private final String gameStatsFile = "gameStatsFile.txt";
	
	// Game instance
	private Game game;

	
	public StatsReportFrame (Game game, boolean saveStats) throws SQLException
	{
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
		JButton saveToFile = new JButton("Save to File");
		saveToFile.addActionListener(this);
		add(saveToFile, BorderLayout.SOUTH);
		
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
		String sql = "SELECT * FROM TopTrumpsData;";
		
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
	
	/*
	 * TODO a method for saving stats to database
	 */
	public void saveStats ()
	{

		//game.getDraws();
		//game.getCurrentPlayer();
		//game.getTotalRounds();
		//game.getHumanPlayer();

		//game.getPlayer(i);

		//int statsDraws = game.getDraws();

		/*
		Statement stmt1 = null;
		String queryD = "INSERT INTO TopTrumpsData (draws)" + "VALUES (3);";
		Statement stmt2 = null;
		String queryWin = "INSERT INTO TopTrumpsData (winning_player) VALUES (1);";
		Statement stmt3 = null;
		String queryRounds = "INSERT INTO TopTrumpsData (rounds_played) VALUES (20);";
		Statement stmt4 = null;
		String queryZeroRounds = "INSERT INTO TopTrumpsData (player_zero_rounds) VALUES (5);";
		Statement stmt5 = null;
		String queryOneRounds = "INSERT INTO TopTrumpsData (player_one_rounds) VALUES (6);";
		Statement stmt6 = null;
		String queryTwoRounds = "INSERT INTO TopTrumpsData (player_two_rounds) VALUES (9);";
		Statement stmt7 = null;
		String queryThreeRounds = "INSERT INTO TopTrumpsData (player_three_rounds) VALUES (0);";
		Statement stmt8 = null;
		String queryFourRounds = "INSERT INTO TopTrumpsData (player_four_rounds) VALUES (0);";*/		

		/*stmt1 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rsSave1 = stmt1.executeQuery(queryD);
		stmt2 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rsSave2 = stmt2.executeQuery(queryWin);
		stmt3 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rsSave3 = stmt3.executeQuery(queryRounds);
		stmt4 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rsSave4 = stmt4.executeQuery(queryZeroRounds);
		stmt5 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rsSave5 = stmt5.executeQuery(queryOneRounds);*/

		
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
				String sql = "SELECT MAX(game_number) FROM TopTrumpsData;";
				ResultSet resultSet = stmt.executeQuery(sql);
				resultSet.next();			// Get the row returned by the MAX query
				nextGameNumber = resultSet.getInt("max") + 1; // Get latest game number in database as it will be the 'max' and add one for the current game to be save
			}
			
			String sql2 = "INSERT INTO TopTrumpsData(game_number) VALUES(" + nextGameNumber + ")";
			stmt.executeUpdate(sql2);

			// For next columns' data you will need to add it to the row using UPDATE as INSERT is just to create row

			String updateData = "UPDATE TopTrumpsData SET draws=1 WHERE game_number= " + nextGameNumber + ";";
			stmt.executeUpdate(updateData);
		}	
		catch (SQLException e1) {
			e1.printStackTrace ();
			System.out.print("SQL statement not found");
		}
		finally {
			closeConnection();
		}
	}
	
	
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
	 * Loads the instance variables for display with results of database queries
	 */
	public void getStats()
	{
		newConnection();
		Statement stmt = null;

		String gamesPlayedSQL = "SELECT MAX(game_number) FROM TopTrumpsData;";
		String computerWinsSQL = "SELECT COUNT(winning_player) FROM TopTrumpsData WHERE winning_player>0;";
		String humanWinsSQL = "SELECT COUNT(winning_player) FROM TopTrumpsData WHERE winning_player=0;";
		String avgDrawsSQL = "SELECT AVG(draws) FROM TopTrumpsData;";
		String maxRoundsSQL = "SELECT MAX(rounds_played) FROM TopTrumpsData;";
		
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

	/*
	 * writes the game stats to a file 
	 */
	public void writeStatsFile ()
	{

		try {
			File gameStatsFile = new File ("gameStatsFile.txt");
			gameStatsFile.createNewFile();
			FileWriter writeStats = new FileWriter(gameStatsFile);
			writeStats.write("testing file writing");
			writeStats.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}



}
