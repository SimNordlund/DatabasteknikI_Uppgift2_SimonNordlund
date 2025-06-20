package Databasklasser;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repositorium {

    private final Properties inställningar = new Properties();

    //Konstruktor. Laddar properties när en instans skapas upp av Repositorium.
    public Repositorium() throws IOException{
        inställningar.load(new FileInputStream("src/inställningar.properties"));
    }

    //Skapar anslutning till databas
    public Connection anslutTillDatabas() throws SQLException {
        Connection c = DriverManager.getConnection(
                inställningar.getProperty("connectionString"),
                inställningar.getProperty("namn"),
                inställningar.getProperty("lösenord"));
        return c;
    }

    //Skapar upp kund objekt, kollar om användare medlem.
    public Kund ärKundMedlem(String användarnamn, String lösenord){

        //Öppnar anslutning till databasen.
        try (Connection c = anslutTillDatabas();

             //Skapar PreparedStatement. Pga ska ta in två parametrar. Undvika SQL-Injektion.
             PreparedStatement prepStatement = c.prepareStatement
                     ("select ID, Namn, Ort from Kund where Användarnamn = ? AND Lösenord = ?")

        ) {
            prepStatement.setString(1, användarnamn);
            prepStatement.setString(2, lösenord);
            ResultSet rs = prepStatement.executeQuery(); //Utför query/frågan. Lagrar resultatet.

            //Om det finns rad körs if-sats, annars returneras null.
            if (rs.next()) {
                return new Kund(rs.getInt("ID"), rs.getString("Namn"), rs.getString("Ort"),
                        lösenord, användarnamn); //Returnerar ett Kund-objekt.
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
        return null;
    }

    //Hämtar samtliga produkter. Sparar ner i lista av objekt.
    public List<Sko> presenteraProdukter() {

        try (Connection c = anslutTillDatabas();

             Statement statement = c.createStatement(); //Statementobjekt. För att köra SQL-frågan nedan.
             //Resultset lagrar resultatet.
             ResultSet rs = statement.executeQuery("select ID, Färg, Pris, Märke, Storlek, Namn from Sko")
        ) {
            List<Sko> allaSkor = new ArrayList<>(); //Lista för att lagra skor.

            //Loopar igenom raderna. Sparar ner i listan allaSkor.
            while (rs.next()) {
                Sko tempSko = new Sko(rs.getInt("ID"), rs.getString("Färg"), rs.getDouble("Pris"),
                        rs.getString("Märke"), rs.getInt("Storlek"), rs.getString("Namn"));
                allaSkor.add(tempSko); //Lägger till sko-objekt.
            }
            return allaSkor;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
        return null;
    }

    //Hämtar samtliga beställningar. Sparar i lista av objekt.
    public List<Beställning> hämtaBeställningar() {
        try (Connection c = anslutTillDatabas();

             Statement statement = c.createStatement(); //Statementobjekt skapas.
             ResultSet rs = statement.executeQuery("select ID, Datum, KundID from Beställning") //Lagrar resultat i rs.
        ) {
            List<Beställning> allaBeställningar = new ArrayList<>();

            while (rs.next()) {
                Beställning tempBeställning = new Beställning(rs.getInt("ID"), rs.getDate("Datum").toLocalDate(),
                        rs.getInt("KundID"));
                allaBeställningar.add(tempBeställning);
            }
            return allaBeställningar;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
        return null;
    }

    //Lägger till skor i varukorg med hjälp av Stored Procedure.
    public void läggTillSko(int kundID, int beställningID, int skoID) {

        try (Connection c = anslutTillDatabas(); //Öppnar db anslutning

             //Förbereder anrop till AddToCart/SP. Vill ha tre parametrar.
             CallableStatement callStatement = c.prepareCall("call AddToCart(?, ?, ?)")
        ) {
            //Sätter parametrar.
            callStatement.setInt(1, kundID);
            callStatement.setInt(2, beställningID);
            callStatement.setInt(3, skoID);
            callStatement.executeQuery(); //Utför query/frågan

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
    }

    public List<Kund> hämtaKunder() { //Används till VG.

        try (Connection c = anslutTillDatabas();

             Statement statement = c.createStatement();
             ResultSet rs = statement.executeQuery("select ID, Namn, Ort from Kund")
        ) {
            List<Kund> allaKunder = new ArrayList<>();

            while (rs.next()) {
                Kund tempKund = new Kund(rs.getInt("ID"), rs.getString("Namn"), rs.getString("Ort"));
                allaKunder.add(tempKund);
            }
            return allaKunder;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getErrorCode());
        }
        return null;
    }

}
