package br.com.lorenzowindmoller.projecthub.view.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.com.lorenzowindmoller.projecthub.R;
import br.com.lorenzowindmoller.projecthub.service.model.User.User;

public class CreateProject extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_PROJECT_NAME =
            "package br.com.lorenzowindmoller.projecthub.view.ui.EXTRA_PROJECT_NAME";
    public static final String EXTRA_PROJECT_TYPE =
            "package br.com.lorenzowindmoller.projecthub.view.ui.EXTRA_PROJECT_TYPE";
    public static final String EXTRA_PROJECT_DESCRIPTION =
            "package br.com.lorenzowindmoller.projecthub.view.ui.EXTRA_PROJECT_DESCRIPTION";
    public static final String EXTRA_PROJECT_IMAGE =
            "package br.com.lorenzowindmoller.projecthub.view.ui.EXTRA_PROJECT_IMAGE";

    private ImageView button_back;
    private ImageView button_copy;

    private FrameLayout button_create;

    private EditText project_name;
    private EditText project_type;
    private EditText project_description;
    private EditText project_url;

    private User user;

    ClipboardManager clipboardManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        button_back = findViewById(R.id.button_cancel_create_project);
        button_copy = findViewById(R.id.button_copy_project);

        button_create = findViewById(R.id.button_create_project);

        project_name = findViewById(R.id.input_name_create_project);
        project_type = findViewById(R.id.input_type_create_project);
        project_description = findViewById(R.id.input_description_create_project);
        project_url = findViewById(R.id.input_image_create_project);

        button_back.setOnClickListener(this);
        button_copy.setOnClickListener(this);
        button_create.setOnClickListener(this);

        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
    }

    public void onClick(View v) {
        if (v == button_back) {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            intent.putExtra("user", user);
            startActivity(intent);
            finish();
        }

        else if (v == button_copy) {
            ClipData clipData = clipboardManager.getPrimaryClip();
            ClipData.Item item = clipData.getItemAt(0);

            project_url.setText(item.getText().toString());
        }

        else if(v == button_create) save_project();
    }

    private void save_project() {
        String name = project_name.getText().toString();
        String type = project_type.getText().toString();
        String description = project_description.getText().toString();
        String image = project_url.getText().toString();

        if (name.isEmpty() || type.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please insert all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_PROJECT_NAME, name);
        data.putExtra(EXTRA_PROJECT_TYPE, type);
        data.putExtra(EXTRA_PROJECT_DESCRIPTION, description);
        data.putExtra(EXTRA_PROJECT_IMAGE, image);

        setResult(RESULT_OK, data);
        finish();
    }
}
