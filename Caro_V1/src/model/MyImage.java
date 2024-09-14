package model;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import view.CaroGraphics;

/**
 * CSD201_Ass - Topic: Game Caro - Group 02
 * @author
 */

/**
 * Setup hình ảnh size cho quân cờ caro X và O
 */
public class MyImage {
	private String urlImage = "/images/";
	public Image imgCross;
	public Image imgNought;

	public MyImage() {
		int size = CaroGraphics.sizeCell - 2;
		imgCross = reSizeImage(getMyImageIcon("crossC.png"), size, size);
		imgNought = reSizeImage(getMyImageIcon("nought.gif"), size, size);
	}

	public Image reSizeImage(Image image, int width, int height) {
		image = new ImageIcon(image.getScaledInstance(width, height,
				imgCross.SCALE_SMOOTH)).getImage();
		return image;
	}

	public Image getMyImageIcon(String nameImageIcon) {
		Image i = new ImageIcon(getClass().getResource(
				urlImage + nameImageIcon)).getImage();
		return i;
	}
}
