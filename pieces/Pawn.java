package JavaChess.pieces;
import JavaChess.Board;
public class Pawn extends ChessPiece{
	private boolean lastPawnJump = false; // Keeps track of whether this Pawn last advanced two spaces, leaving it vulnerable to an En Passant
	public Pawn(String color){
		super("PAWN", color, 2, new double[] {1d,-1d});
	}
	private Pawn(String type, String color, int range, double[] attack_slopes, boolean has_moved, boolean last_pawn_pump){
	    super(type, color, range, attack_slopes, has_moved);
	    lastPawnJump = last_pawn_pump;
    }
	public boolean validMovement(int myXCoor, int myYCoor, int targXCoor, int targYCoor, Board board){
		int offset = 0;
		if (!hasMoved){ // Allow moving 2 spaces on first move
			offset = -2; // Only allows moving forward
		}
		else{
			offset = -1; // Only allows moving forward
		}
		if (COLOR.equals("B"))
			offset = -1 * offset;
		if (Math.abs(offset) == 2){
			if (myYCoor + offset == targYCoor && myXCoor == targXCoor){
				if (!board.isEmpty(myXCoor,myYCoor + (offset / 2))){
					System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nInvalid Move: Path Blocked.");
					return false;
				}
				return true;
			}
			else if (myYCoor + (offset / 2) == targYCoor && myXCoor == targXCoor){
				return true;
			}
		}
		else if (Math.abs(offset) == 1){
			if (myYCoor + offset == targYCoor && myXCoor == targXCoor){
				return true;
			}
		}
		return false;
	}
	public boolean validAttack(int myXCoor, int myYCoor, int targXCoor, int targYCoor, Board board){
		int offset = -1; //Can only move forward
		if (COLOR.equals("B"))
			offset = 1;
		if (myYCoor + offset == targYCoor && Math.abs(myXCoor - targXCoor) == 1)
			return true;
		else{
			return false;
		}
	} 
	public boolean getLastPawnJump(){
		return lastPawnJump;
	}
	public void setLastPawnJump(boolean boo){
		lastPawnJump = boo;
	}
	public String toString(){
		return COLOR + "-" + "P";
	}

	public Pawn copy() {
	    return new Pawn(TYPE, COLOR, RANGE, ATTACK_SLOPES, hasMoved, lastPawnJump);
	} 

	public boolean equals(ChessPiece p) {
	    if (p.TYPE != TYPE ||
	            p.COLOR != COLOR ||
	            p.RANGE != RANGE ||
	            p.ATTACK_SLOPES.length != ATTACK_SLOPES.length ||
	            p.hasMoved != hasMoved ||
	            ((Pawn)p).lastPawnJump != lastPawnJump) 
	    {
	        return false;            
	    }
	    for (int i = 0;i < ATTACK_SLOPES.length;i++) {
	        if (ATTACK_SLOPES[i] != p.ATTACK_SLOPES[i]) {
	            return false;
	        }
	    }
	    return true;
	}
}
