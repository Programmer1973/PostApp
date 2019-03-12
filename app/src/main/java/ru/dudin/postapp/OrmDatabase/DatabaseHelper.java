package ru.dudin.postapp.OrmDatabase;

/**
 * @created 12.03.2019
 * @author Andrey Dudin <programmer1973@mail.ru>
 * @version 0.1.0.2019.03.12
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static DatabaseHelper sInstance;

    public static final String NAME = "database.db";
    public static final int VERSION = 1;

    public static void createInstance(final Context context) {
        sInstance = new DatabaseHelper(context);
    }

    public static DatabaseHelper getInstance() {
        return sInstance;
    }

    public DatabaseHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, PostOrm.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(final SQLiteDatabase sqLiteDatabase, final ConnectionSource connectionSource, final int i, final int i1) {
        // do nothing
    }
}