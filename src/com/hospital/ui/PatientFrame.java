package com.hospital.ui;

import com.hospital.model.Patient;
import com.hospital.service.HospitalManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Add / display patients.
 */
public class PatientFrame extends JFrame {

    private final HospitalManager manager;
    private final JTextField txtId = new JTextField(15);
    private final JTextField txtName = new JTextField(15);
    private final JTextField txtAge = new JTextField(15);
    private final JComboBox<String> cmbGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});
    private final JTextField txtPhone = new JTextField(15);
    private final DefaultTableModel tableModel =
            new DefaultTableModel(new String[]{"ID", "Name", "Age", "Gender", "Phone"}, 0);

    public PatientFrame(HospitalManager manager) {
        this.manager = manager;
        setTitle("Patient Management");
        setSize(620, 450);
        setLocationRelativeTo(null);
        initUI();
        refreshTable();
    }

    private void initUI() {
        JPanel form = new JPanel(new GridLayout(5, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        form.add(new JLabel("Patient ID:"));
        form.add(txtId);
        form.add(new JLabel("Name:"));
        form.add(txtName);
        form.add(new JLabel("Age:"));
        form.add(txtAge);
        form.add(new JLabel("Gender:"));
        form.add(cmbGender);
        form.add(new JLabel("Phone (10 digits):"));
        form.add(txtPhone);

        JButton btnAdd = new JButton("Add Patient");
        JButton btnClear = new JButton("Clear");
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAdd);
        btnPanel.add(btnClear);

        JTable table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);

        btnAdd.addActionListener(e -> addPatient());
        btnClear.addActionListener(e -> clearFields());

        setLayout(new BorderLayout());
        add(form, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);
    }

    private void addPatient() {
        try {
            String id = txtId.getText().trim();
            String name = txtName.getText().trim();
            int age = Integer.parseInt(txtAge.getText().trim());
            String gender = (String) cmbGender.getSelectedItem();
            String phone = txtPhone.getText().trim();

            manager.addPatient(id, name, age, gender, phone);
            JOptionPane.showMessageDialog(this, "Patient added successfully!");
            clearFields();
            refreshTable();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Age must be a number.",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAge.setText("");
        txtPhone.setText("");
        cmbGender.setSelectedIndex(0);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Patient p : manager.getPatients()) {
            tableModel.addRow(new Object[]{
                    p.getPatientId(), p.getName(), p.getAge(), p.getGender(), p.getPhone()});
        }
    }
}
