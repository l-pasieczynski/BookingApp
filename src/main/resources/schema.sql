CREATE SCHEMA IF NOT EXISTS booking;
USE booking;

DROP TABLE IF EXISTS TEST;
CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255));
SELECT * FROM TEST ORDER BY ID;
