package pieces;
public class Bishop extends ChessPiece{
	public Bishop(String color){
		COLOR = color.toUpperCase();
		RANGE = -1;
		TYPE="BISHOP";	
	}
	public String toString(){
		return COLOR + "Bi";
	}

}
