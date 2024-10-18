package edu.njit.cs114.anagramfinder;

import java.io.IOException;
import java.util.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 3/22/2024
 */
public class AnagramFinderListImpl extends AbstractAnagramFinder {

    private List<WordChArrPair> wordChArrPairList = new ArrayList<>();

    private class WordChArrPair implements Comparable<WordChArrPair> {
        public final String word;
        public final char [] charArr;

        public WordChArrPair(String word) {
            this.word = word;
            charArr = word.toCharArray();
            Arrays.sort(charArr);
        }

        public boolean isAnagram(WordChArrPair wordArrPair) {
            /**
             * To be completed
             * Compare charArr already sorted using Arrays.equals() method
             */
            return Arrays.equals(this.charArr, wordArrPair.charArr);
        }

        @Override
        public int compareTo(WordChArrPair wordArrPair) {
            return this.word.compareTo(wordArrPair.word);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof WordChArrPair)) {
                return false;
            }
            WordChArrPair other = (WordChArrPair) obj;
            return this.word.equals(other.word);
        }
    }




    @Override
    public void clear() {
        wordChArrPairList.clear();
    }

    @Override
    public void addWord(String word) {
        WordChArrPair wordChArrPair = new WordChArrPair(word);
        if (!wordChArrPairList.contains(wordChArrPair)) {
            wordChArrPairList.add(wordChArrPair);
        }
    }

    @Override
    public List<List<String>> getMostAnagrams() {
        List<WordChArrPair> wordArrPairList = new ArrayList<>(wordChArrPairList);
        Collections.sort(wordArrPairList);
        ArrayList<List<String>> mostAnagramsList = new ArrayList<>();
        /**
         * To be completed
         *  Note : use isAnagram()method of WordArrPair to identify an anagram
         */
        for (WordChArrPair pair : this.wordChArrPairList) {
            List<String> anagramL = null;
            for (List<String> list : mostAnagramsList) {
                if (list.size() > 0 &&
                        (new WordChArrPair(list.get(0))).isAnagram(pair)
                ) {
                    anagramL = list;
                    break;
                }
            }

            if (anagramL != null) {
                anagramL.add(pair.word);
            } else {
                anagramL = new ArrayList<>();
                anagramL.add(pair.word);
                mostAnagramsList.add(anagramL);
            }
        }

        int maxAnagramSize = 0;
        HashMap<Integer, List<List<String>>> aG = new HashMap<>();

        for (List<String> xAnagrams : mostAnagramsList) {
            if (xAnagrams.size() > maxAnagramSize)
                maxAnagramSize = xAnagrams.size();

            if(aG.containsKey(xAnagrams.size()))
                aG.get(xAnagrams.size()).add(xAnagrams);
            else {
                List<List<String>> group = new ArrayList<>();
                group.add(xAnagrams);
                aG.put(xAnagrams.size(), group);
            }
        }
        return aG.get(maxAnagramSize);
    }

    public static void main(String [] args) {
        AnagramFinderListImpl finder = new AnagramFinderListImpl();
        finder.clear();
        long startTime = System.nanoTime();
        int nWords=0;
        try {
            nWords = finder.processDictionary("words.txt");
        } catch (IOException e) {
            e.printStackTrace ();
        }
        List<List<String>> anagramGroups = finder.getMostAnagrams();
        long estimatedTime = System.nanoTime () - startTime ;
        double seconds = ((double) estimatedTime /1000000000) ;
        System.out.println("Number of words : " + nWords);
        System.out.println("Number of groups of words with maximum anagrams : "
                + anagramGroups.size());
        if (!anagramGroups.isEmpty()) {
            System.out.println("Length of list of max anagrams : " + anagramGroups.get(0).size());
            for (List<String> anagramGroup : anagramGroups) {
                System.out.println("Anagram Group : "+ anagramGroup);
            }
        }
        System.out.println ("Time (seconds): " + seconds);

    }
}
