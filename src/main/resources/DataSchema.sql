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

create table IF NOT EXISTS rooms
(
    id       int auto_increment PRIMARY KEY,
    roomNum  TEXT null,
    roomName TEXT null,
    capacity int  null
);

DELETE FROM artefacts;
ALTER TABLE artefacts AUTO_INCREMENT = 1;

INSERT INTO artefacts (historicEra, style, originCountry, currentRoom, author, dateOfCreation, width, height, depth, insurance, name, type, material) VALUES
('Renaissance', 'Victorian', 'Italy', 'Room 1', 'Leonardo da Vinci', '1503-01-01', 77.0, 53.0, 2.0, 1000000.0, 'Mona Lisa', 'Painting', ''),
('Ancient Egypt', 'Renaissance', 'Egypt', 'Room 2', 'Unknown', '1300-01-01', 30.0, 60.0, 15.0, 500000.0, 'Sarcophagus', 'Furniture', 'Metal'),
('Impressionism', 'Greek', 'France', 'Room 3', 'Claude Monet', '1872-01-01', 81.0, 60.0, 3.0, 750000.0, 'Impression thingy', 'Painting', '');

INSERT INTO loans (isApproved, name, contactInfo, telNum, artefactName, startDate, endDate) VALUES
(FALSE, "Bob", "Bob@email.com", "1234567890", "Mona Lisa", '2024-07-01', '2024-12-01'),
(TRUE, "Alice", "Alice@email.com", 987654321, "Sarcophagus of Tutankhamun", '2024-06-15', '2024-11-15');

INSERT INTO rooms (roomNum, roomName, capacity) VALUES
('101', 'Ancient Artifacts', 5),
('102', 'Renaissance Paintings', 3),
('103', 'Modern Art', 4),
('104', 'Sculpture Gallery', 2),
('105', 'Photography Exhibit', 2),
('106', 'Contemporary Art', 3),
('107', 'Medieval Art', 1),
('108', 'Asian Art', 4),
('109', 'African Art', 3),
('110', 'European Art', 4);