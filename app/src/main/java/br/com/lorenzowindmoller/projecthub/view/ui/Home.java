package br.com.lorenzowindmoller.projecthub.view.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.lorenzowindmoller.projecthub.R;
import br.com.lorenzowindmoller.projecthub.service.model.Project.Project;
import br.com.lorenzowindmoller.projecthub.service.model.Project.ProjectAdapter;
import br.com.lorenzowindmoller.projecthub.service.model.User.User;
import br.com.lorenzowindmoller.projecthub.viewmodel.ProjectViewModel;
import br.com.lorenzowindmoller.projecthub.viewmodel.UserViewModel;

public class Home extends AppCompatActivity implements View.OnClickListener {
    public static final int UPDATE_USER_REQUEST = 1;
    public static final int CREATE_PROJECT_REQUEST = 2;

    private UserViewModel userViewModel;
    private ProjectViewModel projectViewModel;

    private ImageView button_back;
    private ImageView button_image;

    private FrameLayout button_create_project;

    private User user;

    private List<Project> list_projects;

    private LinearLayout empty_projects;
    private ScrollView non_empty_projects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        button_back = findViewById(R.id.button_back_home);
        button_image = findViewById(R.id.button_profile_home);

        button_create_project = findViewById(R.id.button_create_project_home);

        empty_projects = findViewById(R.id.frame_empty_projects);
        non_empty_projects = findViewById(R.id.frame_non_empty_projects);

        if (user.getImage() != "") {
            Picasso.get()
                .load(user.getImage())
                .resize(180, 180)
                .centerCrop()
                .into(button_image);
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ProjectAdapter adapter = new ProjectAdapter();
        recyclerView.setAdapter(adapter);

        projectViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
        projectViewModel.getAllProjects(user.getId()).observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                list_projects = projects;

                if (projects.size() > 0) {
                    non_empty_projects.setVisibility(View.VISIBLE);
                    empty_projects.setVisibility(View.GONE);
                } else {
                    non_empty_projects.setVisibility(View.GONE);
                    empty_projects.setVisibility(View.VISIBLE);
                }

                adapter.setProjects(projects);
            }
        });

        button_back.setOnClickListener(this);
        button_image.setOnClickListener(this);
        button_create_project.setOnClickListener(this);
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

        else if (v == button_create_project) {
            Intent intent = new Intent(getApplicationContext(), CreateProject.class);
            intent.putExtra("user", user);
            startActivityForResult(intent, CREATE_PROJECT_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == UPDATE_USER_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(Profile.EXTRA_NAME);
            String email = data.getStringExtra(Profile.EXTRA_EMAIL);
            String password = data.getStringExtra(Profile.EXTRA_PASSWORD);
            String image = data.getStringExtra(Profile.EXTRA_IMAGE);

            User new_user = new User(name, email, password, image);

            new_user.setId(user.getId());

            user = new_user;

            userViewModel.update(user);

            if (!image.equals("")) {
                Picasso.get()
                    .load(image)
                    .resize(50, 50)
                    .centerCrop()
                    .into(button_image);
            }

            Toast.makeText(this, "User updated", Toast.LENGTH_SHORT).show();
        }

        else if (requestCode == UPDATE_USER_REQUEST && resultCode == -2) {
            userViewModel.delete(user);

            Toast.makeText(this, "User deleted", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        else if (requestCode == CREATE_PROJECT_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(CreateProject.EXTRA_PROJECT_NAME);
            String type = data.getStringExtra(CreateProject.EXTRA_PROJECT_TYPE);
            String description = data.getStringExtra(CreateProject.EXTRA_PROJECT_DESCRIPTION);
            String image = data.getStringExtra(CreateProject.EXTRA_PROJECT_IMAGE);

            Project new_project = new Project(user.getId(), name, type, description, image);

            projectViewModel.insert(new_project);

            Toast.makeText(this, "Project created", Toast.LENGTH_SHORT).show();
        }
    }

}
