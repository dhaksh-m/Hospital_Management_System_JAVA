
package com.hospital.model;

/**
 * Represents a Doctor in the hospital system.
 */
public class Doctor {

    private String doctorId;
    private String name;
    private String specialization;

    /**
     * Constructor to create a Doctor.
     */
    public Doctor(String doctorId, String name, String specialization) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
    }

    // Getters
    public String getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    // Setters
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    /**
     * Display doctor information in a single line.
     */
    @Override
    public String toString() {
        return doctorId + " - " + name + " (" + specialization + ")";
    }

    /**
     * Display detailed doctor information.
     */
    public String details() {
        return "ID: " + doctorId
                + "\nName: " + name
                + "\nSpecialization: " + specialization;
    }

    /**
     * Convert doctor information to CSV format.
     * Format: doctorId,name,specialization
     */
    public String toCsv() {
        return escape(doctorId) + ","
                + escape(name) + ","
                + escape(specialization);
    }

    /**
     * Create a Doctor object from a CSV line.
     */
    public static Doctor fromCsv(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] parts = line.split(",", -1);

        if (parts.length != 3) {
            return null;
        }

        String doctorId = parts[0].trim();
        String name = parts[1].trim();
        String specialization = parts[2].trim();

        if (doctorId.isEmpty()
                || name.isEmpty()
                || specialization.isEmpty()) {
            return null;
        }

        return new Doctor(doctorId, name, specialization);
    }

    /**
     * Clean CSV field values.
     */
    private static String escape(String value) {
        if (value == null) {
            return "";
        }

        // Replace commas to preserve the existing simple CSV format.
        return value.replace(",", " ").trim();
    }
}
