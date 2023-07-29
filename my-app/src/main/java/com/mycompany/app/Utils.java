package com.mycompany.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<String> loadFile(String filename) {
        List<String> lines = new ArrayList<>();

		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();

			while (line != null) {
                lines.add(line);
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

        return lines;
    }
    


    public static String[] reverseWords(String[] words) {
        String[] backwords = new String[words.length];
        for (int iter=0 ; iter<words.length ; iter++) {
            backwords[iter] = new StringBuilder(words[iter]).reverse().toString();
        }
        return backwords;
    }

    public static void sop(String out) {
        System.out.println(out);
    }
}
