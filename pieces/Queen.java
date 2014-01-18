package JavaChess.pieces;
import JavaChess.Board;
public class Queen extends ChessPiece{
	public Queen(String color){
		super("QUEEN", color, -1, new double[] {1d,-1d,0d,10d,-10d});
	}
	public boolean validMovement(int myXCoor, int myYCoor, int targXCoor, int targYCoor, Board board){
		double slope = Math.abs(super.getSlope(myXCoor,myYCoor,targXCoor,targYCoor));
		if (slope == 0.0){
			if (myXCoor < targXCoor){
				for (int i = myXCoor + 1;i<targXCoor;i++){
					if (!board.isEmpty(i,myYCoor))
						return false;
				}
				return true;
			}
			else if (myXCoor > targYCoor){
				for (int i = myXCoor -1;i>targXCoor;i--){
					if (!board.isEmpty(i,myYCoor))
						return false;
				}
				return true;
			}
		}
		else if(slope == 1.0){
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
		else if (slope == 10.0){
			if (myYCoor > targYCoor){
				for (int i = myYCoor - 1;i>targYCoor;i--){
					if (!board.isEmpty(myXCoor,i))
						return false;
				}
				return true;
			}
			else if (myYCoor < targYCoor){
				for (int i = myYCoor + 1;i<targYCoor;i++){
					if (!board.isEmpty(myXCoor,i))
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
		return COLOR + "-" + "Q";
	}

}
