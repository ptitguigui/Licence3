/*
  Guillaume Lepretre
  Christopher Caroni
*/

/* Question 1 */

longueur([],0).
longueur([_|XS],Y) :- longueur(XS,N), Y is N + 1.

/* Question 2 */

somme([],0).
somme([X|XS],Y) :- somme(XS,N), Y is N + X.

/* Question 3 */

membre(X, [Y|T]) :- X = Y.
membre(X, [Y|T]) :- X \= Y, membre(X, T).

/* Question 4 */

/* ajoute HEAD a la liste Tail et retourne dans [Head|Tail] */
ajoute_en_tete(Head, Tail, [Head| Tail]).

/* Question 5 */

ajoute_en_queue(Add,[],[Add]).
ajoute_en_queue(Add,[Head|Tail], [Head|Newtail]) :- ajoute_en_queue(Add,Tail, Newtail).

/* Question 6 */

extraire_tete_v1([Head|Tail],Head,Tail).
extraire_tete(Oldlist, Head, Tail) :- ajoute_en_tete(Head, Tail, Oldlist).

/* Question 7 */

/* on ajoute les head de la 2eme liste a la fin de la 1ere liste jusqu'à ce qu'il se vide */
concatene(Premier, [], Premier).
concatene(Premier, [Head|Tail], Res) :- ajoute_en_queue(Head, Premier, Newlist), concatene(Newlist, Tail, Res).

/* Question 8 */

/* on ajoute les head de la 1ere liste a une nouvelle, en boucle jusqu'à la liste soit vide. Elle est
alors renversé */

retourne([], Vide, Vide).
retourne([Head|Tail], Vide, Res) :- ajoute_en_tete(Head, Vide, Ajoute) , retourne(Tail, Ajoute, Res).


/* Tris sur les listes */

/* Question 9 */
/* insère un élément dans une liste à la bonne place. */

insert_trie(E, [], [E]) :- !.
insert_trie(E, [X|XS], R) :- E =< X, !, ajoute_en_tete(E, [X|XS], R).
insert_trie(E, [X|XS], [X|Y]) :- E > X, !, insert_trie(E, XS, Y).

/* Question 10 */

tri_insert([], []) :- !.
tri_insert([X | L1], R) :- tri_insert(L1, T), insert_trie(X, T, R).

/* Question 11 */
/* divise une liste en deux parties égales (à un élément près) */

divise([], [], []).
divise([X], [X], []).
divise([X1,X2|XS], [X1|Y], [X2|Z]) :- divise(XS, Y, Z).

/* Question 12 */
/* fusionne deux listes, en conservant l'ordre des éléments. */
fusion(X, [], X).
fusion(X, [Y|YS], R) :- insert_trie(Y, X, T), fusion(T, YS, R).

/* Question 13 */
/* trie une liste avec l'algorithme de tri fusion */
tri_fusion(X, L) :- divise(X, Y, Z), tri_insert(Y, YRES), tri_insert(Z, ZRES), fusion(YRES, ZRES, L).

/* Question 14 */
/* étant donné un élément et une liste produit deux listes, l'une contenant les élément plus grands et l'autre contenant les éléments plus petits. */
balance(_, [], [], []) :- !.
balance(X, [Y|YS], [Y|R1], R2) :- Y < X, !, balance(X, YS, R1, R2).
balance(X, [Y|YS], R1, [Y|R2]) :- Y >= X, !, balance(X, YS, R1, R2).

/* Question 15 */
/* trie une liste à l'aide de l'algorithme de tri rapide. */
tri_rapide([], []) :- !.
tri_rapide([X|XS], R) :- balance(X, XS, R1, R2),
                          tri_rapide(R1, LT1), tri_rapide(R2, LT2),
                          concatene(LT1, [X], RES), concatene(RES, LT2, R).

/* Opérations de base sur les ensembles */

/* Question 16 */
/* retourne vrai si et seulement si l'ensemble passé en argument est vide. */
est_vide([]).

/* Question 17 */
/* ajoute un élément à un ensemble. L'élément n'est ajouté que s'il n'est pas déjà dans l'ensemble. */
ajoute_ensemble(INS, [], [INS]) :- !.
ajoute_ensemble(INS, [INS|YS], [INS|YS]) :- !.
ajoute_ensemble(INS, [Y|YS], [Y|R]) :- ajoute_ensemble(INS, YS, R).

/* Question 18 */
/* retourne vrai si et seulement si le premier ensemble est un sous-ensemble du second. */
sous_ensemble([], _).
sous_ensemble([X|XS], R) :- membre(X, R), !, sous_ensemble(XS, R).

/* Question 19 */
/* retourne l'union de deux ensembles. */
union([], R, R) :- !.
union(R, [], R) :- !.
union([X|XS], Y, R) :- ajoute_ensemble(X, Y, Z) , union(XS, Z, R).

/* Question 20 */
/* retourne l'intersection de deux ensembles. */
intersect([], _, []) :- !.
intersect(_, [], []) :- !.
intersect([X|XS], YS, [X|R]) :- membre(X, YS), !, intersect(XS, YS, R).
intersect([_|XS], YS, R) :- intersect(XS, YS, R).

/* Question 21 */
/* retourne la différence de deux ensembles. */
diff([], _, []) :- !.
diff(_, [], []) :- !.
diff([X|XS], YS, [X|R]) :- not(membre(X, YS)), !, diff(XS, YS, R).
diff([_|XS], YS, R) :- diff(XS, YS, R).
