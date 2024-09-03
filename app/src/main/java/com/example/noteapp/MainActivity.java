package com.example.noteapp;

import static android.graphics.PorterDuff.Mode.ADD;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.noteapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ActivityResultLauncher<Intent> launcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        DBhandler db = new DBhandler(MainActivity.this);

        ArrayList<notemodel> notemodelArrayList = new ArrayList<>();
        Note_Adapter noteAdapter = new Note_Adapter(this, notemodelArrayList);

        binding.recyclerview.setAdapter(noteAdapter);

        List<notemodel> notemodelList = db.getnotemodelList();
        for (notemodel notes : notemodelList) {

            Log.d("priya", "id: " + notes.getId() + " title: " + notes.getTitle() + " note: " + notes.getNote());
            notemodelArrayList.add(notes);

        }

        launcher= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                      result-> {
                          if (result.getResultCode() == RESULT_OK) {
                              Intent update = result.getData();
                              if (update != null) {
                                  String id = update.getStringExtra("ids");
                                  assert id != null;
                                  notemodel note = db.getnoteid(Integer.parseInt(id));
                                  int updatedPosition = -1;
                                  for (int i = 0; i < notemodelArrayList.size(); i++) {
                                      if (notemodelArrayList.get(i).getId() == Integer.parseInt(id)){
                                          updatedPosition = i;
                                          break;

                                      }
                                  }
                                  Log.d("hii", "hei" + updatedPosition);
                                  notemodelArrayList.clear();
                                  notemodelArrayList.add(note);
                                  noteAdapter.notifyItemChanged(updatedPosition);
                              }
                          } else if (result.getResultCode() == 1) {
                              notemodelArrayList.clear();
                              List<notemodel> notes = db.getnotemodelList();
                              notemodelArrayList.addAll(notes);
                              noteAdapter.notifyItemInserted(notemodelArrayList.size());

                          }
                      });







        binding.addcontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Write your heart out :)", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                launcher.launch(intent);

            }
        });






    }

}

