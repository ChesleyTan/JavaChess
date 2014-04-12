package JavaChess.pieces;
import JavaChess.Board;
import java.util.ArrayList;
public class King extends ChessPiece{
	private boolean isChecked = false;
	private ArrayList<int[]> checkedBy = new ArrayList<int[]>();  // Keeps track of the coordinates of the pieces currently checking the king
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
	private King(String type, String color, int range, double[] attack_slopes, boolean has_moved, boolean is_Checked, ArrayList<int[]> checked_By){
	    super(type, color, range, attack_slopes, has_moved);
	    isChecked = is_Checked;
        checkedBy = new ArrayList<int[]>();
        for (int[] i : checked_By) {
            int[] copy = new int[i.length];
            System.arraycopy(i, 0, copy, 0, i.length);
            checkedBy.add(copy);
        }
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

	public King copy() {
	    return new King(TYPE, COLOR, RANGE, ATTACK_SLOPES, hasMoved, isChecked, checkedBy);
	} 

	public boolean equals(ChessPiece p) {
	    if (p.TYPE != TYPE ||
	            p.COLOR != COLOR ||
	            p.RANGE != RANGE ||
	            p.ATTACK_SLOPES.length != ATTACK_SLOPES.length ||
	            p.hasMoved != hasMoved ||
	            ((King)p).isChecked != isChecked ||
	            ((King)p).checkedBy.size() != checkedBy.size())
	    {
	        return false;            
	    }
	    for (int i = 0;i < ATTACK_SLOPES.length;i++) {
	        if (ATTACK_SLOPES[i] != p.ATTACK_SLOPES[i]) {
	            return false;
	        }
	    }
	    
	    for (int i = 0;i < checkedBy.size();i++) {
	        for (int u = 0;u < checkedBy.get(i).length;u++) {
	            if (checkedBy.get(i)[u] != ((King)p).checkedBy.get(i)[u]) {
	                return false;
	            }
	        }
	    }
	    return true;
	}
}
