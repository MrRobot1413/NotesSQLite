package aleksey.git.notessqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    ImageButton mShowDialog;
    AlertDialog.Builder mBuilder;
    View mView;
    TextInputLayout note;
    Button btnAdd;
    Button btnCancel;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShowDialog = findViewById(R.id.btnShowDialog);
        mBuilder = new AlertDialog.Builder(MainActivity.this);
        mView = getLayoutInflater().inflate(R.layout.dialog_login, null);
        note = mView.findViewById(R.id.noteField);
        btnAdd = mView.findViewById(R.id.btnAdd);
        btnCancel = mView.findViewById(R.id.btnCancel);
        mBuilder.setView(mView);
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
                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

}