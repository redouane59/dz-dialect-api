package io.github.Redouane59.dz.model.sentence;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.github.Redouane59.dz.model.word.Sentence;
import java.util.List;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@XmlRootElement(name = "Sentences")
public class Sentences {

  @Setter
  private List<Sentence> sentences;
  private int            count;
  @JsonInclude(Include.NON_NULL)
  @Setter
  private Set<String>    errors;

  public int getCount() {
    if (sentences == null || sentences.isEmpty()) {
      return 0;
    }
    return sentences.size();
  }

}
