package JavaChess;
import JavaChess.pieces.*;

// Note: Coordinates are reversed because board stores coordinates in a format that resembles (Y,X) whereas computation and user input should be in format (X,Y)
public class Board {

	private ChessPiece[][] _board; 
	public Board() {
		_board = new ChessPiece[8][8];
		int r = 0;
		String color = "B";
		for (ChessPiece[] row : _board) {
			for (ChessPiece piece : row) {
				if (r == 0 || r == 7) {
					_board[r][0] = new Rook(color);
					_board[r][1] = new Knight(color);
					_board[r][2] = new Bishop(color);
					_board[r][3] = new Queen(color);
					_board[r][4] = new King(color);
					_board[r][5] = new Bishop(color);
					_board[r][6] = new Knight(color);
					_board[r][7] = new Rook(color);
				}
				else if (r == 1 || r == 6) {
					for (int i = 0; i < 8; i++) {
						_board[r][i] = new Pawn(color);
					}
				}
				else {
					color = "W";
				}

			}
			r += 1;
		}
	}
	public ChessPiece get( int myXCoor, int myYCoor) {
		return _board[myYCoor][myXCoor];
	}
	public boolean isEmpty(int myXCoor, int myYCoor) {
		return _board[myYCoor][myXCoor] == null;
	}
	public ChessPiece set(int myXCoor, int myYCoor, ChessPiece newVal) {
		ChessPiece originalpiece = _board[myYCoor][myXCoor];
		_board[myYCoor][myXCoor] = newVal;
		return originalpiece;
	}

	// Prints _board when it is white's turn
	public String toString() {
		String foo = "";
		for (int i = 0;i<_board.length;i++) {
			for (int u = 0;u<_board[0].length;u++) {
				if (_board[i][u] == null){
					foo += "_____" + "\t\b";
					continue;
				}
				foo += " " + _board[i][u] + "\t\b";
			}
			foo += "\n";
			for (int u = 0;u<_board[0].length;u++) {
				foo += "(" + u + "," + i + ")" + "\t\b";
			}
			foo += "\n\n";
		}
		return foo.substring(0,foo.length()-1); // Remove extra newline
	}

	// Takes argument of user color, prints board depending on whose turn it is
	public String toString(String color){
		String retStr = "============================================================\n";
		if (color.equals("W")){
			retStr += toString();
		}
		else if (color.equals("B")){
			retStr += getReverse();
		}
		else{
			retStr += toString();
		}
		retStr += "============================================================\n";
		return retStr;
	}

	// Prints _board in reverse when it is black's turn
	public String getReverse(){
		String foo = "";
		for (int i = _board.length-1;i>-1;i--){
			for (int u = _board[0].length-1;u>-1;u--) {
				if (_board[i][u] == null){
					foo += "_____" + "\t\b";
					continue;
				}
				foo += " " + _board[i][u] + "\t\b";
			}
			foo += "\n";
			for (int u = _board[0].length-1;u>-1;u--) {
				foo += "(" + u + "," + i + ")" + "\t\b";
			}
			foo += "\n\n";
		}
		return foo.substring(0,foo.length()-1); // Remove extra newline
	}
}

