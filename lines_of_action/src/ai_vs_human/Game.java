package ai_vs_human;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Game {
    
    Board board;
    int turn;
    int size;
    int humanPlayer;
    int AIPlayer;
    boolean playing = false;
    BoardCanvas canvas;
    
    public Game(Board board)
    {
        init(board, 1);
    }
    
    public void start()
    {
          playing = true;      
          if(!humanPlays())
              computerMove();
    }
    
    public void setCanvas(BoardCanvas canvas)
    {
        this.canvas = canvas;
    }
    
    // returns true if it's the human's turn to play
    public boolean humanPlays()
    {
        return turn+1 == humanPlayer;
    }
    
    public void init(Board board, int humanPlayer)
    {
        this.board = board;
        this.size = board.size;
        turn = 0;
        this.humanPlayer = humanPlayer;
        AIPlayer = 3 - humanPlayer;
    }

    public ArrayList<Action> getAllValidMoves(int player, Board board)
    {
        ArrayList<Action> ret = new ArrayList<>();
        for(int i=0;i<board.size;i++)
            for(int j=0;j<board.size;j++)
                if(board.board[i][j]==player)
                {
                    for(Point p : getValidMoves(board, i,j))
                    {
                        ret.add(new Action(new Point(j,i), p));
                    }
                }
        return ret;
    }
    
    public ArrayList<Point> getValidMoves( int i, int j)
    {        
        return getValidMoves(board, i,j);
    }
    
    public ArrayList<Point> getValidMoves(Board board, int i, int j)
    {
        ArrayList<Point> ret = new ArrayList<>();

        int nc = 0;
        int nr = 0;
        int nd1 = 1;
        int nd2 = 1;
        
        for(int k=0;k<size;k++)
        {
            if(board.board[k][j]!=0) nc++;
            if(board.board[i][k]!=0) nr++;
        }
        
        int ii = i+1, jj = j+1;
        while(ii < size && jj < size)
        {
            if(board.board[ii][jj]!=0)nd1++;
            ii++;
            jj++;
        }
        ii = i - 1;
        jj = j - 1;
        while(ii >= 0 && jj >= 0)
        {
            if(board.board[ii][jj]!=0)nd1++;
            ii--;
            jj--;
        }
        
        ii = i + 1;
        jj = j - 1;
        while(ii < size && jj >= 0)
        {
            if(board.board[ii][jj]!=0)nd2++;
            ii++;
            jj--;
        }
        
        ii = i - 1;
        jj = j + 1;
        while(ii >= 0 && jj < size)
        {
            if(board.board[ii][jj]!=0)nd2++;
            ii--;
            jj++;
        }
        
        int mycolor = board.board[i][j];
        
        // first check if we can move on the same row
        ret.addAll(movements(i,j,nc,1,0,mycolor));
        ret.addAll(movements(i,j,nc,-1,0,mycolor));
        ret.addAll(movements(i,j,nr,0,1,mycolor));
        ret.addAll(movements(i,j,nr,0,-1,mycolor));
        ret.addAll(movements(i,j,nd1,1,1,mycolor));
        ret.addAll(movements(i,j,nd1,-1,-1,mycolor));
        ret.addAll(movements(i,j,nd2,1,-1,mycolor));
        ret.addAll(movements(i,j,nd2,-1,1,mycolor));
        
        return ret;
    }
    
    void msgWins(int winner)
    {
        canvas.forcepaint();
        String msg = winner == 1 ? "The computer wins!" : "You win!";
        JOptionPane.showMessageDialog(null, msg, "Winner", JOptionPane.INFORMATION_MESSAGE);
        playing = false;
    }

    public void computerMove()
    {
        Action action = new Action();        

        int v = AlphaBeta.alphabeta(this, board, AIPlayer, action);
        System.out.println("v="+v);
        System.out.println(action);
        
        move(board, action.a.y, action.a.x, action.b.y, action.b.x, AIPlayer);
        
        if(wins(AIPlayer)) // check if I win
            msgWins(1);
        else if(wins(humanPlayer))  // check if the human won
            msgWins(2);
        
        turn = 1 - turn;
    }

    public Board applyAction(Action action, Board board, int player)
    {
        Board newBoard = new Board(board);
        move(newBoard, action.a.y, action.a.x, action.b.y, action.b.x, player);
        return newBoard;
    }

    public void move(Board board, int i0, int j0, int i1, int j1, int player)
    {
        board.board[i0][j0] = 0;
        if(board.board[i1][j1] == 3-player)
            board.nCheckers[2-player]--;
        board.board[i1][j1] = player;
    }    

    public void move(int i0, int j0, int i1, int j1)
    {
        move(board, i0, j0, i1, j1, turn+1);
        
        if(wins(humanPlayer))
            msgWins(2);
        else if(wins(AIPlayer))
            msgWins(1);
        
        turn = 1 - turn;
    }
    

    public boolean wins(int player) 
    {
        return board.checkGroup(player) == board.nCheckers[player-1];
    }    

    ArrayList<Point> movements(int i, int j, int n, int di, int dj, int mycolor)
    {
        ArrayList<Point> ret = new ArrayList<>();
        i += di;
        j += dj;
        for(int k=1; k < n; k++)
        {
            // check if i,j is inside the board
           if(i<0 || i>=size || j<0 || j>=size) return ret;
            if(board.board[i][j] != mycolor && board.board[i][j] != 0) 
                return ret;
            i += di;
            j += dj;
            
        }

        if(i<0 || i>=size || j<0 || j>=size) return ret;

        if(board.board[i][j] == mycolor) return ret;
        ret.add(new Point(j,i));
        return ret;
    }
}

