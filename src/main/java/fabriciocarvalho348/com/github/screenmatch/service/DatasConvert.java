package fabriciocarvalho348.com.github.screenmatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DatasConvert implements IDataConvert {
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T obtainDatas(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);

        } catch(JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
