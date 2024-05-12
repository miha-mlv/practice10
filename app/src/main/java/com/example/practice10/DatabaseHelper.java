package com.example.practice10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Пропишем в отдельные переменные название БД и её столбов
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_SURNAME = "Surname";
    private static final String COLUMN_PASSWORD = "Password";
    public DatabaseHelper(Context context) {
        super(context, "users.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_SURNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_PHONE + " TEXT)";
        db.execSQL(createTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    // Добавление нового контакта
    public boolean addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, contact.getName());
        cv.put(COLUMN_PHONE, contact.getPhone());
        cv.put(COLUMN_SURNAME, contact.getSurname());
        cv.put(COLUMN_PASSWORD, contact.getPassword());
        long result = db.insert(TABLE_NAME, null, cv);
        db.close();
        return result != -1;
    }
    // Удаление контакта по номеру телефона
    public boolean deleteContact(String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_PHONE + " = ?", new String[]{phone});
        db.close();
        return result > 0;
    }
    // Поиск контакта по номеру телефона
    public Contact findContact(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_SURNAME, COLUMN_PASSWORD, COLUMN_PHONE},
                COLUMN_PHONE + " = ?", new String[]{phone}, null,
                null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Contact contact = new Contact(cursor.getInt(0),
                    cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            cursor.close();
            db.close();
            return contact;
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return null;
    }
    // Получение всех контактов
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +
                TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(cursor.getInt(0),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contactList;
    }
    public boolean updateContact(String oldPhone, String newName,
                                 String newPhone, String surname, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, newName);
        cv.put(COLUMN_PHONE, newPhone);
        cv.put(COLUMN_SURNAME, surname);
        cv.put(COLUMN_PASSWORD, password);
        // Обновляем запись, где номер телефона равен oldPhone
        int result = db.update(TABLE_NAME, cv, COLUMN_PHONE + " = ?", new String[]{oldPhone});
                db.close();
        return result > 0;
    }
}
