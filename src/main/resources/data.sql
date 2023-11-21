-- this is where you can load data in the DB
INSERT INTO EMPLOYEE (ID, NAME) VALUES
(1, 'John Doe'),
(2, 'Jane Smith'),
(3, 'Bob Johnson');

-- Insert tasks for employee with id 1
INSERT INTO TASK (ID,DESCRIPTION,DUE_DATE,EMPLOYEE_ID) VALUES
(1, 'Task 1 for John Doe', '2023-01-15', 1),
(2, 'Task 2 for John Doe', '2023-02-20', 1);

-- Insert tasks for employee with id 2
INSERT INTO TASK (ID,DESCRIPTION,DUE_DATE,EMPLOYEE_ID) VALUES
(3, 'Task 1 for Jane Smith', '2023-03-10', 2),
(4, 'Task 2 for Jane Smith', '2023-04-15', 2);

-- Insert tasks for employee with id 3
INSERT INTO TASK (ID,DESCRIPTION,DUE_DATE,EMPLOYEE_ID) VALUES
(5, 'Task 1 for Bob Johnson', '2023-05-01', 3),
(6, 'Task 2 for Bob Johnson', '2023-06-05', 3);