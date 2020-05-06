package br.com.lorenzowindmoller.projecthub.service.model.Project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.lorenzowindmoller.projecthub.R;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectHolder> {
    private List<Project> projects = new ArrayList<>();

    @NonNull
    @Override
    public ProjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_item, parent, false);
        return new ProjectHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectHolder holder, int position) {
        Project currentProject = projects.get(position);
        holder.textViewName.setText(currentProject.getName());
        holder.textViewType.setText(currentProject.getType());

        if (!currentProject.getImage().equals("")) {
            Picasso.get()
                .load(currentProject.getImage())
                .resize(50, 50)
                .centerCrop()
                .into(holder.imageViewProject);
        }
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
        notifyDataSetChanged();
    }

    public Project getProjectAt(int position) {
        return projects.get(position);
    }

    class ProjectHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewType;
        private ImageView imageViewProject;

        public ProjectHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.project_name);
            textViewType = itemView.findViewById(R.id.project_type);
            imageViewProject = itemView.findViewById(R.id.project_image);
        }

    }
}
