# SAE-Secouriste




# TO-DO

- @c0w5lip: Vue mensuelle calendrier + keybinds pour le calendrier
- ?: faire des keybinds pour les controllers
- ?: rendre évident ce qui est cliquable ou pas avec deso onHover sur les boutons etc


# RENDU!

### POO :

- Le diagramme de classes correspondant au diagramme de conception de la BDD (Figure 1), accompagn´e d’une explication textuelle des diff´erents choix effectu´es (explication des m´ethodes ajout´ees, des
associations, des types des attributs lorsque ceux-ci ne sont pas ´evidents, etc.) ;
- Le diagramme de s´equence du sc´enario choisi ;
- Le code complet de la partie « mod`ele », comment´e en fran¸cais ou en anglais ;
- **_Une classe Scenario permettant d’impl´ementer et tester le sc´enario mod´elis´e par le diagramme de
  s´equence._**

### IHM :

- code source
-  fichier texte contenant les commandes de compilation et d’ex´ecution de l’application. Ces
   commandes devront placer les fichiers compil´es dans un dossier nomm´e classes et les fichiers
   sources doivent ˆetre plac´es dans un dossier nomm´e sources
- Un PDF justifiant les changements appliqu´es `a la maquette, s’il y en a
- Une courte vid´eo de d´emonstration de l’application, montrant un exemple pour chacune des
  op´erations CRUD, une d´emonstration de l’export de fichier et, ´eventuellement, une d´emonstration
  de l’affichage d’un graphique.

### Graphe :

- Le code source Java des fonctionnalit´es suivantes (comment´e) :
Verification du DAG ;
Approche exhaustive ;
Approche gloutonne ;
Int´egration dans l’application finale

- Un rapport (entre 2 et 4 pages max) comprenant :
– Une description d´etaill´ee des algorithmes utilis´es ;
– Les r´esultats des tests comparatifs avec des graphiques ou tableaux ;
– Une analyse critique des avantages et inconv´enients de chaque approche.










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
