package com.example.roomdatabase;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.roomdatabase.database.AppDatabase;
import com.example.roomdatabase.database.PersonModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

   AppDatabase db;

    RecyclerView recyclerView;
    Adapter adapter;
    List<PersonModel> personModel;
    Context context;

    EditText edt_search;
    ImageView img_search , img_cancel_search;

    Button btn_add_Main , btn_deleteAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=this;

        btn_add_Main=findViewById(R.id.btn_add_Main);
        btn_add_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.btnAddClickedMain = true;
                App.updateClicked = false;
                startActivity(new Intent(context,AddActivity.class));
            }
        });


//        recyclerView=findViewById(R.id.recyclerview);

        refreshList();



        edt_search=findViewById(R.id.edt_search);
        img_search=findViewById(R.id.imgSearch);
         img_search.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 personModel = db.personDao().findProduct(edt_search.getText().toString());

                 adapter = new Adapter(db,personModel, context);
                 recyclerView.setAdapter(adapter);
                 Toast.makeText(MainActivity.this, personModel.size() + " کارمند پیدا شد", Toast.LENGTH_SHORT).show();
             }
         });


         btn_deleteAll = findViewById(R.id.btn_deleteAll_Main);
         btn_deleteAll.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 db.personDao().deleteAll(personModel);
                 refreshList();
        }
         });



         img_cancel_search=findViewById(R.id.img_cancel_search);
         img_cancel_search.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 edt_search.setText("");
                 refreshList();
             }
         });

    }

    public void refreshList() {

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,"persons")
                .allowMainThreadQueries()
                .build();

        personModel = db.personDao().getAllPersons();

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new Adapter(db,personModel , context);
        recyclerView.setAdapter(adapter);

    }

//    public void updatePerson(int position) {
//      Intent intent= new Intent(context,AddActivity.class);
//      intent.putExtra("position",position);
//
//      startActivity(intent);
//
//    }





}
