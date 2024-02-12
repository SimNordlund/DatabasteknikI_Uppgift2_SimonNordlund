package Databasklasser;

public class Kategori {
    private int ID;
    private String kategori;

    public Kategori(int ID, String kategori) {
        this.ID = ID;
        this.kategori = kategori;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}
