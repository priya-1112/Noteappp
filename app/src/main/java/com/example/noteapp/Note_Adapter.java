package com.example.noteapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Note_Adapter extends RecyclerView.Adapter<Note_Adapter.Viewholder> {

    Context context;
    ArrayList<notemodel> notemodelArrayList;
    List<notemodel> notemodelList;
    DBhandler db;

    Note_Adapter(Context context, List<notemodel> notemodelList){

        this.context = context;
       this.notemodelList= notemodelList;
       this.db = new DBhandler(context);
    }

    @NonNull
    @Override
    public Note_Adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View create = LayoutInflater.from(context).inflate(R.layout.recycler_layout,parent,false);
        Viewholder view = new Viewholder(create);
        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull Note_Adapter.Viewholder holder, int position) {

        notemodel note = notemodelList.get(position);
        holder.notetittext.setText(note.getTitle());
        holder.notetytext.setText(note.getNote());


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_box_delete);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);

                Button cancel_btn = dialog.findViewById(R.id.cancel_btn);
                Button delete_btn = dialog.findViewById(R.id.delete_btn);

                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();

                    }
                });

                delete_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int position = holder.getAdapterPosition();
                        db.delete(note);
                        notemodelList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, notemodelList.size());

                        dialog.dismiss();

                    }
                });
                dialog.show();
                return true;

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update = new Intent(context, MainActivity2.class);
                update.putExtra("id", String.valueOf(note.getId()));
                context.startActivity(update);




            }


        });

        holder.vert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(context,view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.vert_delete, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        int positions = holder.getAdapterPosition();
                        db.delete(note);
                        notemodelList.remove(positions);
                        notifyItemRemoved(positions);
                        notifyItemRangeChanged(positions,notemodelList.size());

                        Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                popupMenu.show();

            }
        });
    }



        @Override
        public int getItemCount () {
            return notemodelList.size();
        }

        public class Viewholder extends RecyclerView.ViewHolder {

            TextView notetittext, notetytext;
            ImageView vert;


            public Viewholder(@NonNull View itemView) {
                super(itemView);

                notetittext = itemView.findViewById(R.id.notetittext);
                notetytext = itemView.findViewById(R.id.notetytext);
                vert = itemView.findViewById(R.id.vert);


            }
        }
    }

