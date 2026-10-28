package com.hospital.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an Appointment linking a Patient and a Doctor.
 */
public class Appointment {
    public static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    private String appointmentId;
    private Patient patient;
    private Doctor doctor;
    private LocalDate date;
    private LocalTime time;
    private String status; // "Booked" or "Cancelled"

    public Appointment(String appointmentId, Patient patient, Doctor doctor,
                       LocalDate date, LocalTime time, String status) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateString() {
        return date.format(DATE_FMT);
    }

    public String getTimeString() {
        return time.format(TIME_FMT);
    }

    @Override
    public String toString() {
        return appointmentId + " | " + patient.getName() + " with "
                + doctor.getName() + " on " + getDateString() + " " + getTimeString()
                + " [" + status + "]";
    }

    /** CSV: appointmentId,patientId,doctorId,date(dd/MM/yyyy),time(HH:mm),status */
    public String toCsv() {
        return appointmentId + "," + patient.getPatientId() + "," + doctor.getDoctorId()
                + "," + getDateString() + "," + getTimeString() + "," + status;
    }
}
