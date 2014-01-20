package JavaChess;
// Class Line
// Stores linear equations in slope-intercept form
// and evaluates an intersection between two lines
public class Line{
	private double _y,_x,_c;
	// Accessor and mutator methods
	public double getYCoefficient() { return _y; }
	public double getSlope(){ return _x; }
	public double getConstant(){ return _c; }
	public void setYCoefficient(int y) { _y = y; }
	public void setSlope(int x) { _x = x; }
	public void setConstant(int c) { _c = c; }

	// Default constructor
	public Line(){
		_y = 0;
		_x = 0;
		_c = 0;
	}

	// Overloaded constructer that creates a line given coefficients in slope-intercept form 
	public Line(double y, double x, double c){
		_y = y;
		_x = x;
		_c = c;
	}

	// Overloaded constructor that creates a line with a point and a slope
	public Line(double slope, int xCoor, int yCoor){
		if (slope == Double.POSITIVE_INFINITY || slope == Double.NEGATIVE_INFINITY){
			_y = 0;
			_x = 1;
			_c = -1 * xCoor;
		}
		else{
			_y = 1;
			_x = slope;
			_c = (-1 * slope * xCoor) + yCoor;
		}
	}

	// Overloaded constructor to create a vertical line
	public Line(double xCoor){
		_y = 0;
		_x = 1;
		_c = -1 * xCoor;
	}

	// Overloaded constructor that creates a line with two points
	public Line(int x1, int y1, int x2, int y2){
		this((1.0 * y2 - y1) / (1.0 * x2 - x1), x1, y1);
	}

	// Method to convert equation simplest form
	public void convertToSlopeIntersect(){
		if (_y == 0){
			if (_x != 1){
				_c /= _x;
				_x = 1;
			}
		}
		else if (_y != 1){
			_x /= _y; 
			_c /= _y; 
			_y = 1; 
		}
	}

	// Method to get the intersection point of this Line and another Line
	// Returns answer in double[] of [double xCoordinate, double yCoordinate]
	public double[] getIntersection(Line l){
		double [] retArr = new double[2];
		if ((_x == l.getSlope() && _y == l.getYCoefficient()) || (_y == 0 && l.getYCoefficient() == 0)){
			return null;
		}
		else{
			convertToSlopeIntersect();
			l.convertToSlopeIntersect();

			if (l.getYCoefficient() == 0){
				retArr[0] = -1 * l.getConstant();
				retArr[1] = _x * retArr[0] + _c;
				return retArr;
			}
			else if (_y == 0){
				retArr[0] = -1 * _c;
				retArr[1] = l.getSlope() * retArr[0] + l.getConstant();
				return retArr;
			}
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

	// Method to print equation in human-readable form
	public String printEquation(){
		return _y + "y = " + _x + "x + " + _c;
	}

	// Overridden toString()
	public String toString(){
		return printEquation();
	}
}
