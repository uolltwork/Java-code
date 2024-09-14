package model;

import java.awt.Point;
import java.util.Vector;
import javax.swing.undo.AbstractUndoableEdit;

/**
 * CSD201_Ass - Topic: Game Caro - Group 02
 *
 * @author Lê Trung Uôl_CE160438
 */
/**
 * Return được sử dụng để xác định một thao tác có thể được hoàn tác (undoable
 * edit) trong ứng dụng Caro kế thừa từ lớp AbstractUndoableEdit, cung cấp các
 * phương thức để thực hiện hoàn tác và lặp lại thao tác trước đó.
 *
 * Lớp Return bao gồm hai thuộc tính là points và point. Thuộc tính points là
 * một vector chứa danh sách các điểm đã đánh trên bàn cờ, còn thuộc tính point
 * là một điểm cụ thể mà người dùng đã đánh.
 */
public class Return extends AbstractUndoableEdit {

    protected Vector points;
    protected Point point;

    public Return(Point p, Vector v) {
        points = v;
        point = p;
    }

    public String getPresentationName() {
        return "";
    }

    public void undo() {
        super.undo();
        if (point == null) {
            System.out.println("Nothing to undo!");
        }
        points.remove(point);
    }

    public void redo() {
        super.redo();
        points.add(point);
    }
}
