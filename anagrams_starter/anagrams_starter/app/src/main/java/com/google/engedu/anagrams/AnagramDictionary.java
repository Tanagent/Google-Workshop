package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();

    private int wordLength = DEFAULT_WORD_LENGTH;
    private HashSet<String> wordSet = new HashSet<String>();
    private ArrayList<String> wordList = new ArrayList<String>();
    private HashMap<String,ArrayList<String>> lettersToWord = new HashMap<String,ArrayList<String>>();
    private HashMap<Integer,ArrayList<String>> sizeToWords = new HashMap<Integer,ArrayList<String>>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();

            wordSet.add(word);
            wordList.add(word);
            String key = alphaOrder(word);
            if(lettersToWord.containsKey(key)){
                lettersToWord.get(key).add(word);
            }
            else{
                ArrayList<String> temp = new ArrayList<String>();
                temp.add(word);
                lettersToWord.put(key,temp);
            }

            if(sizeToWords.containsKey(word.length()))
                sizeToWords.get(word.length()).add(word);
            else{
                ArrayList<String> temp = new ArrayList<String>();
                temp.add(word);
                sizeToWords.put(word.length(), temp);
            }
        }
    }

    public String alphaOrder(String word){
        char[] charArr = word.toCharArray();
        Arrays.sort(charArr);
        return String.valueOf(charArr);
    }

    public boolean isGoodWord(String word, String base) {

        if(wordSet.contains(word) && !word.contains(base))
            return true;

        return false;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for(int i = 97; i <= 122; i++)
        {
            String newWord = word + (char)i;
            if(lettersToWord.containsKey(alphaOrder(newWord)))
            {
                ArrayList<String> temp = lettersToWord.get(alphaOrder(newWord));
                for(String s: temp)
                {
                    result.add(s);
                }
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {

        boolean hasMin = false;
        String starter;

        //int index = random.nextInt(wordList.size()-1);
        ArrayList<String> words = sizeToWords.get(wordLength);
        int index = random.nextInt(words.size());
        do{
            if(index > (words.size()-1))
                index = 0;
            starter = words.get(index);

            if(getAnagramsWithOneMoreLetter(starter).size() >= MIN_NUM_ANAGRAMS)
                hasMin = true;

            index++;

        }while(!hasMin);

        if(wordLength <= MAX_WORD_LENGTH)
            wordLength++;

        return starter;
    }
}
