import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import javax.swing.JFrame;

/* Accesses SQL database and returns stat values - depending on Jbuttons pressed in GUI.
saveStatsToDatabase()
getStatsFromDatabase()
viewStats()
 *
 */

public class StatsReportFrame extends JFrame{
       
	private String dbname = "m_16_1105443p";
	private String tableName = "TopTrumpsData";
	private String username = "m_16_1105443p";
	private String password = "1105443p";
	private Connection connection =null;
	public String gamesPlayed;
	private final String gameStatsFile = "gameStatsFile.txt";
	Game game;
	private final int numOfPlayers;
	

	public StatsReportFrame (int numOfPlayers, Game game)
	{
		this.numOfPlayers = numOfPlayers;
		this.game = game; 
	}
	
	/*
	 * sets up the database connection
	 */
	
	public void newConnection() throws IOException
	{

		
		
		try
		{
		connection = DriverManager.getConnection ("jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/" + dbname, username, password);
		} 
	
		catch(SQLException e) 
		{
			System.err.println("Connection Failed!");
			e.printStackTrace();
			return;
		}
	
		if (connection != null) 
		{
			System.out.println("Connection successful");
		} 
		else
		{
			System.err.println("Failed to make connection!");
		}
		
		saveStats();
	
	}
	
	/*
	 * TODO a method for saving stats to database
	 */
	
	
	public void saveStats () throws IOException
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
		
		try {
		

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
		
	
		int nextGameNumber = 0;
		Statement stmt = null;
		String sql = "SELECT MAX(game_number) FROM TopTrumpsData;";
		
		try {
		
			stmt = connection.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);

			if(!resultSet.isBeforeFirst()) {
				nextGameNumber = 1;			// Empty database so this will be the first row and game
			}
			else {
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
	
	

	/*
	 * instantiates
	 * and runs the queries 
	 * to get the game stats 
	 */
		}
	
			public void getStats () throws IOException 
			{
						
			Statement stmtGames = null;
			String query1 = "SELECT game_number FROM TopTrumpsData;"; //must also add total games 
			Statement stmtZero = null; 
			String query2 = "SELECT player_zero_rounds FROM TopTrumpsData;";
			Statement stmtOne = null;
			String query3 = "SELECT player_one_rounds FROM TopTrumpsData;"; 
			Statement stmtDraws = null;
			String query4 = "SELECT AVG (draws), draws FROM TopTrumpsData GROUP BY draws;"; //must calculate average draws 
			Statement stmtRounds = null;
			String query5 = "SELECT rounds_played FROM TopTrumpsData;"; //must find max amount of rounds
	
			try 
			{
				stmtGames = connection.createStatement();
				ResultSet rs1 = stmtGames.executeQuery(query1);
				stmtZero = connection.createStatement();
				ResultSet rs2 = stmtZero.executeQuery(query2);
				stmtOne = connection.createStatement();
				ResultSet rs3 = stmtOne.executeQuery(query3);
				stmtDraws = connection.createStatement();
				ResultSet rs4 = stmtDraws.executeQuery(query4);
				stmtRounds = connection.createStatement();
				ResultSet rs5 = stmtRounds.executeQuery(query5);
				
				
			while (rs1.next())
			{

				String gamesPlayed = rs1.getString("game_number"); 
				System.out.println("Number of games played:" + " " + gamesPlayed);
				
			}
			while (rs2.next())
			{
				int playerZero = rs2.getInt("player_zero_rounds");
				System.out.println("Player Zero rounds:" + " " + playerZero);
			}
			while (rs3.next())
			{
				int playerOne = rs3.getInt("player_one_rounds");
				System.out.println("Player one rounds:" + " " + playerOne);
				
			}
			while (rs4.next())
			{
				int draws = rs4.getInt("draws");
				System.out.println("Draws:" + " " + draws);
			
			}
			
			while (rs5.next())
			{
				int roundsPlayed = rs5.getInt("rounds_played");
				System.out.println("Rounds played:" + " " + roundsPlayed);
			}
			}
			catch (SQLException e2)
			{
				e2.printStackTrace();
				System.out.print("Get SQL statement not found");
			}
			
		writeStatsFile();
		closeConnection();
		
	}
			
		/**
		 * closes the database connection
		 */

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

		/*
		 * TODO finish the report frame - add data 
		 */
		
	
        public int viewStatsFrame(){
               
        	JFrame statsFrame = new JFrame();
        	statsFrame.setVisible(true);
        	statsFrame.setBounds(150, 150, 600, 500);
        	statsFrame.setSize(500, 400);
        	statsFrame.setTitle("Game Statistics");        	
        	
                int stats = 0;
  
                return stats; // TEST - CHANGE AS APPROPRIATE
        }
        
        /*
         * writes the game stats to a file 
         */
        
        
        public void writeStatsFile () throws IOException
        {
        	
        	File gameStatsFile = new File ("gameStatsFile.txt");
        	gameStatsFile.createNewFile();
        	FileWriter writeStats = new FileWriter(gameStatsFile);
        	writeStats.write("testing file writing");
        	writeStats.close();
        }
        
        
        
        
}
