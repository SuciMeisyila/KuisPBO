/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kuispbo658;

/**
 *
 * @author ASUS
 */
public class Penonton extends Bioskop {
    private int totalTiketDibeli;

    public Penonton(String id, String nama) {
        super(id, nama);
        this.totalTiketDibeli = 0;
    }

    public int getTotalTiketDibeli() {
        return totalTiketDibeli;
    }

    public void tambahTiketDibeli(int jumlah) {
        this.totalTiketDibeli += jumlah;
    }

    @Override
    public String getInfoRingkas() {
        return "[Penonton] ID: " + getId() + " | Nama: " + getNama() + " | Total Tiket: " + totalTiketDibeli;
    }
}