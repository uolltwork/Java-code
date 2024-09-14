package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * CSD201_Ass - Topic: Game Caro - Group 02
 * @author 
 */
public class MainFrame extends JFrame {

    private JButton playGameButton, howToPlayButton, aboutUsButton, quitButton;
      
    
    public MainFrame() {

        setTitle("Caro_V1");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLocationRelativeTo(null);

        this.setIconImage(getToolkit().getDefaultToolkit().getImage(getClass().getResource("/images/IconCaro.png")));
        //disable maximize button
        this.setResizable(true);
        // centering the frame
        this.setLocationRelativeTo(null);
        //maximize the frame
        //this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH);

        //Create panel and set layout and background color
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 255, 125)); // màu vàng

        //Create buttons with icons and set alignment
        
        ImageIcon playIcon = new ImageIcon("src/images/palyButton.png");
        playGameButton = new JButton(playIcon);
        playGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        ImageIcon howToPlayIcon = new ImageIcon("src/images/Guide.png");
        howToPlayButton = new JButton(howToPlayIcon);
        howToPlayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        ImageIcon aboutUsIcon = new ImageIcon("src/images/aboutus.png");
        aboutUsButton = new JButton(aboutUsIcon);
        aboutUsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        ImageIcon quitIcon = new ImageIcon("src/images/Exit.png");
        quitButton = new JButton(quitIcon);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Add buttons and logo label to panel with padding
        ImageIcon logo = new ImageIcon();
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));
        panel.add(logoLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(playGameButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(howToPlayButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(aboutUsButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(quitButton);

        //Add ActionListener to "Play Game" button
        playGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CaroFrame playGameFrame = new CaroFrame();
                setVisible(false); 
                playGameFrame.setVisible(true);
            }
        });
        aboutUsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AboutUsFrame abs = new AboutUsFrame();
                abs.setVisible(true);
            }
        });
        howToPlayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                howToPlayFrame abs = new howToPlayFrame();
                abs.setVisible(true);
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int confirm = JOptionPane.showConfirmDialog(null, "Do you want to quit the game?", "Quit Game", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainFrame();

    }

}
