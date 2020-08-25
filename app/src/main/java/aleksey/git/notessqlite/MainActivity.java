package aleksey.git.notessqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private ImageButton mShowDialog;
    private AlertDialog.Builder mBuilder;
    private View mView;
    private TextInputLayout note;
    private Button btnAdd;
    private Button btnCancel;
    private AlertDialog dialog;
    private SimpleDateFormat formatter;
    private Date date;
    private TextView noNotesText;
    private TextView noNotesDescr;
    private ImageButton addImage, addImage2;
    DBHelper dbHelper;
    SQLiteDatabase database;
    ContentValues contentValues;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
        cursor = database.query(DBHelper.TABLE_NAME, null, null, null, null, null, null);
        contentValues = new ContentValues();

        noNotesText = findViewById(R.id.noNotesText);
        noNotesDescr = findViewById(R.id.noNotesDescr);

        addImage = findViewById(R.id.btnAddNote);
        addImage2 = findViewById(R.id.btnAddNote2);

        mBuilder = new AlertDialog.Builder(MainActivity.this);
        mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
        note = mView.findViewById(R.id.noteField);
        btnAdd = mView.findViewById(R.id.btnAdd);
        btnCancel = mView.findViewById(R.id.btnCancel);
        mBuilder.setView(mView);
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        date = new Date();
        dialog = mBuilder.create();
        if(addImage.getVisibility() == View.VISIBLE){
            addImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.show();
                    btnAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(note.getEditText().getText().toString().trim().equals("")){
                                Snackbar.make(view, "Field can't be empty", Snackbar.LENGTH_LONG).show();
                            } else{
                                addNote();
                            }
                        }
                    });
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            dataToConsole();
                        }
                    });
                }
            });
        } else{
            addImage2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.show();
                    btnAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(note.getEditText().getText().toString().trim().equals("")){
                                Snackbar.make(view, "Field can't be empty", Snackbar.LENGTH_LONG).show();
                            } else{
                                addNote();
                            }
                        }
                    });
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            dataToConsole();
                        }
                    });
                }
            });
        }

        checkIfMoreThanOneNote();
    }

    public void delete(){
        database.delete(DBHelper.TABLE_NAME, null, null);
    }

    public void checkIfMoreThanOneNote(){
        if(1 > cursor.getColumnIndex(DBHelper.ID)){
            noNotesText.setVisibility(View.VISIBLE);
            noNotesDescr.setVisibility(View.VISIBLE);
        } else{
            noNotesText.setVisibility(View.GONE);
            noNotesDescr.setVisibility(View.GONE);
            addImage.setVisibility(View.GONE);
            addImage2.setVisibility(View.VISIBLE);
        }
    }

    public void addNote(){
            contentValues.put(DBHelper.NOTE, note.getEditText().getText().toString());
            contentValues.put(DBHelper.TIME, formatter.format(date));
            database.insert(DBHelper.TABLE_NAME, null, contentValues);
            database.update(DBHelper.TABLE_NAME, contentValues, null, null);
    }

    public void dataToConsole(){
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.ID);
            int nameIndex = cursor.getColumnIndex(DBHelper.NOTE);
            int emailIndex = cursor.getColumnIndex(DBHelper.TIME);
            do {
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", note = " + cursor.getString(nameIndex) +
                        ", time = " + cursor.getString(emailIndex));
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
            cursor.close();
        }
    }
}