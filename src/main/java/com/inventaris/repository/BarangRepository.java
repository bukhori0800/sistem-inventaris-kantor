package com.inventaris.repository;

import com.inventaris.model.Barang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository untuk entitas Barang.
 * Spring Data JPA secara otomatis menyediakan implementasi CRUD.
 */
@Repository
public interface BarangRepository extends JpaRepository<Barang, Long> {
    // Semua operasi CRUD (Save, FindAll, FindById, Delete) sudah tersedia.
}