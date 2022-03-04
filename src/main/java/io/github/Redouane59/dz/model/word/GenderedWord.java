package io.github.Redouane59.dz.model.word;

import io.github.Redouane59.dz.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GenderedWord extends Word {

  private Gender  gender;
  private boolean singular;

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
