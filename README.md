📦 Sistem Inventaris Kantor (CRUD Desktop)
Proyek ini adalah aplikasi desktop untuk manajemen inventaris kantor, yang mengimplementasikan operasi CRUD (Create, Read, Update, Delete) dengan arsitektur modern menggunakan Java Swing untuk antarmuka, Spring Boot untuk framework, dan Hibernate/JPA untuk persistensi data ke database MySQL.

✨ Fitur Utama
- Create (Simpan): Menambahkan barang inventaris baru (Nama Barang, Tipe, Kondisi, Jumlah).
- Read (Baca): Menampilkan semua data inventaris dalam tabel yang real-time.
- Update (Perbarui): Memperbarui detail barang yang sudah ada.
- Delete (Hapus): Menghapus data barang dari database.
- Persistent Storage: Data disimpan secara permanen di database MySQL (via XAMPP).
- User Interface: Antarmuka pengguna yang intuitif dibangun dengan Java Swing

🛠️ Teknologi yang Digunakan
| Kategori | Teknologi | Tujuan |
| Frontend/GUI | Java Swing | Membangun antarmuka aplikasi desktop. |
| Backend/Framework | Spring Boot 3+ | Memudahkan konfigurasi dan menjalankan aplikasi Java Enterprise. |
| Data Access | Spring Data JPA / Hibernate | ORM (Object-Relational Mapping) untuk memetakan objek Java ke tabel database. |
| Database | MySQL (MariaDB via XAMPP) | Tempat penyimpanan data inventaris. |
| Build Tool | Maven | Manajemen dependensi proyek. |
| Java Version | JDK 21+ | Versi Java yang direkomendasikan. |

⚙️ Persyaratan Sistem
Sebelum menjalankan proyek ini, pastikan Anda memiliki:
- Java Development Kit (JDK): Versi 17 atau lebih tinggi (disarankan JDK 21/25).
- NetBeans IDE: Versi terbaru yang mendukung proyek Maven.
- XAMPP: Terinstal dan menjalankan modul Apache dan MySQL.

🚀 Langkah-Langkah Menjalankan Proyek
Ikuti langkah-langkah di bawah ini untuk mengatur dan menjalankan aplikasi:
1. Setup Database (XAMPP)
- Pastikan MySQL pada XAMPP sudah berjalan.
- Buka phpMyAdmin (http://localhost/phpmyadmin).
- Buat database baru dengan nama: inventaris_kantor.

2. Konfigurasi Proyek
- Update pom.xml: Pastikan file pom.xml Anda sudah berisi dependensi Spring Data JPA dan MySQL Connector.
- Buat Folder Resources: Pastikan folder src/main/resources/ sudah dibuat.
- Setup Koneksi Database: Letakkan file application.properties di dalam src/main/resources/. File ini berisi konfigurasi koneksi ke inventaris_kantor (pastikan spring.datasource.password sesuai dengan konfigurasi XAMPP Anda).

3. Build Proyek
- Setelah semua file kode (.java) berada di paket yang benar dan pom.xml sudah di-update:
- Klik kanan pada nama proyek di NetBeans.
- Pilih Clean and Build. Ini akan mengunduh semua dependensi Maven.

4. Eksekusi Aplikasi
- Pastikan XAMPP (MySQL) masih berjalan.
- Di NetBeans, navigasi ke com.inventaris.
- Klik kanan pada AplikasiInventaris.java.
- Pilih Run File (atau Shift + F6).
- Aplikasi Spring Boot akan memulai koneksi ke database, dan jendela aplikasi desktop Swing akan muncul, siap untuk operasi CRUD.
