package Kundprogram;

import Databasklasser.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//Spara ner kunduppgifter i Objekt av kund???????
//Kolla upp sen så alla throws och catches är rätt.
//Skapa kund + beställningsklass. Detta för att kunna lösa lambas och ha best practice.

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
                allaSkor.forEach(s -> System.out.println("Skriv " + s.getID() + " om du vill ha följande sko:\nFärg: " +
                        s.getFärg() + ", Pris: " + s.getPris() + " kr, Märke: " + s.getMärke() + ", Storlek: " +
                        s.getStorlek() + "\n"));
            } else if (användareVal.equals("2")) {

                System.out.println("Vilket märke vill du sortera på?");
                System.out.println("1 - Adidas\n2 - Nike\n3 - Foodora \n4 - Ecco\n5 - Elektrolux\n6 - Nintendo");
                String användareValMärke = sc.nextLine();

                if (användareValMärke.equals("1")) {

                    List <Sko> tempSkoLista = allaSkor.stream().filter(x -> x.getMärke().equals("Adidas")).toList();
                    Map<Integer, List<Sko>> tempMap = tempSkoLista.stream().collect(Collectors.groupingBy(n-> n.getID()));




                    allaSkor.stream().filter(x -> x.getMärke().equals("Adidas")).forEach(x ->
                            System.out.println("Färg: " + x.getFärg() + ", Pris: " + x.getPris() +
                                    " kr, Märke: " + x.getMärke() + ", Storlek: " + x.getStorlek() + "\n"));

                } else if (användareValMärke.equals("2")) {

                    allaSkor.stream().filter(x -> x.getMärke().equals("Nike")).forEach(x ->
                            System.out.println("Färg: " + x.getFärg() + ", Pris: " + x.getPris() +
                                    " kr, Märke: " + x.getMärke() + ", Storlek: " + x.getStorlek() + "\n"));



                } else if (användareValMärke.equals("3")) {

                    List <Sko> tempSkoLista = allaSkor.stream().filter(x -> x.getMärke().equals("Foodora")).toList();

                    allaSkor.stream().filter(x -> x.getMärke().equals("Foodora")).forEach(x ->
                            System.out.println("Färg: " + x.getFärg() + ", Pris: " + x.getPris() +
                                    " kr, Märke: " + x.getMärke() + ", Storlek: " + x.getStorlek() + "\n"));

                } else if (användareValMärke.equals("4")) {

                    allaSkor.stream().filter(x -> x.getMärke().equals("Ecco")).forEach(x ->
                            System.out.println("Färg: " + x.getFärg() + ", Pris: " + x.getPris() +
                                    " kr, Märke: " + x.getMärke() + ", Storlek: " + x.getStorlek() + "\n"));

                } else if (användareValMärke.equals("5")) {

                    allaSkor.stream().filter(x -> x.getMärke().equals("Elektrolux")).forEach(x ->
                            System.out.println("Färg: " + x.getFärg() + ", Pris: " + x.getPris() +
                                    " kr, Märke: " + x.getMärke() + ", Storlek: " + x.getStorlek() + "\n"));

                } else if (användareValMärke.equals("6")) {

                    allaSkor.stream().filter(x -> x.getMärke().equals("Nintendo")).forEach(x ->
                            System.out.println("Färg: " + x.getFärg() + ", Pris: " + x.getPris() +
                                    " kr, Märke: " + x.getMärke() + ", Storlek: " + x.getStorlek() + "\n"));
                }
            } else {
                System.out.println("Du måste ange 1, 2 eller 3. ");
                continue;
            }

            int kundValAvSko = Integer.parseInt(sc.nextLine()); //Fånga fel här måste med catch XD

            if (nummerPåBeställning == 0) {
                List<Beställning> allaBeställningar = r.hämtaBeställningar();
                //Taskig försök till lambda nedan? XD Kunde använt .size() Typomvandlning nedan.
                nummerPåBeställning = (int) allaBeställningar.stream().count() + 1;
            }

            r.läggTillSko(användareKund.getID(), nummerPåBeställning, kundValAvSko);
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
