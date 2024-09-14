package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import controller.PlayerController;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import model.MyImage;

/**
 * CSD201_Ass - Topic: Game Caro - Group 02
 *
 * @author
 */
public class CaroFrame extends JFrame implements ActionListener {

    private HashMap<String, Integer> playerScores = new HashMap<String, Integer>();

    private static final long serialVersionUID = 1L;
    private int width = CaroGraphics.width;
    private int height = CaroGraphics.height;

    private CaroGraphics caroGraphics;

    public static JLabel lbStatusO;
    public static JLabel lbStatusX;
    private JLabel lbNamePlayerO;
    private JLabel lbNamePlayerX;
    private JLabel lbScoreO;
    private JLabel lbScoreX;
    private ImageIcon iconPlayerO;
    private ImageIcon iconPlayerX;
    private int scoreO = 0, scoreX = 0;

    private String playerName1 = "Player 1", playerName2 = "Player 2";

    private MyImage myImage = new MyImage();
    private PlayerController selectPlayerFrame;

    private String backgroundMusicPath = "/Audio/BgTheme.wav";
    
    private Audio backgroundMusic;
    private boolean muted;

    public CaroFrame() {
        init();
        backgroundMusic = new Audio(backgroundMusicPath);
        backgroundMusic.start();
    }

    private void init() {
        setTitle("Caro_V1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initGraphics();
        setJMenuBar(createJMenuBar());
        add(createMainPainl());

        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        selectPlayer();
    }

    private void initGraphics() {
        caroGraphics = new CaroGraphics();
        caroGraphics.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                caroGraphics.actionClick(e.getPoint());
                if (caroGraphics.getWiner() > 0) {
                    win(caroGraphics.getWiner());
                }
            }
        });
    }

    private void selectPlayer() {
        if (selectPlayerFrame == null) {
            selectPlayerFrame = new PlayerController(this);
        }
        selectPlayerFrame.setVisible(true);
    }

    public void updateStatus() {
        playerName1 = selectPlayerFrame.getPlayerName1();
        playerName2 = selectPlayerFrame.getPlayerName2();
        caroGraphics.player = caroGraphics.playerRoot;
        lbNamePlayerX.setText(playerName1);
        lbNamePlayerO.setText(playerName2);
        if (selectPlayerFrame.getStart() == 1) {
            caroGraphics.playerRoot = true;
        } else {
            caroGraphics.playerRoot = false;
        }
        caroGraphics.player = caroGraphics.playerRoot;
        caroGraphics.setStatus();
        System.out.println("updated");
    }

    /**
     * setup menu bar
     *
     * @return
     */
    private JMenuBar createJMenuBar() {
        JMenuBar mb = new JMenuBar();
        String[] game = {"New Game", "New Round", "", "Exit"};
        mb.add(createJMenu("Play", game, KeyEvent.VK_P));
        String[] help = {"Guide", "", "About us"};
        mb.add(createJMenu("Info", help, KeyEvent.VK_I));
        String[] home = {"Back to Menu"};
        mb.add(createJMenu("Home", home, KeyEvent.VK_H));
        String[] score = {"Hight Score"};
        mb.add(createJMenu("Score", score, KeyEvent.VK_C));
//        String[] audio = {"Turn on","Turn off"};
//        mb.add(createJMenu("Audio", audio, KeyEvent.VK_A));

        String[] audio = {"Turn on", "Turn off"};
        JMenu audioMenu = createJMenu("Audio", audio, KeyEvent.VK_A);
        JMenuItem turnOffMenuItem = audioMenu.getItem(1); // Lấy JMenuItem "Turn off"
        turnOffMenuItem.addActionListener(e -> TurnOff()); // Gắn bộ lắng nghe sự kiện cho JMenuItem "Turn off"
        mb.add(audioMenu);
        return mb;
    }

    private JMenu createJMenu(String menuName, String itemName[], int key) {
        JMenu m = new JMenu(menuName);
        m.addActionListener(this);
        m.setMnemonic(key);

        for (int i = 0; i < itemName.length; i++) {
            if (itemName[i].equals("")) {
                m.add(new JSeparator());
            } else {
                m.add(createJMenuItem(itemName[i]));
            }
        }
        return m;
    }

    private JMenuItem createJMenuItem(String itName) {
        JMenuItem item = new JMenuItem(itName);
        item.addActionListener(this);
        return item;
    }

    /**
     * create main board
     *
     * @return
     */
    private JPanel createMainPainl() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createPanelGraphics(), BorderLayout.CENTER);
        panel.add(createSidebarPanel(true), BorderLayout.WEST);
        panel.add(createSidebarPanel(false), BorderLayout.EAST);
        return panel;
    }

    /**
     *
     * @return
     */
    private JPanel createPanelGraphics() {
        this.setIconImage(getToolkit().getDefaultToolkit().getImage(getClass().getResource("/images/IconCaro.png")));
        JPanel panelGraphics = new JPanel(null);
        panelGraphics.add(caroGraphics, BorderLayout.CENTER);
        int bound = 10;
        caroGraphics.setBounds(bound, bound, caroGraphics.width,
                caroGraphics.height);
        panelGraphics.setPreferredSize(new Dimension(caroGraphics.width + bound
                * 2, caroGraphics.height + bound * 2));

        panelGraphics.setBorder(new LineBorder(Color.black));
        panelGraphics.setBackground(Color.green);
        return panelGraphics;
    }

    private JPanel createSidebarPanel(boolean player) {
        JPanel panel = new JPanel(new BorderLayout());

        panel.add(createPanelStatus(player), BorderLayout.PAGE_START);

        panel.add(createPlayerPanel(player), BorderLayout.CENTER);

        panel.add(createPanelBottom(player), BorderLayout.PAGE_END);
        return panel;
    }

    private JPanel createPanelStatus(boolean player) {
        JPanel panelStatus = new JPanel(new GridLayout(2, 1, 2, 2));
        JPanel panel1 = new JPanel();

        if (player) {
            lbStatusX = new JLabel();
            lbStatusX.setHorizontalAlignment(JLabel.CENTER);
            lbNamePlayerX = new JLabel(" Player 1 ");
            lbNamePlayerX.setHorizontalAlignment(JLabel.CENTER);

            lbScoreX = new JLabel("0");
            lbScoreX.setFont(lbScoreX.getFont().deriveFont(Font.PLAIN, 35f));
            lbScoreX.setForeground(Color.red);
            lbScoreX.setHorizontalAlignment(JLabel.CENTER);

            panel1.add(lbStatusX);
            panel1.add(lbNamePlayerX);
            panelStatus.add(panel1);
            panelStatus.add(lbScoreX);

        } else {
            lbStatusO = new JLabel();
            lbStatusO.setHorizontalAlignment(JLabel.CENTER);
            lbNamePlayerO = new JLabel(" Player 2 ");
            lbNamePlayerO.setHorizontalAlignment(JLabel.CENTER);

            lbScoreO = new JLabel("0");
            lbScoreO.setFont(lbScoreO.getFont().deriveFont(Font.PLAIN, 35f));
            lbScoreO.setForeground(Color.blue);
            lbScoreO.setHorizontalAlignment(JLabel.CENTER);

            panel1.add(lbStatusO);
            panel1.add(lbNamePlayerO);
            panelStatus.add(panel1);
            panelStatus.add(lbScoreO);
        }
        int bound = 1;
        panelStatus.setBorder(new LineBorder(Color.black));
        panelStatus.setPreferredSize(new Dimension(width / 3, height / 6 - 25));
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(bound, bound, bound, bound));
        panel.add(panelStatus);
        return panel;
    }

    private JPanel createPlayerPanel(boolean player) {
        int boundw = 10;
        int boundh = 10;
        int h = height * 2 / 3 + boundh;
        int w = width / 3;
        String imgPlayerO = "playerO.png";
        String imgPlayerX = "playerX.png";
        iconPlayerO = new ImageIcon(myImage.reSizeImage(
                myImage.getMyImageIcon(imgPlayerO), w - boundw, h - boundh));
        iconPlayerX = new ImageIcon(myImage.reSizeImage(
                myImage.getMyImageIcon(imgPlayerX), w - boundw, h - boundh));

        ImageIcon icon = player ? iconPlayerX : iconPlayerO;
        JLabel lbPlayer = new JLabel(icon);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(w, h));

        panel.add(lbPlayer, BorderLayout.CENTER);

        int bound = 1;
        panel.setBorder(new LineBorder(Color.black));
        JPanel panel1 = new JPanel();
        panel1.setBorder(new EmptyBorder(bound, bound, bound, bound));
        panel1.add(panel);
        return panel1;
    }

    /**
     * button function
     *
     * @param player
     * @return
     */
    private JPanel createPanelBottom(boolean player) {
        String[] str1 = {"Undo", "Resignation"};
        String[] str2 = {"New Game", "New Round"};
        String[] str;
        if (player) {
            str = str1;
        } else {
            str = str2;
        }
        int size = str.length;
        JPanel panel = new JPanel(new GridLayout(size, 1, 5, 5));
        for (int i = 0; i < size; i++) {
            panel.add(createJButton(str[i]));
        }
        int bound = 1;
        panel.setBorder(new LineBorder(Color.WHITE));
        panel.setPreferredSize(new Dimension(width / 3, height / 6));
        JPanel panel1 = new JPanel();
        panel1.setBorder(new EmptyBorder(bound, bound, bound, bound));
        panel1.add(panel);
        return panel1;
    }

    private JButton createJButton(String btnName) {
        JButton btn = new JButton(btnName);
        btn.addActionListener(this);
        return btn;
    }

    /**
     * all method of game
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command == "New Game") {
            actionNewGame();
        }
        if (command == "New Round") {
            actionNewUnit();
        }
        if (command == "Exit") {
            actionExit();
        }
        if (command == "Guide") {
            actionHelp();
        }
        if (command == "About us") {
            actionAbout();
        }
        if (command == "Undo") {
            actionUndo();
        }
        if (command == "Resignation") {
            actionGiveIn();
        }
        if (command == "Back to Menu") {
            actionReturn();
        }
        if (command == "Hight Score") {
            //showScoreboard();
            UpdateScoresFromFile();
        }
        if (command == "Turn on") {
            TurnOn();
        }
        if (command == "Turn off") {
            TurnOff();
        }

    }

    private void actionNewGame() {
        int select = showDialog("Do you want to play new game?",
                "New Game");
        if (select == 0) {
            scoreO = 0;
            scoreX = 0;
            clear();
        }
    }

    private void actionNewUnit() {
        int select = showDialog("Do you want to play new round?",
                "New Round");
        if (select == 0) {
            clear();
        }
    }

    private void actionExit() {
        int select = showDialog("Do you want to exit?", "Exit");
        if (select == 0) {
            System.exit(0);
        }
    }

    /**
     * Return menu
     */
    private void actionReturn() {
        int select = showDialog("Do you want to return Menu?", "Exit");
        if (select == 0) {
            this.setVisible(false); // ẩn frame hiện tại
            new MainFrame().setVisible(true); // hiển thị lại MainFrame
        }
    }

//    private MainFrame actionQuitHome() {
//        Container c = getContentPane();
//        while (!(c instanceof MainFrame)) {
//            c = c.getParent();
//        }
//        return (MainFrame) c;
//    }
    /**
     * undo to action
     */
    private void actionUndo() {
        caroGraphics.undo();
    }

    private void actionHelp() {
        new CaroInfo(0);
    }

    private void actionAbout() {
        new CaroInfo(1);
    }

    /**
     * Resign to the game
     */
    private void actionGiveIn() {
        int sO = 0, sX = 0;
        String playerName = "";
        if (caroGraphics.player) {
            sO = 1;
            playerName = playerName1;
        } else {
            sX = 1;
            playerName = playerName2;
        }

        int select = showDialog(playerName + " you really want to resign?",
                "New Game");
        if (select == 0) {
            scoreO += sO;
            scoreX += sX;
            clear();
        }
    }

    private void TurnOn() {
        if (backgroundMusic != null) {
            backgroundMusic.unmute();
        }
    }

    private void TurnOff() {
        if (backgroundMusic != null) {
            backgroundMusic.mute();
        }
    }

    //chỉ hiển thị board ko lưu
    private void showScoreboardX() {
        StringBuilder sb = new StringBuilder();
        sb.append("Scoreboard:\n");

        // Create a copy of playerScores and sort it by value in descending order
        Map<String, Integer> sortedScores = new LinkedHashMap<>();
        playerScores.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEachOrdered(x -> sortedScores.put(x.getKey(), x.getValue()));

        int rank = 1;
        for (Map.Entry<String, Integer> entry : sortedScores.entrySet()) {
            sb.append(rank++).append(". ");
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Scoreboard", JOptionPane.INFORMATION_MESSAGE);
    }

    // hiển thị board và lưu
    private void updateScoresFromFile() {
        File file = new File("scores.txt");
        if (!file.exists()) {
            return;
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(":");
                String playerName = tokens[0];
                int score = Integer.parseInt(tokens[1]);
                playerScores.put(playerName, score);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Show scoreboard
        StringBuilder sb = new StringBuilder();
        sb.append("Top 5 highest score:\n");

        // Create a copy of playerScores and sort it by value in descending order
        Map<String, Integer> sortedScores = new LinkedHashMap<>();
        playerScores.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .forEachOrdered(x -> sortedScores.put(x.getKey(), x.getValue()));

        int rank = 1;
        for (Map.Entry<String, Integer> entry : sortedScores.entrySet()) {
            sb.append(rank++).append(". ");
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Ranking", JOptionPane.INFORMATION_MESSAGE);
    }

    private void UpdateScoresFromFile() {
        File file = new File("scores.txt");
        if (!file.exists()) {
            return;
        }

        // Load existing scores from the file
        Map<String, Integer> existingScores = new LinkedHashMap<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(":");
                String playerName = tokens[0];
                int score = Integer.parseInt(tokens[1]);
                existingScores.put(playerName, score);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Update player scores
        existingScores.putAll(playerScores);

        // Sort scores in descending order
        List<Map.Entry<String, Integer>> sortedScores = existingScores.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        // Limit to top 5 scores
        if (sortedScores.size() > 5) {
            sortedScores = sortedScores.subList(0, 5);
        }

        // Update the file with the updated scores
        try (PrintWriter writer = new PrintWriter(file)) {
            for (Map.Entry<String, Integer> entry : sortedScores) {
                writer.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Show scoreboard
        StringBuilder sb = new StringBuilder();
        sb.append("Top 5 highest scores:\n");
        int rank = 1;
        for (Map.Entry<String, Integer> entry : sortedScores) {
            sb.append(rank++).append(". ");
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Ranking", JOptionPane.INFORMATION_MESSAGE);
    }

    private int showDialog(String message, String title) {
        int select = JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                null, null);
        return select;
    }

    private void clear() {
        caroGraphics.init();
        updateScore();
        selectPlayer();
        caroGraphics.setStatus();
    }

    private void updateScore() {
        lbScoreO.setText(scoreO + "");
        lbScoreX.setText(scoreX + "");
    }

    //check winner v1
    /**
     * Notification win player update score ++
     *
     * @param winer
     */
//    private void win(int winer) {
//        String playerName = "";
//        if (winer == 1) {
//            scoreX++;
//            playerName = playerName1;
//        } else {
//            scoreO++;
//            playerName = playerName2;
//        }
//        Object[] options = {"New Game", "New Round"};
//        int select = JOptionPane.showOptionDialog(this, "Congratulations, "
//                + playerName + " you won this game "
//                + (scoreO + scoreX), "Good job bro",
//                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
//                null, options, options[options.length - 1]);
//        if (select == 2) {
//            actionExit();
//        } else if (select == 0) {
//            scoreO = 0;
//            scoreX = 0;
//        }
//        clear();
//    }
//    
    private String currentPlayerName; // Thêm biến để lưu trữ tên người chơi

    //check winner v2
    private void win(int winner) {
        String playerName = (winner == 1) ? playerName1 : playerName2;
        int score = (winner == 1) ? ++scoreX : ++scoreO;

        // Update playerScores
        if (playerScores.containsKey(playerName)) {
            playerScores.put(playerName, playerScores.get(playerName) + 1);
        } else {
            playerScores.put(playerName, 1);
        }

        Object[] options = {"New Game", "New Round"};
        int select = JOptionPane.showOptionDialog(this,
                "Congratulations, " + (currentPlayerName != null ? currentPlayerName : playerName) + "! You won this game. Your score: " + score,
                "Good job!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[options.length - 1]);

        if (select == 1) {
            clear();
        }

    }

}
