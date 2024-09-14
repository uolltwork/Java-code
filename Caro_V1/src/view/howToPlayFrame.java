/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author ADMIN
 */
import java.awt.*;
import javax.swing.*;

public class howToPlayFrame extends JFrame {
    
    public howToPlayFrame() {  
        setTitle("HOW TO PLAY");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null); // Center the frame on screen
        setLayout(new BorderLayout());

        JLabel title = new JLabel("GUIDE", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.TRUETYPE_FONT, 16));
        textArea.setEditable(false);
        
        textArea.setText("\n"
                + "Two players, one using the symbol O, the other using the symbol X\n"
                + "take turns filling in their symbols in the boxes.\n"
                + "The winner is the person who can create a sequence of \n"
                + "5 of their own symbols first\n"
                + "(either horizontally, vertically or diagonally.)");
        JScrollPane scrollPane = new JScrollPane(textArea);
       
        scrollPane.setAlignmentX(CENTER_ALIGNMENT);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AboutUsFrame();
    }
}

