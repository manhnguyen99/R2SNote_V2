package com.example.r2snote_v2.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.r2snote_v2.R;

import com.example.r2snote_v2.fragment.DialogAddNote;
import com.example.r2snote_v2.fragment.PriorityCategoryStatusDialog;
import com.example.r2snote_v2.model.PriorityCategoryStatusData;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class PriorityCategoryStatusAdapter extends RecyclerView.Adapter<PriorityCategoryStatusAdapter.PriorityViewHolder> {
    private List<PriorityCategoryStatusData> dataList;
    private Context context;

    public PriorityCategoryStatusAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<PriorityCategoryStatusData> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public List<PriorityCategoryStatusData> getData() {
        return dataList;
    }


    @NonNull
    @Override
    public PriorityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_priority_category_status_row, parent, false);
        return new PriorityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PriorityViewHolder holder, int position) {
        PriorityCategoryStatusData data = dataList.get(position);
        if (data == null) {
            return;
        }
        holder.bind(data);

    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }

    public class PriorityViewHolder extends RecyclerView.ViewHolder {
        private PriorityCategoryStatusData data;
        private TextView dataName, dataCreatedDate, dataEmail;

        public PriorityViewHolder(@NonNull View itemView) {
            super(itemView);
            dataName = itemView.findViewById(R.id.data_name);
            dataCreatedDate = itemView.findViewById(R.id.data_created_date);
            itemView.setOnLongClickListener(view -> {
                new MaterialAlertDialogBuilder(view.getContext())
                        .setTitle("Edit it?")
                        .setMessage("Do you want edit Note " + data.getName())
                        .setCancelable(false)
                        .setNegativeButton("Cancel", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        })
                        .setPositiveButton("Edit", (dialogInterface, i) -> {
                            Bundle args = new Bundle();
                            args.putString("tab",data.getTab());
                            args.putString("name", data.getName());
                            args.putString("createddate", data.getCreatedDate());
                            PriorityCategoryStatusDialog dialog = PriorityCategoryStatusDialog.newInstance();
                            dialog.setArguments(args);
                            dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), null);

                        })
                        .show();

                return true;
            });
        }

        public void bind(PriorityCategoryStatusData data) {
            this.data = data;
            dataName.setText(data.getName());
            dataCreatedDate.setText(data.getCreatedDate());
        }
    }
}
