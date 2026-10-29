import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private final String doctorId;
    private final String name;
    private final String specialty;
    private final List<AvailabilitySlot> availabilitySlots;
    private final List<Appointment> appointments;

    public Doctor(String doctorId, String name, String specialty) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialty = specialty;
        this.availabilitySlots = new ArrayList<>();
        this.appointments = new ArrayList<>();
    }

    public void addSlot(AvailabilitySlot slot) {
        this.availabilitySlots.add(slot);
    }

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
    }

    public List<AvailabilitySlot> getAvailability() {
        List<AvailabilitySlot> openSlots = new ArrayList<>();
        for (AvailabilitySlot slot : availabilitySlots) {
            if (slot.isAvailable()) {
                openSlots.add(slot);
            }
        }
        return openSlots;
    }

    public void acceptAppointment(String appointmentId) {
        Appointment appt = findAppointment(appointmentId);
        if (appt == null) {
            throw new IllegalArgumentException("Appointment with ID " + appointmentId + " not found.");
        }
        if (appt.getStatus() != AppointmentStatus.PENDING) {
            throw new IllegalStateException("Only PENDING appointments can be accepted. Current: " + appt.getStatus());
        }
        appt.accept();
    }

    public void declineAppointment(String appointmentId) {
        Appointment appt = findAppointment(appointmentId);
        if (appt == null) {
            throw new IllegalArgumentException("Appointment with ID " + appointmentId + " not found.");
        }
        if (appt.getStatus() != AppointmentStatus.PENDING) {
            throw new IllegalStateException("Only PENDING appointments can be declined. Current: " + appt.getStatus());
        }
        appt.decline();
    }

    public Appointment findAppointment(String appointmentId) {
        for (Appointment a : appointments) {
            if (a.getAppointmentId().equalsIgnoreCase(appointmentId)) {
                return a;
            }
        }
        return null;
    }

    public AvailabilitySlot findSlot(String slotId) {
        for (AvailabilitySlot s : availabilitySlots) {
            if (s.getSlotId().equalsIgnoreCase(slotId)) {
                return s;
            }
        }
        return null;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public List<AvailabilitySlot> getAllSlots() {
        return availabilitySlots;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    @Override
    public String toString() {
        return String.format("Dr. %s (%s) [ID: %s]", name, specialty, doctorId);
    }
}
