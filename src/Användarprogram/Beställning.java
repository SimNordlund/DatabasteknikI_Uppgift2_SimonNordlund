package Användarprogram;

import java.time.LocalDate;

public class Beställning {
    private int ID;
    private LocalDate datum;
    private int kundID;

    public Beställning() {
    }

    public Beställning(int ID, LocalDate datum, int kundID) {
        this.ID = ID;
        this.datum = datum;
        this.kundID = kundID;
    }
}
