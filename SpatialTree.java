import java.awt.geom.Point2D;
import java.util.ArrayList;


public class SpatialTree{
	
   private int mySize;
   private Node myRoot;
   
   public SpatialTree()
   {
	   mySize = 0;
	   myRoot = null;
   }
   
   
   

   public void add( Point2D val ) {
	   if ( this.isEmpty() ) {
		    addRoot( val );
	   }
	   else {
		   insertHelper( val, myRoot );
	   }
   }
   
    public Location<Point2D> addRoot( Point2D val )
    {
	   if ( myRoot != null )
	   {
		   throw new IllegalStateException( "Tree is Not Empty! Cannot add Root!" );
	   }
	   // true because first node is x node
	   myRoot = new Node( val, null, true );
	   mySize++;
	   return myRoot;
   }
   
   private void insertHelper( Point2D val, Location<Point2D> loc ) {
	   Node theNode = validateLocation( loc );
	 
	   // compare x or y values
	   if(theNode.isXNode) {
		   xNodeHelper(theNode, val);
	   } else {
		   yNodeHelper(theNode, val);
	   }
	   
   }
   
   private Node validateLocation( Location<Point2D> loc )
   {
	   if (( loc == null ) || !( loc instanceof Node ))
	   {
		   throw new IllegalArgumentException( "Not a Valid Location! ");
	   }
	   return (Node) loc;
   }
   private void xNodeHelper(Node n, Point2D val) {
	   if ( val.getX() <= n.getValue().getX()) {
		   if ( n.myLeftChild == null ) {
			   addLeftChild( val, n );
		   }
		   else {
			   insertHelper( val, n.myLeftChild );
		   }
	   }
	   else {
		   if ( n.myRightChild == null ) {
			   addRightChild( val, n );
		   }
		   else {
			   insertHelper( val, n.myRightChild );
		   }
	   }
   }
   
   private void yNodeHelper(Node n, Point2D val) {
	   if ( val.getY() <= n.getValue().getY()) {
		   if ( n.myLeftChild == null ) {
			   addLeftChild( val, n );
		   }
		   else {
			   insertHelper( val, n.myLeftChild );
		   }
	   }
	   else {
		   if ( n.myRightChild == null ) {
			   addRightChild( val, n );
		   }
		   else {
			   insertHelper( val, n.myRightChild );
		   }
	   }
   }
   
  
   public Location<Point2D> addLeftChild( Point2D val, Location<Point2D> loc ) 
   {
	   Node n = validateLocation( loc );
	   if ( n.myLeftChild != null )
	   {
		   throw new IllegalStateException( "Left Child already exists!" );
	   }
	   n.myLeftChild = new Node( val, n, !n.isXNode );
	   mySize++;
	   return n.myLeftChild;
   }
   
   public Location<Point2D> addRightChild( Point2D val, Location<Point2D> loc ) 
   {
	   Node n = validateLocation( loc );
	   if ( n.myRightChild != null )
	   {
		   throw new IllegalStateException( "Right Child already exists!" );
	   }
	   n.myRightChild = new Node( val, n, !n.isXNode );
	   mySize++;
	   return n.myRightChild;
   }
   
   public boolean isRoot( Location<Point2D> loc ) {
	   Node n = validateLocation( loc );
	   return n == myRoot;
	  
   }
   
   public int size() {
	   return mySize;
   }
   
   public boolean isEmpty() {
	   return mySize == 0;
   }
   
   public boolean isLeaf( Location<Point2D> loc ) {
	   Node n = validateLocation( loc );
	   return ((n.myLeftChild == null) && (n.myRightChild == null));
   }
   
   public boolean isInternal( Location<Point2D> loc ) {
	   Node n = validateLocation( loc );
	   return ! isLeaf( n );
   }
  
   public void draw() {
	   StdDraw.setXscale(0, 512);
	   StdDraw.setYscale(0,512);
	   drawHelper(this.myRoot);
   }
   private void drawHelper(Node node) {
	   
	   	if(node.isXNode) {
	   		xDrawHelper(node);
	   	} else {
	   		yDrawHelper(node);
	   	}
	   	if(node.myLeftChild != null) {
	   		drawHelper(node.myLeftChild);
	   	}
	   	if(node.myRightChild != null) {
	   		drawHelper(node.myRightChild);
	   	}
   }
   private void xDrawHelper(Node n) {
	   // default value for if node is root
	   Point2D bounds = lineHelper(n);
	   // left bound
	   double lowerBound = bounds.getX();
	   // right bound;
	   double upperBound = bounds.getY();
	   
	   Point2D point = n.myValue;
	   StdDraw.setPenColor();
	   StdDraw.setPenRadius(0.01);
	   StdDraw.point(point.getX(), point.getY());
	   StdDraw.setPenRadius();
	   StdDraw.setPenColor(StdDraw.RED);
	   StdDraw.line(point.getX(), lowerBound, point.getX(), upperBound);
   }
   private void yDrawHelper(Node n) {
	// default value for if node is root
	   Point2D bounds = lineHelper(n);
	   // left bound
	   double lowerBound = bounds.getX();
	   // right bound;
	   double upperBound = bounds.getY();
	   Point2D point = n.myValue;
	   StdDraw.setPenColor();
	   StdDraw.setPenRadius(0.01);
	   StdDraw.point(point.getX(), point.getY());
	   StdDraw.setPenRadius();
	   StdDraw.setPenColor(StdDraw.BLUE);
	   StdDraw.line(lowerBound, point.getY(), upperBound, point.getY());
   }
   
   private Point2D lineHelper(Node n) {
	   double upperBound = 512;
	   double lowerBound = 0;
	  
	   Node traverser = n;
	   if(n.myParent !=  null) {
		   Node trailer = n.myParent;
		   traverser = n.myParent;
		   if(n.isXNode) {
			   if(traverser.myValue.getY() > n.myValue.getY()) {
				   upperBound = traverser.myValue.getY();
				   for(int i = 1; traverser.myParent!= null; i++ ) {
					   traverser = traverser.myParent;
					   if(i % 2 == 0) {
						   if(!traverser.isXNode && traverser.myValue.getY() < trailer.myValue.getY() && traverser.myValue.getY() < n.myValue.getY()) {
						   lowerBound = traverser.myValue.getY();
						   break;
						   }
					   }
				   }
				   
			   } else {
				   lowerBound = traverser.myValue.getY();
				   for(int i = 1; traverser.myParent!= null; i++) {
					   if(i % 2 == 0) {
						   traverser = traverser.myParent;
						   if(!traverser.isXNode && traverser.myValue.getY() > trailer.myValue.getY() && traverser.myValue.getY() > n.myValue.getY()) {
						   upperBound = traverser.myValue.getY();
						   break;
						   }
					   }
				   }
			   }
			   
		   } else {
			   if(traverser.myValue.getX() > n.myValue.getX()) {
				   upperBound = traverser.myValue.getX();
				   for(int i = 1; traverser.myParent!= null; i++) {
					   if(i % 2 == 0) {
						   traverser = traverser.myParent;
						   if(traverser.isXNode && traverser.myValue.getX() < trailer.myValue.getX() && traverser.myValue.getX() < n.myValue.getX()) {
						   lowerBound = traverser.myValue.getX();
						   break;
						   }
					   }
				   }
			   } else {
				   lowerBound = traverser.myValue.getX();
				   for(int i = 1; traverser.myParent!= null; i++) {
					   traverser = traverser.myParent;
					   if(i % 2 == 0) {
						   if(traverser.isXNode && traverser.myValue.getX() > trailer.myValue.getX() && traverser.myValue.getX() > n.myValue.getX()) {
						   upperBound = traverser.myValue.getX();
						   break;
						   }
					   }
				   }
			   }
			   
		   }
		   
		   
	   }
	   
	   return new Point2D.Double(lowerBound, upperBound);
   }
   
   
   public ArrayList<Point2D> query(Point2D center, double radius) { 
	   return queryHelper(this.myRoot, center, radius);
   }
   
   private ArrayList<Point2D> queryHelper(Node n, Point2D center, double radius) {
	   ArrayList<Point2D> points = new ArrayList<Point2D>();
	
	   double d = 0;
	   if(n.isXNode) {
		   d = n.myValue.getX() - center.getX();
	   } else {
		   d = n.myValue.getY() - center.getY();
	   }
	   if ( Math.abs(d) < radius) {
		   if(center.distance(n.myValue) < radius) {
			  points.add(n.myValue);  
		   }
		   if(n.myLeftChild!= null) {
			   points.addAll(queryHelper(n.myLeftChild, center, radius));
		   }
		   if(n.myRightChild!= null) {
			   points.addAll(queryHelper(n.myRightChild, center, radius));
		   }
	   } else {
		   if(n.myLeftChild!= null && d > 0 ) {
			   points.addAll(queryHelper(n.myLeftChild, center, radius));
		   }
		   if(n.myRightChild!= null && d < 0) {
			   points.addAll(queryHelper(n.myRightChild, center, radius));
		   }
	   }
	   return points;
	  
   }
   
   public String toString()
   {
	   StringBuilder sb = new StringBuilder();
	   toString( myRoot, sb, 0 );
	   return sb.toString();
   }
   
   private void toString( Node loc, StringBuilder sb, int level )
   {
	   if ( loc != null ) 
	   {
		   for (int i=0; i < 2 * level; i++ ) 
		   {
			   sb.append( " " );
		   }
		   sb.append( loc.myValue + "\n" );
		   toString( loc.myLeftChild, sb, level + 1 );
		   toString( loc.myRightChild, sb, level + 1);
	   }
   }
   
   private static class Node implements Location<Point2D> 
   {
	   private Point2D myValue;
	   private boolean isXNode;
	   private Node myParent;
	   private Node myLeftChild;
	   private Node myRightChild;
	   
	   public Node ( Point2D val, Node parent, boolean isXNode ) 
	   {
		  
		   myValue = val;
		   this.isXNode = isXNode;
		   myParent = parent;
		   myLeftChild = null;
		   myRightChild = null;
		   
	   }
	   
	   public Point2D getValue() 
	   {
		   return myValue;
	   }
	   
	   public String toString()
	   {
		   return myValue.toString();
	   }
	   

	
   }
}


