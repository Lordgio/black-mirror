package pl.hypeapps.blackmirror.network;


import pl.hypeapps.blackmirror.model.location.TimeZone;
import retrofit2.Call;

/**
 * Interfejs służący do komunikacji pomiędzy warstwą
 * dostępu do danych lokalizacyjnych.
 */
public interface LocationRepository {

    Call<TimeZone> getTimeZoneByLocationName(String location);

}
