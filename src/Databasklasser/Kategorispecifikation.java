package Databasklasser;

public class Kategorispecifikation {
    private int skoID;
    private int kategoriID;

    public Kategorispecifikation(int skoID, int kategoriID) {
        this.skoID = skoID;
        this.kategoriID = kategoriID;
    }

    public int getSkoID() {
        return skoID;
    }

    public void setSkoID(int skoID) {
        this.skoID = skoID;
    }

    public int getKategoriID() {
        return kategoriID;
    }

    public void setKategoriID(int kategoriID) {
        this.kategoriID = kategoriID;
    }
}
