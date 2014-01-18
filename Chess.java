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
    private static boolean isChecked = false;
    private static int counter = 0;
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
    public static boolean isChecked(String color) { 	
	for (int x = 0; x < 8; x++) {
	    for (int y = 0; y < 8; y++) {
		ChessPiece piece = board.get(x,y);
		if (piece != null && !piece.getColor().equals(color)) {
	            
		    if ((piece.getColor()).equals("W")) {
			
			if (piece.validAttack(x, y,blackKingXCoor,blackKingYCoor, board)) {
			    return true; 
			}
		    }
		    else {
			
			if (piece.validAttack(x, y,whiteKingXCoor,whiteKingYCoor, board)) {
                            return true;
			}
		    }
		}
	    }
	}
	return false;
    }
	//public boolean frozenKing() {
	    
		    
		    
	    
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
	public static void toggleUserColor(){
		if (userColor.equals("W"))
			userColor = "B";
		else
			userColor = "W";
	}
	public static void main(String[] args){
		//cheat();
		
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
			// Note: Coordinates are reversed because board stores coordinates in a format that resembles (Y,X) whereas computation and user input should be in format (X,Y)
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
			board.set(targXCoor, targYCoor, chosen);
			    
			if (chosen.getType().equals("KING")){    			    			    updateKingCoor(chosen.getColor(), targXCoor, targYCoor);
				if (isChecked(userColor)) {
					
	                     		board.set(myXCoor,myYCoor,chosen);
			     		board.set(targXCoor, targYCoor, target);						     
			     System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nInvalid Move: Your King is still in check.");
				updateKingCoor(chosen.getColor(),  myXCoor, myYCoor);
			
			}
			 else {
			      board.set(myXCoor,myYCoor,chosen);
			      board.set(targXCoor, targYCoor, target);
			      board.move(myXCoor,myYCoor,targXCoor,targYCoor,userColor);
			      updateKingCoor(chosen.getColor(), targXCoor, targYCoor);
			      toggleUserColor();
			 }
			}
			else if (isChecked(userColor)) {
				board.set(myXCoor,myYCoor,chosen);
			     		board.set(targXCoor, targYCoor, target);						     
			     System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nInvalid Move: Your King is still in check.");
			
			}
			 else {
			    board.set(myXCoor,myYCoor,chosen);
			    board.set(targXCoor, targYCoor, target);
			    board.move(myXCoor,myYCoor,targXCoor,targYCoor,userColor);
			    toggleUserColor();
			 }
			    
			System.out.println(board.toString(userColor));
		}
	}
	public static void cheat(){
		for (int u = 0;u<8;u++){
			for (int i = 0;i<8;i++){
				board.set(i,u,null);
			}
		}
		board.set(4,7,new King("W"));
		board.set(7,7,new Rook("W"));
	}
}
