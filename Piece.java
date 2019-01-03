
package chess;

public class Piece {

	private ChessColour colour;
	private ChessPieces name;
	private char shortName;
	private String imageName;

	protected Piece(ChessColour colour, ChessPieces name) {
		this.colour = colour;
		this.name = name;
		this.shortName = name.getShortName();
		if (colour == ChessColour.BLACK)
			this.shortName = Character.toLowerCase(this.shortName);
		imageName = colour.toString().toLowerCase() + "_" + name.toString().toLowerCase() + ".png";

	}

	public ChessColour getColour() {
		return this.colour;
	}

	public ChessPieces getName() {
		return this.name;
	}

	public char getShortName() {
		return this.shortName;
	}

	public String getImageName() {
		return this.imageName.toLowerCase();
	}

	public String toString() {
		return colour + " " + name;
	}

	// Method checks for legal moves
	public boolean isLegalMove(ChessBoard board, Coordinate src, Coordinate dest) {

		Piece movingPiece = board.getSquare(src).getPiece();

		Piece takenPiece = board.getSquare(dest).getPiece();
		if ((takenPiece != null) && (takenPiece.getColour().equals(this.colour)))
			return false;
		return true;

	}

}
