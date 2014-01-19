package JavaChess;
import JavaChess.pieces.*;
import java.util.Scanner;
public class Chess{
	private static Scanner scanInt = new Scanner(System.in);
	private static Scanner scanStr = new Scanner(System.in);
	private static String player1, player2, winner;
	private static int blackKingXCoor = 4; // Position of king at start of game
	private static int whiteKingXCoor = 4; // Position of king at start of game
	private static int blackKingYCoor = 0;
	private static int whiteKingYCoor = 7;
	private static String userColor = "W";
	private static ChessPiece lastEnPassant = null; 
	private static boolean kingPreviouslyChecked = false;
	private static Board board = new Board();
	public static int getXOfKing(String userColor){
		if (userColor.equals("W")){
			return whiteKingXCoor;
		}
		else{
			return blackKingXCoor;
		}
	}
	public static int getYOfKing(String userColor){
		if (userColor.equals("W")){
			return whiteKingYCoor;
		}
		else{
			return blackKingYCoor;
		}
	}
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
	public static boolean kingCheckedAfterMove(){
		boolean retBool;
		if (retBool = isChecked(userColor, getXOfKing(userColor), getYOfKing(userColor))) { // Check if the resulting position leads to a check on the user's king
			if (kingPreviouslyChecked){ // Case when the user's king is checked in the turn before this one
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nInvalid Move: Your King is still in check.");
			}
			else{ // Case when the user's move brings the user's king into check
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nInvalid Move: Your King is in check after this move.");
			}
		}
		return retBool;
	}
	// Method to check if a king is checked and set the isChecked boolean of the piece appropriately
	// Also allows for virtualization of king at those coordinates
	public static boolean isChecked(String color, int kingXCoor, int kingYCoor) {
		boolean isChecked = false;
		// Remove old entries in checkedBy array of the king
		if (board.get(kingXCoor,kingYCoor) != null && board.get(kingXCoor,kingYCoor).getType().equals("KING")){
			((King)board.get(kingXCoor,kingYCoor)).clearCheckedBy();
		}
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				ChessPiece piece = board.get(x,y);
				if (piece != null && !piece.getColor().equals(color)) {
					if (piece.validAttack(x, y,kingXCoor,kingYCoor, board)) {
						if (board.get(kingXCoor,kingYCoor) != null && board.get(kingXCoor,kingYCoor).getType().equals("KING")){  // Allows for virtualization of move: if the supplied args for king's coordinates are not the true coords, this prevents an error
							((King)board.get(kingXCoor,kingYCoor)).setIsChecked(true);
							((King)board.get(kingXCoor,kingYCoor)).addCheckedBy(new int[] {x,y});
						}
						isChecked = true;
					}
				}
			}
		}
		if (isChecked == false && board.get(kingXCoor,kingYCoor) != null && board.get(kingXCoor,kingYCoor).getType().equals("KING")){
			((King)board.get(kingXCoor,kingYCoor)).setIsChecked(false);
			((King)board.get(kingXCoor,kingYCoor)).clearCheckedBy();
		}
		return isChecked;
	}
	
	// Method to check if a king can move within its 3x3 surrounding
	public static boolean canMove(String color, int kingXCoor, int kingYCoor) {
		ChessPiece king = board.get(kingXCoor, kingYCoor);
		for (int x = -1; x < 2; x ++) {
			for (int y = -1; y < 2; y ++) {
				if (x != 0 && y != 0) { // Skips case when the offsets are both 0, hence the piece is moving to itself
					int newXCoor = kingXCoor + x;
					int newYCoor = kingYCoor + y;
					if (newXCoor >= 0 && newXCoor < 8 && newYCoor >= 0 && newYCoor < 8){ // Prevents index out of bounds
						if (king.validMovement(kingXCoor, kingYCoor, newXCoor, newYCoor, board)) {
							if (!isChecked(color, newXCoor, newYCoor)) { // If there exists a position where the king is not in check, then return true
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	// Method to check if any of the user's pieces can block or kill an enemy piece that is checking the user's king
	public static boolean canIntercept(String color, int kingXCoor, int kingYCoor){
		King king = (King)board.get(kingXCoor,kingYCoor);
		int numPiecesCheckedBy = king.getCheckedBy().size();
		int x = 0;
		int y = 0;
		if (numPiecesCheckedBy == 1){
			int[] checkerCoords = king.getCheckedBy().get(0);
			String checkingPieceType = board.get(checkerCoords[0],checkerCoords[1]).getType();
			if (checkingPieceType.equals("QUEEN") || checkingPieceType.equals("ROOK") || checkingPieceType.equals("BISHOP")){
				Line checker = new Line(kingXCoor, kingYCoor, checkerCoords[0], checkerCoords[1]); // Create a line representing the line of sight of the checking piece
				for (; x < 8; x++) {
					for (y = 0; y < 8; y++) {
						ChessPiece piece = board.get(x,y);
						if (piece != null && piece.getColor().equals(color)) {
							for (double slope:piece.getAttackSlopes()){
								Line interceptor = new Line();
								if (Math.abs(slope) == 10){
									interceptor = new Line((double)x);
								}
								else{
									interceptor = new Line(slope, x, y);
								}
								double[] intersection = interceptor.getIntersection(checker);
								// Diagnostic info
								System.out.println("Checker: " + checker);
								System.out.println("Interceptor: " + interceptor);
								System.out.println((intersection != null) ? "Intersect at (" + intersection[0] + "," + intersection[1] + ")" : "No intersection");
								
								int xOfIntersection, yOfIntersection;
								if (intersection != null && (int)intersection[0] == intersection[1] && (int)intersection[1] == intersection[1]){
									xOfIntersection = (int)intersection[0];
									yOfIntersection = (int)intersection[1];
									if (xOfIntersection >= 0 && xOfIntersection < 8 && yOfIntersection >= 0 && yOfIntersection < 8){
										if ((board.get(xOfIntersection,yOfIntersection) == null && piece.validMovement(x, y, xOfIntersection,yOfIntersection, board)) || (board.get(xOfIntersection,yOfIntersection) != null && piece.validAttack(x, y, xOfIntersection, yOfIntersection, board))){
											int xOfChecker = checkerCoords[0];
											int yOfChecker = checkerCoords[1];
											if (xOfIntersection >= xOfChecker && yOfIntersection <= yOfChecker && xOfIntersection <= kingXCoor && yOfIntersection >= kingYCoor){
												return true;
											}
											else if (xOfIntersection <= xOfChecker && yOfIntersection <= yOfChecker && xOfIntersection >= kingXCoor && yOfIntersection >= kingYCoor){
												return true;
											}
											else if (xOfIntersection >= xOfChecker && yOfIntersection >= yOfChecker && xOfIntersection <= kingXCoor && yOfIntersection <= kingYCoor){
												return true;
											}
											else if (xOfIntersection <= xOfChecker && yOfIntersection >= yOfChecker && xOfIntersection >= kingXCoor && yOfIntersection <= kingYCoor){
												return true;
											}
											else{
												continue;
											}	
										}
									}
								}
								else{
									continue;
								}
							}
						}
					}
				}
			}
		}
		else if (numPiecesCheckedBy >= 2){  // If checked by 2 or more pieces simulataneusly, then the intersection of the lines occur at the king, so no interception can be made
			return false;	
		}
		return false;
	}
	
	public static boolean isCheckmate(){
		int kingXCoor, kingYCoor;
		if (userColor.equals("W")){
			kingXCoor = whiteKingXCoor;
			kingYCoor = whiteKingYCoor;
		}
		else{
			kingXCoor = blackKingXCoor;
			kingYCoor = blackKingYCoor;
		}
		if (isChecked(userColor, kingXCoor, kingYCoor)){
			if (!canMove(userColor, kingXCoor, kingYCoor)){
				if (!canIntercept(userColor, kingXCoor, kingYCoor)){
					if (userColor.equals("W")){
						winner = player1;
					}
					else{
						winner = player2;
					}
					return true;
				}
			}
		}
		return false;
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
		while (!isCheckmate()){
			
			// Checks if user's king is in check before moving
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

			// Special move: Castling
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
									if (isChecked(userColor, 5, myYCoor) || isChecked(userColor, 6, myYCoor)){
										System.out.println("Invalid Castle: King passes through square attacked by enemy piece");
										continue;
									}
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
									if (isChecked(userColor, 3, myYCoor) || isChecked(userColor, 2, myYCoor)){
										System.out.println("Invalid Castle: King passes through square attacked by enemy piece");
										continue;
									}
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
			
			// Special move: En Passant
			else if (target != null && chosen.getType().equals("PAWN") && target.getType().equals("PAWN") && !target.getColor().equals(userColor)){
				if (myYCoor == targYCoor){
					if (lastEnPassant == target){
						if (userColor.equals("W")){
							board.set(targXCoor, targYCoor, null);
							board.set(myXCoor, myYCoor, null);
							board.set(targXCoor, targYCoor - 1, chosen);
						}
						else{
							board.set(targXCoor, targYCoor, null);
							board.set(myXCoor, myYCoor, null);
							board.set(targXCoor, targYCoor + 1, chosen);
						}
						
						if (kingCheckedAfterMove()) { // Check if the resulting position leads to a check on the user's king
							board.set(myXCoor, myYCoor, chosen);
							board.set(targXCoor, targYCoor, target);
							if (userColor.equals("W")){
								board.set(targXCoor, targYCoor - 1, null);
							}
							else{
								board.set(targXCoor, targYCoor + 1, null);
							}
							System.out.println(board.toString(userColor));
							continue;
						}
						else{
							if (userColor.equals("W")){
								System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nSuccessful En Passant: " + chosen + "(" + myXCoor + "," + myYCoor + ") takes " + target + "(" + targXCoor + "," + targYCoor + ").  Pawn lands at (" + targXCoor + "," + (targYCoor - 1) + ")." + "\n");
							}
							else{
								System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nSuccessful En Passant: " + chosen + "(" + myXCoor + "," + myYCoor + ") takes " + target + "(" + targXCoor + "," + targYCoor + ").  Pawn lands at (" + targXCoor + "," + (targYCoor + 1) + ")." + "\n");
							}
							chosen.toggleHasMoved();
							toggleUserColor();
							System.out.println(board.toString(userColor));
							continue;
						}
					}
				}
			}
			
			// Special move: Pawn advances two spaces
			else if (target == null && chosen.getType().equals("PAWN") && Math.abs(myYCoor - targYCoor) == 2 && chosen.validMovement(myXCoor, myYCoor, targXCoor, targYCoor, board)){
				board.set(targXCoor, targYCoor, chosen);
				board.set(myXCoor, myYCoor, null);
				if (kingCheckedAfterMove()){
					board.set(myXCoor,myYCoor,board.set(targXCoor,targYCoor,target)); // Return pieces to original position by swapping 
					System.out.println(board.toString(userColor));
					continue;
				}
				else{
					lastEnPassant = chosen;
					chosen.toggleHasMoved();
					System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nSuccessful move: " + chosen + " (" + myXCoor + "," + myYCoor + ") to (" + targXCoor + "," + targYCoor + ").\n");
					toggleUserColor();
					System.out.println(board.toString(userColor));
					continue;
				}
			}

			// Checks if user's king is in check following moving
			if (validMove(myXCoor, myYCoor, targXCoor, targYCoor, userColor)) {
				if (chosen.getType().equals("KING")){
					updateKingCoor(chosen.getColor(), targXCoor, targYCoor); 
				}/* need to update it first because if the chosen piece is a king, then
				I wouldn't want to ask for check on the same exact coords, but I would want to ask for check on 
				the new coords of the king*/
				board.set(targXCoor,targYCoor,chosen); // Tentatively move the chosen piece to the target position
				board.set(myXCoor, myYCoor, null); // Remove chosen piece from old position
				if (kingCheckedAfterMove()) { // Check if the resulting position leads to a check on the user's king
					board.set(myXCoor,myYCoor,board.set(targXCoor,targYCoor,target)); // Return pieces to original position by swapping 
					if (chosen.getType().equals("KING")){
						updateKingCoor(chosen.getColor(), myXCoor, myYCoor); 
					}
				}

				else { // Case when the resulting position does NOT result in a check on the user's king
					chosen.toggleHasMoved();
					if (target == null) { // Print feedback information to user
						System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nSuccessful move: " + chosen + " (" + myXCoor + "," + myYCoor + ") to (" + targXCoor + "," + targYCoor + ").\n");
					}
					else {  // Print feedback information to user
						System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nSuccessful kill: " + chosen + " (" + myXCoor + "," + myYCoor + ") takes " + target + " (" + targXCoor + "," + targYCoor + ").\n");
					}
					lastEnPassant = null; // If En Passant not used, opportunity is lost
					toggleUserColor(); // Toggle switching of turns after successful move
				}
			}

			System.out.println(board.toString(userColor)); // Print out board
		}
		System.out.println("CHECKMATE, " + winner.toUpperCase() + " WINS!");
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
		board.set(4,0,new King("B"));
		board.set(3,6,new Pawn("W"));
		board.set(2,5,new Queen("B"));
		board.set(3,1,new Pawn("B"));
		isChecked("W",4,7);
		System.out.println("White King can move:" + canMove("W",4,7));
		System.out.println("White can intercept:" + canIntercept("W",4,7));
	}
}
