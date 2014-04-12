package JavaChess.pieces;
import JavaChess.Board;
public class Knight extends ChessPiece{
	public Knight(String color){
		super("KNIGHT", color, 2, new double[] {0.5d,-0.5d,2d,-2d});
	}
	private Knight(String type, String color, int range, double[] attack_slopes, boolean has_moved){
	    super(type, color, range, attack_slopes, has_moved);
    }
	public boolean validMovement(int myXCoor, int myYCoor, int targXCoor, int targYCoor, Board board){
		double slope = Math.abs(super.getSlope(myXCoor,myYCoor,targXCoor,targYCoor));
		if ((slope == 2.0 || slope == 0.5) && (Math.abs(myXCoor - targXCoor) == 1 || Math.abs(myYCoor - targYCoor) == 1))
			return true;
		else
			return false;
	}
	public boolean validAttack(int myXCoor, int myYCoor, int targXCoor, int targYCoor, Board board){
		return validMovement(myXCoor,myYCoor,targXCoor,targYCoor,board);
	} 
	public String toString(){
		return COLOR + "-" + "N";
	}

	public Knight copy() {
		return new Knight(TYPE, COLOR, RANGE, ATTACK_SLOPES, hasMoved);
	} 

	public boolean equals(ChessPiece p) {
	    if (p.TYPE != TYPE ||
	            p.COLOR != COLOR ||
	            p.RANGE != RANGE ||
	            p.ATTACK_SLOPES.length != ATTACK_SLOPES.length ||
	            p.hasMoved != hasMoved)
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
