package pl.hypeapps.blackmirror.ui.features.home;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pl.hypeapps.blackmirror.R;
import pl.hypeapps.blackmirror.model.news.News;
import pl.hypeapps.blackmirror.model.weather.WeatherResponse;
import pl.hypeapps.blackmirror.speechrecognition.googlespeechapi.SpeechRecognizer;
import pl.hypeapps.blackmirror.speechrecognition.sphinx.ActivationKeywordListener;
import pl.hypeapps.blackmirror.speechrecognition.sphinx.PocketSphinx;
import pl.hypeapps.blackmirror.ui.base.BaseActivity;
import pl.hypeapps.blackmirror.ui.widget.CalendarWidgetView;
import pl.hypeapps.blackmirror.ui.widget.CommandRecognizingAnimationView;
import pl.hypeapps.blackmirror.ui.widget.NewsWidgetView;
import pl.hypeapps.blackmirror.ui.widget.TimeWidgetView;
import pl.hypeapps.blackmirror.ui.widget.WeatherWidgetView;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * The class is responsible for the main screen of the application.
 */
public class HomeActivity extends BaseActivity implements HomeView,
        ActivationKeywordListener, SpeechRecognizer.Listener {

    /**
     * We provide references to the appropriate xml view.
     */
    @Override
    protected Integer getLayoutRes() {
        return R.layout.activity_home;
    }

    public HomePresenter homePresenter = new HomePresenter();

    @BindView(R.id.activation_keyword_indicator)
    public TextView activationKeywordTextIndicator;

    @BindView(R.id.command_recognizing_animation)
    public CommandRecognizingAnimationView commandRecognizingAnimation;

    @BindView(R.id.weather_widget)
    public WeatherWidgetView weatherWidget;

    @BindView(R.id.time_widget)
    public TimeWidgetView timeWidget;

    @BindView(R.id.calendar_widget)
    public CalendarWidgetView calendarWidget;

    @BindView(R.id.news_widget)
    public NewsWidgetView newsWidgetView;

    @BindView(R.id.active_speech_indicator)
    public ImageView activeSpeechIndicator;

    @BindView(R.id.welcome_view)
    public TextView welcomeView;

    private static final String TAG = "HomeActivity";

    private PocketSphinx pocketSphinx;

    private SpeechRecognizer commandSpeechRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pocketSphinx = new PocketSphinx(this, this);
        commandSpeechRecognizer = new SpeechRecognizer(this, this);
        homePresenter.onAttachView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        commandSpeechRecognizer.onStart();
    }

    @Override
    protected void onStop() {
        commandSpeechRecognizer.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pocketSphinx.onDestroy();
    }

    /**
     * An event that indicates that you are ready to listen for a keyword.
     */
    @Override
    public void onActivationKeywordRecognizerReady() {
        Log.i("ACTIVATION PHRASE ", " ACTIVATION READY ");
        activationKeywordTextIndicator.setVisibility(View.VISIBLE);
        pocketSphinx.startListeningToActivationKeyword();
    }

    /**
     * An event that indicates that a keyword has been detected.
     */
    @Override
    public void onActivationKeywordDetected() {
        Log.i("ACTIVATION PHRASE ", " DETECTED ");
        commandRecognizingAnimation.startAnimation();
        activationKeywordTextIndicator.setVisibility(INVISIBLE);
        activeSpeechIndicator.setVisibility(View.INVISIBLE);
        // Start listening to the command.
        commandSpeechRecognizer.startListeningCommand();
        if (welcomeView.getVisibility() == View.VISIBLE) {
            YoYo.with(Techniques.Tada)
                    .onEnd(new YoYo.AnimatorCallback() {
                        @Override
                        public void call(Animator animator) {
                            welcomeView.setVisibility(View.INVISIBLE);
                        }
                    }).playOn(welcomeView);
        }
    }

    /**
     * An event that speaks of speech recognition.
     * @param result in the parameter returns the result of recognizing the voice command.
     */
    @Override
    public void onSpeechRecognized(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // We would like to inform the presenter about the recognition of the voice command.
                homePresenter.onSpeechRecognized(result);
            }
        });
    }

    /**
     * An event that indicates that the voice command has been completed.
     */
    @Override
    public void onFinishSpeechRecognizing() {
        // we start listening to the keyword again.
        pocketSphinx.startListeningToActivationKeyword();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activationKeywordTextIndicator.setVisibility(VISIBLE);
                commandRecognizingAnimation.stopAnimation();
            }
        });
    }

    /**
     * An event that indicates speech detection while listening to a keyword.
     */
    @Override
    public void onActivationKeywordBeginningOfSpeech() {
        activeSpeechIndicator.setVisibility(View.VISIBLE);
    }

    /**
     * An event that announces the end of speech listening while listening to a keyword.
     */
    @Override
    public void onActivationKeywordEndOfSpeech() {
        activeSpeechIndicator.setVisibility(View.INVISIBLE);
    }

    /**
     * Shows the weather widget.
     */
    @Override
    public void showWeatherWidget(WeatherResponse weatherResponse) {
        weatherWidget.show(weatherResponse);
    }

    /**
     * Hides the weather widget.
     */
    @Override
    public void hideWeatherWidget() {
        weatherWidget.hide();
    }

    /**
     * Shows the time widget.
     */
    @Override
    public void showTimeWidget(String timeZone) {
        timeWidget.show(timeZone);
    }

    /**
     * Hides the time widget.
     */
    @Override
    public void hideTimeWidget() {
        timeWidget.hide();
    }

    /**
     * Shows widget with Tvn messages.
     */
    @Override
    public void showTvnNewsWidget(List<News> newsList) {
        newsWidgetView.setTvnNews(newsList);
    }

    /**
     * Shows widget with Polsat messages.
     */
    @Override
    public void showPolsatNewsWidget(List<News> news) {
        newsWidgetView.setPolsatNews(news);
    }

    /**
     * Hides all messages.
     */
    @Override
    public void hideAllNewsWidget() {
        newsWidgetView.hide();
    }

    /**
     * Shows the calendar widget.
     */
    @Override
    public void showCalendarWidget() {
        calendarWidget.show();
    }

    /**
     * Hides the calendar widget.
     */
    @Override
    public void hideCalendarWidget() {
        calendarWidget.hide();
    }

    /**
     * Changes the calendar month to the next.
     */
    @Override
    public void setCalendarNextMonth() {
        calendarWidget.nextMonth();
    }

    /**
     * Changes the calendar month to the previous one.
     */
    @Override
    public void setCalendarPreviousMonth() {
        calendarWidget.previousMonth();
    }

    /**
     * Displays an error notification.
     *
     * @param message in parameter returns error message.
     */
    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Closes the application window.
     */
    @OnClick(R.id.exit)
    public void exit() {
        this.finish();
    }

    /**
     * Restarts the application.
     */
    @OnClick(R.id.restart)
    public void restart() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    /**
     * Restarts the application.
     */
    @Override
    public void startSplashScreen() {
        welcomeView.setBackground(ContextCompat.getDrawable(this, R.drawable.splash_screen));
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f)
                .setDuration(3000);
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                welcomeView.setBackground(null);
                welcomeView.setText("Witaj!");
                YoYo.with(Techniques.ZoomIn).playOn(welcomeView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }
}
