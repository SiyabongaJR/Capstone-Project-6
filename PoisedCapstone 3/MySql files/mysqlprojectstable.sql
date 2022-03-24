drop table if exists letters;

CREATE TABLE existing_projects(project_number INTEGER NOT NULL, project_name VARCHAR(50), building_type VARCHAR(50), Project_Address VARCHAR(50), erf_Number INTEGER,  total_Fee DOUBLE, paid_To_Date DOUBLE,
  project_Deadline DATE, project_completion VARCHAR(50) , completion_date VARCHAR(50), PRIMARY KEY (project_number));
  
