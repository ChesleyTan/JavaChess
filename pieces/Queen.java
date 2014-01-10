package pieces;
public class Queen extends ChessPiece{
	public Queen(String color){
		COLOR = color.toUpperCase();
		RANGE = -1;
		TYPE="QUEEN";	
	}
	public String toString(){
		return COLOR + "Q";
	}

}
