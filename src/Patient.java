import java.util.ArrayList;
import java.util.List;

public class Patient {
    public static final int MAX_UPCOMING_APPOINTMENTS = 3;

    private final String patientId;
    private final String name;
    private final String contactInfo;
    private final List<Appointment> appointments;

    public Patient(String patientId, String name, String contactInfo) {
        this.patientId = patientId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.appointments = new ArrayList<>();
    }

    public List<AvailabilitySlot> viewAvailability(Doctor doctor) {
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null.");
        }
        return doctor.getAvailability();
    }

    public Appointment bookAppointment(Doctor doctor, AvailabilitySlot slot, String appointmentId) {
        if (doctor == null || slot == null) {
            throw new IllegalArgumentException("Doctor and Slot must not be null.");
        }

        int upcomingCount = countUpcomingAppointments();
        if (upcomingCount >= MAX_UPCOMING_APPOINTMENTS) {
            throw new IllegalStateException(String.format(
                    "Booking rejected: Patient '%s' already has %d upcoming appointment(s). The maximum allowed is %d (R4).",
                    name, upcomingCount, MAX_UPCOMING_APPOINTMENTS
            ));
        }

        if (!slot.isAvailable()) {
            throw new IllegalStateException(String.format(
                    "Booking rejected: Slot '%s' is no longer available.", slot.getSlotId()
            ));
        }

        Appointment appointment = new Appointment(appointmentId, this.patientId, doctor.getDoctorId(), slot);
        appointment.submit();
        slot.setAvailable(false);

        this.appointments.add(appointment);
        doctor.addAppointment(appointment);

        return appointment;
    }

    public int countUpcomingAppointments() {
        int count = 0;
        for (Appointment a : appointments) {
            if (a.countsTowardLimit()) {
                count++;
            }
        }
        return count;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %s, Phone: %s)", name, patientId, contactInfo);
    }
}
