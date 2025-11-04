import java.sql.*;

public class Database {
    private Connection connection;

    public Database() throws SQLException {
        String url = "jdbc:sqlite:database/sushi.db";
        connection = DriverManager.getConnection(url);
        System.out.println("Connesso al database");
    }

    public String selectAll() {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT id, piatto, prezzo, quantita FROM menu ORDER BY id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            sb.append("ID | Piatto | Prezzo | Quantità\n");
            sb.append("-------------------------------\n");

            while (rs.next()) {
                int id = rs.getInt("id");
                String piatto = rs.getString("piatto");
                double prezzo = rs.getDouble("prezzo");
                int quantita = rs.getInt("quantita");

                sb.append("ID: ").append(id)
                        .append(" | Piatto: ").append(piatto)
                        .append(" | Prezzo: ").append(prezzo)
                        .append(" | Quantità: ").append(quantita)
                        .append("\n");
            }
        } catch (SQLException e) {
            return "Errore nel selectAll: " + e.getMessage();
        }
        return sb.toString();
    }


    public boolean insert(String piatto, double prezzo, int quantita) {
        String sql = "INSERT INTO menu(piatto, prezzo, quantita) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, piatto);
            pstmt.setDouble(2, prezzo);
            pstmt.setInt(3, quantita);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Errore di insert: " + e.getMessage());
            return false;
        }
    }

    public boolean update(int id, String piatto, double prezzo, int quantita) {
        String sql = "UPDATE menu SET piatto = ?, prezzo = ?, quantita = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, piatto);
            pstmt.setDouble(2, prezzo);
            pstmt.setInt(3, quantita);
            pstmt.setInt(4, id);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.err.println("Errore di update: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM menu WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.err.println("Errore di delete: " + e.getMessage());
            return false;
        }
    }

    public boolean exists(int id) {
        String sql = "SELECT COUNT(*) AS c FROM menu WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("c") > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore in exists: " + e.getMessage());
        }
        return false;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (SQLException e) {
            // ignora
        }
    }
}
