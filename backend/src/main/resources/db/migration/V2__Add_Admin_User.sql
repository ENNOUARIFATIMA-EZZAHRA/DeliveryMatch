-- Insert the base user record.
-- The password here MUST be a BCrypt hash. The example hash is for the password 'admin123'.
-- It is highly recommended to generate your own hash for security.
INSERT INTO user (nom, prenom, email, mot_de_pass, date_inscription, role) 
VALUES (
    'Admin', 
    'Super', 
    'admin@deliverymatch.com', 
    '$2a$10$G.pA6s7v9v.gM/C8SgYx3.p2i2U3h2Jz7j4/jC6E3Y0.aW9aB7wP2', -- This is a hash for 'admin123'
    NOW(), 
    'ADMINISTRATEUR'
);

-- Get the ID of the user we just inserted
SET @last_user_id = LAST_INSERT_ID();

-- Now, insert the corresponding record into the administrateur table
-- to make this user an administrator.
INSERT INTO administrateur (id) 
VALUES (@last_user_id); 