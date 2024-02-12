package Kundprogram;

import Databasklasser.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

//Kolla upp sen så alla throws och catches är rätt.

public class SkoButik {

    Repositorium r = new Repositorium();
    Scanner sc = new Scanner(System.in);

    public SkoButik() throws IOException, SQLException {
        int nummerPåBeställning = 0;
        int skoID;

        //Frågar efter användarnamn & lösenord.
        System.out.println("Vad är ditt användarnamn?");
        String kundAnvändarnamn = sc.nextLine();
        System.out.println("Vad är ditt lösenord?");
        String kundLösenord = sc.nextLine();

        //Kontroll om medlem görs nedan. Program avslutas om ej kund.
        Kund användareKund = r.ärKundMedlem(kundAnvändarnamn, kundLösenord);
        if (användareKund == null) {
            System.out.println("Du är inte kund. Adjö!");
            System.exit(1);
        }
        //Testar eget funktionsgränssnitt & Lambda.
        Inloggad kundMeddelande = () -> System.out.println("Välkommen " + användareKund.getNamn() + "!");
        kundMeddelande.KundInloggad();

        while (true) {
            List<Sko> allaSkor = r.presenteraProdukter(); //Hämtar alla skor, lagras i lista av Sko-Objekt.
            System.out.println("Ange 1 för att skriva ut samtliga produkter.\nAnge 2 för att sortera på märke.");
            String användareVal = sc.nextLine();

            if (användareVal.equals("1")) { //Om anger 1, skriver ut samtliga produkter
                allaSkor.forEach(s -> System.out.println("Skriv skons namn: '" + s.getNamn() +
                        "' om du vill ha följande sko:\nFärg: " + s.getFärg() + ", Pris: " +
                        s.getPris() + " kr, Märke: " + s.getMärke() + ", Storlek: " +
                        s.getStorlek() + "\n"));
            } else if (användareVal.equals("2")) { //Om anger 2, får möjlighet att filtrera.
                System.out.println("Vilket märke vill du sortera på?\n" +
                        "- Adidas\n- Nike\n- Foodora \n- Ecco\n- Elektrolux\n- Nintendo");
                String användareValMärke = sc.nextLine();

                if (!användareValMärke.equals("Adidas") && !användareValMärke.equals("Nike")
                        && !användareValMärke.equals("Foodora") &&
                        !användareValMärke.equals("Ecco") && !användareValMärke.equals("Elektrolux")
                        && !användareValMärke.equals("Nintendo")) {
                    System.out.println("Du måste välja ett av märkena!");
                    continue;
                }
                System.out.println("Ange skons namn för den sko du vill ha!");
                allaSkor.stream().filter(x -> x.getMärke().equals(användareValMärke)).forEach(x ->
                        System.out.println("Namn: " + x.getNamn() + ", Färg: " + x.getFärg() + ", Pris: " + x.getPris() +
                                " kr, Märke: " + x.getMärke() + ", Storlek: " + x.getStorlek()));

            } else {
                System.out.println("Du måste ange 1, eller 2");
                continue;
            }

            String kundValAvSko = sc.nextLine(); //Sparar ner kunds val av sko.

            //Filtrerar ut eftersökt sko. Letar upp objekt där namn matchar med angiven sko av användare.
            List <Sko> listaSkoID = allaSkor.stream().filter(x -> x.getNamn().equals(kundValAvSko)).toList();
            if (!listaSkoID.isEmpty()) {
                Sko matchandeSko = listaSkoID.get(0); //Tar den första matchande skon
                skoID = matchandeSko.getID(); // Hämtar ID
                // Fortsätt med logiken här...
            } else {
                System.out.println("Ingen sko matchade ditt val. Försök igen.");
                continue;
            }

            if (nummerPåBeställning == 0) {
                List<Beställning> allaBeställningar = r.hämtaBeställningar();
                nummerPåBeställning = (int) allaBeställningar.stream().count() + 1; //Kunde använt .size() Typomvandlning??
            }

            //Metod som tar in 3 parametrar, kallar på SP i mySQL.
            r.läggTillSko(användareKund.getID(), nummerPåBeställning, skoID);

            System.out.println(kundValAvSko + " är tillagd i varukorg!\n" +
                    "Skriv Avsluta för avslut. Annars skriv något annat!");
            String kundInputAvslut = sc.nextLine().toLowerCase();
            if (kundInputAvslut.equals("avsluta")) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        SkoButik sb = new SkoButik();
    }
}
