Bienvenue sur mon TP Donjon
=================

Mini projet réalisé dans le cadre de la Conception Orienté Objet par Guillaume Leprêtre. Ce projet sera évalué par Quentin Baert.
Dans ce tp vous pouvez jouer à une partie de donjon...

----------

# Composition du tp :

- Une archive maven
- Le code source dans `/src/main`
- Les tests unitaire dans `src/test`
- La génération de la javadoc dans `/doc`
- Les différents UML dans `/UML_ressources`
- Le jar executable dans `target/COO-Donjon-1.0-SNAPSHOT.jar`

# Comment lancer le jeu :

- Télécharger l'archive à l'aide de la commande `git clone`
- Si c'est la premiere fois que vous lancer le jeu, compiler avec `mvn package`
- Lancer la commande executable `COO-Donjon-1.0-SNAPSHOT.jar` dans le dossier `target`
- Celui-ci vous montrera le donjon que vous devez parcourir
- De base, le donjon fait une taille de 5
- Vous pouvez choisir la taille du donjon en rajoutant la taille que vous voulez en option
- Par exemple si vous voulez une taille de 10 lancez `COO-Donjon-1.0-SNAPSHOT.jar 10`
- Attention, la taille doit etre `supérieur à 5 et inférieur à 20 !`

# Déroulement d'une partie :

- Une fois la commande lancé, vous pouvez apercevoir le donjon que vous devez traverser
- Vous vous trouvez tout en haut à gauche du donjon et la sortie se trouve en bas à droite
- Le but est de `sortir du donjon vivant`
- Ainsi, vous vous devez vous déplacer de salle en salle
- Cependant vous ne pouvez sortir d'une salle si un monstre est présent
- Vous devez alors `l'affronter jusqu'à sa mort`
- Vous pouvez fouiller les alentours de la salle 
- Ainsi, vous pouvez `dégoter des informations sur la salle` et `trouver des objets`
- Lorsque vous trouvez des objets, vous le `garder dans votre inventaire`
- Vous pouvez `utiliser vos objets` pour mener à bien votre expedition


# Differentes actions possibles :

## Attaquer:
- Le joueur peut attaquer des monstres
- Pour cela il faut choisir l'action `Attack a monster`
- Si le monstre attaqué n'est pas mort alors celui-ci `riposte`
- Lors d'une attaque, `un message montrant les dégât commis` apparait pendant quelques secondes

## Se déplacer:
- Le joueur peut se déplacer de salle en salle
- Pour cela il faut choisir l'action `Move to a room`
- Si un monstre est présent alors le joueur ne peux se `déplacer`

## Fouiller la salle:
- Le joueur peut fouille une salle
- Pour cela il faut choisir l'action `Look around yourself`
- Cette action permet de donner des infos sur la salle
- Le joueur peut savoir les `monstres` et les `objets` present dans la salle
- Lors de l'action `un message apparait` donc pendant quelques secondes
- Si un objet est present, `le joueur le ramasse`

## Utiliser des objets:
- Le joueur peut utiliser des objets
- Pour cela il faut choisir la method `Use an item`
- Les objets sont des potions `rare` à trouver
- Elles permettent d'améliorer un attribut du joueur
- Lors de l'utilisation `un message apparait` pendant quelques secondes



# Quelques précisions de code :

## Génération d'un donjon

J'ai décidé de générer mon donjon de façon complète et aléatoire. Tout se passe dans la methode `initializeDungeon`. Dans cette methode, j'initialise les salle où je crée des montres et des objets de façon aléatoire, puis je génère mon labyrinthe en liant les différentes salles. Pour cela, j'utilise une methode récursive `recursiveProceduration` qui fonctionne de cette façon:

- Utilisation d'une pile par la `classe Stack`
- Choisis une salle et `l'insèrer` dans la pile
- Tant que la pile n'est pas vide, `récupérer l'élement au dessus` de la pile
- Récuperer les voisins jamais visités
- Si il existe des voisins
- Choisis un au hasard et `les lier entre eux`
- Inserer le voisin dans la pile
- Sinon retirer l'élement au dessus de la pile
- Rapeler la methode jusqu'a que la condition n'est plus remplis


## Affichage du jeu

Pour l'affichage du jeu j'ai décidé de créer une classe `Menu` qui affiche la plupart des informations du jeu...
Tout d'abord, cette classe me permet de lister les différents élements que le joueur peux choisir comme les actions ou un objet qui veux utiliser...
Pour cela j'utilise une methode générique `choice` qui prends une liste<T> en paramètre...
Ensuite la classe `Menu`, me permet aussi d'afficher la salle où se situe le joueur avec les portes représentés par un `"d"` grâce à la methode `drawGame` ainsi que d'afficher les statistiques du joueur grâce à la méthode `stats`
Enfin, je peux clear la console avec `clearConsole` afin de bien afficher le jeu et éviter que le joueur se perd avec toutes les informations du jeu