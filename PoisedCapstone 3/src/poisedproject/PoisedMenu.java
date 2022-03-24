package poisedproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Poised menu class to present user with entire program menu options
 * Call methods from specific classes given user choice. 
 * @author Sire
 *
 */
public class PoisedMenu {
	
	/**
	 *menu method connects to the poisepms database
	 *Presents user with menu options
	 *Calls different classes' methods as per user menu choice.
	 */
	public static void menu() {
		
		
		
		// boolean to keep while loop in  check and terminate
		boolean loggedIn = true;
		
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
            // Set up finished, do some stuff:
		
			// While loop with menu options for user selection
			while(loggedIn) {
			
				System.out.println("\nPlease choose an option from the following menu options: \n" 
						+ "\n Input number \"1\" to add a new project\n"
						+ "\n Input number \"2\" to change the due date of the project\n"
						+ "\n Input number \"3\" to Change the total amount of the total fee paid to date\n" 
						+ "\n Input number \"4\" to update contractor Details\n" 
						+ "\n Input number \"5\" to view Completed Projects\n"
						+ "\n Input number \"6\" to view Incomplete Projects\n"
						+ "\n Input number \"7\" to view OverDue Projects\n"
						+ "\n Input number \"8\" to view All Projects\n"
						+ "\n Input number \"9\" to finalise the project\n" 
						+ "\n Input number \"10\" to exit menu\n");
		
				// taking in user input to use in if else if statements given user input
				// Given selection present user with appropriate menu option
				Scanner in = new Scanner(System.in);
				int menuInput = in.nextInt();
	
				if(menuInput == 1) {
			
					NewProject.addNewProject();
					
				}
					
				else if(menuInput == 2) {
			
					ExistingProjectsUpdate.updateProjectDeadline( statement, connection);
					
				}
				else if(menuInput == 3) {
			
					ExistingProjectsUpdate.updateProjectFeePaidToDate(statement, connection);
					
				}
				else if(menuInput == 4) {
					
					UpdateContractorDetails.contractorDetailsUpdate( connection, statement);
				}
				else if(menuInput == 5) {
			
					ViewProjects.viewCompletedProjects( statement);
					
				}
				else if(menuInput == 6) {
			
					ViewProjects.viewIncompleteProjects( statement);
				}
				else if(menuInput == 7) {
			
					ViewProjects.viewOverDueProjects( statement);
					
				}
				else if(menuInput == 8) {
			
					ViewProjects.viewAllProjects( statement);
					
				}
				else if(menuInput == 9) {
					
					FinalizeProjects.projectFinalize( statement, connection);
					
				}

				else if(menuInput == 10) {
			
					System.out.println("Exiting. Goodbye....");
					break;
				}
			}
		
		}catch(InputMismatchException | SQLException e) {
		
			System.out.println("Input error. Read menu options carefully");
			e.printStackTrace();
			e.getMessage();
		}
	}
}
