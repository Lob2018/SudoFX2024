-- Création de la table "software"
CREATE TABLE software (
softwareid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
currentversion VARCHAR(128) NOT NULL,
lastversion VARCHAR(128) NOT NULL,
createdat TIMESTAMP NOT NULL,
updatedat TIMESTAMP NOT NULL
);
-- Création de la table "menu"
CREATE TABLE menu (
menuid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
mode INTEGER NOT NULL
);
-- Création de la table "playerlanguage"
CREATE TABLE playerlanguage (
playerlanguageid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
iso VARCHAR(2) NOT NULL
);
-- Création de la table "background"
CREATE TABLE background (
backgroundid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
hexcolor VARCHAR(7) NOT NULL,
imagepath VARCHAR(260),
isimage BOOLEAN DEFAULT FALSE NOT NULL,
);
-- Création de la table "gamelevel"
CREATE TABLE gamelevel (
levelid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
levelname VARCHAR(64) NOT NULL
);
-- Création de la table "grid"
CREATE TABLE grid (
gridid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
defaultgridvalue VARCHAR(162) NOT NULL,
gridvalue VARCHAR(162) NOT NULL,
difficulty TINYINT
);
-- Création de la table "player"
CREATE TABLE player (
playerid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
playerlanguageplayerlanguageid BIGINT FOREIGN KEY REFERENCES playerlanguage(playerlanguageid),
backgroundbackgroundid BIGINT FOREIGN KEY REFERENCES background(backgroundid),
menumenuid BIGINT FOREIGN KEY REFERENCES menu(menuid),
name VARCHAR(256) NOT NULL UNIQUE,
isselected BOOLEAN DEFAULT FALSE NOT NULL,
createdat TIMESTAMP NOT NULL,
updatedat TIMESTAMP NOT NULL
);
CREATE INDEX idx_player_isselected ON player(isselected);
-- Création de la table "game"
CREATE TABLE game (
gameid BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
gridgridid BIGINT FOREIGN KEY REFERENCES grid(gridid),
playerplayerid BIGINT FOREIGN KEY REFERENCES player(playerid),
levellevelid BIGINT FOREIGN KEY REFERENCES gamelevel(levelid),
isselected BOOLEAN DEFAULT FALSE,
createdat TIMESTAMP NOT NULL,
updatedat TIMESTAMP NOT NULL
);
CREATE INDEX idx_game_isselected ON game(isselected);