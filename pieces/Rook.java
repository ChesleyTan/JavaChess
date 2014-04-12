package JavaChess.pieces;
import JavaChess.Board;
public class Rook extends ChessPiece{
	public Rook(String color){
		super("ROOK", color, -1, new double[] {0d,10d,-10d});
	}
	private Rook(String type, String color, int range, double[] attack_slopes, boolean has_moved){
	    super(type, color, range, attack_slopes, has_moved);
    }
	public boolean validMovement(int myXCoor, int myYCoor, int targXCoor, int targYCoor, Board board){
		if ( !(myXCoor == targXCoor || myYCoor == targYCoor) ){
			return false;
		}
		else {
			if (myXCoor == targXCoor){
				if (myYCoor < targYCoor){
					for (int i = myYCoor + 1;i<targYCoor;i++){
						if (!board.isEmpty(myXCoor,i))
							return false;
					}
				}
				else if (myYCoor > targYCoor){
					for (int i = myYCoor - 1;i>targYCoor;i--){
						if (!board.isEmpty(myXCoor,i))
							return false;
					}
				}
			}
			else if (myYCoor == targYCoor){
				if (myXCoor < targXCoor){
					for (int i = myXCoor + 1;i<targXCoor;i++){
						if (!board.isEmpty(i, myYCoor))
							return false;
					}
				}
				else if (myXCoor > targXCoor){
					for (int i = myXCoor - 1;i>targXCoor;i--){
						if (!board.isEmpty(i,myYCoor))
							return false;
					}
				}
				
			}
			return true;	
		}
	}
	public boolean validAttack(int myXCoor, int myYCoor, int targXCoor, int targYCoor, Board board){
		return validMovement(myXCoor,myYCoor,targXCoor,targYCoor,board);
	} 
	public String toString(){
		return COLOR + "-" + "R";
	}

	public Rook copy() {
		return new Rook(TYPE, COLOR, RANGE, ATTACK_SLOPES, hasMoved);
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
