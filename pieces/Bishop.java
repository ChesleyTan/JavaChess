package JavaChess.pieces;
import JavaChess.Board;
public class Bishop extends ChessPiece{
	public Bishop(String color){
		super("BISHOP", color, -1, new double[] {1d,-1d});
	}
	private Bishop(String type, String color, int range, double[] attack_slopes, boolean has_moved){
	    super(type, color, range, attack_slopes, has_moved);
    }
	public boolean validMovement(int myXCoor, int myYCoor, int targXCoor, int targYCoor, Board board){
		if (Math.abs(super.getSlope(myXCoor,myYCoor,targXCoor,targYCoor)) == 1.0){
			if (myXCoor < targXCoor && myYCoor < targYCoor){
				for (int i = myXCoor + 1,u = myYCoor + 1;i<targXCoor && u<targYCoor;u++,i++){
					if (!board.isEmpty(i,u))
						return false;
				}
				return true;
			}	
			else if (myXCoor > targXCoor && myYCoor > targYCoor){
				for (int i = myXCoor - 1,u = myYCoor - 1;i>targXCoor && u>targYCoor;u--,i--){
					if (!board.isEmpty(i,u))
						return false;
				}
				return true;
			}
			else if (myXCoor < targXCoor && myYCoor > targYCoor){
				for (int i = myXCoor + 1,u = myYCoor - 1;i<targXCoor && u>targYCoor;u--,i++){
					if (!board.isEmpty(i,u))
						return false;
				}	
				return true;
			}	
			else if (myXCoor > targXCoor && myYCoor < targYCoor){
				for (int i = myXCoor - 1,u = myYCoor + 1;i>targXCoor && u<targYCoor;u++,i--){
					if (!board.isEmpty(i,u))
						return false;
				}	
				return true;
			}
		}
		return false;
	}
	public boolean validAttack(int myXCoor, int myYCoor, int targXCoor, int targYCoor, Board board){
		return validMovement(myXCoor,myYCoor,targXCoor,targYCoor,board);
	} 
	public String toString(){
		return COLOR + "-" + "Bi";
	}
    
	public Bishop copy() {
		return new Bishop(TYPE, COLOR, RANGE, ATTACK_SLOPES, hasMoved);
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
