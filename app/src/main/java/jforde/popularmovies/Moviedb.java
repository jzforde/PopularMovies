package jforde.popularmovies;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by jillianforde on 5/21/16.
 */
public class Moviedb extends Application{
    public RealmConfiguration realmConfiguration;
    @Override
    public void onCreate() {
        super.onCreate();
        realmConfiguration = new RealmConfiguration().Builder(this)
                .name("movies.realm")
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
