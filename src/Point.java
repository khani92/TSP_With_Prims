/**
 * This class holds the x and y coordinates from the use input
 * @author Nikhil
 *
 */
public class Point {

	private double x;
	private double y;

	Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double distance(Point obj) {

		return (Math.sqrt(Math.pow((this.x - obj.x), 2)
				+ Math.pow((this.y - obj.y), 2)));
	}

	public static double distance(Point obj1, Point obj2) {
		return (Math.sqrt(Math.pow((obj1.x - obj2.x), 2)
				+ Math.pow((obj1.y - obj2.y), 2)));
	}

	public static void main(String[] args) {

		Point p1 = new Point(2, 3);
		Point p2 = new Point(3, 2);
		System.out.println(p1.distance(p2));
		System.out.println(distance(p1, p2));
	}
}
