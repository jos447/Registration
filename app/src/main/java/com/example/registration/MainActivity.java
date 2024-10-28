package com.example.registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
EditText e1,e2,e3,e4;
Button b1,b2,b3;
DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FirebaseApp.initializeApp(this);
        databaseReference= FirebaseDatabase.getInstance().getReference("x");
        e1=findViewById(R.id.name);
        e2=findViewById(R.id.course);
        e3=findViewById(R.id.fee);
        e4=findViewById(R.id.password);
        b1=findViewById(R.id.Verify);
        b2=findViewById(R.id.view);
        b3=findViewById(R.id.add);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Verify();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e4.setVisibility(View.VISIBLE);
                b1.setVisibility(View.VISIBLE);
            }
        });
    }
    public void Verify(){
        String pass=e4.getText().toString().trim();
        if(pass.equals("jose")){
            Toast.makeText(this, "Access Granted", Toast.LENGTH_SHORT).show();
            e4.setText("");
           e4.setVisibility(View.GONE);
          b1.setVisibility(View.GONE);


            Intent i=new Intent(this, MainActivity2.class);
            startActivity(i);
        }
        else if(pass.isEmpty()){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Incorect Password", Toast.LENGTH_SHORT).show();
            e4.setText("");
        }
    }
    public void add()
    {
        String name=e1.getText().toString().trim();
        String course=e2.getText().toString().trim();
        String fees=e3.getText().toString().trim();
        if(!name.isEmpty()&&!course.isEmpty()&&!fees.isEmpty())
        {
            String Combined=name + "  "+course + "  "+fees;
            String key=databaseReference.push().getKey();
            databaseReference.child(key).setValue(Combined).addOnSuccessListener(e ->{
                        Toast.makeText(this,"Data added Successful",Toast.LENGTH_SHORT).show();
                        e1.setText(" ");
                        e2.setText(" ");
                        e3.setText(" ");

        })

                    .addOnFailureListener(e ->{
                        Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
                        e1.setText(" ");
                        e2.setText(" ");
                        e3.setText(" ");
                    });

        }
        else{
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            e1.setText(" ");
            e2.setText(" ");
            e3.setText(" ");

        }
    }
}

