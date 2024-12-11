DROP DATABASE IF EXISTS lombard;
CREATE DATABASE lombard;

USE lombard;

CREATE TABLE client (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,
    bill VARCHAR(20)
);

CREATE TABLE purchaser (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,
    bill VARCHAR(20)
);

CREATE TABLE manager (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE appraiser (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE property (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    price DECIMAL(10, 2),
    status VARCHAR(50)
);

CREATE TABLE loan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    client_id BIGINT NOT NULL,
    property_id BIGINT NOT NULL,
    start_date DATE,
    end_date DATE,
    status VARCHAR(50),
    FOREIGN KEY (client_id) REFERENCES client(id),
    FOREIGN KEY (property_id) REFERENCES property(id)
);

CREATE TABLE appraisal_request (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    appraiser_id BIGINT NOT NULL,
    property_id BIGINT NOT NULL,
    start_date DATE,
    status VARCHAR(50),
    FOREIGN KEY (appraiser_id) REFERENCES appraiser(id),
    FOREIGN KEY (property_id) REFERENCES property(id)
);

CREATE TABLE purchase_request (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    manager_id BIGINT NOT NULL,
    property_id BIGINT NOT NULL,
    start_date DATE,
    status VARCHAR(50),
    FOREIGN KEY (manager_id) REFERENCES manager(id),
    FOREIGN KEY (property_id) REFERENCES property(id)
);

CREATE TABLE purchase_offer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    purchaser_id BIGINT NOT NULL,
    property_id BIGINT NOT NULL,
    start_date DATE,
    status VARCHAR(50),
    FOREIGN KEY (purchaser_id) REFERENCES purchaser(id),
    FOREIGN KEY (property_id) REFERENCES property(id)
);

INSERT INTO client (name, phone, bill) VALUES 
('Valera', '89929874536', 'as83jk43123'),
('Katya', '89968872353', 'kfjhh48sda');

INSERT INTO purchaser (name, phone, bill) VALUES 
('Ivan', '89123456789', 'v023kknjew3'),
('Maria', '89501234567', '0bken3kbs3');

INSERT INTO manager (name, phone) VALUES 
('John', '89234567890'),
('Alice', '89654321098');

INSERT INTO appraiser (name, phone) VALUES 
('Bob', '89765432109'),
('Charlie', '89324675321');

INSERT INTO property (name, price, status) VALUES
('A124', NULL, 'Requested'),
('H266', 2500000.00, 'Offered'),
('F322', 2000000.00, 'Sold'),
('A884', 2500000.00, 'Evaluated'),
('H521', 2200000.00, 'Redeemed'),
('F782', 750000.00, 'Evaluated'),
('K415', 260000.00, 'Sold'),
('U778', 752000.00, 'Redeemed');

INSERT INTO loan (client_id, property_id, start_date, end_date, status) VALUES
(1, 1, NULL, NULL, 'Requested'),
(1, 2, '2022-07-01', '2022-10-01', 'Complete'),
(2, 3, '2023-06-01', '2023-09-01', 'Complete'),
(2, 4, '2024-10-01', '2025-01-01', 'Process'),
(2, 5, '2020-03-01', '2020-06-01', 'Complete'),
(2, 6, '2024-09-01', '2024-12-01', 'Process'),
(1, 7, '2021-01-05', '2021-04-05', 'Complete'),
(2, 8, '2022-09-03', '2022-12-03', 'Complete');

INSERT INTO appraisal_request (appraiser_id, property_id, start_date, status) VALUES 
(1, 1, '2024-12-01', 'Requested'),
(1, 2, '2022-07-01', 'Complete'),
(1, 3, '2023-06-01', 'Complete'),
(2, 4, '2024-10-01', 'Complete'),
(2, 5, '2020-03-01', 'Complete'),
(2, 6, '2024-11-01', 'Complete'),
(2, 7, '2021-01-05', 'Complete'),
(2, 8, '2022-09-03', 'Complete');

INSERT INTO purchase_request (manager_id, property_id, start_date, status) VALUES 
(1, 2, '2022-10-01', 'Process'),
(1, 3, '2023-09-01', 'Complete'),
(1, 7, '2021-04-05', 'Complete');

INSERT INTO purchase_offer (purchaser_id, property_id, start_date, status) VALUES 
(1, 3, '2023-10-01', 'Complete'),
(2, 7, '2021-12-10', 'Complete');

DELIMITER //

CREATE FUNCTION least_busy_appraiser() RETURNS BIGINT DETERMINISTIC
BEGIN
    DECLARE least_busy_appraiser_id BIGINT;

    SELECT id INTO least_busy_appraiser_id FROM (
        SELECT a.id AS id, COUNT(CASE WHEN ar.status = 'Requested' THEN 1 END) AS requested_appraisal_request_count
        FROM appraiser a
        JOIN appraisal_request ar ON a.id = ar.appraiser_id
        GROUP BY id 
        ORDER BY requested_appraisal_request_count
        LIMIT 1
    ) subquery;

    RETURN least_busy_appraiser_id;
END //

CREATE FUNCTION least_busy_manager() RETURNS BIGINT DETERMINISTIC
BEGIN
    DECLARE least_busy_manager_id BIGINT;

    SELECT id INTO least_busy_manager_id FROM (
        SELECT m.id AS id, COUNT(CASE WHEN pr.status = 'Process' THEN 1 END) AS process_purchase_request_count
        FROM manager m
        LEFT JOIN purchase_request pr ON m.id = pr.manager_id
        GROUP BY id
        ORDER BY process_purchase_request_count
        LIMIT 1
    ) subquery;

    RETURN least_busy_manager_id;
END //

CREATE FUNCTION price_client_properties(client_id BIGINT) RETURNS DECIMAL(13, 2) DETERMINISTIC
BEGIN
    DECLARE total_price DECIMAL(13, 2) DEFAULT 0;

    SELECT SUM(p.price) INTO total_price
    FROM property p
    JOIN loan l ON p.id = l.property_id
    WHERE l.client_id = client_id;

    RETURN total_price;
END //

CREATE FUNCTION price_purchaser_properties(purchaser_id BIGINT) RETURNS DECIMAL(13, 2) DETERMINISTIC
BEGIN
    DECLARE total_price DECIMAL(13, 2) DEFAULT 0;

    SELECT SUM(p.price) INTO total_price
    FROM property p
             JOIN purchase_offer po ON p.id = po.property_id
    WHERE po.purchaser_id = purchaser_id;

    RETURN total_price;
END //

CREATE PROCEDURE create_property(name VARCHAR(256), client_id BIGINT, OUT property_id BIGINT)
BEGIN
    SET FOREIGN_KEY_CHECKS = 0;

    INSERT INTO property (name, price, status)
    VALUES (name, NULL, 'Requested');

    SET property_id = LAST_INSERT_ID();

    INSERT INTO loan (client_id, property_id, status)
    VALUES (client_id, property_id, 'Requested');

    SET FOREIGN_KEY_CHECKS = 1;
END //

CREATE TRIGGER new_property
AFTER INSERT ON property
FOR EACH ROW
BEGIN
    INSERT INTO appraisal_request (appraiser_id, property_id, start_date, status)
    VALUES ((SELECT least_busy_appraiser()), NEW.id, CURDATE(), 'Requested');
END //

CREATE PROCEDURE evaluate_appraisal(appraisal_request_id BIGINT, price DECIMAL(10, 2))
BEGIN
    UPDATE property
    SET price = price,
        status = 'Evaluated'
    WHERE id IN (SELECT property_id FROM appraisal_request WHERE id = appraisal_request_id);

    UPDATE appraisal_request
    SET status = 'Complete'
    WHERE id = appraisal_request_id;
END //

CREATE TRIGGER update_property
AFTER UPDATE ON property
FOR EACH ROW
BEGIN
    IF NEW.status = 'Evaluated' THEN
        UPDATE loan
        SET status = 'Process',
            start_date = CURDATE(),
            end_date = DATE_ADD(CURDATE(), INTERVAL 3 MONTH)
        WHERE loan.property_id = NEW.id;

    ELSEIF NEW.status = 'Offered' THEN
        UPDATE loan
        SET status = 'Complete'
        WHERE loan.property_id = NEW.id;
        INSERT INTO purchase_request (manager_id, property_id, start_date, status)
        VALUES ((SELECT least_busy_manager()), NEW.id, CURDATE(), 'Process');

    ELSEIF NEW.status = 'Redeemed' THEN
        UPDATE loan
        SET status = 'Complete'
        WHERE loan.property_id = NEW.id;
    END IF;
END //

CREATE PROCEDURE offer_property(property_id BIGINT)
BEGIN
    UPDATE property
    SET status = 'Offered'
    WHERE id = property_id;
END //

CREATE PROCEDURE redeem_property(property_id BIGINT)
BEGIN
    UPDATE property
    SET status = 'Redeemed'
    WHERE id = property_id;
END //

CREATE PROCEDURE create_purchase_offer(purchaser_id BIGINT, purchase_request_id BIGINT, OUT purchase_offer BIGINT)
BEGIN
    INSERT INTO purchase_offer (purchaser_id, property_id, start_date, status)
    VALUES (purchaser_id, (SELECT property_id FROM purchase_request WHERE id = purchase_request_id), CURDATE(), 'Process');
    SET purchase_offer = LAST_INSERT_ID();
END //

CREATE EVENT offer_overdue_loans
ON SCHEDULE EVERY 1 MINUTE DO
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE property_id BIGINT;
    DECLARE cur CURSOR FOR SELECT loan.property_id FROM loan WHERE loan.end_date < CURDATE() AND loan.status = 'Process';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    OPEN cur;

    read_loop: LOOP
        FETCH cur INTO property_id;
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        CALL offer_property(property_id);
    END LOOP;

    CLOSE cur;
END //

CREATE EVENT update_purchase_status
ON SCHEDULE EVERY 1 MINUTE
DO
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE property_id BIGINT;
    DECLARE cur CURSOR FOR SELECT purchase_offer.property_id FROM purchase_offer WHERE purchase_offer.status = 'Process';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    OPEN cur;
    
    read_loop: LOOP
        FETCH cur INTO property_id;
        IF done THEN
            LEAVE read_loop;
        END IF;
        
        IF EXISTS (SELECT 1 FROM purchase_request WHERE purchase_request.property_id = property_id) THEN
            UPDATE property p
            SET p.status = 'Sold'
            WHERE p.id = property_id;
            
            UPDATE purchase_request pr
            SET pr.status = 'Comlpete'
            WHERE pr.property_id = property_id;

            UPDATE purchase_offer po
            SET po.status = 'Comlpete'
            WHERE po.property_id = property_id;
        END IF;
    END LOOP;
    
    CLOSE cur;
END //

DELIMITER ;

CREATE VIEW connected_loan AS
SELECT c.name AS client_name, c.phone AS client_phone,
       p.name AS property_name, p.price AS property_price, p.status AS property_status,
       l.start_date AS loan_start_date, l.end_date AS loan_end_date, l.status AS loan_status
FROM client c
JOIN loan l ON c.id = l.client_id
JOIN property p ON l.property_id = p.id;

CREATE VIEW connected_appraisal_request AS
SELECT a.name AS appraiser_name, a.phone AS appraiser_phone,
       p.name AS property_name, p.price AS property_price, p.status AS property_status,
       ar.start_date AS appraisal_start_date, ar.status AS appraisal_status
FROM appraiser a
JOIN appraisal_request ar ON a.id = ar.appraiser_id
JOIN property p ON ar.property_id = p.id;

CREATE VIEW connected_purchase_request AS
SELECT m.name AS manager_name, m.phone AS manager_phone,
       p.name AS property_name, p.price AS property_price, p.status AS property_status,
       pr.start_date AS request_start_date, pr.status AS request_status
FROM manager m
JOIN purchase_request pr ON m.id = pr.manager_id
JOIN property p ON pr.property_id = p.id;

CREATE VIEW connected_purchase_offer AS
SELECT pu.name AS purchaser_name, pu.phone AS purchaser_phone,
       p.name AS property_name, p.price AS property_price, p.status AS property_status,
       po.start_date AS offer_start_date, po.status AS offer_status
FROM purchaser pu
JOIN purchase_offer po ON pu.id = po.purchaser_id
JOIN property p ON po.property_id = p.id;
