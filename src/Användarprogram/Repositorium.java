package Användarprogram;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repositorium {

    private final Properties inställningar = new Properties();

    //Konstruktor. Laddar properties när en instans skapas upp av Repositorium.
    public Repositorium() throws IOException, SQLException {
        inställningar.load(new FileInputStream("src/inställningar.properties"));
    }

    //Hämtar anslutning till databas
    public Connection anslutTillDatabas() throws SQLException {
        Connection c = DriverManager.getConnection(
                inställningar.getProperty("connectionString"),
                inställningar.getProperty("namn"),
                inställningar.getProperty("lösenord"));
        return c;
    }

    //Skapar upp kund objekt, kollar om användare medlem.
    public Kund ärKundMedlem(String användarnamn, String lösenord) throws IOException {

        //Connectar till databasen med hjälp av uppgifter i Properties.
        try (Connection c = anslutTillDatabas();

             //Skapar PreparedStatement. Pga ska ta in två parametrar.
             PreparedStatement prepStatement = c.prepareStatement
                     ("select ID, Namn, Ort from Kund where Användarnamn = ? AND Lösenord = ?")

        ) {
            prepStatement.setString(1, användarnamn);
            prepStatement.setString(2, lösenord);
            ResultSet rs = prepStatement.executeQuery(); //Utför query

            //Om det finns rad körs if-sats, annars returneras null.
            if (rs.next()) {
                Kund tempKund = new Kund(rs.getInt("ID"), rs.getString("Namn"), rs.getString("Ort"),
                        lösenord, användarnamn);
                return tempKund;
            }

        } catch (SQLException e) {
            System.out.println(e.getErrorCode()); //Denna? Se bild OneOne
        }
        return null;
    }

    public List<Sko> presenteraProdukter() {

        try (Connection c = anslutTillDatabas();

             Statement statement = c.createStatement(); //Statement
             //Genom statement kan man exevera SQL, se nedan. Resultset hämtar en rad i taget.
             ResultSet rs = statement.executeQuery("select ID, Färg, Pris, Märke, Storlek from Sko")
        ) {
            List<Sko> allaSkor = new ArrayList<>();

            //Loopar igenom raderna. Sparar ner i listan allaSkor.
            while (rs.next()) {
                Sko tempSko = new Sko(rs.getInt("ID"), rs.getString("Färg"), rs.getDouble("Pris"),
                        rs.getString("Märke"), rs.getInt("Storlek"));
                allaSkor.add(tempSko);
            }
            System.out.println("Här kommer alla produkter! Välj ett alternativ.\n");
            return allaSkor;

        } catch (SQLException e) {
            System.out.println(e.getErrorCode()); //Denna?
        }
        return null;
    }

    public List<Beställning> hittaBeställning() {
        try (Connection c = anslutTillDatabas();

             Statement statement = c.createStatement();
             //select count(*) from Beställning sen +1?
             ResultSet rs = statement.executeQuery("select ID, Datum, KundID from Beställning")
        ) {
            List<Beställning> allaBeställningar = new ArrayList<>();

            while (rs.next()) { //Behövs while loop?
                Beställning tempBeställning = new Beställning(rs.getInt("ID"), rs.getDate("Datum").toLocalDate(),
                        rs.getInt("KundID"));
                allaBeställningar.add(tempBeställning);
            }
            return allaBeställningar;

        } catch (SQLException e) {
            System.out.println(e.getErrorCode()); //Denna?
        }
        return null; //Om fel, returnerar 0
    }

    public void läggTillSko(int kundID, int beställningID, int skoID) {

        try (Connection c = anslutTillDatabas();
             //Kallar på SP.
             CallableStatement callStatement = c.prepareCall("call AddToCart(?, ?, ?)")
        ) {
            callStatement.setInt(1, kundID);
            callStatement.setInt(2, beställningID);
            callStatement.setInt(3, skoID);
            callStatement.executeQuery(); //Bara execute?


        } catch (SQLException e) {
            System.out.println(e.getErrorCode()); //Denna?
        }
    }
}
