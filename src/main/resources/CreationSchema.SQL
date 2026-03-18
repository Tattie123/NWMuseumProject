CREATE
DATABASE IF NOT EXISTS museumproject;

USE
museumproject;

CREATE TABLE IF NOT EXISTS artefacts
(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    historicEra VARCHAR(255) NOT NULL,
    style VARCHAR(255) NOT NULL,
    originCountry VARCHAR(255) NOT NULL,
    currentRoom VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    dateOfCreation DATE NOT NULL,
    width DOUBLE NOT NULL,
    height DOUBLE NOT NULL,
    depth DOUBLE NOT NULL,
    insurance DOUBLE NOT NULL,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    material VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS loans
(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    isApproved BOOLEAN NOT NULL,
    name VARCHAR(255) NOT NULL,
    contactInfo VARCHAR(255) NOT NULL,
    telNum VARCHAR(32) NOT NULL,
    artefactName VARCHAR(255) NOT NULL,
    startDate DATE NOT NULL,
    endDate DATE NOT NULL
);

create table IF NOT EXISTS rooms
(
    id       int auto_increment PRIMARY KEY,
    roomNum  TEXT null,
    roomName TEXT null,
    capacity int  null
);

CREATE TABLE IF NOT EXISTS images
(
    id int auto_increment PRIMARY KEY,
    name
    varchar(255) NOT NULL,
    data longblob NOT NULL
);

DELETE FROM artefacts;
ALTER TABLE artefacts AUTO_INCREMENT = 1;

DELETE FROM loans;
ALTER TABLE loans AUTO_INCREMENT = 1;

DELETE FROM rooms;
ALTER TABLE rooms AUTO_INCREMENT = 1;

DELETE FROM images;
ALTER TABLE images AUTO_INCREMENT = 1;