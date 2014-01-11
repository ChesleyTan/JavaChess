package pieces;

public abstract class ChessPiece{
	protected final String TYPE;
	protected final String COLOR;
	protected final int RANGE;
	protected boolean isPinned = false;
	protected ChessPiece(String type, String color, int range){
		TYPE = type.toUpperCase();
		COLOR = color.toUpperCase();
		RANGE = range;
	}
	public boolean getIsPinned(){ return isPinned; }
	public abstract boolean validMovement(int myXCoor, int myYCoor, int targXCoor, int targYCoor); // Checks to see if the piece can naturally make such a movement
	public abstract String toString();
	public double getSlope(int myXCoor, int myYCoor, int targXCoor, int targYCoor){ // Helper method for the validMovement() method
		if (myXCoor == targXCoor){
			return 10.0; // return of 10 signifies an undefined slope, or a vertical movement
		}
		else{
			return (myYCoor - targYCoor) / (myXCoor - targXCoor);
		}
	} 
}
