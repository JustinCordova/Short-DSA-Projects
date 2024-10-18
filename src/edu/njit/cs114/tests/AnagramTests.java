package edu.njit.cs114.tests;

import edu.njit.cs114.anagramfinder.AnagramFinderHashTableImpl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import edu.njit.cs114.anagramfinder.AnagramFinderListImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 3/24/2024
 */
public class AnagramTests extends UnitTests {

    private static final String DICTIONARY_FILE = "testWords.txt";


    private void createDictionary(String [] words) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(DICTIONARY_FILE));
            for (String word : words) {
                writer.write(word);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean equals(String [] words1, String [] words2) {
        String [] words11 = Arrays.copyOf(words1, words1.length);
        String [] words21 = Arrays.copyOf(words1, words2.length);
        Arrays.sort(words11);
        Arrays.sort(words21);
        return Arrays.equals(words11, words21);
    }

    @Test
    public void noAnagramsForHashMapTest() {
        String [] words = new String [] {"maple", "dice"};
        createDictionary(words);
        AnagramFinderHashTableImpl impl = new AnagramFinderHashTableImpl();
        try {
            impl.processDictionary(DICTIONARY_FILE);
            List<List<String>> anagramGroups = impl.getMostAnagrams();
            assertEquals(2, anagramGroups.size());
            totalScore += 3;
            assertEquals(1, anagramGroups.get(0).size());
            totalScore += 1;
            assertEquals(1, anagramGroups.get(1).size());
            totalScore += 2;
            String [] words1 = new String [] {anagramGroups.get(0).get(0),
                    anagramGroups.get(1).get(0)};
            assertEquals(true, equals(words, words1));
            totalScore += 4;
            success("noAnagramsForHashMapTest");
        } catch(Exception e) {
            failure("noAnagramsForHashMapTest", e);
        }
    }

    @Test
    public void noAnagramsForListTest() {
        String [] words = new String [] {"maple", "dice"};
        createDictionary(words);
        AnagramFinderListImpl impl = new AnagramFinderListImpl();
        try {
            impl.processDictionary(DICTIONARY_FILE);
            List<List<String>> anagramGroups = impl.getMostAnagrams();
            assertEquals(2, anagramGroups.size());
            totalScore += 3;
            assertEquals(1, anagramGroups.get(0).size());
            totalScore += 1;
            assertEquals(1, anagramGroups.get(1).size());
            totalScore += 2;
            String [] words1 = new String [] {anagramGroups.get(0).get(0),
                    anagramGroups.get(1).get(0)};
            assertEquals(true, equals(words, words1));
            totalScore += 4;
            success("noAnagramsForListTest");
        } catch(Exception e) {
            failure("noAnagramsForListTest", e);
        }
    }

    @Test
    public void SingleMostAnagramsForHashMapTest() {
        String [] words = new String [] {"marsipobranchiata", "maple", "dice", "maple", "maple", "basiparachromatin"};
        createDictionary(words);
        AnagramFinderHashTableImpl impl = new AnagramFinderHashTableImpl();
        try {
            impl.processDictionary(DICTIONARY_FILE);
            List<List<String>> anagramGroups = impl.getMostAnagrams();
            assertEquals(1, anagramGroups.size());
            totalScore += 3;
            assertEquals(2, anagramGroups.get(0).size());
            totalScore += 1;
            String [] words1 = new String [] {anagramGroups.get(0).get(0),
                    anagramGroups.get(0).get(1)};
            assertEquals(true, equals(words1, new String [] {"marsipobranchiata","basiparachromatin" }));
            totalScore += 4;
            success("SingleMostAnagramsForHashMapTest");
        } catch(Exception e) {
            failure("SingleMostAnagramsForHashMapTest", e);
        }
    }

    @Test
    public void SingleMostAnagramsForListTest() {
        String [] words = new String [] {"marsipobranchiata", "maple", "dice", "maple", "maple", "basiparachromatin"};
        createDictionary(words);
        AnagramFinderListImpl impl = new AnagramFinderListImpl();
        try {
            impl.processDictionary(DICTIONARY_FILE);
            List<List<String>> anagramGroups = impl.getMostAnagrams();
            assertEquals(1, anagramGroups.size());
            totalScore += 3;
            assertEquals(2, anagramGroups.get(0).size());
            totalScore += 1;
            String [] words1 = new String [] {anagramGroups.get(0).get(0),
                    anagramGroups.get(0).get(1)};
            assertEquals(true, equals(words1, new String [] {"marsipobranchiata","basiparachromatin" }));
            totalScore += 4;
            success("SingleMostAnagramsForListTest");
        } catch(Exception e) {
            failure("SingleMostAnagramsForListTest", e);
        }
    }

    @Test
    public void multiAnagramsForHashMapTest() {
        String [] words = new String [] {"alerting", "alter", "altering", "integral","maple",
                "post", "apers", "pears",
                "pots", "spot", "stop", "tops",
                "keats", "relating", "skate", "stake", "steak", "triangle", "takes"};
        createDictionary(words);
        AnagramFinderHashTableImpl impl = new AnagramFinderHashTableImpl();
        try {
            impl.processDictionary(DICTIONARY_FILE);
            List<List<String>> anagramGroups = impl.getMostAnagrams();
            assertEquals(3, anagramGroups.size());
            totalScore += 4;
            assertEquals(5, anagramGroups.get(0).size());
            totalScore += 1;
            assertEquals(5, anagramGroups.get(1).size());
            totalScore += 1;
            assertEquals(5, anagramGroups.get(2).size());
            totalScore += 1;
            String [] wordGroup1 = {"post", "pots", "spot", "stop", "tops"};
            String [] words1 = anagramGroups.get(0).toArray(new String[0]);
            assertEquals(true, equals(wordGroup1, words1));
            totalScore += 4;
            String [] wordGroup2 = {"alerting", "altering", "integral", "relating", "triangle"};
            String [] words2 = anagramGroups.get(0).toArray(new String[0]);
            assertEquals(true, equals(wordGroup2, words2));
            totalScore += 4;
            String [] wordGroup3 = {"keats", "skate", "stake", "steak", "takes"};
            String [] words3 = anagramGroups.get(0).toArray(new String[0]);
            assertEquals(true, equals(wordGroup3, words3));
            totalScore += 4;
            success("multiAnagramsForHashMapTest");
        } catch(Exception e) {
            failure("multiAnagramsForHashMapTest", e);
        }
    }

    @Test
    public void multiAnagramsForListTest() {
        String [] words = new String [] {"alerting", "alter", "altering", "integral","maple",
                "post", "apers", "pears",
                "pots", "spot", "stop", "tops",
                "keats", "relating", "skate", "stake", "steak", "triangle", "takes"};
        createDictionary(words);
        AnagramFinderListImpl impl = new AnagramFinderListImpl();
        try {
            impl.processDictionary(DICTIONARY_FILE);
            List<List<String>> anagramGroups = impl.getMostAnagrams();
            assertEquals(3, anagramGroups.size());
            totalScore += 4;
            assertEquals(5, anagramGroups.get(0).size());
            totalScore += 1;
            assertEquals(5, anagramGroups.get(1).size());
            totalScore += 1;
            assertEquals(5, anagramGroups.get(2).size());
            totalScore += 1;
            String [] wordGroup1 = {"post", "pots", "spot", "stop", "tops"};
            String [] words1 = anagramGroups.get(0).toArray(new String[0]);
            assertEquals(true, equals(wordGroup1, words1));
            totalScore += 4;
            String [] wordGroup2 = {"alerting", "altering", "integral", "relating", "triangle"};
            String [] words2 = anagramGroups.get(0).toArray(new String[0]);
            assertEquals(true, equals(wordGroup2, words2));
            totalScore += 4;
            String [] wordGroup3 = {"keats", "skate", "stake", "steak", "takes"};
            String [] words3 = anagramGroups.get(0).toArray(new String[0]);
            assertEquals(true, equals(wordGroup3, words3));
            totalScore += 4;
            success("multiAnagramsForListTest");
        } catch(Exception e) {
            failure("multiAnagramsForListTest", e);
        }
    }

    @Test
    public void hashMapEfficiencyTest() {
        AnagramFinderHashTableImpl impl = new AnagramFinderHashTableImpl();
        try {
            long startTime = System.nanoTime();
            impl.processDictionary("words.txt");
            List<List<String>> anagramGroups = impl.getMostAnagrams();
            long estimatedTime = System.nanoTime () - startTime ;
            double seconds = ((double) estimatedTime /1000000000) ;
            assertEquals(true, seconds < 0.7);
            totalScore += 4;
            assertEquals(true, seconds < 0.5);
            totalScore += 2;
            success("hashMapEfficiencyTest");
        } catch(Exception e) {
            failure("hashMapEfficiencyTest", e);
        }
    }

}