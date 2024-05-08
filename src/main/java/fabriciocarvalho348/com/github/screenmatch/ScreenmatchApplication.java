package fabriciocarvalho348.com.github.screenmatch;

import fabriciocarvalho348.com.github.screenmatch.model.DataSerie;
import fabriciocarvalho348.com.github.screenmatch.service.ConsumoAPI;
import fabriciocarvalho348.com.github.screenmatch.service.DatasConvert;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumoAPI consumoAPI = new ConsumoAPI();
		var json = consumoAPI.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=56f41d3d");
//		System.out.println(json);
//		json = consumoAPI.obterDados("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);
		DatasConvert convert = new DatasConvert();
		DataSerie datas = convert.obtainDatas(json, DataSerie.class);
		System.out.println(datas);

	}
}
