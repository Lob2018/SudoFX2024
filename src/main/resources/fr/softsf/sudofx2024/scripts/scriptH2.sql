-- Suppression des tables
DROP TABLE IF EXISTS software;
DROP TABLE IF EXISTS game;
DROP TABLE IF EXISTS gamelevel;
DROP TABLE IF EXISTS grid;
DROP TABLE IF EXISTS player;
DROP TABLE IF EXISTS playerlanguage;
DROP TABLE IF EXISTS background;

-- Création de la table "software"
CREATE TABLE software (
    softwareuuid UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    currentversion VARCHAR(128),
    lastversion VARCHAR(128),
    createdat TIMESTAMP NOT NULL,
    updatedat TIMESTAMP NOT NULL
);

-- Création de la table "playerlanguage"
CREATE TABLE playerlanguage (
    languageuuid UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    iso VARCHAR(2) NOT NULL
);

-- Création de la table "background"
CREATE TABLE background (
    backgrounduuid UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    hexcolor VARCHAR(7) NOT NULL,
    imagepath VARCHAR(260),
    isimage BOOLEAN DEFAULT FALSE,
    isselected BOOLEAN DEFAULT FALSE
);

-- Création de la table "gamelevel"
CREATE TABLE gamelevel (
    leveluuid UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    levelname VARCHAR(64) NOT NULL
);

-- Création de la table "grid"
CREATE TABLE grid (
    griduuid UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    defaultgridvalue VARCHAR(162),
    gridvalue VARCHAR(162)
);

-- Création de la table "player"
CREATE TABLE player (
    playeruuid UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    languagelanguageuuid UUID,
    backgroundbackgrounduuid UUID,
    name VARCHAR(256) NOT NULL UNIQUE,
    isselected BOOLEAN DEFAULT FALSE,
    createdat TIMESTAMP NOT NULL,
    updatedat TIMESTAMP NOT NULL
);

-- Création de la table "game"
CREATE TABLE game (
    gameuuid UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    gridgriduuid UUID,
    playerplayeruuid UUID,
    levelleveluuid UUID,
    isselected BOOLEAN DEFAULT FALSE,
    createdat TIMESTAMP NOT NULL,
    updatedat TIMESTAMP NOT NULL
);

-- Ajout des clés étrangères aux tables "player" et "game"
ALTER TABLE player ADD CONSTRAINT fk_language FOREIGN KEY (languagelanguageuuid) REFERENCES playerlanguage(languageuuid);
ALTER TABLE player ADD CONSTRAINT fk_background FOREIGN KEY (backgroundbackgrounduuid) REFERENCES background(backgrounduuid);

ALTER TABLE game ADD CONSTRAINT fk_grid FOREIGN KEY (gridgriduuid) REFERENCES grid(griduuid);
ALTER TABLE game ADD CONSTRAINT fk_player FOREIGN KEY (playerplayeruuid) REFERENCES player(playeruuid);
ALTER TABLE game ADD CONSTRAINT fk_level FOREIGN KEY (levelleveluuid) REFERENCES gamelevel(leveluuid);