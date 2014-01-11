package pieces;
public class King extends ChessPiece{
	private boolean isChecked;
	public King(String color){
		super("KING", color, 1);
	}
	public boolean validMovement(int myXCoor, int myYCoor, int targXCoor, int targYCoor){
		double slope = Math.abs(super.getSlope(myXCoor,myYCoor,targXCoor,targYCoor));
		if ((slope == 0.0 || slope == 1.0 || slope == 10.0) && (Math.abs(myXCoor - targXCoor) == 1 || Math.abs(myYCoor - targYCoor) == 1))
			return true;
		else
			return false;
	}
	public boolean getIsChecked(){ return isChecked; }
	public String toString(){
		return COLOR + "-" + "K";
	}
}
