package com.example.roomdatabase.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PersonDao {

    //get all list
    @Query("SELECT * FROM personmodel")
    List<PersonModel> getAllPersons();


    //add
    @Insert
    void insertAll(PersonModel personModel);


    //search
    @Query("SELECT * FROM personmodel WHERE name = :name")
    List<PersonModel> findProduct(String name);


    // delete each item (use in adapter)
    @Delete
    void deletePerson(PersonModel personModel);

    //delete all
    @Delete
    void deleteAll(List<PersonModel> personModel);


    @Update
    void updatePerson(PersonModel personModels);
}
