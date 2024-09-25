CREATE TABLE customer (
  customer_id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  mobile_number VARCHAR(20) NOT NULL,
  pwd VARCHAR(500) NOT NULL,
  role VARCHAR(100) NOT NULL,
  create_dt DATE DEFAULT NULL,
  PRIMARY KEY (customer_id)
);

--Authorities table
CREATE TABLE authorities (
  id INT NOT NULL AUTO_INCREMENT,
  customer_id INT NOT NULL,
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_authorities_customer FOREIGN KEY (customer_id) REFERENCES customer (customer_id)
);


-- Create accounts table
CREATE TABLE accounts (
  customer_id INT NOT NULL,
  account_number INT PRIMARY KEY,
  account_type VARCHAR(100) NOT NULL,
  branch_address VARCHAR(200) NOT NULL,
  create_dt DATE DEFAULT NULL,
  CONSTRAINT fk_customer_accounts FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
);


-- Create account_transactions table
CREATE TABLE account_transactions (
  transaction_id VARCHAR(200) PRIMARY KEY,
  account_number INT NOT NULL,
  customer_id INT NOT NULL,
  transaction_dt DATE NOT NULL,
  transaction_summary VARCHAR(200) NOT NULL,
  transaction_type VARCHAR(100) NOT NULL,
  transaction_amt INT NOT NULL,
  closing_balance INT NOT NULL,
  create_dt DATE DEFAULT NULL,
  CONSTRAINT fk_accounts_transactions FOREIGN KEY (account_number) REFERENCES accounts (account_number) ON DELETE CASCADE,
  CONSTRAINT fk_customer_transactions FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
);





-- Create loans table
CREATE TABLE loans (
  loan_number INT AUTO_INCREMENT PRIMARY KEY,
  customer_id INT NOT NULL,
  start_dt DATE NOT NULL,
  loan_type VARCHAR(100) NOT NULL,
  total_loan INT NOT NULL,
  amount_paid INT NOT NULL,
  outstanding_amount INT NOT NULL,
  create_dt DATE DEFAULT NULL,
  CONSTRAINT fk_customer_loans FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
);



-- Create cards table
CREATE TABLE cards (
  card_id INT AUTO_INCREMENT PRIMARY KEY,
  card_number VARCHAR(100) NOT NULL,
  customer_id INT NOT NULL,
  card_type VARCHAR(100) NOT NULL,
  total_limit INT NOT NULL,
  amount_used INT NOT NULL,
  available_amount INT NOT NULL,
  create_dt DATE DEFAULT NULL,
  CONSTRAINT fk_customer_cards FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE
);



-- Create notice_details table
CREATE TABLE notice_details (
  notice_id INT AUTO_INCREMENT PRIMARY KEY,
  notice_summary VARCHAR(200) NOT NULL,
  notice_details VARCHAR(500) NOT NULL,
  notic_beg_dt DATE NOT NULL,
  notic_end_dt DATE DEFAULT NULL,
  create_dt DATE DEFAULT NULL,
  update_dt DATE DEFAULT NULL
);

CREATE TABLE contact_messages (
  contact_id varchar(50) NOT NULL PRIMARY KEY,
  contact_name varchar(50) NOT NULL,
  contact_email varchar(100) NOT NULL,
  subject varchar(500) NOT NULL,
  message varchar(2000) NOT NULL,
  create_dt date DEFAULT NULL
);

