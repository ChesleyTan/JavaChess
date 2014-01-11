public class Board {
    
    private Object[][] _board; 
    public Board() {
	_board = new Object[8][8];
    }
    private Object get( int r, int c) {
	return _board[r-1][c-1];
    }
    private boolean isEmpty(int r, int c) {
	return _board[r-1][c-1] == null;
    }
    private Object set(int r, int c, Object newVal) {
	Object originalpiece = _board[r-1][c-1];
	_board[r-1][c-1] = newVal;
	return originalpiece;
    }
    public String toString() {
	String foo = "";
	for (Object i[] : _board) {
	    for (Object piece : i) {
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

