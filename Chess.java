package JavaChess;
import JavaChess.pieces.*;
import java.util.Scanner;
public class Chess{
	private static Scanner scanInt = new Scanner(System.in);
	private static Scanner scanStr = new Scanner(System.in);
	private static String player1, player2;
	private static int blackKingXCoor = 4; // Position of king at start of game
	private static int whiteKingXCoor = 4; // Position of king at start of game
	private static int blackKingYCoor = 0;
	private static int whiteKingYCoor = 7;
	private static String userColor = "W";
	//private static boolean isChecked = false;
	//private static int counter = 0;
	private static Board board = new Board();
	public static void updateKingCoor(String color, int xCoor, int yCoor){
		if (color.equals("W")){
			whiteKingXCoor = xCoor;
			whiteKingYCoor = yCoor;
		}
		else if (color.equals("B")){
			blackKingXCoor = xCoor;
			blackKingYCoor = yCoor;
		}
	}
	public static boolean isChecked(String color, int kingXCoor, int kingYCoor) {         
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				ChessPiece piece = board.get(x,y);
				if (piece != null && !piece.getColor().equals(color)) {
					if (piece.validAttack(x, y,kingXCoor,kingYCoor, board)) {
						((King)board.get(kingXCoor,kingYCoor)).setIsChecked(true);	
						return true; 
					}
				}
			}
		}
		((King)board.get(kingXCoor,kingYCoor)).setIsChecked(false);	
		return false;
	}
	public static boolean canMove(String color, int kingXCoor, int kingYCoor) {
		for (int x = -1; x < 2; x ++) {
			for (int y = -1; y < 2; y ++) {
				if (x != 0 && y != 0) {
					ChessPiece king = board.get(kingXCoor, kingYCoor);
					if (king.validMovement(kingXCoor, kingYCoor, kingXCoor + x, kingYCoor + y, board)) {
						if (!isChecked(color, kingXCoor + x, kingYCoor + y)) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}


	public static boolean validCastle(int myXCoor, int myYCoor, int targXCoor, int targYCoor){
		if (myYCoor != targYCoor)
			return false;
		else{
			if (myXCoor < targXCoor){
				for (int i = myXCoor+1;i<targXCoor;i++){
					if ( !(board.isEmpty(i,myYCoor)) ){
						return false;
					}
				}
				return true;
			}
			else if (myXCoor > targXCoor){
				for (int i = myXCoor-1;i>targXCoor;i--){
					if ( !(board.isEmpty(i,myYCoor)) ){
						return false;
					}
				}
				return true;
			}
			else{
				return false;
			}
		}
	}
	public static boolean validMove(int myXCoor, int myYCoor, int targXCoor, int targYCoor, String myColor){
		ChessPiece myPiece = board.get(myXCoor,myYCoor);
		ChessPiece targPiece = board.get(targXCoor,targYCoor);
		if (targPiece != null && myPiece.getColor().equals(targPiece.getColor())){
			System.out.println("\nInvalid move: Attacking own piece.\n");
			return false;
		}
		if (targPiece == null){
			if (myPiece.validMovement(myXCoor,myYCoor,targXCoor,targYCoor,board)){
				return true;
			}
			else{
				System.out.println("\n\n\n\n\n\n\n\n\nInvalid move: " + "(" + myXCoor + "," + myYCoor + ") to (" + targXCoor + "," + targYCoor + ").\n");
				return false;
			}
		}
		else if (myPiece.validAttack(myXCoor,myYCoor,targXCoor,targYCoor,board)){
			return true;
		}
		System.out.println("\n\n\n\n\n\n\n\n\nInvalid move: " + "(" + myXCoor + "," + myYCoor + ") to (" + targXCoor + "," + targYCoor + ").\n");
		return false;
	}
	public static void toggleUserColor(){
		if (userColor.equals("W"))
			userColor = "B";
		else
			userColor = "W";
	}
	public static void main(String[] args){
		cheat();

		// Set up player names
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		do{
			System.out.print("Player 1 Name: ");
			player1 = scanStr.nextLine();
			System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nPlayer 2 Name: ");
			player2 = scanStr.nextLine();
			if (player1.equals(player2))
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nPlease choose a different name.");
			else
				System.out.println("P1:" + player1 + " P2:" + player2);
		}while(player1.equals(player2));

		// Start Game
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nBoard:\n");
		System.out.println(board.toString(userColor));
		ChessPiece chosen = null;
		ChessPiece target;
		int myXCoor,myYCoor,targXCoor,targYCoor;
		while (true){
			
			// Checks if user's king is in check before moving
			boolean kingPreviouslyChecked = false;
			if (userColor.equals("W")){
				if (isChecked(userColor, whiteKingXCoor, whiteKingYCoor)){ // Check whether the user's king is in check prior to user's turn
					kingPreviouslyChecked = ((King)board.get(whiteKingXCoor,whiteKingYCoor)).isChecked(); // Keeps track of whether the user's king was checked before this turn
					System.out.println("Your King is in check!");
				} 
			}
			else{
				if (isChecked(userColor, blackKingXCoor, blackKingYCoor)){ // Check whether the user's king is in check prior to user's turn
					kingPreviouslyChecked = ((King)board.get(blackKingXCoor,blackKingYCoor)).isChecked(); // Keeps track of whether the user's king was checked before this turn
					System.out.println("Your King is in check!");
				} 
			}

			do {
				System.out.println((userColor.equals("W"))?player1 + "'s turn:":player2 + "'s turn:");        
				System.out.println("Choose your piece to move: Tell me its x-coordinate.");
				myXCoor = InputValidator.nextValidInt(scanInt,0,8);
				System.out.println("Now tell me its y-coordinate.");
				myYCoor = InputValidator.nextValidInt(scanInt,0,8);
				chosen = board.get(myXCoor,myYCoor);
				System.out.println("The piece you chose is " + chosen);
				if (chosen == null || !chosen.getColor().equals(userColor))
					System.out.println("Please choose a valid piece.");
			} while(chosen == null || !chosen.getColor().equals(userColor));

			System.out.println("Choose a place or piece to move to: Tell me its x-coordinate.");
			targXCoor = InputValidator.nextValidInt(scanInt,0,8);
			System.out.println("Now tell me its y-coordinate.");
			targYCoor = InputValidator.nextValidInt(scanInt,0,8);
			// Implementation for castling feature 
			target = board.get(targXCoor,targYCoor);
			if (target != null && chosen.getColor().equals(target.getColor())){
				if (chosen.getType().equals("KING") && (target.getType().equals("ROOK"))){
					if (((King)chosen).isChecked()){
						System.out.println("Invalid Castle: King is in check.");
						continue;
					}
					else{
						if (!chosen.hasMoved() && !target.hasMoved()){
							if (validCastle(myXCoor,myYCoor,targXCoor,targYCoor)){
								if (myXCoor == 4 && targXCoor == 7){
									board.set(6,myYCoor,chosen);
									board.set(5,myYCoor,target);
									board.set(4,myYCoor,null);
									board.set(7,myYCoor,null);
									updateKingCoor(chosen.getColor(), 6, myYCoor);
									chosen.toggleHasMoved();
									target.toggleHasMoved();
									System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nSuccessful castle: Kingside");
									toggleUserColor();
									System.out.println(board.toString(userColor));
									continue;
								}
								else if (myXCoor == 4 && targXCoor == 0){
									board.set(2,myYCoor,chosen);
									board.set(3,myYCoor,target);
									board.set(4,myYCoor,null);
									board.set(0,myYCoor,null);
									updateKingCoor(chosen.getColor(), 2, myYCoor);
									chosen.toggleHasMoved();
									target.toggleHasMoved();
									System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nSuccessful castle: Queenside");
									toggleUserColor();
									System.out.println(board.toString(userColor));
									continue;
								}
								else{
									System.out.println("Invalid Castle: Incorrect positioning.");
								}
							} 
							else{
								System.out.println("Invalid Castle: Path blocked.");
								continue;
							}
						}
						else{
							System.out.println("Invalid Castle: Either King or Rook has moved before.");
							continue;
						}
					}
				}
			}

			// Checks if user's king is in check following moving
			int xOfKing; // These variables keep track of the coordinates of the current user's king 
			int yOfKing;
			if (validMove(myXCoor, myYCoor, targXCoor, targYCoor, userColor)) {
				if (chosen.getType().equals("KING")){
					updateKingCoor(chosen.getColor(), targXCoor, targYCoor); 
				}/* need to update it first because if the chosen piece is a king, then
				I wouldn't want to ask for check on the same exact coords, but I would want to ask for check on 
				the new coords of the king*/
				if (userColor.equals("W")) { 
					xOfKing = whiteKingXCoor;
					yOfKing = whiteKingYCoor;
				}
				else {
					xOfKing = blackKingXCoor;
					yOfKing = blackKingYCoor;
				}
				board.set(targXCoor,targYCoor,chosen); // Tentatively move the chosen piece to the target position
				board.set(myXCoor, myYCoor, null); // Remove chosen piece from old position
				if (isChecked(userColor, xOfKing, yOfKing)) { // Check if the resulting position leads to a check on the user's king
					if (kingPreviouslyChecked){ // Case when the user's king is checked in the turn before this one
						System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nInvalid Move: Your King is still in check.");
					}
					else{ // Case when the user's move brings the user's king into check
						System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nInvalid Move: Your King is in check after this move.");
					}
					board.set(myXCoor,myYCoor,board.set(targXCoor,targYCoor,target)); // Return pieces to original position by swapping 
					if (chosen.getType().equals("KING")){
						updateKingCoor(chosen.getColor(), myXCoor, myYCoor); 
					}
				}

				else { // Case when the resulting position does NOT result in a check on the user's king
					chosen.toggleHasMoved();
					if (target == null) { // Print feedback information to user
						System.out.println("\n\n\n\n\n\n\n\n\n\nSuccessful move: " + chosen + " (" + myXCoor + "," + myYCoor + ") to (" + targXCoor + "," + targYCoor + ").\n");
					}
					else {  // Print feedback information to user
						System.out.println("\n\n\n\n\n\n\n\n\n\nSuccessful kill: " + chosen + " (" + myXCoor + "," + myYCoor + ") takes " + target + " (" + targXCoor + "," + targYCoor + ").\n");
					}
					toggleUserColor(); // Toggle switching of turns after successful move
				}
			}

			System.out.println(board.toString(userColor)); // Print out board
		}
	}
	// Method for setting up the board in a special arrangement
	// IMPORTANT NOTE: When starting the king is a position that is not its standard position on the chessboard,
	// you MUST manually update the whiteKingXCoor/blackKingXCoor and whiteKingYCoor/blackKingYCoor
	public static void cheat(){
		for (int u = 0;u<8;u++){
			for (int i = 0;i<8;i++){
				board.set(i,u,null);
			}
		}
		board.set(4,7,new King("W"));
		board.set(3,0,new King("B"));
		blackKingXCoor = 3;
		blackKingYCoor = 0;
		board.set(4,0,new Queen("B"));
	}
}
