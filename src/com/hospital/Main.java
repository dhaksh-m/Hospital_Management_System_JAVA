package com.hospital;

import com.hospital.service.HospitalManager;
import com.hospital.ui.MainFrame;

import javax.swing.*;

/**
 * Application entry point.
 */
public class Main {
    public static void main(String[] args) {
        // Use system look and feel for native appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        HospitalManager manager = new HospitalManager();

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(manager);
            frame.setVisible(true);
        });
    }
}
