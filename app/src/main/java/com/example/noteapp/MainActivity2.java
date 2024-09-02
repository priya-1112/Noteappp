package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.noteapp.databinding.ActivityMain2Binding;
import com.example.noteapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    ActivityMain2Binding binding;
    DBhandler db = new DBhandler(MainActivity2.this);

    public void onBackPressed() {

        saveandupdatedata();

        super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent update = getIntent();
        String id = update.getStringExtra("id");

        if (id!=null){
            notemodel note = db.getnoteid(Integer.parseInt(id));
            binding.notetitle.setText(note.getTitle());
            binding.notetype.setText(note.getNote());
        }

        binding.title1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveandupdatedata();

            }

        });

        binding.title1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveandupdatedata();


            }

        });




        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notemodel notemodel = new notemodel();
                Intent update = getIntent();
                String ID = update.getStringExtra("id");

                if (ID != null){
                    notemodel.setId(Integer.parseInt(ID));

                    db.delete(notemodel);
                    Toast.makeText(MainActivity2.this, "Successfully deleted", Toast.LENGTH_SHORT).show();


                }
                else {

                    Toast.makeText(MainActivity2.this, "Successfully deleted", Toast.LENGTH_SHORT).show();


                }

            }
        });

    }

        private void saveandupdatedata(){
        Intent update = getIntent();
        String ids = update.getStringExtra("id");


        String title = binding.notetitle.getText().toString();
        String content = binding.notetype.getText().toString();


        if (ids != null && content.isEmpty() && title.isEmpty() ){
            Toast.makeText(MainActivity2.this, "Cannot update empty note :|", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);

        }
        else if (ids!=null ) {
            notemodel notemodel = new notemodel();
            notemodel.setId(Integer.parseInt(ids));
            notemodel.setNote(content);
            notemodel.setTitle(title);
            db.update(notemodel);

            Intent updates  = new Intent(MainActivity2.this,MainActivity.class);
            updates.putExtra("ids",ids);

            Toast.makeText(MainActivity2.this, "Note Updated", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, updates);
            finish();


        }

        else if ( title.isEmpty() && content.isEmpty()) {
            Toast.makeText(MainActivity2.this, "Nothing to save :(", Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED);
            finish();


        }
        else{
            notemodel notemodel = new notemodel();
            notemodel.setNote(content);
            notemodel.setTitle(title);
            db.addnote(notemodel);

            Toast.makeText(MainActivity2.this, "Saved note successfully", Toast.LENGTH_SHORT).show();
            setResult(1);
            finish();


        }


    }
}


