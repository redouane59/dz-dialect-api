package io.github.Redouane59.dz.model.sentence;

import io.github.Redouane59.dz.model.sentence.V2.SentenceBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SentenceType {

  NVA(new SentenceBuilder("nva_sentence.json"));
  // PV(new PVSentenceBuilder()),
  // NV(new NVSentenceBuilder()),
  // PV(new PVSentenceBuilder())
/*  ,
  PVA(new PVASentenceBuilder()),
  PVD(new PVDSentenceBuilder()),
  NVA(new NVASentenceBuilder()),
  PVN(new PVNSentenceBuilder()),
  QVP(new QVPSentenceBuilder()),
  PVOS(new PVSOSentenceBuilder())*/

  private SentenceBuilder sentenceBuilder;

}
