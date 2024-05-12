package com.example.practice10;

import android.database.sqlite.SQLiteDatabase;

public class Contact {
    private int id; //идентификатор
    private String name;//имя
    private String surname;//имя
    private String password;//имя
    private String phone;//номер телефона
    public Contact(int id, String name, String phone, String surname, String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.surname = surname;
        this.password = password;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}