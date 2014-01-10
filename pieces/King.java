package pieces;
public class King extends ChessPiece{
	private boolean isChecked;
	public King(String color){
		COLOR = color.toUpperCase();
		RANGE = 1;
		TYPE="KING";	
	}
	public boolean getIsChecked(){ return isChecked; }
	public String toString(){
		return COLOR + "K";
	}
}
