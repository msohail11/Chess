/**
 * Rook Class is responsible for implementing the canMove method based on the chess rules for a legal move with a rook.
 * @author Waleed Rizwan
 * @author Mohammad (Massab) Sohail
 */

package chess;

import java.util.ArrayList;

public class Rook extends Piece {


    /**
     * constructor calls the superclass Piece constructor to assign color and name to each piece
     * Then sets hasNotMovedYet to true because the piece every piece has not moved during initialization by default.
     * @param color The piece can have color "w" for white, or "b" for black
     * @param name The abbreviated name of the piece: "R" for Rook
     */
    public Rook(String color, String name){
        super(color,name);
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
     * @return true if move is possible with given coordinates for a Rook, false otherwise
     */
    @Override
    public boolean canMove(int sRow, int sCol, int dRow, int dCol,  Board board, Piece prevMove, int [] CoordOfPrevMove) {
        if (dRow < 0 || dCol < 0 || dRow > 7 || dCol > 7) {
            return false;
        }


        //same row moving to a diff col.. e.g: a1 f1 -- horizontally
        if(sRow == dRow && sCol != dCol){
            if(isPathClear(sRow, sCol, dRow, dCol, board)){
                hasNotMoved = false;
            }
            return true;
        }

        //same col moving to a diff row.. e.g: a1 a4 --vertically
        if(sCol == dCol && sRow != dRow){
            if(isPathClear(sRow, sCol, dRow, dCol, board)){
                hasNotMoved = false;
            }
            return true;
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
        Piece[][] gameBoard = board.getBoard();
        Piece sPiece = gameBoard[sRow][sCol];
        Piece dPiece = gameBoard[dRow][dCol];

        //if trying to attk same color piece
        if(sPiece != null && dPiece != null){
            if(sPiece.getColor().equals(dPiece.getColor())){
                return false;
            }
        }
        //if rook is moving up check column for pieces (vertically)

        //rook is moving up the board
        if(sRow > dRow){
            for(int tempRow = dRow + 1; tempRow < sRow; tempRow++){
                Piece tempPiece = gameBoard[tempRow][sCol];
                if(tempPiece!= null){
                    return false;
                }
            }

        }
        //rook is moving down the board
        else if(sRow < dRow){
            for(int tempRow = dRow - 1; tempRow > sRow; tempRow--){
                Piece tempPiece = gameBoard[tempRow][sCol];
                if(tempPiece!= null){
                    return false;
                }
            }
        }


        //if rook is moving is across the row (horizontally)

        //moving to right side of board
        else if(sCol < dCol){
            for(int tempCol = dCol - 1; tempCol > sCol; tempCol--){
                Piece tempPiece = gameBoard[sRow][tempCol];
                if(tempPiece!= null){
                    return false;
                }
            }
        }

        //moving to left side of baord
        else if(sCol > dCol){
            for(int tempCol = dCol + 1; tempCol < sCol; tempCol++){
                Piece tempPiece = gameBoard[sRow][tempCol];
                if(tempPiece!= null){
                    return false;
                }
            }
        }

        return true;
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
        ArrayList<ArrayList<Integer>> moves = new ArrayList<>();
        Piece[][] pieces = Board.getBoard();
        // Up check
       boolean rookHasNotMovedVal = board.getBoard()[sRow][sCol].hasNotMoved;

        for (int pos = sRow; pos >= 0; pos--) {

            if (canMove(sRow, sCol, pos, sCol, board, null, null)) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(pos);
                move.add(sCol);
                moves.add(move);
            }
        }
        // Down check
        for (int pos = sRow; pos <= pieces[0].length; pos++) {
            if (canMove(sRow, sCol, pos, sCol, board, null, null)) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(pos);
                move.add(sCol);
                moves.add(move);

            }
        }
        // Left check
        for (int pos = sCol; pos >= 0; pos--) {
            if (canMove(sRow, sCol, sRow, pos, board, null, null)) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(sRow);
                move.add(pos);
                moves.add(move);

            }
        }
        // Right check
        for (int pos = sCol; pos <= pieces[sRow].length; pos++) {
            if (canMove(sRow, sCol, sRow, pos, board, null, null)) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(sRow);
                move.add(pos);
                moves.add(move);
            }
        }
        board.getBoard()[sRow][sCol].hasNotMoved = rookHasNotMovedVal;

        return moves;
    }



}