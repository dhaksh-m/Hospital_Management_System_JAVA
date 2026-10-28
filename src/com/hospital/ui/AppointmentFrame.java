package com.hospital.ui;

import com.hospital.model.Appointment;
import com.hospital.model.Doctor;
import com.hospital.model.Patient;
import com.hospital.service.HospitalManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Book a new appointment.
 */
public class AppointmentFrame extends JFrame {

    private final HospitalManager manager;
    private final JComboBox<String> cmbPatient = new JComboBox<>();
    private final JComboBox<String> cmbDoctor = new JComboBox<>();
    private final JTextField txtDate = new JTextField("DD/MM/YYYY", 15);
    private final JTextField txtTime = new JTextField("HH:MM (24h)", 15);

    public AppointmentFrame(HospitalManager manager) {
        this.manager = manager;
        setTitle("Book Appointment");
        setSize(420, 300);
        setLocationRelativeTo(null);
        initUI();
        loadCombos();
    }

    private void initUI() {
        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        form.add(new JLabel("Patient:"));
        form.add(cmbPatient);
        form.add(new JLabel("Doctor:"));
        form.add(cmbDoctor);
        form.add(new JLabel("Date (DD/MM/YYYY):"));
        form.add(txtDate);
        form.add(new JLabel("Time (HH:MM):"));
        form.add(txtTime);

        JButton btnBook = new JButton("Book Appointment");
        btnBook.addActionListener(e -> book());

        setLayout(new BorderLayout());
        add(form, BorderLayout.CENTER);
        add(btnBook, BorderLayout.SOUTH);
    }

    private void loadCombos() {
        cmbPatient.removeAllItems();
        cmbDoctor.removeAllItems();
        for (Patient p : manager.getPatients()) {
            cmbPatient.addItem(p.getPatientId() + " - " + p.getName());
        }
        for (Doctor d : manager.getDoctors()) {
            cmbDoctor.addItem(d.getDoctorId() + " - " + d.getName());
        }
        if (cmbPatient.getItemCount() == 0) {
            cmbPatient.addItem("No patients - add one first");
        }
        if (cmbDoctor.getItemCount() == 0) {
            cmbDoctor.addItem("No doctors - add one first");
        }
    }

    private String extractId(String comboItem) {
        if (comboItem == null) return "";
        int idx = comboItem.indexOf(" - ");
        if (idx > 0) return comboItem.substring(0, idx).trim();
        return comboItem.trim();
    }

    private void book() {
        try {
            String patientId = extractId((String) cmbPatient.getSelectedItem());
            String doctorId = extractId((String) cmbDoctor.getSelectedItem());
            LocalDate date = LocalDate.parse(txtDate.getText().trim(), Appointment.DATE_FMT);
            LocalTime time = LocalTime.parse(txtTime.getText().trim(), Appointment.TIME_FMT);

            Appointment a = manager.bookAppointment(patientId, doctorId, date, time);
            JOptionPane.showMessageDialog(this,
                    "Appointment booked!\nID: " + a.getAppointmentId());
            dispose();
        } catch (java.time.format.DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid date/time format.\nUse DD/MM/YYYY and HH:MM (e.g. 25/12/2026 and 10:30).",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
