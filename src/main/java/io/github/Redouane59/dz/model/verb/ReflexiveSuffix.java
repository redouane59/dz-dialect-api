package io.github.Redouane59.dz.model.verb;

import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.RootLang;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
/**
 * Suffixes associated to a verb that replace a noun in sentences
 */
// @todo use json instead of translation
public enum ReflexiveSuffix {
  LI(Lang.DZ, List.of(
      new PPSuffix(PersonalProunoun.I, "li"),
      new PPSuffix(PersonalProunoun.YOU_M, "lek"),
      new PPSuffix(PersonalProunoun.YOU_F, "lek"),
      new PPSuffix(PersonalProunoun.HE, "lou"),
      new PPSuffix(PersonalProunoun.SHE, "lha"),
      new PPSuffix(PersonalProunoun.WE, "lna"),
      new PPSuffix(PersonalProunoun.YOU_P, "lkoum"),
      new PPSuffix(PersonalProunoun.THEY_M, "lhoum"),
      new PPSuffix(PersonalProunoun.THEY_F, "lhoum")
  )),
  NI(Lang.DZ, List.of(
      new PPSuffix(PersonalProunoun.I, "ni"),
      new PPSuffix(PersonalProunoun.YOU_M, "nek"),
      new PPSuffix(PersonalProunoun.YOU_F, "nek"),
      new PPSuffix(PersonalProunoun.HE, "nou"),
      new PPSuffix(PersonalProunoun.SHE, "nha"),
      new PPSuffix(PersonalProunoun.WE, "na"),
      new PPSuffix(PersonalProunoun.YOU_P, "nkoum"),
      new PPSuffix(PersonalProunoun.THEY_M, "nhoum"),
      new PPSuffix(PersonalProunoun.THEY_F, "nhoum")
  )),
  I(Lang.DZ, List.of(
      new PPSuffix(PersonalProunoun.I, "ni"),
      new PPSuffix(PersonalProunoun.YOU_M, "ek"),
      new PPSuffix(PersonalProunoun.YOU_F, "ek"),
      new PPSuffix(PersonalProunoun.HE, "h"),
      new PPSuffix(PersonalProunoun.SHE, "ha"),
      new PPSuffix(PersonalProunoun.WE, "na"),
      new PPSuffix(PersonalProunoun.YOU_P, "koum"),
      new PPSuffix(PersonalProunoun.THEY_M, "houm"),
      new PPSuffix(PersonalProunoun.THEY_F, "houm")
  )),
  LES(Lang.FR, List.of(
      new PPSuffix(PersonalProunoun.I, "me"),
      new PPSuffix(PersonalProunoun.YOU_M, "te"),
      new PPSuffix(PersonalProunoun.YOU_F, "te"),
      new PPSuffix(PersonalProunoun.HE, "le"),
      new PPSuffix(PersonalProunoun.SHE, "la"),
      new PPSuffix(PersonalProunoun.WE, "nous"),
      new PPSuffix(PersonalProunoun.YOU_P, "vous"),
      new PPSuffix(PersonalProunoun.THEY_M, "les"),
      new PPSuffix(PersonalProunoun.THEY_F, "les")
  )),
  LEUR(Lang.FR, List.of(
      new PPSuffix(PersonalProunoun.I, "me"),
      new PPSuffix(PersonalProunoun.YOU_M, "te"),
      new PPSuffix(PersonalProunoun.YOU_F, "te"),
      new PPSuffix(PersonalProunoun.HE, "lui"),
      new PPSuffix(PersonalProunoun.SHE, "lui"),
      new PPSuffix(PersonalProunoun.WE, "nous"),
      new PPSuffix(PersonalProunoun.YOU_P, "vous"),
      new PPSuffix(PersonalProunoun.THEY_M, "leur"),
      new PPSuffix(PersonalProunoun.THEY_F, "leur")
  ));

  final Lang           lang;
  final List<PPSuffix> ppSuffixList;


  public String getSuffixValue(PersonalProunoun personalProunoun, String verbValue) {
    Optional<PPSuffix> ppSuffix = ppSuffixList.stream().filter(o -> o.getPersonalProunoun() == personalProunoun).findAny();
    if (ppSuffix.isEmpty()) {
      System.err.println("suffix empty");
      return "";
    }

    String value = ppSuffix.get().getSuffixValue();

    if (lang.getRootLang() == RootLang.AR &&
        (verbValue.charAt(verbValue.length() - 1) == value.charAt(0)    // manage double letters
         || verbValue.charAt(verbValue.length() - 1) == 'e' && value.charAt(0) == 'i') // manage i + ek
    ) {
      value = value.substring(1);
    }

    return value;
  }

}
