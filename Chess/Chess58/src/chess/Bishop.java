/**
 * Bishop Class is responsible for implementing the canMove method based on the chess rules for a legal move with a bishop.
 * Bishop Class also implements isPathClear method to make sure there are no pieces obstructing movement of the bishop.
 * @author Waleed Rizwan
 * @author Mohammad (Massab) Sohail
 */

package chess;

import java.util.ArrayList;

public class Bishop extends Piece {

    /**
     * constructor calls the superclass Piece constructor to assign color and name to each piece
     * @param color The piece can have color "w" for white, or "b" for black
     * @param name The abbreviated name of the piece: "B" for Bishop
     */
    public Bishop(String color, String name){
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
     * @param startingCoordOfPrevMove starting row and column of the previous move
     * @return true if move is possible with given coordinates for a specific piece, false otherwise
     */
    @Override
    public boolean canMove(int sRow, int sCol, int dRow, int dCol,  Board board, Piece prevMove, int [] startingCoordOfPrevMove) {

        //bishop can move to a tile if the x and y are moved up or down by the same amount... in other words, deltaX must equal deltaY
        int deltaX =Math.abs(sRow-dRow);
            int deltaY =Math.abs(sCol-dCol);
            if(deltaX == deltaY){
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
        //1.is bishop going up and right?
        //2.is bishop going up and left?
        //3.is bishop going down and right?
        //4.is bishop going down and left?
        //going to the right means dCol > sCol and left means dCol< sCol.
        //going up means sRow > dRow and down means sRow < dRow.

        //going up
        if(sRow > dRow){
            //up and right
            if (dCol > sCol){
                int tempCol = dCol - 1;
                for(int tempRow = dRow + 1; tempRow < sRow; tempRow++){
                    Piece tempPiece = gameBoard[tempRow][tempCol];
                    if(tempPiece!= null){
                        return false;
                    }
                    tempCol--;
                }
            }

            //up and left
            else if(dCol < sCol){
                int tempCol = dCol + 1;
                for(int tempRow = dRow + 1; tempRow < sRow; tempRow++){
                    Piece tempPiece = gameBoard[tempRow][tempCol];
                    if(tempPiece!= null){
                        return false;
                    }
                    tempCol++;
                }
            }

        }

        //going down
        else if(sRow < dRow){
            int i = 0, j = 0;
            //if right
            if(dCol> sCol){
                int tempCol = dCol - 1;
                for(int tempRow = dRow - 1; tempRow > sRow; tempRow--){
                    Piece tempPiece = gameBoard[tempRow][tempCol];
                    if(tempPiece!= null){
                        return false;
                    }
                    tempCol--;
                }
            }

            //if left
            else if(dCol < sCol){
                int tempCol = dCol + 1;
                for(int tempRow = dRow - 1; tempRow > sRow; tempRow--){
                    Piece tempPiece = gameBoard[tempRow][tempCol];
                    if(tempPiece!= null){
                        return false;
                    }
                    tempCol++;
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
        for (int dRow = sRow, dCol = sCol; dRow >= 0 && dCol >= 0; dRow--, dCol--) {
            if (canMove(sRow, sCol, dRow, dCol, board, null, null) && isPathClear(sRow,sCol,dRow,dCol,board)) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(dRow);
                move.add(dCol);
                moves.add(move);
            }
        }
        for (int dRow = sRow, dCol = sCol; dRow <= 7 && dCol <= 7; dRow++, dCol++) {
            if (canMove(sRow, sCol, dRow, dCol, board, null, null) && isPathClear(sRow,sCol,dRow,dCol,board)) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(dRow);
                move.add(dCol);
                moves.add(move);
            }
        }
        for (int dRow = sRow, dCol = sCol; dRow >= 0 && dCol <= 7; dRow--, dCol++) {
            if (canMove(sRow, sCol, dRow, dCol, board, null, null) && isPathClear(sRow,sCol,dRow,dCol,board)) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(dRow);
                move.add(dCol);
                moves.add(move);
            }
        }
        for (int dRow = sRow, dCol = sCol; dRow <= 7 && dCol >= 0; dRow++, dCol--) {
            if (canMove(sRow, sCol, dRow, dCol, board, null, null) && isPathClear(sRow,sCol,dRow,dCol,board)) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(dRow);
                move.add(dCol);
                moves.add(move);
            }
        }
        return moves;
    }




}
