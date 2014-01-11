package pieces;
public class Queen extends ChessPiece{
	public Queen(String color){
		super("QUEEN", color, -1);
	}
	public boolean validMovement(int myXCoor, int myYCoor, int targXCoor, int targYCoor){
		double slope = Math.abs(super.getSlope(myXCoor,myYCoor,targXCoor,targYCoor));
		if (slope == 0.0 || slope == 1.0 || slope == 10.0)
			return true;	
		else
			return false;
	}
	public boolean validAttack(int myXCoor, int myYCoor, int targXCoor, int targYCoor){
		return validMovement(myXCoor,myYCoor,targXCoor,targYCoor);
	} 
	public String toString(){
		return COLOR + "-" + "Q";
	}

}
