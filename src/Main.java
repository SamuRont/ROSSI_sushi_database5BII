import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database db = null;
        try {
            db = Database.getInstance();
        } catch (SQLException e) {
            System.err.println("Errore di connessione al database: " + e.getMessage());
            System.exit(-1);
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("Menu CRUD Sushi ");
            System.out.println("1) Visualizza menu (READ)");
            System.out.println("2) Inserisci nuovo piatto (CREATE)");
            System.out.println("3) Modifica piatto esistente (UPDATE)");
            System.out.println("4) Elimina piatto (DELETE)");
            System.out.println("5) Esci");
            System.out.print("Scegli un'opzione: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input non valido.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println(db.selectAll());
                    break;
                case 2:
                    System.out.print("Nome piatto: ");
                    String nome = scanner.nextLine().trim();
                    double prezzo = readDouble(scanner, "Prezzo: ");
                    int quantita = readInt(scanner, "Quantità: ");
                    if (db.insert(nome, prezzo, quantita)) {
                        System.out.println("Piatto inserito con successo");
                    } else {
                        System.out.println("Errore durante l'inserimento.");
                    }
                    break;
                case 3:
                    int idUp = readInt(scanner, "ID del piatto da modificare: ");
                    if (!db.exists(idUp)) {
                        System.out.println(" ID non trovato.");
                        break;
                    }
                    System.out.print("Nuovo nome: ");
                    String newName = scanner.nextLine().trim();
                    double newPrezzo = readDouble(scanner, "Nuovo prezzo: ");
                    int newQuantita = readInt(scanner, "Nuova quantità: ");
                    if (db.update(idUp, newName, newPrezzo, newQuantita)) {
                        System.out.println("ggiornamento riuscito");
                    } else {
                        System.out.println("Aggiornamento fallito.");
                    }
                    break;
                case 4:
                    int idDel = readInt(scanner, "ID del piatto da eliminare: ");
                    if (!db.exists(idDel)) {
                        System.out.println(" ID non trovato.");
                        break;
                    }
                    System.out.print("Sei sicuro di voler eliminare? (s/N): ");
                    String conf = scanner.nextLine().trim().toLowerCase();
                    if (conf.equals("s") || conf.equals("y")) {
                        if (db.delete(idDel)) {
                            System.out.println("Eliminazione riuscita!");
                        } else {
                            System.out.println("Eliminazione fallita.");
                        }
                    } else {
                        System.out.println("Operazione annullata.");
                    }
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Opzione non valida.");
            }
        }

        db.close();
        System.out.println(" Uscita dal programma.");
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero intero valido.");
            }
        }
    }

    private static double readDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un numero decimale valido (usa il punto come separatore).");
            }
        }
    }
}
