package com.mycompany.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mycompany.app.Utils.*;

/**
 * Crossword_Linear_String class implements Crossword representing the puzzle as a linear string  
 *
 */
public class Crossword_Linear_String implements Crossword {

    private int x, y;
    String puzzle, rotatedPuzzle, northEastDiagonalPuzzle, southWestDiagonalPuzzle, northWestDiagonalPuzzle,
            southEastDiagonalPuzzle;
    String[] words;
    List<String> answers;

    public Crossword_Linear_String(String filename) throws PuzzleFormatException {
        List<String> lines = Utils.loadFile(filename);
        parseLines(lines);
    }

    public Crossword_Linear_String(List<String> lines) throws PuzzleFormatException {        
        parseLines(lines);
    }

    /**
     * solve renders the results to the command line
     *
     */
    public void solve() {
        getMatches();
    }

    /**
     * getMatches depends on the puzzle having been parsed
     * It goes through each phase of word search: 
     *      Across, Down, and both diagonals which are handled separately by top and bottom half
     *      since the logic is different.
     * The word list has reverse duplicates so those matches are rendered conditionally.
     *
     */
    private void getMatches() {
        boolean reverse = false;
        String last = "";
        answers = new ArrayList<>();
        for (String word : words) {
            int dW = word.length() - 1;
            int found = puzzle.indexOf(word);
            if (found >= 0) {
                int dX = found / x;
                int dY = found % (x + 1);
                answers.add((reverse ? last : word) + " "
                        + dX + ":" + (dY + (reverse ? dW : 0)) + " "
                        + dX + ":" + (dY + (reverse ? 0 : dW)));
            }
            found = rotatedPuzzle.indexOf(word);
            if (found >= 0) {
                int dX = found % (y + 1);
                int dY = found / (y + 1);
                answers.add((reverse ? last : word) + " "
                        + (dX + (reverse ? dW : 0)) + ":" + (dY) + " "
                        + (dX + (reverse ? 0 : dW)) + ":" + (dY));
            }
            found = northEastDiagonalPuzzle.indexOf(word);
            if (found >= 0) {
                int dX = found % (x + 1);
                int dY = dX + found / (x + 1);
                answers.add((reverse ? last : word) + " "
                        + (dX + (reverse ? dW : 0)) + ":" + (dY + (reverse ? dW : 0)) + " "
                        + (dX + (reverse ? 0 : dW)) + ":" + (dY + (reverse ? 0 : dW)));
            }
            found = southWestDiagonalPuzzle.indexOf(word);
            if (found >= 0) {
                int dY = found / x;
                int dX = found % (x + 1);
                answers.add((reverse ? last : word) + " "
                        + (dX + dY + 1 + (reverse ? dW : 0)) + ":" + (dX + (reverse ? dW : 0)) + " "
                        + (dX + dY + 1 + (reverse ? 0 : dW)) + ":" + (dX + (reverse ? 0 : dW)));
            }
            found = southEastDiagonalPuzzle.indexOf(word);
            if (found >= 0) {
                int dY = found / y;
                int dX = found % (x + 1);
                answers.add((reverse ? last : word) + " "
                        + (y - dX - 1 - (reverse ? dW : 0)) + ":" + (dY + dX + (reverse ? dW : 0)) + " "
                        + (y - dY - 1 - (reverse ? 0 : dW)) + ":" + (dY + dX + (reverse ? 0 : dW)));
            }
            found = northWestDiagonalPuzzle.indexOf(word);
            if (found >= 0) {
                int dY = found / y;
                int dX = found % (x + 1);
                answers.add((reverse ? last : word) + " " + dX + "~" + dY  + "                "
                        + (y - dX - dY - 2 - (reverse ? dW : 0)) + ":" + (dX + (reverse ? dW : 0)) + " "
                        + (y - dX - dY - 2 - (reverse ? 0 : dW)) + ":" + (dX + (reverse ? 0 : dW)));
            }
            last = word; // remember the previous word for display purposes
            reverse = !reverse; // the even go arounds are matching words in reverse order
        }
        answers.stream().forEach(a -> sop(a));
    }

    /**
     * parseLines turns the raw lines read in from the file to the data structure required to 
     * process the puzzle
     * the puzzle is stored as a linear string in 6 ways:
     *    -read line by line
     *    -read vertically column by column
     *    -4 triangular corners
     * finally, the search words are read
     */
    private void parseLines(List<String> lines) throws PuzzleFormatException {
        try {
            String[] dimensions = lines.get(0).split("x");
            x = Integer.parseInt(dimensions[0]);
            y = Integer.parseInt(dimensions[1]);
            puzzle = getPuzzle(lines);
            rotatedPuzzle = rotatePuzzle(puzzle);
            northEastDiagonalPuzzle = snipNorthEastPuzzle(puzzle);
            northWestDiagonalPuzzle = snipNorthWestPuzzle(puzzle);
            southWestDiagonalPuzzle = snipSouthWestPuzzle(puzzle);
            southEastDiagonalPuzzle = snipSouthEastPuzzle(puzzle);
            words = getWords(lines);
        } catch (Exception e) {
            // anything that goes wrong will be more generally conisdered a puzzle format issue
            throw new PuzzleFormatException(e);
        }
    }

    /**
     * linear string reading the top left corner letters
     *
     */
    private String snipNorthWestPuzzle(String puzzle) {
        StringBuilder upperPuzzle = new StringBuilder();
        for (int row = y-2 ; row>0 ; row--) {
            for (int column = 0; column<x&&row-column>-1 ; column++) {
                upperPuzzle.append(puzzle.charAt(row*(x+1)-column*x));
            }
            upperPuzzle.append(
                    "-------------------------------------------------------------------------------------------------------------------------"
                            .substring(121+row-x));
        }
        return upperPuzzle.toString();
    }

    /**
     * linear string reading the bottom left corner letters
     *
     */
    private String snipSouthWestPuzzle(String puzzle) {
        StringBuilder lowerPuzzle = new StringBuilder();
        for (int row = 1; row < y - 1; row++) {
            for (int column = 0; column + row < y; column++) {
                lowerPuzzle.append(puzzle.charAt(row * (x + 1) + column * (x + 2)));
            }
            lowerPuzzle.append(
                    "-------------------------------------------------------------------------------------------------------------------------"
                            .substring(120 - row));
        }
        return lowerPuzzle.toString();
    }

    /**
     * linear string reading the top right corner letters
     *
     */
    private String snipNorthEastPuzzle(String puzzle) {
        StringBuilder upperPuzzle = new StringBuilder();
        for (int column = 0; column < x - 1; column++) {
            for (int row = 0; row + column < x; row++) {
                upperPuzzle.append(puzzle.charAt(row * (x + 2) + column));
            }
            upperPuzzle.append(
                    "-------------------------------------------------------------------------------------------------------------------------"
                            .substring(120 - column));
        }
        return upperPuzzle.toString();
    }

    /**
     * linear string reading the bottom right corner letters
     *
     */
    private String snipSouthEastPuzzle(String puzzle) {
        StringBuilder lowerPuzzle = new StringBuilder();
        for (int column = 0; column < x - 1; column++) {
            for (int row = y - 1; row - column >= 0; row--) {
                lowerPuzzle.append(puzzle.charAt(y + row * x + column - 1));
            }
            lowerPuzzle.append(
                    "-------------------------------------------------------------------------------------------------------------------------"
                            .substring(120 - column));
        }
        return lowerPuzzle.toString();
    }

    /**
     * linear string reading the top down
     *
     */
    private String rotatePuzzle(String puzzle) {
        StringBuilder rotate = new StringBuilder();

        for (int iter = 0; rotate.length() < (x + 1) * y + 1; iter = (iter + x + 1) % (x * y + y - 1)) {
            rotate.append(puzzle.charAt(iter));
            if (rotate.length() % (x + 1) == x)
                rotate.append('-');
        }

        return rotate.toString();
    }

    /**
     * getWords reads the list of words that follow the puzzle array
     * each word is duplicated in reverse an to find word that go in opposite direction
     *
     */
    private String[] getWords(List<String> lines) {
        String[] words = new String[(lines.size() - y - 1) * 2];
        int wordStart = y + 1;
        for (int iter = 0; iter < lines.size() - wordStart; iter++) {
            words[iter * 2] = lines.get(iter + wordStart).replaceAll(" ", "");
            words[iter * 2 + 1] = new StringBuilder(words[iter * 2]).reverse().toString();
        }
        return words;
    }

    /**
     * getPuzzle removes the spaces between the letters and inserts a dash as not to match over lines
     * since the dimensions are known, a linear array can be managed
     *
     */
    private String getPuzzle(List<String> lines) {
        String puzzle = "";
        for (int iter = 0; iter < y; iter++) {
            puzzle += lines.get(iter + 1).replaceAll(" ", "").toUpperCase() + "-";
        }
        return puzzle;
    }

    /**
     * utility to aide testing
     *
     */
    @Override
    public Map<String, String> inspect() {
        Map<String, String> inspector = new HashMap<>();
        inspector.put("puzzle", puzzle);
        inspector.put("rotatedPuzzle", rotatedPuzzle);
        inspector.put("northEastDiagonalPuzzle", northEastDiagonalPuzzle);
        inspector.put("southWestDiagonalPuzzle", southWestDiagonalPuzzle);
        inspector.put("northWestDiagonalPuzzle", northWestDiagonalPuzzle);
        inspector.put("southEastDiagonalPuzzle", southEastDiagonalPuzzle);
        inspector.put("words", Arrays.toString(words));
        inspector.put("answers", answers==null?"":answers.toString());
        return inspector;
    }
}
