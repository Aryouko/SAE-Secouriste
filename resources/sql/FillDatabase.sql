USE sae_db;

-- Nettoyage
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

-- Insertion des sites
INSERT INTO Site (code, nom, longitude, latitude) VALUES
                                                      (1, 'Stade Central', 2.35, 48.85),
                                                      (2, 'Gymnase Nord', 2.36, 48.86);

-- Insertion des sports
INSERT INTO Sport (code, nom) VALUES
                                  (1, 'Football'),
                                  (2, 'Basketball');

-- Insertion des journées
INSERT INTO Journee (jour, mois, annee) VALUES
                                            (6, 6, 2025),
                                            (7, 6, 2025);
SET @idJournee1 = 1;
SET @idJournee2 = 2;

-- Insertion des compétences
INSERT INTO Competence (intitule) VALUES
                                      ('PSE1'), ('PSE2'), ('CE'), ('CP'), ('CO'), ('SSA'),
                                      ('VPSP'), ('PBC'), ('PBF');

-- Insertion des nécessités
INSERT INTO Necessite VALUES
                          ('PSE1', 'PSE2'),
                          ('PSE2', 'CE'),
                          ('CE', 'CP'),
                          ('CP', 'CO'),
                          ('SSA', 'PSE1'),
                          ('VPSP', 'PSE2'),
                          ('PBF', 'PBC');

-- Insertion des utilisateurs
INSERT INTO User (login, password, role) VALUES
                                             ('alice@example.com', 'alice', 'rescuer'),
                                             ('bob@example.com', 'bob', 'rescuer'),
                                             ('carol@example.com', 'carol', 'admin'),
                                             ('dave@example.com', 'dave', 'rescuer'),
                                             ('emma@example.com', 'emma', 'rescuer'),
                                             ('admin@admin.com', 'admin', 'admin');
SET @idAlice = 1;
SET @idBob = 2;
SET @idCarol = 3;
SET @idDave = 4;
SET @idEmma = 5;
SET @idAdmin = 6;

-- Insertion des secouristes
INSERT INTO Secouriste VALUES
                           (@idAlice, 'Dupont', 'Alice', '1990-01-01', '0123456789', '123 rue A'),
                           (@idBob, 'Martin', 'Bob', '1985-02-02', '0123456789', '456 rue B'),
                           (@idDave, 'Durand', 'Dave', '1995-03-03', '0123456789', '789 rue C'),
                           (@idEmma, 'Bernard', 'Emma', '1992-04-04', '0123456789', '101 rue D');

-- Insertion des administrateurs
INSERT INTO Administrateur VALUES
                               (@idCarol, 'Lemoine', 'Carol', '1980-05-05', '0123456789', '202 rue E'),
                               (@idAdmin, 'Admin', 'Admin', '1970-01-01', '0123456789', '303 rue F');

-- Insertion des DPS
INSERT INTO DPS VALUES
                    (10, 'DPS Matin', 8, 12, 1, 1, @idJournee1),
                    (20, 'DPS Après-midi', 14, 18, 1, 1, @idJournee1),
                    (30, 'DPS Soir', 18, 22, 2, 2, @idJournee2);

-- Insertion des besoins
INSERT INTO Besoin (dps, competence) VALUES
                                         (10, 'PSE1'), (10, 'CE'), (10, 'CP'), (10, 'CO'),
                                         (20, 'PSE2'), (20, 'SSA'), (20, 'VPSP'),
                                         (30, 'PSE1'), (30, 'PSE2'), (30, 'PBF');

-- Insertion des disponibilités
INSERT INTO Disponibilite VALUES
                              (@idAlice, @idJournee1),
                              (@idBob, @idJournee1),
                              (@idDave, @idJournee1),
                              (@idEmma, @idJournee1),
                              (@idEmma, @idJournee2);

-- Insertion des possessions
-- Alice
INSERT INTO Possession VALUES (@idAlice, 'PSE1'), (@idAlice, 'CE'), (@idAlice, 'CP');

-- Bob
INSERT INTO Possession VALUES (@idBob, 'PSE2'), (@idBob, 'CO'), (@idBob, 'PBC'), (@idBob, 'PSE1');

-- Dave
INSERT INTO Possession VALUES (@idDave, 'PSE1'), (@idDave, 'PSE2'), (@idDave, 'SSA'), (@idDave, 'CO');

-- Emma
INSERT INTO Possession VALUES (@idEmma, 'PSE1'), (@idEmma, 'PSE2'), (@idEmma, 'PBF');
