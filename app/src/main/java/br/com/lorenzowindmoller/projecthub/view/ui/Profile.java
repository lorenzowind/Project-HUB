package br.com.lorenzowindmoller.projecthub.view.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import br.com.lorenzowindmoller.projecthub.R;
import br.com.lorenzowindmoller.projecthub.service.model.User;

public class Profile extends AppCompatActivity implements View.OnClickListener, ImageDialog.ImageDialogListener {
    public static final String EXTRA_NAME =
            "package br.com.lorenzowindmoller.projecthub.view.ui.EXTRA_NAME";
    public static final String EXTRA_EMAIL =
            "package br.com.lorenzowindmoller.projecthub.view.ui.EXTRA_EMAIL";
    public static final String EXTRA_PASSWORD =
            "package br.com.lorenzowindmoller.projecthub.view.ui.EXTRA_PASSWORD";
    public static final String EXTRA_IMAGE =
            "package br.com.lorenzowindmoller.projecthub.view.ui.EXTRA_IMAGE";

    private ImageView button_cancel;
    private ImageView button_confirm;
    private ImageView button_edit_image;

    private TextView button_delete_account;

    private ImageView image_profile;
    private String image = "";

    private EditText text_name;
    private EditText text_email;
    private EditText text_password;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        button_cancel = findViewById(R.id.button_cancel_profile);
        button_confirm = findViewById(R.id.button_confirm_profile);
        button_edit_image = findViewById(R.id.edit_image_profile);

        button_delete_account = findViewById(R.id.textview_delete_profile);

        image_profile = findViewById(R.id.image_profile);

        text_name = findViewById(R.id.text_name_profile);
        text_email = findViewById(R.id.text_email_profile);
        text_password = findViewById(R.id.text_password_profile);

        text_name.setText(user.getName());
        text_email.setText(user.getEmail());
        text_password.setText(user.getPassword());

        if (user.getImage() != "") {
            Picasso.get()
                    .load(user.getImage())
                    .resize(180, 180)
                    .centerCrop()
                    .into(image_profile);
        }

        button_cancel.setOnClickListener(this);
        button_confirm.setOnClickListener(this);
        button_edit_image.setOnClickListener(this);

        button_delete_account.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == button_confirm) update_user();

        else if (v == button_cancel) {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }

        else if (v == button_edit_image) change_image();

        else if (v == button_delete_account) {
            Intent data = new Intent();
            setResult(-2, data);
            finish();
        }
    }

    private void update_user() {
        String name = text_name.getText().toString();
        String email = text_email.getText().toString();
        String password = text_password.getText().toString();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please insert all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_EMAIL, email);
        data.putExtra(EXTRA_PASSWORD, password);
        data.putExtra(EXTRA_IMAGE, image);

        setResult(RESULT_OK, data);
        finish();
    }

    private void change_image() {
        ImageDialog dialogEmail = new ImageDialog();
        dialogEmail.show(getSupportFragmentManager(), "ImageDialog");
    }

    @Override
    public void applyTextImageDialog(String url) {
        Drawable drawable = image_profile.getDrawable();

        if(url.length() != 0) {
            image = url;
            Picasso.get()
                    .load(image)
                    .resize(180, 180)
                    .centerCrop()
                    .into(image_profile);
        }
        else {
            Toast.makeText(this, "URL not valid", Toast.LENGTH_SHORT).show();
        }

        if (image_profile.getDrawable() == null || url.length() == 0) image_profile.setImageDrawable(drawable);
    }

}
