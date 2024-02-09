package Databasklasser;

public class Varuspecifikation {

    private int ID;
    private int antal;
    private int SkoID;
    private int BeställningID;

    public Varuspecifikation(int ID, int antal, int skoID, int beställningID) {
        this.ID = ID;
        this.antal = antal;
        SkoID = skoID;
        BeställningID = beställningID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getAntal() {
        return antal;
    }

    public void setAntal(int antal) {
        this.antal = antal;
    }

    public int getSkoID() {
        return SkoID;
    }

    public void setSkoID(int skoID) {
        SkoID = skoID;
    }

    public int getBeställningID() {
        return BeställningID;
    }

    public void setBeställningID(int beställningID) {
        BeställningID = beställningID;
    }
}
