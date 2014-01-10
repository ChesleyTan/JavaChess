package pieces;

public abstract class ChessPiece{
	private final String TYPE;
	private final String COLOR;
	private final int RANGE;
	private boolean isPinned = false;
	public boolean getIsPinned(){ return isPinned; }
	public abstract String toString(){ }
}
