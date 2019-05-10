package com.example.roomdatabase;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.roomdatabase.database.AppDatabase;
import com.example.roomdatabase.database.PersonModel;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    AppDatabase db;
    List<PersonModel> personModels;
    Context context;





    public Adapter(AppDatabase db, List<PersonModel> personModels, Context context) {
        this.db = db;
        this.personModels = personModels;
        this.context = context;
    }

//    public Adapter(List<PersonModel> personModels, Context context) {
//        this.personModels = personModels;
//        this.context = context;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final PersonModel model = personModels.get(position);
        holder.txt_name.setText(model.getName());
        holder.txt_family.setText(model.getFamili());
        holder.txt_phone.setText(model.getMobile());

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               AlertDialog.Builder builder= new AlertDialog.Builder(context);
//               builder.setTitle("حذف یا آپدیت"+ " "+personModels.get(position).getName());
               builder.setMessage("حذف یا آپدیت"+ " "+personModels.get(position).getName());
               builder.setPositiveButton("حذف", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {

                       db.personDao().deletePerson(personModels.get(position));
                       //to refresh list
                       ((MainActivity)context).refreshList();
                   }
               });


               builder.setNegativeButton("اپدیت", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       AddActivity.model=model;
                       App.updateClicked=true;
                       context.startActivity(new Intent(context,AddActivity.class));
                   }
               });
               builder.create().show();

           }
       });

    }



    @Override
    public int getItemCount() {
        return personModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name,txt_family,txt_phone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_name=itemView.findViewById(R.id.row_txt_name);
            txt_family=itemView.findViewById(R.id.row_txt_family);
            txt_phone=itemView.findViewById(R.id.row_txt_phone);
        }
    }
}
