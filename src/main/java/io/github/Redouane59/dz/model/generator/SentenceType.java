package io.github.Redouane59.dz.model.generator;

import io.github.Redouane59.dz.model.generator.NVA.NVASentenceBuilder;
import io.github.Redouane59.dz.model.generator.PV.PVSentenceBuilder;
import io.github.Redouane59.dz.model.generator.PVA.PVASentenceBuilder;
import io.github.Redouane59.dz.model.generator.PVD.PVDSentenceBuilder;
import io.github.Redouane59.dz.model.generator.PVN.PVNSentenceBuilder;
import io.github.Redouane59.dz.model.generator.QVP.QVPSentenceBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SentenceType {
  PV(false, new PVSentenceBuilder()),
  PVA(true, new PVASentenceBuilder()),
  PVD(true, new PVDSentenceBuilder()),
  NVA(true, new NVASentenceBuilder()),
  PVN(false, new PVNSentenceBuilder()),
  QVP(false, new QVPSentenceBuilder());

  private boolean                 needsStateVerb;
  private AbstractSentenceBuilder sentenceBuilder;

}
