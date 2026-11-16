/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.kuispbo658;

/**
 *
 * @author ASUS
 */
public class JadwalFilm extends Bioskop {
    private int kuotaKursi;

    public JadwalFilm(String id, String judulDanStudio, int kuotaKursi) {
        super(id, judulDanStudio);
        this.kuotaKursi = kuotaKursi;
    }

    public int getKuotaKursi() {
        return kuotaKursi;
    }

    public void kurangiKuota(int jumlah) throws TransaksiInvalidException {
        if (jumlah <= 0) {
            throw new TransaksiInvalidException("Jumlah tiket harus lebih dari 0.");
        }
        if (this.kuotaKursi < jumlah) {
            throw new TransaksiInvalidException(
                "Kuota tidak mencukupi untuk " + getNama() + " (Sisa: " + this.kuotaKursi + ", Diminta: " + jumlah + ")");
        }
        this.kuotaKursi -= jumlah;
    }

    @Override
    public String getInfoRingkas() {
        return "[JadwalFilm] Kode: " + getId() + " | Film/Studio: " + getNama() + " | Sisa Kuota: " + kuotaKursi;
    }

}