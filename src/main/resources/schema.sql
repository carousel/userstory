DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_work_experience;

CREATE TABLE users (
       id INT AUTO_INCREMENT PRIMARY KEY,
       first_name VARCHAR(250) NOT NULL,
       last_name VARCHAR(250) NOT NULL,
       username VARCHAR(250) NOT NULL,
       age INT NOT NULL,
       description VARCHAR(250) DEFAULT NULL
);
-- CREATE TABLE user_work_experience (
--     id INT AUTO_INCREMENT PRIMARY KEY,
--     user_id INT NOT NULL,
--     status VARCHAR(12) NOT NULL,
--     date_end DATE DEFAULT NULL
-- );