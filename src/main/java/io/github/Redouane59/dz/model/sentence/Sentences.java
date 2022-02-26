package io.github.Redouane59.dz.model.sentence;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.github.Redouane59.dz.model.generator.AbstractSentence;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Sentences {

  private List<AbstractSentence> sentences;
  private int                    count;
  @JsonInclude(Include.NON_NULL)
  private Set<String>            errors;

}
