package io.github.Redouane59.dz.model.sentence.PVSO;

import static io.github.Redouane59.dz.model.sentence.WordPicker.RANDOM;

import io.github.Redouane59.dz.helper.Config;
import io.github.Redouane59.dz.helper.DB;
import io.github.Redouane59.dz.model.Article;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.RootLang;
import io.github.Redouane59.dz.model.Translation;
import io.github.Redouane59.dz.model.sentence.AbstractSentence;
import io.github.Redouane59.dz.model.verb.Conjugation;
import io.github.Redouane59.dz.model.verb.Conjugator;
import io.github.Redouane59.dz.model.verb.GenericSuffixes.Suffix;
import io.github.Redouane59.dz.model.word.GenderedWord;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/*
  Pronoun + verb + person suffix + object (if exists and COI)
 */
public class PVSOSentence extends AbstractSentence {


  public Translation buildSentenceValue(final Lang lang) {
    String               ppValue    = getPersonalProunoun().getTranslationValue(lang);
    Optional<Conjugator> conjugator = getVerb().getConjugationByTense(getTense());
    if (conjugator.isEmpty()) {
      System.err.println("empty conjugator");
      return new Translation(lang, "");
    }
    Optional<Conjugation> conjugation = conjugator.get().getConjugationByCriteria(getPersonalProunoun().getGender(),
                                                                                  getPersonalProunoun().isSingular(),
                                                                                  getPersonalProunoun().getPossession());

    String verbValue = "";
    if (conjugation.isEmpty()) {
      System.err.println("empty conjugation");
    } else {
      verbValue = conjugation.get().getTranslationValue(lang);
    }
    String result = "";
    if (Config.DISPLAY_PROUNOUNS.contains(lang)) {
      result = ppValue + " ";
    }
    result += getVerbAndSuffixValue(lang, verbValue);
    return new Translation(lang, result);
  }

  private String getVerbAndSuffixValue(Lang lang, String verbValue) {
    String  personSuffixValue = "";
    boolean isDirect          = RANDOM.nextBoolean();
    if (isDirect && !getVerb().isDirectComplement()) {
      isDirect = false;
    } else if (!isDirect && !getVerb().isIndirectComplement()) {
      isDirect = true;
    }
    if (lang == Lang.FR) {
      Suffix suffix = DB.FR_SUFFIXES.getSuffix(getSuffix().getGender(), getSuffix().getPossession(), getSuffix().isSingular());
      if (isDirect) {
        personSuffixValue = suffix.getDirectValue();
      } else {
        personSuffixValue = suffix.getIndirectValue();
      }
    } else if (lang.getRootLang() == RootLang.AR) {
      Suffix suffix = DB.DZ_SUFFIXES.getSuffix(getSuffix().getGender(), getSuffix().getPossession(), getSuffix().isSingular());
      if (isDirect && !getVerb().isDzOppositeComplement()) {
        personSuffixValue = suffix.getDirectValue();
      } else {
        personSuffixValue = suffix.getIndirectValue();
      }
    }
    String result = "";

    if (lang == Lang.FR) {
      result += personSuffixValue + " " + verbValue;
    } else if (lang.getRootLang() == RootLang.AR) {
      // -a+ek -> ak || -ou+ek -> ouk
      if (verbValue.endsWith("a") && personSuffixValue.startsWith("e")
          || verbValue.endsWith("ou") && personSuffixValue.startsWith("ek")) {
        result += verbValue + personSuffixValue.substring(1);
      } // ouou -> ouh
      else if (verbValue.endsWith("ou") && personSuffixValue.startsWith("ou")) {
        result += verbValue + "h";
      } else {
        result += verbValue + personSuffixValue;
      }
    }

    if (!isDirect) {
      result += " " + addObject(lang);
    }
    return result;
  }

  private String addObject(Lang lang) {
    String value = "";
    if (getNoun() != null) {
      GenderedWord      nounWord  = getNoun().getWordBySingular(true);
      String            nounValue = nounWord.getTranslationValue(lang);
      Optional<Article> article   = Article.getArticle(nounWord.getGender(lang), true, true);
      if (article.isPresent()) {
        value += " ";
        value += article.get().getTranslationValue(lang, nounValue);
        value += " ";
        value += nounValue;
      }
    }
    if (value.isEmpty()) {
      //System.err.println("no object found");
    }
    return value;
  }

}
