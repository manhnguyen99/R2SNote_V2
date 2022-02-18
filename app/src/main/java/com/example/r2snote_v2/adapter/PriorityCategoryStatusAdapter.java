package com.example.r2snote_v2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.r2snote_v2.R;

import com.example.r2snote_v2.model.PriorityData;

import java.util.List;

public class PriorityAdapter extends RecyclerView.Adapter<PriorityAdapter.PriorityViewHolder>{
    private List<PriorityData> priorityList;
    private Context context;

    public PriorityAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<PriorityData> priorityList) {
        this.priorityList = priorityList;
        notifyDataSetChanged();
    }

    public List<PriorityData> getData() {
        return priorityList;
    }


    @NonNull
    @Override
    public PriorityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_priority_row, parent, false);
        return new PriorityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PriorityViewHolder holder, int position) {
        PriorityData priority = priorityList.get(position);
        if (priority == null) {
            return;
        }
        holder.bind(priority);

    }

    @Override
    public int getItemCount() {
        if (priorityList == null) {
            return 0;
        }
        return priorityList.size();
    }

    public class PriorityViewHolder extends RecyclerView.ViewHolder{
        private PriorityData priority;
        private TextView priorityName, priorityCreatedDate, priorityEmail;

        public PriorityViewHolder(@NonNull View itemView) {
            super(itemView);
            priorityName = itemView.findViewById(R.id.priority_name);
            priorityCreatedDate = itemView.findViewById(R.id.priority_created_date);
        }

        public void bind(PriorityData priority) {
            this.priority = priority;
            priorityName.setText(priority.getName());
            priorityCreatedDate.setText(priority.getCreatedDate());
        }
    }
}
