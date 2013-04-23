package com.runninggee57.cs577_huffman_coding;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class WordIterator {

  BufferedReader br;
  String filename;
  String nextWord;
  int current_pos = 0;
  
  public WordIterator(String file) {
    try {
      br = new BufferedReader(new FileReader(file));
      filename = file;
      getNextWord();
    }
    catch (FileNotFoundException e) {
      System.out.println("File '" + file + "' does not exist. Please enter a valid file.");
      return;
    }
  }
  
  private void getNextWord() {
    try {
    nextWord = "";
    int readChar = br.read();
    
    // get rid of any multiple spaces leading into a word
    while ((char)readChar == ' ')
      readChar = br.read();
    
    while (readChar != -1  && (char)readChar != ' ') { // -1 means EOF
      nextWord += (char)readChar;
      readChar = br.read();
    }
    }
    catch (IOException e) {
      System.out.println("There was an IO Error");
      return;
    }
  }
  
  public String next() {
    String ret = nextWord;
    getNextWord();
    return ret;
  }
}
