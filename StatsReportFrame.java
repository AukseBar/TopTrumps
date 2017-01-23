import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;

/* Accesses SQL database and returns stat values - depending on Jbuttons pressed in GUI.
saveStatsToDatabase()
getStatsFromDatabase()
viewStats()

 * 
 */

public class StatsReportFrame extends JFrame{
	
	
	public void saveStatsToDatabase(){
		
		public void actionPerformed(ActionEvent event) {
			
			if (event.getSource() == btnSaveStats)
			{
			
			{
				
				String dbname = "postgres";
				String username = "postgres";
				String password = "";			/*input appropriate password */
				Connection connection =null;
				
				try
					{
					connection =
					DriverManager.getConnection("jdbc:postgresql://localhost:5433/" +
					dbname,username, password);
					}
				
					catch (SQLException e1)
					{
					System.err.println("Connection Failed!");
					e1.printStackTrace();
					return;
					}
				
				Statement stmt = null;
			}}
		
	}
	
	public int getStatsFromDatabase(){
		
		int stats =  0;
		return stats;   // TEST - CHANGE AS APPROPRIATE
		
	}
	
	
	public int viewStats(){
		
		int stats = 0;
		return stats; // TEST - CHANGE AS APPROPRIATE
	}
	}

