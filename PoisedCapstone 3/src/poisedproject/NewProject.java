package poisedproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Date;

/**
 * New project Class, presents user with various project details input options
 * Gets project details from user. Add project details to database.
 * 
 * @author Sire
 */
public class NewProject{
	
	/**
	 * Method gets all project input details from user.
	 * <p>
	 * 
	 * connects to the database.
	 * Present user with menu options for project creation.
	 * Takes in user project details, validates where needed.
	 * Sends prepared  sql statement to database to add al project details to database.
	 * Prints out project details after changes made by user.
	 * 
	 */
	public static void addNewProject() {
		
		try {	
			
			
            	// Connect to the poisepms database, via the jdbc:mysql: channel on localhost (this PC)
            	// Use username "my user name", password "my password".
				Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/poisepms?useSSL=false",
                    "siya",
                    "SiyaSire26@92"
                    );
            
				// Create a direct line to the database for running our queries
				Statement statement = connection.createStatement();
				int rowsAffected;
          
				String projectCompletion = "nonFinalized";
				String completionDate = "none";
				
				// Getting all project details from user
				System.out.println("\nWelcome to Your Poised project management menu to create a new project, read each input request carefully");
			
				System.out.println("\nPlease enter the  projectName or type \"none\" if no project name exists");
				Scanner input = new Scanner(System.in);
				String projectName = input.nextLine();

				System.out.println("\nPlease enter a unique projectNumber ");
				Scanner input1 = new Scanner(System.in);
				int projectNumber = input1.nextInt(); 
				
				// Got more efficient value checking code on https://stackoverflow.com/questions/46061989/jdbc-check-if-entry-exists
				// Checking if project number exists

				PreparedStatement projectNumberStatement = connection.prepareStatement("SELECT COUNT(*) FROM existing_projects "
						+ "WHERE project_number = ?");
				
				// Setting project number to prepared statement to be searched 
				projectNumberStatement.setInt(1,projectNumber);
				
				// Executing query
				// Setting to rsProjectNumber
				ResultSet rsProjectNumber = projectNumberStatement.executeQuery();
				int n = 0;
				
				// moving through rows
				if ( rsProjectNumber.next() ) {
				    n = rsProjectNumber.getInt(1);
				}
				// check if n > 0 then project number exists
				// request unique project number
				if ( n > 0 ) {
					System.out.println("\nProject number already exists, Please enter a unique projectNumber ");
					int projectNumberUpdate = input1.nextInt();
					
					projectNumber = projectNumberUpdate;
				}
				
				System.out.println("\nPlease enter the buildingType");
				String buildingType = input.nextLine();

				System.out.println("\nPlease enter the projectAddress");
				String projectAddress = input.nextLine();

				System.out.println("\nPlease enter the erfNumber");
				int erfNumber = input1.nextInt();

				System.out.println("\nPlease enter the totalFee");
				Scanner input3 = new Scanner(System.in);
				double totalFee = input3.nextDouble();

				System.out.println("\nPlease enter the paidToDate");
				double paidToDate = input3.nextDouble();
				
				System.out.println("\nPlease enter the projectDeadline in the \" Year-Month-Day \" digit form e.g 2022-12-25 ");
				String  projectDeadline = input.next();
				
				String memberTitle = "Client";

				System.out.println("\nPlease enter the name of the Client");
				Scanner clientDetails = new Scanner(System.in);
				String clientName = clientDetails.nextLine();
				
				System.out.println("\nPlease enter the Surname of the Client");
				String clientSurname = clientDetails.nextLine();
				
				System.out.println("\nPlease enter the telephoneNumber of the Client");
				int clientTelephoneNumber = input1.nextInt();

				System.out.println("\nPlease enter the emailAddress of the Client");
				String clientEmailAddress = clientDetails.nextLine();

				System.out.println("\nPlease enter the physicalAddress of the Client");
				String clientPhysicalAddress = clientDetails.nextLine();
	            
	            // String for the prepared statement
	            String sqlClient = "INSERT INTO project_members VALUES (?, ?, ?, ?,?,?, ?) ";
	            
	            // If project name does not exist, assign one
				if(projectName.equalsIgnoreCase("none")) {
				
				projectName = buildingType + " " + clientSurname;
	            	}
				
	            // String for the prepared statement
	            String sqlProject = "INSERT INTO existing_projects VALUES (?, ?, ?, ?,?, ?, ?, ?,?,?) ";
	            // prepared SQL statement to be executed
	            PreparedStatement projectStatement = connection.prepareStatement(sqlProject);
	            
				// Got help on changing date string from user
				// pass date string in JDBC escape format to valueof() to get Date object
				Date projectDeadlineDate = Date.valueOf(projectDeadline);
				
	            // Parameter values set to the prepared statement string "psProject"
	            projectStatement.setInt(1, projectNumber);
	            projectStatement.setString(2,projectName);
	            projectStatement.setString(3,buildingType);
	            projectStatement.setString(4, projectAddress);
	            projectStatement.setInt(5, erfNumber);
	            projectStatement.setDouble(6,totalFee);
	            projectStatement.setDouble(7,paidToDate);
	            projectStatement.setDate(8,projectDeadlineDate);
	            projectStatement.setString(9, projectCompletion);
	            projectStatement.setString(10, completionDate);
	            
	            // Executing the Prepared statement
	            rowsAffected = projectStatement.executeUpdate();
	   
	            System.out.println("\nQuery complete, " + rowsAffected + " rows added.");
	            
	            // prepared SQL statement to be executed
	            PreparedStatement clientStatement = connection.prepareStatement(sqlClient);
	            
	            // Parameter values set to the prepared statement string "psClient"
	            clientStatement.setInt(1,projectNumber);
	            clientStatement.setString(2,projectName);
	            clientStatement.setString(3, memberTitle);
	            clientStatement.setString(4,clientName);
	            clientStatement.setInt(5,clientTelephoneNumber);
	            clientStatement.setString(6, clientEmailAddress);
	            clientStatement.setString(7, clientPhysicalAddress);
	            
	            // Executing the Prepared statement
	            rowsAffected = clientStatement.executeUpdate();
	   
	            System.out.println("\nQuery complete, " + rowsAffected + " rows added.");
				
	            // Calling project member method to add project members
				addProjectMember(connection, projectNumber, projectName);
				
				// printing out project details after project addition
	            System.out.println("\nALL PROJECT DETAILS after Project : " + projectName + " addition");
	            ViewProjects.printAllFromTable(statement);
				            
	            // printing out project member details after project addition
	            System.out.println("\nALL PROJECT MEMBERS after Project : " + projectName + " addition");
	            printAllFromMembersTable(statement);

				}catch(InputMismatchException e) {
			
				System.out.println("Input mismatch error. read input options carefully");
				e.printStackTrace();
				e.getMessage();
			
			}catch(SQLException e) {
				 e.printStackTrace();
			}
	}

	/**
	 * Takes in user input for project member details
	 * Adds details to database.
	 * <p>
	 * 
	 * Present user with menu option to take in project member details.
	 * Pass prepared sql statement of details to database.
	 * Allow user to add more project members 
	 * Until user selects escape menu option from menu.
	 * <p>
	 * @param connection
	 * @param projectNumber
	 * @param projectName
	 * @throws SQLException
	 */
	public static void addProjectMember(Connection connection, int projectNumber, String projectName) throws SQLException {
		
		int rowsAffected;
		boolean login = true;
		
		try {
			
		// Using while loop to get all project member details
		while(login){
			
			System.out.println("\nYou are about to enter other the project member details, Type \"1\" to continue or type \"0\" when finished");
			Scanner input = new Scanner(System.in);
			int guard = input.nextInt();
			 
			if(guard == 1) {
				System.out.println("\nPlease enter the title of the project member, eg   Architect, Contractor, Project manager or Engineer ");
				Scanner in = new Scanner(System.in);
				String memberTitle = in.nextLine();

				System.out.println("\nPlease enter the name of the " + memberTitle);
				String memberName = in.nextLine();

				System.out.println("\nPlease enter the telephoneNumber of the " + memberTitle);
				Scanner memberNumberUpdate = new Scanner(System.in);
				int memberNameTelephoneNumber = memberNumberUpdate.nextInt();

				System.out.println("\nPlease enter the emailAddress of the " + memberTitle);
				String memberNameEmailAddress = in.nextLine();

				System.out.println("\nPlease enter the physicalAddress of the " + memberTitle);
				String memberNamePhysicalAddress = in.nextLine();

				// String for the prepared statement
				String sqlMember = "INSERT INTO project_members VALUES (?, ?, ?, ?,?, ?,?) ";
				// prepared SQL statement to be executed
				PreparedStatement memberStatement = connection.prepareStatement(sqlMember);
		
				// Parameter values set to the prepared statement string "psMember"
				memberStatement.setInt(1,projectNumber);
				memberStatement.setString(2,projectName);
				memberStatement.setString(3, memberTitle);
				memberStatement.setString(4,memberName);
				memberStatement.setInt(5,memberNameTelephoneNumber);
				memberStatement.setString(6, memberNameEmailAddress);
				memberStatement.setString(7, memberNamePhysicalAddress);
		
				// Executing the Prepared statement
				rowsAffected = memberStatement.executeUpdate();
				
				System.out.println("\nQuery complete, " + rowsAffected + " rows added.");
				System.out.println("\nYou have just entered the " + memberTitle + " details");				
			}
			else if(guard == 0) {
				
				System.out.println("You are done adding all the project member details");
				break;
			}
		}
		}catch(InputMismatchException e) {
		
			System.out.println("Input error. Read menu options carefully");
			e.printStackTrace();
			e.getMessage();
		}
	}
	
    /**
     * Method printing all values in project_members table in all rows.
     * Takes a statement to try to avoid spreading DB access too far.
     * <p>
     * @param a statement on an existing connection
     * @throws SQLException
     */
    public static void printAllFromMembersTable(Statement statement) throws SQLException{
        
    	// Getting resultset sql statement
    	// Get results
        ResultSet results = statement.executeQuery("SELECT project_number, project_Name, member_title, member_name, member_phone, member_email,"
        		+ " member_address FROM project_members");
        
        // Loop through results
        // Display results to user
        while (results.next()) {
            System.out.println(
                    "\nproject_number : "+results.getString("project_number") +
                    "\nproject_Name : "+results.getString("project_Name") +
                    "\nmember_title : "+ results.getString("member_title") +
                    "\nmember_name : " + results.getString("member_name") +
                    "\nmember_phone : "+ results.getInt("member_phone") +       
                    "\nmember_email : "+ results.getString("member_email")+ 
                    "\nmember_address : "+ results.getString("member_address")
                );
        }
    }
}