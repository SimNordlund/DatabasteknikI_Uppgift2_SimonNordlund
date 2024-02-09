package Databasklasser;

public class Kund {

    private int ID;
    private String namn;
    private String ort;
    private String lösenord;
    private String användarnamn;

    public Kund() {
    }

    public Kund(int ID, String namn, String ort, String lösenord, String användarnamn) {
        this.ID = ID;
        this.namn = namn;
        this.ort = ort;
        this.lösenord = lösenord;
        this.användarnamn = användarnamn;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getLösenord() {
        return lösenord;
    }

    public void setLösenord(String lösenord) {
        this.lösenord = lösenord;
    }

    public String getAnvändarnamn() {
        return användarnamn;
    }

    public void setAnvändarnamn(String användarnamn) {
        this.användarnamn = användarnamn;
    }
}
