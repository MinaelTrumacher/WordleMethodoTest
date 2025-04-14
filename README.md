# 🎮 Wordle Java - Projet Méthodo Test

Bienvenue dans **Wordle Java**, une version personnalisée du célèbre jeu de lettres, développée avec Java (Swing) dans le cadre d'un projet de méthodologie de tests.

---

## 🧩 Fonctionnalités

- ✅ Interface graphique (Swing)
- ✅ Trois modes de difficulté :
    - **Facile** : mot de 5 lettres, pas de timer
    - **Normal** : mot de 5 à 8 lettres, timer de 30 minutes
    - **Difficile** : mot allemand long, timer de 15 minutes
- ✅ Feedback visuel couleur (vert, jaune, rouge)
- ✅ Système de **score avancé** :
    - +1 point par lettre bien placée (verte)
    - −1 point par lettre mal placée (jaune)
    - −2 points par lettre absente (rouge)
    - −2 points par tentative après la première
- ✅ Statistiques persistantes :
    - Nombre de victoires / défaites
    - Moyenne de tentatives
    - Meilleur score enregistré
- ✅ Fichiers de sauvegarde automatique (`GameStats.txt`)
- ✅ Tests unitaires complets avec JUnit 5
- ✅ Couverture de code ≥ 90 % (via IntelliJ ou JaCoCo)

---

## 🚀 Lancer le projet

### Prérequis

- Java 17+
- IntelliJ IDEA
- IntelliJ IDEA pour visualiser l'interface et les tests

### Exécution

- Se mettre sur la classe WordleUI et cliquer sur le bouton "play"