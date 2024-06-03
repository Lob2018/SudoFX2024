-- Deleting tables
DROP TABLE IF EXISTS `SOFTWARE`;
DROP TABLE IF EXISTS `PLAYERLANGUAGE`;
DROP TABLE IF EXISTS `BACKGROUND`;
DROP TABLE IF EXISTS `GAMELEVEL`;
DROP TABLE IF EXISTS `GRID`;
DROP TABLE IF EXISTS `PLAYER`;
DROP TABLE IF EXISTS `GAME`;

-- Creation of the "software" table
CREATE TABLE `SOFTWARE` (
    `softwareuuid` UUID PRIMARY KEY UNIQUE DEFAULT gen_random_uuid(),
    `currentversion` VARCHAR(128),
    `lastversion` VARCHAR(128),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Creation of the "player_language" table
CREATE TABLE `PLAYERLANGUAGE` (
    `languageuuid` UUID PRIMARY KEY UNIQUE DEFAULT gen_random_uuid(),
    `iso` VARCHAR(2) NOT NULL
);

-- Creation of the "background" table
CREATE TABLE `BACKGROUND` (
    `backgrounduuid` UUID PRIMARY KEY UNIQUE DEFAULT gen_random_uuid(),
    `hexcolor` VARCHAR(7) NOT NULL,
    `imagepath` VARCHAR(260),
    `isimage` BOOLEAN DEFAULT false,
    `isselected` BOOLEAN DEFAULT false
);

-- Creation of the "game_level" table
CREATE TABLE `GAMELEVEL` (
    `leveluuid` UUID PRIMARY KEY UNIQUE DEFAULT gen_random_uuid(),
    `levelname` VARCHAR(64) NOT NULL
);

-- Creation of the "grid" table
CREATE TABLE `GRID` (
    `griduuid` UUID PRIMARY KEY UNIQUE DEFAULT gen_random_uuid(),
    `defaultgridvalue` VARCHAR(162),
    `gridvalue` VARCHAR(162)
);

-- Creation of the "player" table
CREATE TABLE `PLAYER` (
    `playeruuid` UUID PRIMARY KEY UNIQUE DEFAULT gen_random_uuid(),
    `languagelanguageuuid` uuid REFERENCES player_language(languageuuid),
    `backgroundbackgrounduuid` uuid REFERENCES background(backgrounduuid),
    `name` VARCHAR(256) NOT NULL,
    `isselected`` BOOLEAN DEFAULT false,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Creation of the "game" table
CREATE TABLE `GAME` (
    `gameuuid` UUID PRIMARY KEY UNIQUE DEFAULT gen_random_uuid(),
    `gridgriduuid` UUID REFERENCES grid(griduuid),
    `playerplayeruuid` UUID REFERENCES player(playeruuid),
    `levelleveluuid` UUID REFERENCES game_level(leveluuid),
    `isselected` BOOLEAN DEFAULT false,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

ALTER TABLE `PLAYER` ADD FOREIGN KEY (`languagelanguageuuid`) REFERENCES `PLAYERLANGUAGE` (`languageuuid`);
ALTER TABLE `PLAYER` ADD FOREIGN KEY (`backgroundbackgrounduuid`) REFERENCES `BACKGROUND` (`backgrounduuid`);

ALTER TABLE `GAME` ADD FOREIGN KEY (`gridgriduuid`) REFERENCES `GRID` (`griduuid`);
ALTER TABLE `GAME` ADD FOREIGN KEY (`playerplayeruuid`) REFERENCES `PLAYER` (`playeruuid`);
ALTER TABLE `GAME` ADD FOREIGN KEY (`levelleveluuid`) REFERENCES `GAMELEVEL` (`leveluuid`);


INSERT INTO SOFTWARE (currentversion, lastversion, createdat, updatedat)
VALUES ('1.0.0', '1.0.0', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


