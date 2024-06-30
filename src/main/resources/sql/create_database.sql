-- Connect to the MySQL server
-- This step is not required in the script but you should use a MySQL client to connect to your server

-- Create the database
CREATE DATABASE jax_rs_hibernate_rest;

-- Create the user with a specified password
CREATE USER 'jackson'@'localhost' IDENTIFIED BY 'jackson';

-- Grant all privileges on the new database to the user
GRANT ALL PRIVILEGES ON jax_rs_hibernate_rest.* TO 'jackson'@'localhost';

-- Apply the privilege changes
FLUSH PRIVILEGES;

