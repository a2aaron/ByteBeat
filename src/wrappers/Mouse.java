package wrappers;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

public class Mouse {
	PointerInfo a;
	Point cursor;
	public Mouse() {
		cursor = MouseInfo.getPointerInfo().getLocation();
	}
	
	public int getX() {
		return (int) cursor.getX();
	}
	
	public int getY() {
		return (int) cursor.getY();
	}
}
