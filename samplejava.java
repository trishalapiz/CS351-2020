import java.sql.*;

public class samplejava {
	public static void main(String args[]) {
		String username = "tlap632"; // "your UPI";
		String password = "tlap632"; // "your password";
		String url = "jdbc:mysql://127.0.0.1:3306/stu_tlap632_COMPSCI_351_C_S1_2020_A2_Q1"; // your database

		// Loads the JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded");
			// Establishes a connection
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Database connected");
			// Creates a statement
			Statement stmt = conn.createStatement();

			/************************Please adapt the following code to complete Lab 9**********************/ 
			// Executes a statement
			String command = "SELECT * " + "FROM PROJECT";
			System.out.println(command);
			final ResultSet result = stmt.executeQuery(command);
			ResultSetMetaData metaData = result.getMetaData();
			int columnCount = metaData.getColumnCount();
			
			/* QUESTION 1 */
			if (columnCount < 5) { // no 'Hours' attribute
				stmt.execute("ALTER TABLE PROJECT ADD COLUMN Hours FLOAT");
				Statement stmt2a = conn.createStatement();
				stmt.execute("UPDATE PROJECT SET Hours = 0.0");
				/* QUESTION 2 */
				final ResultSet workedOnProjects = stmt2a.executeQuery("SELECT p.Pnumber, SUM(w.Hours) FROM PROJECT p INNER JOIN WORKS_ON w ON p.Pnumber=w.Pno GROUP BY p.Pnumber");
				while (workedOnProjects.next()) { // first row of above query is: 1   52.5
					stmt.executeUpdate(String.format("UPDATE PROJECT SET Hours = (SELECT SUM(Hours) FROM WORKS_ON WHERE Pnumber=Pno AND Pnumber=%d) WHERE Pnumber=%d", workedOnProjects.getInt(1),  workedOnProjects.getInt(1)));
				}
			} else {
				stmt.execute("UPDATE PROJECT SET Hours = 0.0");
				Statement stmt2b = conn.createStatement();
				/* QUESTION 2 */
				final ResultSet workedOnProjects = stmt2b.executeQuery("SELECT p.Pnumber, SUM(w.Hours) FROM PROJECT p INNER JOIN WORKS_ON w ON p.Pnumber=w.Pno GROUP BY p.Pnumber");
				while (workedOnProjects.next()) { // first row of above query is: 1   52.5
					stmt.executeUpdate(String.format("UPDATE PROJECT SET Hours = (SELECT SUM(Hours) FROM WORKS_ON WHERE Pnumber=Pno AND Pnumber=%d) WHERE Pnumber=%d", workedOnProjects.getInt(1),  workedOnProjects.getInt(1)));
				}
			}
			
			// PRINTING OUT THE COLUMNS OF THE PROJECT TABLE
			for (int i=1; i<=columnCount; i++) {
				if (i>1) {
					System.out.print('\t');
				} System.out.print(metaData.getColumnLabel(i));
			}
			System.out.println();
			System.out.println("-----------------------------------------------------");  
			
			Statement stmt3 = conn.createStatement();
			final ResultSet p = stmt3.executeQuery("SELECT * FROM PROJECT");
			// ITERATES THROUGH THE RESULTS AND PRINTS THE TUPLES (rows)
			while (p.next()) {	// iterating through the rows of PROJECT          
				for (int i=1; i<= columnCount; i++) {       
					if (i>1) System.out.print('\t');              
					System.out.print(p.getString(i)); // print out value of a particular column/row              
				}             
				System.out.println();        
			} 
			
			System.out.println("-----------------------------------------------------"); 
			
			Statement stmt4 = conn.createStatement();
			
			final ResultSet projectRows = stmt4.executeQuery("SELECT * FROM PROJECT");
			
			/* QUESTION 3 */
			while (projectRows.next()) {
				float hours = projectRows.getFloat("Hours");
				if (hours < 100) {
					System.out.println(projectRows.getString("Pname") + " Short");
				} else {
					System.out.println(projectRows.getString("Pname") + " Long");
				}
			}
			/************************PLease adapt the above code to complete Lab 9**********************/
			// closes the connection (optional)
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}