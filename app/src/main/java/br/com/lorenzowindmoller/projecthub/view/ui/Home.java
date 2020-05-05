package br.com.lorenzowindmoller.projecthub.view.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import br.com.lorenzowindmoller.projecthub.R;
import br.com.lorenzowindmoller.projecthub.service.model.User;
import br.com.lorenzowindmoller.projecthub.viewmodel.UserViewModel;

public class Home extends AppCompatActivity implements View.OnClickListener {
    public static final int UPDATE_USER_REQUEST = 1;

    private UserViewModel userViewModel;

    private ImageView button_back;
    private ImageView button_image;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        button_back = findViewById(R.id.button_back_home);
        button_image = findViewById(R.id.button_profile_home);

        button_back.setOnClickListener(this);
        button_image.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == button_back) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else if (v == button_image) {
            Intent intent = new Intent(getApplicationContext(), Profile.class);
            intent.putExtra("user", user);
            startActivityForResult(intent, UPDATE_USER_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_USER_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(Profile.EXTRA_NAME);
            String email = data.getStringExtra(Profile.EXTRA_EMAIL);
            String password = data.getStringExtra(Profile.EXTRA_PASSWORD);
            //String image = data.getStringExtra(Profile.EXTRA_IMAGE);

            User new_user = new User(name, email, password, "");

            new_user.setId(user.getId());

            user = new_user;

            userViewModel.update(user);

            Toast.makeText(this, "User updated", Toast.LENGTH_SHORT).show();
        }

        else if (requestCode == UPDATE_USER_REQUEST && resultCode == -2) {
            userViewModel.delete(user);

            Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
    }

}
