package pieces;
public class Pawn extends ChessPiece{
	private boolean hasMoved = false;
	public Pawn(String color){
		super("PAWN", color, 2);
	}
	public boolean validMovement(int myXCoor, int myYCoor, int targXCoor, int targYCoor){
		if (myYCoor <= targYCoor || myXCoor % 2 == targXCoor % 2)
			return false;
		else if (Math.abs(myYCoor - targYCoor) == 1 && Math.abs(myXCoor - targYCoor) == 1){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean getHasMoved(){ return hasMoved; }
	public String toString(){
		return COLOR + "K";
	}
}
