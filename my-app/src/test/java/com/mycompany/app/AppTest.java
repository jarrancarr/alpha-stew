package com.mycompany.app;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Map;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    /**
     * puzzle formatter Test :-)
     */
    @Test
    public void testPuzzleFormatter()
    {
        Crossword crossword = null;
        try {
            crossword = new Crossword_Linear_String(Arrays.asList(new String[]{"3x3","A B C","D E F","G H I","WORD","ABC"}));
            Map<String, String> data = crossword.inspect();
            assertTrue( data.get("puzzle").equals("ABC-DEF-GHI-"));
            assertTrue( data.get("rotatedPuzzle").equals("ADG-BEH-CFI--"));
            assertTrue( data.get("northWestDiagonalPuzzle").equals("DB--"));
            assertTrue( data.get("northEastDiagonalPuzzle").equals("AEI-BF--"));
            assertTrue( data.get("southWestDiagonalPuzzle").equals("DH--"));
            assertTrue( data.get("southEastDiagonalPuzzle").equals("GEC-HF--"));
            assertTrue( data.get("words").equals("[WORD, DROW, ABC, CBA]"));
        } catch (PuzzleFormatException e) {
            assertTrue(false);
        }
        try {
            crossword = new Crossword_Linear_String(Arrays.asList(new String[]{"3x","A B C","D E F","G H I","WORD","ABC"}));
            crossword.solve();
            Map<String, String> data = crossword.inspect();
            assertTrue(data.get("answers").equals("[ABC 0:0 0:2]"));
        } catch (PuzzleFormatException e) {
            assertTrue(false);
        }
    }


    /**
     * puzzle solver Test :-)
     * 
     * K W A H
     * C A N Y
     * U R L F
     * D S A E
     * can, hawk, wars, kale, arc, sly, us
     */
    @Test
    public void testPuzzleSolver() {
        Crossword crossword = null;
        try {
            crossword = new Crossword_Linear_String(Arrays.asList(new String[]{"4x4","K W A H","C A N Y","U R L F","D S A E","can", "hawk", "wars", "kale", "arc", "sly", "us"}));
            crossword.solve();
            assertTrue(true);
        } catch (PuzzleFormatException e) {
            assertTrue(false);
        }
    }
}