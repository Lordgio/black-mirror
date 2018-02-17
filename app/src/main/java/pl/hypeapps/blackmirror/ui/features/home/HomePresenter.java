package pl.hypeapps.blackmirror.ui.features.home;

import android.util.Log;

import pl.hypeapps.blackmirror.network.LocationRepository;
import pl.hypeapps.blackmirror.network.NewsRepository;
import pl.hypeapps.blackmirror.network.WeatherRepository;
import pl.hypeapps.blackmirror.network.api.location.LocationDataSource;
import pl.hypeapps.blackmirror.network.api.weather.WeatherDataSource;
import pl.hypeapps.blackmirror.network.rss.news.NewsDataSource;
import pl.hypeapps.blackmirror.speechrecognition.TextCommandInterpreter;
import pl.hypeapps.blackmirror.ui.presenter.Presenter;

/**
 * Klasa, której zadaniem jest manipulowanie widokiem, oraz zarządzanie warstwą
 * dostępu do danych.
 */
public class HomePresenter extends Presenter<HomeView> implements TextCommandInterpreter.Listener {

    private static final String TAG = "HomePresenter";

    private final WeatherRepository weatherDataSource = new WeatherDataSource();

    private final LocationRepository locationDataSource = new LocationDataSource();

    private final NewsRepository newsDataSource = new NewsDataSource();

    private TextCommandInterpreter textCommandInterpreter = new TextCommandInterpreter(this);

    /**
     * Zdarzenie wykonywane kiedy dojdzie do rozpoznania mowy.
     * Parametr wykorzystywany jest do interpretacji komendy.
     *
     * @param result rezultat rozpoznawania mowy.
     */
    void onSpeechRecognized(String result) {
        textCommandInterpreter.interpret(result);
    }

    /**
     * Metoda wykonywana kiedy tworzymy widok.
     */
    @Override
    protected void onAttachView(HomeView view) {
        super.onAttachView(view);
        this.view.startSplashScreen();
    }

    /**
     * Metoda wykonywana kiedy niszczymy widok.
     */
    @Override
    protected void onDetachView() {
        super.onDetachView();
    }

    /**
     * Metoda wywoływana kiedy rozpoznawanie komendy zakończy się niepowodzeniem.
     * Pokazuje komunikat o błędzie.
     */
    @Override
    public void onFailureCommandRecognizing() {
        Log.e(TAG, "Text command interpreter failed to recognize command");
        this.view.showError("Niepoprawna komenda");
    }

    /**
     * Metoda wywoływana kiedy rozpoznana zostanie komenda pokazania pogody.
     * Pokazuje widżet.
     */
    @Override
    public void onShowWeatherCommandRecognized(String location) {
        Log.i(TAG, "Text command interpreter recognized weather command for location: " + location);
    }

    /**
     * Metoda wywoływana kiedy rozpoznana zostanie komenda ukrycia pogody.
     * Ukrywa widżet.
     */
    @Override
    public void onHideWeatherCommandRecognized() {
        Log.i(TAG, "Text command interpreter recognized hide weather widget command.");
        this.view.hideWeatherWidget();
    }

    /**
     * Metoda wywoływana kiedy rozpoznana zostanie komenda pokazania zegara.
     * Rejestruje obserwatora żądania REST.
     * @param location lokalizacja dla, której ma zostać pokazany czas.
     */
    @Override
    public void onShowTimeCommandRecognized(String location) {
        Log.i(TAG, "Text command interpreter recognized time command for location: " + location);
    }

    /**
     * Metoda wywoływana kiedy rozpoznana zostanie komenda ukrycia zegara.
     * Ukrywa widżet.
     */
    @Override
    public void onHideTimeCommandRecognized() {
        Log.i(TAG, "Text command interpreter recognized hide time widget command.");
        this.view.hideTimeWidget();
    }

    /**
     * Metoda wywoływana kiedy rozpoznana zostanie komenda pokazania kalendarza.
     * Pokazuje kalendarz.
     */
    @Override
    public void onShowCalendarCommandRecognized() {
        Log.i(TAG, "Text command interpreter recognized show calendar command.");
        this.view.showCalendarWidget();
    }

    /**
     * Metoda wywoływana kiedy rozpoznana zostanie komenda ukrycia kalendarza.
     * Ukrywa kalendarz.
     */
    @Override
    public void onHideCalendarCommandRecognized() {
        Log.i(TAG, "Text command interpreter recognized hide calendar command.");
        this.view.hideCalendarWidget();
    }

    /**
     * Metoda wywoływana kiedy rozpoznana zostanie komenda pokazania kolejnego miesiąca.
     * Zmienia aktualny miesiąć kalendarza na kolejny.
     */
    @Override
    public void onNextMonthCommandRecognized() {
        Log.i(TAG, "Text command interpreter recognized calendar next month command.");
        this.view.setCalendarNextMonth();
    }

    /**
     * Metoda wywoływana kiedy rozpoznana zostanie komenda pokazania poprzedniego miesiąca.
     * Zmienia aktualny miesiąć kalendarza na poprzedni.
     */
    @Override
    public void onPreviousMonthRecognized() {
        Log.i(TAG, "Text command interpreter recognized calendar previous month command.");
        this.view.setCalendarPreviousMonth();
    }

    /**
     * Metoda wywoływana kiedy rozpoznana zostanie komenda pokazania widżetu wiadomości.
     * Pokazuje widżet.
     */
    @Override
    public void onShowNewsCommandRecognized() {
        Log.i(TAG, "Text command interpreter recognized show all news command.");
        callPolsatNews();
        callTvnNews();
    }

    /**
     * Metoda wywoływana kiedy rozpoznana zostanie komenda ukrycia widżetu wiadomości.
     * Ukrywa widżet.
     */
    @Override
    public void onHideNewsCommandRecognized() {
        Log.i(TAG, "Text command interpreter recognized hide all news command.");
        this.view.hideAllNewsWidget();
    }

    /**
     * Metoda wywoływana kiedy rozpoznana zostanie komenda pokazania widżetu wiadomości kanału tvn.
     * Pokazuje widżet z kanałem tvn.
     */
    @Override
    public void onShowTvnNewsCommandRecognized() {
        Log.i(TAG, "Text command interpreter recognized show tvn news command.");
        callTvnNews();
    }

    /**
     * Metoda wywoływana kiedy rozpoznana zostanie komenda pokazania widżetu wiadomości kanału polsat.
     * Pokazuje widżet z kanałem polsat.
     */
    @Override
    public void onShowPolsatNewsCommandRecognized() {
        Log.i(TAG, "Text command interpreter recognized show polsat news command.");
        callPolsatNews();
    }

    private void callTvnNews() {
        Log.i(TAG, "Text command interpreter recognized news command for tvn news, polsat hide if visible");
    }

    private void callPolsatNews() {
        Log.i(TAG, "Text command interpreter recognized news command for polsat news, tvn24 hide if visible");
    }
}
