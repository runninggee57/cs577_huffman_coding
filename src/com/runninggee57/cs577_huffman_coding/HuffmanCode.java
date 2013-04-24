package com.runninggee57.cs577_huffman_coding;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class HuffmanCode {
  public enum AGE {
    NEWEST,
    OLDEST;
  }

  public HashMap<String, Integer> vocabulary;
  HashMap<String, String> coding = new HashMap<String, String>();
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
        next = wi.next();
      }
      System.out.println(i + " of " + files.size() + " files read");
    }
    
    // create the encoding
    // make the priority queue with the entire vocabulary
    PriorityQueue<encodingNode> queue = new PriorityQueue<encodingNode>();
    for (String s : vocabulary.keySet()) {
      queue.add(new encodingNode(vocabulary.get(s), s));
    }
    
    encodingNode root = null;
    while (!queue.isEmpty()) {
      encodingNode left = queue.poll();
      encodingNode right = queue.poll();
      root = new encodingNode(left.count + right.count, null, left, right);
      queue.add(root);
    }
    
    createCoding(root, "");
  }
  
  private void createCoding(encodingNode n, String curSymbol) {
    if (n.left != null && n.right != null) { // never a case where one is null and other isn't
      createCoding(n.left, curSymbol + '0');
      createCoding(n.right, curSymbol + '1');
    }
    else {
      // at leaf
      coding.put(n.word, curSymbol);
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
  
  private class encodingNode implements Comparable<encodingNode> {
    public int count;
    public String word;
    public encodingNode left;
    public encodingNode right;
    
    encodingNode(int count, String word) {
      this.left = null;
      this.right = null;
      this.count = count;
      this.word = word;
    }
    
    encodingNode(int count, String word, encodingNode left, encodingNode right) {
      this.left = left;
      this.right = right;
      this.count = count;
      this.word = word;
    }
    
    @Override
    public int compareTo(encodingNode arg0) {
      if (count < arg0.count)
        return -1;
      else if (count == arg0.count)
        return 0;
      else
        return 1;
    }
    
  }
}
