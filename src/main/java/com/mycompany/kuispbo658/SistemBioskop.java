/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kuispbo658;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS
 */

public class SistemBioskop implements Serializable {
    private static final long serialVersionUID = 1L;
    private String namaBioskop;
    private Map<String, Penonton> daftarPenonton;
    private Map<String, JadwalFilm> daftarJadwalFilm;
    private List<TransaksiTiket> riwayatTransaksi;

    public SistemBioskop(String namaBioskop) {
        this.namaBioskop = namaBioskop;
        this.daftarPenonton = new HashMap<>();
        this.daftarJadwalFilm = new HashMap<>();
        this.riwayatTransaksi = new ArrayList<>();
    }

    public void tambahPenonton(String id, String nama) {
        Penonton p = new Penonton(id, nama);
        daftarPenonton.put(id, p);
    }

    public void tambahJadwalFilm(String kode, String judulDanStudio, int kuota) {
        JadwalFilm jf = new JadwalFilm(kode, judulDanStudio, kuota);
        daftarJadwalFilm.put(kode, jf);
    }

    public void jalankanTransaksiDariFile(String namaFile) throws Exception {
        File file = new File(namaFile);
        if (!file.exists()) {
            throw new FileNotFoundException("File '" + namaFile + "' tidak ditemukan di direktori project.");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String baris;
            int nomorBaris = 0;

            while ((baris = br.readLine()) != null) {
                nomorBaris++;
                baris = baris.trim();
                if (baris.isEmpty()) {
                    continue;
                }

                try {
                    prosesSatuBarisTransaksi(baris, nomorBaris);
                } catch (TransaksiInvalidException | NumberFormatException e) {
                    System.out.println("[TRANSAKSI DITOLAK - Baris " + nomorBaris + "] " + e.getMessage());
                }
            }
        }
    }

    private void prosesSatuBarisTransaksi(String baris, int nomorBaris) throws TransaksiInvalidException {
        String[] bagian = baris.split("/");
        if (bagian.length < 3) {
            throw new TransaksiInvalidException("Format baris tidak valid: '" + baris + "'");
        }

        String idPenonton = ekstrakNilai(bagian[0], "ID-");
        String kodeFilm = ekstrakNilai(bagian[1], "KD-");
        String strJumlah = ekstrakNilai(bagian[2], "JML-");

        int jumlah;
        try {
            jumlah = Integer.parseInt(strJumlah);
        } catch (NumberFormatException e) {
            throw new TransaksiInvalidException("Nilai JML bukan angka valid: '" + strJumlah + "'");
        }

        Penonton penonton = daftarPenonton.get(idPenonton);
        if (penonton == null) {
            throw new TransaksiInvalidException("ID Penonton '" + idPenonton + "' tidak terdaftar di sistem.");
        }

        JadwalFilm film = daftarJadwalFilm.get(kodeFilm);
        if (film == null) {
            throw new TransaksiInvalidException("Kode Film '" + kodeFilm + "' tidak ditemukan.");
        }

        film.kurangiKuota(jumlah);
        penonton.tambahTiketDibeli(jumlah);

        TransaksiTiket trx = new TransaksiTiket(penonton, film, jumlah);
        riwayatTransaksi.add(trx);

        System.out.println("[SUKSES - Baris " + nomorBaris + "] " + penonton.getNama() + " (" + idPenonton + ") membeli "
                + jumlah + " tiket '" + film.getNama() + "' (" + kodeFilm + "). Sisa kuota: " + film.getKuotaKursi());
    }

    private String ekstrakNilai(String segmen, String prefix) throws TransaksiInvalidException {
        String bersih = segmen.trim();
        if (!bersih.startsWith(prefix)) {
            throw new TransaksiInvalidException("Segmen '" + bersih + "' tidak diawali prefix '" + prefix + "'");
        }
        return bersih.substring(prefix.length()).trim();
    }

    public void simpanLaporanAkhir() {
        System.out.println("\n--- MENYIMPAN LAPORAN AKHIR & SERIALIZATION ---");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("snapshot.obj"))) {
            oos.writeObject(this);
            System.out.println("[SERIALIZATION BERHASIL] State objek sistem berhasil disimpan ke 'snapshot.obj'.");
        } catch (IOException e) {
            System.out.println("[SERIALIZATION GAGAL] Gagal menyimpan snapshot.obj: " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("laporan.txt"))) {
            bw.write("=== LAPORAN DAFTAR KUNCI (ID AKTOR AKTIF) - " + namaBioskop + " ===");
            bw.newLine();

            List<String> daftarIdUrut = new ArrayList<>(daftarPenonton.keySet());
            Collections.sort(daftarIdUrut);

            for (String idKunci : daftarIdUrut) {
                bw.write(idKunci);
                bw.newLine();
            }

            System.out.println("[LAPORAN TEKS BERHASIL] Daftar ID (keySet berurutan) berhasil ditulis ke 'laporan.txt'.");
        } catch (IOException e) {
            System.out.println("[LAPORAN TEKS GAGAL] Gagal menulis laporan.txt: " + e.getMessage());
        }
    }
}