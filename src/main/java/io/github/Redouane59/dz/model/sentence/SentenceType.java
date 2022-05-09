package io.github.Redouane59.dz.model.sentence;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SentenceType {

  NV(new SentenceBuilder("src/main/resources/sentences/nv_sentence.json")),
  NVA(new SentenceBuilder("src/main/resources/sentences/nva_sentence.json")),
  PVS(new SentenceBuilder("src/main/resources/sentences/pvs_sentence.json")),
  PVSN(new SentenceBuilder("src/main/resources/sentences/pvsn_sentence.json")),
  PV(new SentenceBuilder("src/main/resources/sentences/pv_sentence.json")),
  PVA(new SentenceBuilder("src/main/resources/sentences/pva_sentence.json")),
  PVD(new SentenceBuilder("src/main/resources/sentences/pvd_sentence.json")),
  NVD(new SentenceBuilder("src/main/resources/sentences/nvd_sentence.json")),
  PVN(new SentenceBuilder("src/main/resources/sentences/pvn_sentence.json")),
  PVN_DEP(new SentenceBuilder("src/main/resources/sentences/pvn_deplacement_sentence.json")),
  PVN3_STA(new SentenceBuilder("src/main/resources/sentences/pvn_state_sentence.json")),
  QPV(new SentenceBuilder("src/main/resources/sentences/qpv_sentence.json")),
  V(new SentenceBuilder("src/main/resources/sentences/v_sentence.json"));

  private SentenceBuilder sentenceBuilder;

  public static List<SentenceBuilder> getSentenceBuilders() {
    return Arrays.stream(values()).map(SentenceType::getSentenceBuilder).collect(Collectors.toList());
  }

}
