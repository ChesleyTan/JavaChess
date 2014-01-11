package pieces;
public class Bishop extends ChessPiece{
	public Bishop(String color){
		super("BISHOP", color, -1);
	}
	public boolean validMovement(int myXCoor, int myYCoor, int targXCoor, int targYCoor){
		if (Math.abs(super.getSlope(myXCoor,myYCoor,targXCoor,targYCoor)) == 1.0)
			return true;
		else
			return false;
	}
	public boolean validAttack(int myXCoor, int myYCoor, int targXCoor, int targYCoor){
		return validMovement(myXCoor,myYCoor,targXCoor,targYCoor);
	} 
	public String toString(){
		return COLOR + "-" + "Bi";
	}

}
