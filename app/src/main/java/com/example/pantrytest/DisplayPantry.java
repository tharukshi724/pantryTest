package com.example.pantrytest;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class DisplayPantry extends AppCompatActivity  {

    private List<Model>list;

    RecyclerView recyclerview;
    MyAdapter myAdapter;


    public FloatingActionButton floatingActionButton;
     FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference root = db.getReference().child("Model");
   EditText eDate;

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_displaydata);


        recyclerview = findViewById(R.id.recyclerview);

       // makeNotify();
       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("MY applcation","MY applcation ",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        list = new ArrayList<>();



       recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this,list);

        recyclerview.setAdapter(myAdapter);





        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Model data = dataSnapshot.getValue(Model.class);
                    data.setKey(dataSnapshot.getRef().getKey());
                    list.add(data);
                }

                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







            //swip to delete items

   new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
       @Override
       public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
           return false;
       }

       @Override
       public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
           AlertDialog.Builder builder = new AlertDialog.Builder(DisplayPantry.this);
           builder.setTitle("Delete Item");
           builder.setMessage("Are you sure ?");
           builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                  myAdapter.deleteItem(viewHolder.getAdapterPosition());

               }

           });
           builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   myAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
               }
           });


         builder.show();


       }

       @Override
       public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
           new RecyclerViewSwipeDecorator.Builder(DisplayPantry.this,c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)

                   .addSwipeRightBackgroundColor(ContextCompat.getColor(DisplayPantry.this,R.color.swipeColor))
                   .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_sweep_24)
                   .addSwipeRightLabel("Delete item")
                   .addBackgroundColor(ContextCompat.getColor(DisplayPantry.this,R.color.swipeColor))
                   .addActionIcon(R.drawable.ic_baseline_delete_sweep_24)
                   .create()
                   .decorate();
           super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);



       }
   }).attachToRecyclerView(recyclerview);

        //implement floating button navigation
        floatingActionButton = findViewById(R.id.addButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Intent intent = new Intent(DisplayPantry.this, MainActivity.class);
                startActivity(intent);
            }
        });



}




    // public void makeNotify(){

       // db.addListenerForSingleValueEvent(new ValueEventListener() {
         //  @Override
        //   public void onDataChange(@NonNull DataSnapshot snapshot) {
              //if(snapshot.hasChildren()){
               //   try{

                    //   Log.d("DisplayPantry","datax");


                   //   String da= snapshot.child("item").getValue().toString();
                   //   Log.d("ee","data1");
                        // String notificationTime = snapshot.child("notificationTime").getValue().toString();
                      //  SimpleDateFormat dates = new SimpleDateFormat("yyyy/MM/dd");
                      //  Log.d("DisplayPantry","data current");
                      //  String currentDate = dates.format(new Date());
                      //  Date date1;
                      //  Date date2;
                      //  Long b1 = Long.valueOf(14);
                      //  Long b2 = Long.valueOf(30);
                    //  date1 = dates.parse(da);
                    //    date2 = dates.parse(currentDate);


                       // long  difference =  Math.abs(date1.getTime() - date2.getTime());
                      //  Log.d("DisplayPantry","data noticc"+difference);

                     //   long differenceDates = difference / (24 * 60 * 60 * 1000);

                      //  if((differenceDates <= b1) && (differenceDates != 0)){

                          //  NotificationCompat.Builder builder =  new NotificationCompat.Builder(DisplayPantry.this,"MY applcation");
                         //   builder.setSmallIcon(R.drawable.ic_baseline_message_24);

                         //   builder.setContentTitle("Item Going to Expired ");
                         //   builder.setContentText("xxxxxx");



                         //   NotificationManagerCompat manager = NotificationManagerCompat.from(DisplayPantry.this);
                          //  manager.notify(1, builder.build());


                      //  }



                   // } catch (Exception exception) {

                  //  }




              //  }


           // }
           // @Override
           // public void onCancelled(@NonNull DatabaseError error) {

          //  }
       // });
    }








