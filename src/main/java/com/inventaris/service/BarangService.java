package com.inventaris.service;

import com.inventaris.model.Barang;
import com.inventaris.repository.BarangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Layanan Logika Bisnis untuk entitas Barang.
 * Menggunakan @Autowired untuk menyuntikkan (inject) BarangRepository.
 */
@Service
public class BarangService {

    private final BarangRepository barangRepository;

    @Autowired
    public BarangService(BarangRepository barangRepository) {
        this.barangRepository = barangRepository;
    }

    // CREATE (Simpan atau Perbarui)
    public Barang simpanBarang(Barang barang) {
        return barangRepository.save(barang);
    }

    // READ All
    public List<Barang> findAllBarang() {
        return barangRepository.findAll();
    }

    // READ One
    public Optional<Barang> findBarangById(Long id) {
        return barangRepository.findById(id);
    }

    // UPDATE
    public Barang perbaruiBarang(Long id, Barang updatedBarang) {
        Optional<Barang> existingBarang = barangRepository.findById(id);
        if (existingBarang.isPresent()) {
            Barang barang = existingBarang.get();
            // Perbarui semua field
            barang.setNamaBarang(updatedBarang.getNamaBarang());
            barang.setTypeBarang(updatedBarang.getTypeBarang());
            barang.setKondisi(updatedBarang.getKondisi());
            barang.setJumlahBarang(updatedBarang.getJumlahBarang());
            return barangRepository.save(barang);
        } else {
            throw new RuntimeException("Barang dengan ID " + id + " tidak ditemukan.");
        }
    }

    // DELETE
    public void hapusBarang(Long id) {
        barangRepository.deleteById(id);
    }
}