package aleksey.git.notessqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DBOperations extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase database;
    ContentValues contentValues;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opers);
    }

    public void addNote(TextInputLayout note, SimpleDateFormat formatter, Date date){
        database = dbHelper.getWritableDatabase();
        cursor = database.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        contentValues = new ContentValues();
        contentValues.put(DBHelper.NOTE, note.getEditText().getText().toString());
        contentValues.put(DBHelper.TIME, formatter.format(date));
        database.insert(DBHelper.TABLE_NAME, null, contentValues);
    }

    public void dataToConsole(){
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.NOTE);
            int emailIndex = cursor.getColumnIndex(DBHelper.TIME);
            do {
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", note = " + cursor.getString(nameIndex) +
                        ", email = " + cursor.getString(emailIndex));
            } while (cursor.moveToNext());
        } else
            Log.d("mLog","0 rows");

        cursor.close();
    }

}
