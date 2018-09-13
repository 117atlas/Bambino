package com.appsonetimes.bambino.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appsonetimes.bambino.OrderActivity;
import com.appsonetimes.bambino.OrderDetailsActivity;
import com.appsonetimes.bambino.R;
import com.appsonetimes.bambino.model.Commande;

import java.util.List;

public class RecyclerAdapterCart extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final String TAG = OrderActivity.class.getSimpleName();
    private List<Commande> coms;
    private Context context;

    public RecyclerAdapterCart(List<Commande> liste, Context context) {
        this.coms = liste;
        this.context = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_item_commande, parent, false);
        return new CommandeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holde, final int position) {
        final CommandeViewHolder holder = (CommandeViewHolder) holde;
        holder.pos = position;
        holder.com = coms.get(position);
        holder.initView();
        /*
         RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.bambi1)
                    .priority(RenderScript.Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context)
                    .load(coms.get(position).getCover())
                    .transition(withCrossFade())
                    .into(holder.image_cart);

        holder.pos = position;
        String str1 = coms.get(position).getCodeProduit()+"";
        holder.code.setText(str1);
        String str2 = coms.get(position).getPrixU()+"";
        holder.priceU.setText(str2);
        holder.qte.setText(coms.get(position).getQuantite());*/
    }

    @Override
    public int getItemCount() {
        return coms.size();
    }

    public class CommandeViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView date;
        TextView montant, livre;
        Commande com;
        int pos;

        public CommandeViewHolder(View v) {
            super(v);
            id = (TextView) v.findViewById(R.id.id);
            date = (TextView) v.findViewById(R.id.date);
            montant = (TextView) v.findViewById(R.id.mont);
            livre = (TextView) v.findViewById(R.id.livré);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, OrderDetailsActivity.class);
                    intent.putExtra(Commande.class.getSimpleName(), coms.get(pos));
                    context.startActivity(intent);
                }
            });
        }

        public void initView(){
            id.setText(com.getIdCommande() +"");
            String s = com.getMontant()+ " FCFA";
            montant.setText(s);
            date.setText(com.getDate());
            String e = (com.isLivre()==0)? "NON":"OUI";
            livre.setText(e);
        }

    }
}


