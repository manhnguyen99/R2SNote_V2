package com.example.r2snote_v2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.r2snote_v2.R;
import com.example.r2snote_v2.model.Priority;
import com.example.r2snote_v2.model.PriorityData;

import java.util.List;

public class PriorityAdapter extends RecyclerView.Adapter<PriorityAdapter.PriorityHolder>{

    private List<PriorityData> priorityList;
    private Context context;

    public PriorityAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<PriorityData> priorityList) {
        this.priorityList = priorityList;
        notifyDataSetChanged();
    }
    public List<PriorityData> getData(){
        return priorityList;
    }

    @Override
    public PriorityHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_priority, parent, false);
        return new PriorityHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  PriorityAdapter.PriorityHolder holder, int position) {
        PriorityData priority = priorityList.get(position);
        if (priority == null)
        {
            return;
        }
        holder.bind(priority);
    }

    @Override
    public int getItemCount() { if (priorityList == null){
        return 0;
    }
        return priorityList.size();
    }

    public class PriorityHolder extends RecyclerView.ViewHolder {
        private PriorityData priorityData;
        private TextView txtName, txtDate;
        public PriorityHolder(@NonNull  View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtDate = itemView.findViewById(R.id.txtCreateDate);
        }

        public void bind(PriorityData priorityData) {
            this.priorityData = priorityData;
            txtName.setText(priorityData.getName());
            txtDate.setText(priorityData.getDate());
        }
    }
}
