package com.hospital.service;

import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Central business logic + in-memory storage + CSV persistence.
 * All validation lives here so both GUI and tests use the same rules.
 */
public class HospitalManager {

    private final List<Patient> patients = new ArrayList<>();
    private final List<Doctor> doctors = new ArrayList<>();
    private final List<Appointment> appointments = new ArrayList<>();
    private int appointmentCounter = 1;

    private static final String DATA_DIR = "data";
    private static final String PATIENT_FILE = DATA_DIR + "/patients.csv";
    private static final String DOCTOR_FILE = DATA_DIR + "/doctors.csv";
    private static final String APPOINTMENT_FILE = DATA_DIR + "/appointments.csv";

    public HospitalManager() {
        loadAll();
    }

    // ---------------- Patient ----------------

    public void addPatient(String patientId, String name, int age, String gender, String phone) {
        if (patientId == null || patientId.trim().isEmpty())
            throw new IllegalArgumentException("Patient ID cannot be empty.");
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Patient name cannot be empty.");
        if (age <= 0 || age > 150)
            throw new IllegalArgumentException("Age must be between 1 and 150.");
        if (gender == null || gender.trim().isEmpty())
            throw new IllegalArgumentException("Please select gender.");
        if (phone == null || !phone.trim().matches("\\d{10}"))
            throw new IllegalArgumentException("Phone must be exactly 10 digits.");
        if (searchPatientById(patientId.trim()) != null)
            throw new IllegalArgumentException("Patient ID already exists: " + patientId);

        patients.add(new Patient(patientId.trim(), name.trim(), age, gender.trim(), phone.trim()));
        savePatients();
    }

    public Patient searchPatientById(String patientId) {
        if (patientId == null) return null;
        for (Patient p : patients) {
            if (p.getPatientId().equalsIgnoreCase(patientId.trim())) {
                return p;
            }
        }
        return null;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    // ---------------- Doctor ----------------

    public void addDoctor(String doctorId, String name, String specialization) {
        if (doctorId == null || doctorId.trim().isEmpty())
            throw new IllegalArgumentException("Doctor ID cannot be empty.");
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Doctor name cannot be empty.");
        if (specialization == null || specialization.trim().isEmpty())
            throw new IllegalArgumentException("Specialization cannot be empty.");
        if (searchDoctorById(doctorId.trim()) != null)
            throw new IllegalArgumentException("Doctor ID already exists: " + doctorId);

        doctors.add(new Doctor(doctorId.trim(), name.trim(), specialization.trim()));
        saveDoctors();
    }

    public Doctor searchDoctorById(String doctorId) {
        if (doctorId == null) return null;
        for (Doctor d : doctors) {
            if (d.getDoctorId().equalsIgnoreCase(doctorId.trim())) {
                return d;
            }
        }
        return null;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    // ---------------- Appointment ----------------

    public Appointment bookAppointment(String patientId, String doctorId, LocalDate date, LocalTime time) {
        if (patientId == null || patientId.trim().isEmpty())
            throw new IllegalArgumentException("Please select a patient.");
        if (doctorId == null || doctorId.trim().isEmpty())
            throw new IllegalArgumentException("Please select a doctor.");
        if (date == null)
            throw new IllegalArgumentException("Please enter appointment date.");
        if (time == null)
            throw new IllegalArgumentException("Please enter appointment time.");
        if (date.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Appointment date cannot be in the past.");

        Patient p = searchPatientById(patientId.trim());
        if (p == null)
            throw new IllegalArgumentException("Patient not found: " + patientId);
        Doctor d = searchDoctorById(doctorId.trim());
        if (d == null)
            throw new IllegalArgumentException("Doctor not found: " + doctorId);

        // Prevent double booking: same doctor, same date+time, still Booked
        for (Appointment a : appointments) {
            if (a.getStatus().equals("Booked")
                    && a.getDoctor().getDoctorId().equalsIgnoreCase(doctorId.trim())
                    && a.getDate().equals(date)
                    && a.getTime().equals(time)) {
                throw new IllegalArgumentException("Doctor already booked at this date/time.");
            }
        }

        String newId = String.format("A%03d", appointmentCounter++);
        Appointment appt = new Appointment(newId, p, d, date, time, "Booked");
        appointments.add(appt);
        saveAppointments();
        return appt;
    }

    public boolean cancelAppointment(String appointmentId) {
        for (Appointment a : appointments) {
            if (a.getAppointmentId().equalsIgnoreCase(appointmentId.trim())) {
                if (a.getStatus().equals("Cancelled")) {
                    throw new IllegalArgumentException("Appointment already cancelled.");
                }
                a.setStatus("Cancelled");
                saveAppointments();
                return true;
            }
        }
        throw new IllegalArgumentException("Appointment ID not found: " + appointmentId);
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    // ---------------- Persistence ----------------

    private void loadAll() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
        } catch (IOException ignored) {
        }
        loadPatients();
        loadDoctors();
        loadAppointments();
    }

    private void loadPatients() {
        patients.clear();
        Path path = Paths.get(PATIENT_FILE);
        if (!Files.exists(path)) return;
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                Patient p = Patient.fromCsv(line);
                if (p != null) patients.add(p);
            }
        } catch (IOException e) {
            System.err.println("Could not load patients: " + e.getMessage());
        }
    }

    private void loadDoctors() {
        doctors.clear();
        Path path = Paths.get(DOCTOR_FILE);
        if (!Files.exists(path)) return;
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                Doctor d = Doctor.fromCsv(line);
                if (d != null) doctors.add(d);
            }
        } catch (IOException e) {
            System.err.println("Could not load doctors: " + e.getMessage());
        }
    }

    private void loadAppointments() {
        appointments.clear();
        int max = 0;
        Path path = Paths.get(APPOINTMENT_FILE);
        if (!Files.exists(path)) return;
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(",", -1);
                if (p.length < 6) continue;
                try {
                    String apptId = p[0].trim();
                    Patient patient = searchPatientById(p[1].trim());
                    Doctor doctor = searchDoctorById(p[2].trim());
                    if (patient == null || doctor == null) continue;
                    LocalDate date = LocalDate.parse(p[3].trim(), Appointment.DATE_FMT);
                    LocalTime time = LocalTime.parse(p[4].trim(), Appointment.TIME_FMT);
                    String status = p[5].trim();
                    appointments.add(new Appointment(apptId, patient, doctor, date, time, status));
                    // keep counter ahead
                    if (apptId.matches("A\\d+")) {
                        int n = Integer.parseInt(apptId.substring(1));
                        if (n >= max) max = n;
                    }
                } catch (Exception ignored) {
                }
            }
        } catch (IOException e) {
            System.err.println("Could not load appointments: " + e.getMessage());
        }
        appointmentCounter = max + 1;
        if (appointmentCounter < 1) appointmentCounter = 1;
    }

    private void savePatients() {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(PATIENT_FILE))) {
            for (Patient p : patients) {
                bw.write(p.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Could not save patients: " + e.getMessage());
        }
    }

    private void saveDoctors() {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(DOCTOR_FILE))) {
            for (Doctor d : doctors) {
                bw.write(d.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Could not save doctors: " + e.getMessage());
        }
    }

    private void saveAppointments() {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(APPOINTMENT_FILE))) {
            for (Appointment a : appointments) {
                bw.write(a.toCsv());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Could not save appointments: " + e.getMessage());
        }
    }
}
