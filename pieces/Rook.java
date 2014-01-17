package JavaChess.pieces;
import JavaChess.Board;
public class Rook extends ChessPiece{
	public Rook(String color){
		super("ROOK", color, -1, new double[] {0d,10d,-10d});
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
		return COLOR + "-" + "Rk";
	}

}
