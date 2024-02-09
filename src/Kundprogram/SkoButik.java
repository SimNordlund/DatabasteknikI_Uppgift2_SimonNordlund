package Kundprogram;

import Databasklasser.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

//Spara ner kunduppgifter i Objekt av kund???????
//Kolla upp sen så alla throws och catches är rätt.
//Skapa kund + beställningsklass. Detta för att kunna lösa lambas och ha best practice.

public class SkoButik {

    Repositorium r = new Repositorium();
    Sko s = new Sko();
    Scanner sc = new Scanner(System.in);

    public SkoButik() throws IOException, SQLException {
        int nummerPåBeställning = 0;

        System.out.println("Vad är ditt användarnamn?");
        String kundAnvändarnamn = sc.nextLine();
        System.out.println("Vad är ditt lösenord?");
        String kundLösenord = sc.nextLine();

        Kund användareKund = r.ärKundMedlem(kundAnvändarnamn, kundLösenord);
        if (användareKund == null) {
            System.out.println("Du är inte kund. Adjö!");
            System.exit(1);
        }

        //Testar eget funktionsgränssnitt & Lambda.
        Inloggad kundMeddelande = () -> System.out.println("Välkommen " + användareKund.getNamn() + "!");
        kundMeddelande.KundInloggad();

        while (true) {
            //Sortera skor innan utskrift? Fråga om Märke? Färg? Pris? Beroende på svar så filtrera.
            List<Sko> allaSkor = r.presenteraProdukter(); //Hämtar alla skor.
            allaSkor.forEach(s -> System.out.println("Skriv " + s.getID() + " om du vill ha följande sko:\nFärg: " +
                    s.getFärg() + ", Pris: " + s.getPris() + " kr, Märke: " + s.getMärke() + ", Storlek: " +
                    s.getStorlek() + "\n")); //Lambda. Göra om med stream?

        /*        String skoBeskrivningar = allaSkor.stream()
                        .map(sko -> "Skriv " + sko.getID() + " om du vill ha följande sko:\nFärg: " +
                                sko.getFärg() + ", Pris: " + sko.getPris() + " kr, Märke: " + sko.getMärke() + ", Storlek: " +
                                sko.getStorlek())
                        .collect(Collectors.joining("\n")); // Använder "\n" som avgränsare mellan varje sko
                System.out.println(skoBeskrivningar); */


            int kundValAvSko = Integer.parseInt(sc.nextLine()); //Fånga fel här måste med catch XD

            if (nummerPåBeställning == 0) {
                List<Beställning> allaBeställningar = r.hittaBeställning();
                //Taskig försök till lambda nedan? XD Kunde använt .size()?
                //Typomvandlning nedan.
                nummerPåBeställning = (int) allaBeställningar.stream().count() + 1;
            }

            r.läggTillSko(användareKund.getID(), nummerPåBeställning, kundValAvSko);
            System.out.println("Skon är tillagd!"); //Ändra denna till lambdas XD


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
