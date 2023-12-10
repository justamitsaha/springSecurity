--customer
INSERT INTO `customer` (`name`,`email`,`mobile_number`, `pwd`, `role`,`create_dt`)
 VALUES ('Happy','abc@xyx.com','9876548337', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'admin',CURDATE());

 --accounts
 INSERT INTO `accounts` (`customer_id`, `account_number`, `account_type`, `branch_address`, `create_dt`)
  VALUES (1, 18657, 'Savings', '123 Main Street, New York', CURDATE());

  --account_transactions
  INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
  `closing_balance`, `create_dt`)  VALUES (UUID(), 18657, 1, CURDATE()-INTERVAL 1 DAY, 'Coffee Shop', 'Withdrawal', 30,34500,CURDATE()-INTERVAL 7 DAY);

  INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
  `closing_balance`, `create_dt`)  VALUES (UUID(), 18657, 1, CURDATE()-INTERVAL 6 DAy, 'Uber', 'Withdrawal', 100,34400,CURDATE()- INTERVAL 6 DAY);

  INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
  `closing_balance`, `create_dt`)  VALUES (UUID(), 18657, 1, CURDATE()-INTERVAL 5 DAY, 'Self Deposit', 'Deposit', 500,34900,CURDATE()-INTERVAL 5 DAY);

  INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
  `closing_balance`, `create_dt`)  VALUES (UUID(), 18657, 1, CURDATE()-INTERVAL 4 DAY, 'Ebay', 'Withdrawal', 600,34300,CURDATE()-INTERVAL 4 DAY);

  INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
  `closing_balance`, `create_dt`)  VALUES (UUID(), 18657, 1, CURDATE()-INTERVAL 2 DAY, 'OnlineTransfer', 'Deposit', 700,35000,CURDATE()-INTERVAL 2 DAY);

  INSERT INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_dt`, `transaction_summary`, `transaction_type`,`transaction_amt`,
  `closing_balance`, `create_dt`)  VALUES (UUID(), 18657, 1, CURDATE()- INTERVAL 1 DAY, 'Amazon.com', 'Withdrawal', 100,34900,CURDATE()-INTERVAL 1 DAY);

--LOANS
INSERT INTO `loans` ( `customer_id`, `start_dt`, `loan_type`, `total_loan`, `amount_paid`, `outstanding_amount`, `create_dt`)
 VALUES ( 1, '2020-10-13', 'Home', 200000, 50000, 150000, '2020-10-13');

INSERT INTO `loans` ( `customer_id`, `start_dt`, `loan_type`, `total_loan`, `amount_paid`, `outstanding_amount`, `create_dt`)
 VALUES ( 1, '2020-06-06', 'Vehicle', 40000, 10000, 30000, '2020-06-06');

INSERT INTO `loans` ( `customer_id`, `start_dt`, `loan_type`, `total_loan`, `amount_paid`, `outstanding_amount`, `create_dt`)
 VALUES ( 1, '2018-02-14', 'Home', 50000, 10000, 40000, '2018-02-14');

INSERT INTO `loans` ( `customer_id`, `start_dt`, `loan_type`, `total_loan`, `amount_paid`, `outstanding_amount`, `create_dt`)
 VALUES ( 1, '2018-02-14', 'Personal', 10000, 3500, 6500, '2018-02-14');

--CARDS
 INSERT INTO `cards` (`card_number`, `customer_id`, `card_type`, `total_limit`, `amount_used`, `available_amount`, `create_dt`)
  VALUES ('4565XXXX4656', 1, 'Credit', 10000, 500, 9500, CURDATE());

 INSERT INTO `cards` (`card_number`, `customer_id`, `card_type`, `total_limit`, `amount_used`, `available_amount`, `create_dt`)
  VALUES ('3455XXXX8673', 1, 'Credit', 7500, 600, 6900, CURDATE());

 INSERT INTO `cards` (`card_number`, `customer_id`, `card_type`, `total_limit`, `amount_used`, `available_amount`, `create_dt`)
  VALUES ('2359XXXX9346', 1, 'Credit', 20000, 4000, 16000, CURDATE());

--notice_details
  INSERT INTO `notice_details` ( `notice_summary`, `notice_details`, `notic_beg_dt`, `notic_end_dt`, `create_dt`, `update_dt`)
  VALUES ('Home Loan Interest rates reduced', 'Home loan interest rates are reduced as per the goverment guidelines. The updated rates will be effective immediately',
  CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), null);

  INSERT INTO `notice_details` ( `notice_summary`, `notice_details`, `notic_beg_dt`, `notic_end_dt`, `create_dt`, `update_dt`)
  VALUES ('Net Banking Offers', 'Customers who will opt for Internet banking while opening a saving account will get a $50 amazon voucher',
  CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), null);

  INSERT INTO `notice_details` ( `notice_summary`, `notice_details`, `notic_beg_dt`, `notic_end_dt`, `create_dt`, `update_dt`)
  VALUES ('Mobile App Downtime', 'The mobile application of the EazyBank will be down from 2AM-5AM on 12/05/2020 due to maintenance activities',
  CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), null);

  INSERT INTO `notice_details` ( `notice_summary`, `notice_details`, `notic_beg_dt`, `notic_end_dt`, `create_dt`, `update_dt`)
  VALUES ('E Auction notice', 'There will be a e-auction on 12/08/2020 on the Bank website for all the stubborn arrears.Interested parties can participate in the e-auction',
  CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), null);

  INSERT INTO `notice_details` ( `notice_summary`, `notice_details`, `notic_beg_dt`, `notic_end_dt`, `create_dt`, `update_dt`)
  VALUES ('Launch of Millennia Cards', 'Millennia Credit Cards are launched for the premium customers of EazyBank. With these cards, you will get 5% cashback for each purchase',
  CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), null);

  INSERT INTO `notice_details` ( `notice_summary`, `notice_details`, `notic_beg_dt`, `notic_end_dt`, `create_dt`, `update_dt`)
  VALUES ('COVID-19 Insurance', 'EazyBank launched an insurance policy which will cover COVID-19 expenses. Please reach out to the branch for more details',
  CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), null);

--authorities
  INSERT INTO `authorities` (`customer_id`, `name`)
   VALUES (1, 'VIEWACCOUNT');

  INSERT INTO `authorities` (`customer_id`, `name`)
   VALUES (1, 'VIEWCARDS');

   INSERT INTO `authorities` (`customer_id`, `name`)
    VALUES (1, 'VIEWLOANS');

   INSERT INTO `authorities` (`customer_id`, `name`)
     VALUES (1, 'VIEWBALANCE');

   DELETE FROM `authorities`;

   INSERT INTO `authorities` (`customer_id`, `name`)
    VALUES (1, 'ROLE_USER');

   INSERT INTO `authorities` (`customer_id`, `name`)
    VALUES (1, 'ROLE_ADMIN');