package com.example.roomdatabase;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    RelativeLayout rl_cancel_search ,rl_search;
    FloatingActionButton fab_add,fab_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        init();

// to hidden FAB while swiping recyclerView
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){

                if (dy > 0 ||dy<0 && fab_add.isShown() && fab_delete.isShown()){
                    fab_add.hide();
                    fab_delete.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    fab_add.show();
                    fab_delete.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });



        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.btnAddClickedMain = true;
                App.updateClicked = false;
                startActivity(new Intent(context,AddActivity.class));
            }
        });

        fab_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.personDao().deleteAll(personModel);
                refreshList();
            }
        });


        refreshList();


         rl_search.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 personModel = db.personDao().findProduct(edt_search.getText().toString());
                 adapter = new Adapter(db,personModel, context);
                 recyclerView.setAdapter(adapter);
                 Toast.makeText(MainActivity.this, personModel.size() + " مورد پیدا شد", Toast.LENGTH_SHORT).show();
             }
         });


        rl_cancel_search.setOnClickListener(new View.OnClickListener() {
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

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new Adapter(db,personModel , context);
        recyclerView.setAdapter(adapter);
    }


    private void init() {
        recyclerView=findViewById(R.id.recyclerview);
        fab_add=findViewById(R.id.fab_Add);
        fab_delete=findViewById(R.id.fab_delete);
        edt_search=findViewById(R.id.edt_search);
        rl_search=findViewById(R.id.rl_search);
        rl_cancel_search=findViewById(R.id.rl_cancel_search);
    }
}
