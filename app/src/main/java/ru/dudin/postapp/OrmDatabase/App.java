package ru.dudin.postapp.OrmDatabase;

/**
 * @created 12.03.2019
 * @author Andrey Dudin <programmer1973@mail.ru>
 * @version 0.1.0.2019.03.12
 */

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseHelper.createInstance(this);
    }
}