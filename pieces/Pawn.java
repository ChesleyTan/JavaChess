package pieces;
public class Pawn extends ChessPiece{
	private boolean hasMoved = false;
	public Pawn(String color){
		super("PAWN", color, 2);
	}
	public boolean validMovement(int myXCoor, int myYCoor, int targXCoor, int targYCoor){
		int offset = -1; //Can only move forward
		if (COLOR.equals("B"))
			offset = 1;
		if (myYCoor + offset == targYCoor && myXCoor == targXCoor)
			return true;
		else{
			return false;
		}
	}
	public boolean validAttack(int myXCoor, int myYCoor, int targXCoor, int targYCoor){
		int offset = -1; //Can only move forward
		if (COLOR.equals("B"))
			offset = 1;
		if (myYCoor + offset == targYCoor && Math.abs(myXCoor - targXCoor) == 1)
			return true;
		else{
			return false;
		}
	} 
	public boolean getHasMoved(){ return hasMoved; }
	public String toString(){
		return COLOR + "-" + "P";
	}
}
