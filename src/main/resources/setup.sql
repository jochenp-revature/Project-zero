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