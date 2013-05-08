package com.runninggee57.cs577_huffman_coding;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class HuffmanCode {
  public enum AGE {
    NEWEST,
    OLDEST;
  }

  public HashMap<String, Integer> curVocabulary;
  public HashMap<String, String> coding = new HashMap<String, String>();
  public HashMap<String, FileData> speechData = new HashMap<String, FileData>();
  File speechDir = new File("speechdata");
  ArrayList<String> files;
  double totalWordCount;
  
  public HuffmanCode() {
    // Generate array of speech files
    files = new ArrayList<String>(Arrays.asList(speechDir.list()));
    if (files == null) {
      System.out.println("Something went wrong getting list of files");
    }
    
    generateWordCounts();
  }
  
  private void generateWordCounts() {
    System.out.println("Generating word counts...");
    // generate word counts
    for (int i = 0; i < files.size(); i++) {
      FileData fd = new FileData(files.get(i));
      HashMap<String, Integer> wordCounts = fd.wordCounts;
      WordIterator wi = new WordIterator("speechdata/" + files.get(i));
      String next = wi.next();
      while (next != "") {
        Integer num = wordCounts.get(next);
        if (num == null) {
          wordCounts.put(next, 1);
        }
        else {
          // then we want to increment the count
          wordCounts.put(next, num + 1);
        }
        next = wi.next();
      }
      speechData.put(files.get(i), fd);
      wi.close();
      System.out.println((i + 1) + " of " + files.size() + " files read");
    }
  }
  
  private void generateVocabulary(int numSpeeches, AGE age) {
    curVocabulary = new HashMap<String, Integer>();
    // sort files the right way
    switch (age) {
    case NEWEST:
      java.util.Collections.sort(files, new newToOld());
      break;
    case OLDEST:
      java.util.Collections.sort(files, new oldToNew());
      break;
    }
    
    int fileNum = 0;
    for (String filename : files) {
      HashMap<String, Integer> wordCounts = speechData.get(filename).wordCounts;
      
      if (fileNum < numSpeeches) {
        // Want to add full counts to vocabulary count
        for (String word : wordCounts.keySet()) {
          Integer curNum = curVocabulary.get(word);
          Integer newNum = wordCounts.get(word);
          if (curNum == null)
            curVocabulary.put(word, newNum);
          else // add count
            curVocabulary.put(word, curNum + newNum);
        }
      }
      else {
        // speech not used in encoding, just add one for each word
        for (String word : wordCounts.keySet()) {
          Integer curNum = curVocabulary.get(word);
          // only add if the word doesn't exist yet in vocabulary
          if (curNum == null)
            curVocabulary.put(word, 1);
        }
      }
      fileNum++;
    }
  }
  
  public void create(int numSpeeches, AGE age) {
    generateVocabulary(numSpeeches, age);
    System.out.println("Generating Huffman Code tree...");
    // create the encoding
    // make the priority queue with the entire vocabulary
    PriorityQueue<encodingNode> queue = new PriorityQueue<encodingNode>();
    for (String s : curVocabulary.keySet()) {
      queue.add(new encodingNode(curVocabulary.get(s), s));
    }
    
    while (queue.size() > 1) {
      encodingNode left = queue.poll();
      encodingNode right = queue.poll();
      queue.add(new encodingNode(left.count + right.count, null, left, right));
    }
    encodingNode root = queue.poll();
    
    System.out.println("Generating code table...");
    createCoding(root, "");
  }
  
  public void encodeAllFiles() {
    System.out.println("Encoding files...");
    for (int i = 0; i < files.size(); i++) {
      String filename = files.get(i);
      FileData fd = speechData.get(filename);
      int num_words = 0;
      String encoding = "";
      WordIterator wi = new WordIterator("speechdata/" + filename);
      String next = wi.next();
      while (next != "") {
        encoding += coding.get(next);
        next = wi.next();
        num_words++;
      }
      wi.close();
      
      // compute compression
      int num_symbols = fd.wordCounts.size();
      double bits_per_block = java.lang.Math.ceil(java.lang.Math.log(num_symbols) / java.lang.Math.log(2));
      double compression = (double)encoding.length() / (num_words * bits_per_block);
      fd.compression = compression;
      
      speechData.put(filename, fd);
      System.out.println((i + 1) + " of " + files.size() + " files encoded");
      
    }
    System.out.println("Files encoded.");
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
  
  public void writeFileCompressions(BufferedWriter bw) {
    System.out.println("Writing file compressions...");
    java.util.Collections.sort(files, new oldToNew());
    
    for (String filename : files) {
      try {
        bw.write(filename.substring(5, 7) + "/" + filename.substring(8,10) + "/" + filename.substring(0,4) + "," + speechData.get(filename).compression + "\n");
      }
      catch(IOException e) {
        System.out.println(e);
        return;
      }
    }
    System.out.println("Compressions written.");
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
  
  private class FileData {
    public String filename;
    public HashMap<String, Integer> wordCounts;
    public double compression;
    
    FileData(String filename) {
      this.filename = filename;
      wordCounts = new HashMap<String, Integer>();
      compression = 0;
    }
    
    FileData(FileData f) {
      this.filename = f.filename;
      this.wordCounts = f.wordCounts;
      this.compression = f.compression;
    }
  }
}
