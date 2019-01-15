
package chess;

import java.util.ArrayList;
import java.util.Iterator;
import javafx.collections.*;

public class ChessBoard {

	private Square board[][];
	private ChessColour activeColour;
	private int fullMove;
	private ArrayList<Piece> takenPieces;
	private ArrayList<Piece> whiteTakenPieces = new ArrayList<Piece>();
	private ArrayList<Piece> blackTakenPieces = new ArrayList<Piece>();
	private ObservableList<Piece> obsWhiteTakenPieces = FXCollections.observableList(whiteTakenPieces);
	private ObservableList<Piece> obsBlackTakenPieces = FXCollections.observableList(blackTakenPieces);

	//Create 2-dimensional gameboard
	public ChessBoard() {
		board = new Square[8][8];
		for (int c = 0; c < 8; c++)
			for (int r = 0; r < 8; r++)
				board[c][r] = new Square(new Coordinate(c, r));
		reset();
		activeColour = ChessColour.WHITE;
		fullMove = 1;

		takenPieces = new ArrayList<Piece>();

	}
	//Create 2-dimensional gameboard
	public ChessBoard(Coordinate positions[], Piece pieces[]) throws IllegalArgumentException {
		if (positions.length != pieces.length)
			throw new IllegalArgumentException("The list of positions must correspond to the list of pieces");

		board = new Square[8][8];
		for (int r = 0; r < 8; r++)
			for (int c = 0; c < 8; c++)
				board[r][c] = new Square(new Coordinate(r, c));
		for (int i = 0; i < positions.length; i++) {
			board[positions[i].getColumnNumber()][positions[i].getRowNumber()].addPiece(pieces[i]);
		}
		activeColour = ChessColour.WHITE;
		fullMove = 1;
	}

	//Method initializes the chessboard with the locations of all pieces
	private void reset() {
		// White rows
		board[0][0].addPiece(new Rook(ChessColour.WHITE));
		board[7][0].addPiece(new Rook(ChessColour.WHITE));
		board[1][0].addPiece(new Knight(ChessColour.WHITE));
		board[6][0].addPiece(new Knight(ChessColour.WHITE));
		board[2][0].addPiece(new Bishop(ChessColour.WHITE));
		board[5][0].addPiece(new Bishop(ChessColour.WHITE));
		board[3][0].addPiece(new Queen(ChessColour.WHITE));
		board[4][0].addPiece(new King(ChessColour.WHITE));

		for (int c = 0; c < 8; c++)
			board[c][1].addPiece(new Pawn(ChessColour.WHITE));

		// Black rows
		board[0][7].addPiece(new Rook(ChessColour.BLACK));
		board[7][7].addPiece(new Rook(ChessColour.BLACK));
		board[1][7].addPiece(new Knight(ChessColour.BLACK));
		board[6][7].addPiece(new Knight(ChessColour.BLACK));
		board[2][7].addPiece(new Bishop(ChessColour.BLACK));
		board[5][7].addPiece(new Bishop(ChessColour.BLACK));
		board[3][7].addPiece(new Queen(ChessColour.BLACK));
		board[4][7].addPiece(new King(ChessColour.BLACK));

		for (int c = 0; c < 8; c++)
			board[c][6].addPiece(new Pawn(ChessColour.BLACK));

		// Middle rows
		Piece p;
		for (int c = 0; c < 8; c++)
			for (int r = 2; r < 6; r++)
				p = board[c][r].deletePiece();

	}

	//Method returns square on chessboard at a specified coordinate
	protected Square getSquare(Coordinate c) {
		return board[c.getColumnNumber()][c.getRowNumber()];
	}

	//Method is used by players to make moves and take away pieces
	public boolean move(Coordinate src, Coordinate dest) {

		Square srcSquare = this.getSquare(src);
		if (!srcSquare.isOccupied())
			return false;

		Piece p = srcSquare.getPiece();
		if (!p.getColour().equals(activeColour))
			return false;

		if (p.isLegalMove(this, src, dest)) {
			Square destSquare = this.getSquare(dest);
			Piece takenPiece = destSquare.addPiece(p);
			if (takenPiece != null) {
				if (takenPiece.getColour() == ChessColour.BLACK) {
					obsBlackTakenPieces.add(takenPiece);
				} else {
					obsWhiteTakenPieces.add(takenPiece);
				}

			}
			srcSquare.deletePiece();
			activeColour = (activeColour == ChessColour.BLACK) ? ChessColour.WHITE : ChessColour.BLACK;
			if (activeColour == ChessColour.WHITE)
				fullMove++;
			// fullMove is incremented only after BLACK has moved.
			return true;
		} else
			return false;
	}

	// Method registers listener on white and black observable lists
	public void addTakenObserver(ListChangeListener listener) {
		obsWhiteTakenPieces.addListener(listener);
		obsBlackTakenPieces.addListener(listener);
	}

	//Print board to console using my own custom notation
	public String toString() {

		String s = "Board\n";
		for (int r = 7; r >= 0; r--) {
			for (int c = 0; c < 8; c++) {
				Piece p = board[c][r].getPiece();
				s += (p == null) ? " " : p.getShortName();
				s += "_";
			}
			s += "\n";
		}
		return s;
	}

	//Print Board out to console using FEN notation
	public String toFEN() {

		String s = "";
		for (int r = 7; r >= 0; r--) {
			for (int c = 0; c < 8; c++) {
				Piece p = board[c][r].getPiece();
				s += (p == null) ? " " : p.getShortName();
			}
			s += "/";
		}
		s += " " + ((activeColour == ChessColour.WHITE) ? "w" : "b");
		s += " " + fullMove;
		s += "\n";

		return s;
	}

	// Method prints out list of taken pieces starting with white
	public String toTakenString() {
		Iterator<Piece> whiteIterator = whiteTakenPieces.iterator();
		Iterator<Piece> blackIterator = blackTakenPieces.iterator();
		String black = "";
		String white = "";
		
		//Iterate through white taken pieces
		while (whiteIterator.hasNext()) {
			Piece piece = (Piece) whiteIterator.next();
				white += piece.getShortName();
		}
		//Iterate through black taken pieces
		while (blackIterator.hasNext()) {
			Piece piece = (Piece) blackIterator.next();
				black += piece.getShortName();
		}
		return white + "," + black;
	}

}
