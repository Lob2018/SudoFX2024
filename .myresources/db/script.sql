-- Deleting tables
DROP TABLE IF EXISTS software;
DROP TABLE IF EXISTS player_language;
DROP TABLE IF EXISTS background;
DROP TABLE IF EXISTS game_level;
DROP TABLE IF EXISTS grid;
DROP TABLE IF EXISTS player;
DROP TABLE IF EXISTS game;

-- Creation of the "software" table
CREATE TABLE software (
    softwareuuid uuid PRIMARY KEY UNIQUE DEFAULT gen_random_uuid(),
    currentversion VARCHAR(128),
    lastversion VARCHAR(128),
    createdat TIMESTAMP NOT NULL,
    updatedat TIMESTAMP NOT NULL
);

-- Creation of the "player_language" table
CREATE TABLE player_language (
    languageuuid uuid PRIMARY KEY UNIQUE DEFAULT gen_random_uuid(),
    iso VARCHAR(2) NOT NULL
);

-- Creation of the "background" table
CREATE TABLE background (
    backgrounduuid uuid PRIMARY KEY UNIQUE DEFAULT gen_random_uuid(),
    hexcolor VARCHAR(7) NOT NULL,
    imagepath VARCHAR(260),
    isimage BOOLEAN DEFAULT false,
    isselected BOOLEAN DEFAULT false
);

-- Creation of the "game_level" table
CREATE TABLE game_level (
    leveluuid uuid PRIMARY KEY UNIQUE DEFAULT gen_random_uuid(),
    levelname VARCHAR(64) NOT NULL
);

-- Creation of the "grid" table
CREATE TABLE grid (
    griduuid uuid PRIMARY KEY UNIQUE DEFAULT gen_random_uuid(),
    defaultgridvalue VARCHAR(162),
    gridvalue VARCHAR(162)
);

-- Creation of the "player" table
CREATE TABLE player (
    playeruuid uuid PRIMARY KEY UNIQUE DEFAULT gen_random_uuid(),
    languagelanguageuuid uuid REFERENCES player_language(languageuuid),
    backgroundbackgrounduuid uuid REFERENCES background(backgrounduuid),
    name VARCHAR(256) NOT NULL,
    isselected BOOLEAN DEFAULT false,
    createdat TIMESTAMP NOT NULL,
    updatedat TIMESTAMP NOT NULL
);

-- Creation of the "game" table
CREATE TABLE game (
    gameuuid uuid PRIMARY KEY UNIQUE DEFAULT gen_random_uuid(),
    gridgriduuid uuid REFERENCES grid(griduuid),
    playerplayeruuid uuid REFERENCES player(playeruuid),
    levelleveluuid uuid REFERENCES game_level(leveluuid),
    isselected BOOLEAN DEFAULT false,
    createdat TIMESTAMP NOT NULL,
    updatedat TIMESTAMP NOT NULL
);
