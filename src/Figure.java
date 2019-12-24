import java.awt.Color;
import java.awt.Point;
import java.util.Vector;

public class Figure {
	int check = 0;
	Color color; 
	int size = 10;
	int style = 0;
	int fill = 0;
	Point endP = null;
	public int getFill() {
		return fill;
	}

	Point startP = null;
	public void setEndP(Point endP) {
		this.endP = endP;
	}

	public void setStartP(Point startP) {
		this.startP = startP;
	}

	Vector<Point> forDraw;
	
	public Vector<Point> getForDraw() {
		return forDraw;
	}

	public void setForDraw(Vector<Point> forDraw) {
		this.forDraw = forDraw;
	}

	public Point getStartP() {
		return startP;
	}


	public Point getEndP() {
		return endP;
	}


	
	public int getCheck() {
		return check;
	}


	public Color getColor() {
		return color;
	}

	public int getSize() {
		return size;
	}


	public int getStyle() {
		return style;
	}

	Figure(int check, Color color, int size,int style,int fill,Point S,Point E){
		this.check = check;
		this.color = color;
		this.size = size ;
		this.style = style ;
		this.startP = S;
		this.endP = E;
		this.fill = fill;
	}

	Figure(int check, Color color, int size,int style,Vector A){
		this.check = check;
		this.color = color;
		this.size = size ;
		this.style = style ;
		this.forDraw = A;
	}
	
}
