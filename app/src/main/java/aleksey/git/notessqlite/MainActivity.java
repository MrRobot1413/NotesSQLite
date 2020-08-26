package aleksey.git.notessqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder mBuilder, mBuilder2;
    private View mView, mView2;
    private TextInputLayout note;
    private Button btnAdd, btnCancel, btnDelete, btnCancel2;
    private AlertDialog dialog, dialog2;
    private SimpleDateFormat formatter;
    private Date date;
    private TextView noNotesText;
    private TextView noNotesDescr;
    private ImageButton addImage, addImage2, deleteImage;
    private RecyclerView NotesList;
    DBHelper dbHelper;
    SQLiteDatabase database;
    ContentValues contentValues;
    Cursor cursor;
    List<Note> notes = new ArrayList<>();
    DataAdapter adapter;

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
        deleteImage = findViewById(R.id.btnDeleteAll);


        mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder2 = new AlertDialog.Builder(MainActivity.this);
        mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
        mView2 = getLayoutInflater().inflate(R.layout.dialog_delete, null);
        note = mView.findViewById(R.id.noteField);
        btnAdd = mView.findViewById(R.id.btnAdd);
        btnCancel = mView.findViewById(R.id.btnCancel);
        btnDelete = mView2.findViewById(R.id.btnDelete);
        btnCancel2 = mView2.findViewById(R.id.btnCancel2);
        mBuilder.setView(mView);
        mBuilder2.setView(mView2);
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        date = new Date();
        dialog = mBuilder.create();
        dialog2 = mBuilder2.create();
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                dialog2.show();
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        delete();
                        dialog2.dismiss();
                        Snackbar.make(v, "All notes deleted successfully", Snackbar.LENGTH_LONG).show();
                    }
                });
                btnCancel2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog2.dismiss();
                    }
                });
            }
        });
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
        setData();
        NotesList = findViewById(R.id.NotesList);
        NotesList.setHasFixedSize(true);
        NotesList.setLayoutManager(new LinearLayoutManager(this));
        NotesList.setItemAnimator(new DefaultItemAnimator());
        adapter = new DataAdapter(this, notes);
    }

    public void delete(){
        database.delete(DBHelper.TABLE_NAME, null, null);
    }

//    public void checkIfMoreThanOneNote(){
//        if(cursor.getColumnIndex(DBHelper.ID) == -1){
//            addImage.setVisibility(View.VISIBLE);
//            noNotesText.setVisibility(View.VISIBLE);
//            noNotesDescr.setVisibility(View.VISIBLE);
//        } else{
//            noNotesText.setVisibility(View.GONE);
//            noNotesDescr.setVisibility(View.GONE);
//            addImage.setVisibility(View.GONE);
//            addImage2.setVisibility(View.VISIBLE);
//        }
//    }

    public void addNote(){
        contentValues.put(DBHelper.NOTE, note.getEditText().getText().toString());
        contentValues.put(DBHelper.TIME, formatter.format(date));
        database.insert(DBHelper.TABLE_NAME, null, contentValues);
        database.update(DBHelper.TABLE_NAME, contentValues, null, null);
    }

    public void dataToConsole(){
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.ID);
            int noteIndex = cursor.getColumnIndex(DBHelper.NOTE);
            int timeIndex = cursor.getColumnIndex(DBHelper.TIME);
            do {
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", note = " + cursor.getString(noteIndex) +
                        ", time = " + cursor.getString(timeIndex));
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
            cursor.close();
        }
    }

    private void setData(){
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.ID);
            int noteIndex = cursor.getColumnIndex(DBHelper.NOTE);
            int timeIndex = cursor.getColumnIndex(DBHelper.TIME);
            notes.add(new Note(cursor.getString(noteIndex), cursor.getString(timeIndex), cursor.getString(idIndex)));
        }
    }
}