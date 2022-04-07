## Principe du projet

Suite de la première partie du PFR, qui consistait en un développement de moteur de recherche en c, avec la possibilité d'effectuer des recherches textes, audios, et images.
Ici, nous irons plus loin (deuxième partie oblige) en développant différentes requêtes complexes, une interface, un historique et différents modes de recherches (multi-moteur..) le tout en JAVA!!! (ce qui inclus l'intégration de notre précédent code en C dans ce projet JAVA)

## Structure du code

Notre projet contient différents dossiers et sous dossier, tel que:

- `src`: contient les sources
- - `controleur`: contient les controleurs
- - `entite`: contient les classes métiers
- - `vue`: contient les vues (boundary)
- `lib`: contient les librairies utilisées
- `bin`: contient les fichiers compilés sortant générés par défaut

## Organisation de l'équipe

Chaques tâches dépends d'un membre : 
- `Gestion rôle Administrateur`: DESCRIPTION : Mehdi NOEL & Simon BERLANGA
- `Gestion rôle Administrateur`: DESCRIPTION : Mehdi NOEL & Simon BERLANGA
- `Gestion mode ouvert`: permet d'avoir l'intersection des résultats de recherche entre deux moteurs : Anna OLFI
- `Gestion recherche ouverte`: actualise l'indexation des fichiers toutes les x secondes : Anna OLFI
- `Gestion recherches complexes audio`: recherche avec occurrences et polarités : Adélie TRICARD
- `Gestion recherches complexes image`: recherche avec seuils de couleur et polarités : Adélie TRICARD
- `Gestion recherches complexes texte`: recherche avec occurrences et polarités : Adélie TRICARD
- `Gestion de l'interface`: DESCRIPTION : Noé MARZAT
- `Gestion de JNA`: DESCRIPTION : Simon BERLANGA
- `Gestion de JNI`: DESCRIPTION : Adélie TRICARD
