package pieces;
public class Knight extends ChessPiece{
	public Knight(String color){
		COLOR = color.toUpperCase();
		RANGE = 2;
		TYPE="KNIGHT";	
	}
	public String toString(){
		return COLOR + "Kn";
	}

}
