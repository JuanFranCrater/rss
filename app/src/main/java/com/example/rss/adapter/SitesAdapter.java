package com.example.rss.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rss.R;
import com.example.rss.model.Site;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by paco on 6/02/18.
 */

public class SitesAdapter extends RecyclerView.Adapter<SitesAdapter.ViewHolder> {
    public ArrayList<Site> mSites;

    public SitesAdapter(){
        this.mSites = new ArrayList<>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.textView1) TextView name;
        @BindView(R.id.textView2) TextView link;
        //private TextView name, link;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //name = (TextView) itemView.findViewById(R.id.textView1);
            //link = (TextView) itemView.findViewById(R.id.textView2);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View siteView = inflater.inflate(R.layout.item_view, parent, false);

        // Return a new holder instance
        return new ViewHolder(siteView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Site site = mSites.get(position);

        holder.name.setText(site.getName());
        holder.link.setText(site.getLink());
    }

    public void setSites(ArrayList<Site> sites) {
        mSites = sites;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mSites.size();
    }

    public Site getAt(int position){
        Site site;
        site = this.mSites.get(position);
        return  site;
    }

    public void add(Site site) {
        this.mSites.add(site);
        notifyItemInserted(mSites.size() - 1);
        notifyItemRangeChanged(0, mSites.size() - 1);
    }

    public void modifyAt(Site site, int position) {
        this.mSites.set(position, site);
        notifyItemChanged(position);
    }

    public void removeAt(int position) {
        this.mSites.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, mSites.size() - 1);
    }
}
