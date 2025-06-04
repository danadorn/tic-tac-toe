import java.util.*;

public class Main {

    static int floor, seat, opt;
    static String[][] morningHall, afternoonHall, nightHall;
    static Scanner input = new Scanner(System.in);
    static List<String> history = new ArrayList<>();

    static void displayLogo() {
        System.out.println("Your Booking System\n");
    }

    static int inputHandler(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
    }

    static void quitHandler() {
        System.out.println("Press any key to continue...");
        input.nextLine();
    }

    static void setUpHall() {
        System.out.println("=== Hall SetUp ===");
        floor = inputHandler("| SetUp floor: ");
        seat = inputHandler("| SetUp seat:  ");
        morningHall = new String[floor][seat];
        afternoonHall = new String[floor][seat];
        nightHall = new String[floor][seat];
        for (int i = 0; i < floor; i++)
            for (int j = 0; j < seat; j++) {
                morningHall[i][j] = "AV";
                afternoonHall[i][j] = "AV";
                nightHall[i][j] = "AV";
            }
        System.out.println("Hall setup successfully!");
        System.out.println("=".repeat(20));
        System.out.println();
    }

    static void displayBooking(String[][] hall) {
        for (int i = 0; i < floor; i++) {
            char floorLetter = (char) ('A' + i);
            for (int j = 0; j < seat; j++) {
                String displayValue = hall[i][j].equals("AV") ? "AV" : "UN";
                System.out.print("|" + floorLetter + (j + 1) + "::" + displayValue + "|     ");
            }
            System.out.println();
        }
    }

    static void displayMenu() {
        System.out.println("=== Booking Menu ===");
        System.out.println("|1. Booking       |");
        System.out.println("|2. Hall          |");
        System.out.println("|3. Showtime      |");
        System.out.println("|4. Reboot Hall   |");
        System.out.println("|5. History       |");
        System.out.println("|0. Exit          |");
        System.out.println("=".repeat(20));
        System.out.print("  Choose option (1-5/0): ");
    }

    static void bookingHall() {
        System.out.println("Start booking process...");
        System.out.println("=".repeat(40));
        System.out.println(". 1) Morning Hall (10:00AM - 12:30PM)");
        System.out.println(". 2) Afternoon Hall (3:00PM - 5:30PM)");
        System.out.println(". 3) Night Hall (7:00PM - 9:30PM)");
        System.out.println("=".repeat(40));
        System.out.print("  Choose option (1-3): ");

        int choice = Integer.parseInt(input.nextLine());
        String[][] selectedHall = switch (choice) {
            case 1 -> morningHall;
            case 2 -> afternoonHall;
            case 3 -> nightHall;
            default -> null;
        };

        if (selectedHall == null) {
            System.out.println("Invalid Input.");
            return;
        }

        displayBooking(selectedHall);
        System.out.print("Enter seat(s) to book (e.g., A1,A2): ");
        String[] seats = input.nextLine().toUpperCase().split(",");

        System.out.print("Enter booking name: ");
        String name = input.nextLine();
        String dateTime = new Date().toString();

        for (String seatCode : seats) {
            if (seatCode.length() < 2) continue;

            char floorChar = seatCode.charAt(0);
            int row = floorChar - 'A';
            int col;

            try {
                col = Integer.parseInt(seatCode.substring(1)) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid seat: " + seatCode);
                continue;
            }

            if (row >= 0 && row < floor && col >= 0 && col < seat) {
                if (selectedHall[row][col].equals("AV")) {
                    selectedHall[row][col] = name;
                    history.add("#" + name + " booked seat " + seatCode + " at " + dateTime);
                    System.out.println("Seat " + seatCode + " booked successfully.");
                } else {
                    System.out.println("Seat " + seatCode + " already booked.");
                }
            } else {
                System.out.println("Seat " + seatCode + " does not exist.");
            }
        }

        quitHandler();
    }

    static void displayHall() {
        System.out.println("# Morning Hall");
        displayBooking(morningHall);
        System.out.println("=".repeat(100));

        System.out.println("# Afternoon Hall");
        displayBooking(afternoonHall);
        System.out.println("=".repeat(100));

        System.out.println("# Night Hall");
        displayBooking(nightHall);
        System.out.println("=".repeat(100));
        System.out.println();
    }

    static void showTime() {
        System.out.println("=== Show Time ====");
        System.out.println("1). #Time: 9:30AM - 12:00PM, Lost in Starlight");
        System.out.println("2). #Time: 12:30PM - 2:00PM, Cargo");
        System.out.println("3). #Time: 2:30PM - 4:00PM, Jackpot!");
        System.out.println("4). #Time: 4:30PM - 6:00PM, The Black Phone");
        System.out.println("5). #Time: 6:30PM - 8:00PM, Despicable Me 3");
        System.out.println("=".repeat(70));
        quitHandler();
    }

    static void rebootHall() {
        for (int i = 0; i < floor; i++)
            for (int j = 0; j < seat; j++) {
                morningHall[i][j] = "AV";
                afternoonHall[i][j] = "AV";
                nightHall[i][j] = "AV";
            }
        history.clear();
        System.out.println("All bookings reset. Halls are now available.");
        quitHandler();
    }

    static void showHistory() {
        if (history.isEmpty()) {
            System.out.println("No booking history available.");
        } else {
            System.out.println("=== Booking History ===");
            for (String record : history) {
                System.out.println(record);
            }
        }
        System.out.println("=".repeat(50));
        quitHandler();
    }

    public static void main(String[] args) {
        displayLogo();
        setUpHall();

        do {
            displayMenu();
            try {
                opt = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                opt = -1;
            }
            System.out.println();

            switch (opt) {
                case 1 -> bookingHall();
                case 2 -> displayHall();
                case 3 -> showTime();
                case 4 -> rebootHall();
                case 5 -> showHistory();
                case 0 -> {
                    System.out.println("Exiting the application...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid input.");
            }

        } while (opt != 0);
    }
}