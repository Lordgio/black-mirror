package pl.hypeapps.blackmirror.network.rss.news;

import pl.hypeapps.blackmirror.model.news.Tvn24News;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 *  Interfejs reprezentujący połączenie z kanałem TVN.
 */
public interface Tvn24Rss {

    @GET("/najnowsze.xml")
    Call<Tvn24News> getNews();

}
