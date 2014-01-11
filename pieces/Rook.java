package pieces;
public class Rook extends ChessPiece{
	public Rook(String color){
		super("ROOK", color, -1);
	}
	public boolean validMovement(int myXCoor, int myYCoor, int targXCoor, int targYCoor){
		if ( !(myXCoor == targXCoor || myYCoor == targYCoor) ){
			return false;
		}
		else {
			return true;	
		}
	}
	public boolean validAttack(int myXCoor, int myYCoor, int targXCoor, int targYCoor){
		return validMovement(myXCoor,myYCoor,targXCoor,targYCoor);
	} 
	public String toString(){
		return COLOR + "-" + "Rk";
	}

}
