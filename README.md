# MovieApp
Application mobile réalisé sous Kotlin utilisant l'API TMDB pour lister et gérer des films.

## Aperçu
4 vues disponibles:
- Vue Home: liste les films populaires du moment
- Vue Detail: affiche le détail de chaque film avec son poster, sa note moyenne ainsi que son synopsis. On peut aussi l'ajouter ou le retirer des favoris.
- Vue Favoris: liste les films ajoutés en tant que favoris, on peut les supprimer et la liste se remet à jour après.
- Vue Recherche: permet de rechercher un film en écrivant dans la barre de recherche un préfixe ou tout le titre.

## A faire
- [ ] Optimiser la recherche en affichant la liste des films en temps réel lors de l'input du titre.
- [ ] Créer une partie Auth avec vue Profil pour se connecter à l'application via un utilisateur.
- [ ] Essayer de réimplenter Room/KSP pour la mise en place d'une BDD suite aux problèmes de compatibilité.
