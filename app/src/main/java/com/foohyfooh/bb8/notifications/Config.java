package com.foohyfooh.bb8.notifications;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Config {

    private String hexColour;
    private @Pattern String pattern;

    public Config(String hexColour, @Pattern String pattern) {
        this.hexColour = hexColour;
        this.pattern = pattern;
    }

    public Config() {
        this("#ffffff", BB8CommandService.ACTION_BLINK);
    }

    public String getHexColour() {
        return hexColour;
    }

    public void setHexColour(String hexColour) {
        this.hexColour = hexColour;
    }

    public @Pattern String getPattern() {
        return pattern;
    }

    public void setPattern(@Pattern String pattern) {
        this.pattern = pattern;
    }

    @StringDef({BB8CommandService.ACTION_BLINK, BB8CommandService.ACTION_FLASH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Pattern{}

}
