package fabriciocarvalho348.com.github.screenmatch.principal;

import fabriciocarvalho348.com.github.screenmatch.model.DatasSeason;
import fabriciocarvalho348.com.github.screenmatch.model.DatasSerie;
import fabriciocarvalho348.com.github.screenmatch.service.ConsumoAPI;
import fabriciocarvalho348.com.github.screenmatch.service.DatasConvert;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private DatasConvert convert = new DatasConvert();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";;
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
        temporadas.forEach(System.out::println);
    }
}
