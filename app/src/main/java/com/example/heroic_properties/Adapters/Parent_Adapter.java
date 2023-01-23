package com.example.heroic_properties.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heroic_properties.Models.Model_parent_all;
import com.example.heroic_properties.R;

import java.util.List;

public class Parent_Adapter extends RecyclerView.Adapter<parent_adapter> {
    public final Context context;
    public final List<Model_parent_all> model_parent_all_list;

    public Parent_Adapter(Context context, List<Model_parent_all> parent_all) {
        this.context = context;
        this.model_parent_all_list = parent_all;
    }

    @NonNull
    @Override
    public parent_adapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.all_properties_parent_rv,null, false);
        return new parent_adapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull parent_adapter holder, int position) {
       Model_parent_all mdpa= model_parent_all_list.get(position);
        holder.title.setText(mdpa.getTitle());

        Child_Adapter child_adapter;
//        child_adapter=new Child_Adapter(context, model_parent_all_list.get(position).childmodelclasslist,context);

        child_adapter=new Child_Adapter(context, mdpa.getChildmodelclasslist(),context);

        holder.rv_child.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.rv_child.setAdapter(child_adapter);
        child_adapter.notifyDataSetChanged();


        holder.Playout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



    }

    @Override
    public int getItemCount() {
        return model_parent_all_list.size();
    }

}

 class parent_adapter extends RecyclerView.ViewHolder{
    RecyclerView rv_child;
    LinearLayout Playout;
    TextView title;

      public parent_adapter(@NonNull View itemView) {
          super(itemView);
          Playout=itemView.findViewById(R.id.playout);
          rv_child=itemView.findViewById(R.id.rv_child);
          title=itemView.findViewById(R.id.tv_parent_title);
      }
  }
