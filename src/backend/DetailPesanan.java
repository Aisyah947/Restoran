package backend;

public class DetailPesanan {
    private int idDetail;
    private int idPesanan;
    private int idMenu;
    private int jumlah;
    private double subtotal;

    public DetailPesanan() {}

    public DetailPesanan(int idDetail, int idPesanan, int idMenu, int jumlah, double subtotal) {
        this.idDetail = idDetail;
        this.idPesanan = idPesanan;
        this.idMenu = idMenu;
        this.jumlah = jumlah;
        this.subtotal = subtotal;
    }

    public int getIdDetail() { return idDetail; }
    public int getIdPesanan() { return idPesanan; }
    public int getIdMenu() { return idMenu; }
    public int getJumlah() { return jumlah; }
    public double getSubtotal() { return subtotal; }

    public void setIdDetail(int idDetail) { this.idDetail = idDetail; }
    public void setIdPesanan(int idPesanan) { this.idPesanan = idPesanan; }
    public void setIdMenu(int idMenu) { this.idMenu = idMenu; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}