package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * CSD201_Ass - Topic: Game Caro - Group 02
 * @author Lê Trung Uôl_CE160438
 */

public class CaroInfo extends JFrame {

    private String direction = "/Info/";
    private String[] fileName = {"caroHelp", "caroAbout"};
    private String[] title = {"Guide", "About us"};

    public CaroInfo() {
    }

    /**
     * CaroInfo khởi tạo các thuộc tính của cửa sổ, chẳng hạn như đặt
     * thao tác đóng để loại bỏ cửa sổ khi đóng, đặt kích thước cửa sổ, đặt tiêu
     * đề, thêm vùng văn bản chứa thông tin về trò chơi, đặt vị trí cửa sổ thành
     * trung tâm
     *
     * @param type
     */
    public CaroInfo(int type) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setTitle("Caro - " + title[type]);
        // add content
        add(createJTextArea(type));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * CreateJTextArea là một phương thức riêng để tạo một đối tượng
     * JTextArea và thiết lập các thuộc tính của nó. Nó đọc nội dung của một tệp
     * có tên được xác định bởi tham số kiểu và đặt nội dung của vùng văn bản
     * thành nội dung của tệp. Lớp InputStream được sử dụng để đọc nội dung của
     * tệp dưới dạng luồng byte, sau đó được chuyển đổi thành đối tượng Chuỗi
     * bằng cách sử dụng lớp InputStreamReader.
     *
     * @param type
     * @return ta
     */
    private JTextArea createJTextArea(int type) {
        InputStream in = getClass().getResourceAsStream(direction + fileName[type]);
        System.out.println(direction + fileName[type]);
        JTextArea ta = new JTextArea();
        ta.setWrapStyleWord(true);
        ta.setLineWrap(true);
        ta.setEditable(false);
        ta.setBackground(null);
        try {
            ta.read(new InputStreamReader(in), null);
        } catch (IOException e) {
            System.out.println("Error read file");
        }
        return ta;
    }
    /*
    private JTextArea createJTextArea(int type) {
        File file = new File(direction + fileName[type] + ".txt");
        System.out.println(direction + fileName[type] + ".txt");
        JTextArea ta = new JTextArea();
        ta.setWrapStyleWord(true);
        ta.setLineWrap(true);
        ta.setEditable(false);
        ta.setBackground(null);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                ta.append(line);
                ta.append("\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
        return ta;
    }
     */
}
