package backend;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Menu {

    private int idMenu;
    private String namaMenu;
    private String kategori;
    private double harga;

    // ===== Constructor =====
    public Menu() {}

    public Menu(String namaMenu, String kategori, double harga) {
        this.namaMenu = namaMenu;
        this.kategori = kategori;
        this.harga = harga;
    }

    // ===== Getter Setter =====
    public int getIdMenu() { return idMenu; }
    public void setIdMenu(int idMenu) { this.idMenu = idMenu; }

    public String getNamaMenu() { return namaMenu; }
    public void setNamaMenu(String namaMenu) { this.namaMenu = namaMenu; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }

    // ===== GET BY ID =====
    public static Menu getById(int id) {
        Menu m = null;
        String sql = "SELECT * FROM menu WHERE id_menu = ?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                m = new Menu();
                m.setIdMenu(rs.getInt("id_menu"));
                m.setNamaMenu(rs.getString("nama_menu"));
                m.setKategori(rs.getString("kategori"));
                m.setHarga(rs.getDouble("harga"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return m;
    }

    // ===== GET ALL =====
    public static ArrayList<Menu> getAll(String keyword) {
        ArrayList<Menu> list = new ArrayList<>();

        String sql = "SELECT * FROM menu WHERE nama_menu LIKE ? OR kategori LIKE ?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Menu m = new Menu();
                m.setIdMenu(rs.getInt("id_menu"));
                m.setNamaMenu(rs.getString("nama_menu"));
                m.setKategori(rs.getString("kategori"));
                m.setHarga(rs.getDouble("harga"));
                list.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ===== SAVE (INSERT / UPDATE) =====
    public void save() {
        if (this.idMenu == 0) {
            String sql = "INSERT INTO menu (nama_menu, kategori, harga) VALUES (?, ?, ?)";

            try (Connection conn = DBHelper.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, this.namaMenu);
                ps.setString(2, this.kategori);
                ps.setDouble(3, this.harga);
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    this.idMenu = rs.getInt(1);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            String sql = "UPDATE menu SET nama_menu=?, kategori=?, harga=? WHERE id_menu=?";

            try (Connection conn = DBHelper.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, this.namaMenu);
                ps.setString(2, this.kategori);
                ps.setDouble(3, this.harga);
                ps.setInt(4, this.idMenu);
                ps.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ===== DELETE =====
    public static boolean delete(int id) {
        String sql = "DELETE FROM menu WHERE id_menu = ?";

        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

