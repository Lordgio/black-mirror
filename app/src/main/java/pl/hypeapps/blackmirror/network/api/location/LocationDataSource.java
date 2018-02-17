package pl.hypeapps.blackmirror.network.api.location;

import pl.hypeapps.blackmirror.BuildConfig;
import pl.hypeapps.blackmirror.model.location.TimeZone;
import pl.hypeapps.blackmirror.network.LocationRepository;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Klasa warstwy dostępu do danych lokalizacyjnych tj. strefa czasaowa,
 * długość geograficzna oraz szerokość geograficzna.
 */
public class LocationDataSource implements LocationRepository {

    private static final String TIME_ZONE_DB_API_KEY = BuildConfig.TIMEZONEDB_API_KEY;

    private static final String GOOGLE_GEO_API_KEY = BuildConfig.GOOGLE_GEO_API_KEY;

    private final GoogleGeoApi googleGeoApi;

    private final TimeZoneDbApi timeZoneDbApi;

    public LocationDataSource() {
        googleGeoApi = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GoogleGeoApi.class);
        timeZoneDbApi = new Retrofit.Builder()
                .baseUrl("http://api.timezonedb.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TimeZoneDbApi.class);
    }

    @Override
    public Call<TimeZone> getTimeZoneByLocationName(String location) {
        return null;
    }
}
