// Class Line
// Stores linear equations in slope-intercept form
// and evaluates an intersection between two lines
public class Line{
	private double _y,_x,_c;
	// Overloaded constructer that creates a line given coefficients in slope-intercept form 
	public Line(double y, double x, double c){
		_y = y;
		_x = x;
		_c = c;
	}
	// Overloaded constructor that creates a line with a point and a slope
	public Line(double slope, int xCoor, int yCoor){
		_y = 1;
		_x = slope;
		_c = (-1 * slope * xCoor) + yCoor;
	}
	// Overloaded constructor that creates a line with two points
	public Line(int x1, int y1, int x2, int y2){
		this((1.0 * y2 - y1) / (1.0 * x2 - x1), x1, y1);
	}
	public double getYCoefficient() { return _y; }
	public double getSlope(){ return _x; }
	public double getConstant(){ return _c; }
	public void setYCoefficient(int y) { _y = y; }
	public void setSlope(int x) { _x = x; }
	public void setConstant(int c) { _c = c; }
	public void convertToSlopeIntersect(){
			if (_y != 1){
				_x /= _y; 
				_c /= _y; 
				_y = 1; 
			}
	}
	public double[] getIntersection(Line l){
		double [] retArr = new double[2];
		if ((_x == l.getSlope() && _y == l.getYCoefficient()) || (_y == 0 && l.getYCoefficient() == 0)){
			return null;
		}
		else{
			convertToSlopeIntersect();
			l.convertToSlopeIntersect();
			double xOfLHS = _x;
			double cOfLHS = _c;
			double xOfRHS = l.getSlope();
			double cOfRHS = l.getConstant();
			xOfLHS -= xOfRHS; // Move all x terms to LHS
			xOfRHS = 0; // Move all x terms to LHS
			cOfRHS -= cOfLHS; // Move all constant terms to RHS
			cOfLHS = 0; // Move all constant terms to RHS
			cOfRHS /= xOfLHS; // Solve for x by dividing on both sides
			xOfLHS = 1;  
			// x coordinate of intersection is found
			retArr[0] = cOfRHS;
			retArr[1] = _x * cOfRHS + _c;
		}
		return retArr;
	}
	public String printEquation(){
		return _y + "y = " + _x + "x + " + _c;
	}
	public String toString(){
		return printEquation();
	}
	//public static void main(String[] args){
	//	Line l1 = new Line(1d,0.5d,-2d);
	//	Line l2 = new Line(1d,1d,0d);
	//	double[] ret;
	//	ret = l1.getIntersection(l2);
	//	if (ret != null){
	//		System.out.println(ret[0] + "," + ret[1]);
	//	}
	//	else{
	//		System.out.println(ret);
	//	}
	//	Line l1 = new Line(4, 7, 3, 5);
	//	System.out.println(l1);
	//}
}
