package MainPackage;

import processing.core.PApplet;

public class Board {

    public int[][] board;
    public PApplet P;

    public Board(PApplet p) {
        P = p;
        board = new int[3][3];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = 0;
            }
        }
    }

    public void update() {
        drawboard();
    }

    public boolean place(int x, int y, int player) {
//        System.out.println("player " + player + ", tried board[" + x + "][" + y + "]");
        if (board[x][y] != 0) {
            return false;
        }
        if (board[x][y] == 0) {
            board[x][y] = player;
        }
        return true;
    }

    public void drawboard() {
        drawgrid();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != 0) {
                    if (board[i][j] == 1) {
                        drawo(i, j);
                    } else if (board[i][j] == -1) {
                        drawx(i, j);
                    }
                }
            }
        }
    }

    public void textDisplay() {
        System.out.println();
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("[");
                if(board[i][j] == -1) System.out.print("-1]");
                else System.out.print(" " + board[i][j] + "]");
            }
            System.out.println();
        }
                                System.out.println();

    }

    void drawgrid() {
        P.background(200);
        P.stroke(0);
        P.line(200, 0, 200, 599);
        P.line(400, 0, 400, 599);
        P.line(0, 200, 599, 200);
        P.line(0, 400, 599, 400);
    }

    void drawo(int x, int y) {
        P.stroke(255, 0, 0);
        P.fill(0, 0, 0, 0);
        P.ellipse(100 + x * 200, 100 + y * 200, 180, 180);
    }

    void drawx(int x, int y) {
        P.stroke(0, 0, 255);
        P.line(x * 200 + 20, y * 200 + 20, x * 200 + 180, y * 200 + 180);
        P.line(x * 200 + 180, y * 200 + 20, x * 200 + 20, y * 200 + 180);
    }

    public void reset() {
//        System.out.println("reseting board");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = 0;
            }
        }
    }

    public int findwin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != 0 && board[i][1] != 0 && board[i][2] != 0) {
                if (board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                    return (int) board[i][0];
                }
            }

            if (board[0][i] != 0 && board[1][i] != 0 && board[2][i] != 0) {
                if (board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
                    return board[0][1];
                }
            }
        }

        if (board[0][0] != 0 && board[1][1] != 0 && board[2][2] != 0) {
            if (board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
                return board[0][0];
            }
        }

        if (board[0][2] != 0 && board[1][1] != 0 && board[2][0] != 0) {
            if (board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
                return board[0][2];
            }
        }

        int tied = 2;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    tied = 0;
                }
            }
        }
//        if(tied == 2) System.out.println("TIED");
        return tied;
    }

    public static final String getSketchClassName() {
        return Thread.currentThread().getStackTrace()[1].getClassName();
    }
}
