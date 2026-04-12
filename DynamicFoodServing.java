public class DynamicFoodServing {

    private static final double SAFE_DISTANCE = 1.0;

    public void checkDynamicDistance(Robot robot, String spotId, String spotName, java.util.Scanner sc) {
        try {
            System.out.print("Enter distance from LEFT (m): ");
            double left = sc.nextDouble();

            System.out.print("Enter distance from RIGHT (m): ");
            double right = sc.nextDouble();

            System.out.print("Enter distance from FRONT (m): ");
            double front = sc.nextDouble();

            System.out.print("Enter distance from BACK (m): ");
            double back = sc.nextDouble();

            if (left < SAFE_DISTANCE || right < SAFE_DISTANCE || front < SAFE_DISTANCE || back < SAFE_DISTANCE) {
                robot.setContactStatus("Casual Contact");
                System.out.println("Warning: close distance detected.");
                giveMovementAdvice(left, right, front, back);
                displayRobotStatus(robot, spotId, spotName);
            } else {
                robot.setContactStatus("Safe");
                System.out.println("You are safe in dynamic distancing.");
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter numeric values for distances.");
            sc.nextLine(); // Clear the invalid input
        }

    }

    public static void giveMovementAdvice(double left, double right, double front, double back) {
        if (left < SAFE_DISTANCE) {
            System.out.println("Move away from LEFT by " + getMoveDistance(left) + " m\n");
        }
        if (right < SAFE_DISTANCE) {
            System.out.println("Move away from RIGHT by " + getMoveDistance(right) + " m\n");
        }
        if (front < SAFE_DISTANCE) {
            System.out.println("Move away from FRONT by " + getMoveDistance(front) + " m\n");
        }
        if (back < SAFE_DISTANCE) {
            System.out.println("Move away from BACK by " + getMoveDistance(back) + " m\n");
        }
    }

    public static double getMoveDistance(double distance) {
        if (distance < 0.5) {
            return 1.0;
        } else {
            return 0.5;
        }
    }

    public static void displayRobotStatus(Robot robot, String spotId, String spotName) {
        System.out.println("\n--- Robot Status ---");
        System.out.println("Robot ID: " + robot.getRobotId());
        System.out.println("Robot Name: " + robot.getRobotName());
        System.out.println("Contact Status: " + robot.getContactStatus());
        System.out.println("Spot ID: " + spotId);
        System.out.println("Spot Name: " + spotName);
    }
}