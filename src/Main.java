import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0 && (args[0].equalsIgnoreCase("--interactive") || args[0].equalsIgnoreCase("-i"))) {
            runInteractiveMode();
            return;
        }

        runAutomatedVerification();

        System.out.println("==========================================================");
        System.out.println("Automated verification completed successfully!");
        System.out.println("Tip: Run with 'java src/Main.java -i' for interactive mode.");
        System.out.println("==========================================================");
    }

    public static void runAutomatedVerification() {
        System.out.println("==========================================================");
        System.out.println("   DOCTOR APPOINTMENT BOOKING SYSTEM - VERIFICATION DEMO  ");
        System.out.println("==========================================================\n");

        Doctor drAbdelmaqsoud = new Doctor("DOC-01", "Mohamed Abdelmaqsoud", "Cardiology");
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate dayAfterTomorrow = LocalDate.now().plusDays(2);

        AvailabilitySlot slot1 = new AvailabilitySlot("SLOT-1", tomorrow, LocalTime.of(9, 0), LocalTime.of(10, 0));
        AvailabilitySlot slot2 = new AvailabilitySlot("SLOT-2", tomorrow, LocalTime.of(10, 30), LocalTime.of(11, 30));
        AvailabilitySlot slot3 = new AvailabilitySlot("SLOT-3", dayAfterTomorrow, LocalTime.of(14, 0), LocalTime.of(15, 0));
        AvailabilitySlot slot4 = new AvailabilitySlot("SLOT-4", dayAfterTomorrow, LocalTime.of(15, 30), LocalTime.of(16, 30));

        drAbdelmaqsoud.addSlot(slot1);
        drAbdelmaqsoud.addSlot(slot2);
        drAbdelmaqsoud.addSlot(slot3);
        drAbdelmaqsoud.addSlot(slot4);

        Patient patientOsama = new Patient("PAT-01", "Osama Ahmed", "+20 100 123 4567");

        System.out.println("1. [R1] Patient views Doctor's availability (UC1):");
        System.out.println("----------------------------------------------------------");
        List<AvailabilitySlot> availableSlots = patientOsama.viewAvailability(drAbdelmaqsoud);
        for (AvailabilitySlot s : availableSlots) {
            System.out.println("   " + s);
        }
        System.out.println("   -> Available slots displayed successfully.\n");

        System.out.println("2. [R2 & R4] Patient books appointments up to limit N = 3 (UC2):");
        System.out.println("----------------------------------------------------------");

        Appointment appt1 = patientOsama.bookAppointment(drAbdelmaqsoud, slot1, "APT-101");
        System.out.println("   Booked 1: " + appt1);
        System.out.println("   Patient upcoming count: " + patientOsama.countUpcomingAppointments() + " / 3");

        Appointment appt2 = patientOsama.bookAppointment(drAbdelmaqsoud, slot2, "APT-102");
        System.out.println("   Booked 2: " + appt2);
        System.out.println("   Patient upcoming count: " + patientOsama.countUpcomingAppointments() + " / 3");

        Appointment appt3 = patientOsama.bookAppointment(drAbdelmaqsoud, slot3, "APT-103");
        System.out.println("   Booked 3: " + appt3);
        System.out.println("   Patient upcoming count: " + patientOsama.countUpcomingAppointments() + " / 3\n");

        System.out.println("3. [R4] Testing N = 3 Constraint: Attempting to book a 4th appointment:");
        System.out.println("----------------------------------------------------------");
        try {
            patientOsama.bookAppointment(drAbdelmaqsoud, slot4, "APT-104");
            System.err.println("   ERROR: The booking should have been rejected!");
        } catch (IllegalStateException e) {
            System.out.println("   [SUCCESSFULLY BLOCKED] Expected exception caught:");
            System.out.println("   \"" + e.getMessage() + "\"\n");
        }

        System.out.println("4. [R3] Doctor accepts an appointment request (UC3):");
        System.out.println("----------------------------------------------------------");
        System.out.println("   Accepting APT-101 by Dr. " + drAbdelmaqsoud.getName() + "...");
        drAbdelmaqsoud.acceptAppointment("APT-101");
        System.out.println("   APT-101 Status: " + appt1.getStatus());
        System.out.println("   Patient upcoming count: " + patientOsama.countUpcomingAppointments() + " / 3");
        System.out.println("   (Accepted appointments still count toward the limit)\n");

        System.out.println("5. [R3 & R4] Doctor declines an appointment request (UC4):");
        System.out.println("----------------------------------------------------------");
        System.out.println("   Declining APT-102 by Dr. " + drAbdelmaqsoud.getName() + "...");
        drAbdelmaqsoud.declineAppointment("APT-102");
        System.out.println("   APT-102 Status: " + appt2.getStatus());
        System.out.println("   Slot 2 available again: " + slot2.isAvailable());
        System.out.println("   Patient upcoming count: " + patientOsama.countUpcomingAppointments() + " / 3");
        System.out.println("   (Declined appointments DO NOT count toward the limit)\n");

        System.out.println("6. [R4] Verification: Patient can now book again since upcoming count < 3:");
        System.out.println("----------------------------------------------------------");
        Appointment appt4 = patientOsama.bookAppointment(drAbdelmaqsoud, slot4, "APT-104");
        System.out.println("   Booked 4: " + appt4);
        System.out.println("   Patient upcoming count: " + patientOsama.countUpcomingAppointments() + " / 3\n");

        System.out.println("7. Testing Double Booking Protection (R2):");
        System.out.println("----------------------------------------------------------");
        Patient patientBelal = new Patient("PAT-02", "Belal Shama", "+20 111 234 5678");
        try {
            patientBelal.bookAppointment(drAbdelmaqsoud, slot4, "APT-105");
            System.err.println("   ERROR: Double booking of slot4 should have been rejected!");
        } catch (IllegalStateException e) {
            System.out.println("   [SUCCESSFULLY BLOCKED] Expected exception caught:");
            System.out.println("   \"" + e.getMessage() + "\"\n");
        }
    }

    public static void runInteractiveMode() {
        Scanner scanner = new Scanner(System.in);
        Doctor doctor = new Doctor("DOC-02", "Ahmed Bahaa", "Neurology");
        LocalDate baseDate = LocalDate.now().plusDays(1);

        doctor.addSlot(new AvailabilitySlot("S1", baseDate, LocalTime.of(9, 0), LocalTime.of(10, 0)));
        doctor.addSlot(new AvailabilitySlot("S2", baseDate, LocalTime.of(10, 30), LocalTime.of(11, 30)));
        doctor.addSlot(new AvailabilitySlot("S3", baseDate.plusDays(1), LocalTime.of(13, 0), LocalTime.of(14, 0)));
        doctor.addSlot(new AvailabilitySlot("S4", baseDate.plusDays(1), LocalTime.of(15, 0), LocalTime.of(16, 0)));

        Patient patient = new Patient("PAT-03", "Belal Shama", "+20 122 345 6789");
        int apptCounter = 1;

        while (true) {
            System.out.println("\n==========================================");
            System.out.println("   Doctor Appointment Booking System Menu ");
            System.out.println("==========================================");
            System.out.println("1. [Patient] View Doctor Availability (R1)");
            System.out.println("2. [Patient] Book Appointment (R2, R4)");
            System.out.println("3. [Patient] View My Appointments");
            System.out.println("4. [Doctor]  View Received Appointments");
            System.out.println("5. [Doctor]  Accept Appointment Request (R3)");
            System.out.println("6. [Doctor]  Decline Appointment Request (R3)");
            System.out.println("7. Run Automated Self-Test");
            System.out.println("0. Exit");
            System.out.print("Select an option: ");

            String input = scanner.nextLine().trim();
            if (input.equals("0")) {
                System.out.println("Exiting system. Goodbye!");
                break;
            }

            try {
                switch (input) {
                    case "1": {
                        System.out.println("\n--- Available Slots for " + doctor + " ---");
                        List<AvailabilitySlot> slots = patient.viewAvailability(doctor);
                        if (slots.isEmpty()) {
                            System.out.println("No slots currently available.");
                        } else {
                            for (AvailabilitySlot s : slots) {
                                System.out.println(s);
                            }
                        }
                        break;
                    }
                    case "2": {
                        System.out.println("\n--- Available Slots ---");
                        List<AvailabilitySlot> slots = patient.viewAvailability(doctor);
                        for (AvailabilitySlot s : slots) {
                            System.out.println(s);
                        }
                        System.out.print("Enter Slot ID to book: ");
                        String slotId = scanner.nextLine().trim();
                        AvailabilitySlot slot = doctor.findSlot(slotId);
                        if (slot == null) {
                            System.out.println("Slot not found.");
                            break;
                        }
                        String apptId = "APT-" + (apptCounter++);
                        Appointment appt = patient.bookAppointment(doctor, slot, apptId);
                        System.out.println("Appointment booked successfully: " + appt);
                        System.out.println("Upcoming appointments count: " + patient.countUpcomingAppointments() + " / 3");
                        break;
                    }
                    case "3": {
                        System.out.println("\n--- Appointments for " + patient.getName() + " ---");
                        List<Appointment> list = patient.getAppointments();
                        if (list.isEmpty()) {
                            System.out.println("No appointments booked yet.");
                        } else {
                            for (Appointment a : list) {
                                System.out.println(a);
                            }
                        }
                        System.out.println("Active upcoming count (toward limit N=3): " + patient.countUpcomingAppointments());
                        break;
                    }
                    case "4": {
                        System.out.println("\n--- Appointments for " + doctor + " ---");
                        List<Appointment> list = doctor.getAppointments();
                        if (list.isEmpty()) {
                            System.out.println("No appointment requests received.");
                        } else {
                            for (Appointment a : list) {
                                System.out.println(a);
                            }
                        }
                        break;
                    }
                    case "5": {
                        System.out.print("Enter Appointment ID to ACCEPT: ");
                        String apptId = scanner.nextLine().trim();
                        doctor.acceptAppointment(apptId);
                        System.out.println("Appointment " + apptId + " ACCEPTED.");
                        break;
                    }
                    case "6": {
                        System.out.print("Enter Appointment ID to DECLINE: ");
                        String apptId = scanner.nextLine().trim();
                        doctor.declineAppointment(apptId);
                        System.out.println("Appointment " + apptId + " DECLINED.");
                        break;
                    }
                    case "7": {
                        runAutomatedVerification();
                        break;
                    }
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Operation error: " + e.getMessage());
            }
        }
    }
}
