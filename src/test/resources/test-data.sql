INSERT INTO buildings VALUES (NULL, 'Hyllie allé', '3');

INSERT INTO buildings VALUES (NULL, 'Drottninggatan', '5');

INSERT INTO buildings VALUES (NULL, 'Emporia', '4');

INSERT INTO apartments VALUES (NULL, '202', 69, 2, 9751, 1);

INSERT INTO apartments VALUES (NULL, '201', 37, 2, 7451, 1);

INSERT INTO apartments VALUES (NULL, '203', 75, 4, 17451, 3);

INSERT INTO tenants VALUES (NULL, 'Test', 'Data', '1234', 1);

INSERT INTO managers VALUES (NULL);

INSERT INTO tasks VALUES (NULL, 'Kitchen', 'To do', 'No', '2018-06-19', '2018-07-01', 1, 1);

INSERT INTO tasks VALUES (NULL, 'Bathroom', 'To do', 'No', '2018-06-19', '2018-07-02', 1, 1);

INSERT INTO task_messages VALUES (NULL, 'Water leaking from kitchen tap.', 1);

INSERT INTO task_messages VALUES (NULL, 'Water all over the floor. Come ASAP.', 1);

INSERT INTO users
VALUES (NULL, 'Test', '$2a$10$Q8LhsI6ybh7XowXtL8cJf.9uZg8XqjFm403HV.NEkDHqXUZH0VX26', 'ROLE_ADMIN', 1);

INSERT INTO users
VALUES (NULL, 'Test2', '$2a$10$Q8LhsI6ybh7XowXtL8cJf.9uZg8XqjFm403HV.NEkDHqXUZH0VX26', 'ROLE_MANAGER', null);

INSERT INTO users
VALUES (NULL, 'Test3', '$2a$10$Q8LhsI6ybh7XowXtL8cJf.9uZg8XqjFm403HV.NEkDHqXUZH0VX26', 'ROLE_TENANT', null);