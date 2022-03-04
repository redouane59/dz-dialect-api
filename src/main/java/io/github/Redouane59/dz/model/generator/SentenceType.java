package io.github.Redouane59.dz.model.generator;

import io.github.Redouane59.dz.model.generator.NVA.NVASentenceBuilder;
import io.github.Redouane59.dz.model.generator.PV.PVSentenceBuilder;
import io.github.Redouane59.dz.model.generator.PVA.PVASentenceBuilder;
import io.github.Redouane59.dz.model.generator.PVAv.PVAvSentenceBuilder;
import io.github.Redouane59.dz.model.generator.PVN.PVNSentenceBuilder;
import io.github.Redouane59.dz.model.generator.QVP.QVPSentenceBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SentenceType {
  PV(false, new PVSentenceBuilder()),
  NVA(true, new NVASentenceBuilder()),
  PVA(true, new PVASentenceBuilder()),
  PVAv(true, new PVAvSentenceBuilder()),
  PVN(false, new PVNSentenceBuilder()),
  QVP(false, new QVPSentenceBuilder());

  private boolean                 needsStateVerb;
  private AbstractSentenceBuilder sentenceBuilder;

  public static SentenceType getSentenceTypeByClass(AbstractSentenceBuilder sentenceBuilder) {
    if (sentenceBuilder instanceof PVSentenceBuilder) {
      return PV;
    } else if (sentenceBuilder instanceof NVASentenceBuilder) {
      return NVA;
    } else if (sentenceBuilder instanceof PVASentenceBuilder) {
      return PVA;
    } else if (sentenceBuilder instanceof PVAvSentenceBuilder) {
      return PVAv;
    } else if (sentenceBuilder instanceof PVNSentenceBuilder) {
      return PVN;
    } else if (sentenceBuilder instanceof QVPSentenceBuilder) {
      return QVP;
    }
    return null;
  }
}
