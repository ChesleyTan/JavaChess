package pieces;
public class Knight extends ChessPiece{
	public Knight(String color){
		super("KNIGHT", color, 2);
	}
	public boolean validMovement(int myXCoor, int myYCoor, int targXCoor, int targYCoor){
		double slope = Math.abs(super.getSlope(myXCoor,myYCoor,targXCoor,targYCoor));
		if ((slope == 2.0 || slope == 0.5) && (Math.abs(myXCoor - targXCoor) == 1 || Math.abs(myYCoor - targYCoor) == 1))
			return true;
		else
			return false;
	}
	public String toString(){
		return COLOR + "-" + "Kn";
	}

}
