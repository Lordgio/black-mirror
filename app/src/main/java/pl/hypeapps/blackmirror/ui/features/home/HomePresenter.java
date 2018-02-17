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
 * A class whose task is to manipulate the view and manage the layer
 * access to data.
 */
public class HomePresenter extends Presenter<HomeView> implements TextCommandInterpreter.Listener {

    private static final String TAG = "HomePresenter";

    private final WeatherRepository weatherDataSource = new WeatherDataSource();

    private final LocationRepository locationDataSource = new LocationDataSource();

    private final NewsRepository newsDataSource = new NewsDataSource();

    private TextCommandInterpreter textCommandInterpreter = new TextCommandInterpreter(this);

    /**
     * An event performed when speech recognition occurs.
     * The parameter is used to interpret the command.
     *
     * @param result the result of speech recognition.
     */
    void onSpeechRecognized(String result) {
        textCommandInterpreter.interpret(result);
    }

    /**
     * The method performed when creating a view.
     */
    @Override
    protected void onAttachView(HomeView view) {
        super.onAttachView(view);
        this.view.startSplashScreen();
    }

    /**
     * The method performed when destroying the view.
     */
    @Override
    protected void onDetachView() {
        super.onDetachView();
    }

    /**
     * The method called when the command recognition fails.
     * Shows error message.
     */
    @Override
    public void onFailureCommandRecognizing() {
        Log.e(TAG, "Text command interpreter failed to recognize command");
        this.view.showError("Incorrect command");
    }

    /**
     * The method is called when the weather display command is recognized.
     * Shows the widget.
     */
    @Override
    public void onShowWeatherCommandRecognized(String location) {
        Log.i(TAG, "Text command interpreter recognized weather command for location: " + location);
    }

    /**
     * The method is called when the weather conceal command is recognized.
     * Hides the widget.
     */
    @Override
    public void onHideWeatherCommandRecognized() {
        Log.i(TAG, "Text command interpreter recognized hide weather widget command.");
        this.view.hideWeatherWidget();
    }

    /**
     * The method is called when the clock display command is recognized.
     * Registers the REST request observer.
     * @param location location for which time is to be shown.
     */
    @Override
    public void onShowTimeCommandRecognized(String location) {
        Log.i(TAG, "Text command interpreter recognized time command for location: " + location);
    }

    /**
     * The method is called when the clock hide command is recognized.
     * Hides the widget.
     */
    @Override
    public void onHideTimeCommandRecognized() {
        Log.i(TAG, "Text command interpreter recognized hide time widget command.");
        this.view.hideTimeWidget();
    }

    /**
     * The method is called when the command to show the calendar is recognized.
     * Shows the calendar.
     */
    @Override
    public void onShowCalendarCommandRecognized() {
        Log.i(TAG, "Text command interpreter recognized show calendar command.");
        this.view.showCalendarWidget();
    }

    /**
     * The method is called when the calendar hide command is recognized.
     * Hides the calendar.
     */
    @Override
    public void onHideCalendarCommandRecognized() {
        Log.i(TAG, "Text command interpreter recognized hide calendar command.");
        this.view.hideCalendarWidget();
    }

    /**
     * The method is called when the command to show the next month is recognized.
     * Changes the current calendar month to the next.
     */
    @Override
    public void onNextMonthCommandRecognized() {
        Log.i(TAG, "Text command interpreter recognized calendar next month command.");
        this.view.setCalendarNextMonth();
    }

    /**
     * The method is called when the previous month's command is recognized.
     * Changes the current calendar month to the previous one.
     */
    @Override
    public void onPreviousMonthRecognized() {
        Log.i(TAG, "Text command interpreter recognized calendar previous month command.");
        this.view.setCalendarPreviousMonth();
    }

    /**
     * The method called when the command to show the news widget is recognized.
     * Shows the widget.
     */
    @Override
    public void onShowNewsCommandRecognized() {
        Log.i(TAG, "Text command interpreter recognized show all news command.");
        callPolsatNews();
        callTvnNews();
    }

    /**
     * The method is called when the hidden command of the message widget is recognized.
     * Hides the widget.
     */
    @Override
    public void onHideNewsCommandRecognized() {
        Log.i(TAG, "Text command interpreter recognized hide all news command.");
        this.view.hideAllNewsWidget();
    }

    /**
     * The method is called when the command to show the tvn news widget is recognized.
     * Shows the widget with the tvn channel.
     */
    @Override
    public void onShowTvnNewsCommandRecognized() {
        Log.i(TAG, "Text command interpreter recognized show tvn news command.");
        callTvnNews();
    }

    /**
     * The method is called when the command to show the news widget of the polsat channel is recognized.
     * Shows widget with polsat channel.
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
