package pl.hypeapps.blackmirror.network.api.weather;

import pl.hypeapps.blackmirror.model.weather.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interfejs reprezentujący połączenie z WeatherApi.
 */
public interface WeatherApi {

    @GET(value = "weather")
    Call<WeatherResponse> getWeatherByCityName(@Query("APPID") String apiKey,
                                               @Query("q") String city,
                                               @Query("units") String units,
                                               @Query("lang") String lang);

}
