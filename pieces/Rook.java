package pieces;
public class Rook extends ChessPiece{
	public Rook(String color){
		COLOR = color.toUpperCase();
		RANGE = -1;
		TYPE="ROOK";	
	}
	public String toString(){
		return COLOR + "Rk";
	}

}
