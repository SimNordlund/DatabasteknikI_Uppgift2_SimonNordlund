package Databasklasser;

public class Lager {

    private int ID;
    private int Antal;

    public Lager(int ID, int antal) {
        this.ID = ID;
        Antal = antal;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getAntal() {
        return Antal;
    }

    public void setAntal(int antal) {
        Antal = antal;
    }
}
