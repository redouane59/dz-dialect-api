package io.github.Redouane59.dz.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class Config {

  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static List<Character> VOWELS                      = List.of('a', 'e', 'é', 'è', 'ê', 'i', 'o', 'ô', 'u', 'h');
  // remove the L if this letter is after
  public static List<String>    CONSONANTS                  = List.of("d", "n", "r", "t", "s", "z", "ch");
  public static boolean         SERIALIZE_ADDITIONAL_INFO   = true;
  public static boolean         SERIALIZE_WORD_PROPOSITIONS = true;
  public static int             MAX_GENERATION_COUNT        = 30;
}
