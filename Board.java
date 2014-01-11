// dedicated to Chesley

import pieces.*;

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
    private ChessPiece get( int r, int c) {
	return _board[r-1][c-1];
    }
    private boolean isEmpty(int r, int c) {
	return _board[r-1][c-1] == null;
    }
    private ChessPiece set(int r, int c, ChessPiece newVal) {
	ChessPiece originalpiece = _board[r-1][c-1];
	_board[r-1][c-1] = newVal;
	return originalpiece;
    }
    public String toString() {
	String foo = "";
	for (ChessPiece i[] : _board) {
	    for (ChessPiece piece : i) {
		foo += piece + " ";
	    }
	    foo += "\n\n";
	}
	return foo;
    }
    public static void main( String[] args ) {
	Board board = new Board();
	System.out.println("Board:");
	System.out.println(board);
    }
}

