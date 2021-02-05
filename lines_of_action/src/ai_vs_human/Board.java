package ai_vs_human;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


public class Board {
    
    int size;
    public int [][] board;
    public int [] nCheckers;

    public Board(Board other)
    {
        nCheckers = new int[2];
        size = other.size;   
        nCheckers[0] = other.nCheckers[0];
        nCheckers[1] = other.nCheckers[1];
        
        board = new int[size][size];
        for(int i=0;i<size;i++)
            for(int j=0;j<size;j++)
                board[i][j] = other.board[i][j];
        state = new int[size][size];    
    }

    public Board(int size)
    {
        nCheckers = new int[2];
        this.size = size;   
        nCheckers[0] = nCheckers[1] = 2 * (size-2);
        board = new int[size][size];
        for(int i=0;i<size;i++)
            for(int j=0;j<size;j++)
                board[i][j] = 0;
        for(int i=1; i<size-1; i++)
        {
            board[0][i] = board[size-1][i] = 1;
            board[i][0] = board[i][size-1] = 2;
        }
        state = new int[size][size];
    }
    
    int [][] state;

    public float evaluate(int player, int whoami)
    {
        return max_connected(player,whoami)*100+pieceSquare(player)+ 100/density(player)+100/area(player);
    }
    public float max_connected(int player,int whoami)
    {
        System.out.println(player+"  "+whoami);
        for(int i=0; i<size; i++)
        {
            for(int j=0;j<size; j++)
            {
                state[i][j] = 0 ;
            }
        }

        int bstMe = 0;
        int bstOther = 0;
        int otherPlayer = 3 - player;


        for(int i=0; i<size; i++)
        {
            for(int j=0;j<size; j++)
            {
                if(state[i][j]==0)
                {
                    if(board[i][j]==player)
                    {
                        int h = dfs(i,j);
                        if(h > bstMe)
                            bstMe = h;
                    }
                    else if(board[i][j]==otherPlayer)
                    {
                        int h = dfs(i,j);
                        if(h > bstOther)
                            bstOther = h;
                    }
                }
            }
        }

        float bestMePct = (float)bstMe / (float)nCheckers[player - 1];
        float bestOtherPct = (float)bstOther / (float)nCheckers[otherPlayer - 1];


        float totPct = bestMePct + bestOtherPct;
        float myPct = (bestMePct / totPct)*2.f-1.f;

        return player == whoami ? myPct : -myPct;
    }

    int pieceSquare(int player) {
        int pieceSquareTable[][] = {
                {-80, -25, -20, -20, -20, -20, -25, -80},
                {-25, 10, 10, 10, 10, 10, 10, -25},
                {-20, 10, 25, 25, 25, 25, 10, -20},
                {-20, 10, 25, 50, 50, 25, 10, -20},
                {-20, 10, 25, 50, 50, 25, 10, -20},
                {-20, 10, 25, 25, 25, 25, 10, -20},
                {-25, 10, 10, 10, 10, 10, 10, -25},
                {-80, -25, -20, -20, -20, -20, -25, -80}
        };

        int point = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size ; j++) {
                if (board[i][j]== player){
                    point+= pieceSquareTable[i][j];
                }
            }
        }
        return point;
    }
    float density(int player)
    {
        System.out.println(player);
        int count = 0 ;
        int x = 0;
        int y = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size ; j++) {
                if (board[i][j]== player){
                    count++ ;
                    x += i ;
                    y += j ;

                }
            }
        }
        if (count == 0)
            return 1;
        int center_x = x/count ;
        int center_y = y/count ;
        int distance = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size ; j++) {
                if (board[i][j]== player){
                    distance += Math.abs(center_x-i);
                    distance+= Math.abs(center_y-j);

                }
            }
        }
        return distance ;
    }

    int area(int player)
    {
        int low_row = 8 ;
        int low_col = 8 ;
        int max_row = -1 ;
        int max_col = -1 ;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size ; j++) {
                if (board[i][j]== player){
                    if (low_row > i)
                        low_row = i;
                    if (low_col > j)
                        low_col = j ;
                    if (max_row < i)
                        max_row = i;
                    if (max_col < j)
                        max_col = j ;

                }
            }
        }
        int area = (max_row-low_row+1)*(max_col-low_col+1);
        return area ;
    }

    
    // a DFS that counts the size of a group starting at position i,j
    int dfs(int x, int y)
    {

        boolean added[][] = new boolean[size][size];
        for (int k = 0; k < size ; k++) {
            for (int l = 0; l < size; l++) {
                added[k][l] = false ;
            }
        }

        int start_x = x;
        int start_y =  y;
        int connected = 0;
        int turn = board[x][y];
        Queue<Integer> q_x = new LinkedList<>() ;
        Queue<Integer> q_y = new LinkedList<>();


        q_x.add(start_x);
        q_y.add(start_y);
        connected++;
        added[start_x][start_y] = true ;

        while (!q_x.isEmpty()){
            int i = q_x.peek();
            int j = q_y.peek();
            q_x.remove();
            q_y.remove();

            if((i>0 && board[i-1][j]==turn) && added[i-1][j]==false){
                connected++;
                added[i-1][j] = true ;
                q_x.add(i-1);
                q_y.add(j) ;
            }
            if((i<(size-1)) && board[i+1][j] == turn && added[i+1][j]==false){
                connected++;
                added[i+1][j] = true ;
                q_x.add(i+1);
                q_y.add(j) ;
            }
            if((j>0) && board[i][j-1] == turn && added[i][j-1]==false){
                connected++;
                added[i][j-1] = true ;
                q_x.add(i);
                q_y.add(j-1) ;
            }
            if((j<(size-1)) && board[i][j+1]==turn && added[i][j+1]==false){
                connected++;
                added[i][j+1] = true ;
                q_x.add(i);
                q_y.add(j+1) ;
            }
            if((i>0) && (j>0) && board[i-1][j-1] == turn && added[i-1][j-1] == false){
                connected++;
                added[i-1][j-1] = true ;
                q_x.add(i-1);
                q_y.add(j-1) ;
            }
            if((i>0) && (j<(size-1)) && board[i-1][j+1]== turn && added[i-1][j+1]==false){
                connected++;
                added[i-1][j+1] = true ;
                q_x.add(i-1);
                q_y.add(j+1) ;
            }
            if((i<(size-1)) && (j>0) && board[i+1][j-1]== turn && added[i+1][j-1]==false){
                connected++;
                added[i+1][j-1] = true ;
                q_x.add(i+1);
                q_y.add(j-1) ;
            }
            if((i<(size-1)) && (j < size-1) && board[i+1][j+1]==turn && added[i+1][j+1]==false){
                connected++;
                added[i+1][j+1] = true ;
                q_x.add(i+1);
                q_y.add(j+1) ;
            }
        }
        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < size; j++) {
                if (added[i][j] == true){
                    state[i][j] = 1;
                }
            }
        }
        return connected ;

    }

    public int checkGroup(int player)
    {
        for(int i=0; i<size; i++)
        {
            for(int j=0;j<size; j++)
            {
                state[i][j] = 0 ;
            }
        }
        for(int i=0; i<size; i++)
        {
            for(int j=0;j<size; j++)
            {
                if(board[i][j]==player)
                    return dfs(i,j);
            }
        }
        return 0;
    }
}
