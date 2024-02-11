package Kundprogram;

import Databasklasser.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

//Kolla upp sen så alla throws och catches är rätt.

public class SkoButik {

    Repositorium r = new Repositorium();
    Scanner sc = new Scanner(System.in);

    public SkoButik() throws IOException, SQLException {
        int nummerPåBeställning = 0;

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
            if (användareVal.equals("1")) {
                allaSkor.forEach(s -> System.out.println("Skriv skons namn: '" + s.getNamn() + "' om du vill ha följande sko:\nFärg: " +
                        s.getFärg() + ", Pris: " + s.getPris() + " kr, Märke: " + s.getMärke() + ", Storlek: " +
                        s.getStorlek() + "\n"));
            } else if (användareVal.equals("2")) {

                System.out.println("Vilket märke vill du sortera på?");
                System.out.println("1 - Adidas\n2 - Nike\n3 - Foodora \n4 - Ecco\n5 - Elektrolux\n6 - Nintendo");
                String användareValMärke = sc.nextLine();

                System.out.println("Ange skons namn för den sko du vill ha!");

                if (användareValMärke.equals("1")) {

                    allaSkor.stream().filter(x -> x.getMärke().equals("Adidas")).forEach(x ->
                            System.out.println("Namn: " + x.getNamn() + " Färg: " + x.getFärg() + ", Pris: " + x.getPris() +
                                    " kr, Märke: " + x.getMärke() + ", Storlek: " + x.getStorlek()));

                } else if (användareValMärke.equals("2")) {

                    allaSkor.stream().filter(x -> x.getMärke().equals("Nike")).forEach(x ->
                            System.out.println("Namn: " + x.getNamn() + " Färg: " + x.getFärg() + ", Pris: " + x.getPris() +
                                    " kr, Märke: " + x.getMärke() + ", Storlek: " + x.getStorlek()));


                } else if (användareValMärke.equals("3")) {

                    allaSkor.stream().filter(x -> x.getMärke().equals("Foodora")).forEach(x ->
                            System.out.println("Namn: " + x.getNamn() + " Färg: " + x.getFärg() + ", Pris: " + x.getPris() +
                                    " kr, Märke: " + x.getMärke() + ", Storlek: " + x.getStorlek()));

                } else if (användareValMärke.equals("4")) {

                    allaSkor.stream().filter(x -> x.getMärke().equals("Ecco")).forEach(x ->
                            System.out.println("Namn: " + x.getNamn() + " Färg: " + x.getFärg() + ", Pris: " + x.getPris() +
                                    " kr, Märke: " + x.getMärke() + ", Storlek: " + x.getStorlek()));

                } else if (användareValMärke.equals("5")) {

                    allaSkor.stream().filter(x -> x.getMärke().equals("Elektrolux")).forEach(x ->
                            System.out.println("Namn: " + x.getNamn() + " Färg: " + x.getFärg() + ", Pris: " + x.getPris() +
                                    " kr, Märke: " + x.getMärke() + ", Storlek: " + x.getStorlek()));

                } else if (användareValMärke.equals("6")) {

                    allaSkor.stream().filter(x -> x.getMärke().equals("Nintendo")).forEach(x ->
                            System.out.println("Namn: " + x.getNamn() + " Färg: " + x.getFärg() + ", Pris: " + x.getPris() +
                                    " kr, Märke: " + x.getMärke() + ", Storlek: " + x.getStorlek()));
                }
            } else {
                System.out.println("Du måste ange 1, 2 eller 3. ");
                continue;
            }

            String kundValAvSko = sc.nextLine(); //Sparar ner kunds val av sko.

            //Filtrerar ut eftersökt sko.
            List <Sko> listaSkoID = allaSkor.stream().filter(x -> x.getNamn().equals(kundValAvSko)).toList();
            int skoID;
            if (!listaSkoID.isEmpty()) {
                Sko matchandeSko = listaSkoID.get(0); // Antag att vi tar den första matchande skon
                skoID = matchandeSko.getID(); // Hämtar ID
                // Fortsätt med logiken här...
            } else {
                System.out.println("Ingen sko matchade ditt val. Försök igen.");
                continue;
            }


            if (nummerPåBeställning == 0) {
                List<Beställning> allaBeställningar = r.hämtaBeställningar();
                //Taskig försök till lambda nedan? XD Kunde använt .size() Typomvandlning nedan.
                nummerPåBeställning = (int) allaBeställningar.stream().count() + 1;
            }

            r.läggTillSko(användareKund.getID(), nummerPåBeställning, skoID);
            System.out.println("Skon är tillagd i varukorg!");


            System.out.println("Skriv Avsluta för avslut. Annars mera dojz!");
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
