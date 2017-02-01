import java.awt.event.ActionEvent;
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
	
	//set up connection
	
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
	
	//begin queries to get stats 

			public void getStats ()
			{
						
			Statement stmt1 = null;
			String query1 = "SELECT game_number FROM TopTrumpsData;";
	
			try 
			{
				stmt1 = connection.createStatement();
				ResultSet rs1 = stmt1.executeQuery(query1);
	
			while (rs1.next())
			{

				String gamesPlayed = rs1.getString("game_number"); //realise this should be an int, can parse later 
				System.out.println(gamesPlayed);

			}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				System.out.print("SQL statement not found");
			}
	
	}
					public void actionPerformed (ActionEvent ae) {
	
						if (ae.getSource() == GUI.btnViewStats) //???????? Y U DO DIS
							newConnection();
							getStats();
					}

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

        
        public int viewStats(){
               
                int stats = 0;
  
                return stats; // TEST - CHANGE AS APPROPRIATE
        }
