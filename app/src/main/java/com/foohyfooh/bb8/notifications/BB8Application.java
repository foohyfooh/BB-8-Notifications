package com.foohyfooh.bb8.notifications;

import android.app.Application;

public class BB8Application extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ConfigManager.loadConfigsFromDatabase(this);
    }
}
