package io.github.Redouane59.dz.model.sentence;

import io.github.Redouane59.dz.model.sentence.V2.AbstractSentenceBuilder;
import io.github.Redouane59.dz.model.sentence.V2.NVSentenceBuilder;
import io.github.Redouane59.dz.model.sentence.V2.PVSentenceBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SentenceType {
  // PV(new PVSentenceBuilder()),
  NV(new NVSentenceBuilder()),
  PV(new PVSentenceBuilder())
/*  ,
  PVA(new PVASentenceBuilder()),
  PVD(new PVDSentenceBuilder()),
  NVA(new NVASentenceBuilder()),
  PVN(new PVNSentenceBuilder()),
  QVP(new QVPSentenceBuilder()),
  PVOS(new PVSOSentenceBuilder())*/;

  private AbstractSentenceBuilder sentenceBuilder;

}
