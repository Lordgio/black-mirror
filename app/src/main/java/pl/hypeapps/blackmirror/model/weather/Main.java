package pl.hypeapps.blackmirror.model.weather;

import com.google.gson.annotations.SerializedName;

/**
 * Model reprezentujący parametry główne pogody.
 */
public class Main {

    public Double temp;

    public Double pressure;

    public Double humidity;

    @SerializedName("temp_min")
    public Double tempMin;

    @SerializedName("temp_max")
    public Double tempMax;
}
