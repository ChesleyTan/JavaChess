package JavaChess;
import JavaChess.pieces.*;
import java.util.ArrayList;

// Class DeltaBoard stores backups of Board using delta compression
// Stored int of 1 signifies no change in ChessPiece
// Stored int of 0 signifies a null that was changed into a ChessPiece
// Stored ChessPiece signifies a ChessPiece that was changed into a ChessPiece
public class DeltaBoard{
	private static boolean debugMode = false;
	private static ArrayList<Object[][]> history = new ArrayList<Object[][]>();
	// Method to create a copy of a board
	public static Board cloneBoard(Board board){
		Board retBoard = new Board();
		for (int i = 0;i < 8;i++){
			for (int u = 0;u < 8;u++){
				if (board.get(i, u) != null){
					retBoard.set(i, u, board.get(i, u).copy());
				}
				else{
					retBoard.set(i, u, null);
				}
			}
		}
		return retBoard;
	}
	public static void backup(Board previousBoard, Board currentBoard){
		Object[][] deltaBoard = new Object[8][8];
		for (int y = 0;y < 8;y++){
			for (int x = 0;x < 8;x++){
				if (debugMode){
					System.out.println("Prev: " + previousBoard.get(x, y) + " Now: " + currentBoard.get(x, y));
					System.out.println("Same? " + ((previousBoard.get(x, y) == null && currentBoard.get(x, y) == null) || (previousBoard.get(x, y) != null && currentBoard.get(x, y) != null && previousBoard.get(x, y).equals(currentBoard.get(x, y)))));
				}
				if (previousBoard.get(x, y) == null && currentBoard.get(x, y) != null) {
                    deltaBoard[y][x] = 0;  // Store a 0 to signify a null
                }
                else if (previousBoard.get(x, y) != null && currentBoard.get(x, y) != null && !previousBoard.get(x, y).equals(currentBoard.get(x, y))){
                    deltaBoard[y][x] = previousBoard.get(x, y).copy(); // Store the old ChessPiece
                }
                else if (previousBoard.get(x, y) != null && currentBoard.get(x, y) == null) {
                    deltaBoard[y][x] = previousBoard.get(x, y).copy();
                }
				else{
					deltaBoard[y][x] = 1; // Store a 1 to signify no change
				}
			}
		}
		history.add(deltaBoard);
	}
	public static int getHistorySize(){
		return history.size();
	}
	
	// Same as restorePrev except the backup is not deleted, this method is used after a restore is done to correctly set the previousBoard variable in Chess
	public static Board getPrev(Board currentBoard){
		Board previousBoard = new Board();
		if (history.size() == 0){
			return currentBoard;
		}
		Object[][] deltaBoard = history.get(history.size() - 1);
		for (int y = 0;y < 8;y++){
			for (int x = 0;x < 8;x++){
				if (deltaBoard[y][x] instanceof Integer){
					if (((Integer)deltaBoard[y][x]) == 0){
						previousBoard.set(x, y, null);
					}
					else{
						previousBoard.set(x, y, currentBoard.get(x,y));
					}
				}
				else{
					previousBoard.set(x, y, (ChessPiece)deltaBoard[y][x]);
				}
			}
		}
		return previousBoard;
	}
	public static Board restorePrev(Board currentBoard){
		Board previousBoard = getPrev(currentBoard);
		if (history.size() > 0){
			history.remove(history.size() - 1);
		}
		return previousBoard;
	}
	public static String toString(int numPrev){
		String retStr = "";
		if (numPrev <= 0 || numPrev > history.size()){ // Prevent index out of range error
			numPrev = 1;
		}
		Object[][] deltaBoard = history.get(history.size() - numPrev);
		retStr += "History #: " + history.size() + "\n";
		for (int y = 0;y < 8;y++) {
			for (int x = 0;x < 8;x++){
				if (deltaBoard[y][x] == null){
					retStr += "_____" + "\t\b";
					continue;
				}
				retStr += " " + deltaBoard[y][x] + "\t\b";
			}
			retStr += "\n";
		}
		return retStr.substring(0,retStr.length()-1); // Remove extra newline
	}
}
