DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_work_experience;
DROP TABLE IF EXISTS role;

CREATE TABLE users (
       id INT AUTO_INCREMENT PRIMARY KEY,
       first_name VARCHAR(250) NOT NULL,
       last_name VARCHAR(250) NOT NULL,
       username VARCHAR(250) NOT NULL,
       age INT NOT NULL,
       description VARCHAR(250) DEFAULT NULL
);
CREATE TABLE role (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(12) NOT NULL,
    description VARCHAR(128) NOT NULL
);