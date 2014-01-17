package JavaChess.pieces;
import JavaChess.Board;
public class Bishop extends ChessPiece{
	public Bishop(String color){
		super("BISHOP", color, -1, new double[] {1d,-1d});
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

}
