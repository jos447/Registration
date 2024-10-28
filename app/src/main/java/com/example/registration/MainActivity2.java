package com.example.registration;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    ListView li;
    Button bt1;
    DatabaseReference databaseReference;
    List<String>dataList;
    ArrayAdapter<String>adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        li=findViewById(R.id.list);
        dataList=new ArrayList<>();
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,dataList);
        li.setAdapter(adapter);
        bt1=findViewById(R.id.clearButton);
        databaseReference= FirebaseDatabase.getInstance().getReference("x");
        loadData();
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearListView();
            }
        });

    }

    private void clearListView() {
       // databaseReference.removeEventListener(valueEventListener);
        dataList.clear();  // Clear the list
        adapter.notifyDataSetChanged();  // Notify the adapter to refresh the ListView
    }

    private void loadData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                dataList.clear();
                int count = 1;
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    String d=snapshot.getValue(String.class);
                    dataList.add(d);
                    if (d != null) {
                        dataList.add(count + ". " + d); // Prepend the number
                        count++;
                    }
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(this,"Error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity2.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}