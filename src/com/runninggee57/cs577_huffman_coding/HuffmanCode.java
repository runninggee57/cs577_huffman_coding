package com.runninggee57.cs577_huffman_coding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class HuffmanCode {
  public enum AGE {
    NEWEST,
    OLDEST;
  }

  public HashMap<String, Integer> vocabulary;
  public HashMap<String, Integer>[] fileWordCounts;
  File speechDir = new File("speechdata");
  ArrayList<String> files;
  double totalWordCount;
  
  public HuffmanCode(int numSpeeches, AGE age) {
    // Generate array of speech files
    files = new ArrayList<String>(Arrays.asList(speechDir.list()));
    if (files == null) {
      System.out.println("Something went wrong getting list of files");
    }
    
    switch (age) {
    case NEWEST:
      java.util.Collections.sort(files, new newToOld());
      break;
    case OLDEST:
      java.util.Collections.sort(files, new oldToNew());
      break;
    }
    
    // generate word counts
    vocabulary = new HashMap<String, Integer>();
    for (int i = 0; i < files.size(); i++) {
      WordIterator wi = new WordIterator("speechdata/" + files.get(i));
      String next = wi.next();
      while (next != "") {
        Integer num = vocabulary.get(next);
        if (num == null) {
          vocabulary.put(next, 1);
        }
        else if (i < numSpeeches){
          // then we want to increment the count since this is a speech we are encoding with
          vocabulary.put(next, num + 1);
        }
        num = fileWordCounts[i].get(next);
        if (num == null) {
          fileWordCounts[i].put(next, 1);
        }
        else {
          fileWordCounts[i].put(next, num + 1);
        }
        next = wi.next();
      }
      System.out.println(i + " of " + files.size() + " files read");
    }
  }
  
  private class oldToNew implements Comparator<String> {
    public int compare(String s1, String s2) {
      return s1.compareTo(s2);
    }
  }
  
  private class newToOld implements Comparator<String> {
    public int compare(String s1, String s2) {
      // reverse order
      return -1 * s1.compareTo(s2);
    }
  }
}
