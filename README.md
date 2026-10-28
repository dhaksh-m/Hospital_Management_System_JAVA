# Hospital Appointment & Patient Management System

A simple Java Swing desktop application to manage patients, doctors, and appointments for a small hospital/clinic. Built as a Java lab project using OOP, `ArrayList`, exception handling, event handling, and CSV file storage (no database required).

## Features

**Patient Management**
- Add patient (ID, name, age, gender, 10-digit phone)
- View all patients in a table
- Search patient by ID

**Doctor Management**
- Add doctor (ID, name, specialization)
- View all doctors

**Appointment Management**
- Book appointment (select patient + doctor, date `DD/MM/YYYY`, time `HH:MM` 24h)
- View all appointments in a table
- Cancel appointment (by ID or table row selection)
- Prevents past dates and double-booking same doctor/date/time

**Validation**
- Empty-field checks, age 1–150, phone exactly 10 digits
- Duplicate ID checks, proper error dialogs

**Storage**
- Auto-saved to CSV files in `data/` folder:
  - `data/patients.csv`
  - `data/doctors.csv`
  - `data/appointments.csv`
- Data reloads automatically on next launch.

## Project Structure

```
HospitalManagement/
├── README.md
├── run.bat              Windows quick-run script
├── run.sh               Linux/Mac quick-run script
├── data/                Auto-created CSV storage (patients, doctors, appointments)
└── src/com/hospital/
    ├── Main.java                 Entry point
    ├── model/
    │   ├── Patient.java
    │   ├── Doctor.java
    │   └── Appointment.java
    ├── service/
    │   └── HospitalManager.java  Business logic + validation + CSV persistence
    └── ui/
        ├── MainFrame.java            Dashboard
        ├── PatientFrame.java         Add/view patients
        ├── DoctorFrame.java          Add/view doctors
        ├── AppointmentFrame.java     Book appointment
        ├── ViewAppointmentsFrame.java View/cancel appointments
        └── SearchPatientFrame.java   Search patient by ID
```

## Requirements

- **JDK 17 or higher** (JDK 17, 21 recommended)
- Any OS: Windows / Linux / macOS
- Optional IDE: Eclipse / IntelliJ IDEA / NetBeans / VS Code

Check Java:

```bash
java -version
javac -version
```

If not installed, download from https://adoptium.net (Temurin JDK 17+) and reinstall, then reopen terminal.

## How to Run

All commands run from the project root folder `HospitalManagement/`.

### Option 1 — Quick run (Windows)

Double-click `run.bat`, or in terminal:

```bat
run.bat
```

This compiles all `src` files into `out/` and launches `com.hospital.Main`.

### Option 2 — Quick run (Linux / macOS)

```bash
chmod +x run.sh
./run.sh
```

### Option 3 — Manual compile & run (any OS)

```bash
# 1. Compile
javac -d out $(find src -name "*.java")

# Windows PowerShell equivalent:
# javac -d out (Get-ChildItem -Recurse -Filter *.java -Path src).FullName

# 2. Run
java -cp out com.hospital.Main
```

On Windows CMD:

```bat
javac -d out src\com\hospital\Main.java src\com\hospital\model\*.java src\com\hospital\service\*.java src\com\hospital\ui\*.java
java -cp out com.hospital.Main
```

### Option 4 — Run in an IDE

**Eclipse:**
1. File → New → Java Project → name `HospitalManagement`
2. Copy `src/com` into the project's `src` folder
3. Right-click `Main.java` → Run As → Java Application

**IntelliJ IDEA:**
1. File → Open → select `HospitalManagement` folder
2. Mark `src` as Sources Root (right-click → Mark Directory as)
3. Set Project SDK to 17+ (File → Project Structure)
4. Open `src/com/hospital/Main.java` → click Run ▶

**VS Code:**
1. Install extension `Extension Pack for Java`
2. Open `HospitalManagement` folder
3. Open `src/com/hospital/Main.java` → click Run

**NetBeans:**
1. File → New Project → Java with Ant → Java Application
2. Replace generated `src` with this project's `src/com`
3. Set Main class to `com.hospital.Main` → Run Project (F6)

## How to Use the App

1. Launch → **Main Dashboard** appears.
2. **Patient Management** → enter ID (e.g. `P001`), name, age, gender, phone → Add.
3. **Doctor Management** → enter ID (e.g. `D001`), name, specialization → Add.
4. **Book Appointment** → select patient & doctor, enter date like `25/12/2026`, time like `10:30` → Book.
5. **View Appointments** → see all; to cancel, type Appt ID (e.g. `A001`) or select row → Cancel.
6. **Search Patient** → enter patient ID → Search.
7. Data persists in `data/*.csv` — safe to close and reopen.

Sample data to try:
- Patient: `P001`, `Ravi Kumar`, `32`, `Male`, `9876543210`
- Doctor: `D001`, `Dr. Sharma`, `Cardiology`
- Appointment date: tomorrow's date in `DD/MM/YYYY`, time `10:30`

## Java Concepts Demonstrated

- Classes, objects, encapsulation, constructors
- `ArrayList` collections
- Exception handling (`IllegalArgumentException`, `DateTimeParseException`)
- Swing GUI + event handling (`JFrame`, `JTable`, `JComboBox`, `ActionListener`)
- `LocalDate` / `LocalTime` with `DateTimeFormatter`
- File handling (CSV read/write with `java.nio.file`)

## Troubleshooting

| Problem | Fix |
|---|---|
| `java: command not found` | Install JDK 17+ and reopen terminal. Set `JAVA_HOME` and add `bin` to PATH. |
| `javac: command not found` | Install full JDK (not just JRE). |
| Blank patient/doctor dropdown when booking | Add at least one patient and one doctor first. |
| `Invalid date/time format` | Use exactly `DD/MM/YYYY` (e.g. `05/10/2026`) and `HH:MM` 24h (e.g. `14:30`). |
| `Doctor already booked` | That slot is taken — pick a different time. |
| CSV files not found | Normal on first run — `data/` is auto-created on launch. |

## Future Enhancements

- MySQL integration, login system, prescriptions, billing, SMS/email reminders, availability calendar.
