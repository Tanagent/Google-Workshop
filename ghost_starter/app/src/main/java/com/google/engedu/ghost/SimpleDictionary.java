package com.google.engedu.ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {

        if(prefix.equals("") || prefix.equals(null)) {
            Random rand = new Random();
            return words.get(rand.nextInt(words.size()));
        } else {
            int start = 0;
            int end = words.size() - 1;

            while(start <= end) {
                int mid = (start + end) / 2;

                if(words.get(mid).startsWith(prefix)) {
                    return words.get(mid);
                } else if(words.get(mid).compareTo(prefix) < 0) {
                    start = mid + 1;
                } else {
                    end = mid + 1;
                }
            }
            return null;
        }

    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        return null;
    }
}
