package com.hospital.ui;

import com.hospital.model.Appointment;
import com.hospital.service.HospitalManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * View all appointments + cancel.
 */
public class ViewAppointmentsFrame extends JFrame {

    private final HospitalManager manager;
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"Appt ID", "Patient", "Doctor", "Date", "Time", "Status"}, 0);

    public ViewAppointmentsFrame(HospitalManager manager) {
        this.manager = manager;
        setTitle("View Appointments");
        setSize(700, 400);
        setLocationRelativeTo(null);
        initUI();
        refreshTable();
    }

    private void initUI() {
        JTable table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);

        JPanel bottom = new JPanel();
        JTextField txtId = new JTextField(10);
        JButton btnCancel = new JButton("Cancel Appointment");
        JButton btnRefresh = new JButton("Refresh");

        bottom.add(new JLabel("Appt ID:"));
        bottom.add(txtId);
        bottom.add(btnCancel);
        bottom.add(btnRefresh);

        btnCancel.addActionListener(e -> {
            String id = txtId.getText().trim();
            if (id.isEmpty() && table.getSelectedRow() >= 0) {
                id = (String) tableModel.getValueAt(table.getSelectedRow(), 0);
            }
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter an Appointment ID or select a row.",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                manager.cancelAppointment(id);
                JOptionPane.showMessageDialog(this, "Appointment " + id + " cancelled.");
                refreshTable();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnRefresh.addActionListener(e -> refreshTable());

        setLayout(new BorderLayout());
        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Appointment a : manager.getAppointments()) {
            tableModel.addRow(new Object[]{
                    a.getAppointmentId(),
                    a.getPatient().getPatientId() + " - " + a.getPatient().getName(),
                    a.getDoctor().getDoctorId() + " - " + a.getDoctor().getName(),
                    a.getDateString(),
                    a.getTimeString(),
                    a.getStatus()
            });
        }
    }
}
