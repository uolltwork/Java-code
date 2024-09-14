/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.*;
import javax.swing.*;

/**
 * CSD201_Ass - Topic: Game Caro - Group 02
 * @author Lê Trung Uôl_CE160438
 */
public class AboutUsFrame extends JFrame {

    public AboutUsFrame() {

        setTitle("ABOUT US");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the frame on screen
        setResizable(false);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        JLabel title = new JLabel("ABOUT US", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        contentPane.add(title, BorderLayout.NORTH);

        // Logo panel at the top of the frame
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("src/images/logofpt.png");
        Image logoImage = logoIcon.getImage();
        Image newImage = logoImage.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newLogoIcon = new ImageIcon(newImage);
        logoLabel.setIcon(newLogoIcon);

        logoLabel.setIcon(logoIcon);
        logoPanel.add(logoLabel);
        contentPane.add(logoPanel, BorderLayout.CENTER);

        // Text area in the center of the frame
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.TRUETYPE_FONT, 16));
        textArea.setAlignmentX(Component.CENTER_ALIGNMENT); // căn giữa theo trục X
        textArea.setAlignmentY(Component.CENTER_ALIGNMENT); // căn giữa theo trục Y
        textArea.setText("\n "
                + "                                  FPT University in Can Tho \n"
                + "                                   Class: SE1704 - Mentor: Vo Hong Khanh - Group 02 \n"
                + "                                    Subject: Data Structures and Algorithm (CSD201)\n"
                + "\n"
                            + "                                 Group member:\n"
                                + "                                   CE160438 - Lê Trung Uôl\n");

        // contentPane.add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        contentPane.add(scrollPane, BorderLayout.SOUTH);
        setContentPane(contentPane);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AboutUsFrame();
    }
}
