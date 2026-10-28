package com.hospital.model;

/**
 * Represents a Patient in the hospital system.
 */
public class Patient {
    private String patientId;
    private String name;
    private int age;
    private String gender;
    private String phone;

    public Patient(String patientId, String name, int age, String gender, String phone) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return patientId + " - " + name;
    }

    public String details() {
        return "ID: " + patientId + "\nName: " + name + "\nAge: " + age
                + "\nGender: " + gender + "\nPhone: " + phone;
    }

    /** CSV format: patientId,name,age,gender,phone */
    public String toCsv() {
        return escape(patientId) + "," + escape(name) + "," + age + ","
                + escape(gender) + "," + escape(phone);
    }

    public static Patient fromCsv(String line) {
        String[] p = line.split(",", -1);
        if (p.length < 5) return null;
        try {
            int age = Integer.parseInt(p[2].trim());
            return new Patient(p[0].trim(), p[1].trim(), age, p[3].trim(), p[4].trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static String escape(String s) {
        // Simple escape: remove commas to keep CSV simple for lab project
        if (s == null) return "";
        return s.replace(",", " ").trim();
    }
}
