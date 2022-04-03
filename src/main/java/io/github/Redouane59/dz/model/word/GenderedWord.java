package io.github.Redouane59.dz.model.word;

import io.github.Redouane59.dz.model.Gender;
import io.github.Redouane59.dz.model.Lang;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GenderedWord extends Word {

  private Gender  gender;
  private boolean singular;

  public Gender getGender(Lang lang) {
    if (this.getTranslationByLang(lang).isEmpty()) {
      return gender;
    }
    return this.getTranslationByLang(lang).get().getGender() != null
           ? this.getTranslationByLang(lang).get().getGender()
           : gender;
  }

  @Override
  public String toString() {
    String result = super.toString();
    result += " (" + this.getGender() + "/";
    if (this.isSingular()) {
      result += "sg";
    } else {
      result += "pl";
    }
    return result + ")";
  }

}
