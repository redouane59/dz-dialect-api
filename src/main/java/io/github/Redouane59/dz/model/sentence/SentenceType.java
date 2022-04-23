package io.github.Redouane59.dz.model.sentence;

import io.github.Redouane59.dz.model.sentence.NVA.NVASentenceBuilder;
import io.github.Redouane59.dz.model.sentence.PV.PVSentenceBuilder;
import io.github.Redouane59.dz.model.sentence.PVA.PVASentenceBuilder;
import io.github.Redouane59.dz.model.sentence.PVD.PVDSentenceBuilder;
import io.github.Redouane59.dz.model.sentence.PVN.PVNSentenceBuilder;
import io.github.Redouane59.dz.model.sentence.PVSO.PVSOSentenceBuilder;
import io.github.Redouane59.dz.model.sentence.QVP.QVPSentenceBuilder;
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
  PVOS(new PVSOSentenceBuilder());

  private ISentenceBuilder sentenceBuilder;

}
