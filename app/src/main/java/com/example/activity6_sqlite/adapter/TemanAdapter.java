package com.example.activity6_sqlite.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activity6_sqlite.MainActivity;
import com.example.activity6_sqlite.R;
import com.example.activity6_sqlite.database.DBController;
import com.example.activity6_sqlite.database.Teman;
import com.example.activity6_sqlite.edit_teman;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;

import java.util.ArrayList;
import java.util.HashMap;

public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.TemanViewHolder> {
    private ArrayList<Teman> listData;
    private Context control;

    public TemanAdapter(ArrayList<Teman> listData) {
        this.listData = listData;
    }

    public TemanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInf = LayoutInflater.from(parent.getContext());
        View v = layoutInf.inflate(R.layout.row_data_teman, parent, false);
        control = parent.getContext();
        return new TemanViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TemanAdapter.TemanViewHolder holder, int position) {
        String nma, tlp, id;

        id = listData.get(position).getId();
        nma = listData.get(position).getNama();
        tlp = listData.get(position).getTelpon();
        DBController db = new DBController(control);

        holder.namaTxt.setTextColor(Color.BLUE);
        holder.namaTxt.setTextSize(20);
        holder.namaTxt.setText(nma);
        holder.telponTxt.setText(tlp);

        holder.cardku.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick (View v) {
                PopupMenu popupMenu = new PopupMenu(control, holder.cardku);
                popupMenu.inflate(R.menu.popupmenu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.mnEdit:
                                Intent i = new Intent(control, edit_teman.class);
                                i.putExtra("id", id);
                                i.putExtra("nama", nma);
                                i.putExtra("telpon", tlp);
                                control.startActivity(i);
                                break;
                            case R.id.mnHapus:
                                HashMap<String,String> values = new HashMap<>();
                                values.put("id", id);
                                db.DeleteData(values);
                                Intent j =new Intent(control, MainActivity.class);
                                control.startActivity(j);
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (listData != null)?listData.size() : 0;
    }

    public class TemanViewHolder extends RecyclerView.ViewHolder {
        private CardView cardku;
        private TextView namaTxt, telponTxt;
        public TemanViewHolder(View view) {
            super(view);
            cardku = (CardView) view.findViewById(R.id.kartuku);
            namaTxt = (TextView) view.findViewById(R.id.textNama);
            telponTxt = (TextView) view.findViewById(R.id.textTelpon);
            cardku.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    return true;
                }
            });
        }
    }
}
