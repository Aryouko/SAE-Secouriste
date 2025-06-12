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

INSERT INTO Competence (intitule) VALUES ('PSE1');
INSERT INTO Competence (intitule) VALUES ('PSE2');
INSERT INTO Competence (intitule) VALUES ('CE');
INSERT INTO Competence (intitule) VALUES ('CP');
INSERT INTO Competence (intitule) VALUES ('CO');
INSERT INTO Competence (intitule) VALUES ('SSA');
INSERT INTO Competence (intitule) VALUES ('VPSP');
INSERT INTO Competence (intitule) VALUES ('PBC');
INSERT INTO Competence (intitule) VALUES ('PBF');

INSERT INTO Necessite VALUES ('PSE1', 'PSE2');
INSERT INTO Necessite VALUES ('PSE2', 'CE');
INSERT INTO Necessite VALUES ('CE', 'CP');
INSERT INTO Necessite VALUES ('CP', 'CO');
INSERT INTO Necessite VALUES ('SSA', 'PSE1');
INSERT INTO Necessite VALUES ('VPSP', 'PSE2');
INSERT INTO Necessite VALUES ('PBF', 'PBC');

-- Disponibilités des secouristes
INSERT INTO Disponibilite VALUES (@idAlice, @idJournee);
INSERT INTO Disponibilite VALUES (@idBob, @idJournee);
INSERT INTO Disponibilite VALUES (@idDave, @idJournee);

-- Compétences possédées par les secouristes
-- Alice
INSERT INTO Possession VALUES (@idAlice, 'PSE1');
INSERT INTO Possession VALUES (@idAlice, 'SSA');

-- Bob
INSERT INTO Possession VALUES (@idBob, 'PSE2');
INSERT INTO Possession VALUES (@idBob, 'PSE1');
INSERT INTO Possession VALUES (@idBob, 'PBC');

-- Dave
INSERT INTO Possession VALUES (@idDave, 'PSE1');
INSERT INTO Possession VALUES (@idDave, 'PSE2');
INSERT INTO Possession VALUES (@idDave, 'CE');
INSERT INTO Possession VALUES (@idDave, 'CP');
INSERT INTO Possession VALUES (@idDave, 'CO');

-- Besoins pour les DPS
INSERT INTO Besoin (dps, competence) VALUES (10, 'PSE1');
INSERT INTO Besoin (dps, competence) VALUES (10, 'CE');
INSERT INTO Besoin (dps, competence) VALUES (20, 'PSE2');
INSERT INTO Besoin (dps, competence) VALUES (20, 'SSA');
INSERT INTO Besoin (dps, competence) VALUES (20, 'VPSP');

-- Ajouter un 2e site et une journée supplémentaire
INSERT INTO Site (code, nom, longitude, latitude) VALUES (2, 'Gymnase Nord', 2.36, 48.86);
INSERT INTO Sport (code, nom) VALUES (2, 'Basketball');
INSERT INTO Journee (jour, mois, annee) VALUES (7, 6, 2025);
SET @idJournee2 = LAST_INSERT_ID();

-- Nouveau DPS sur la nouvelle journée
INSERT INTO DPS VALUES (30, 'DPS Soir', 18, 22, 2, 2, @idJournee2);

-- Ajouter des besoins pour ce DPS
INSERT INTO Besoin (dps, competence) VALUES (30, 'PSE1');
INSERT INTO Besoin (dps, competence) VALUES (30, 'PSE2');
INSERT INTO Besoin (dps, competence) VALUES (30, 'PBF');

-- Ajouter un nouveau user et secouriste
INSERT INTO User (login, password, role) VALUES ('emma@example.com', 'emma', 'rescuer');
SET @idEmma = LAST_INSERT_ID();
INSERT INTO Secouriste VALUES (@idEmma, 'Bernard', 'Emma', '020202la', '04040404', 'ailleurs');

-- Compétences d’Emma
INSERT INTO Possession VALUES (@idEmma, 'PSE1');
INSERT INTO Possession VALUES (@idEmma, 'PSE2');
INSERT INTO Possession VALUES (@idEmma, 'PBF');
INSERT INTO Possession VALUES (@idEmma, 'PBC');

-- Disponibilités
INSERT INTO Disponibilite VALUES (@idEmma, @idJournee2);
INSERT INTO Disponibilite VALUES (@idEmma, @idJournee); -- dispo sur les deux jours

-- Plus de besoins pour tester les combinaisons
INSERT INTO Besoin (dps, competence) VALUES (10, 'CP');
INSERT INTO Besoin (dps, competence) VALUES (10, 'CO');
INSERT INTO Besoin (dps, competence) VALUES (10, 'PSE1');
