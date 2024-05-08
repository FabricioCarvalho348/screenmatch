package fabriciocarvalho348.com.github.screenmatch.service;

public interface IDataConvert {
    <T> T obtainDatas(String json, Class<T> classe);
}
