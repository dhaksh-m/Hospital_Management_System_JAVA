package com.hospital.ui;

import com.hospital.service.HospitalManager;

import javax.swing.*;
import java.awt.*;

/**
 * Main Dashboard window.
 */
public class MainFrame extends JFrame {

    private final HospitalManager manager;

    public MainFrame(HospitalManager manager) {
        this.manager = manager;
        setTitle("Hospital Management System");
        setSize(420, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("HOSPITAL MANAGEMENT SYSTEM", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(title, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new GridLayout(6, 1, 8, 8));
        JButton btnPatient = new JButton("Patient Management");
        JButton btnDoctor = new JButton("Doctor Management");
        JButton btnBook = new JButton("Book Appointment");
        JButton btnView = new JButton("View Appointments");
        JButton btnSearch = new JButton("Search Patient");
        JButton btnExit = new JButton("Exit");

        btnPanel.add(btnPatient);
        btnPanel.add(btnDoctor);
        btnPanel.add(btnBook);
        btnPanel.add(btnView);
        btnPanel.add(btnSearch);
        btnPanel.add(btnExit);
        panel.add(btnPanel, BorderLayout.CENTER);

        btnPatient.addActionListener(e -> new PatientFrame(manager).setVisible(true));
        btnDoctor.addActionListener(e -> new DoctorFrame(manager).setVisible(true));
        btnBook.addActionListener(e -> new AppointmentFrame(manager).setVisible(true));
        btnView.addActionListener(e -> new ViewAppointmentsFrame(manager).setVisible(true));
        btnSearch.addActionListener(e -> new SearchPatientFrame(manager).setVisible(true));
        btnExit.addActionListener(e -> System.exit(0));

        add(panel);
    }
}
