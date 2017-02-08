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
	private String username = "m_16_1105443p";
	private String password = "1105443Pp";
	private Connection connection =null;
	public String gamesPlayed;
	private final String gameStatsFile = "gameStatsFile.txt";
	

	public StatsReportFrame ()
	{

	this("m_16_1105443p", "m_16_1105443p", "1105443p");
	}

	public StatsReportFrame (String dbname, String username, String password)
	{

		this.dbname = dbname;
		this.username = username;
		this.password = password;
	}
	
	/*
	 * sets up the database connection
	 */
	
	public void newConnection()
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
	
	}
	
	/*
	 * TODO a method for saving stats to database
	 */
	
	
	
	
	
	
	
	/*
	 * instantiates
	 * and runs the queries 
	 * to get the game stats 
	 */

			public void getStats () throws IOException 
			{
						
			Statement stmt1 = null;
			String query1 = "SELECT game_number FROM TopTrumpsData;";
			Statement stmt2 = null; 
			String query2 = "SELECT player_zero_rounds FROM TopTrumpsData;";
			Statement stmt3 = null;
			String query3 = "SELECT player_one_rounds FROM TopTrumpsData;";
			Statement stmt4 = null;
			String query4 = "SELECT draws FROM TopTrumpsData;";
			Statement stmt5 = null;
			String query5 = "SELECT rounds_played FROM TopTrumpsData;";
	
			try 
			{
				stmt1 = connection.createStatement();
				ResultSet rs1 = stmt1.executeQuery(query1);
				stmt2 = connection.createStatement();
				ResultSet rs2 = stmt2.executeQuery(query2);
				stmt3 = connection.createStatement();
				ResultSet rs3 = stmt3.executeQuery(query3);
				stmt4 = connection.createStatement();
				ResultSet rs4 = stmt4.executeQuery(query4);
				stmt5 = connection.createStatement();
				ResultSet rs5 = stmt5.executeQuery(query5);
				
				
			while (rs1.next())
			{

				String gamesPlayed = rs1.getString("game_number"); 
				System.out.println("Number of games played:" + " " + gamesPlayed);
				
			}
			while (rs2.next())
			{
				String playerZero = rs2.getString("player_zero_rounds");
				System.out.println("Player Zero rounds:" + " " + playerZero);
			}
			while (rs3.next())
			{
				String playerOne = rs3.getString("player_one_rounds");
				System.out.println("Player one rounds:" + " " + playerOne);
				
			}
			while (rs4.next())
			{
				String draws = rs4.getString("draws");
				System.out.println("Draws:" + " " + draws);
			
			}
			
			while (rs5.next())
			{
				String roundsPlayed = rs5.getString("rounds_played");
				System.out.println("Rounds played:" + " " + roundsPlayed);
			}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				System.out.print("SQL statement not found");
			}
			
		writeStatsFile();
	
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
		 * TODO set up the report frame 
		 */
		
	
        public int viewStatsFrame(){
               
		
		JFrame statsFrame = new JFrame();
        	statsFrame.setVisible(true);
        	statsFrame.setBounds(150, 150, 600, 500);
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
