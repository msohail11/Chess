/**
 * Knight Class is responsible for implementing the canMove method based on the chess rules for a legal move with a knight.
 * The Knight class does not need to worry about the path being clear, because it can hop over pieces. It only needs to make sure it cannot go to a tile with a piece of the same color already on it.
 * @author Waleed Rizwan
 * @author Mohammad (Massab) Sohail
 */

package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Knight extends Piece {

    /**
     * constructor calls the superclass Piece constructor to assign color and name to each piece
     * @param color The piece can have color "w" for white, or "b" for black
     * @param name The abbreviated name of the piece: "N" for Knight
     */
    public Knight(String color, String name){
        super(color,name);
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
     * @return true if move is possible with given coordinates for a Knight, false otherwise
     */
    @Override
    public boolean canMove(int sRow, int sCol, int dRow, int dCol,  Board board, Piece prevMove, int [] CoordOfPrevMove) {
        Piece srcPiece = Board.getBoard()[sRow][sCol];
        Piece destPiece = Board.getBoard()[dRow][dCol];
        int upOneRow =  sRow + 1;
        int upOneCol =  sCol + 1;
        int upTwoRows = sRow + 2;
        int upTwoCols = sCol + 2;
        int downOneRow =  sRow - 1;
        int downOneCol =  sCol - 1;
        int downTwoRows = sRow - 2;
        int downTwoCols = sCol - 2;

        //A knight can move in 2 ways:
        //      1. 2 squares horizontally and one square vertically.
        //      2. 2 squares vertically and one square horizontally.


        //PIECE IS BEING CAPTURED
        if(destPiece != null){
            if(srcPiece.getColor().equals(destPiece.getColor())) {
                return false;
            }
            //MOVES ONE ROW, BUT TWO COLS
            if(upOneRow == dRow || downOneRow == dRow){
                if(upTwoCols == dCol || downTwoCols == dCol){
                    return true;
                }
            }
            //MOVES TWO ROWS, BUT ONE COL
            else if(upTwoRows == dRow || downTwoRows == dRow){
                if(upOneCol == dCol || downOneCol == dCol){
                    return true;
                }
            }
        }
        //PIECE IS GOING TO EMPTY TILE
        else{
            //MOVES ONE ROW, BUT TWO COLS
            if(upOneRow == dRow || downOneRow == dRow){
                if(upTwoCols == dCol || downTwoCols == dCol){
                    return true;
                }
            }
            //MOVES TWO ROWS, BUT ONE COL
            else if(upTwoRows == dRow || downTwoRows == dRow){
                if(upOneCol == dCol || downOneCol == dCol){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *Checks to see if there are any blocking pieces in the path of the piece that is trying to be moved.
     * @param sRow Row of starting Tile in the board array
     * @param sCol Column of starting Tile in the board array
     * @param dRow Row of destination Tile in the board array
     * @param dCol Column of destination Tile in the board array
     * @param board instance of the game board that the game is being played on
     * @return true if the path is clear, false otherwise
     */
    @Override
    public boolean isPathClear(int sRow, int sCol, int dRow, int dCol, Board board) {
        return true;
    }



    private boolean isLandableSpace(final int row, final int col) {
        final int lowerBound = 0;
        final int upperBound = 8 - 1;
        return row >= lowerBound && col >= lowerBound && row <= upperBound && col <= upperBound
                && (Board.getBoard()[row][col] == null || !this.getColor().equals(Board.getBoard()[row][col].getColor()));
    }


    /**
     * Gives a list of viable moves that a piece can make
     * @param sRow starting row of piece
     * @param sCol starting col of piece
     * @param board instance of chessboard
     * @return Arraylist of all viable moves
     */

    @Override
    public ArrayList<ArrayList<Integer>> listMoves(int sRow, int sCol, Board board) {
        return new ArrayList<>(Arrays.asList(
                Arrays.asList(sRow + 2, sCol + 1),
                Arrays.asList(sRow + 2, sCol - 1),
                Arrays.asList(sRow - 2, sCol + 1),
                Arrays.asList(sRow - 2, sCol - 1),
                Arrays.asList(sRow + 1, sCol + 2),
                Arrays.asList(sRow + 1, sCol - 2),
                Arrays.asList(sRow - 1, sCol + 2),
                Arrays.asList(sRow - 1, sCol - 2)
        ).stream().filter(move -> isLandableSpace(move.get(0), move.get(1))).map(ArrayList::new).collect(Collectors.toList()));
    }



}
