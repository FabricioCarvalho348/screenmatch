package fabriciocarvalho348.com.github.screenmatch.principal;

import fabriciocarvalho348.com.github.screenmatch.model.DatasEpisode;
import fabriciocarvalho348.com.github.screenmatch.model.DatasSeason;
import fabriciocarvalho348.com.github.screenmatch.model.DatasSerie;
import fabriciocarvalho348.com.github.screenmatch.model.Episode;
import fabriciocarvalho348.com.github.screenmatch.service.ConsumoAPI;
import fabriciocarvalho348.com.github.screenmatch.service.DatasConvert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private DatasConvert convert = new DatasConvert();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=56f41d3d";

    public void exibeMenu() throws InterruptedException {
        System.out.println("Digite o nome da série para busca: ");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DatasSerie dados = convert.obtainDatas(json, DatasSerie.class);
        System.out.println(dados);

        List<DatasSeason> temporadas = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
            DatasSeason datasSeason = convert.obtainDatas(json, DatasSeason.class);
            temporadas.add(datasSeason);
        }
        temporadas.forEach(System.out::println);

//        for (int i = 0; i < dados.totalTemporadas(); i++) {
//            List<DatasEpisode> episodesSeasons = temporadas.get(i).episodios();
//            for (int j = 0; j < episodesSeasons.size(); j++) {
//                System.out.println(episodesSeasons.get(j).titulo());
//            }
//        }

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DatasEpisode> datasEpisodesList = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        datasEpisodesList.stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DatasEpisode::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episode> episodeList = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episode(t.numero(), d)))
                .collect(Collectors.toList());

        episodeList.forEach(System.out::println);

        System.out.println("Digite o titulo de algum episódio: ");

        var trechoTitulo = leitura.nextLine();
        Optional<Episode> searchedEpisode = episodeList.stream()
                .filter(e -> e.getTitle().contains(trechoTitulo.toUpperCase()))
                .findFirst();
        if(searchedEpisode.isPresent()) {
            System.out.println("Episódio encontrado!");
            System.out.println("Temporada: " + searchedEpisode.get().getSeason());
        } else {
            System.out.println("Episódio não encotrado!");
        }

        System.out.println("A partir de que ano você deseja ver os episódios? ");
        var ano = leitura.nextInt();
        leitura.nextLine();

        LocalDate searchDate = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodeList.stream()
                .filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(searchDate))
                .forEach(e -> System.out.println("Temporada: " + e.getSeason() +
                        " Episódio: " + e.getTitle() +
                        " Data de lançamento: " + e.getReleaseDate().format(formatter)
                ));

        Map<Integer, Double> ratingBySeason = episodeList.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.groupingBy(Episode::getSeason,
                        Collectors.averagingDouble(Episode::getRating)));
        System.out.println(ratingBySeason);

        DoubleSummaryStatistics est = episodeList.stream()
                .filter(e -> e.getRating() > 0.0)
                .collect(Collectors.summarizingDouble(Episode::getRating));
        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor episódio: " + est.getMax());
        System.out.println("Pior episódio: " + est.getMin());
        System.out.println("Quantidade: " + est.getCount());
    }
}
