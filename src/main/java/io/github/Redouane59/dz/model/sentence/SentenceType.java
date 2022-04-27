package io.github.Redouane59.dz.model.sentence;

import io.github.Redouane59.dz.model.sentence.V2.SentenceBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SentenceType {

  NV(new SentenceBuilder("nv_sentence.json")),
  NVA(new SentenceBuilder("nva_sentence.json")),
  PV(new SentenceBuilder("pv_sentence.json")),
  PVA(new SentenceBuilder("pva_sentence.json")),
  PVN(new SentenceBuilder("pvn_sentence.json")),
  V(new SentenceBuilder("v_sentence.json"));


  private SentenceBuilder sentenceBuilder;

}
