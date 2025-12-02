package com.inventaris.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entitas Barang Inventaris Kantor.
 * Dipetakan ke tabel 'barang' di database.
 */
@Entity
public class Barang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namaBarang;
    private String typeBarang;
    private String kondisi; // Baik, Rusak Ringan, Rusak Berat
    private int jumlahBarang;

    // Konstruktor default (Wajib untuk JPA/Hibernate)
    public Barang() {}

    // Konstruktor dengan parameter
    public Barang(String namaBarang, String typeBarang, String kondisi, int jumlahBarang) {
        this.namaBarang = namaBarang;
        this.typeBarang = typeBarang;
        this.kondisi = kondisi;
        this.jumlahBarang = jumlahBarang;
    }

    // --- Getters dan Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNamaBarang() { return namaBarang; }
    public void setNamaBarang(String namaBarang) { this.namaBarang = namaBarang; }
    public String getTypeBarang() { return typeBarang; }
    public void setTypeBarang(String typeBarang) { this.typeBarang = typeBarang; }
    public String getKondisi() { return kondisi; }
    public void setKondisi(String kondisi) { this.kondisi = kondisi; }
    public int getJumlahBarang() { return jumlahBarang; }
    public void setJumlahBarang(int jumlahBarang) { this.jumlahBarang = jumlahBarang; }

    @Override
    public String toString() {
        return "Barang{" + "id=" + id + ", namaBarang='" + namaBarang + '\'' + '}';
    }
}