package pl.hypeapps.blackmirror.network;

import java.util.List;

import pl.hypeapps.blackmirror.model.news.News;
import retrofit2.Call;

/**
 * Interfejs służący do komunikacji pomiędzy warstwą
 * dostępu do danych o wiadomościach ze świata.
 */
public interface NewsRepository {

    Call<List<News>> getTvnNews();

   Call<List<News>> getPolsatNews();

}
