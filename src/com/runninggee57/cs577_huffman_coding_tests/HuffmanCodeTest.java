package com.runninggee57.cs577_huffman_coding_tests;

import static org.junit.Assert.*;
import java.util.Set;
import org.junit.Test;
import com.runninggee57.cs577_huffman_coding.HuffmanCode;

public class HuffmanCodeTest {

  @Test
  public void testHuffmanCode() {
    HuffmanCode hc = new HuffmanCode(1000, HuffmanCode.AGE.OLDEST);
    
    Set<String> set = hc.vocabulary.keySet();
    for (String s : set) {
      System.out.println("Word: " + s + " - " + hc.vocabulary.get(s));
    }
  }

}
