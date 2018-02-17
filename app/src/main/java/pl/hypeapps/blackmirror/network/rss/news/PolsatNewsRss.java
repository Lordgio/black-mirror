package pl.hypeapps.blackmirror.network.rss.news;

import pl.hypeapps.blackmirror.model.news.PolsatNews;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Interfejs reprezentujący połączenie z kanałem PolsatNews.
 */
public interface PolsatNewsRss {

    @GET("wszystkie.xml")
    Call<PolsatNews> getNews();
}
