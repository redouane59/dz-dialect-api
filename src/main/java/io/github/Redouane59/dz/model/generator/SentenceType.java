package io.github.Redouane59.dz.model.generator;

import io.github.Redouane59.dz.model.generator.NVA.NVASentenceBuilder;
import io.github.Redouane59.dz.model.generator.PV.PVSentenceBuilder;
import io.github.Redouane59.dz.model.generator.PVA.PVASentenceBuilder;
import io.github.Redouane59.dz.model.generator.PVD.PVDSentenceBuilder;
import io.github.Redouane59.dz.model.generator.PVN.PVNSentenceBuilder;
import io.github.Redouane59.dz.model.generator.PVO.PVOSentenceBuilder;
import io.github.Redouane59.dz.model.generator.QVP.QVPSentenceBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SentenceType {
  PV(new PVSentenceBuilder()),
  PVA(new PVASentenceBuilder()),
  PVD(new PVDSentenceBuilder()),
  NVA(new NVASentenceBuilder()),
  PVN(new PVNSentenceBuilder()),
  QVP(new QVPSentenceBuilder()),
  PVO(new PVOSentenceBuilder());

  private AbstractSentenceBuilder sentenceBuilder;

}
