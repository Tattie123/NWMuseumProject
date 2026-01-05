CREATE TABLE IF NOT EXISTS artefacts (
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

CREATE TABLE IF NOT EXISTS loans (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    isApproved BOOLEAN NOT NULL,
    name VARCHAR(255) NOT NULL,
    contactInfo VARCHAR(255) NOT NULL,
    telNum VARCHAR(32) NOT NULL,
    artefactName VARCHAR(255) NOT NULL,
    startDate DATE NOT NULL,
    endDate DATE NOT NULL
);

create table IF NOT EXISTS rooms (
    id       int auto_increment PRIMARY KEY,
    roomNum  TEXT null,
    roomName TEXT null,
    capacity int  null
);

DELETE FROM artefacts;
ALTER TABLE artefacts AUTO_INCREMENT = 1;

DELETE FROM loans;
ALTER TABLE loans AUTO_INCREMENT = 1;

DELETE FROM rooms;
ALTER TABLE rooms AUTO_INCREMENT = 1;

INSERT INTO artefacts (historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, depth, insurance, name, type, material) VALUES
('Ancient Egypt', 'Greek', 'Egypt', '101a', 'Unknown', '1323-01-01', 25.0, 50.0, 10.0, 2000000.0, 'Sarcophagus of Tutankhamun', 'Furniture', 'Wood'),
('Renaissance', 'Victorian', 'Italy', '101b', 'Leonardo da Vinci', '1503-01-01', 77.0, 53.0, 2.0, 1000000.0, 'Mona Lisa', 'Painting', ''),
('Ancient Egypt', 'Renaissance', 'Egypt', 'd04', 'Unknown', '1300-01-01', 30.0, 60.0, 15.0, 500000.0, 'Sarcophagus', 'Furniture', 'Metal'),
('Impressionism', 'Greek', 'France', 'c10', 'Claude Monet', '1872-01-01', 81.0, 60.0, 3.0, 750000.0, 'Impression thingy', 'Painting', '');

INSERT INTO loans (isApproved, name, contactInfo, telNum, artefactName, startDate, endDate) VALUES
(FALSE, "Bob", "Bob@email.com", "1234567890", "Mona Lisa", '2024-07-01', '2024-12-01'),
(TRUE, "Alice", "Alice@email.com", 987654321, "Sarcophagus of Tutankhamun", '2024-06-15', '2024-11-15');

INSERT INTO rooms (roomNum, roomName, capacity) VALUES
('101a', 'Ancient Artifacts', 5),
('101b', 'Renaissance Paintings', 3),
('c10', 'Modern Art', 4),
('d04', 'Sculpture Gallery', 2)