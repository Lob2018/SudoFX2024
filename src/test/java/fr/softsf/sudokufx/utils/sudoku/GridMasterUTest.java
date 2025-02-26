package fr.softsf.sudokufx.utils.sudoku;

import fr.softsf.sudokufx.utils.SecureRandomGenerator;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class GridMasterUTest {
    private static final SecureRandomGenerator secureRandomGenerator = new SecureRandomGenerator();
    private IGridMaster iGridMaster;
    private GridMaster gridMaster;

    private static final GridMaster gridMasterNormally = new GridMaster(secureRandomGenerator);
    private static int gridMasterNormallyLastLevel = -1;

    @BeforeEach
    void init() {
        iGridMaster = new GridMaster(secureRandomGenerator);
        gridMaster = new GridMaster(secureRandomGenerator);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -100, 300})
    void createUnknownGrids_success(int level) {
        int[][] grids = iGridMaster.creerLesGrilles(level);
        assertNotNull(grids);
        assertNotNull(grids[0]);
        assertNotNull(grids[1]);
        assertNotNull(grids[2]);
        // The resolved grid
        assertEquals(81, grids[0].length);
        int countForResolvedGrid = 0;
        for (int value : grids[0]) {
            if (value == 0) countForResolvedGrid++;
        }
        assertEquals(0, countForResolvedGrid);
        // The grid to be resolved
        assertEquals(81, grids[1].length);
        int countForToBeResolvedGrid = 0;
        for (int value : grids[1]) {
            if (value == 0) countForToBeResolvedGrid++;
        }
        assertNotEquals(0, countForToBeResolvedGrid);
        // The possibilities
        assertTrue(grids[2][0] >= 0 && grids[2][0] <= 33, "The unknown level is set as easy and must be between 0 and 33 percent of possibilities");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void createGridsQuickly_success(int level) {
        int[][] grids = iGridMaster.creerLesGrilles(level);
        assertNotNull(grids);
        assertNotNull(grids[0]);
        assertNotNull(grids[1]);
        assertNotNull(grids[2]);
        // The resolved grid
        assertEquals(81, grids[0].length);
        int countForResolvedGrid = 0;
        for (int value : grids[0]) {
            if (value == 0) countForResolvedGrid++;
        }
        assertEquals(0, countForResolvedGrid);
        // The grid to be resolved
        assertEquals(81, grids[1].length);
        int countForToBeResolvedGrid = 0;
        for (int value : grids[1]) {
            if (value == 0) countForToBeResolvedGrid++;
        }
        assertNotEquals(0, countForToBeResolvedGrid);
        // The possibilities
        assertTrue(grids[2][0] >= 0 && grids[2][0] <= 100,
                "The possibilities must be between 0 and 100 for level : " + level);
    }

    /**
     * Tests grid generation for different difficulty levels.
     * Introduces a 600ms delay when the level changes to simulate processing time.
     *
     * @param level The difficulty level.
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 1, 2, 2, 3, 3})
    void createGridsNormally_success(int level) {
        int[][] grids = gridMasterNormally.creerLesGrilles(level);
        assertNotNull(grids);
        assertNotNull(grids[0]);
        assertNotNull(grids[1]);
        assertNotNull(grids[2]);
        // The resolved grid
        assertEquals(81, grids[0].length);
        int countForResolvedGrid = 0;
        for (int value : grids[0]) {
            if (value == 0) countForResolvedGrid++;
        }
        assertEquals(0, countForResolvedGrid);
        // The grid to be resolved
        assertEquals(81, grids[1].length);
        int countForToBeResolvedGrid = 0;
        for (int value : grids[1]) {
            if (value == 0) countForToBeResolvedGrid++;
        }
        assertNotEquals(0, countForToBeResolvedGrid);
        // The possibilities
        assertTrue(grids[2][0] >= 0 && grids[2][0] <= 100,
                "The possibilities must be between 0 and 100 for level : " + level);
        if (gridMasterNormallyLastLevel != level) {
            gridMasterNormallyLastLevel = level;
            Awaitility.await()
                    .atMost(650, TimeUnit.MILLISECONDS)
                    .pollDelay(600, TimeUnit.MILLISECONDS)
                    .until(() -> true);
        }
    }

    @Test
    void resolveGrid_success() {
        int[] toBeResolvedGrid = new int[]{
                5, 3, 4, 6, 7, 8, 9, 0, 2,
                6, 7, 2, 1, 9, 5, 3, 4, 8,
                1, 9, 8, 3, 4, 2, 5, 6, 7,
                8, 5, 9, 7, 6, 1, 4, 2, 3,
                4, 2, 6, 8, 5, 3, 0, 0, 1,
                7, 1, 3, 9, 2, 4, 8, 5, 6,
                9, 6, 1, 5, 3, 7, 2, 8, 4,
                2, 8, 7, 4, 1, 9, 6, 3, 5,
                3, 4, 5, 2, 8, 6, 0, 0, 9
        };
        int result = iGridMaster.resoudreLaGrille(toBeResolvedGrid);
        assertEquals(0, result);
        assertArrayEquals(new int[]{
                5, 3, 4, 6, 7, 8, 9, 1, 2,
                6, 7, 2, 1, 9, 5, 3, 4, 8,
                1, 9, 8, 3, 4, 2, 5, 6, 7,
                8, 5, 9, 7, 6, 1, 4, 2, 3,
                4, 2, 6, 8, 5, 3, 7, 9, 1,
                7, 1, 3, 9, 2, 4, 8, 5, 6,
                9, 6, 1, 5, 3, 7, 2, 8, 4,
                2, 8, 7, 4, 1, 9, 6, 3, 5,
                3, 4, 5, 2, 8, 6, 1, 7, 9
        }, toBeResolvedGrid);
    }

    @Test
    void resolveGrid_failed() {
        int[] toBeResolvedGrid = new int[]{
                5, 3, 4, 6, 7, 8, 9, 0, 2,
                6, 7, 2, 1, 9, 5, 3, 4, 8,
                1, 9, 8, 3, 4, 2, 5, 6, 7,
                8, 5, 9, 7, 6, 1, 4, 2, 3,
                4, 2, 6, 8, 5, 3, 0, 0, 1,
                7, 1, 3, 9, 2, 4, 8, 5, 6,
                9, 6, 1, 5, 3, 7, 2, 8, 4,
                2, 8, 7, 4, 1, 9, 6, 3, 5,
                3, 4, 5, 2, 8, 6, 0, 8, 9
        };
        int result = iGridMaster.resoudreLaGrille(toBeResolvedGrid);
        assertEquals(-1, result);
    }

    /**
     * Generate a grid anyway after 1s if the possibilities per level cannot be reached
     *
     * @param level The level that cannot reach his possibilities
     * @implNote This test duration is minimum 3 seconds (one per level)
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void createGridLevelImpossiblePossibilities_success(int level) {
        switch (level) {
            case 2 -> {
                gridMaster.setAverageImpossiblePossibilitiesForTests();
                assertEquals(50000, gridMaster.getMoyenMinPossibilites());
                assertEquals(-1, gridMaster.getMoyenMaxPossibilites());
            }
            case 3 -> {
                gridMaster.setDifficultImpossiblePossibilitiesForTests();
                assertEquals(50000, gridMaster.getMoyenMaxPossibilites());
            }
            default -> {
                gridMaster.setEasyImpossiblePossibilitiesForTests();
                assertEquals(-1, gridMaster.getMoyenMinPossibilites());
            }
        }
        int[][] grids = gridMaster.creerLesGrilles(level);
        assertNotNull(grids);
        assertNotNull(grids[0]);
        assertNotNull(grids[1]);
        assertNotNull(grids[2]);
        // The resolved grid
        assertEquals(81, grids[0].length);
        int countForResolvedGrid = 0;
        for (int value : grids[0]) {
            if (value == 0) countForResolvedGrid++;
        }
        assertEquals(0, countForResolvedGrid);
        // The grid to be resolved
        assertEquals(81, grids[1].length);
        int countForToBeResolvedGrid = 0;
        for (int value : grids[1]) {
            if (value == 0) countForToBeResolvedGrid++;
        }
        assertNotEquals(0, countForToBeResolvedGrid);
        // The possibilities
        assertTrue(grids[2][0] >= 0 && grids[2][0] <= 100,
                "The possibilities must be between 0 and 100 for level : " + level);
    }

}
