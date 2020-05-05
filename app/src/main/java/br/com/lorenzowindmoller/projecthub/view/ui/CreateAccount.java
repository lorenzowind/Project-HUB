package br.com.lorenzowindmoller.projecthub.view.ui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.lorenzowindmoller.projecthub.R;

public class CreateAccount extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_NAME =
            "package br.com.lorenzowindmoller.projecthub.view.ui.EXTRA_NAME";
    public static final String EXTRA_EMAIL =
            "package br.com.lorenzowindmoller.projecthub.view.ui.EXTRA_EMAIL";
    public static final String EXTRA_PASSWORD =
            "package br.com.lorenzowindmoller.projecthub.view.ui.EXTRA_PASSWORD";

    private ImageView button_back;
    private FrameLayout button_create_account;

    private EditText edit_text_name;
    private EditText edit_text_email;
    private EditText edit_text_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        button_back = findViewById(R.id.button_back_create_account);
        button_create_account = findViewById(R.id.button_continue_create_account);

        edit_text_name = findViewById(R.id.input_name_create_account);
        edit_text_email = findViewById(R.id.input_email_create_account);
        edit_text_password = findViewById(R.id.input_password_create_account);

        button_back.setOnClickListener(this);
        button_create_account.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == button_create_account) saveUser();

        if (v == button_back) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
    }

    private void saveUser() {
        String name = edit_text_name.getText().toString();
        String email = edit_text_email.getText().toString();
        String password = edit_text_password.getText().toString();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please insert all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_EMAIL, email);
        data.putExtra(EXTRA_PASSWORD, password);

        setResult(RESULT_OK, data);
        finish();
    }
}
