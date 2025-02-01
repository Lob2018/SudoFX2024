package fr.softsf.sudokufx.interfaces;

public interface IGridMaster {
    /**
     * Crée les grilles de Sudoku (résolue et à résoudre) en fonction du niveau de difficulté.
     * Facile :
     * 35-45 cases cachées, et difficulté de 0 à 13797
     * Moyen :
     * 45-39 cases cachées, et difficulté de 13797 à 27594
     * Difficile :
     * 49 à 59 cases cachées, et difficulté de 27594 à 41391
     *
     * @param niveau Le niveau de difficulté (1 : facile, 2 : moyen, 3 : difficile).
     * @return Un tableau à trois dimensions contenant :
     * La grille résolue.
     * La grille à résoudre avec les cases cachées.
     * Le pourcentage de difficulté de la grille à résoudre (la plage retenue va de 4900 0% à 39500 100%).
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

    /**
     * Affiche la grille Sudoku sous forme de texte dans la console.
     *
     * @param grille Grille Sudoku à afficher.
     */
    void afficherLaGrilleDansLaConsole(final int[] grille);
}
