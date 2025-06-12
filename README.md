# SAE-Secouriste




# TO-DO

- IHM
    - Ajouter un bouton pour ajouter un secouriste
    - Ajouter un bouton pour supprimer un secouriste
- POO
- DAO
    - Ajouter un DAO pour les dispositifs
    - Ajouter un DAO pour les jours


DAO :

A quoi ca sert :
Effectue les opérations CRUD (Create, Read, Update, Delete)

Comment j'ai realise ce DAO :

    - ConnexionDao  Classe utilitaire unique pour gérer la connexion JDBC.           !!!!!! modifier l"url de connexion

    - SecouristeDao  Une classe de gestion des informations secouriste

    - CompetenceDao     Les competences

    Fichier	Rôle	Obligatoire
    ConnexionBDD.java	Gérer la connexion JDBC	✅
    SecouristeDAO.java	CRUD pour les secouristes	✅
    DPSDAO.java	CRUD pour les dispositifs	✅
    JourneeDAO.java	CRUD pour les jours	✅
    DisponibiliteDAO.java	CRUD pour les disponibilités	✅
    SportDAO.java	CRUD pour les sports (si nécessaires)	✅
    DAOFactory.java	Fournir les DAO

jure ça marche