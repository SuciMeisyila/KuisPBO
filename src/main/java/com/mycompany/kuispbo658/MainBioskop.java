/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kuispbo658;

/**
 *
 * @author ASUS
 */
public class MainBioskop {
    public static void main(String[] args) {
        SistemBioskop bioskop = new SistemBioskop("Cinema XXI");
        bioskop.tambahPenonton("ACT-01", "Oki");
        bioskop.tambahPenonton("ACT-02", "Putri");
        bioskop.tambahJadwalFilm("OBJ-A", "Premiere Studio Action", 10);
        bioskop.tambahJadwalFilm("OBJ-B", "Reguler Studio Horor", 2);
        bioskop.tambahJadwalFilm("OBJ-C", "Ekonomis Studio Drama", 50);

        System.out.println("--- MEMULAI PEMBACAAN FILE TRANSAKSI ---");
        try {
            bioskop.jalankanTransaksiDariFile("input_transaksi.txt");
        } catch (Exception e) {
            System.out.println("[SISTEM FATAL ERROR] File gagal dibaca/diproses: " + e.getMessage());
        }

        bioskop.simpanLaporanAkhir();
    }
}