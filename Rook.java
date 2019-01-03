/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;


public class Rook extends Piece {
    public Rook(ChessColour colour) {
        super(colour, ChessPieces.ROOK);
    }
    
    public boolean isLegalMove(ChessBoard board,  Coordinate src, Coordinate dest){
        if (!super.isLegalMove(board, src, dest)) return false;
        
        if (src.getColumn() != dest.getColumn() ) return false;
        int x = src.getColumn();
        int srcY = src.getRow();
        int destY = src.getRow();
        if (srcY > destY) {
           int temp = srcY;
            srcY = destY;
            destY = temp;   
        }
        srcY++; destY--;
        for (int i=srcY; i<=destY;i++) {
            if (board.getSquare(new Coordinate(x,i)).isOccupied()) return false;      
        }
        return true;
    }
}
