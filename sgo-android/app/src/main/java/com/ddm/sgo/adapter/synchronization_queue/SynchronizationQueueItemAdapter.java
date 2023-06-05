package com.ddm.sgo.adapter.synchronization_queue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ddm.sgo.R;

import java.util.List;

public class SynchronizationQueueItemAdapter extends RecyclerView.Adapter<SynchronizationQueueItemAdapter.ItemViewHolder> {
    private final List<SynchronizationQueueItem> itemList;
    private OnClickListener onClickListener;


    public SynchronizationQueueItemAdapter(List<SynchronizationQueueItem> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_synchronization_queue_item, parent, false);

        return new ItemViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        SynchronizationQueueItem queueItem = itemList.get(position);

        holder.nameText.setText(queueItem.name);
        holder.infoText.setText(queueItem.infoText);
        holder.statusText.setText(queueItem.status);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(position, queueItem);
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
        void onClick(int position, SynchronizationQueueItem model);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView infoText;
        TextView statusText;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.name_text);
            infoText = itemView.findViewById(R.id.info_text);
            statusText = itemView.findViewById(R.id.status_value);
        }
    }
}
