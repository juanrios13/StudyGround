package com.futureapp.studyground;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ElectMateriasActivity extends AppCompatActivity {
    Button btnRegistro;

    // Variables firebase
    FirebaseAuth auth;
    DatabaseReference db;
    //Datos para registro
    String email="";
    String pwd="";
    String name="";
    String programa="";
    String univ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elect_materias);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        btnRegistro=(Button) findViewById(R.id.btnRegistro);

        ArrayList<String> listaMaterias = (ArrayList<String>) getIntent().getStringArrayListExtra("materias");
         email=getIntent().getStringExtra("email");
         pwd=getIntent().getStringExtra("pwd");
         name=getIntent().getStringExtra("name");
         programa=getIntent().getStringExtra("programa");
         univ=getIntent().getStringExtra("univ");


        ViewGroup checkboxContainer = (ViewGroup) findViewById(R.id.checkbox_container);

        for (int i = 0; i < listaMaterias.size(); i++) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(listaMaterias.get(i));
            checkboxContainer.addView(checkBox);
        }

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void registerUser() {
        auth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {


                    //Lista con valores para add to firebase
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("email", email);
                    map.put("pwd", pwd);
                    map.put("programa", programa);
                    map.put("universidad", univ);

                    String id = auth.getCurrentUser().getUid();

                    db.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                Toast.makeText(ElectMateriasActivity.this, "Datos subidos correctamente", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ElectMateriasActivity.this, ProfileMainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(ElectMateriasActivity.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(ElectMateriasActivity.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
