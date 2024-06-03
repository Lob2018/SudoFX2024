-- Création de la table "software"
CREATE TABLE software (
softwareuuid UUID PRIMARY KEY,
currentversion VARCHAR(128),
lastversion VARCHAR(128),
createdat TIMESTAMP NOT NULL,
updatedat TIMESTAMP NOT NULL
);
-- Création de la table "menu"
CREATE TABLE menu (
menuuuid UUID PRIMARY KEY,
mode INTEGER NOT NULL
);
-- Création de la table "playerlanguage"
CREATE TABLE playerlanguage (
playerlanguageuuid UUID PRIMARY KEY,
iso VARCHAR(2) NOT NULL
);
-- Création de la table "background"
CREATE TABLE background (
backgrounduuid UUID PRIMARY KEY,
hexcolor VARCHAR(7) NOT NULL,
imagepath VARCHAR(260),
isimage BOOLEAN DEFAULT FALSE,
);
-- Création de la table "gamelevel"
CREATE TABLE gamelevel (
leveluuid UUID PRIMARY KEY,
levelname VARCHAR(64) NOT NULL
);
-- Création de la table "grid"
CREATE TABLE grid (
griduuid UUID PRIMARY KEY,
defaultgridvalue VARCHAR(162),
gridvalue VARCHAR(162)
);
-- Création de la table "player"
CREATE TABLE player (
playeruuid UUID PRIMARY KEY,
playerlanguageplayerlanguageuuid UUID FOREIGN KEY REFERENCES playerlanguage(playerlanguageuuid),
backgroundbackgrounduuid UUID FOREIGN KEY REFERENCES background(backgrounduuid),
menumenuuuid UUID FOREIGN KEY REFERENCES menu(menuuuid),
name VARCHAR(256) NOT NULL UNIQUE,
isselected BOOLEAN DEFAULT FALSE,
createdat TIMESTAMP NOT NULL,
updatedat TIMESTAMP NOT NULL
);
CREATE INDEX idx_player_isselected ON player(isselected);
-- Création de la table "game"
CREATE TABLE game (
gameuuid UUID PRIMARY KEY,
gridgriduuid UUID FOREIGN KEY REFERENCES grid(griduuid),
playerplayeruuid UUID FOREIGN KEY REFERENCES player(playeruuid),
levelleveluuid UUID FOREIGN KEY REFERENCES gamelevel(leveluuid),
isselected BOOLEAN DEFAULT FALSE,
createdat TIMESTAMP NOT NULL,
updatedat TIMESTAMP NOT NULL
);
CREATE INDEX idx_game_isselected ON game(isselected);