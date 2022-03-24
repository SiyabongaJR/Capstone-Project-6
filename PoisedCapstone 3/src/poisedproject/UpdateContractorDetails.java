package poisedproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * UpdateContractorDetails class responsible for updating the contractor details.
 * @author Sire
 *
 */
public class UpdateContractorDetails {

	/**
	 * Method presents user with all  project members.
	 * Takes in user contractor details update.
	 * Passes prepared sql statement to database.
	 * Updates the database.
	 * Prints out project members from database after change.
	 * @param connection with poisepms database.
	 * @param statement on an existing connection.
	 */
	public static void contractorDetailsUpdate(Connection connection, Statement statement) {
		 
		 try {
				
	            int rowsAffected;
	
			 		System.out.println("\n--------ALL PROJECT MEMBERS--------" );
			 	
			 		NewProject.printAllFromMembersTable(statement);
			 		
			 		String contractor = "Contractor";
			 		
			 		// Prompting user for new contractor details
					// Take in user input and update appropriately
					System.out.println("\nPlease enter the updated details of the contractor as promted by the following options");
					System.out.println("\nPlease enter the project number for the contractor details you are changing");
					Scanner in = new Scanner(System.in);
					int prjectNumber = in.nextInt();
					
					System.out.println("\nPlease enter the name of the contractor");
					Scanner memberNameCheck = new Scanner(System.in);
					String memberName = memberNameCheck.nextLine();
					
		            // String for the prepared statement
		            String sqlName = "UPDATE project_members SET member_name =  (?) " + "WHERE project_number =  (?)"
		               		+ "and member_title = (?) ";
		            // prepared SQL statement to be executed
		            PreparedStatement psName = connection.prepareStatement(sqlName);
		               
		            // Parameter values set to the prepared statement string "psName"
		            psName.setString(1,memberName);
		            psName.setInt(2, prjectNumber);
		            psName.setString(3,contractor);
		            
		            // executing update
		            rowsAffected = psName.executeUpdate();
		            System.out.println("\nQuery complete, " + rowsAffected + " rows removed.");
		               
					System.out.println("\nPlease enter the contractor's  new Telephone number:");
					Scanner telephoneNumberUpdate = new Scanner(System.in);
					int telephoneNumber = telephoneNumberUpdate.nextInt();
					
		            // String for the prepared statement
		            String sqltelephone = "UPDATE project_members SET member_phone =  (?) " + "WHERE project_number =  (?)"
		               + "and member_title = (?) ";
		            
		            // prepared SQL statement to be executed
		            PreparedStatement psTelephone = connection.prepareStatement(sqltelephone);
		               
		            // Parameter values set to the prepared statement string "ps"
		            psTelephone.setInt(1,telephoneNumber);
		            psTelephone.setInt(2, prjectNumber);
		            psTelephone.setString(3,contractor);

		            rowsAffected = psTelephone.executeUpdate();
		            System.out.println("\nQuery complete, " + rowsAffected + " rows removed.");
		               
					System.out.println("\nPlease enter the contractor's new email address:");
					Scanner emailAddressupdate = new Scanner(System.in);
					String emailAddress = emailAddressupdate.nextLine();
					
		            // String for the prepared statement
		            String sqlEmail = "UPDATE project_members SET member_email =  (?) " + "WHERE project_number =  (?)"
		            		   + "and member_title = (?) ";
		            // prepared SQL statement to be executed
		            PreparedStatement psEmail = connection.prepareStatement(sqlEmail);
		               
		            // Parameter values set to the prepared statement string "ps"
		            psEmail.setString(1,emailAddress);
		            psEmail.setInt(2, prjectNumber);
		            psEmail.setString(3,contractor);

		            rowsAffected = psEmail.executeUpdate();
		            System.out.println("\nQuery complete, " + rowsAffected + " rows removed.");
								
					System.out.println("\nPlease enter the contractor's new Physical address:");
					Scanner physicalAddressEdit = new Scanner(System.in);
					String physicalAddress = physicalAddressEdit.nextLine();

		            // String for the prepared statement
		            String sqlAddress = "UPDATE project_members SET member_address =  (?) " + "WHERE project_number =  (?)"
		            		   + "and member_title = (?) ";
		            // prepared SQL statement to be executed
		            PreparedStatement psAddress = connection.prepareStatement(sqlAddress);
		               
		            // Parameter values set to the prepared statement string "psAddress"
		            psAddress.setString(1,physicalAddress);
		            psAddress.setInt(2, prjectNumber);
		            psAddress.setString(3,contractor);

		            rowsAffected = psAddress.executeUpdate();
		            System.out.println("\nQuery complete, " + rowsAffected + " rows removed.");
		               
		            System.out.println("\n--------ALL PROJECT MEMBERS AFTER CHANGES--------" );
					 	
				 	NewProject.printAllFromMembersTable(statement);
					
			}catch(InputMismatchException  e) {
			
			System.out.println();
			e.printStackTrace();
			e.getMessage();
			}catch(SQLException e) {
				 e.printStackTrace();
			}
	}
}
