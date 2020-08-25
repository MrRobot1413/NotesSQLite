package aleksey.git.notessqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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
    DBOperations operations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        operations = new DBOperations();
        mShowDialog = findViewById(R.id.btnAddNote);
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
        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(note.getEditText().getText().toString().trim().equals("")){
                            Snackbar.make(view, "Field can't be empty", Snackbar.LENGTH_LONG).show();
                        } else{
                            operations.addNote(note.getEditText().getText().toString(), formatter.format(date), date);
                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        operations.dataToConsole();
                    }
                });
            }
        });

    }

}