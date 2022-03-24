package poisedproject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 *Finalize peoject Class responsible for finalizing project
 * @author Sire
 *
 */
public class FinalizeProjects {

	/**
	 * Finalizes a project using either the project number or the project name.
	 * <p>
	 * Prints all company projects to user.
	 * Takes user search option, calls appropriate methods to search project from poisepms database.
	 * Checks if project paid up, if paid up, present user with finalization options.
	 * If not paid up, presents user with invoice for Client with accompanying details.
	 * <p>
	 * @param statement on an existing connection.
	 * @param connection with poisepms database.
	 */
	public static void projectFinalize( Statement statement, Connection connection) {
		
		try {
			
            
            String projectCompletion = "Finalized";
            System.out.println("\n ------------------ALL COMPANY PROJECTS-----------------------");
            ViewProjects.printAllFromTable(statement);
           
            // Menu options to finalize project
            System.out.println("\nplease type \'1\' to use the project number  to Finalize a project");
            System.out.println("\nplease type \'2\' to use the project name  to Finalize a project"); 
                     
            Scanner input = new Scanner(System.in);
            int searchChoice = input.nextInt();
           
           if(searchChoice == 1) {
        	   
           	   System.out.println("\nplease enter  project number for the project you want to Finalize");
           	   Scanner in = new Scanner(System.in);
               int projectNumber = in.nextInt();
               
               // Passing details to method that will finalize project using ProjectNumber
               getFinalizedProjectNumber(connection, statement, projectCompletion, projectNumber);
              
       
           } 
           
           else if(searchChoice == 2) {
        	          	   
           	   System.out.println("\nplease enter  project name for the project you want to Finalize a project");
           	   Scanner in = new Scanner(System.in);
               String projectName = in.nextLine();
               
               // Passing details to method to finalize using projectName
               getFinalizedProjectName(connection, statement, projectCompletion, projectName);
               
           	}            
         
		}catch(InputMismatchException e) {
		
			System.out.println("Input error. Read menu options carefully");
			e.printStackTrace();
			e.getMessage();
		}catch (SQLException e) {
            // We only want to catch a SQLException - anything else is off-limits for now.
            e.printStackTrace();
        }
	}

	/**
	 * Uses project name to search for project and Finalize project.
	 * <p>
	 * Passes sql statement to database to get total fee and paid to date.
	 * Loops through result from data base, checks if paid up or not
	 * then presents appropriate options to user given paid up status of project.
	 * If project paid up, finalize project 
	 * If project not paid up then present user with invoice for client.
	 * <p>
	 * @param connection with poisepms database.
	 * @param statement statement on an existing connection.
	 * @param projectCompletion to change project completion status.
	 * @param projectName to search for specific project from poisepms database.
	 * @throws SQLException
	 */
	public static void getFinalizedProjectName(Connection connection, Statement statement, String projectCompletion, String projectName)
			throws SQLException {
		
			ResultSet results;
		
			// String for the prepared statement
			String projectNamesql = "SELECT total_Fee, paid_To_Date"
		   		+ "  FROM existing_projects WHERE project_Name = ?";
		   
			// prepared SQL statement to be executed 
		   PreparedStatement projectNameStatement = connection.prepareStatement(projectNamesql);
		   
		   // Parameter values set to the prepared statement string 
		   projectNameStatement.setString(1,projectName);
		   
		   //executing update
		   results = projectNameStatement.executeQuery();
		   
		   // using while loop to loop through ResultSet
		   while(results.next()) {
			   
		       double totalFeeDouble = results.getDouble("total_Fee");
		       double paidToDateDouble = results.getDouble("paid_To_Date");
		       
				// Checking for paid up projects from poisepms database
				if (paidToDateDouble>= totalFeeDouble) {

					
					// Presenting user with project name and requiring completion date for each paid up project
					System.out.println("\nProject " + projectName + " is complete and paid up, enter the completion date"
							+ " in the number format of year-month-day e.g 2022-12-24 so the project"
							+ " will be marked as \" finilized \" ");
				
					Scanner input1= new Scanner(System.in);
					String completionDate = input1.next();
					
					// String for prepared statement	
					String sqlDetails = "UPDATE existing_projects SET completion_Date =  (?) " + "WHERE project_Name =  (?)";
					
					
					PreparedStatement psCompletion = connection.prepareStatement(sqlDetails);
					
		            // Setting statement string parameters
					psCompletion.setString(1,completionDate);
				
					psCompletion.setString(2, projectName);
					
					// executing update
					psCompletion.executeUpdate();
					
					String sqlFinalize = "UPDATE existing_projects SET project_Completion = ?"
							+ " WHERE project_Name = ?";
					
					PreparedStatement psFinalize = connection.prepareStatement(sqlFinalize);
					
					// Setting statement string parameters
					psFinalize.setString(1,projectCompletion);
					psFinalize.setString(2, projectName);
					
					// executing update
					psFinalize.executeUpdate();
					
					System.out.println("Query complete");
											
		            System.out.println("\n ------------------ALL COMPANY PROJECTS AFTER CHANGE-----------------------");
		            ViewProjects.printAllFromTable(statement);

				}
				// If the project is completed but not paid up, require client details to create invoice of amount to be invoiced
				else if(paidToDateDouble < totalFeeDouble) {
			
					double toBeInvoiced = totalFeeDouble - paidToDateDouble;

					System.out.println("\nProject " + projectName
							+ " is complete but not paid up, and an invoice will be generated for the client");
					// Printing out the invoice for the client
					System.out.println("\nThe invoice of the client will be generated below: ");
					
		            String clientDetails = "SELECT member_title, member_name, member_phone, member_email, member_address"
		               		+ " FROM project_members WHERE project_Name = ? and member_title = \'client\' ";
		               
		            // prepared SQL statement to be executed
		            PreparedStatement psclientDetails = connection.prepareStatement(clientDetails);
		               
		            // Parameter values set to the prepared statement string "psclientDetails"
		            psclientDetails.setString(1,projectName);
		             
		            // executing query
		            ResultSet  resultsInvoice = psclientDetails.executeQuery();
		             
		            // passing resultsInvoice to clientInvoiceDetails to print out client details
		            clientInvoiceDetails(resultsInvoice);					
					
		            System.out.println("\nThe total fee owed by the client is R: " + toBeInvoiced);

		}
			   
		   }
	}
	
	/**
	 * Uses project number to search database for project to finalize.
	 * <p>
	 * Passes sql statement to database to get total fee and paid to date.
	 * Loops through result from data base, checks if paid up or not
	 * then presents appropriate options to user given paid up status of project.
	 * If project paid up, finalize project 
	 * If project not paid up then present user with invoice for client.
	 * 
	 * <p>
	 * @param connection with poisepms database
	 * @param statement statement on an existing connection.
	 * @param projectCompletion to change project completion status.
	 * @param projectNumber to search for specific project from poisepms database.
	 * @throws SQLException
	 */
	public static void getFinalizedProjectNumber(Connection connection, Statement statement, String projectCompletion, int projectNumber)
			throws SQLException {
		ResultSet results;
		
			// String for the prepared statement
		   String sqlProjectNumber = "SELECT  total_Fee, paid_To_Date FROM existing_projects WHERE project_Number = ?";
		   
		   // statement to be passed to database
		   PreparedStatement projectNumberStatement = connection.prepareStatement(sqlProjectNumber);
		   
		   // setting the parameters to statement
		   projectNumberStatement.setInt(1,projectNumber);
		   
		   results = projectNumberStatement.executeQuery();
		   
		   // using while loop to loop through ResultSet
		   while(results.next()) {
			   
		       double totalFeeDouble = results.getDouble("total_Fee");
		       
		       double paidToDateDouble = results.getDouble("paid_To_Date");
		       
				// Checking for paid up projects from file
				if (paidToDateDouble>= totalFeeDouble) {

					// Presenting user with project name and requiring completion date for each paid up project
					System.out.println("\nProject " + projectNumber + " is complete and paid up, enter the completion date"
							+ " in the number format of year-month-day e.g 2022-12-24 so the project"
							+ " will be marked as \" finilized \" ");
							    	
					Scanner input1= new Scanner(System.in);
					String completionDate = input1.next();
									
					String sql = "UPDATE existing_projects SET completion_Date =  (?) " + "WHERE project_Number =  (?)";
					
					// statement to be passed to the database
					PreparedStatement psCompletion = connection.prepareStatement(sql);
					
					// setting parameters to statement
					psCompletion.setString(1,completionDate);
					psCompletion.setInt(2, projectNumber);
					psCompletion.executeUpdate();
					String sqlFinalize = "UPDATE existing_projects SET project_Completion = ?"
							+ " WHERE project_Number = ?";
					
					PreparedStatement psFinalize = connection.prepareStatement(sqlFinalize);
					
					psFinalize.setString(1,projectCompletion);
					psFinalize.setInt(2, projectNumber);
					psFinalize.executeUpdate();
					System.out.println("Query complete");
					
		            System.out.println("\n ------------------ALL COMPANY PROJECTS AFTER CHANGE-----------------------");
		            ViewProjects.printAllFromTable(statement);
											

				}
				// If the project is completed but not paid up, require client details to create invoice of amount to be invoiced
				else if(paidToDateDouble < totalFeeDouble) {
			
					double toBeInvoiced = totalFeeDouble - paidToDateDouble;

					System.out.println("\nProject " + projectNumber
							+ " is complete but not paid up, and an invoice will be generated for the client");
					// Printing out the invoice for the client
					
					System.out.println("\nThe invoice of the client will be generated below: ");
					
		               String clientDetails = "SELECT member_title, member_name, member_phone, member_email, member_address"
		               		+ "  FROM project_members WHERE project_Number = ? and member_title = \'client\' ";
		               
		               // prepared SQL statement to be executed
		               PreparedStatement psclientDetails = connection.prepareStatement(clientDetails);
		               
		               // Parameter values set to the prepared statement string "psclientDetails"
		               psclientDetails.setInt(1,projectNumber);
		             
		               ResultSet resultsInvoice = psclientDetails.executeQuery();
					
		                clientInvoiceDetails(resultsInvoice);					
					System.out.println("\nThe total fee owed by the client is R: " + toBeInvoiced);

					} 
		   }
	}

	/**
	 * Uses search result from database to print out client details for invoicing.
	 * <p>
	 * 
	 * @param resultsInvoice search result from database.
	 * @throws SQLException
	 */
	public static void clientInvoiceDetails(ResultSet resultsInvoice) throws SQLException {
		while (resultsInvoice.next()) {
			
		    System.out.println("\nmember Title : " + resultsInvoice.getString("member_title") 
		    +"\nclient Name : " + resultsInvoice.getString("member_name")
		    +"\nclient Telephone Number : " +resultsInvoice.getInt("member_phone")
		    +"\nclientEmailAddress : " + resultsInvoice.getString("member_email")
		    +"\nclient Physical Address : " + resultsInvoice.getString("member_address"));
		}
	}     
}

