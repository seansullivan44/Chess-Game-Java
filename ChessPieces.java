/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;


public enum ChessPieces {
    PAWN ('P'),
    KNIGHT ('N'),
    BISHOP ('B'),
    ROOK ('R'),
    QUEEN ('Q'),
    KING ('K');
    
    private final char shortName;

    ChessPieces (char shortName) {
        this.shortName = shortName;
    }
    
    public char getShortName() {
        return this.shortName;
    }
}


