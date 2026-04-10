import java.util.Random;
import java.util.Scanner;

public class StaticFoodServing extends DynamicFoodServing {

    private static final RestrictedSpots[] SPOTS = {
        new RestrictedSpots(1, "Dining Foyer",           50.0, 0.70, 5),
        new RestrictedSpots(2, "Main Dining Hall",      200.0, 0.75, 15),
        new RestrictedSpots(3, "Dining Terrace One",     80.0, 0.65, 10),
        new RestrictedSpots(4, "Dining Terrace Two",     80.0, 0.65, 10),
        new RestrictedSpots(5, "Family Dining Terrace", 120.0, 0.70, 12)
    };

    private static final Random  RNG   = new Random();
    private static final Scanner INPUT = new Scanner(System.in);

    public static void main(String[] args) {

        printBanner();

        // Replace with your own Nottingham Student ID and full name
        Robot robot = new Robot("20XXXXXXX", "Your Full Name");

        boolean continueSession = true;

        while (continueSession) {
            displaySpotMenu();

            int choice = readIntInRange("Select a spot (1-5, or 0 to exit): ", 0, 5);

            if (choice == 0) {
                System.out.println("\n[System] Session ended. Robot returning to base. Goodbye!\n");
                break;
            }

            RestrictedSpots selectedSpot = SPOTS[choice - 1];
            System.out.println("\n[System] Robot " + robot.getRobotId()
                    + " (" + robot.getRobotName() + ") attempting to enter: "
                    + selectedSpot.getSpotName());

            // Upper bound +2 so the spot can legitimately be at or over capacity
            int currentOccupancy = RNG.nextInt(selectedSpot.getMaxCapacity() + 2);

            System.out.printf("[System] Current occupancy in '%s': %d / %d%n",
                    selectedSpot.getSpotName(),
                    currentOccupancy,
                    selectedSpot.getMaxCapacity());

            if (currentOccupancy < selectedSpot.getMaxCapacity()) {
                entryPermitted(robot, selectedSpot);
            } else {
                continueSession = entryNotPermitted(robot, selectedSpot);
            }

            if (continueSession) {
                System.out.print("\n[System] Would you like to perform another action? (yes/no): ");
                String again = INPUT.next().trim().toLowerCase();
                continueSession = again.equals("yes") || again.equals("y");
            }
        }

        INPUT.close();
    }

    private static void entryPermitted(Robot robot, RestrictedSpots spot) {
        System.out.println("\n✔  ENTRY PERMITTED to " + spot.getSpotName());
        System.out.println("─".repeat(50));
        System.out.println("[System] Proceeding to dynamic distancing check...\n");

        StaticFoodServing sfs = new StaticFoodServing();
        sfs.checkDynamicDistance(robot,
                String.valueOf(spot.getSpotID()),
                spot.getSpotName());

        printDateTime();
    }

    /**
     * @return true to continue the session loop, false to end it
     */
    private static boolean entryNotPermitted(Robot robot, RestrictedSpots spot) {
        System.out.println("\n✘  ENTRY NOT PERMITTED — '"
                + spot.getSpotName() + "' is at full capacity.");
        System.out.printf("[System] Average wait time for '%s': %d minute(s).%n",
                spot.getSpotName(), spot.getPermittedAvgTime());

        System.out.print("[System] Would the robot like to wait? (yes/no): ");
        String waitChoice = INPUT.next().trim().toLowerCase();

        if (waitChoice.equals("yes") || waitChoice.equals("y")) {
            System.out.println("[System] Robot is waiting...");
            System.out.println("[System] Wait time elapsed. Robot is now permitted to enter.\n");
            entryPermitted(robot, spot);
            return true;
        }

        System.out.println("\n[System] Would the robot like to visit another spot?");
        displaySpotMenu();
        int altChoice = readIntInRange("Enter a spot number (1-5) or 0 to exit: ", 0, 5);

        if (altChoice == 0) {
            System.out.println("\n[System] Robot returning to base. Goodbye!\n");
            return false;
        }

        RestrictedSpots altSpot = SPOTS[altChoice - 1];
        System.out.println("\n[System] Redirecting robot to: " + altSpot.getSpotName());

        int altOccupancy = RNG.nextInt(altSpot.getMaxCapacity() + 2);
        System.out.printf("[System] Current occupancy in '%s': %d / %d%n",
                altSpot.getSpotName(), altOccupancy, altSpot.getMaxCapacity());

        if (altOccupancy < altSpot.getMaxCapacity()) {
            entryPermitted(robot, altSpot);
        } else {
            System.out.println("✘  Alternative spot is also full. Robot returning to standby.");
        }

        return true;
    }

    private static void displaySpotMenu() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════════╗");
        System.out.println("  ║       RESTAURANT RESTRICTED SPOTS MENU          ║");
        System.out.println("  ╠══════════════════════════════════════════════════╣");
        for (RestrictedSpots s : SPOTS) {
            System.out.printf("  ║  %-48s║%n", s.toString());
        }
        System.out.println("  ║  [0] Exit System                                ║");
        System.out.println("  ╚══════════════════════════════════════════════════╝");
    }

    /** Loops until the user enters a valid integer within [min, max]. */
    private static int readIntInRange(String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (INPUT.hasNextInt()) {
                value = INPUT.nextInt();
                if (value >= min && value <= max) {
                    return value;
                }
            } else {
                INPUT.next(); // discard invalid token
            }
            System.out.println("[Error] Please enter a number between " + min + " and " + max + ".");
        }
    }

    private static void printDateTime() {
        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        java.time.format.DateTimeFormatter fmt =
                java.time.format.DateTimeFormatter.ofPattern("dd-MMM-yyyy  HH:mm:ss");
        System.out.println("Date & Time  : " + now.format(fmt));
    }

    private static void printBanner() {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║    FOOD SERVING MULTI-ROBOTIC SYSTEM  (FSMS)         ║");
        System.out.println("║    Social Distancing Module  —  v1.0                 ║");
        System.out.println("║    COMP1029 Coursework  |  2025-2026                 ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Loaded spot information:");
        for (RestrictedSpots s : SPOTS) {
            s.displaySpotInfo();
        }
    }
}
