package fr.softsf.sudokufx.utils.sudoku;

import fr.softsf.sudokufx.interfaces.IGridMaster;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Provides essential functionalities for generating and solving Sudoku puzzles.
 */
@Component
public class GridMaster implements IGridMaster {

    private static final int ORDRE = 3;
    private static final int DIMENSION = ORDRE * ORDRE;
    private static final int NOMBRE_CASES = DIMENSION * DIMENSION;
    private static final String CHIFFRES = "0123456789";
    // Nombre de cases à cacher en fonction du niveau
    private static final int facileMinCachees = 35;
    private static final int moyenMinCachees = 45;
    private static final int moyenMaxCachees = 49;
    private static final int difficileMaxCachees = 59;
    // Difficulté (0 à 41391) de la grille en fonction du niveau
    private static final int moyenMinDifficulte = 13797;
    private static final int moyenMaxDifficulte = 27594;

    /**
     * Générateur de grilles de Sudoku.
     */
    public GridMaster() {
    }

    /**
     * Génère une grille de Sudoku à résoudre en cachant des cases selon le niveau de difficulté.
     *
     * @param niveau          Le niveau de difficulté (1 : facile, 2 : moyen, 3 : difficile).
     * @param grilleResolue   La grille résolue à partir de laquelle les cases seront cachées.
     * @param grilleAResoudre La grille à résoudre, avec ses cases cachées (valeur à zéro).
     * @return La difficulté dans la grille à résoudre (somme des possibilités restantes).
     * Retourne le niveau facile pour toutes autres valeurs que 2 ou 3.
     */
    private static int genererLaGrilleAResoudre(final int niveau, final int[] grilleResolue, final int[] grilleAResoudre) {
        int sommeDesPossibilites;
        return switch (niveau) {
            case 2 -> {
                int nombreDeCasesACacher = nombreAleatoire(moyenMinCachees, moyenMaxCachees);
                do {
                    // copier la grilleResolue
                    System.arraycopy(grilleResolue, 0, grilleAResoudre, 0, NOMBRE_CASES);
                    // cacher les cases
                    cacherLesCases(nombreDeCasesACacher, grilleAResoudre);
                    // récupérer les possibilités
                    int[] possibilites = getPossibilites(grilleAResoudre);
                    sommeDesPossibilites = SommeDesPossibilitesDeLaGrille(possibilites);
                } while (sommeDesPossibilites < moyenMinDifficulte || sommeDesPossibilites > moyenMaxDifficulte);
                yield sommeDesPossibilites;
            }
            case 3 -> {
                int nombreDeCasesACacher = nombreAleatoire(moyenMaxCachees, difficileMaxCachees);
                do {
                    // Copier la grilleResolue
                    System.arraycopy(grilleResolue, 0, grilleAResoudre, 0, NOMBRE_CASES);
                    // Cacher les cases
                    cacherLesCases(nombreDeCasesACacher, grilleAResoudre);
                    // Récupérer la somme des possibilités
                    sommeDesPossibilites = SommeDesPossibilitesDeLaGrille(getPossibilites(grilleAResoudre));
                } while (sommeDesPossibilites < moyenMaxDifficulte);
                yield sommeDesPossibilites;
            }
            default -> {
                int nombreDeCasesACacher = nombreAleatoire(facileMinCachees, moyenMinCachees);
                do {
                    // Copier la grilleResolue
                    System.arraycopy(grilleResolue, 0, grilleAResoudre, 0, NOMBRE_CASES);
                    // Cacher les cases
                    cacherLesCases(nombreDeCasesACacher, grilleAResoudre);
                    // Récupérer la somme des possibilités
                    sommeDesPossibilites = SommeDesPossibilitesDeLaGrille(getPossibilites(grilleAResoudre));
                } while (sommeDesPossibilites > moyenMinDifficulte);
                yield sommeDesPossibilites;
            }
        };
    }

    /**
     * Cache un nombre spécifié de cases dans la grille à résoudre en les remplaçant par zéro.
     *
     * @param nombreDeCasesACacher Le nombre de cases à cacher dans la grille (au maximum 81 cases).
     * @param grilleAResoudre      Le tableau représentant la grille à résoudre, où les cases seront cachées.
     */
    private static void cacherLesCases(int nombreDeCasesACacher, final int[] grilleAResoudre) {
        Set<Integer> set = new HashSet<>();
        Random random = new Random();
        nombreDeCasesACacher = Math.min(nombreDeCasesACacher, NOMBRE_CASES);
        while (set.size() < nombreDeCasesACacher) {
            int valeur = random.nextInt(NOMBRE_CASES);
            if (set.add(valeur)) {
                grilleAResoudre[valeur] = 0;
            }
        }
    }

    /**
     * Génère un nombre entier aléatoire dans un intervalle spécifié.
     *
     * @param minInclus La valeur minimale incluse du nombre aléatoire à générer.
     * @param maxExclus La valeur maximale exclue du nombre aléatoire à générer.
     * @return Un entier aléatoire compris entre minInclus (inclus) et maxExclus (exclus).
     */
    private static int nombreAleatoire(final int minInclus, final int maxExclus) {
        return new Random().nextInt(minInclus, maxExclus);
    }

    /**
     * Calcule la somme des possibilités de toutes les cases de la grille de Sudoku.
     *
     * @param possibilites Tableau des possibilités par case (511 = toutes possibilités).
     * @return La somme des possibilités.
     */
    private static int SommeDesPossibilitesDeLaGrille(final int[] possibilites) {
        int somme = 0;
        for (int possibilite : possibilites) {
            somme += possibilite;
        }
        return somme;
    }

    /**
     * Calcule les possibilités pour chaque case de la grille de Sudoku.
     *
     * @param grille La grille de Sudoku actuelle.
     * @return Tableau des possibilités par case (511 = toutes possibilités).
     */
    private static int[] getPossibilites(final int[] grille) {
        int[] possibilites = new int[NOMBRE_CASES];
        initialiserPossibilites(grille, possibilites);
        return possibilites;
    }

    /**
     * Initialise le tableau des possibilités pour chaque case.
     *
     * @param grille       Grille Sudoku actuelle.
     * @param possibilites Tableau à initialiser avec les possibilités.
     */
    private static void initialiserPossibilites(final int[] grille, final int[] possibilites) {
        // Chaque possibilité est de 1 à 9, soit 511 ou en binaire (111111111)
        Arrays.fill(possibilites, (1 << DIMENSION) - 1);
        // Élimine les possibilités basées sur les valeurs existantes dans la grille
        for (int ligne = 0; ligne < DIMENSION; ligne++) {
            for (int colonne = 0; colonne < DIMENSION; colonne++) {
                int valeur = grille[ligne * DIMENSION + colonne];
                if (valeur != 0) eliminerPossibilite(possibilites, ligne, colonne, valeur);
            }
        }
    }

    /**
     * Élimine la valeur des possibilités des cases alentours.
     *
     * @param possibilites Tableau des possibilités.
     * @param ligne        Ligne de la case.
     * @param colonne      Colonne de la case.
     * @param valeur       Valeur à éliminer.
     */
    private static void eliminerPossibilite(final int[] possibilites, final int ligne, final int colonne, final int valeur) {
        int masque = ~(1 << (valeur - 1));
        int sauvegarde = possibilites[ligne * DIMENSION + colonne];
        // Élimination de la valeur sur la ligne
        for (int i = 0; i < DIMENSION; i++) {
            possibilites[ligne * DIMENSION + i] &= masque;
        }
        // Élimination de la valeur sur la colonne
        for (int i = 0; i < DIMENSION; i++) {
            possibilites[i * DIMENSION + colonne] &= masque;
        }
        // Élimination de la valeur dans le carré
        int debutX = (colonne / ORDRE) * ORDRE;
        int debutY = (ligne / ORDRE) * ORDRE;
        for (int i = 0; i < ORDRE; i++) {
            for (int j = 0; j < ORDRE; j++) {
                possibilites[(debutY + i) * DIMENSION + (debutX + j)] &= masque;
            }
        }
        // Restaure les possibilités originales de la case
        possibilites[ligne * DIMENSION + colonne] = sauvegarde;
    }

    /**
     * Remplit récursivement la grille Sudoku (backtracking).
     *
     * @param grille       Grille Sudoku à remplir.
     * @param possibilites Tableau des possibilités pour chaque case.
     * @return true si la grille est remplie avec succès, false sinon.
     */
    private static boolean remplirLaGrille(final int[] grille, final int[] possibilites) {
        int index = laCaseVideAvecLeMoinsDePossibilites(grille, possibilites);
        if (index < 0) return true;
        int possibilitesDeLaCase = possibilites[index];
        while (possibilitesDeLaCase != 0) {
            // Choisit une valeur aléatoire parmi les possibilités restantes
            int valeur = choisirValeurAleatoire(possibilitesDeLaCase);
            // Retire cette valeur des possibilités de la case
            possibilitesDeLaCase &= ~(1 << (valeur - 1));
            // Place la valeur dans la grille
            grille[index] = valeur;
            // Crée une copie des possibilités pour la récursion
            int[] nouvellesPossibilites = Arrays.copyOf(possibilites, NOMBRE_CASES);
            // Élimine la valeur choisie des possibilités des cases affectées
            eliminerPossibilite(nouvellesPossibilites, index / DIMENSION, index % DIMENSION, valeur);
            // Appel récursif pour remplir le reste de la grille
            if (remplirLaGrille(grille, nouvellesPossibilites)) return true;
        }
        // Si aucune valeur ne fonctionne, réinitialise la case et retourne false
        grille[index] = 0;
        return false;
    }

    /**
     * Vérifie la cohérence entre la grille et les possibilités.
     * <p>
     * Cette méthode s'assure que chaque valeur non nulle dans la grille
     * est bien présente dans les possibilités correspondantes.
     *
     * @param grille       La grille Sudoku actuelle.
     * @param possibilites Le tableau des possibilités pour chaque case.
     * @return 0 si la vérification est réussie, -1 si une incohérence est détectée.
     */
    private static int verifierLaCoherenceEntreLaGrilleEtLesPossibilites(final int[] grille, final int[] possibilites) {
        for (int i = 0; i < NOMBRE_CASES; i++) {
            int valeur = grille[i];
            if (valeur != 0 && (possibilites[i] & (1 << (valeur - 1))) == 0) {
                return -1; // Incohérence détectée
            }
        }
        return 0; // Vérification réussie
    }

    /**
     * Trouve la case vide avec le moins de possibilités.
     *
     * @param grille       Grille Sudoku actuelle.
     * @param possibilites Tableau des possibilités.
     * @return L'index de la case trouvée, ou -1 si la grille est complète.
     */
    private static int laCaseVideAvecLeMoinsDePossibilites(final int[] grille, final int[] possibilites) {
        int meilleurIndex = -1;
        int meilleurScore = Integer.MAX_VALUE;
        for (int i = 0; i < NOMBRE_CASES; i++) {
            // case vide
            if (grille[i] == 0) {
                // compte le nombre de bits à 1 (possibilités restantes)
                int score = compterBits(possibilites[i]);
                // mise à jour si la case moins de possibilités
                if (score < meilleurScore) {
                    meilleurIndex = i;
                    meilleurScore = score;
                }
            }
        }
        return meilleurIndex;
    }

    /**
     * Choisit une valeur aléatoire parmi les possibilités données.
     *
     * @param possibilitesDeLaCase Entier représentant les valeurs possibles.
     * @return Une valeur choisie aléatoirement.
     */
    private static int choisirValeurAleatoire(final int possibilitesDeLaCase) {
        int nombrePossibilites = compterBits(possibilitesDeLaCase);
        // Génère un index aléatoire parmi les possibilités
        int choix = new Random().nextInt(nombrePossibilites);
        // Parcourt les bits de possibilitesDeLaCase
        for (int i = 0; i < DIMENSION; i++) {
            // Vérifie si le bit i est à 1 (donc si i+1 est une possibilité)
            if ((possibilitesDeLaCase & (1 << i)) != 0) {
                if (choix == 0) return i + 1;
                choix--;
            }
        }
        return 0;
    }

    /**
     * Compte le nombre de bits à 1 dans un entier.
     *
     * @param x Entier à analyser.
     * @return Le nombre de bits à 1.
     */
    private static int compterBits(final int x) {
        return Integer.bitCount(x);
    }

    @Override
    public final int resoudreLaGrille(final int[] grille) {
        int[] possibilites = getPossibilites(grille);
        remplirLaGrille(grille, possibilites);
        return verifierLaCoherenceEntreLaGrilleEtLesPossibilites(grille, possibilites);
    }

    @Override
    public final int[][] creerLesGrilles(final int niveau) {
        // Initialiser la grille résolue
        int[] grilleResolue = new int[NOMBRE_CASES];
        // Résoudre la grille
        resoudreLaGrille(grilleResolue);
        // Initialiser la grille à résoudre
        int[] grilleAResoudre = new int[NOMBRE_CASES];
        // En fonction du niveau, cacher un certain nombre de cases et tenir compte de la difficulté
        int sommeDesPossibilites = genererLaGrilleAResoudre(niveau, grilleResolue, grilleAResoudre);
        // le pourcentage de difficulté estimé
        int pourcentageDeDifficulte = ((sommeDesPossibilites - 4900) * 100) / (39500 - 4900);
        // Limiter le pourcentage entre 0 et 100
        pourcentageDeDifficulte = pourcentageDeDifficulte < 0 ? 0 : Math.min(pourcentageDeDifficulte, 100);
        return new int[][]{grilleResolue, grilleAResoudre, new int[]{pourcentageDeDifficulte}};
    }

}

