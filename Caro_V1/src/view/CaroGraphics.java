package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.UndoManager; 
import model.MyImage;
import model.Environment;
import model.Return;

/**
 * CSD201_Ass - Topic: Game Caro - Group 02
 *
 */
public class CaroGraphics extends JPanel {

    private static final long serialVersionUID = 1L; 
    public final static int sizeCell = 30;
    public final static int row = 18;
    public final static int col = 18;
    public final static int width = sizeCell * col + 1;
    public final static int height = sizeCell * row + 1;
    private int sizeImg = sizeCell - 2;
    public boolean player, playerRoot;
    private Environment environment;
    private MyImage myImage = new MyImage();
    private Icon iconActive;
    private UndoManager undoManager = new UndoManager();
    protected Vector<Point> pointVector;
    private int winer = 0;

    public int getWiner() {			
        return winer;
    }

    public void setWiner(int winer) {			
        this.winer = winer;				
    }

    public void init() {		
        winer = 0;
        environment = new Environment();		
        player = playerRoot;					
        pointVector = new Vector<Point>();		
        repaint();							
    }

    public CaroGraphics() {								
        makeIcon();										
        setPreferredSize(new Dimension(width, height));		
        init();											
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);								
        setBackground(new Color(238, 238, 238));				
        for (int i = 0; i <= row; i++) {							
            g.drawLine(i * sizeCell, 0, i * sizeCell, height - 1);
            g.drawLine(0, i * sizeCell, width - 1, i * sizeCell);
        }
        drawImg(g);
        System.out.println("Yes");		
      
    }

    private void drawImg(Graphics g) {			

        boolean player = playerRoot;		
        for (int i = 0; i < pointVector.size(); i++) {	

            Image image = player ? myImage.imgCross : myImage.imgNought; 
            Point point = convertPointToCaro(convertPoint(pointVector.get(i)));
            g.drawImage(image, point.x, point.y, null);	
            player = !player;	
        }
    }

    /**
     * Phương thức drawImg() dùng để vẽ các hình ảnh của quân cờ được đánh trên
     * bàn cờ. Ở mỗi lần lặp, phương thức sẽ lấy giá trị của biến player, dùng
     * nó để xác định loại hình ảnh cờ (chữ "X" hoặc "O") cần và vẽ nó lên bàn
     * cờ tại vị trí tương ứng với giá trị của phần tử trong pointVector. Sau
     * mỗi vòng lặp, giá trị của biến player sẽ được thay đổi để lần lượt đánh
     * dấu các nước đi của hai người chơi.
     */
    private Point convertPoint(Point point) {				
        int x, y;
        int deviation = 1;
        //
        x = (point.x % sizeCell > deviation) ? (point.x / sizeCell * sizeCell + sizeCell / 2)
                : (point.x / sizeCell * sizeCell - sizeCell / 2);
        y = (point.y % sizeCell > deviation) ? (point.y / sizeCell * sizeCell + sizeCell / 2)
                : (point.y / sizeCell * sizeCell - sizeCell / 2);
        return new Point(x, y);
       
    }

    private Point convertPointToMaxtrix(Point point) { 
        return new Point(point.y / sizeCell, point.x / sizeCell);
    }

    private Point convertPointToCaro(Point point) {
        return new Point(point.x - sizeImg / 2, point.y - sizeImg / 2);
    }

    /**
     * Hàm setStatus() được sử dụng để cập nhật trạng thái của ứng dụng Caro sau
     * mỗi lượt đánh.
     * Đặt icon cho các nhãn trạng thái của người chơi X và O thành iconActive.
     * Nếu lượt chơi là của người chơi X (player = true), nhãn trạng thái của
     * người chơi X sẽ được kích hoạt và nhãn trạng thái của người chơi O sẽ bị
     * vô hiệu hóa. Ngược lại, nếu lượt chơi là của người chơi O (player =
     * false), nhãn trạng thái của người chơi O sẽ được kích hoạt và nhãn trạng
     * thái của người chơi X sẽ bị vô hiệu hóa.
     */
    public void setStatus() {
        CaroFrame.lbStatusO.setIcon(iconActive);
        CaroFrame.lbStatusX.setIcon(iconActive);
        if (player) {
            CaroFrame.lbStatusX.setEnabled(true);
            CaroFrame.lbStatusO.setEnabled(false);
        } else {
            CaroFrame.lbStatusX.setEnabled(false);
            CaroFrame.lbStatusO.setEnabled(true);
        }
    }

    /**
     * Setup icon thể hiện trạng thái hoạt đông ( đến lượt đi )
     */
    private void makeIcon() {
        iconActive = new ImageIcon(myImage.reSizeImage(
                myImage.getMyImageIcon("actionG.png"), 20, 20));
    }

    /**
     * Khi người chơi nhấp chuột vào một điểm trên bàn cờ Caro. Đầu tiên, hàm
     * thực hiện việc chuyển đổi tọa độ của điểm được nhấp chuột từ tọa độ màn
     * hình sang tọa độ trên bàn cờ thông qua hàm convertPoint(). Sau đó, hàm
     * gọi phương thức updateMatrix() của đối tượng environment để cập nhật ma
     * trận bàn cờ thông qua người chơi hiện tại và tọa độ của điểm được nhấp
     * chuột (được chuyển đổi sang tọa độ trên ma trận). Nếu việc cập nhật ma
     * trận thành công, điểm được nhấp chuột được thêm vào vector pointVector,
     * và một UndoableEditEvent được tạo ra để lưu lại trạng thái hiện tại của
     * bàn cờ, nhằm cho việc hoàn tác lại các bước được thực hiện trước đó nếu
     * cần thiết. Sau đó, hàm gọi phương thức repaint() để vẽ lại
     *
     * @param point
     */
    void actionClick(Point point) {
        // repaint();
        Point pointTemp = convertPoint(point);
        if (environment.updateMatrix(player, convertPointToMaxtrix(pointTemp))) {
            pointVector.addElement(point);
            undoManager.undoableEditHappened(new UndoableEditEvent(this,
                    new Return(point, pointVector)));

            repaint();
            // drawImg(player, convertPointToCaro(point));
            player = !player;
            setStatus();
            if (environment.getWin() > 0) {
                winer = environment.getWin();
            }
        }
    }

    /**
     * Hàm undo() được sử dụng trong ứng dụng Caro để hoàn tác (undo) lần đánh
     * trước đó. Khi người dùng chọn lệnh undo, hàm này sẽ được gọi để hoàn tác
     * lại nước cờ cuối cùng và hiển thị lại trạng thái bàn cờ trước khi người
     * dùng đánh nước đó. Đảo lượt chơi giữa hai người chơi. Lấy ra điểm cuối
     * cùng trong danh sách các điểm đã đánh (pointVector). Chuyển đổi điểm đó
     * sang dạng ma trận bằng cách sử dụng phương thức convertPointToMaxtrix và
     * gán cho biến point. Hoàn tác lại bàn cờ ở vị trí của điểm point bằng cách
     * sử dụng phương thức undoMatrix. Gọi phương thức undo() của undoManager để
     * hoàn tác lại thao tác của người dùng. Cập nhật trạng thái của ứng dụng
     * bằng phương thức setStatus. Vẽ lại bàn cờ bằng phương thức repaint.
     */
    public void undo() {
        try {
            player = !player;
            Point point = pointVector.get(pointVector.size() - 1);
            point = convertPointToMaxtrix(convertPoint(point));
            environment.undoMatrix(point);
            undoManager.undo();
            setStatus();
            repaint();

        } catch (Exception e) {
            //Bắt ngoại lệ và in thông báo
            JFrame frame = new JFrame("JOprionPane showMessageDialog example");
            JOptionPane.showMessageDialog(frame, "Nothing to undo!", "Notification", JOptionPane.INFORMATION_MESSAGE);  
        }
    }

    public boolean canUndo() {
        return undoManager.canUndo();
    }
}
