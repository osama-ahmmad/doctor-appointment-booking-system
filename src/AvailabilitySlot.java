import java.time.LocalDate;
import java.time.LocalTime;

public class AvailabilitySlot {
    private final String slotId;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private boolean available;

    public AvailabilitySlot(String slotId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.slotId = slotId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.available = true;
    }

    public String getSlotId() {
        return slotId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return String.format("[%s] Date: %s | Time: %s - %s | Status: %s",
                slotId, date, startTime, endTime, available ? "AVAILABLE" : "BOOKED");
    }
}
