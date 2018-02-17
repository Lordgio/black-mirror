package pl.hypeapps.blackmirror.network;

import pl.hypeapps.blackmirror.model.weather.WeatherResponse;
import retrofit2.Call;

/**
 * Interfejs służący do komunikacji pomiędzy warstwą
 * dostępu do informacji pogodowych.
 */
public interface WeatherRepository {

    Call<WeatherResponse> getWeatherByCityName(String cityName, String units, String lang);

}
