package com.inventaris;

import com.inventaris.model.Barang;
import com.inventaris.service.BarangService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Kelas Utama Aplikasi Inventaris.
 * Menggabungkan Spring Boot, Hibernate, dan Java Swing.
 */
@SpringBootApplication(scanBasePackages = {"com.inventaris"})
public class AplikasiInventaris {

    // Main method untuk menjalankan Spring Boot Application
    public static void main(String[] args) {
        // Jalankan aplikasi Spring Boot secara non-headless agar Swing dapat berjalan
        new SpringApplicationBuilder(AplikasiInventaris.class)
                .headless(false)
                .run(args);
    }

    /**
     * Bean untuk menjalankan GUI setelah Spring context siap.
     */
    @Bean
    public CommandLineRunner run(BarangService barangService) {
        return args -> {
            // Jalankan GUI di Event Dispatch Thread (Wajib untuk Swing)
            SwingUtilities.invokeLater(() -> {
                InventoryGui gui = new InventoryGui(barangService);
                gui.setVisible(true);
            });
        };
    }
}

// =================================================================
// Kelas GUI Swing
// =================================================================

class InventoryGui extends JFrame {

    private final BarangService barangService;

    // Komponen GUI
    private final JTextField txtNama, txtType, txtJumlah;
    private final JComboBox<String> cmbKondisi;
    private final JButton btnSimpan, btnUpdate, btnHapus, btnClear;
    private final JTable tableBarang;
    private final DefaultTableModel tableModel;

    // Status untuk mode Update/Delete
    private Long selectedBarangId = null;

    public InventoryGui(BarangService barangService) {
        this.barangService = barangService;

        // --- Setup JFRAME ---
        setTitle("Sistem Inventaris Kantor (CRUD Spring/Hibernate MySQL)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        
        // --- Model Tabel ---
        String[] columnNames = {"ID", "Nama Barang", "Type", "Kondisi", "Jumlah"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Membuat semua sel tidak dapat diedit
            }
        };
        tableBarang = new JTable(tableModel);
        tableBarang.setFont(new Font("Arial", Font.PLAIN, 12));
        tableBarang.setRowHeight(25);
        tableBarang.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(tableBarang);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Barang Inventaris"));

        // --- Panel Input ---
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        txtNama = new JTextField(20);
        txtType = new JTextField(20);
        txtJumlah = new JTextField(20);
        String[] kondisiOptions = {"Baik", "Rusak Ringan", "Rusak Berat"};
        cmbKondisi = new JComboBox<>(kondisiOptions);
        cmbKondisi.setSelectedItem("Baik");

        JLabel lblNama = new JLabel("Nama Barang:");
        lblNama.setFont(labelFont);
        JLabel lblType = new JLabel("Type/Model:");
        lblType.setFont(labelFont);
        JLabel lblKondisi = new JLabel("Kondisi:");
        lblKondisi.setFont(labelFont);
        JLabel lblJumlah = new JLabel("Jumlah Barang:");
        lblJumlah.setFont(labelFont);

        inputPanel.add(lblNama);
        inputPanel.add(txtNama);
        inputPanel.add(lblType);
        inputPanel.add(txtType);
        inputPanel.add(lblKondisi);
        inputPanel.add(cmbKondisi);
        inputPanel.add(lblJumlah);
        inputPanel.add(txtJumlah);

        // --- Panel Tombol ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnSimpan = new JButton("Simpan (Tambah)");
        btnUpdate = new JButton("Perbarui Data");
        btnHapus = new JButton("Hapus Data");
        btnClear = new JButton("Reset Form");
        
        // Styling tombol
        btnSimpan.setBackground(new Color(60, 179, 113)); // Medium Sea Green
        btnSimpan.setForeground(Color.WHITE);
        btnUpdate.setBackground(new Color(30, 144, 255)); // Dodger Blue
        btnUpdate.setForeground(Color.WHITE);
        btnHapus.setBackground(new Color(255, 99, 71)); // Tomato
        btnHapus.setForeground(Color.WHITE);
        btnClear.setBackground(new Color(192, 192, 192)); // Silver

        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnClear);

        // --- Layout Utama ---
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(inputPanel, BorderLayout.NORTH);
        controlPanel.add(buttonPanel, BorderLayout.CENTER);
        
        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        // --- Listeners ---
        btnSimpan.addActionListener(e -> simpanBarang());
        btnUpdate.addActionListener(e -> perbaruiBarang());
        btnHapus.addActionListener(e -> hapusBarang());
        btnClear.addActionListener(e -> clearForm());

        // Listener saat baris tabel dipilih (untuk Update/Delete)
        tableBarang.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tableBarang.getSelectedRow() != -1) {
                int selectedRow = tableBarang.getSelectedRow();
                // Ambil ID dari kolom pertama (index 0)
                selectedBarangId = (Long) tableModel.getValueAt(selectedRow, 0); 

                txtNama.setText(tableModel.getValueAt(selectedRow, 1).toString());
                txtType.setText(tableModel.getValueAt(selectedRow, 2).toString());
                cmbKondisi.setSelectedItem(tableModel.getValueAt(selectedRow, 3).toString());
                txtJumlah.setText(tableModel.getValueAt(selectedRow, 4).toString());
                
                // Aktifkan tombol Update/Hapus dan Nonaktifkan Simpan
                btnUpdate.setEnabled(true);
                btnHapus.setEnabled(true);
                btnSimpan.setEnabled(false);

            } else {
                // Jika tidak ada yang terpilih atau deseleksi, bersihkan form
                if (!e.getValueIsAdjusting()) {
                    clearForm();
                }
            }
        });

        // Muat data awal
        loadData();
        
        // Finalisasi tampilan
        pack();
        setSize(900, 650); // Ukuran default
        setLocationRelativeTo(null); // Posisikan di tengah layar
        btnUpdate.setEnabled(false);
        btnHapus.setEnabled(false);
    }

    // METHOD: READ (Memuat data dari database dan mengisi tabel)
    private void loadData() {
        // Jalankan di thread Swing
        SwingUtilities.invokeLater(() -> {
            try {
                tableModel.setRowCount(0); // Bersihkan tabel
                List<Barang> barangList = barangService.findAllBarang();
                for (Barang barang : barangList) {
                    Object[] row = new Object[]{
                            barang.getId(),
                            barang.getNamaBarang(),
                            barang.getTypeBarang(),
                            barang.getKondisi(),
                            barang.getJumlahBarang()
                    };
                    tableModel.addRow(row);
                }
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(this, "Gagal memuat data. Pastikan XAMPP (MySQL) sudah berjalan dan koneksi sudah benar di application.properties.", "Koneksi Database Gagal", JOptionPane.ERROR_MESSAGE);
                 System.err.println("Error saat memuat data: " + e.getMessage());
            }
        });
    }

    // METHOD: Membersihkan form input dan mereset status
    private void clearForm() {
        selectedBarangId = null;
        txtNama.setText("");
        txtType.setText("");
        cmbKondisi.setSelectedItem("Baik"); 
        txtJumlah.setText("");
        
        tableBarang.clearSelection();
        btnSimpan.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    
    // METHOD: Validasi input
    private boolean validateInput() {
        if (txtNama.getText().trim().isEmpty() || txtType.getText().trim().isEmpty() || txtJumlah.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama, Type, dan Jumlah wajib diisi.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            if (Integer.parseInt(txtJumlah.getText().trim()) < 0) {
                 JOptionPane.showMessageDialog(this, "Jumlah Barang tidak boleh negatif.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
                 return false;
            }
            // Cek apakah input jumlah adalah angka valid
            Integer.parseInt(txtJumlah.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Jumlah Barang harus berupa angka yang valid.", "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }


    // METHOD: CREATE (Menyimpan barang baru)
    private void simpanBarang() {
        if (!validateInput()) return;
        
        try {
            String nama = txtNama.getText().trim();
            String type = txtType.getText().trim();
            String kondisi = (String) cmbKondisi.getSelectedItem();
            int jumlah = Integer.parseInt(txtJumlah.getText().trim());

            Barang newBarang = new Barang(nama, type, kondisi, jumlah);
            barangService.simpanBarang(newBarang);
            JOptionPane.showMessageDialog(this, "Barang berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            
            loadData(); // REFRESH READ
            clearForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data. Error: " + ex.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // METHOD: UPDATE (Memperbarui barang yang sudah dipilih)
    private void perbaruiBarang() {
        if (selectedBarangId == null) {
            JOptionPane.showMessageDialog(this, "Silakan pilih barang yang ingin diperbarui dari tabel.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validateInput()) return;


        try {
            String nama = txtNama.getText().trim();
            String type = txtType.getText().trim();
            String kondisi = (String) cmbKondisi.getSelectedItem();
            int jumlah = Integer.parseInt(txtJumlah.getText().trim());
            
            Barang updatedBarang = new Barang(nama, type, kondisi, jumlah);
            
            // ID dari barang yang dipilih
            barangService.perbaruiBarang(selectedBarangId, updatedBarang); 
            JOptionPane.showMessageDialog(this, "Barang berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);

            loadData(); // REFRESH READ
            clearForm();
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error Update", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal perbarui data. Error: " + ex.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // METHOD: DELETE (Menghapus barang yang sudah dipilih)
    private void hapusBarang() {
        if (selectedBarangId == null) {
            JOptionPane.showMessageDialog(this, "Silakan pilih barang yang ingin dihapus dari tabel.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int dialogResult = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus barang ID: " + selectedBarangId + "?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (dialogResult == JOptionPane.YES_OPTION) {
            try {
                barangService.hapusBarang(selectedBarangId);
                JOptionPane.showMessageDialog(this, "Barang berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                
                loadData(); // REFRESH READ
                clearForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data. Error: " + ex.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
}