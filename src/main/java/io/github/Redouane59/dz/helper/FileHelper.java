package io.github.Redouane59.dz.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHelper {

  public static List<List<String>> getCsv(String fileName, String delimiter) {
    List<List<String>> records = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] values = line.split(delimiter);
        records.add(Arrays.asList(values));
      }
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    return records;
  }
}
