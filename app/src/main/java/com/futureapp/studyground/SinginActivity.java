package com.futureapp.studyground;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class SinginActivity extends AppCompatActivity {

    private EditText txtemail;
    private EditText txtpwd;
    private EditText txtcPwd;
    private EditText txtname;
    private Button buttonRegistro;
    private Button buttonReturn;
    private Spinner spinPrograma;
    private Spinner spinUniversidad;
    private Spinner spinMaterias;

    //Datos a registrar
    private String name = "";
    private String email = "";
    private String pwd = "";
    private String cpwd = "";
    private String univ = "";
    private String[] materias = new String[10];
    private String programa = "";

    // Variables firebase
    FirebaseAuth auth;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //instacia firebase auth
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        txtemail = (EditText) findViewById(R.id.txtemail);
        txtpwd = (EditText) findViewById(R.id.txtpwd);
        txtcPwd = (EditText) findViewById(R.id.txtcPwd);
        txtname = (EditText) findViewById(R.id.txtname);
        buttonRegistro = (Button) findViewById(R.id.buttonRegistro);
        buttonReturn = (Button) findViewById(R.id.buttonReturn);
        spinUniversidad = (Spinner) findViewById(R.id.spinuniversidad);
        spinPrograma = (Spinner) findViewById(R.id.spinprograma);
        spinMaterias = (Spinner) findViewById(R.id.spinmaterias);

        spinPrograma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                programa = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = txtemail.getText().toString();
                pwd = txtpwd.getText().toString();
                cpwd = txtcPwd.getText().toString();
                name = txtname.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !pwd.isEmpty() && !cpwd.isEmpty() && !programa.isEmpty()) {
                    if (pwd.length() >= 6) {
                        if (pwd.equals(cpwd)) {
                            registerUser();
                        } else {
                            Toast.makeText(SinginActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(SinginActivity.this, "La contraseña debe contener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SinginActivity.this, "Debes completar los campos", Toast.LENGTH_SHORT).show();
                }

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

                    String id = auth.getCurrentUser().getUid();

                    db.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {
                                Toast.makeText(SinginActivity.this, "Datos subidos correctamente", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SinginActivity.this, ProfileMainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(SinginActivity.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(SinginActivity.this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
