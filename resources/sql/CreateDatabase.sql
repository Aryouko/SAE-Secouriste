-- Création de la base
CREATE DATABASE sae_db;
USE sae_db;

-- Suppression des tables si elles existent déjà
DROP TABLE IF EXISTS site;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS secouriste;

-- Table des sites
CREATE TABLE site (
    code VARCHAR(20) PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    longitude FLOAT,
    latitude FLOAT
);

-- Creation User pour
CREATE TABLE user (
    idUser INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Table des secouristes
CREATE TABLE secouriste (
    idS BIGINT PRIMARY KEY, FOREIGN KEY (idS) REFERENCES user(idUser),
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    date_naissance VARCHAR(20),
    email VARCHAR(100),
    tel VARCHAR(20)
);

-- Table des sports
CREATE TABLE sport (
    code BIGINT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

-- Table des journées
CREATE TABLE journee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    jour INT NOT NULL,
    mois INT NOT NULL,
    annee INT NOT NULL
);



-- Table des compétences
CREATE TABLE competence (
    intitule VARCHAR(100) PRIMARY KEY
);

-- Table des DPS
CREATE TABLE dps (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    horaire_depart INT,
    horaire_fin INT,
    site_code VARCHAR(20),
    sport_code BIGINT,
    journee_id INT,
    FOREIGN KEY (site_code) REFERENCES site(code),
    FOREIGN KEY (sport_code) REFERENCES sport(code),
    FOREIGN KEY (journee_id) REFERENCES journee(id)
);

-- Table des besoins (liée à DPS)
CREATE TABLE besoin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dps_id BIGINT,
    nombre INT,
    FOREIGN KEY (dps_id) REFERENCES dps(id)
);

-- Table des disponibilités
CREATE TABLE disponibilite (
    id INT AUTO_INCREMENT PRIMARY KEY,
    secouriste_id BIGINT,
    journee_id INT,
    FOREIGN KEY (secouriste_id) REFERENCES secouriste(idS),
    FOREIGN KEY (journee_id) REFERENCES journee(id)
);

-- Table des possessions (compétences d'un secouriste)
CREATE TABLE possession (
    secouriste_id BIGINT,
    competence_intitule VARCHAR(100),
    PRIMARY KEY (secouriste_id, competence_intitule),
    FOREIGN KEY (secouriste_id) REFERENCES secouriste(idS),
    FOREIGN KEY (competence_intitule) REFERENCES competence(intitule)
);

-- Table des nécessités (compétence nécessite une autre)
CREATE TABLE necessite (
    comp1 VARCHAR(100),
    comp2 VARCHAR(100),
    PRIMARY KEY (comp1, comp2),
    FOREIGN KEY (comp1) REFERENCES competence(intitule),
    FOREIGN KEY (comp2) REFERENCES competence(intitule)
);

