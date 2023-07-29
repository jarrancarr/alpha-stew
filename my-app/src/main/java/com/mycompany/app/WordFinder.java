package com.mycompany.app;

/**
 * WordFinder is the launching App that takes a puzzle file and uses a crossword implementation to parse and search
 * for words listed in the puzzle file.
 *
 */
public class WordFinder 
{
    public static void main( String[] args )
    {
        String filename = "default.txt";
        if (args.length>0) {
            switch(args[0]) {
                case "-?": 
                    System.out.println("Usage: java WordFinder <puzzle_file_name>"); 
                    System.exit(0);
                    break;
                default: filename = args[0];
            }
        }
        Crossword crossword = null;
        try {
            crossword = new Crossword_Linear_String(filename);
        } catch (PuzzleFormatException e) {
            e.printStackTrace();
            System.exit(1);
        }
        crossword.solve();
    }
}
