package com.yuriyk_israelb.ex1;

import java.util.Random;

public class GameBoard {
    private int[][] board;
    public static final int BOARD_SIZE = 4; // the bord's row size
    public static final int SHUFFLE_ITARATIONS = 100; // the number of iteration in shuffle function


    public GameBoard() {
        this.board = new int[BOARD_SIZE][BOARD_SIZE];
        int count = 1;
        for(int i = 0; i < this.board.length; i++)
        {
            for (int j = 0; j < this.board[0].length; j++)
            {
                this.board[i][j] = count;
                count++;
            }
        }
        shuffle(BOARD_SIZE-1, BOARD_SIZE-1);
    }

    public int[][] getBoard(){ return this.board; }

    /**
     *  this function check if the board in final state
     *  if yes, return true, otherwise return false
     */
    public boolean isWin(int[][] board) {
        int num = 1;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if(this.board[i][j] != num)
                    return false;
                num++;
            }
        }
        return true;
    }

    /**
     *  this function create 3 random numbers
     *  twoOpt - integer between 0 and 1
     *  threeOpt - integer between 0 and 2
     *  fourOpt - integer between 0 and 3
     *  all of them indicate the number of move options in specific position
     *  also this function check what position is board on and make random legal move
     *  the result is mixed board with solution
     */
    public void shuffle(int i, int j ) {
        Random rand = new Random();
        for (int k = 0; k < SHUFFLE_ITARATIONS; k++) {
            int twoOpt = rand.nextInt(2);
            int threeOpt = rand.nextInt(3);
            int fourOpt = rand.nextInt(4);
            if(i == (BOARD_SIZE-1) && j == (BOARD_SIZE-1)){
                if(twoOpt == 0) {
                    swap(i, j, i-1, j);
                    i--;
                }
                else {
                    swap(i, j, i, j-1);
                    j--;
                }
            }
            else if(i == 0 && j == 0) {
                if (twoOpt == 0){
                    swap(i, j, i+1, j);
                    i++;
                }
                else {
                    swap(i, j, i, j+1);
                    j++;
                }
            }
            else if(i == 0 && j == (BOARD_SIZE-1)) {
                if(twoOpt == 0) {
                    swap(i, j , i+1, j);
                    i++;
                }
                else{
                    swap(i, j, i, j-1);
                    j--;
                }
            }
            else if(i == (BOARD_SIZE-1) && j == 0) {
                if(twoOpt == 0) {
                    swap(i, j, i-1, j);
                    i--;
                }
                else{
                    swap(i, j, i, j+1);
                    j++;
                }
            }
            else if(j == 0 && i != 0 && i != (BOARD_SIZE-1)) {
                if(threeOpt == 0) {
                    swap(i, j, i-1, j);
                    i--;
                }
                else if(threeOpt == 1){
                    swap(i, j, i, j+1);
                    j++;
                }
                else {
                    swap(i, j, i+1, j);
                    i++;
                }
            }
            else if(i == 0 && j != 0 && j != (BOARD_SIZE-1)) {
                if(threeOpt == 0){
                    swap(i, j, i+1, j);
                    i++;
                }
                else if(threeOpt == 1){
                    swap(i, j, i, j+1);
                    j++;
                }
                else{
                    swap(i, j, i, j-1);
                    j--;
                }
            }
            else if(i == (BOARD_SIZE-1) && j != 0 && j != (BOARD_SIZE-1)) {
                if(threeOpt == 0){
                    swap(i, j, i-1, j);
                    i--;
                }
                else if(threeOpt == 1){
                    swap(i, j, i, j+1);
                    j++;
                }
                else{
                    swap(i, j, i, j-1);
                    j--;
                }
            }
            else if(j == (BOARD_SIZE-1) && i != 0 && i != (BOARD_SIZE-1)) {
                if(threeOpt == 0){
                    swap(i, j, i-1, j);
                    i--;
                }
                else if(threeOpt == 1){
                    swap(i, j, i, j-1);
                    j--;
                }
                else{
                    swap(i, j, i+1, j);
                    i++;
                }
            }
            else {
                if(fourOpt == 0){
                    swap(i, j, i+1, j);
                    i++;
                }
                else if(fourOpt == 1){
                    swap(i, j, i, j+1);
                    j++;
                }
                else if(fourOpt == 2){
                    swap(i, j, i-1, j);
                    i--;
                }
                else{
                    swap(i, j, i, j-1);
                    j--;
                }
            }
        }
    }

    /**
     *  this function find number sixteen which symbolizes the empty cell
     */
    public int[] findSixteen(){
        int indx[] = new int[2];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if(this.board[i][j] == 16){
                    indx[0] = i;
                    indx[1] = j;
                    break;
                }
            }
        }
        return indx;
    }

    /**
     *  this function swap any number with number 16
     */
    public void swap(int i, int j, int i1, int j1)
    {
        this.board[i][j] = this.board[i1][j1];
        this.board[i1][j1] = 16;
    }
}
