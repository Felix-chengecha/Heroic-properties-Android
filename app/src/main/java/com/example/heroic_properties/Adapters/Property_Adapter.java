package com.example.heroic_properties.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.heroic_properties.Models.Property_model;
import com.example.heroic_properties.R;

import java.util.List;

public class Property_Adapter  extends RecyclerView.Adapter<property_adapter> {
    public final Context context;
    public final List<Property_model> nmodel;
    public  Property_Adapter.Moreclicklistener morelistener;

    public Property_Adapter (Context context, List<Property_model> nmodels) {
        this.context = context;
        this.nmodel = nmodels;
    }

    @NonNull
    @Override
    public property_adapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_items,parent,false);
        return new property_adapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull property_adapter holder, int position) {
        Property_model nm=nmodel.get(position);
        holder.Loc.setText(nm.getLocation());
        holder.Cost.setText(String.format(" %sKES/month",nm.getCost()));
        holder.cType.setText(nm.getType());
        Glide.with(context)
                .load(nm.getImage())
                .into(holder.img_child);



        holder.rlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                morelistener.onclicking(v,nm.getPropid());
            }
        });
    }


    @Override
    public int getItemCount() {
        return nmodel.size();
    }

    public interface Moreclicklistener{
        void onclicking(View v, String pid );
    }

    public void Layclick(Property_Adapter.Moreclicklistener mrlistener) {
        this.morelistener=mrlistener;
    }


}
class property_adapter extends RecyclerView.ViewHolder{
    LinearLayout rlayout;
    ImageView img_child;
    TextView Cost, Loc, cType, CKost;

    public property_adapter (@NonNull View itemView) {
        super(itemView);

        rlayout=itemView.findViewById(R.id.Rlayout);
        img_child=itemView.findViewById(R.id.iv_child);
        Cost=itemView.findViewById(R.id.Ccost);
        Loc=itemView.findViewById(R.id.loc);
        cType=itemView.findViewById(R.id.Ctype);
    }
}

