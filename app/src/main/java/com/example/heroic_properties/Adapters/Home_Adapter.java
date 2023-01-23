package com.example.heroic_properties.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.heroic_properties.Models.Property_model;
import com.example.heroic_properties.R;

import java.util.List;

public class Home_Adapter extends RecyclerView.Adapter<home_adapter> {

    public final Context context;
    public final List<Property_model> nmodel;
    public  Home_Adapter.Moreclicklistener morelistener;

    public Home_Adapter(Context context, List<Property_model> nmodel) {
        this.context = context;
        this.nmodel = nmodel;
    }

    @NonNull
    @Override
    public home_adapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.nearby_items,parent,false);
        return new home_adapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull home_adapter holder, int position) {
        Property_model nm=nmodel.get(position);
        holder.location.setText(nm.getLocation());
        holder.type.setText(nm.getType());
        Glide.with(context)
                .load(nm.getImage())
                .into(holder.img);

        holder.Nearby_rv.setOnClickListener(new View.OnClickListener() {
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

    public void Layclick(Home_Adapter.Moreclicklistener mrlistener) {
        this.morelistener=mrlistener;
    }

}

class home_adapter extends RecyclerView.ViewHolder{
    ImageView img;
    RelativeLayout Nearby_rv;
    TextView type,location;

    public home_adapter(@NonNull View itemView) {
        super(itemView);
        Nearby_rv=itemView.findViewById(R.id.nearby_rv);
        type=itemView.findViewById(R.id.prop_type);
        location=itemView.findViewById(R.id.prop_loc);
        img=itemView.findViewById(R.id.prop_img);

    }
}
