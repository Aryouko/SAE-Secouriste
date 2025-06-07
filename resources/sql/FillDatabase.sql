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

-- Insertion des utilisateurs et récupération des id
INSERT INTO User (login, password, role) VALUES ('alice@example.com', 'alice', 'rescuer');
SET @idAlice = LAST_INSERT_ID();
INSERT INTO User (login, password, role) VALUES ('bob@example.com', 'bob', 'rescuer');
SET @idBob = LAST_INSERT_ID();
INSERT INTO User (login, password, role) VALUES ('carol@example.com', 'carol', 'admin');
SET @idCarol = LAST_INSERT_ID();
INSERT INTO User (login, password, role) VALUES ('dave@example.com', 'dave', 'rescuer');
SET @idDave = LAST_INSERT_ID();
INSERT INTO User (login, password, role) VALUES ('admin@admin.com', 'admin', 'admin');
SET @idAdmin = LAST_INSERT_ID();

-- Insertion des secouristes avec les bons id
INSERT INTO Secouriste VALUES (@idAlice, 'Dupont', 'Alice', '010101la', '03030303', 'labas');
INSERT INTO Secouriste VALUES (@idBob, 'Martin', 'Bob', '010101la', '03030303', 'labas');
INSERT INTO Secouriste VALUES (@idDave, 'Durand', 'Dave', '010101la', '03030303', 'labas');

-- Insertion des administrateurs
INSERT INTO Administrateur VALUES (@idCarol, 'Lemoine', 'Carol', '010101la', '03030303', 'labas');
INSERT INTO Administrateur VALUES (@idAdmin, 'Admin', 'Admin', '010101la', '03030303', 'labas');

-- Insertion d’un site
INSERT INTO Site (code, nom, longitude, latitude) VALUES (1, 'Stade Central', 2.35, 48.85);

-- Insertion d’un sport
INSERT INTO Sport (code, nom) VALUES (1, 'Football');


-- Insertion d’une journée
INSERT INTO Journee (jour, mois, annee) VALUES ( 6, 6, 2025); -- id auto-incrémenté
SET @idJournee = LAST_INSERT_ID();

-- Récupérer l’id de la journée insérée (ex : 1)
-- Insertion de DPS
INSERT INTO DPS VALUES (10, 'DPS Matin', 8, 12, 1, 1, @idJournee);
INSERT INTO DPS VALUES (20, 'DPS Après-midi', 14, 18, 1, 1, @idJournee);

INSERT INTO Competence (intitule) VALUES ('PSC1');
INSERT INTO Competence (intitule) VALUES ('PSE2');

-- On suppose que les secouristes 1 et 2, les DPS 10 et 20, et les compétences 'PSC1' et 'PSE2' existent déjà
INSERT INTO Affectation VALUES (1, 10, 'PSC1');
INSERT INTO Affectation VALUES (2, 10, 'PSE2');
INSERT INTO Affectation VALUES (1, 20, 'PSE2');
INSERT INTO Affectation VALUES (2, 20, 'PSC1');


