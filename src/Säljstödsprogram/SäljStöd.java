package Säljstödsprogram;

import Databasklasser.Beställning;
import Databasklasser.Kund;
import Databasklasser.Repositorium;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map; // Importera Map från java.util
import java.util.stream.Collectors;

public class SäljStöd {

    Repositorium r = new Repositorium();

    public SäljStöd() throws SQLException, IOException {
        System.out.println("Vad behöver du hjälp med?");

        List<Kund> allaKunder = r.hämtaKunder();
        List<Beställning> allaBeställningar = r.hämtaBeställningar();

        //Fråga 1
        //Fråga 2

        //Skapa en Map med ID som nyckel och kundens namn som värde
        Map<Integer, String> IdOchNamn = allaKunder.stream()
                .collect(Collectors.toMap(k -> k.getID(), k -> k.getNamn()));
        IdOchNamn.forEach((id, namn) -> System.out.println(id + " " + namn));


        //Räkna antalet beställningar per kundID
        Map<Integer, Long> beställningarPerKund = allaBeställningar.stream()
                .collect(Collectors.groupingBy(b -> b.getKundID(), Collectors.counting()));
        beställningarPerKund.forEach((kundID, antalBeställningar) -> System.out.println(kundID + " " + antalBeställningar));

        Map<Integer, List<Beställning>> testMap123 = allaBeställningar.stream()
                .collect(Collectors.groupingBy(n -> n.getKundID()));


        //Rapport skapas och skrivs ut i konsol
        beställningarPerKund.forEach((kundID, antalBeställningar) -> {
            String kundNamn = IdOchNamn.get(kundID);
            System.out.println("Kund: " + kundNamn + ", Antal beställningar: " + antalBeställningar);
        });
        //Fråga 3

    }

    public static void main(String[] args) throws SQLException, IOException {
        SäljStöd ss = new SäljStöd();
    }
}
