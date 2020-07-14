import java.sql.*; 


public class connect {    
	public static void main(String args[]) {       
		String username = "tlap632"; //"your UPI";       
		String password = "tlap632"; //"your password";       
		String url = "jdbc:mysql://127.0.0.1:3306/stu_tlap632_COMPSCI_351_C_S1_2020"; //e.g. "jdbc:mysql://127.0.0.1:3306/stu_UPI_COMPSCI_351_C_S1_2020"

		   
	//Loads the JDBC driver  
		try {   
			Class.forName("com.mysql.jdbc.Driver");   
			System.out.println("Driver loaded");       
		 //Establishes a connection          
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Database connected");          
		 //Creates a statement          
			Statement stmt= conn.createStatement(); 
		 //Executes a statement          
			String command = "SELECT * " + "FROM DEPARTMENT";         
		 //Obtains the results as a set of rows    
			System.out.println(command);      
			ResultSet result = stmt.executeQuery(command); // executes single SQL statement     

		  //Obtains the metadata associated with the results 
			
		  	//  Retrieves the  number, types and properties of
     		// this <code>ResultSet</code> object's columns.
			ResultSetMetaData  metaData = result.getMetaData(); 
			//Obtains the number of columns         
			int columnCount = metaData.getColumnCount(); 
			System.out.println(columnCount);       
  			//Prints the names of the columns obtained from the metadata        
			for (int i=1; i<=columnCount; i++) { 
				if (i > 1) System.out.print('\t');   
				System.out.print(metaData.getColumnLabel(i));          
			}          
			System.out.println();       
			System.out.println("-----------------------------------------------------");     
            
			// Iterates through the results and prints the tuples (rows)      	   
			while (result.next()) {   //  .next() means accessing rows  
				for (int i=1; i<= columnCount; i++) {  // i means column
					if (i>1) System.out.print('\t');              
					System.out.print(result.getString(i)); // print value              
				}             
				System.out.println();        
			}        
                                                         //closes the connection  (optional)      
			conn.close();      
		}    
		catch (Exception e) {   
			e.printStackTrace();
		}  
	}     
}              

/* DON'T TOUCH! LAB 9

	retrieve project numbers and put them in a list
				while (result.next()) { // .next() is accessing the rows in PROJECT
					ArrayList<Integer> Pnumber = new ArrayList<Integer>(); // list of project numbers --> [1, 2, 3, 10, 20, 30, ..., 92]
					int p = result.getString("Pnumber"); // retrieve project number by row
					Pnumber.add(Integer.valueOf(p)); // convert p from int to Int
				}

				for (Integer i : Pnumber) { // accessing project numbers

				}



				
				ResultSet workedOnProjects = stmt.executeQuery("SELECT * FROM WORKS_ON");
				// ITERATE THROUGH TABLE AND ADD PROJECT NUMBERS INTO A SET
				while (workedOnProjects.next()) { // iterating through the rows in WORKS_ON
					int p = workedOnProjects.getInt("Pno"); // retrieve project number by row
					Pno.add(Integer.valueOf(p)); // add if not in the set
				}

				// add elements from set into arraylist
				Iterator<Integer> m = Pno.iterator();
				while (m.hasNext()) {
					PnoList.add(m.next());
				}

				// put project number from PROJECT into a list
				while (result.next()) { 
					int num = result.getInt("Pnumber");
					Pnumber.add(Integer.valueOf(num));
				}
				
				
				Collections.sort(PnoList);
				Collections.sort(Pnumber); // project numbers in arraylist are sorted in the order according to Pnumber in PROJECT
				// use FOR EACH loop to iterate through project number and ADD the total hours in the project
				
				
				
				// PROJECT 4 IS NOT WORKED ON
				for (Integer a : Pnumber) { // Pnumber = 1, 2, 3, 4, 10, 20, 30, 61, 62, 63, 91, 92
					float hoursWorked = 0.0;
					for (Integer b : PnoList) { // PnoList = 1, 2, 43, 10, 20, 30, 61, 62, 63, 91, 92
						if (a.equals(b)) { // Pnumber also in Pno

						}
					}
				}

				//Set<Integer> Pno = new Set<Integer>(); // to store unique project numbers
			//ArrayList<Integer> PnoList = new ArrayList<Integer>();
			//ArrayList<Integer> Pnumber = new ArrayList<Integer>(); // convert the set of project numbers into an arraylist --> [1, 2, 3, 10, 20, 30, ..., 92]

			
				// get total hours for each project
				ResultSet workedOnProjects = stmt.executeQuery("SELECT Pnumber, Hours FROM PROJECT INNER JOIN WORKS_ON ON Pnumber=Pno GROUP BY Pnumber");
				while (workedOnProjects.next()) { // going through rows from the query above
					// first row would be 1, 52.5
					//stmt.executeUpdate("UPDATE PROJECT SET Hours = 0.0 WHERE");
					//stmt.executeUpdate("UPDATE PROJECT SET Hours = %f WHERE Pnumber=%d", workedOnProjects.getFloat(2), workedOnProjects.getInt(Pnumber));
					float hoursWorked = 0.0; // so it resets everytime
					hoursWorked = workedOnProjects.getFloat(2);
					stmt.executeUpdate("UPDATE PROJECT SET Hours = " + hoursWorked + " WHERE Pnumber=" + workedOnProjects.getInt(1));

				-----------------
				while (workedOnProjects.next()) { // going through rows from the query above
				// first row would be 1, 52.5
				//float hoursWorked = 0.0; // so it resets everytime
				//hoursWorked = workedOnProjects.getFloat(2);
				stmt.executeUpdate("UPDATE PROJECT SET Hours = (SELECT SUM(Hours) FROM PROJECT INNER JOIN WORKS_ON ON Pnumber=Pno) WHERE Pnumber=" + workedOnProjects.getInt(1)); // 1 is Pnumber, 2 is SUM(HOURS)
				// UPDATE PROJECT SET Hours = (SELECT SUM(Hours) FROM PROJECT INNER JOIN WORKS_ON ON Pnumber=Pno) WHERE Pnumber
			}
			}

				//  PRINTING THE WHOLE PROJECT TABLE OUT  //
				for (int i=1; i<=columnCount; i++) { // print column names
					if (i > 1) System.out.print('\t');   
					System.out.print(metaData.getColumnLabel(i));          
				}          
				System.out.println();       
				System.out.println("-----------------------------------------------------");     
				
				// Iterates through the results and prints the tuples (rows)      	   
				// result = tuples from PROJECT table
				while (result.next()) {   //  .next() means accessing rows  
					for (int i=1; i<= columnCount; i++) {  // i means column
						if (i>1) System.out.print('\t');              
						System.out.print(result.getString(i)); // print value for a particular tuple and attribute            
					}             
					System.out.println();        
				}
*/


