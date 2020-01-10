/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Murilo Oliveira
 * Created: 11/11/2019
 */
DELETE FROM oauth_client_details;

INSERT INTO users (username, email, password, activated)
SELECT * FROM (SELECT 'admin', 'admin@admin.com', '$2a$10$r0RFDmpneBVryx.ihHK9gu6FFJQi4nTxQUqzdSTvrPpaKZMxigqpy', true) AS tmp
WHERE NOT EXISTS (
    SELECT username FROM users WHERE username = 'admin'
) LIMIT 1;

INSERT INTO authority (name)
SELECT * FROM (SELECT 'ROLE_USER') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM authority WHERE name = 'ROLE_USER'
) LIMIT 1;

INSERT INTO authority (name)
SELECT * FROM (SELECT 'ROLE_ADMIN') AS tmp
WHERE NOT EXISTS (
    SELECT name FROM authority WHERE name = 'ROLE_ADMIN'
) LIMIT 1;

INSERT INTO user_authority (username, authority)
SELECT * FROM (SELECT 'admin', 'ROLE_USER') AS tmp
WHERE NOT EXISTS (
    SELECT username, authority FROM user_authority WHERE username = 'admin' and authority = 'ROLE_USER'
) LIMIT 1;

INSERT INTO user_authority (username, authority)
SELECT * FROM (SELECT 'admin', 'ROLE_ADMIN') AS tmp
WHERE NOT EXISTS (
    SELECT username, authority FROM user_authority WHERE username = 'admin' and authority = 'ROLE_ADMIN'
) LIMIT 1;