package com.futureapp.studyground;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.ArrayList;

public class ElectMateriasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elect_materias);


        ArrayList<String> listaMaterias = (ArrayList<String>) getIntent().getStringArrayListExtra("materias");

        ViewGroup checkboxContainer = (ViewGroup) findViewById(R.id.checkbox_container);

        for (int i = 0; i < listaMaterias.size(); i++) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(listaMaterias.get(i));
            checkboxContainer.addView(checkBox);
        }

    }
}
