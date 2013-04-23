package com.runninggee57.cs577_huffman_coding_tests;

import static org.junit.Assert.*;
import org.junit.Test;
import com.runninggee57.cs577_huffman_coding.WordIterator;

public class WordIteratorTest {

  @Test
  public void testNext() {
    WordIterator wi = new WordIterator("speechdata/1789_04_30_3446.txt");
    String word = wi.next();
    while (word != "") {
      System.out.println("Next word: " + word);
      word = wi.next();
    }
  }

}
