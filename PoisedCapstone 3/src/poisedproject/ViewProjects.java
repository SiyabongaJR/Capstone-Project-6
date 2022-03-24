package poisedproject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * viewProjects class is responsible for displaying project details to user
 * <P>
 * Displays All the projects, the incomplete projects, the completed projects and the overdue projects to the user 
 * @author Sire
 *
 */
public class ViewProjects {

	/**
	 * method to print out all company projects.
	 * Presents user with a list of all projects in the table
	 * <p>
	 * 
	 * @param statement on an existing connection.
	 */
	public static void viewAllProjects( Statement statement) {
		
		try {
			
             
            System.out.println("\n ------------------ALL COMPANY PROJECTS-----------------------");
            
            printAllFromTable(statement);
            
            
		}catch(SQLException e) {
			 e.printStackTrace();
		}	
	}	
	
	/**
	 * Method to print out incomplete projects to user.
	 * <p>
	 * 
	 * Passes sql statement to database
	 * loops through results
	 * prints out results to user.
	 * 
	 * @param statement on an existing connection.
	 */
	public static void viewIncompleteProjects( Statement statement) {
				
		try {
			
 
				System.out.println("\n ------------------INCOMPLETE PROJECTS-----------------------");
				
				// run SELECT statement
				 ResultSet incomplete = statement.executeQuery("SELECT project_Number, project_Name, building_Type, project_Address,"
			        		+ "erf_Number, total_Fee, paid_To_Date, project_Deadline,"
			        		+ " project_Completion,completion_Date  FROM existing_projects WHERE  project_Completion = \'nonFinalized\'");
				 
				 // Loop over the results, printing them all.
				 // printing out details
			     while (incomplete.next()) {
			            System.out.println(
			                    "\nproject_Number : "+ incomplete.getInt("project_Number") +
			                    "\nproject_Name : " + incomplete.getString("project_Name") + 
			                    "\nbuilding_Type : "+ incomplete.getString("building_Type") +   
			                    "\nproject_Address : "+ incomplete.getString("project_Address") +       
			                    "\nerf_Number : "+ incomplete.getInt("erf_Number") +   
			                    "\ntotal_Fee : "+ incomplete.getDouble("total_Fee") +      
			                    "\npaid_To_Date : "+ incomplete.getDouble("paid_To_Date") +      
			                    "\nproject_Deadline : "+ incomplete.getString("project_Deadline") +  
			                    "\nproject_Completion : "+ incomplete.getString("project_Completion")+
			                    "\ncompletion_Date : "+ incomplete.getString("completion_Date")
		                );
			        }

	}catch(SQLException e) {
			 e.printStackTrace();
		}
	}	

	/**
	 * Method to print out completed projects to user.
	 * <p>
	 * 
	 * Passes sql statement to database
	 * loops through results
	 * prints out results to user.
	 * 
	 * @param statement on an existing connection.
	 */
	public static void viewCompletedProjects( Statement statement) {
		
		try {
			
				System.out.println("\n ------------------Completed PROJECTS-----------------------");
				
				// run SELECT statement
			 	ResultSet complete = statement.executeQuery("SELECT project_Number, project_Name, building_Type, project_Address,"
		        		+ "erf_Number, total_Fee, paid_To_Date, project_Deadline,"
		        		+ " project_Completion,completion_Date  FROM existing_projects WHERE  project_Completion = \'Finalized\'");
			 	
			 	// Loop over the results, printing them all.
		        while (complete.next()) {
		            System.out.println(
		                    "\nproject_Number : "+ complete.getInt("project_Number") +
		                    "\nproject_Name : " + complete.getString("project_Name") + 
		                    "\nbuilding_Type : "+ complete.getString("building_Type") +   
		                    "\nproject_Address : "+ complete.getString("project_Address") +       
		                    "\nerf_Number : "+ complete.getInt("erf_Number") +   
		                    "\ntotal_Fee : "+ complete.getDouble("total_Fee") +      
		                    "\npaid_To_Date : "+ complete.getDouble("paid_To_Date") +      
		                    "\nproject_Deadline : "+ complete.getString("project_Deadline") +  
		                    "\nproject_Completion : "+ complete.getString("project_Completion")+
		                    "\ncompletion_Date : "+ complete.getString("completion_Date")
		                );
		        }

				
		}catch(SQLException e) {
			 e.printStackTrace();
		}	
	}
	
	/**
	 * Method to print out overdue projects to user.
	 * <p>
	 * 
	 * Passes sql statement to database
	 * loops through results
	 * prints out results to user.
	 * @param statement on an existing connection.
	 */
	public static void viewOverDueProjects( Statement statement) {
		try {
			
 			
					System.out.println("\n ------------------OVERDUE PROJECTS-----------------------");	
					
					// run SELECT statement
					ResultSet results = statement.executeQuery("SELECT project_Number, project_Name, building_Type, project_Address,"
			        		+ "erf_Number, total_Fee, paid_To_Date, project_Completion,completion_Date, project_Deadline"
			        		+ "  FROM existing_projects WHERE project_Deadline < CURDATE() ");
					
					 // looping through statement Resultset
					 // printing out details
			        while (results.next()) {
			            System.out.println(
			                    "\nproject_Number : "+ results.getInt("project_Number") +
			                    "\nproject_Name : " + results.getString("project_Name") + 
			                    "\nbuilding_Type : "+ results.getString("building_Type") +   
			                    "\nproject_Address : "+ results.getString("project_Address") +       
			                    "\nerf_Number : "+ results.getInt("erf_Number") +   
			                    "\ntotal_Fee : "+ results.getDouble("total_Fee") +      
			                    "\npaid_To_Date : "+ results.getDouble("paid_To_Date") +      
			                    "\nproject_Completion : "+ results.getString("project_Completion")+
			                    "\ncompletion_Date : "+ results.getString("completion_Date")+
			                    "\nproject_Deadline : "+ results.getString("project_Deadline")
			                );
			        }

				
		}catch(SQLException e) {
			 e.printStackTrace();
		}
	}
	
    /**
     * Method printing all values in all rows.
     * Takes a statement to try to avoid spreading DB access too far.
     * 
     * @param a statement on an existing connection
     * @throws SQLException
     */
    public static void printAllFromTable(Statement statement) throws SQLException{
        
    	// run SELECT statement
        ResultSet results = statement.executeQuery("SELECT project_Number, project_Name, building_Type, project_Address,"
        		+ "erf_Number, total_Fee, paid_To_Date, project_Deadline, project_Completion, completion_Date  FROM existing_projects");
        while (results.next()) {
            System.out.println(
                   "\nproject_Number : "+ results.getInt("project_Number") +
                   "\nproject_Name : " + results.getString("project_Name") + 
                   "\nbuilding_Type : "+ results.getString("building_Type") +   
                   "\nproject_Address : "+ results.getString("project_Address") +       
                   "\nerf_Number : "+ results.getInt("erf_Number") +   
                   "\ntotal_Fee : "+ results.getDouble("total_Fee") +      
                   "\npaid_To_Date : "+ results.getDouble("paid_To_Date") +      
                   "\nproject_Deadline : "+ results.getString("project_Deadline") +  
                   "\nproject_Completion : "+ results.getString("project_Completion")+
                   "\ncompletion_Date : "+ results.getString("completion_Date")
                );
        }
    }
}
