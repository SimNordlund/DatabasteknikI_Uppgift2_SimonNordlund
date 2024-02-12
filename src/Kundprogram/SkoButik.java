package Kundprogram;

import Databasklasser.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SkoButik {
    Repositorium r = new Repositorium();
    Scanner sc = new Scanner(System.in);

    public SkoButik() throws IOException {
        int nummerPåBeställning = 0;

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
            List <String> skoMärken = allaSkor.stream().map(x -> x.getMärke()).distinct().toList();

            System.out.println("Ange 1 för att skriva ut samtliga produkter.\nAnge 2 för att sortera på märke.");
            String användareVal = sc.nextLine();

            if (användareVal.equals("1")) { //Om anger 1, skriver ut samtliga produkter
                allaSkor.forEach(x -> System.out.println("Skriv skons namn: '" + x.getNamn() +
                        "' om du vill ha följande sko:\nFärg: " + x.getFärg() + ", Pris: " +
                        x.getPris() + " kr, Märke: " + x.getMärke() + ", Storlek: " +
                        x.getStorlek() + "\n"));
            } else if (användareVal.equals("2")) { //Om anger 2, får möjlighet att filtrera.
                System.out.println("Vilket märke vill du sortera på?\n" +
                        skoMärken.stream().collect(Collectors.joining("\n- ", "- ", "")));
                String användareValMärke = sc.nextLine();

                //Kollar så att användaren angivit giltigt märke.
                boolean märkeFinns = skoMärken.stream().anyMatch(märke -> märke.equalsIgnoreCase(användareValMärke));
                if (!märkeFinns) {
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
            List<Sko> listaSkoID = allaSkor.stream().filter(x -> x.getNamn().equals(kundValAvSko)).toList();
            if (listaSkoID.isEmpty()) {
                System.out.println("Ingen sko matchade ditt val. Försök igen.");
                continue;
            }
            Sko matchandeSko = listaSkoID.get(0); //Hämtar Sko-Objektet om det finns matchning.

            //Finns det ingen beställning så tar fram nästa beställningsnummer.
            if (nummerPåBeställning == 0) {
                List<Beställning> allaBeställningar = r.hämtaBeställningar();
                nummerPåBeställning = allaBeställningar.size()+1; //Kunde använt .size()
            }

            //Metod som tar in 3 parametrar. SP
            r.läggTillSko(användareKund.getID(), nummerPåBeställning, matchandeSko.getID());

            System.out.println(kundValAvSko + " är tillagd i varukorg!\n" +
                    "Skriv Avsluta för avslut. Annars skriv något annat!");
            String kundInputAvslut = sc.nextLine().toLowerCase();

            if (kundInputAvslut.equals("avsluta")) {
                System.exit(0);
            }
        }
    }
    public static void main(String[] args) throws IOException{
        SkoButik sb = new SkoButik();
    }
}
