package fabriciocarvalho348.com.github.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatasSeason(@JsonAlias("Season") Integer numero,
                          @JsonAlias("Episodes") List<DatasEpisode> episodios) {

}
