import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Appointment {
    private final String appointmentId;
    private final String patientId;
    private final String doctorId;
    private final AvailabilitySlot slot;
    private AppointmentStatus status;
    private LocalDateTime createdAt;

    public Appointment(String appointmentId, String patientId, String doctorId, AvailabilitySlot slot) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.slot = slot;
    }

    public void submit() {
        this.status = AppointmentStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public void accept() {
        this.status = AppointmentStatus.ACCEPTED;
    }

    public void decline() {
        this.status = AppointmentStatus.DECLINED;
        if (slot != null) {
            slot.setAvailable(true);
        }
    }

    public boolean isUpcoming() {
        if (slot == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        if (slot.getDate().isAfter(today)) {
            return true;
        }
        if (slot.getDate().isEqual(today)) {
            return slot.getEndTime().isAfter(now);
        }
        return false;
    }

    public boolean countsTowardLimit() {
        return isUpcoming() && (status == AppointmentStatus.PENDING || status == AppointmentStatus.ACCEPTED);
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public AvailabilitySlot getSlot() {
        return slot;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return String.format("[%s] Patient: %s | Doctor: %s | Slot: (%s %s-%s) | Status: %s",
                appointmentId, patientId, doctorId,
                slot.getDate(), slot.getStartTime(), slot.getEndTime(), status);
    }
}
