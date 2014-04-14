package JavaChess;
import JavaChess.pieces.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
public class Chess{
	private static Scanner scanInt = new Scanner(System.in);
	private static Scanner scanStr = new Scanner(System.in);
	private static String player1, player2, winner;
	private static ChessPiece chosen = null;
	private static ChessPiece target;
	private static int myXCoor,myYCoor,targXCoor,targYCoor;
	private static int blackKingXCoor = 4; // Position of king at start of game
	private static int whiteKingXCoor = 4; // Position of king at start of game
	private static int blackKingYCoor = 0;
	private static int whiteKingYCoor = 7;
	private static String userColor = "W";
	private static boolean kingPreviouslyChecked = false;
	private static Board board = new Board();
	private static Board previousBoard = board;
	private final static boolean debugMode = false;

	private final static java.net.URL asciiFile = Chess.class.getResource("/JavaChess/resources/ascii.txt");

	public static String clearScreen(){
		String retStr = "";
		if (debugMode){
			retStr = "\n";
		}
		else{
			retStr = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"; 	
		}
		return retStr;
	}
	public static void loopRound(){ // Tasks to be done when looping a round
		System.out.println(board.toString(userColor));
	}
	public static void advanceRound(){ // Tasks to be done at the end of a successful round
		chosen.toggleHasMoved();
		for (int i = 0;i < 8;i++){
			for (int u = 0;u < 8;u++){
				ChessPiece piece = board.get(i, u);
				if (piece != null && piece.getType().equals("PAWN") && piece.getColor().equals(userColor)){
					if (((Pawn)piece).getLastPawnJump()){
						((Pawn)piece).setLastPawnJump(false);
					}
				}
			}
		}
		if (target != null && target.getType().equals("ROOK") && target.getColor().equals(userColor)){ // If the completed move was a castle, then toggle hasMoved for rook
			target.toggleHasMoved();
		}
		DeltaBoard.backup(previousBoard, board); 
		toggleUserColor();
		System.out.println(board.toString(userColor));
		if (debugMode){
			System.out.println(DeltaBoard.toString(1));
		}
	}
	public static int getXOfKing(String userColor){
		if (userColor.equals("W")){ 
			if (board.get(whiteKingXCoor, whiteKingYCoor) != null && board.get(whiteKingXCoor, whiteKingYCoor).getType().equals("KING")){ // Sanity check to make sure coords are correct
				return whiteKingXCoor;
			}
			else{
				for (int i = 0;i < 8;i++){
					for (int u = 0;u < 8;u++){
						ChessPiece piece = board.get(i, u);
						if (piece != null && piece.getType().equals("KING") && piece.getColor().equals("W")){
							whiteKingXCoor = i;
							whiteKingYCoor = u;
						}
					}
				}
			}
			return whiteKingXCoor;
		}
		else{
			if (board.get(blackKingXCoor, blackKingYCoor) != null && board.get(blackKingXCoor, blackKingYCoor).getType().equals("KING")){ // Sanity check to make sure coords are correct
				return blackKingXCoor;
			}
			else{
				for (int i = 0;i < 8;i++){
					for (int u = 0;u < 8;u++){
						ChessPiece piece = board.get(i, u);
						if (piece != null && piece.getType().equals("KING") && piece.getColor().equals("B")){
							blackKingXCoor = i;
							blackKingYCoor = u;
						}
					}
				}
			}
			return blackKingXCoor;
		}
	}
	public static int getYOfKing(String userColor){
		if (userColor.equals("W")){
			if (board.get(whiteKingXCoor, whiteKingYCoor) != null && board.get(whiteKingXCoor, whiteKingYCoor).getType().equals("KING")){ // Sanity check to make sure coords are correct
				return whiteKingYCoor;
			}
			else{
				for (int i = 0;i < 8;i++){
					for (int u = 0;u < 8;u++){
						ChessPiece piece = board.get(i, u);
						if (piece != null && piece.getType().equals("KING") && piece.getColor().equals("W")){
							whiteKingXCoor = i;
							whiteKingYCoor = u;
						}
					}
				}
			}
			return whiteKingYCoor;
		}
		else{
			if (board.get(blackKingXCoor, blackKingYCoor) != null && board.get(blackKingXCoor, blackKingYCoor).getType().equals("KING")){ // Sanity check to make sure coords are correct
				return blackKingYCoor;
			}
			else{
				for (int i = 0;i < 8;i++){
					for (int u = 0;u < 8;u++){
						ChessPiece piece = board.get(i, u);
						if (piece != null && piece.getType().equals("KING") && piece.getColor().equals("B")){
							blackKingXCoor = i;
							blackKingYCoor = u;
						}
					}
				}
			}
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
		if (retBool = isChecked(userColor, getXOfKing(userColor), getYOfKing(userColor), false)) { // Check if the resulting position leads to a check on the user's king
			if (kingPreviouslyChecked){ // Case when the user's king is checked in the turn before this one
				System.out.println(clearScreen() + "Invalid Move: Your King is still in check.");
			}
			else{ // Case when the user's move brings the user's king into check
				System.out.println(clearScreen() + "Invalid Move: Your King is in check after this move.");
			}
		}
		return retBool;
	}
	// Method to check if a king is checked and set the isChecked boolean of the piece appropriately
	// Also allows for virtualization of the move by not setting the king's local variables isChecked and checkedBy[] 
	public static boolean isChecked(String color, int kingXCoor, int kingYCoor, boolean virtualizeMove) {
		boolean isChecked = false;
		// Remove old entries in checkedBy array of the king
		if (board.get(kingXCoor,kingYCoor) != null && board.get(kingXCoor,kingYCoor).getType().equals("KING") && !virtualizeMove){
			((King)board.get(kingXCoor,kingYCoor)).clearCheckedBy();
		}
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				ChessPiece piece = board.get(x,y);
				if (piece != null && !piece.getColor().equals(color)) {
					if (piece.validAttack(x, y,kingXCoor,kingYCoor, board)) {
						if (board.get(kingXCoor,kingYCoor) != null && board.get(kingXCoor,kingYCoor).getType().equals("KING") && !virtualizeMove){  // Allows for virtualization of move: if the supplied args for king's coordinates are not the true coords, this prevents an error
							((King)board.get(kingXCoor,kingYCoor)).setIsChecked(true);
							((King)board.get(kingXCoor,kingYCoor)).addCheckedBy(new int[] {x,y});
						}
						isChecked = true;
					}
				}
			}
		}
		if (isChecked == false && board.get(kingXCoor,kingYCoor) != null && board.get(kingXCoor,kingYCoor).getType().equals("KING") && !virtualizeMove){
			((King)board.get(kingXCoor,kingYCoor)).setIsChecked(false);
			((King)board.get(kingXCoor,kingYCoor)).clearCheckedBy();
		}
		return isChecked;
	}

	// Method to check if a king can move within its 3x3 surrounding
	public static boolean canMove(String color, int kingXCoor, int kingYCoor) {
		boolean retBool = false;
		ChessPiece king = board.get(kingXCoor, kingYCoor);
		for (int x = -1; x < 2; x ++) {
			for (int y = -1; y < 2; y ++) {
				if (!(x == 0 && y == 0)) { // Skips case when the offsets are both 0, hence the piece is moving to itself
					int newXCoor = kingXCoor + x;
					int newYCoor = kingYCoor + y;

					// Diagnostic
					if (debugMode){
						System.out.println("Checking if king can move to (" + newXCoor + "," + newYCoor + ")"); 
					}

					if (newXCoor >= 0 && newXCoor < 8 && newYCoor >= 0 && newYCoor < 8){ // Prevents index out of bounds
						target = board.get(newXCoor, newYCoor);
						if (target == null && king.validMovement(kingXCoor, kingYCoor, newXCoor, newYCoor, board)) {
							board.set(newXCoor, newYCoor, king);
							board.set(kingXCoor, kingYCoor, null);
							updateKingCoor(userColor, newXCoor, newYCoor);
							if (!isChecked(color, newXCoor, newYCoor, true)) { // If there exists a position where the king is not in check, then return true
								if (debugMode){
									System.out.println("KING NOT IN CHECK HERE");
								}
								retBool = true;
							}
							board.set(newXCoor, newYCoor, null);
							board.set(kingXCoor, kingYCoor, king);
							updateKingCoor(userColor, kingXCoor, kingYCoor);
						}
						else if (target != null && !target.getColor().equals(userColor) && king.validAttack(kingXCoor, kingYCoor, newXCoor, newYCoor, board)) {
							board.set(newXCoor, newYCoor, king);
							board.set(kingXCoor, kingYCoor, null);
							updateKingCoor(userColor, newXCoor, newYCoor);
							if (!isChecked(color, newXCoor, newYCoor, true)) { // If there exists a position where the king is not in check, then return true
								if (debugMode){
									System.out.println("KING NOT IN CHECK HERE");
								}
								retBool = true;
							}
							board.set(newXCoor, newYCoor, target);
							board.set(kingXCoor, kingYCoor, king);
							updateKingCoor(userColor, kingXCoor, kingYCoor);
						}

					}
				}
			}
		}
		return retBool;
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
								if (debugMode){
									System.out.println("Checker: " + checker);
									System.out.println("Scanning: " + piece);
									System.out.println("Interceptor: " + interceptor);
									System.out.println((intersection != null) ? "Intersect at (" + intersection[0] + "," + intersection[1] + ")" : "No intersection");
								}
								
								int xOfIntersection, yOfIntersection;
								if (intersection != null && (int)intersection[0] == intersection[0] && (int)intersection[1] == intersection[1]){
									xOfIntersection = (int)intersection[0];
									yOfIntersection = (int)intersection[1];
									if (xOfIntersection >= 0 && xOfIntersection < 8 && yOfIntersection >= 0 && yOfIntersection < 8){
										if ((board.get(xOfIntersection,yOfIntersection) == null && piece.validMovement(x, y, xOfIntersection,yOfIntersection, board)) || (board.get(xOfIntersection,yOfIntersection) != null && !board.get(xOfIntersection,yOfIntersection).getColor().equals(userColor) && piece.validAttack(x, y, xOfIntersection, yOfIntersection, board))){
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
			else{
				System.out.println("CHECKING PIECE NOT INTERCEPTABLE" + checkingPieceType);
			}
		}
		else if (numPiecesCheckedBy >= 2){  // If checked by 2 or more pieces simulataneusly, then the intersection of the lines occur at the king, so no interception can be made
			return false;	
		}
		return false;
	}

	public static boolean isCheckmate(){
		int kingXCoor, kingYCoor;
		kingXCoor = getXOfKing(userColor);
		kingYCoor = getYOfKing(userColor);
		if (isChecked(userColor, kingXCoor, kingYCoor, false)){
			if (!canMove(userColor, kingXCoor, kingYCoor)){
				if (debugMode){
					System.out.println("KING CANNOT MOVE");
				}
				if (!canIntercept(userColor, kingXCoor, kingYCoor)){
					if (userColor.equals("B")){ // if checkmate is true, then other player wins
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

	public static void handlePawnPromotion(int myXCoor, int myYCoor){
		int targetRank;
		if (userColor.equals("W")){
			targetRank = 0;
		}
		else{
			targetRank = 7;
		}
		if (myYCoor == targetRank){
			System.out.println(clearScreen() + "What would you like to promote your pawn to?");
			System.out.println("[1] Bishop\n[2] Rook\n[3] Knight\n[4] Queen\n");
			System.out.print("Number of your selection: ");
			int choice = InputValidator.nextValidInt(scanInt, 1, 4);
			switch (choice){
				case 1:
					board.set(myXCoor, myYCoor, new Bishop(userColor));
					break;
				case 2:
					board.set(myXCoor, myYCoor, new Rook(userColor));
					break;
				case 3:
					board.set(myXCoor, myYCoor, new Knight(userColor));
					break;
				case 4:
					board.set(myXCoor, myYCoor, new Queen(userColor));
					break;
				default:
					break;

			}
		}
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
			System.out.println(clearScreen() + "Invalid move: Attacking own piece.\n");
			return false;
		}
		if (targPiece == null){
			if (myPiece.validMovement(myXCoor,myYCoor,targXCoor,targYCoor,board)){
				return true;
			}
			else{
				System.out.println(clearScreen() + "Invalid move: " + "(" + myXCoor + "," + myYCoor + ") to (" + targXCoor + "," + targYCoor + ").\n");
				return false;
			}
		}
		else if (myPiece.validAttack(myXCoor,myYCoor,targXCoor,targYCoor,board)){
			return true;
		}
		System.out.println(clearScreen() + "Invalid move: " + "(" + myXCoor + "," + myYCoor + ") to (" + targXCoor + "," + targYCoor + ").\n");
		return false;
	}
	public static void toggleUserColor(){
		if (userColor.equals("W"))
			userColor = "B";
		else
			userColor = "W";
	}
	
	// Special move: Castling
	public static boolean handleCastle(){
		boolean shouldCallContinue = false;  // Returns whether the while loop should skip the current iteration
		if (target != null && chosen.getColor().equals(target.getColor())){
			if (chosen.getType().equals("KING") && (target.getType().equals("ROOK"))){
				if (((King)chosen).isChecked()){
					System.out.println(clearScreen() + "Invalid Castle: King is in check.");
					loopRound();
					shouldCallContinue = true;
					return shouldCallContinue;
				}
				else{
					if (!chosen.hasMoved() && !target.hasMoved()){
						if (validCastle(myXCoor,myYCoor,targXCoor,targYCoor)){
							if (myXCoor == 4 && targXCoor == 7){
								if (isChecked(userColor, 5, myYCoor, true) || isChecked(userColor, 6, myYCoor, true)){
									System.out.println(clearScreen() + "Invalid Castle: King passes through square attacked by enemy piece");
									loopRound();
									shouldCallContinue = true;
									return shouldCallContinue;
								}
								board.set(6,myYCoor,chosen);
								board.set(5,myYCoor,target);
								board.set(4,myYCoor,null);
								board.set(7,myYCoor,null);
								updateKingCoor(chosen.getColor(), 6, myYCoor);
								System.out.println(clearScreen() + "Successful castle: Kingside");
								advanceRound();
								shouldCallContinue = true;
								return shouldCallContinue;
							}
							else if (myXCoor == 4 && targXCoor == 0){
								if (isChecked(userColor, 3, myYCoor, true) || isChecked(userColor, 2, myYCoor, true)){
									System.out.println(clearScreen() + "Invalid Castle: King passes through square attacked by enemy piece");
									loopRound();
									shouldCallContinue = true;
									return shouldCallContinue;
								}
								board.set(2,myYCoor,chosen);
								board.set(3,myYCoor,target);
								board.set(4,myYCoor,null);
								board.set(0,myYCoor,null);
								updateKingCoor(chosen.getColor(), 2, myYCoor);
								System.out.println(clearScreen() + "Successful castle: Queenside");
								advanceRound();
								shouldCallContinue = true;
								return shouldCallContinue;
							}
							else{
								System.out.println(clearScreen() + "Invalid Castle: Incorrect positioning.");
								loopRound();
								shouldCallContinue = true;
								return shouldCallContinue;
							}
						} 
						else{
							System.out.println(clearScreen() + "Invalid Castle: Path blocked.");
							loopRound();
							shouldCallContinue = true;
							return shouldCallContinue;
						}
					}
					else{
						System.out.println(clearScreen() + "Invalid Castle: Either King or Rook has moved before.");
						loopRound();
						shouldCallContinue = true;
						return shouldCallContinue;
					}
				}
			}
		}
		return shouldCallContinue;
	}

	// Special move: En Passant
	public static boolean handleEnPassant(){
		boolean shouldCallContinue = false;	// Returns whether the while loop should skip the current iteration
		if (target != null && chosen.getType().equals("PAWN") && target.getType().equals("PAWN") && !target.getColor().equals(userColor)){
			if (myYCoor == targYCoor){
				if (((Pawn)target).getLastPawnJump()){
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
						loopRound();
						shouldCallContinue = true;
					}
					else{
						if (userColor.equals("W")){
							System.out.println(clearScreen() + "Successful En Passant: " + chosen + "(" + myXCoor + "," + myYCoor + ") takes " + target + "(" + targXCoor + "," + targYCoor + ").  Pawn lands at (" + targXCoor + "," + (targYCoor - 1) + ")." + "\n");
						}
						else{
							System.out.println(clearScreen() + "Successful En Passant: " + chosen + "(" + myXCoor + "," + myYCoor + ") takes " + target + "(" + targXCoor + "," + targYCoor + ").  Pawn lands at (" + targXCoor + "," + (targYCoor + 1) + ")." + "\n");
						}
						advanceRound();
						shouldCallContinue = true;
					}
				}
			}
		}
		return shouldCallContinue;
	}

	// Special move: Pawn advances two spaces
	public static boolean handlePawnJump(){
		boolean shouldCallContinue = false;	// Returns whether the while loop should skip the current iteration
		if (target == null && chosen.getType().equals("PAWN") && !chosen.hasMoved() && Math.abs(myYCoor - targYCoor) == 2 && chosen.validMovement(myXCoor, myYCoor, targXCoor, targYCoor, board)){
			board.set(targXCoor, targYCoor, chosen);
			board.set(myXCoor, myYCoor, null);
			if (kingCheckedAfterMove()){
				board.set(myXCoor,myYCoor,board.set(targXCoor,targYCoor,target)); // Return pieces to original position by swapping 
				loopRound();
				shouldCallContinue = true;
			}
			else{
				System.out.println(clearScreen() + "Successful move: " + chosen + " (" + myXCoor + "," + myYCoor + ") to (" + targXCoor + "," + targYCoor + ").\n");
				advanceRound();	
				((Pawn)chosen).setLastPawnJump(true);
				shouldCallContinue = true;
			}
		}
		return shouldCallContinue;
	}

	// Method to set up the game, prints to console
	public static void setup(){
		System.out.println(clearScreen());
		if (debugMode) {
		    player1 = "P1";
		    player2 = "P2";
		    return;
		}
		do{
			System.out.print("Player 1 Name: ");
			player1 = scanStr.nextLine();
			System.out.print(clearScreen() + "Player 2 Name: ");
			player2 = scanStr.nextLine();
			if (player1.equals(player2))
				System.out.println(clearScreen() + "Please choose a different name.");
			else
				System.out.println("P1:" + player1 + " P2:" + player2);
		}while(player1.equals(player2));
	}

	// Method to get user choice from console for piece to move and target to move to
	public static boolean getUserChoice(){
		boolean shouldCallContinue = false;
		chosen = null;
		do {
			System.out.println((userColor.equals("W"))?player1 + "'s turn:":player2 + "'s turn:");        
			System.out.println("Choose your piece to move: Tell me its x-coordinate. Or, to undo the last move, input -1.");
			myXCoor = InputValidator.nextValidInt(scanInt,-1,7);
			// Implementation for undoing a move
			if (myXCoor == -1){
				if (DeltaBoard.getHistorySize() > 0){ // Prevent undos beyond the start of the game
					toggleUserColor();
				}
				board = DeltaBoard.restorePrev(board);
				System.out.println(clearScreen());
				loopRound();
				shouldCallContinue = true;
				return shouldCallContinue;
			}
			System.out.println("Now tell me its y-coordinate.");
			myYCoor = InputValidator.nextValidInt(scanInt,0,7);
			chosen = board.get(myXCoor,myYCoor);
			System.out.println("The piece you chose is " + chosen);
			if (chosen == null || !chosen.getColor().equals(userColor))
				System.out.println("Please choose a valid piece.");
		} while(chosen == null || !chosen.getColor().equals(userColor));

		System.out.println("Choose a place or piece to move to: Tell me its x-coordinate.");
		targXCoor = InputValidator.nextValidInt(scanInt,0,7);
		System.out.println("Now tell me its y-coordinate.");
		targYCoor = InputValidator.nextValidInt(scanInt,0,7);
		target = board.get(targXCoor, targYCoor);
		return shouldCallContinue;
	}
	public static String about(){
		return 
			"ChessWeasel is a Chess game written by Chesley Tan and Christopher Kim.\n\n" + 
			"It follows all the standard rules of chess and includes\n\n" +
			"all the special rules as well, including castling, en passant, and pawn promotion.\n\n" + 
			"To attempt a castle: Select your king and select your rook as your target.\n\n" +
			"To attempt an En Passant: Select your pawn and select the opponent pawn as your target.\n\n" + 
			"ChessWeasel also includes an undo feature :)\n\n";
	}
	public Board getBoard() {
	    return board;
	}
	public void run() {    	
		//cheat();

		if (!debugMode){
			System.out.println(clearScreen() + clearScreen() + "Welcome to ChessWeasel - A Java Chess Game");
			for (int i = 0;i < 5;i++){
				try{
					System.out.println();
					Thread.sleep(300);
				} catch (InterruptedException e){
					System.err.println(e.getMessage());
				}
			}

			System.out.println("    by Chesley Tan and Christopher Kim");

			for (int i = 0;i < 5;i++){
				try{
					System.out.println();
					Thread.sleep(300);
				} catch (InterruptedException e){
					System.err.println(e.getMessage());
				}
			}


			try{
				BufferedReader asciiReader = new BufferedReader(new InputStreamReader(asciiFile.openStream())); // Credits in ascii.txt 
				String nextLine = "";	
				while (!(nextLine = asciiReader.readLine()).trim().equals("")){  // Read until a blank line is found
					System.out.println(nextLine);
					Thread.sleep(300);
				}
				for (int i = 0;i < 5;i++){
					System.out.println();
					Thread.sleep(300);
				}
			} catch (Exception e){
				System.err.println(e.getMessage());
			}
		}

		System.out.println(clearScreen() + "What would you like to do?\n[1] Start the game!\n[2] Read the instructions/about :D\n");
		System.out.print("Choice: ");
		int choice = InputValidator.nextValidInt(scanInt, 1, 2);
		switch (choice){
			case 1:
				break;
			case 2:
				System.out.println(clearScreen());
				System.out.println(about());
				System.out.println("==================================================================\n");
				System.out.println("Enter 0 to start game.");
				InputValidator.nextValidInt(scanInt, 0, 0);
				break;
		}


		setup();


		System.out.println(clearScreen() + "Board:\n");
		System.out.println(board.toString(userColor));
		while (!isCheckmate()){ // Runs game until a player wins
		    playRound();
        }
        System.out.println("CHECKMATE, " + winner.toUpperCase() + " WINS!"); // Print out victory at termination of while loop
	}
	public void playRound() {
        previousBoard = DeltaBoard.cloneBoard(board);
        // Checks if user's king is in check before moving
        if (isChecked(userColor, getXOfKing(userColor), getYOfKing(userColor), false)){ // Check whether the user's king is in check prior to user's turn
            System.out.println("Your King is in check!");
        } 
        kingPreviouslyChecked = ((King)board.get(getXOfKing(userColor),getYOfKing(userColor))).isChecked(); // Keeps track of whether the user's king was checked before this turn

        // getUserChoice() returns boolean signifying if iteration of the loop should be skipped
        if (getUserChoice()){
            return;
        }

        // handleCastle() returns boolean signifying if iteration of the loop should be skipped
        if (handleCastle()){
            return;
        }

        // handleEnPassant() returns boolean signifying if iteration of the loop should be skipped
        if (handleEnPassant()){
            return;
        }

        // handlePawnJump() returns boolean signifying if iteration of the loop should be skipped
        if (handlePawnJump()){
            return;
        }


        // Regular Move: Checks if king is in check after move
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
                loopRound();
            }

            else { // Case when the resulting position does NOT result in a check on the user's king
                if (chosen.getType().equals("PAWN")){
                    handlePawnPromotion(targXCoor, targYCoor);	
                }
                if (target == null) { // Print feedback information to user
                    System.out.println(clearScreen() + "Successful move: " + chosen + " (" + myXCoor + "," + myYCoor + ") to (" + targXCoor + "," + targYCoor + ").\n");
                }
                else {  // Print feedback information to user
                    System.out.println(clearScreen() + "Successful kill: " + chosen + " (" + myXCoor + "," + myYCoor + ") takes " + target + " (" + targXCoor + "," + targYCoor + ").\n");
                }
                advanceRound();
            }
        }
        else{
            loopRound();
        }
    }

    public void gfxPlayRound(int myXCoor, int myYCoor, int targXCoor, int targYCoor) {
        previousBoard = DeltaBoard.cloneBoard(board);
        // Checks if user's king is in check before moving
        if (isChecked(userColor, getXOfKing(userColor), getYOfKing(userColor), false)){ // Check whether the user's king is in check prior to user's turn
            System.out.println("Your King is in check!");
        } 
        kingPreviouslyChecked = ((King)board.get(getXOfKing(userColor),getYOfKing(userColor))).isChecked(); // Keeps track of whether the user's king was checked before this turn

        this.myXCoor = myXCoor;
        this.myYCoor = myYCoor;
        this.targXCoor = targXCoor;
        this.targYCoor = targYCoor;
        chosen = board.get(myXCoor, myYCoor);
        target = board.get(targXCoor, targYCoor);

        // handleCastle() returns boolean signifying if iteration of the loop should be skipped
        if (handleCastle()){
            return;
        }

        // handleEnPassant() returns boolean signifying if iteration of the loop should be skipped
        if (handleEnPassant()){
            return;
        }

        // handlePawnJump() returns boolean signifying if iteration of the loop should be skipped
        if (handlePawnJump()){
            return;
        }


        // Regular Move: Checks if king is in check after move
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
                loopRound();
            }

            else { // Case when the resulting position does NOT result in a check on the user's king
                if (chosen.getType().equals("PAWN")){
                    handlePawnPromotion(targXCoor, targYCoor);	
                }
                if (target == null) { // Print feedback information to user
                    System.out.println(clearScreen() + "Successful move: " + chosen + " (" + myXCoor + "," + myYCoor + ") to (" + targXCoor + "," + targYCoor + ").\n");
                }
                else {  // Print feedback information to user
                    System.out.println(clearScreen() + "Successful kill: " + chosen + " (" + myXCoor + "," + myYCoor + ") takes " + target + " (" + targXCoor + "," + targYCoor + ").\n");
                }
                advanceRound();
            }
        }
        else{
            loopRound();
        }
    
    }

    public String getUserColor() {
        return userColor;
    }

	public static void main(String[] args){
	    Chess chess = new Chess();
	    chess.run();
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
		board.set(0,0,new Rook("B"));
		board.set(7,0,new Rook("B"));
		board.set(7,7,new Rook("W"));
		board.set(0,7,new Rook("W"));
		board.set(3,5,new Queen("B"));
		board.set(5,5,new Queen("B"));
		// Demonstrate checkmate
		//board.set(4,7,new King("W"));
		//board.set(7,7,new Rook("W"));
		//board.set(7,7,new Rook("W"));
		//board.set(1,0,new Rook("B"));
		//board.set(5,0,new Rook("B"));
		//board.set(7,1,new Pawn("B"));
		//board.set(0,1,new Pawn("B"));
		//board.set(7,6,new Pawn("W"));
		//board.set(6,6,new Pawn("W"));
		//board.set(5,6,new Pawn("W"));
		//board.set(1,6,new Pawn("W"));
		//board.set(0,5,new Pawn("W"));
		//board.set(5,5,new Knight("W"));
		//board.set(3,3,new Bishop("W"));
		//board.set(2,1,new Bishop("W"));
		//board.set(5,2,new King("B"));
		//board.set(6,3,new Queen("W"));
		//blackKingXCoor = 5;
		//blackKingYCoor = 2;

		/* Demonstrate interception
		board.set(4,7,new King("W"));
		board.set(7,7,new Rook("W"));
		board.set(7,7,new Rook("W"));
		board.set(1,0,new Rook("B"));
		board.set(5,0,new Rook("B"));
		board.set(7,1,new Pawn("B"));
		board.set(0,1,new Pawn("B"));
		board.set(7,6,new Pawn("W"));
		board.set(6,6,new Pawn("W"));
		board.set(5,6,new Pawn("W"));
		board.set(1,6,new Pawn("W"));
		board.set(0,5,new Pawn("W"));
		board.set(5,5,new Knight("W"));
		board.set(3,3,new Bishop("W"));
		board.set(2,1,new Bishop("W"));
		board.set(5,2,new King("B"));
		board.set(6,3,new Queen("W"));
		blackKingXCoor = 5;
		blackKingYCoor = 2;
		*/
	
		// Some diagnostic stuff
		//isChecked("B",5,2,false);
		//System.out.println("Black King can move:" + canMove("B",5,2));
		//System.out.println("Black can intercept:" + canIntercept("B",5,2));
		//System.out.println("Black checked by: " + ((King)board.get(5,2)).getCheckedBy().get(0)[0] + ((King)board.get(5,2)).getCheckedBy().get(0)[1]); 
	}
}
