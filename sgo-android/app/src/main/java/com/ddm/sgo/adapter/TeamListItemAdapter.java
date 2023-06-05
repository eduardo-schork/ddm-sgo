package com.ddm.sgo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddm.sgo.R;
import com.ddm.sgo.model.Team;

import java.util.List;


public class TeamListItemAdapter extends RecyclerView.Adapter<TeamListItemAdapter.ItemViewHolder> {
    private final List<Team> itemList;
    private OnClickListener onClickListener;


    public TeamListItemAdapter(List<Team> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_project_list_item, parent, false);

        return new ItemViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Team projectModel = itemList.get(position);

        String infoText = "Tamanho da equipe: " + projectModel.numberOfMembers + " integrantes.";

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
        void onClick(int position, Team model);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView infoText;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.name_text);
            infoText = itemView.findViewById(R.id.info_text);
        }
    }
}
