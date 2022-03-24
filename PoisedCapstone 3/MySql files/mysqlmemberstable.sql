drop table if exists letters;

CREATE TABLE project_members(project_number INTEGER NOT NULL,project_name VARCHAR(50), member_title VARCHAR(50), member_name VARCHAR(50), member_phone INTEGER, member_email VARCHAR(50),
member_address VARCHAR(50), FOREIGN KEY(project_number) REFERENCES existing_projects(project_number));

