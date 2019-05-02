package com.example.roomdatabase;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.roomdatabase.database.AppDatabase;
import com.example.roomdatabase.database.PersonModel;

import java.util.List;


public class AddActivity extends AppCompatActivity {

    public static PersonModel model;
    EditText edt_name,edt_family,edt_mobile;
    Button btn_savePerson , btn_update_person;


    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        context=this;

        edt_name =findViewById(R.id.edt_add_name);
        edt_family =findViewById(R.id.edt_add_family);
        edt_mobile=findViewById(R.id.edt_add_email);

        edt_name.setText("");
        edt_family.setText("");
        edt_mobile.setText("");

        btn_savePerson=findViewById(R.id.btn_save_person);


        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,"persons")
                .allowMainThreadQueries()
                .build();


        btn_savePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PersonModel personModel=new PersonModel(edt_name.getText().toString(),edt_family.getText().toString(),edt_mobile.getText().toString());
                db.personDao().insertAll(personModel);

                edt_name.setText("");
                edt_family.setText("");
                edt_mobile.setText("");

//                db.personDao().insertAll(new PersonModel(edt_name.getText().toString(),edt_family.getText().toString(),edt_email.getText().toString()));
                startActivity(new Intent(AddActivity.this,MainActivity.class));
            }
        });


// jahat clear and por kardane eit Text ha
        if(App.updateClicked ){
            edt_name.setText(model.getName());
            edt_mobile.setText(model.getMobile());
            edt_family.setText(model.getFamili());

        }else if(!App.updateClicked){
            edt_name.setText("");
            edt_mobile.setText("");
            edt_family.setText("");
        }else if(App.btnAddClickedMain){
            edt_name.setText("");
            edt_mobile.setText("");
            edt_family.setText("");
        }


        btn_update_person=findViewById(R.id.btn_update_person);
        btn_update_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                model.setName(edt_name.getText().toString());
                model.setFamili(edt_family.getText().toString());
                model.setMobile(edt_mobile.getText().toString());

              db.personDao().updatePerson(model);


                edt_name.setText("");
                edt_family.setText("");
                edt_mobile.setText("");

                // jahat clear kardane editTextha
                App.updateClicked=false;

//                db.personDao().insertAll(new PersonModel(edt_name.getText().toString(),edt_family.getText().toString(),edt_email.getText().toString()));
                startActivity(new Intent(AddActivity.this,MainActivity.class));


            }
        });
    }
}
