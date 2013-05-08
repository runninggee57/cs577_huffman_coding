package com.runninggee57.cs577_huffman_coding_tests;

import static org.junit.Assert.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import org.junit.Test;
import com.runninggee57.cs577_huffman_coding.HuffmanCode;

public class HuffmanCodeTest {

  @Test
  public void testHuffmanCode() {
    HuffmanCode hc = new HuffmanCode();
    
    hc.create(1000, HuffmanCode.AGE.OLDEST);
    
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
