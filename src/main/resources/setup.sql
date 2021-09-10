DROP TYPE IF EXISTS jochenp.user_role CASCADE;
CREATE TYPE jochenp.user_role AS ENUM ('Admin', 'Employee', 'Customer');

DROP TABLE IF EXISTS jochenp.users CASCADE;
CREATE TABLE jochenp.users (
	id SERIAL PRIMARY KEY,
	username VARCHAR(50) NOT NULL UNIQUE,
	pwd VARCHAR(50) NOT NULL,
	user_role jochenp.user_role NOT NULL
);

DROP TABLE IF EXISTS jochenp.accounts CASCADE;
CREATE TABLE jochenp.accounts (
	id SERIAL PRIMARY KEY,
	balance NUMERIC (50, 2) DEFAULT 0,
	acc_owner INTEGER NOT NULL REFERENCES jochenp.users(id),
	active BOOLEAN DEFAULT FALSE
);

DROP TABLE IF EXISTS jochenp.accounts_jt CASCADE;
CREATE TABLE jochenp.users_accounts_jt (
	acc_owner INTEGER NOT NULL REFERENCES jochenp.users(id),
	account INTEGER NOT NULL REFERENCES jochenp.accounts(id)
);

DROP TABLE IF EXISTS jochenp.applications CASCADE;
CREATE TABLE jochenp.applications (
	id SERIAL PRIMARY KEY,
	app_owner INTEGER NOT NULL REFERENCES jochenp.users(id)
);

SELECT * FROM jochenp.users;

SELECT * FROM jochenp.accounts;

SELECT * FROM jochenp.accounts WHERE active = false;

SELECT * FROM jochenp.users_accounts_jt;

INSERT INTO jochenp.users (username, pwd, user_role)
	VALUES ('Moe', 'admin', 'Admin');

INSERT INTO jochenp.accounts (balance, acc_owner)
	VALUES (500, 5), (1000, 6);

INSERT INTO jochenp.users_accounts_jt
	VALUES (1, 1),
	(3, 3),
	(5, 3),
	(6, 4);
	
UPDATE accounts SET active = TRUE WHERE id = 3;

SELECT balance FROM accounts WHERE id = 1;

UPDATE users SET username = 'Robin', pwd = 'Holy...', user_role = 'Employee' WHERE id = 1 RETURNING id;

DELETE FROM users WHERE username = 'Larry';
