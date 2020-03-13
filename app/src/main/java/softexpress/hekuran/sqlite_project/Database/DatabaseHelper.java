package softexpress.hekuran.sqlite_project.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import softexpress.hekuran.sqlite_project.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = Config.DATABASE_NAME;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static synchronized DatabaseHelper getInstance(Context context){
        if(databaseHelper==null){
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_PERSON_TABLE = "CREATE TABLE " + Config.TABLE_PERSON + "("
                + Config.COLUMN_PERSON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_PERSON_FIRST_NAME + " TEXT NOT NULL, "
                + Config.COLUMN_PERSON_LAST_NAME + " TEXT NOT NULL, "
                + Config.COLUMN_PERSON_PHONE + " TEXT, " //nullable
                + Config.COLUMN_PERSON_GENDER + " TEXT, " //nullable
                + Config.COLUMN_PERSON_EMPLOYED + " INTEGER, " //nullable
                + Config.COLUMN_PERSON_BIRTHDATE + " TEXT, " //nullable
                + Config.COLUMN_PERSON_BIRTH_PLACE + " TEXT, " //nullable
                + Config.COLUMN_PERSON_MARTIAL_STATUS + " TEXT " //nullable
                + ")";

        Logger.d("Table create SQL: " + CREATE_PERSON_TABLE);

        db.execSQL(CREATE_PERSON_TABLE);

        Logger.d("DB created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_PERSON);
        onCreate(db);
    }

}
