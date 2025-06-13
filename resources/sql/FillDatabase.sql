-- Nettoyage comme avant
DELETE FROM Affectation;
DELETE FROM Necessite;
DELETE FROM Possession;
DELETE FROM Disponibilite;
DELETE FROM Besoin;
DELETE FROM DPS;
DELETE FROM Competence;
DELETE FROM Journee;
DELETE FROM Sport;
DELETE FROM Administrateur;
DELETE FROM Secouriste;
DELETE FROM User;
DELETE FROM Site;
ALTER TABLE User AUTO_INCREMENT = 1;

-- Insertion sites JO 2030
INSERT INTO Site (code, nom, longitude, latitude) VALUES
                                                      (1, 'Centre de ski alpin', 6.6, 45.9),
                                                      (2, 'Patinoire olympique', 6.7, 45.9),
                                                      (3, 'Stade de biathlon', 6.65, 45.95),
                                                      (4, 'Piste de ski de fond', 6.7, 45.85),
                                                      (5, 'Arène de hockey sur glace', 6.68, 45.88),
                                                      (6, 'Centre de saut à ski', 6.63, 45.92),
                                                      (7, 'Village olympique', 6.64, 45.87);

-- Sports JO 2030
INSERT INTO Sport (code, nom) VALUES
                                  (1, 'Ski alpin'),
                                  (2, 'Patinage artistique'),
                                  (3, 'Biathlon'),
                                  (4, 'Ski de fond'),
                                  (5, 'Hockey sur glace'),
                                  (6, 'Saut à ski'),
                                  (7, 'Snowboard'),
                                  (8, 'Curling');

-- Journées pour plusieurs jours d’épreuves
INSERT INTO Journee (jour, mois, annee) VALUES (1, 2, 2026);
SET @idJournee1 = LAST_INSERT_ID();
INSERT INTO Journee (jour, mois, annee) VALUES (2, 2, 2026);
SET @idJournee2 = LAST_INSERT_ID();
INSERT INTO Journee (jour, mois, annee) VALUES (3, 2, 2026);
SET @idJournee3 = LAST_INSERT_ID();

-- Compétences
INSERT INTO Competence (intitule) VALUES
                                      ('PSE1'), ('PSE2'), ('CE'), ('CP'), ('CO'), ('SSA'), ('VPSP'), ('PBC'), ('PBF');

-- Graphe des nécessités
INSERT INTO Necessite VALUES ('PSE1', 'PSE2');
INSERT INTO Necessite VALUES ('PSE2', 'CE');
INSERT INTO Necessite VALUES ('CE', 'CP');
INSERT INTO Necessite VALUES ('CP', 'CO');
INSERT INTO Necessite VALUES ('SSA', 'PSE1');
INSERT INTO Necessite VALUES ('VPSP', 'PSE2');
INSERT INTO Necessite VALUES ('PBF', 'PBC');

-- Insertion de secouristes (User + Secouriste)

INSERT INTO User (login, password, role) VALUES
                                             ('sec1@example.com', 'pass1', 'rescuer'),
                                             ('sec2@example.com', 'pass2', 'rescuer'),
                                             ('sec3@example.com', 'pass3', 'rescuer'),
                                             ('sec4@example.com', 'pass4', 'rescuer'),
                                             ('sec5@example.com', 'pass5', 'rescuer'),
                                             ('sec6@example.com', 'pass6', 'rescuer'),
                                             ('sec7@example.com', 'pass7', 'rescuer'),
                                             ('sec8@example.com', 'pass8', 'rescuer'),
                                             ('sec9@example.com', 'pass9', 'rescuer'),
                                             ('sec10@example.com', 'pass10', 'rescuer');

-- Récupération des IDs User insérés
SELECT @idSec1 := idUser FROM User WHERE login = 'sec1@example.com';
SELECT @idSec2 := idUser FROM User WHERE login = 'sec2@example.com';
SELECT @idSec3 := idUser FROM User WHERE login = 'sec3@example.com';
SELECT @idSec4 := idUser FROM User WHERE login = 'sec4@example.com';
SELECT @idSec5 := idUser FROM User WHERE login = 'sec5@example.com';
SELECT @idSec6 := idUser FROM User WHERE login = 'sec6@example.com';
SELECT @idSec7 := idUser FROM User WHERE login = 'sec7@example.com';
SELECT @idSec8 := idUser FROM User WHERE login = 'sec8@example.com';
SELECT @idSec9 := idUser FROM User WHERE login = 'sec9@example.com';
SELECT @idSec10 := idUser FROM User WHERE login = 'sec10@example.com';

-- Insertion dans Secouriste avec les bons IDs (idSecouriste = idUser)
INSERT INTO Secouriste (idSecouriste, nom, prenom, date_naissance, tel, adresse) VALUES
                                                                                     (@idSec1, 'Nom1', 'Prenom1', '010101', '1111111111', 'adresse1'),
                                                                                     (@idSec2, 'Nom2', 'Prenom2', '020202', '2222222222', 'adresse2'),
                                                                                     (@idSec3, 'Nom3', 'Prenom3', '030303', '3333333333', 'adresse3'),
                                                                                     (@idSec4, 'Nom4', 'Prenom4', '040404', '4444444444', 'adresse4'),
                                                                                     (@idSec5, 'Nom5', 'Prenom5', '050505', '5555555555', 'adresse5'),
                                                                                     (@idSec6, 'Nom6', 'Prenom6', '060606', '6666666666', 'adresse6'),
                                                                                     (@idSec7, 'Nom7', 'Prenom7', '070707', '7777777777', 'adresse7'),
                                                                                     (@idSec8, 'Nom8', 'Prenom8', '080808', '8888888888', 'adresse8'),
                                                                                     (@idSec9, 'Nom9', 'Prenom9', '090909', '9999999999', 'adresse9'),
                                                                                     (@idSec10, 'Nom10', 'Prenom10', '101010', '0000000000', 'adresse10');

-- Disponibilités sur les trois jours
INSERT INTO Disponibilite VALUES (@idSec1, @idJournee1);
INSERT INTO Disponibilite VALUES (@idSec1, @idJournee2);
INSERT INTO Disponibilite VALUES (@idSec1, @idJournee3);

INSERT INTO Disponibilite VALUES (@idSec2, @idJournee1);
INSERT INTO Disponibilite VALUES (@idSec3, @idJournee1);
INSERT INTO Disponibilite VALUES (@idSec4, @idJournee2);
INSERT INTO Disponibilite VALUES (@idSec5, @idJournee2);
INSERT INTO Disponibilite VALUES (@idSec6, @idJournee3);
INSERT INTO Disponibilite VALUES (@idSec7, @idJournee3);
INSERT INTO Disponibilite VALUES (@idSec8, @idJournee1);
INSERT INTO Disponibilite VALUES (@idSec9, @idJournee2);
INSERT INTO Disponibilite VALUES (@idSec10, @idJournee3);

-- Possessions compétences respectant le graphe, exemples :
INSERT INTO Possession VALUES (@idSec1, 'PSE1');
INSERT INTO Possession VALUES (@idSec1, 'SSA');

INSERT INTO Possession VALUES (@idSec2, 'PSE2');
INSERT INTO Possession VALUES (@idSec2, 'PSE1');

INSERT INTO Possession VALUES (@idSec3, 'CE');
INSERT INTO Possession VALUES (@idSec3, 'PSE2');

INSERT INTO Possession VALUES (@idSec4, 'CP');
INSERT INTO Possession VALUES (@idSec4, 'CE');

INSERT INTO Possession VALUES (@idSec5, 'CO');
INSERT INTO Possession VALUES (@idSec5, 'CP');

INSERT INTO Possession VALUES (@idSec6, 'VPSP');
INSERT INTO Possession VALUES (@idSec6, 'PSE2');

INSERT INTO Possession VALUES (@idSec7, 'PBC');
INSERT INTO Possession VALUES (@idSec7, 'PBF');

INSERT INTO Possession VALUES (@idSec8, 'PSE1');
INSERT INTO Possession VALUES (@idSec8, 'SSA');

INSERT INTO Possession VALUES (@idSec9, 'PSE2');
INSERT INTO Possession VALUES (@idSec9, 'PBC');

INSERT INTO Possession VALUES (@idSec10, 'PBF');
INSERT INTO Possession VALUES (@idSec10, 'PBC');
