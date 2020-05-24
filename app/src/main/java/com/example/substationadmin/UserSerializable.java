package com.example.substationadmin;

import java.io.Serializable;

public class UserSerializable implements Serializable {
    private String id, email, jabatan;
    private int wilayah;

    public UserSerializable(String id, String email, String jabatan, int wilayah ){
        this.id = id;
        this.email = email;
        this.jabatan = jabatan;
        this.wilayah = wilayah;
    }

    public UserSerializable(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public int getWilayah() {
        return wilayah;
    }

    public void setWilayah(int wilayah) {
        this.wilayah = wilayah;
    }
}
