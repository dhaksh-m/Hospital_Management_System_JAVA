package com.hospital.ui;

import com.hospital.model.Doctor;
import com.hospital.service.HospitalManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Add / display doctors.
 */
public class DoctorFrame extends JFrame {

    private final HospitalManager manager;
    private final JTextField txtId = new JTextField(15);
    private final JTextField txtName = new JTextField(15);
    private final JComboBox<String> cmbSpec = new JComboBox<>(new String[]{
            "General", "Cardiology", "Orthopedics", "Pediatrics",
            "Dermatology", "Neurology", "Gynecology", "ENT", "Other"
    });
    private final DefaultTableModel tableModel =
            new DefaultTableModel(new String[]{"ID", "Name", "Specialization"}, 0);

    public DoctorFrame(HospitalManager manager) {
        this.manager = manager;
        setTitle("Doctor Management");
        setSize(560, 400);
        setLocationRelativeTo(null);
        initUI();
        refreshTable();
    }

    private void initUI() {
        JPanel form = new JPanel(new GridLayout(3, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        form.add(new JLabel("Doctor ID:"));
        form.add(txtId);
        form.add(new JLabel("Name:"));
        form.add(txtName);
        form.add(new JLabel("Specialization:"));
        cmbSpec.setEditable(true);
        form.add(cmbSpec);

        JButton btnAdd = new JButton("Add Doctor");
        JButton btnClear = new JButton("Clear");
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAdd);
        btnPanel.add(btnClear);

        JTable table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);

        btnAdd.addActionListener(e -> addDoctor());
        btnClear.addActionListener(e -> {
            txtId.setText("");
            txtName.setText("");
            cmbSpec.setSelectedIndex(0);
        });

        setLayout(new BorderLayout());
        add(form, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);
    }

    private void addDoctor() {
        try {
            String id = txtId.getText().trim();
            String name = txtName.getText().trim();
            String spec = ((String) cmbSpec.getSelectedItem()).trim();
            manager.addDoctor(id, name, spec);
            JOptionPane.showMessageDialog(this, "Doctor added successfully!");
            txtId.setText("");
            txtName.setText("");
            refreshTable();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Doctor d : manager.getDoctors()) {
            tableModel.addRow(new Object[]{d.getDoctorId(), d.getName(), d.getSpecialization()});
        }
    }
}
