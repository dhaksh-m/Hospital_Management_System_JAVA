package com.hospital.ui;

import com.hospital.model.Patient;
import com.hospital.service.HospitalManager;

import javax.swing.*;
import java.awt.*;

/**
 * Search patient by ID.
 */
public class SearchPatientFrame extends JFrame {

    private final HospitalManager manager;
    private final JTextField txtSearch = new JTextField(15);
    private final JTextArea txtResult = new JTextArea(8, 30);

    public SearchPatientFrame(HospitalManager manager) {
        this.manager = manager;
        setTitle("Search Patient");
        setSize(420, 320);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel top = new JPanel();
        top.add(new JLabel("Patient ID:"));
        top.add(txtSearch);
        JButton btnSearch = new JButton("Search");
        top.add(btnSearch);

        txtResult.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtResult);

        btnSearch.addActionListener(e -> search());

        // Allow Enter key to search
        txtSearch.addActionListener(e -> search());

        setLayout(new BorderLayout(10, 10));
        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private void search() {
        String id = txtSearch.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Patient ID.",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Patient p = manager.searchPatientById(id);
        if (p == null) {
            txtResult.setText("No patient found with ID: " + id);
        } else {
            txtResult.setText(p.details());
        }
    }
}
