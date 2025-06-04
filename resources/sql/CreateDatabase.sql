-- Création de la base
CREATE DATABASE sae_db;
USE sae_db;

-- Suppression des tables si elles existent déjà
DROP TABLE IF EXISTS Site;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Secouriste;

-- Table des sites
CREATE TABLE Site (
    code INTEGER PRIMARY KEY,
    nom VARCHAR(64) NOT NULL,
    longitude FLOAT,
    latitude FLOAT,
    CONSTRAINT PRIMARY KEY(code)
);

-- Creation User pour
CREATE TABLE user (
    idUser INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Table des secouristes
CREATE TABLE Secouriste (
    id INTEGER PRIMARY KEY,
    nom VARCHAR(32) NOT NULL,
    prenom VARCHAR(32) NOT NULL,
    date_naissance VARCHAR(32),
    email VARCHAR(64),
    tel VARCHAR(10),
    adresse VARCHAR(64),
    CONSTRAINT PRIMARY KEY(id),
    CONSTRAINT FOREIGN KEY (id) REFERENCES user(idUser),
);

-- Table des sports
CREATE TABLE Sport (
    code INTEGER,
    nom VARCHAR(64) NOT NULL,
    CONSTRAINT PRIMARY KEY(code)
);

-- Table des journées
CREATE TABLE Journee (
    jour INT NOT NULL,
    mois INT NOT NULL,
    annee INT NOT NULL,
    CONSTRAINT PRIMARY KEY(jour, mois, annee)
);



-- Table des compétences
CREATE TABLE Competence (
    intitule VARCHAR(64),
    CONSTRAINT PRIMARY KEY
);

-- Table des DPS
CREATE TABLE DPS (
    id INTEGER,
    name VARCHAR(100) NOT NULL,
    horaire_depart INTEGER,
    horaire_fin INTEGER,
    site_code INTEGER,
    sport_code INTEGER,
    journee_id INTEGER,
    CONSTRAINT PRIMARY KEY(id),
    CONSTRAINT FOREIGN KEY (site_code) REFERENCES site(code),
    CONSTRAINT FOREIGN KEY (sport_code) REFERENCES sport(code),
    CONSTRAINT FOREIGN KEY (journee_id) REFERENCES journee(id)
);

-- Table des besoins (liée à DPS)
CREATE TABLE Besoin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dps_id BIGINT,
    nombre INT,
    FOREIGN KEY (dps_id) REFERENCES dps(id)
);

-- Table des disponibilités
CREATE TABLE Disponibilite (
    secouristeDisp INTEGER,
    journeeDisp INTEGER,
    CONSTRAINT PRIMARY KEY(secouristeDisp, journeeDisp)
    FOREIGN KEY (secouristeDisp) REFERENCES Secouriste(id),
    FOREIGN KEY (journeeDisp) REFERENCES Journee(id)
);

-- Table des possessions (compétences d'un secouriste)
CREATE TABLE Possession (
    secouriste_id BIGINT,
    competence_intitule VARCHAR(100),
    PRIMARY KEY (secouriste_id, competence_intitule),
    FOREIGN KEY (secouriste_id) REFERENCES secouriste(idS),
    FOREIGN KEY (competence_intitule) REFERENCES competence(intitule)
);

-- Table des nécessités (compétence nécessite une autre)
CREATE TABLE Necessite (
    comp1 VARCHAR(100),
    comp2 VARCHAR(100),
    PRIMARY KEY (comp1, comp2),
    FOREIGN KEY (comp1) REFERENCES competence(intitule),
    FOREIGN KEY (comp2) REFERENCES competence(intitule)
);

