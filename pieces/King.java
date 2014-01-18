package JavaChess.pieces;
import JavaChess.Board;
import java.util.ArrayList;
public class King extends ChessPiece{
	private boolean isChecked = false;
	private ArrayList<int[]> checkedBy = new ArrayList<int[]>();
	public void addCheckedBy(int[] arr){
		checkedBy.add(arr);
	}
	public ArrayList<int[]> getCheckedBy(){
		return checkedBy;
	}
	public void clearCheckedBy(){
		ArrayList<int[]> newArr = new ArrayList<int[]>();
		checkedBy = newArr;
	}
	public boolean isChecked(){ return isChecked; }
	public void setIsChecked(boolean checked) { isChecked = checked; }
	public King(String color){
		super("KING", color, 1, new double[] {1d, -1d, 0d, 10d, -10d});
	}
	public boolean validMovement(int myXCoor, int myYCoor, int targXCoor, int targYCoor, Board board){
		double slope = Math.abs(super.getSlope(myXCoor,myYCoor,targXCoor,targYCoor));
		if ((slope == 0.0 || slope == 1.0 || slope == 10.0) && (Math.abs(myXCoor - targXCoor) == 1 || Math.abs(myYCoor - targYCoor) == 1))
			return true;
		else
			return false;
	}
	public boolean validAttack(int myXCoor, int myYCoor, int targXCoor, int targYCoor, Board board){
		return validMovement(myXCoor,myYCoor,targXCoor,targYCoor,board);
	} 
	public String toString(){
		return COLOR + "-" + "K";
	}
}
