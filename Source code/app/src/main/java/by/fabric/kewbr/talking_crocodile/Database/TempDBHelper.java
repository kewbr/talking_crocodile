package by.fabric.kewbr.talking_crocodile.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import by.fabric.kewbr.talking_crocodile.Model.WordModel;

public class TempDBHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "words_selection";
    private static final String KEY_ID = "_id";
    private static final String KEY_WORD = "word";
    private static final String KEY_GUESSED = "guessed";

    public TempDBHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableRequest = "CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_WORD + " TEXT, " +
                KEY_GUESSED + "INTEGER)";
        db.execSQL(createTableRequest);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropTable);
    }

    public boolean addData(WordModel item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_WORD, item.getWord());
        contentValues.put(KEY_GUESSED, item.getGuessed());

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }
}
