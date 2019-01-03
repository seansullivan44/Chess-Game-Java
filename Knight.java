package chess;

public class Knight extends Piece {

	public Knight(ChessColour colour) {
		super(colour,ChessPieces.KNIGHT);
	}
	
	//Method Checks if move is legal
	public boolean isLegalMove (ChessBoard board, Coordinate src, Coordinate dest) {
		
		//Check if square is occupied
		if(super.isLegalMove(board, src, dest) == false) {
			return false;
		}
		//Check if there is an forwards L shape
		if(Math.abs(board.getSquare(src).getRowNumber() - board.getSquare(dest).getRowNumber()) == 2 && Math.abs(board.getSquare(src).getColumnNumber() - board.getSquare(dest).getColumnNumber()) == 1 ) {
			return true;
		}
		//Check if there is a sideways L shape
		if(Math.abs(board.getSquare(src).getColumnNumber() - board.getSquare(dest).getColumnNumber()) == 2 && Math.abs(board.getSquare(src).getRowNumber() - board.getSquare(dest).getRowNumber()) == 1 ) {
			return true;
		}
		
		return false; //Return false if none of the valid moves were made
		
		
	}
		
		
	
	
	
}
