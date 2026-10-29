# Sequence Diagram - Doctor Appointment Booking System

```mermaid
sequenceDiagram
    autonumber
    actor Patient
    actor Doctor
    participant Slot as AvailabilitySlot
    participant Appt as Appointment

    %% R1: View Availability
    note over Patient, Slot: R1: View Doctor Availability
    Patient->>Patient: viewAvailability(doctor)
    Patient->>Slot: getAvailability()
    activate Slot
    Slot-->>Patient: return available slots
    deactivate Slot

    %% R2 & R4: Book Appointment with N Limit Check
    note over Patient, Appt: R2 & R4: Book Appointment (N Limit Check)
    Patient->>Patient: bookAppointment(doctor, slot, appointmentId)
    activate Appt

    Patient->>Patient: countUpcomingAppointments()
    Patient->>Appt: countsTowardLimit()
    note over Patient, Appt: Count future PENDING and ACCEPTED appointments

    alt Upcoming Count < N
        Patient->>Slot: isAvailable()
        activate Slot
        Slot-->>Patient: true
        deactivate Slot
        Patient->>Appt: submit() [status = PENDING]
        Patient->>Slot: setAvailable(false)
        Appt-->>Patient: Appointment Submitted (PENDING)
    else Upcoming Count >= N
        Appt-->>Patient: Booking Failed (Maximum N active appointments reached)
    end
    deactivate Appt

    %% R3: Doctor Accept / Decline
    note over Doctor, Appt: R3: Doctor Accept or Decline

    alt Doctor Accepts
        Doctor->>Appt: acceptAppointment(appointmentId)
        activate Appt
        Appt->>Appt: accept() [status = ACCEPTED]
        Appt-->>Doctor: Appointment Accepted
        deactivate Appt
    else Doctor Declines
        Doctor->>Appt: declineAppointment(appointmentId)
        activate Appt
        Appt->>Appt: decline() [status = DECLINED]
        Appt-->>Doctor: Appointment Declined
        deactivate Appt
    end