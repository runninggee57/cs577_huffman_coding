package com.runninggee57.cs577_huffman_coding_tests;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import org.junit.Test;
import com.runninggee57.cs577_huffman_coding.HuffmanCode;

public class HuffmanCodeTest {

  @Test
  public void testHuffmanCode() {
    HuffmanCode hc = new HuffmanCode();
    
    hc.create(1000, HuffmanCode.AGE.OLDEST);
    
    PriorityQueue<Map.Entry<String, String>> shortest = new PriorityQueue<Map.Entry<String, String>>(hc.coding.size(), new Comparator<Map.Entry<String, String>>() {
          public int compare(Map.Entry<String, String> a, Map.Entry<String, String> b) {
            if (a.getValue().length() < b.getValue().length())
              return -1;
            else if (a.getValue().length() == b.getValue().length())
              return 0;
            else
              return 1;
          }
        });
    PriorityQueue<Map.Entry<String, String>> longest = new PriorityQueue<Map.Entry<String, String>>(hc.coding.size(), new Comparator<Map.Entry<String, String>>() {
      public int compare(Map.Entry<String, String> a, Map.Entry<String, String> b) {
        if (a.getValue().length() < b.getValue().length())
          return 1;
        else if (a.getValue().length() == b.getValue().length())
          return 0;
        else
          return -1;
      }
    });
    for (Map.Entry<String, String> e : hc.coding.entrySet()) {
      shortest.add(e);
      longest.add(e);
    }
    
    Map.Entry<String, String> shortCode = shortest.poll();
    Map.Entry<String, String> longCode = longest.poll();
    System.out.println("Shortest: " + shortCode.getKey() + " - " + shortCode.getValue());
    System.out.println("Longest: " + longCode.getKey() + " - " + longCode.getValue());
    
    Set<String> set = hc.curVocabulary.keySet();
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter("vocabulary.txt"));
      for (String s : set) {
        bw.write("Word: " + s + " - " + hc.curVocabulary.get(s) + " - " + hc.coding.get(s) + "\n");
      }
      bw.close();
    }
    catch (IOException e) {
      System.out.println(e);
    }
  }
  
  @Test
  public void testEncodeAllFiles() {
    HuffmanCode hc = new HuffmanCode();
    hc.create(1, HuffmanCode.AGE.NEWEST);
    try {
      hc.encodeAllFiles();
      
      BufferedWriter bw = new BufferedWriter(new FileWriter("speech_compressions.csv"));
      hc.writeFileCompressions(bw);
      bw.close();
    }
    catch (IOException e) {
      System.out.println(e);
    }
  }

}
