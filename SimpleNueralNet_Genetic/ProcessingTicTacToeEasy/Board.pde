class Board {
  int[][] board;

  Board() {
    board = new int[3][3];
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        board[i][j] = 0;
      }
    }
  }


  void update() {

    drawboard();
  }

  boolean place(int x, int y, int player) {
    println("X: " + x + ", Y: " + y);
    if (board[x][y] != 0) return false;
    if (board[x][y] == 0) board[x][y] = player;
    return true;
  }

  void drawboard() {
    drawgrid();
    for (int i=0; i<3; i++)
      for (int j=0; j<3; j++)
        if (board[i][j] != 0) {
          if (board[i][j] == 1)
            drawo(i, j);
          else if (board[i][j] == 2)
            drawx(i, j);
        }
  }

  void drawgrid() {
    background(200);
    stroke(0);
    line(200, 0, 200, 599);
    line(400, 0, 400, 599);
    line(0, 200, 599, 200);
    line(0, 400, 599, 400);
  }

  void drawo(int x, int y) {
    stroke(255, 0, 0);
    fill(0, 0, 0, 0);
    ellipse(100+x*200, 100+y*200, 180, 180);
  }

  void drawx(int x, int y) {
    stroke(0, 0, 255);
    line(x*200+20, y*200+20, x*200+180, y*200+180);
    line(x*200+180, y*200+20, x*200+20, y*200+180);
  }

  void reset() {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        board[i][j] = 0;
      }
    }
  }

  int findwin() {
    for (int i=0; i<3; i++) {
      if (board[i][0] != 0 && board[i][1] != 0 && board[i][2] != 0)
        if (board[i][0] == board[i][1] && board[i][0] == board[i][2])
          return (int)board[i][0];

      if (board[0][i] != 0 && board[1][i] != 0 && board[2][i] != 0)
        if (board[0][i] == board[1][i] && board[0][i] == board[2][i])
          return board[0][1];
    }

    if (board[0][0] != 0 && board[1][1] != 0 && board[2][2] != 0)
      if (board[0][0] == board[1][1] && board[0][0] == board[2][2])
        return board[0][0];

    if (board[0][2] != 0 && board[1][1] != 0 && board[2][0] != 0)  
      if (board[0][2] == board[1][1] && board[0][2] == board[2][0])
        return board[0][2];
        
        boolean tied = true;
        for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        if(board[i][j] == 0) tied = false;
      }
    }
    return tied ? -1 : 0;
  }
}
