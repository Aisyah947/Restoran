package backend;

import java.sql.*;
import java.util.ArrayList;

public class Pesanan {
    private int idPesanan;
    private int idPelanggan;
    private String tanggalPesanan;
    private String status;

    // ===== CONSTRUCTOR =====
    public Pesanan() {}

    public Pesanan(int idPelanggan, String tanggalPesanan, String status) {
        this.idPelanggan = idPelanggan;
        this.tanggalPesanan = tanggalPesanan;
        this.status = status;
    }

    // ===== GETTER SETTER =====
    public int getIdPesanan() { return idPesanan; }
    public void setIdPesanan(int idPesanan) { this.idPesanan = idPesanan; }

    public int getIdPelanggan() { return idPelanggan; }
    public void setIdPelanggan(int idPelanggan) { this.idPelanggan = idPelanggan; }

    public String getTanggalPesanan() { return tanggalPesanan; }
    public void setTanggalPesanan(String tanggalPesanan) { this.tanggalPesanan = tanggalPesanan; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // ===== SIMPAN =====
    public void simpan() {
        String sql = "INSERT INTO pesanan (id_pelanggan, tanggal_pesanan, status) VALUES (?, ?, ?)";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, idPelanggan);
            stmt.setString(2, tanggalPesanan);
            stmt.setString(3, status);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.idPesanan = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error simpan pesanan: " + e.getMessage());
        }
    }

    // ===== UPDATE =====
    public void update() {
        String sql = "UPDATE pesanan SET id_pelanggan=?, tanggal_pesanan=?, status=? WHERE id_pesanan=?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPelanggan);
            stmt.setString(2, tanggalPesanan);
            stmt.setString(3, status);
            stmt.setInt(4, idPesanan);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error update pesanan: " + e.getMessage());
        }
    }

    // ===== HAPUS =====
    public void hapus() {
        String sql = "DELETE FROM pesanan WHERE id_pesanan=?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPesanan);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error hapus pesanan: " + e.getMessage());
        }
    }

    // ===== GET ALL =====
    public static ArrayList<Pesanan> getAll() {
        ArrayList<Pesanan> list = new ArrayList<>();
        String sql = "SELECT * FROM pesanan";

        try (Connection conn = DBHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pesanan p = new Pesanan();
                p.setIdPesanan(rs.getInt("id_pesanan"));
                p.setIdPelanggan(rs.getInt("id_pelanggan"));
                p.setTanggalPesanan(rs.getString("tanggal_pesanan"));
                p.setStatus(rs.getString("status"));

                list.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error getAll pesanan: " + e.getMessage());
        }
        return list;
    }

    // ===== CARI =====
    public static ArrayList<Pesanan> cari(String keyword) {
        ArrayList<Pesanan> list = new ArrayList<>();
        String sql = "SELECT * FROM pesanan WHERE id_pelanggan LIKE ? OR status LIKE ? OR tanggal_pesanan LIKE ?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String key = "%" + keyword + "%";

            stmt.setString(1, key);
            stmt.setString(2, key);
            stmt.setString(3, key);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pesanan p = new Pesanan();
                p.setIdPesanan(rs.getInt("id_pesanan"));
                p.setIdPelanggan(rs.getInt("id_pelanggan"));
                p.setTanggalPesanan(rs.getString("tanggal_pesanan"));
                p.setStatus(rs.getString("status"));

                list.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error cari pesanan: " + e.getMessage());
        }

        return list;
    }
}
