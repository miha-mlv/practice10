package com.example.practice10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.practice10.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("myPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Phone", binding.editNumber.getText().toString());
                editor.putString("Surname", binding.editSurname.getText().toString());
                editor.putString("Name", binding.editName.getText().toString());
                // Сохранение изменений
                editor.apply();
                Toast.makeText(MainActivity.this, "Данные записаны", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("myPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                // Удаление данных по ключу
                editor.remove("username");
                // Удаление всех данных
                editor.clear();
                // Применение изменений
                editor.apply();
                Toast.makeText(MainActivity.this, "Данные удалены", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("myPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Phone", binding.editNumber.getText().toString());
                editor.putString("Surname", binding.editSurname.getText().toString());
                editor.putString("Name", binding.editName.getText().toString());
                // Сохранение изменений
                editor.apply();
                Toast.makeText(MainActivity.this, "Данные изменены", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получение данных
                SharedPreferences sharedPreferences = getSharedPreferences("myPreferences", MODE_PRIVATE);
                // Чтение строкового значения
                binding.editNumber.setText(sharedPreferences.getString("Phone", "Номер телефона"));
                binding.editSurname.setText(sharedPreferences.getString("Surname", "Фамилия"));
                binding.editName.setText(sharedPreferences.getString("Name", "Имя"));

                Toast.makeText(MainActivity.this, "Данные получены", Toast.LENGTH_SHORT).show();
            }
        });

    }
}