/**
 * King Class is responsible for implementing the 
 * canMove method based on the chess rules for a legal move with a king.
 * King Class also implements isPathClear method to make sure there are no pieces obstructing movement of the bishop, and also allows castling.
 * @author Waleed Rizwan
 * @author Mohammad (Massab) Sohail
 */

package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class King extends Piece {

    /**
     * constructor calls the superclass Piece constructor to assign color and name to each piece
     * @param color The piece can have color "w" for white, or "b" for black
     * @param name The abbreviated name of the piece: "K" for King
     */
    public King(String color, String name){
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
     * @return true if move is possible with given coordinates for a pawn, false otherwise
     */
    @Override
    public boolean canMove(int sRow, int sCol, int dRow, int dCol,  Board board, Piece prevMove, int [] CoordOfPrevMove) {
        Piece srcPiece =  Board.getBoard()[sRow][sCol];
        Piece destPiece = Board.getBoard()[dRow][dCol];
        Piece wKingSideRook = Board.getBoard()[7][7];
        Piece bKingSideRook = Board.getBoard()[0][7];
        Piece wQueenSideRook = Board.getBoard()[7][0];
        Piece bQueenSideRook = Board.getBoard()[0][0];
        String color = srcPiece.getColor();
        /*

        castling rules:
        1) No pieces in between
        2)Rook and King can never have moved
        3)king moves 2 spaces to right or left and rook goes on other side
        4)Need to make sure you are not putting yourself into check or crossing check... ******* This might take some time... implemnt inCheck first.

         */

        //white castle
        if(srcPiece.toString().equals("wK")){



            //king side castle(bottom-right side)

            if(dRow == 7 && sRow == 7 && (dCol == sCol + 2) && (srcPiece.hasNotMoved && wKingSideRook.hasNotMoved) && (Board.getBoard()[7][6] == null && Board.getBoard()[7][5] == null) ){
                Board.getBoard()[7][6] = srcPiece;
                Board.getBoard()[7][5] = wKingSideRook;
                Board.getBoard()[7][7] = null;
                srcPiece.hasNotMoved = false;
                wKingSideRook.hasNotMoved = false;
                return true;
            }

            //queen side castle(bottom-left side)

            else if(dRow == 7 && sRow == 7 && (dCol == sCol - 2) && (srcPiece.hasNotMoved && wQueenSideRook.hasNotMoved) && (Board.getBoard()[7][1] == null && Board.getBoard()[7][2] == null && Board.getBoard()[7][3] == null) ){
                Board.getBoard()[7][2] = srcPiece;
                Board.getBoard()[7][3] = wQueenSideRook;
                Board.getBoard()[7][0] = null;
                srcPiece.hasNotMoved = false;
                wQueenSideRook.hasNotMoved = false;
                return true;
            }
        }//end white castle


        //black castle
       else if(srcPiece.toString().equals("bK")){

            //king side castle(top-right side)

            if(dRow == 0 && sRow == 0 && (dCol == sCol + 2) && (srcPiece.hasNotMoved && bKingSideRook.hasNotMoved) && (Board.getBoard()[0][6] == null && Board.getBoard()[0][5] == null) ){
                Board.getBoard()[0][6] = srcPiece;
                Board.getBoard()[0][5] = bKingSideRook;
                Board.getBoard()[0][7] = null;
                srcPiece.hasNotMoved = false;
                bKingSideRook.hasNotMoved = false;
                return true;
            }

            //queen side castle(top-left side)

            if(dRow ==  0 && sRow == 0 && (dCol == sCol - 2) && (srcPiece.hasNotMoved && bQueenSideRook.hasNotMoved) && (Board.getBoard()[0][1] == null && Board.getBoard()[0][2] == null && (Board.getBoard()[0][3] == null)) ){
                Board.getBoard()[0][2] = srcPiece;
                Board.getBoard()[0][3] = bQueenSideRook;
                Board.getBoard()[0][0] = null;
                srcPiece.hasNotMoved = false;
                bQueenSideRook.hasNotMoved = false;
                return true;
            }
        }//end black castle



        //if king tries to capture own piece
        if(srcPiece != null && destPiece != null){

            if(srcPiece.getColor().equals(destPiece.getColor())){
                return false;
            }
        }


        //moving one diagonal square
        /*diagonals*/
        if (dCol == sCol - 1 && (dRow == sRow + 1 || dRow == sRow - 1)) //move left, then up or down
        {
            hasNotMoved = false;
            return true;
        }

        if (dCol == sCol + 1 && (dRow == sRow + 1 || dRow == sRow - 1)) //move right, then up or down
        {
            hasNotMoved = false;
            return true;
        }

        //moving vertical one square
        if((dCol == sCol && (Math.abs(dRow - sRow) == 1) && isPathClear(sRow, sCol, dRow, dCol, board))){
            hasNotMoved = false;
            return true;
        }

        //moving horizontal one square
        if(sRow == dRow && (Math.abs(dCol - sCol) == 1) && isPathClear(sRow, sCol, dRow, dCol, board)){
            hasNotMoved = false;
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
                Arrays.asList(sRow + 1, sCol + 1),
                Arrays.asList(sRow + 1, sCol - 1),
                Arrays.asList(sRow - 1, sCol + 1),
                Arrays.asList(sRow - 1, sCol - 1),
                Arrays.asList(sRow + 1, sCol + 1),
                Arrays.asList(sRow + 1, sCol - 1),
                Arrays.asList(sRow - 1, sCol + 1),
                Arrays.asList(sRow - 1, sCol - 1)
        ).stream().filter(move -> isLandableSpace(move.get(0), move.get(1))).map(ArrayList::new).collect(Collectors.toList()));
    }







}
