package JavaChess.pieces;
import JavaChess.Board;

public abstract class ChessPiece{
	protected final String TYPE;
	protected final String COLOR;
	protected final int RANGE;
	protected double[] ATTACK_SLOPES;
	protected boolean isPinned = false;
	protected boolean hasMoved = false;
	protected ChessPiece(String type, String color, int range, double[] attack_slopes){
		TYPE = type.toUpperCase();
		COLOR = color.toUpperCase();
		RANGE = range;
		ATTACK_SLOPES = attack_slopes;
	}
	public String getType(){ return TYPE; }
	public String getColor(){ return COLOR; }
	public int getRange(){ return RANGE; }
	public double[] getAttackSlopes(){ return ATTACK_SLOPES; }
	public boolean getIsPinned(){ return isPinned; }
	public boolean hasMoved(){ return hasMoved; }
	public void toggleHasMoved(){ hasMoved = true; } 
	public abstract boolean validMovement(int myXCoor, int myYCoor, int targXCoor, int targYCoor, Board board); // Checks to see if the piece can naturally make such a movement
	public abstract boolean validAttack(int myXCoor, int myYCoor, int targXCoor, int targYCoor, Board board); // Checks to see if the piece can naturally make such a movement
	public abstract String toString();
	public double getSlope(int myXCoor, int myYCoor, int targXCoor, int targYCoor){ // Helper method for the validMovement() method
		if (myXCoor == targXCoor){
			return 10.0; // return of 10 signifies an undefined slope, or a vertical movement
		}
		else{
			return (myYCoor - targYCoor * 1.0) / (myXCoor - targXCoor);
		}
	}

}
