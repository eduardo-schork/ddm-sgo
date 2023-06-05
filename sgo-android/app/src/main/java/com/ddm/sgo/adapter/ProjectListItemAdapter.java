package com.ddm.sgo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddm.sgo.R;
import com.ddm.sgo.model.Project;

import java.util.List;

public class ProjectListItemAdapter extends RecyclerView.Adapter<ProjectListItemAdapter.ItemViewHolder> {
    private final List<Project> itemList;
    private final long lastCheckinProjectId;
    private OnClickListener onClickListener;

    public ProjectListItemAdapter(List<Project> itemList, long lastCheckinProjectId) {
        this.itemList = itemList;
        this.lastCheckinProjectId = lastCheckinProjectId;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_project_list_item, parent, false);

        return new ItemViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Project projectModel = itemList.get(position);

        String infoText = projectModel.city + " - " + projectModel.state + ", " + projectModel.country;

        if (projectModel.uid == lastCheckinProjectId) {
            holder.imageView.setVisibility(View.VISIBLE);
        }

        holder.nameText.setText(projectModel.name);
        holder.infoText.setText(infoText);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(position, projectModel);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(int position, Project model);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView infoText;
        ImageView imageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.alertIconView);
            nameText = itemView.findViewById(R.id.name_text);
            infoText = itemView.findViewById(R.id.info_text);
        }
    }
}
