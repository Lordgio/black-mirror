package pl.hypeapps.blackmirror.network.rss.news;

import java.util.List;

import pl.hypeapps.blackmirror.model.news.News;
import pl.hypeapps.blackmirror.network.NewsRepository;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Klasa warstwy dostępu do danych wiadomości ze świata.
 */
public class NewsDataSource implements NewsRepository {

    private Tvn24Rss tvn24Rss;

    private PolsatNewsRss polsatNewsRss;

    public NewsDataSource() {
        tvn24Rss = new Retrofit.Builder()
                .baseUrl("https://www.tvn24.pl/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
                .create(Tvn24Rss.class);
        polsatNewsRss = new Retrofit.Builder()
                .baseUrl("http://www.polsatnews.pl/rss/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()
                .create(PolsatNewsRss.class);
    }

    @Override
    public Call<List<News>> getTvnNews() {
        return null;
    }

    @Override
    public Call<List<News>> getPolsatNews() {
        return null;
    }
}
