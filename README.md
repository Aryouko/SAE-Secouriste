# SAE-Secouriste



```
-Dglass.win.uiScale=1 --module-path "D:\home\BUT\S2\Secouriste\SAE-Secouriste\lib\javafx\lib" --add-modules javafx.controls,javafx.fxml,javafx.web --add-exports=javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED --add-exports=javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED --add-exports=javafx.graphics/com.sun.javafx.util=ALL-UNNAMED --add-exports=javafx.base/com.sun.javafx.logging=ALL-UNNAMED --add-exports=javafx.graphics/com.sun.prism=ALL-UNNAMED --add-exports=javafx.graphics/com.sun.glass.ui=ALL-UNNAMED --add-exports=javafx.graphics/com.sun.javafx.geom.transform=ALL-UNNAMED
```


# TO-DO


- Thomas:
  - graphes (tests + rapport)
  - switch pour choisir la stratégie d'affectation (exhaustive ou gloutonne)
  - Vue mensuelle calendrier ( + keybinds pour le calendrier)
  - Peaufiner Map
  - font global
  - Pop up settings
  - btn setting à switch

si on a le temps:
  - faire des keybinds pour les controllers
  - rendre évident ce qui est cliquable ou pas avec deso onHover sur les boutons etc

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





  

# DAO :

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


# README GRAPHE CELIAN

Ok l'équipe j'explique tout ce que je fais comme ca je m'en souviens
et on comprend par derriere. je suis parti sur une structure claire et pas un truc de gros golmon

1)  Ok Deja je commence par la creation des methodes dans le Dao Necessite, Je fait les methodes
    en Anglais Necessite = SkillNeeded, Dans les methodes Créées :

    - findAll() Le plus Important qui renvoi tt la table que je vais pouvoir utilisé dans la création de graph

2)  Maintenant j'ai besoin de transformer ma Liste de Necessite en Matrice. J
    Je vais donc modifier mon fichier MatrixUtils qui contient les methodes de base des matrices.
    Simplement la creation de la matrice pour le moment

    -private Map<Competence, Integer> MapSkillToHisInt ;  Un attribut qui va permettre de savoir quel nombre est attribue a quel competence. Elle est initialisé dans la premiere methode convertToListInt pour etre correctement sur d'associé les bonnes valeurs.

    - private Map<Integer, Integer[]> convertToListInt(List<Necessite> elements) ; Convertit une liste de Necessite en un dictionnaire avec une competence d'un coté (key) et toutes les competences vers qui elle est relié (values), mais init
    - public static int[][] createAdjacencyMatrix(List<Necessite> elements) Tu lis le nom de la fonction espece de golmon
    - matrixToString pour afficher la matrice mais jsp Override

    / J'ai fait des tests d'affichage dans BasicGraphFunction


3) Dans GraphAlgorithms

    - isOriented() verif si la matrice est orienté


