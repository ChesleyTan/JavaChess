public class Line{
	private double _y,_x,_c;
	public Line(double y, double x, double c){
		_y = y;
		_x = x;
		_c = c;
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
	public static void main(String[] args){
		Line l1 = new Line(1,0.5,-2);
		Line l2 = new Line(1,1,0);
		double[] ret;
		ret = l1.getIntersection(l2);
		if (ret != null){
			System.out.println(ret[0] + "," + ret[1]);
		}
		else{
			System.out.println(ret);
		}
	}
}
