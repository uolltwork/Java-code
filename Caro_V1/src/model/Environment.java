package model;

import java.awt.Point;
import view.CaroGraphics;

/**
 * CSD201_Ass - Topic: Game Caro - Group 02
 */
public class Environment {

    private int matrix[][];
    private int win = 0;

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public Environment() {
        matrix = new int[CaroGraphics.row + 2][CaroGraphics.col + 2];
    }

    /**
     * updateMatrix nhận hai tham số - một giá trị
     * boolean useCross và một đối tượng Điểm có tên là điểm. Nó trả về một giá
     * trị boolean.
     *
     * Bên trong phương thức, biến row được gán giá trị point.x + 1 và biến col
     * được gán giá trị point.y + 1. Biến player được gán giá trị 1 nếu useCross
     * là true và 2 nếu useCross là false.
     *
     * Sau đó, một vòng lặp được thực hiện trong đó phương thức
     * System.out.println() được gọi lặp lại, với số lần bằng với giá trị của
     * CaroGraphics.row.
     *
     * Sau đó, phương thức này sẽ kiểm tra xem giá trị của matrix[row][col] có
     * bằng 0 hay không. Nếu đúng như vậy, giá trị của player được gán cho
     * matrix[row][col]. Nếu không, phương thức sẽ in "Lỗi!" và trả về false.
     *
     * Một vòng lặp khác được thực hiện trong đó hai biến i và j được sử dụng để
     * lặp qua mảng ma trận. Các giá trị của từng phần tử trong mảng ma trận
     * được in ra bàn điều khiển.
     *
     * Phương thức checkWin sau đó được gọi với các giá trị của hàng và col làm
     * tham số và kết quả được lưu trữ trong biến win.
     *
     * Cuối cùng trả về true
     *
     * @param useCross
     * @param point
     * @return
     */
    public boolean updateMatrix(boolean useCross, Point point) {
        int row = point.x + 1;
        int col = point.y + 1;
        short player = (short) (useCross ? 1 : 2);

        for (int i = 0; i < CaroGraphics.row; i++) {
            System.out.println();
        }

        if (matrix[row][col] == 0) {
            matrix[row][col] = player;
        } else {
            System.out.println("Error!");
            return false;
        }

        for (int i = 1; i < CaroGraphics.row - 1; i++) {
            for (int j = 1; j < CaroGraphics.col - 1; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        win = checkWin(row, col);
        return true;
    }

    public void undoMatrix(Point point) {
        int row = point.x + 1;
        int col = point.y + 1;
        matrix[row][col] = 0;
    }

    /**
     * kiểm tra xem người chơi đã chiến thắng hay chưa. Phương thức
     * này nhận đầu vào là hàng (row) và cột (col) của một ô trên bàn cờ, và trả
     * về giá trị là 0 nếu không có chiến thắng và trả về giá trị của người chơi
     * (1 hoặc 2) nếu người chơi đó đã chiến thắng. Phương thức này sử dụng một
     * mảng 2 chiều để lưu trữ tất cả các hướng có thể đi trên bàn cờ và tìm
     * kiếm trong các hướng đó để đếm số lượng ô liên tiếp có giá trị giống nhau
     * và trả về giá trị của người chơi nếu số lượng ô liên tiếp đạt đủ 5. 
     *
     * @param row
     * @param col
     * @return
     */
    private int checkWin(int row, int col) {
        int[][] rc = {{0, -1, 0, 1}, {-1, 0, 1, 0}, {1, -1, -1, 1},
        {-1, -1, 1, 1}};
        int i = row, j = col;
        for (int direction = 0; direction < 4; direction++) {
            int count = 0;
            System.out.println("[" + direction + "]-" + "[" + row + "," + col
                    + "]  ");

            i = row;
            j = col;
            while (i > 0 && i < matrix.length && j > 0 && j < matrix.length
                    && matrix[i][j] == matrix[row][col]) {
                count++;
                if (count == 5) {
                    return matrix[row][col];
                }
                System.out.print("\t[" + i + "," + j + "]  ");
                i += rc[direction][0];
                j += rc[direction][1];
                System.out.println("--->[" + i + "," + j + "]  ");
            }
            System.out.println("\tcount1 : " + count);

            count--;
            i = row;
            j = col;
            while (i > 0 && i < matrix.length && j > 0 && j < matrix.length
                    && matrix[i][j] == matrix[row][col]) {
                count++;
                if (count == 5) {
                    return matrix[row][col];
                }
                System.out.print("\t[" + i + "," + j + "]  ");
                i += rc[direction][2];
                j += rc[direction][3];
                System.out.println("--->[" + i + "," + j + "]  ");
            }
            System.out.println("\tcount : " + count);
        }
        return 0;
    }
}
