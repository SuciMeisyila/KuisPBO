/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kuispbo658;

/**
 *
 * @author ASUS
 */
import java.io.Serializable;

public class TransaksiTiket implements Serializable {
    private static final long serialVersionUID = 1L;
    private Penonton penonton;
    private JadwalFilm jadwalFilm;
    private int jumlah;

    public TransaksiTiket(Penonton penonton, JadwalFilm jadwalFilm, int jumlah) {
        this.penonton = penonton;
        this.jadwalFilm = jadwalFilm;
        this.jumlah = jumlah;
    }

    public Penonton getPenonton() {
        return penonton;
    }

    public JadwalFilm getJadwalFilm() {
        return jadwalFilm;
    }

    public int getJumlah() {
        return jumlah;
    }
}