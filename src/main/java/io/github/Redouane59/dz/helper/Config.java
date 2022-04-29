package io.github.Redouane59.dz.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.Redouane59.dz.model.Lang;
import java.util.List;

public class Config {

  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static List<Lang>      DISPLAY_PROUNOUNS         = List.of(Lang.FR);
  public static List<Lang>      DISPLAY_STATE_VERB        = List.of(Lang.FR);
  public static List<Character> VOWELS                    = List.of('a', 'e', 'é', 'è', 'i', 'o', 'ô', 'u', 'h');
  // remove the L if this letter is after
  public static List<String>    CONSONANTS                = List.of("d", "n", "r", "t", "s", "z", "ch");
  public static boolean         SERIALIZE_ADDITIONAL_INFO = true;
}
