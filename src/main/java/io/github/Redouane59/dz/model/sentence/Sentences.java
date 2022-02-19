package io.github.Redouane59.dz.model.sentence;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Sentences {

  private List<Sentence> sentences;
  private int            count;
  @JsonInclude(Include.NON_NULL)
  private List<String>   errors;

}
