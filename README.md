# Doctor Appointment Booking System

## Project overview

The Doctor Appointment Booking System is intended to let patients view doctor availability and request appointments, while allowing doctors to accept or decline appointment requests.

## Requirements summary

| ID | Type | Requirement |
|---|---|---|
| R1 | Functional | A patient can view the days and times when a doctor is available. |
| R2 | Functional | A patient can book an appointment during an available time slot. |
| R3 | Functional | A doctor can accept or decline an appointment request. |
| R4 | Functional | A patient can have at most 3 upcoming appointments at any given time. |
| R5 | Non-functional | Patient medical data shall be accessible only to authorized staff. |
| R6 | Non-functional | Available appointment times shall be displayed within 2 seconds. |
| R7 | Non-functional | The application shall be usable on mobile phone screens. |

## Appointment limit

The chosen limit is **N = 3** upcoming appointments per patient.

Only appointments that are both **future** and **Pending** or **Accepted** count toward the limit. **Declined**, **Cancelled**, and **past** appointments do not count.

## Project documents

- [Requirements](requirements.md)
- [Use cases](use_cases.md)
- [Use-case diagram](use_case_diagram.md)
- [Class diagram](class_diagram.md)
- [Sequence diagram](sequence_diagram.md)

## Development stages

1. **Use Cases & Requirements** — Completed in `requirements.md`, `use_cases.md`, and `use_case_diagram.md`.
2. **Class Diagram** — Completed in `class_diagram.md`.
3. **Sequence Diagram** — Completed in `sequence_diagram.md`.
4. **Implementation** — Completed in `src/`.

## Proposed approaches for non-functional requirements

These are proposals only and are not implemented in the current repository:

- **R5 — Access control:** Use authenticated accounts, role-based authorization, and server-side checks so that medical data is returned only to authorized staff. Protect data in transit and at rest where applicable.
- **R6 — Response time:** Measure the availability request from the client perspective, use efficient database queries and indexes, and consider caching stable availability data. Verify that the displayed result meets the two-second target under representative load.
- **R7 — Mobile usability:** Use responsive layouts, touch-friendly controls, readable typography, and viewport testing across common mobile screen sizes. Validate the experience with accessibility and usability checks.

## Implementation and running

A lightweight, working Java application is implemented in [`src/`](src/) following the domain model and sequence diagrams:

- `src/AppointmentStatus.java`: Status enum (`PENDING`, `ACCEPTED`, `DECLINED`).
- `src/AvailabilitySlot.java`: Doctor bookable time windows (`isAvailable()`, date, start/end time).
- `src/Appointment.java`: Appointment entity (`submit()`, `accept()`, `decline()`, `isUpcoming()`, `countsTowardLimit()`).
- `src/Doctor.java`: Doctor entity (`getAvailability()`, `acceptAppointment()`, `declineAppointment()`).
- `src/Patient.java`: Patient entity (`viewAvailability()`, `bookAppointment()`, $N = 3$ upcoming limit enforcement).
- `src/Main.java`: Verification demo and interactive CLI menu.

### Prerequisites

- Java 11 or newer (Java 21+ recommended).

### Running the Application

1. **Run Automated Verification Demo (Self-test)**:
   ```bash
   java src/Main.java
   ```
   This automatically verifies all requirements in sequence:
   - **R1 (UC1)**: Queries and displays doctor availability.
   - **R2 (UC2)**: Books 3 upcoming appointments successfully.
   - **R4**: Verifies that booking a 4th upcoming appointment is rejected (limit $N = 3$).
   - **R3 (UC3)**: Doctor accepts an appointment request.
   - **R3 & R4 (UC4)**: Doctor declines an appointment request, freeing the slot and decrementing the active limit count.
   - **R4 Verification**: Patient successfully books again after a slot is freed.
   - **R2 Double-booking protection**: Rejects booking an already booked slot.

2. **Run Interactive CLI Mode**:
   ```bash
   java src/Main.java -i
   ```
   Provides a text menu to view availability, book appointments, accept/decline requests, and review active appointments.

3. **Alternative Compilation (using `javac` if preferred)**:
   ```bash
   javac -d bin src/*.java
   java -cp bin Main
   ```

## Team

Team member details and contribution links were not provided in the repository.

| Student ID | Full name | Assigned task | Branch | PR link |
|---|---|---|---|---|
| Not provided | Not provided | Not provided | Not provided | Not provided |
