
-- Insert into customer table
INSERT INTO customer (name, email, mobile_number, pwd, role, create_dt)
VALUES ('Happy', 'happy@example.com', '5334122365', '{bcrypt}$2a$12$66470DSU6DCXXqMiEadhtO60F1FFU0svn96szTKa.0saPPIKLaHbO', 'admin', CURRENT_DATE);

INSERT INTO customer (name, email, mobile_number, pwd, role, create_dt)
VALUES ('Sad', 'sad@example.com', '5334122366', '{bcrypt}$2a$12$66470DSU6DCXXqMiEadhtO60F1FFU0svn96szTKa.0saPPIKLaHbO', 'admin', CURRENT_DATE);


--Insert into authorities
--INSERT INTO authorities (customer_id, name)
--VALUES (1, 'VIEWACCOUNT');
--
--INSERT INTO authorities (customer_id, name)
--VALUES (1, 'VIEWCARDS');
--
--INSERT INTO authorities (customer_id, name)
--VALUES (1, 'VIEWLOANS');
--
--INSERT INTO authorities (customer_id, name)
--VALUES (1, 'VIEWBALANCE');

--DELETE FROM authorities;
--
INSERT INTO authorities (customer_id, name)
VALUES (1, 'ROLE_USER');

INSERT INTO authorities (customer_id, name)
VALUES (1, 'ROLE_ADMIN');



-- Insert into accounts table
INSERT INTO accounts (customer_id, account_number, account_type, branch_address, create_dt)
VALUES (1, 1865764534, 'Savings', '123 Main Street, New York', CURRENT_DATE);

-- Inserting data into account_transactions table
INSERT INTO account_transactions (
  transaction_id, account_number, customer_id, transaction_dt,
  transaction_summary, transaction_type, transaction_amt, closing_balance, create_dt
) VALUES (
  RANDOM_UUID(), 1865764534, 1, DATEADD('DAY', -7, CURRENT_DATE),
  'Coffee Shop', 'Withdrawal', 30, 34500, DATEADD('DAY', -7, CURRENT_DATE)
);

INSERT INTO account_transactions (
  transaction_id, account_number, customer_id, transaction_dt,
  transaction_summary, transaction_type, transaction_amt, closing_balance, create_dt
) VALUES (
  RANDOM_UUID(), 1865764534, 1, DATEADD('DAY', -6, CURRENT_DATE),
  'Uber', 'Withdrawal', 100, 34400, DATEADD('DAY', -6, CURRENT_DATE)
);

INSERT INTO account_transactions (
  transaction_id, account_number, customer_id, transaction_dt,
  transaction_summary, transaction_type, transaction_amt, closing_balance, create_dt
) VALUES (
  RANDOM_UUID(), 1865764534, 1, DATEADD('DAY', -5, CURRENT_DATE),
  'Self Deposit', 'Deposit', 500, 34900, DATEADD('DAY', -5, CURRENT_DATE)
);

INSERT INTO account_transactions (
  transaction_id, account_number, customer_id, transaction_dt,
  transaction_summary, transaction_type, transaction_amt, closing_balance, create_dt
) VALUES (
  RANDOM_UUID(), 1865764534, 1, DATEADD('DAY', -4, CURRENT_DATE),
  'Ebay', 'Withdrawal', 600, 34300, DATEADD('DAY', -4, CURRENT_DATE)
);

INSERT INTO account_transactions (
  transaction_id, account_number, customer_id, transaction_dt,
  transaction_summary, transaction_type, transaction_amt, closing_balance, create_dt
) VALUES (
  RANDOM_UUID(), 1865764534, 1, DATEADD('DAY', -2, CURRENT_DATE),
  'OnlineTransfer', 'Deposit', 700, 35000, DATEADD('DAY', -2, CURRENT_DATE)
);

INSERT INTO account_transactions (
  transaction_id, account_number, customer_id, transaction_dt,
  transaction_summary, transaction_type, transaction_amt, closing_balance, create_dt
) VALUES (
  RANDOM_UUID(), 1865764534, 1, DATEADD('DAY', -1, CURRENT_DATE),
  'Amazon.com', 'Withdrawal', 100, 34900, DATEADD('DAY', -1, CURRENT_DATE)
);

-- Insert into loans table
INSERT INTO loans (customer_id, start_dt, loan_type, total_loan, amount_paid, outstanding_amount, create_dt)
VALUES (1, '2020-10-13', 'Home', 200000, 50000, 150000, '2020-10-13');

INSERT INTO loans (customer_id, start_dt, loan_type, total_loan, amount_paid, outstanding_amount, create_dt)
VALUES (1, '2020-06-06', 'Vehicle', 40000, 10000, 30000, '2020-06-06');

INSERT INTO loans (customer_id, start_dt, loan_type, total_loan, amount_paid, outstanding_amount, create_dt)
VALUES (1, '2018-02-14', 'Home', 50000, 10000, 40000, '2018-02-14');

INSERT INTO loans (customer_id, start_dt, loan_type, total_loan, amount_paid, outstanding_amount, create_dt)
VALUES (1, '2018-02-14', 'Personal', 10000, 3500, 6500, '2018-02-14');


-- Insert into cards table
INSERT INTO cards (card_number, customer_id, card_type, total_limit, amount_used, available_amount, create_dt)
VALUES ('4565XXXX4656', 1, 'Credit', 10000, 500, 9500, CURRENT_DATE);

INSERT INTO cards (card_number, customer_id, card_type, total_limit, amount_used, available_amount, create_dt)
VALUES ('3455XXXX8673', 1, 'Credit', 7500, 600, 6900, CURRENT_DATE);

INSERT INTO cards (card_number, customer_id, card_type, total_limit, amount_used, available_amount, create_dt)
VALUES ('2359XXXX9346', 1, 'Credit', 20000, 4000, 16000, CURRENT_DATE);



-- Insert into notice_details table
INSERT INTO notice_details (notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
VALUES ('Home Loan Interest rates reduced', 'Home loan interest rates are reduced as per the government guidelines. The updated rates will be effective immediately', DATEADD('DAY', -30, CURRENT_DATE), DATEADD('DAY', 30, CURRENT_DATE), CURRENT_DATE, NULL);

INSERT INTO notice_details (notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
VALUES ('Net Banking Offers', 'Customers who will opt for Internet banking while opening a saving account will get a $50 amazon voucher', DATEADD('DAY', -30, CURRENT_DATE), DATEADD('DAY', 30, CURRENT_DATE), CURRENT_DATE, NULL);

INSERT INTO notice_details (notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
VALUES ('Mobile App Downtime', 'The mobile application of the EazyBank will be down from 2AM-5AM on 12/05/2020 due to maintenance activities', DATEADD('DAY', -30, CURRENT_DATE), DATEADD('DAY', 30, CURRENT_DATE), CURRENT_DATE, NULL);

INSERT INTO notice_details (notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
VALUES ('E Auction notice', 'There will be an e-auction on 12/08/2020 on the Bank website for all the stubborn arrears. Interested parties can participate in the e-auction', DATEADD('DAY', -30, CURRENT_DATE), DATEADD('DAY', 30, CURRENT_DATE), CURRENT_DATE, NULL);

INSERT INTO notice_details (notice_summary, notice_details, notic_beg_dt, notic_end_dt, create_dt, update_dt)
VALUES ('Launch of Millennia Cards', 'Millennia Credit Cards are launched for the premium customers of EazyBank', DATEADD('DAY', -30, CURRENT_DATE), DATEADD('DAY', 30, CURRENT_DATE), CURRENT_DATE, NULL);

