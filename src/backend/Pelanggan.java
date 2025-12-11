package backend;
import java.sql.*;
import java.util.ArrayList;

public class Pelanggan {
    private int idPelanggan;
    private String nama;
    private String email;
    private String telepon;
    private String alamat;
    
    // Constructor
    public Pelanggan() {}
    
    public Pelanggan(String nama, String email, String telepon, String alamat) {
        this.nama = nama;
        this.email = email;
        this.telepon = telepon;
        this.alamat = alamat;
    }
    
    // Getters and Setters
    public int getIdPelanggan() { return idPelanggan; }
    public void setIdPelanggan(int idPelanggan) { this.idPelanggan = idPelanggan; }
    
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelepon() { return telepon; }
    public void setTelepon(String telepon) { this.telepon = telepon; }
    
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    
    // CRUD Methods
    public void simpan() {
        String sql = "INSERT INTO pelanggan (nama, email, telepon, alamat) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, nama);
            stmt.setString(2, email);
            stmt.setString(3, telepon);
            stmt.setString(4, alamat);
            stmt.executeUpdate();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.idPelanggan = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error simpan pelanggan: " + e.getMessage());
        }
    }
    
    public void update() {
        String sql = "UPDATE pelanggan SET nama=?, email=?, telepon=?, alamat=? WHERE id_pelanggan=?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nama);
            stmt.setString(2, email);
            stmt.setString(3, telepon);
            stmt.setString(4, alamat);
            stmt.setInt(5, idPelanggan);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error update pelanggan: " + e.getMessage());
        }
    }
    
    public void hapus() {
        String sql = "DELETE FROM pelanggan WHERE id_pelanggan=?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idPelanggan);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error hapus pelanggan: " + e.getMessage());
        }
    }
    
    public static ArrayList<Pelanggan> getAll() {
        ArrayList<Pelanggan> list = new ArrayList<>();
        String sql = "SELECT * FROM pelanggan";
        
        try (Connection conn = DBHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Pelanggan p = new Pelanggan();
                p.setIdPelanggan(rs.getInt("id_pelanggan"));
                p.setNama(rs.getString("nama"));
                p.setEmail(rs.getString("email"));
                p.setTelepon(rs.getString("telepon"));
                p.setAlamat(rs.getString("alamat"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error getAll pelanggan: " + e.getMessage());
        }
        return list;
    }
    
    public static ArrayList<Pelanggan> cari(String keyword) {
        ArrayList<Pelanggan> list = new ArrayList<>();
        String sql = "SELECT * FROM pelanggan WHERE nama LIKE ? OR email LIKE ? OR telepon LIKE ?";
        
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String pattern = "%" + keyword + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            stmt.setString(3, pattern);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Pelanggan p = new Pelanggan();
                p.setIdPelanggan(rs.getInt("id_pelanggan"));
                p.setNama(rs.getString("nama"));
                p.setEmail(rs.getString("email"));
                p.setTelepon(rs.getString("telepon"));
                p.setAlamat(rs.getString("alamat"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error cari pelanggan: " + e.getMessage());
        }
        return list;
    }
    
    @Override
public String toString() {
    return nama;
}

}