package br.com.lorenzowindmoller.projecthub.view.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.List;

import br.com.lorenzowindmoller.projecthub.R;
import br.com.lorenzowindmoller.projecthub.service.model.User.User;
import br.com.lorenzowindmoller.projecthub.viewmodel.UserViewModel;

public class Login extends AppCompatActivity implements View.OnClickListener {
    public static final int CREATE_USER_REQUEST = 1;

    private UserViewModel userViewModel;
    private List<User> list_users;

    private EditText edit_text_email;
    private EditText edit_text_password;

    private FrameLayout button_continue;
    private FrameLayout button_create_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        userViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                list_users = users;
            }
        });

        edit_text_email = findViewById(R.id.input_email);
        edit_text_password = findViewById(R.id.input_password);
        button_continue = findViewById(R.id.button_continue_login);
        button_create_account = findViewById(R.id.button_create_account_login);

        button_continue.setOnClickListener(this);
        button_create_account.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == button_continue) check_user();

        else if (v == button_create_account) {
            Intent intent = new Intent(getApplicationContext(), CreateAccount.class);
            startActivityForResult(intent, CREATE_USER_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_USER_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(CreateAccount.EXTRA_NAME);
            String email = data.getStringExtra(CreateAccount.EXTRA_EMAIL);
            String password = data.getStringExtra(CreateAccount.EXTRA_PASSWORD);

            User new_user = new User(name, email, password, null);

            for (User user : list_users) {
                if (user.getEmail().equals(new_user.getEmail())) {
                    Toast.makeText(this, "This email already exists", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            userViewModel.insert(new_user);

            Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User not created", Toast.LENGTH_SHORT).show();
        }
    }

    public void check_user() {
        String email = edit_text_email.getText().toString();
        String password = edit_text_password.getText().toString();

        for (User user : list_users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)){
                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
                return;
            }
        }
        Toast.makeText(this, "Incorrect email/password", Toast.LENGTH_SHORT).show();
    }
}
