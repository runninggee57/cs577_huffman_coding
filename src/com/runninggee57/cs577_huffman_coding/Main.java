package com.runninggee57.cs577_huffman_coding;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

  /**
   * @param args
   */
  public static void main(String[] args) {
    // Encode with most recent speech
    HuffmanCode hc = new HuffmanCode(1, HuffmanCode.AGE.NEWEST);
    try {
      hc.encodeAllFiles();
      
      BufferedWriter bw = new BufferedWriter(new FileWriter("speech_compressions_1_recent.csv"));
      hc.writeFileCompressions(bw);
      bw.close();
    }
    catch (IOException e) {
      System.out.println(e);
    }
    
    // Encode with 2 most recent speeches
    hc = new HuffmanCode(2, HuffmanCode.AGE.NEWEST);
    try {
      hc.encodeAllFiles();
      
      BufferedWriter bw = new BufferedWriter(new FileWriter("speech_compressions_2_recent.csv"));
      hc.writeFileCompressions(bw);
      bw.close();
    }
    catch (IOException e) {
      System.out.println(e);
    }
    
    // Encode with 10 most recent speeches
    hc = new HuffmanCode(10, HuffmanCode.AGE.NEWEST);
    try {
      hc.encodeAllFiles();
      
      BufferedWriter bw = new BufferedWriter(new FileWriter("speech_compressions_10_recent.csv"));
      hc.writeFileCompressions(bw);
      bw.close();
    }
    catch (IOException e) {
      System.out.println(e);
    }
    
    // Encode with 100 most recent speeches
    hc = new HuffmanCode(100, HuffmanCode.AGE.NEWEST);
    try {
      hc.encodeAllFiles();
      
      BufferedWriter bw = new BufferedWriter(new FileWriter("speech_compressions_100_recent.csv"));
      hc.writeFileCompressions(bw);
      bw.close();
    }
    catch (IOException e) {
      System.out.println(e);
    }
    
    // Encode with 300 most recent speeches
    hc = new HuffmanCode(300, HuffmanCode.AGE.NEWEST);
    try {
      hc.encodeAllFiles();
      
      BufferedWriter bw = new BufferedWriter(new FileWriter("speech_compressions_300_recent.csv"));
      hc.writeFileCompressions(bw);
      bw.close();
    }
    catch (IOException e) {
      System.out.println(e);
    }
    
    // Encode with 2 oldest speeches
    hc = new HuffmanCode(2, HuffmanCode.AGE.OLDEST);
    try {
      hc.encodeAllFiles();
      
      BufferedWriter bw = new BufferedWriter(new FileWriter("speech_compressions_2_oldest.csv"));
      hc.writeFileCompressions(bw);
      bw.close();
    }
    catch (IOException e) {
      System.out.println(e);
    }
    
    // Encode with 10 oldest speeches
    hc = new HuffmanCode(10, HuffmanCode.AGE.OLDEST);
    try {
      hc.encodeAllFiles();
      
      BufferedWriter bw = new BufferedWriter(new FileWriter("speech_compressions_10_oldest.csv"));
      hc.writeFileCompressions(bw);
      bw.close();
    }
    catch (IOException e) {
      System.out.println(e);
    }
    
    // Encode with 100 oldest speeches
    hc = new HuffmanCode(100, HuffmanCode.AGE.OLDEST);
    try {
      hc.encodeAllFiles();
      
      BufferedWriter bw = new BufferedWriter(new FileWriter("speech_compressions_100_oldest.csv"));
      hc.writeFileCompressions(bw);
      bw.close();
    }
    catch (IOException e) {
      System.out.println(e);
    }
    
    // Encode with 300 oldest speeches
    hc = new HuffmanCode(300, HuffmanCode.AGE.OLDEST);
    try {
      hc.encodeAllFiles();
      
      BufferedWriter bw = new BufferedWriter(new FileWriter("speech_compressions_300_oldest.csv"));
      hc.writeFileCompressions(bw);
      bw.close();
    }
    catch (IOException e) {
      System.out.println(e);
    }
    
    // Encode with all speeches
    hc = new HuffmanCode(626, HuffmanCode.AGE.NEWEST);
    try {
      hc.encodeAllFiles();
      
      BufferedWriter bw = new BufferedWriter(new FileWriter("speech_compressions_all.csv"));
      hc.writeFileCompressions(bw);
      bw.close();
    }
    catch (IOException e) {
      System.out.println(e);
    }

  }

}
