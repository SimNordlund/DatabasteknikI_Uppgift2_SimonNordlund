package Databasklasser;

public class Sko {

    private int ID;
    private String färg;
    private double pris;
    private String märke;
    private int storlek;
    private String namn;
    private int lagerID;

    public Sko() {
    }

    public Sko(int ID, String färg, double pris, String märke, int storlek, String namn) {
        this.ID = ID;
        this.färg = färg;
        this.pris = pris;
        this.märke = märke;
        this.storlek = storlek;
        this.namn = namn;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFärg() {
        return färg;
    }

    public void setFärg(String färg) {
        this.färg = färg;
    }

    public double getPris() {
        return pris;
    }

    public void setPris(double pris) {
        this.pris = pris;
    }

    public String getMärke() {
        return märke;
    }

    public void setMärke(String märke) {
        this.märke = märke;
    }

    public int getStorlek() {
        return storlek;
    }

    public void setStorlek(int storlek) {
        this.storlek = storlek;
    }

    public int getLagerID() {
        return lagerID;
    }

    public void setLagerID(int lagerID) {
        this.lagerID = lagerID;
    }
}
