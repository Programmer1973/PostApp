package ru.dudin.postapp.OrmDatabase;

/**
 * @created 12.03.2019
 * @author Andrey Dudin <programmer1973@mail.ru>
 * @version 0.1.0.2019.03.12
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = PostOrm.TABLE_NAME)
public class PostOrm {

    public static final String TABLE_NAME = "Post";
    public static final String COLUMN_NAME_ID = "_id";
    public static final String COLUMN_NAME_MESSAGE = "message";
    public static final String COLUMN_NAME_CREATED_TIME =  "createdTime";


    @DatabaseField(generatedId = true, columnName = PostOrm.COLUMN_NAME_ID)
    private int mId;

    @DatabaseField(columnName = PostOrm.COLUMN_NAME_MESSAGE)
    private String mMessage;

    @DatabaseField(columnName = PostOrm.COLUMN_NAME_CREATED_TIME)
    private String mCreatedTime;


    public int getId() {
        return mId;
    }

    public void setId(final int mId) {
        this.mId = mId;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getCreatedTime() {
        return mCreatedTime;
    }

    public void setCreatedTime(String mCreatedTime) {
        this.mCreatedTime = mCreatedTime;
    }
}