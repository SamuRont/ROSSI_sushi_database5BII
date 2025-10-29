import java.sql.*;

public class Database {
    //connetti
    //query
    private Connection connection;

    public Database() throws SQLException {
        String url = "jdbc:sqlite:database/sushi.db";
        connection = DriverManager.getConnection(url);
        System.out.println("Connected to database");
    }


    public String selectAll() {
        String result = "";

        //Controlla connessione al database
        try {
            if(connection == null || !connection.isValid(5)){
                System.err.println("Errore di connessione al database");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Errore di connessione al database");
            return null;
        }

        String query = "SELECT * FROM menu";


        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet piatti = statement.executeQuery();
            while (piatti.next()) {
                result += piatti.getString("id") + "\t";
                result += piatti.getString("piatto") + "\t";
                result += piatti.getString("prezzo") + "\t";
                result += piatti.getString("quantita") + "\n";
            }


        } catch (SQLException e) {
            System.err.println("Errore di query: " + e.getMessage());
            return null;
        }


        return result;
    }

    public boolean insert(String nomePiatto, float prezzo, int quantita) {

        //Controlla connessione al database
        try {
            if(connection == null || !connection.isValid(5)){
                System.err.println("Errore di connessione al database");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Errore di connessione al database");
            return false;
        }

        String query = "INSERT INTO menu(piatto, prezzo, quantita) VALUES (?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, nomePiatto);
            statement.setFloat(2, prezzo);
            statement.setInt(3, quantita);

            statement.executeUpdate();


        } catch (SQLException e) {
            System.err.println("Errore di query: " + e.getMessage());
            return false;
        }


        return true;
    }
}
