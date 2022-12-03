CREATE TABLE IF NOT EXISTS prices (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    brand_id int NOT NULL,
    start_date timestamp NOT NULL,
    end_date timestamp NOT NULL,
    price_list int NOT NULL,
    product_id int NOT NULL,
    priority int NOT NULL,
    price numeric(10, 2) NOT NULL,
    curr varchar(3) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO prices (brand_id, start_date, end_date, price_list, product_id, priority, price, curr)
            VALUES (1, parsedatetime('2020-06-14-00.00.00', 'yyyy-MM-dd-HH.mm.ss'), parsedatetime('2020-12-31-23.59.59', 'yyyy-MM-dd-HH.mm.ss'), 1, 35455, 0, 35.50, 'EUR');

INSERT INTO prices (brand_id, start_date, end_date, price_list, product_id, priority, price, curr)
            VALUES (1, parsedatetime('2020-06-14-15.00.00', 'yyyy-MM-dd-HH.mm.ss'), parsedatetime('2020-06-14-18.30.00', 'yyyy-MM-dd-HH.mm.ss'), 2, 35455, 1, 25.45, 'EUR');

INSERT INTO prices (brand_id, start_date, end_date, price_list, product_id, priority, price, curr)
            VALUES (1, parsedatetime('2020-06-15-00.00.00', 'yyyy-MM-dd-HH.mm.ss'), parsedatetime('2020-06-15-11.00.00', 'yyyy-MM-dd-HH.mm.ss'), 3, 35455, 1, 30.50, 'EUR');

INSERT INTO prices (brand_id, start_date, end_date, price_list, product_id, priority, price, curr)
            VALUES (1, parsedatetime('2020-06-15-16.00.00', 'yyyy-MM-dd-HH.mm.ss'), parsedatetime('2020-12-31-23.59.59', 'yyyy-MM-dd-HH.mm.ss'), 4, 35455, 1, 38.95, 'EUR');