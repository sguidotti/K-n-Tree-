

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;


public class TestDriver {
	
	public static void main(String[] args) {
		SpatialTree tree = new SpatialTree();
		Random r = new Random();
		for(int i =  0; i < 50; i++) {
			tree.add(new Point2D.Double(r.nextInt(500), r.nextInt(500)));
		}
		
		
		
		
		StdDraw.clear();
		StdDraw.setCanvasSize(512, 512);
		for(;;) {
		// check if the user is currently clicking
		boolean currentClickState = StdDraw.isMousePressed();
		tree.draw();
		
		// location and their current click location
		if(currentClickState) {
			// get current location of the click
			double currentClickX = StdDraw.mouseX();
			double currentClickY = StdDraw.mouseY();
		
			StdDraw.clear();
			StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
			StdDraw.filledCircle(currentClickX, currentClickY, 50);
			ArrayList<Point2D> points = tree.query(new Point2D.Double(currentClickX,currentClickY), 50);
			for(Point2D point:points) {
				StdDraw.setPenColor(StdDraw.YELLOW);
				StdDraw.setPenRadius(0.03);
				StdDraw.point(point.getX(), point.getY());
				
			} 
			
			
		}
		}
	}
	
}
