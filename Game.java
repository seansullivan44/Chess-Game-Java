package chess;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.*;
import java.util.List;
import javafx.collections.*;

public class Assign4 extends Application implements ListChangeListener {

	// Create board and button objects
	private ChessBoard board = new ChessBoard();
	private Button[] whiteTakenSquare = new Button[16];
	private Button[] blackTakenSquare = new Button[16];

	@Override
	public void start(Stage primaryStage) {

		BorderPane root = new BorderPane(); // Root branch
		GridPane grid = new GridPane(); // Holds buttons for the game board grid
		Pane leftVBox = new VBox(); // Holds the taken white pieces
		Pane rightVBox = new VBox(); // Holds the taken black pieces

		board.addTakenObserver(this);

		SquareEventHandler handle = new SquareEventHandler(board);

		// Create buttons for left Vertical squares (Taken White Pieces)
		for (int i = 0; i < 16; i++) {
			whiteTakenSquare[i] = new Button("");
			whiteTakenSquare[i].setOnAction(handle);
			whiteTakenSquare[i].setStyle("-fx-background-color: grey; -fx-border-color:white;");
			whiteTakenSquare[i].setMinSize(90, 45);
			leftVBox.getChildren().add(whiteTakenSquare[i]);
		}

		// Create button for right Vertical squares (Taken Black Pieces)
		for (int i = 0; i < 16; i++) {
			blackTakenSquare[i] = new Button("");
			blackTakenSquare[i].setOnAction(handle);
			blackTakenSquare[i].setStyle("-fx-background-color: grey; -fx-border-color:white;");
			blackTakenSquare[i].setMinSize(90, 45);
			rightVBox.getChildren().add(blackTakenSquare[i]);

		}

		Coordinate Boardcoordinates[] = new Coordinate[64]; // Used to keep track of grid coordinates
		Button Gbtn[][] = new Button[8][8]; // Create grid of square buttons

		for (int c = 0; c < 8; c++) {
			int rowCor = 7;
			int i = 0;

			for (int r = 0; r < 8; r++) {
				Boardcoordinates[i] = new Coordinate(c, rowCor - r); // Create coordinate for place on grid
				Gbtn[c][r] = new Button("" + Boardcoordinates[i].name()); // Create new button with coordinate text
				Gbtn[c][r].setOnAction(handle);

				// Initialize color for respective button
				if ((c + r) % 2 != 0) {
					Gbtn[c][r].setStyle("-fx-background-color: white;");
				} else {
					Gbtn[c][r].setStyle("-fx-background-color: grey;");
				}

				Gbtn[c][r].setMinSize(90, 90); // Set size of grid button
				grid.add(Gbtn[c][r], c, r); // Add button to grid

			}
		}

		// Set graphics for initial game board
		for (Button[] button : Gbtn) {
			for (Button buttonInside : button) {

				Piece piece = board.getSquare(new Coordinate(buttonInside.getText())).getPiece();
				if (piece != null) {
					buttonInside.setGraphic(new ImageView(new Image("file:src/chess/images/" + piece.getImageName())));
				}
			}
		}

		// Arrange scene graph
		root.setCenter(grid);
		root.setLeft(leftVBox);
		root.setRight(rightVBox);

		Scene scene = new Scene(root, 900, 720); // Set scene size

		primaryStage.setTitle("Chess");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void onChanged(Change c) {

		while (c.next()) {
			if (c.wasAdded()) { 
				int index = c.getFrom(); 
				List<Piece> pieces = c.getAddedSubList();
				for (Piece p : pieces) {
					// Check for piece color and set appropriate graphic
					if (pieces.get(0).getColour() == ChessColour.WHITE) {
						whiteTakenSquare[index]
								.setGraphic(new ImageView(new Image("file:src/chess/images/" + p.getImageName())));
					} else {
						blackTakenSquare[index]
								.setGraphic(new ImageView(new Image("file:src/chess/images/" + p.getImageName())));
					}

				}
			}
		}

	}

}

class SquareEventHandler implements EventHandler {

	private ChessBoard board = new ChessBoard();
	private boolean firstClick;
	private Button firstButton;

	SquareEventHandler(ChessBoard board) {
		this.board = board;
		firstClick = true;
	}

	@Override
	public void handle(Event event) {
		Button b = (Button) event.getSource();

		if (firstClick != true && board.move(new Coordinate(firstButton.getText()), new Coordinate(b.getText()))) {
			b.setGraphic(firstButton.getGraphic());
			firstButton.setGraphic(null);
			firstClick = true;
		} else {
			firstButton = b;
			firstClick = false;
		}

	}

}
