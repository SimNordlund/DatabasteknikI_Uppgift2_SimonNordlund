package Databasklasser;

import java.time.LocalDate;


public class Beställning {
    private int ID;
    private LocalDate datum;
    private int kundID;

    public Beställning() {}

    public Beställning(int ID) { //Sen för att ev. justera G-uppgift.
        this.ID = ID;
    }

    public Beställning(int ID, LocalDate datum, int kundID) {
        this.ID = ID;
        this.datum = datum;
        this.kundID = kundID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public int getKundID() {
        return kundID;
    }

    public void setKundID(int kundID) {
        this.kundID = kundID;
    }
}
