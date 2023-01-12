/**
 * Piece is the base class for all chess pieces. It contains 
 * the skeleton, meaning it has all the shared attributes of every chess piece like the 
 * name and color, and also the abstract canMove method and isPathClear method.
 * @author Waleed Rizwan
 * @author Mohammad (Massab) Sohail
 */

package chess;

import java.util.ArrayList;

public abstract class Piece {
    private String name, color;
    public boolean enpassant;
    public boolean hasNotMoved;


    /**
     * constructor that assigns color and name to each piece
     * enpassant: true if this piece just moved up two columns
     * hasNotMoved: No piece initialized has moved by default, so set to true
     * @param color The piece can have color "w" for white, or "b" for black
     * @param name The abbreviated name of the piece e.g: "P" for Pawn, "Q" for Queen, etc..
     */
    public Piece(String color, String name){
        this.color = color;
        this.name = name;
        this.enpassant = false;
        this.hasNotMoved = true;
    }


    /**
     *Checks to see if the piece can move to a given tile with the passed in coordinates.
     * @param sRow Row of starting Tile in the board array
     * @param sCol Column of starting Tile in the board array
     * @param dRow Row of destination Tile in the board array
     * @param dCol Column of destination Tile in the board array
     * @param board instance of the game board that the game is being played on
     * @param prevMove The piece that was moved last turn
     * @param CoordOfPrevMove starting row and column of the previous move
     * @return true if move is possible with given coordinates for a specific piece, false otherwise
     */
    public abstract boolean canMove(int sRow, int sCol, int dRow, int dCol,  Board board, Piece prevMove, int [] CoordOfPrevMove);


    /**
     *Checks to see if there are any blocking pieces in the path of the piece that is trying to be moved.
     * @param sRow Row of starting Tile in the board array
     * @param sCol Column of starting Tile in the board array
     * @param dRow Row of destination Tile in the board array
     * @param dCol Column of destination Tile in the board array
     * @param board instance of the game board that the game is being played on
     * @return true if the path is clear, false otherwise
     */
    public abstract boolean isPathClear(int sRow, int sCol, int dRow, int dCol,  Board board);

    /**
     * Gives a list of viable moves that a piece can make
     * @param sRow starting row of piece
     * @param sCol starting col of piece
     * @param board instance of chessboard
     * @return Arraylist of all viable moves
     */
    public abstract ArrayList<ArrayList<Integer>>  listMoves(int sRow, int sCol, Board board);



    /**
     * Gives the user the color of the piece
     * @return the color of the piece in the form of a String
     */
    public String getColor() {
        return color;
    }


    /**
     * Gives the user the name of the piece
     * @return the name of the piece in the form of a String
     */
    public String getName() {
        return name;
    }

    /**
     * Creates a string representation of this piece e.g: "wP" for a white pawn
     * @return string representation of this piece
     */
    @Override
    public String toString() {
        return color + name;
    }
}
