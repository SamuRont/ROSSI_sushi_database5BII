import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database db = null;
        try {
            db = new Database();
        } catch (SQLException e) {
            System.err.println("Errore di connessione al database: " + e.getMessage());
            System.exit(-1);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci il nome del piatto da inserire: ");
        String nomePiatto = scanner.nextLine();
        System.out.println("Inserisci il prezzo del piatto da inserire: ");
        float prezzo = scanner.nextFloat();
        scanner.nextLine();
        System.out.println("Inserisci la quantità del piatto da inserire: ");
        int quantita = scanner.nextInt();
        scanner.nextLine();

        if(db.insert(nomePiatto, prezzo, quantita))
            System.out.println("Piatto inserito con successo");

        System.out.println(db.selectAll());


    }
}
