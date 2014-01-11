import pieces.*;
import java.util.Scanner;
public class Chess{
	private static Scanner scanInt = new Scanner(System.in);
	private static String userColor = "W";
	public static void main(String[] args){
		Board board = new Board();
		System.out.println("Board:");
		System.out.println(board.toString(userColor));
		ChessPiece chosen = null;
		int myXCoor,myYCoor,targXCoor,targYCoor;
		while (true){
			// Note: Coordinates are reversed because board stores coordinates in a format that resembles (Y,X) whereas computation and user input should be in format (X,Y)
			do {
			System.out.println("Choose your piece to move: Tell me it's x-coordinate.");
			myXCoor = InputValidator.nextValidInt(scanInt,0,8);
			System.out.println("Now tell me it's y-coordinate.");
			myYCoor = InputValidator.nextValidInt(scanInt,0,8);
			chosen = board.get(myXCoor,myYCoor);
			System.out.println("The piece you chose is " + chosen);
			if (chosen == null || !chosen.getColor().equals(userColor))
				System.out.println("Please choose a valid piece.");
			} while(chosen == null || !chosen.getColor().equals(userColor));
			
			System.out.println("Choose a place or piece to move to: Tell me it's x-coordinate.");
			targXCoor = InputValidator.nextValidInt(scanInt,0,8);
			System.out.println("Now tell me it's y-coordinate.");
			targYCoor = InputValidator.nextValidInt(scanInt,0,8);
			// Note: Implement castling here: check if target piece is same color and a rook and other conditions
			if (board.move(myXCoor,myYCoor,targXCoor,targYCoor,userColor)){
				if (userColor.equals("W"))
					userColor = "B";
				else
					userColor = "W";
			}
			System.out.println(board.toString(userColor));
		}	
	}
}
