package com.example.pantrytest;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;

import static android.content.Context.NOTIFICATION_SERVICE;
import com.google.firebase.firestore.DocumentSnapshot;





public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    List<Model> modelList;



    public MyAdapter(List<Model> modelList) {
        this.modelList = modelList;




    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model model = this.modelList.get(position);

        holder.item.setText(model.getItem());
        holder.size.setText(model.getSize());


        try{
            String d1= this.modelList.get(position).getDate();
            SimpleDateFormat dates = new SimpleDateFormat("yyyy/MM/dd");
            String currentDate = dates.format(new Date());
            Date date1;
            Date date2;
            Long b1 = Long.valueOf(14);
            Long b2 = Long.valueOf(30);
            date1 = dates.parse(d1);
            date2 = dates.parse(currentDate);


            long  difference =  Math.abs(date1.getTime() - date2.getTime());


            long differenceDates = difference / (24 * 60 * 60 * 1000);

            String dayDifference = Long.toString(differenceDates);
            model.setDayDifference(dayDifference);








        } catch (Exception exception) {

        }
        holder.dayDifference.setText(model.getDayDifference()+"days");



        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dia = DialogPlus.newDialog(holder.edit.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true,600)
                        .create();

                View myview = dia.getHolderView();

                EditText item = myview.findViewById(R.id.item);
                EditText size = myview.findViewById(R.id.size);
                EditText date = myview.findViewById(R.id.date);
                Button btn = myview.findViewById(R.id.updateBtn);

                item.setText(model.getItem());
                size.setText(model.getSize());
                date.setText(model.getDate());

                dia.show();




            }
        });


    }





    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

         TextView item;
        TextView size;
        TextView dayDifference;

        Button edit;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item = (TextView)itemView.findViewById(R.id.text1);
            size = (TextView)itemView.findViewById(R.id.text3);
            dayDifference = (TextView)itemView.findViewById(R.id.text2);
            edit = itemView.findViewById(R.id.edit);
        }
    }

    public void deleteItem(int position){
      modelList.remove(position);
       // String key = keys.get(position);
       // DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Model");
        //ref.child(key).removeValue();
        //getSnapshots().getSnapshot(position).getReference().delete();
      notifyItemRemoved(position);

    }


}













