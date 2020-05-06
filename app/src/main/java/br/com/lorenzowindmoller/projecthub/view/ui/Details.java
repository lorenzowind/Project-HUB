package br.com.lorenzowindmoller.projecthub.view.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import br.com.lorenzowindmoller.projecthub.R;
import br.com.lorenzowindmoller.projecthub.service.model.Project.Project;
import br.com.lorenzowindmoller.projecthub.service.model.User.User;
import br.com.lorenzowindmoller.projecthub.view.ui.component.ImageDialog;

public class Details extends AppCompatActivity implements View.OnClickListener, ImageDialog.ImageDialogListener {
    public static final String EXTRA_ID =
            "package br.com.lorenzowindmoller.projecthub.view.ui.EXTRA_ID";
    public static final String EXTRA_NAME =
            "package br.com.lorenzowindmoller.projecthub.view.ui.EXTRA_NAME";
    public static final String EXTRA_TYPE =
            "package br.com.lorenzowindmoller.projecthub.view.ui.EXTRA_EMAIL";
    public static final String EXTRA_DESCRIPTION =
            "package br.com.lorenzowindmoller.projecthub.view.ui.EXTRA_PASSWORD";
    public static final String EXTRA_IMAGE =
            "package br.com.lorenzowindmoller.projecthub.view.ui.EXTRA_IMAGE";

    private ImageView button_cancel;
    private ImageView button_confirm;
    private ImageView button_edit_image;

    private ImageView image_project;
    private String image = "";

    private EditText text_name;
    private EditText text_type;
    private EditText text_description;

    private User user;
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        project = (Project) intent.getSerializableExtra("project");

        button_cancel = findViewById(R.id.button_cancel_project_details);
        button_confirm = findViewById(R.id.button_confirm_project_details);
        button_edit_image = findViewById(R.id.edit_image_project_details);

        image_project = findViewById(R.id.project_image_details);

        text_name = findViewById(R.id.text_name_project_details);
        text_type = findViewById(R.id.text_type_project_details);
        text_description = findViewById(R.id.text_description_project_details);

        text_name.setText(project.getName());
        text_type.setText(project.getType());
        text_description.setText(project.getDescription());

        if (project.getImage() != "") {
            Picasso.get()
                    .load(project.getImage())
                    .resize(180, 180)
                    .centerCrop()
                    .into(image_project);
        }

        button_cancel.setOnClickListener(this);
        button_confirm.setOnClickListener(this);
        button_edit_image.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v == button_confirm) update_project();

        else if (v == button_cancel) {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }

        else if (v == button_edit_image) change_image();
    }

    private void update_project() {
        String name = text_name.getText().toString();
        String type = text_type.getText().toString();
        String description = text_description.getText().toString();

        if (name.isEmpty() || type.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please insert all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_ID, Integer.toString(project.getId()));
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_TYPE, type);
        data.putExtra(EXTRA_DESCRIPTION, description);

        if (!image.equals("")) data.putExtra(EXTRA_IMAGE, image);
        else data.putExtra(EXTRA_IMAGE, project.getImage());

        setResult(RESULT_OK, data);
        finish();
    }

    private void change_image() {
        ImageDialog dialogEmail = new ImageDialog();
        dialogEmail.show(getSupportFragmentManager(), "ImageDialog");
    }

    @Override
    public void applyTextImageDialog(String url) {
        Drawable drawable = image_project.getDrawable();

        if(url.length() != 0) {
            image = url;
            Picasso.get()
                    .load(image)
                    .resize(180, 180)
                    .centerCrop()
                    .into(image_project);
        }
        else {
            Toast.makeText(this, "URL not valid", Toast.LENGTH_SHORT).show();
        }

        if (image_project.getDrawable() == null || url.length() == 0) image_project.setImageDrawable(drawable);
    }

}
