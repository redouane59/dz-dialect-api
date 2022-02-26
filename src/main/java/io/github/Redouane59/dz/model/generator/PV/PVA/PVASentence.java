package io.github.Redouane59.dz.model.generator.PV.PVA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.Redouane59.dz.model.Lang;
import io.github.Redouane59.dz.model.complement.adjective.Adjective;
import io.github.Redouane59.dz.model.generator.PV.PVSentence;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PVASentence extends PVSentence {

  @JsonIgnore
  private Adjective adjective;

  @Override
  public String buildSentenceValue(final Lang lang) {
    String pv = super.buildSentenceValue(lang);
    return pv
           + " "
           + adjective.getTranslationByGender(getPersonalProunoun().getGender(), getPersonalProunoun().isSingular(), lang).getValue();
  }

}
