import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class TennisStudyTracker {

    // Representa un día de entrenamiento / estudio
    static class DayLog {
        String date; // por ejemplo: "2025-11-26" o "Wednesday"
        double tennisHours;
        int matchesPlayed;
        double studyHours;
        int assignmentsDone;

        public DayLog(String date, double tennisHours, int matchesPlayed,
                double studyHours, int assignmentsDone) {
            this.date = date;
            this.tennisHours = tennisHours;
            this.matchesPlayed = matchesPlayed;
            this.studyHours = studyHours;
            this.assignmentsDone = assignmentsDone;
        }

        public String toCSV() {
            return String.format("%s,%.2f,%d,%.2f,%d", date, tennisHours, matchesPlayed, studyHours, assignmentsDone);
        }

        public static DayLog fromCSV(String line) {
            String[] parts = line.split(",");
            if (parts.length != 5)
                return null;
            return new DayLog(parts[0], Double.parseDouble(parts[1]), Integer.parseInt(parts[2]),
                    Double.parseDouble(parts[3]), Integer.parseInt(parts[4]));
        }
    }

    // Lista donde guardamos todos los días
    private static ArrayList<DayLog> logs = new ArrayList<>();
    private static final String FILE_NAME = "tennis_study_log.csv";
    private static final double WEEKLY_STUDY_GOAL = 35.0;

    public static void main(String[] args) {
        loadData();
        Scanner in = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Tennis & Study Tracker ===");

        while (running) {
            printMenu();
            System.out.print("Choose an option: ");
            String choice = in.nextLine().trim();

            switch (choice) {
                case "1":
                    addDayLog(in);
                    break;
                case "2":
                    showSummary();
                    break;
                case "3":
                    System.out.println("Exiting... See you next practice.");
                    saveData();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println(); // línea en blanco para que se vea más limpio
        }

        in.close();
    }

    // Muestra el menú principal
    private static void printMenu() {
        System.out.println("---------------");
        System.out.println("1) Add day log");
        System.out.println("2) Show overall summary");
        System.out.println("3) Exit");
    }

    // Pide los datos de un día y lo guarda en la lista
    private static void addDayLog(Scanner in) {
        System.out.print("Date (e.g. 2025-11-26 or 'Wednesday'): ");
        String date = in.nextLine().trim();

        System.out.print("Tennis hours: ");
        double tennisHours = Double.parseDouble(in.nextLine().trim());

        System.out.print("Matches played: ");
        int matchesPlayed = Integer.parseInt(in.nextLine().trim());

        System.out.print("Study hours: ");
        double studyHours = Double.parseDouble(in.nextLine().trim());

        System.out.print("Assignments completed: ");
        int assignmentsDone = Integer.parseInt(in.nextLine().trim());

        DayLog log = new DayLog(date, tennisHours, matchesPlayed, studyHours, assignmentsDone);
        logs.add(log);
        saveData();

        System.out.println("Day added successfully.");
    }

    // Muestra la suma total de todo lo registrado
    private static void showSummary() {
        if (logs.isEmpty()) {
            System.out.println("No data yet. Add at least one day first.");
            return;
        }

        double totalTennisHours = 0;
        int totalMatches = 0;
        double totalStudyHours = 0;
        int totalAssignments = 0;

        for (DayLog log : logs) {
            totalTennisHours += log.tennisHours;
            totalMatches += log.matchesPlayed;
            totalStudyHours += log.studyHours;
            totalAssignments += log.assignmentsDone;
        }

        System.out.println("=== Overall Summary ===");
        System.out.println("Days tracked: " + logs.size());
        System.out.println("Total tennis hours: " + totalTennisHours);
        System.out.println("Total matches played: " + totalMatches);
        System.out.println("Total study hours: " + totalStudyHours);
        System.out.println("Total assignments completed: " + totalAssignments);

        System.out.println("--- Analytics ---");
        System.out.printf("Weekly Study Goal: %.1f hours%n", WEEKLY_STUDY_GOAL);
        double progressPercent = (totalStudyHours / WEEKLY_STUDY_GOAL) * 100;
        System.out.printf("Current Progress: %.1f hours (%.1f%%)%n", totalStudyHours, progressPercent);

        if (totalStudyHours < WEEKLY_STUDY_GOAL) {
            System.out.printf("Remaining: %.1f hours to reach goal%n", WEEKLY_STUDY_GOAL - totalStudyHours);
        } else {
            System.out.println("Goal reached! Great job!");
        }
    }

    private static void saveData() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (DayLog log : logs) {
                out.println(log.toCSV());
            }
            System.out.println("Data saved to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static void loadData() {
        File file = new File(FILE_NAME);
        if (!file.exists())
            return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                DayLog log = DayLog.fromCSV(line);
                if (log != null)
                    logs.add(log);
            }
            System.out.println("Loaded " + logs.size() + " days from " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}
