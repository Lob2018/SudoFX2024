-- Creating values for software
INSERT INTO software (softwareuuid, currentversion, lastversion, createdat, updatedat)
VALUES (UUID(), '1.0.0', '1.0.0',now(), now());

-- Creating values for playerlanguage
INSERT INTO playerlanguage (playerlanguageuuid, iso) VALUES (UUID(), 'FR');
INSERT INTO playerlanguage (playerlanguageuuid, iso) VALUES (UUID(), 'EN');

-- Creating values for background
INSERT INTO background (backgrounduuid, hexcolor, imagepath, isimage) VALUES (UUID(), '#000000', '', FALSE);

-- Creating values for menu
INSERT INTO menu (menuuuid, mode) VALUES (UUID(), 0);
INSERT INTO menu (menuuuid, mode) VALUES (UUID(), 1);
INSERT INTO menu (menuuuid, mode) VALUES (UUID(), 2);

-- Creating values for gamelevel
INSERT INTO gamelevel (leveluuid, levelname) VALUES (UUID(), 'facile');
INSERT INTO gamelevel (leveluuid, levelname) VALUES (UUID(), 'normal');
INSERT INTO gamelevel (leveluuid, levelname) VALUES (UUID(), 'difficile');

-- Creating values for player
INSERT INTO player (playeruuid, name, playerlanguageplayerlanguageuuid, backgroundbackgrounduuid, menumenuuuid, isselected, createdat, updatedat)
VALUES (UUID(), 'anonyme', (SELECT playerlanguageuuid FROM playerlanguage WHERE iso = 'FR'), (SELECT backgrounduuid FROM background WHERE hexcolor = '#000000'), (SELECT menuuuid FROM menu WHERE mode = 0), TRUE, now(), now());