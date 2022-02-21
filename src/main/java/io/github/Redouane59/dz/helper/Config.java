package io.github.Redouane59.dz.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.Redouane59.dz.model.Lang;
import java.util.List;

public class Config {

  public static final ObjectMapper    OBJECT_MAPPER     = new ObjectMapper();
  public static       List<Lang>      DISPLAY_PROUNOUNS = List.of(Lang.FR);
  public static       List<Character> VOWELS            = List.of('a', 'e', 'é', 'è', 'i', 'o', 'ô', 'u', 'h');
}
