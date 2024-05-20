package com.example.practice10;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText nameInput = findViewById(R.id.name_input);
        EditText phoneInput = findViewById(R.id.phone_input);
        EditText surnameInput = findViewById(R.id.surname_input);
        EditText passwordInput = findViewById(R.id.password_input);
        Button saveButton = findViewById(R.id.save_button);
        Button deleteButton = findViewById(R.id.delete_button);
        Button findButton = findViewById(R.id.find_button);
        RecyclerView contactsList = findViewById(R.id.contacts_list);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Contact> contacts = dbHelper.getAllContacts();
        ContactAdapter adapter = new ContactAdapter(contacts);
        contactsList.setLayoutManager(new LinearLayoutManager(this));
        contactsList.setAdapter(adapter);
        //Прописываем логику для сохранения нового контакта
        saveButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            String phone = phoneInput.getText().toString();
            String surname = surnameInput.getText().toString();
            String password = passwordInput.getText().toString();
            if (dbHelper.addContact(new Contact(0, name, phone, surname, password)))
            {
                contacts.add(new Contact(0, name, phone, surname, password));
                adapter.notifyItemInserted(contacts.size() - 1);
                Toast.makeText(this, "Contact saved successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to save contact", Toast.LENGTH_SHORT).show();
            }
        });
        //удаляем контакт
        deleteButton.setOnClickListener(v -> {
            String phone = phoneInput.getText().toString();
            if (dbHelper.deleteContact(phone)) {
                int position = -1;
                for (int i = 0; i < contacts.size(); i++) {
                    if (contacts.get(i).getPhone().equals(phone))
                    {
                        position = i;
                        contacts.remove(i);
                        break;
                    }
                }
                if (position != -1) {
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(this, "Contact deleted successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Contact not found",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Failed to delete contact",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //ищем контакт по номеру телефона
        findButton.setOnClickListener(v -> {
            String phone = phoneInput.getText().toString();
            Contact foundContact = dbHelper.findContact(phone);
            if (foundContact != null) {
                nameInput.setText(foundContact.getName());
                phoneInput.setText(foundContact.getPhone());
                passwordInput.setText(foundContact.getPassword());
                surnameInput.setText(foundContact.getSurname());
                Toast.makeText(this, "Contact found: " +
                        foundContact.getName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Contact not found",
                        Toast.LENGTH_SHORT).show();
            }
        });
//обновляем данные
        Button updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(v -> {
            String oldPhone = phoneInput.getText().toString();
            String newName = nameInput.getText().toString();
            String newPhone = phoneInput.getText().toString();
            String newSurname = surnameInput.getText().toString();
            String newPassword = passwordInput.getText().toString();
            if (dbHelper.updateContact(oldPhone, newName, newPhone, newSurname, newPassword)) {
                Toast.makeText(this, "Contact updated successfully!", Toast.LENGTH_SHORT).show();
                // Обновляем список и адаптер
                refreshContactsList(dbHelper, contacts, adapter,
                        contactsList);
            } else {
                Toast.makeText(this, "Failed to update contact",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Метод для обновления списка контактов после изменения в базе данных
    private void refreshContactsList(DatabaseHelper dbHelper,
                                     List<Contact> contacts, ContactAdapter adapter, RecyclerView
                                             contactsList) {
        contacts = dbHelper.getAllContacts(); // Загружаемобновленный список
        adapter = new ContactAdapter(contacts);
        contactsList.setAdapter(adapter);
    }
}
