package com.example.heroic_properties.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.heroic_properties.Models.Model_Child_all;
import com.example.heroic_properties.Fragments.More_details;
import com.example.heroic_properties.R;

import java.util.List;

public class Child_Adapter extends RecyclerView.Adapter<child_adapter> {
    public final Context context;
    public final List<Model_Child_all> child_all_properties;

//    public  Child_Adapter.Childclicklisterner childlistener;


    public Child_Adapter(Context context, List<Model_Child_all> child_all_properties, Context context1) {
        this.context = context;
        this.child_all_properties = child_all_properties;
   }


    @NonNull
    @Override
    public child_adapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.all_properties_child_rv,null, false);
        return new child_adapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull child_adapter holder, int position) {
        Model_Child_all mcap=child_all_properties.get(position);
        Glide.with(context)
                .load(mcap.getImage())
                .into(holder.img_child);

        holder.Cost.setText(String.format("%s KES/month", mcap.getCost()));
        holder.Loc.setText(mcap.getLoc());
        holder.cType.setText(mcap.getType());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 AppCompatActivity appCompatActivity=(AppCompatActivity) v.getContext();
                 More_details more_details=new More_details();

                FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ffframelayout1, more_details);
                Bundle args = new Bundle();
                args.putString("prop_id", mcap.getPropid());
                more_details.setArguments(args);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });

    }
    @Override
    public int getItemCount() {
        return child_all_properties.size();
    }


}
   class child_adapter extends RecyclerView.ViewHolder{
       ImageView img_child;
       LinearLayout rlayout;
       TextView  Cost, Loc, cType, CKost;
        public child_adapter(@NonNull View itemView) {
            super(itemView);
            img_child=itemView.findViewById(R.id.iv_child);
            Cost=itemView.findViewById(R.id.Ccost);
            Loc=itemView.findViewById(R.id.loc);
            cType=itemView.findViewById(R.id.Ctype);
            rlayout=itemView.findViewById(R.id.Rlayout);

        }
}
