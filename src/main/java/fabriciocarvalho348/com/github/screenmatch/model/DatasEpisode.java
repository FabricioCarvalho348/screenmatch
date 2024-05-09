package fabriciocarvalho348.com.github.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatasEpisode(@JsonAlias("Title") String titulo,
                           @JsonAlias("Episode") Integer numero,
                           @JsonAlias("imdbRating") String avaliacao,
                           @JsonAlias("Released") String dataLancamento) {
}
