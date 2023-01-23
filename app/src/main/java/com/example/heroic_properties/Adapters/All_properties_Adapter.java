package com.example.heroic_properties.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroic_properties.R;

public class All_properties_Adapter extends RecyclerView.Adapter<all_properties_Adapter> {


    @NonNull
    @Override
    public all_properties_Adapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_properties_child_rv,parent,false);

        return new all_properties_Adapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull all_properties_Adapter holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

class all_properties_Adapter extends  RecyclerView.ViewHolder{

    public all_properties_Adapter(@NonNull View itemView) {
        super(itemView);
    }
}
