package fr.softsf.sudokufx.utils.sudoku;

/**
 * Interface defining methods to generate and solve Sudoku puzzles
 */
public sealed interface IGridMaster permits GridMaster {
    /**
     * Crée les grilles de Sudoku (résolue et à résoudre) en fonction du niveau de difficulté.
     * Facile :
     * 35-45 cases cachées, possibilités théoriques de 0 à 13797, et pratique de 4800 à 16533
     * Moyen :
     * 45-39 cases cachées, possibilités théoriques de 13797 à 27594, et pratique de 16533 à 28266
     * Difficile :
     * 49 à 59 cases cachées, possibilités théoriques de 27594 à 41391, et pratique de 28266 à 40000
     *
     * @param niveau Le niveau de difficulté (1 : facile, 2 : moyen, 3 : difficile).
     * @return Un tableau à trois dimensions contenant :
     * La grille résolue.
     * La grille à résoudre avec les cases cachées.
     * Le pourcentage de possibilités de la grille à résoudre (la plage retenue va de 4800 0% à 40000 100%).
     */
    int[][] creerLesGrilles(final int niveau);

    /**
     * Génère une grille de Sudoku complète à partir d'une grille vide ou partiellement remplie.
     * Processus :
     * 1. Initialise ou utilise une grille existante.
     * 2. Prépare les possibilités pour chaque case.
     * 3. Remplit récursivement la grille :
     * Trouve la case avec le moins d'options.
     * Teste chaque valeur possible.
     * Met à jour les possibilités des cases alentours.
     * Continue jusqu'à ce que la grille soit complète ou pas.
     *
     * @param grille Tableau représentant la grille de Sudoku initiale (peut être partiellement remplie).
     * @return 0 si la grille est générée avec succès et cohérente, sinon -1.
     **/
    int resoudreLaGrille(final int[] grille);
}
