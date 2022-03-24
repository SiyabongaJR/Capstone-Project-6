package poisedproject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 *  Existing Project Update class updates project deadline and project fee paid to date.
 * @author Sire
 *<p>
 */
public class ExistingProjectsUpdate {
	
	/**
	 * Updates the project deadline. Using either the project number or project name
	 * to search database.
	 * <p>
	 * Gets user project search choice.
	 * Gets projects deadline date update.
	 * Updates deadline in existing_projects table in poisepms database.
	 * Prints out updated changes to user.
	 * <p>
	 * @param statement  on an existing connection.
	 * @param connection with poisepms database.
	 */
	public static void updateProjectDeadline( Statement statement, Connection connection) {
				
		try {
				
            int rowsAffected;
            
            System.out.println("\n ------------------ALL COMPANY PROJECTS-----------------------");
            ViewProjects.printAllFromTable(statement);
            
           // menu options to update project deadline
           System.out.println("\nplease type \'1\' to use the project number  to update project deadline");
           System.out.println("\nplease type \'2\' to use the project name  to to update project deadline");        
           Scanner input = new Scanner(System.in);
           int searchChoice = input.nextInt();
           
           if(searchChoice == 1) {
        	   
           	   System.out.println("\nplease enter  project number for the project you want to update the deadline for");
           	   Scanner in = new Scanner(System.in);
               int projectNumber = in.nextInt();
               
               System.out.println("\nplease enter the updated project deadline in the YYYY-MM-DD format");
               String projectDeadLine = in.next();
               
               // String for the prepared statement
               String projectNumbersql = "UPDATE existing_projects SET project_Deadline =  (?) " + "WHERE project_Number =  (?)";
               // prepared SQL statement to be executed
               PreparedStatement projectNumberStatement = connection.prepareStatement(projectNumbersql);
               
              
               // Converting date string to date object
               Date projectDeadlineDate = Date.valueOf(projectDeadLine);
               
               // Parameter values set to the prepared statement string
               projectNumberStatement.setDate(1,projectDeadlineDate);
               projectNumberStatement.setInt(2, projectNumber);
               
               // Executing updates
               rowsAffected = projectNumberStatement.executeUpdate();
    
               System.out.println("\nQuery complete, " + rowsAffected + " rows removed.");
               
               System.out.println("\n ------------------COMPANY PROJECTS AFTER CHANGE-----------------------");
               ViewProjects.printAllFromTable(statement);

               
           } 
           
           else if(searchChoice == 2) {
        	          	   
           	   System.out.println("\nplease enter the project name for the project you want to update the deadline for");
           	   Scanner in = new Scanner(System.in);
               String projectName = in.nextLine();
               
               System.out.println("\nplease enter the updated project deadline in the YYYY-MM-DD format");
               String  projectDeadLine= in.nextLine();
              
               // String for the prepared statement
               String projectNamesql = "UPDATE existing_projects SET project_Deadline =  (?) " + "WHERE project_Name =  (?)";
               
               PreparedStatement projectNameStament = connection.prepareStatement(projectNamesql);
               
               
               // Converting date string to date object               
               Date projectDeadlineDate = Date.valueOf(projectDeadLine);
               
               // Parameter values set to the prepared statement string "ps"
               projectNameStament.setDate(1,projectDeadlineDate);
               projectNameStament.setString(2, projectName);

               // Executing updates
               rowsAffected = projectNameStament.executeUpdate();
    
               System.out.println("\nQuery complete, " + rowsAffected + " rows removed.");
               System.out.println("\n ------------------COMPANY PROJECTS AFTER CHANGE-----------------------");
               ViewProjects.printAllFromTable(statement);
 
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
	 * Updates the project deadline. Using either the project number or project name
	 * to search database.
	 * <p>
	 * Gets user project search choice.
	 * Gets projects deadline date update.
	 * Updates fee paid to date in existing_projects table in poisepms database.
	 * Prints out updated changes to user.
	 * <p>
	 * @param statement on an existing connection.
	 * @param connection with poisepms database.
	 */
	public static void updateProjectFeePaidToDate( Statement statement, Connection connection) {
		
		try {

             int rowsAffected;
            
            System.out.println("\n ------------------ALL COMPANY PROJECTS-----------------------");
            ViewProjects.printAllFromTable(statement);
            
            // Menu options to update project fee paid thus far
            System.out.println("\nplease type \'1\' to use the project number  to update project the fee paid to date");
            System.out.println("\nplease type \'2\' to use the project name  to to update projectthe fee paid to date");        
         
           Scanner input = new Scanner(System.in);
           int searchChoice = input.nextInt();
           
           if(searchChoice == 1) {
        	   
           	   System.out.println("\nplease enter  project number for the project you want to update the fee paid to date for");
           	   Scanner in = new Scanner(System.in);
               String projectNumber = in.nextLine();
               
               System.out.println("\nplease enter the updated project fee paid to date");
               double paidToDate = in.nextDouble();
               
               // String for the prepared statement
               String projectNumbersql = "UPDATE existing_projects SET paid_To_Date =  (?) " + "WHERE project_Number =  (?)";
               // prepared SQL statement to be executed
               PreparedStatement projectNumberStatement = connection.prepareStatement(projectNumbersql);
               
               // Parameter values set to the prepared statement string
               projectNumberStatement.setDouble(1,paidToDate);
               projectNumberStatement.setString(2, projectNumber);

               // Executing update
               rowsAffected = projectNumberStatement.executeUpdate();
    
               System.out.println("\nQuery complete, " + rowsAffected + " rows removed.");
               System.out.println("\n ------------------COMPANY PROJECTS AFTER CHANGE-----------------------");
               ViewProjects.printAllFromTable(statement);
               
           } 
           else if(searchChoice == 2) {
        	          	   
           	   System.out.println("\nplease enter  project name for the project you want to update the fee paid to date for");
           	   Scanner in = new Scanner(System.in);
               String projectName = in.nextLine();
               
               System.out.println("\nplease enter the updated project fee paid to date");
               double paidToDate = in.nextDouble();
              
               // String for the prepared statement
               String projectNamesql = "UPDATE existing_projects SET paid_To_Date =  (?) " + "WHERE project_Name =  (?)";
               
               // prepared SQL statement to be executed  
               PreparedStatement projectNameStatement = connection.prepareStatement(projectNamesql);
               
               // Parameter values set to the prepared statement string               
               projectNameStatement.setDouble(1,paidToDate);
               projectNameStatement.setString(2, projectName);
               
               //executing update
               rowsAffected = projectNameStatement.executeUpdate();
    
               System.out.println("\nQuery complete, " + rowsAffected + " rows removed.");
               System.out.println("\n ------------------COMPANY PROJECTS AFTER CHANGE-----------------------");
               ViewProjects.printAllFromTable(statement);

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
}
