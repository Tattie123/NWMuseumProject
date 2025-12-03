CREATE TABLE IF NOT EXISTS artefacts (
    historicEra TEXT NOT NULL,
    style TEXT NOT NULL,
    originCountry TEXT NOT NULL,
    currentRoom TEXT NOT NULL,
    author TEXT NOT NULL,
    dateOfCreation DATE NOT NULL,
    width DOUBLE NOT NULL,
    height DOUBLE NOT NULL,
    insurance DOUBLE NOT NULL,
    id INT AUTO_INCREMENT PRIMARY KEY,
    depth DOUBLE NOT NULL,
    name TEXT NOT NULL,
    type TEXT NOT NULL,
    material TEXT NOT NULL
);
