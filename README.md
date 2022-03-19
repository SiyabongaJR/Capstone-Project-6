# Capstone-Project-6
## Poised Database System

# Project Details

This is my **HyperionDev Software Engineering Bootcamp Level 3 Project** The project is for a small structural engineering firm to better manage their engineering projects more efficiently. The program is programmed in Java and uses a database (PoisedPMS) to keep their company projects more organized and secure. The projects adds more features and functonality to Capstone 5 project. Adding JDBC to the program for a more organized and easy way to access their project details and the project members involved in the project, eg Architects, contractors etc.


# Functionality

* When the program is compiled, a menu option will be presented, with 10 menu options:

1. Add new project
2. Edit Project due date
3. Edit total fee paid thus far
4. Edit contractor
5. View Completed Projects
6. View incomplete Projects
7. View Overdue Projects
8. View all projects
9. Finalize projects
10. Exit program

* If option 1 is selected, it calls the the New Project class which has a method to take in all user project details and project member details by calling the addProject member method. Then add the project details to the Existing_projects table in the PoisedPMS database, and add all the project member details from the user and add them to the project_members table in the PoisedPMS database, then present the user with all the company projects , and Project members after the new project details.

* If option 2 or option 3 is selected, it calls the ExistingProjectsUpdate class which has a method to change the Project due date or the Total fee paid thus far and update it in the Existing_Projects table in PoisedPMS database.

* If option 4 is selected, it calls the UpdateContractorDetails class which has a method to update the Project contractor details either by searching through the database using the project number or using the project name, then it will update the details in the project_members table in the PoisedPMS database. Then it wil present the user with the updated project member details.


* Option 5, 6, 7 and 8 all get called from the View projects class, which has methods to show Completed projects, incomplete projects, overdue projects and view all company projects methods, which all read from the Existing_projects table in the PoisedPMS database then present the user with the details in an easy to read manner.
*  

