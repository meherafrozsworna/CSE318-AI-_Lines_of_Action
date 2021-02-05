package ai_vs_human;

import java.util.ArrayList;

public class AlphaBeta {

    public static int alphabeta(Game game, Board state, int player, Action action)
    {
        int r = alphabeta(game, state, player, true, action, player, 0, -99999, 99999);
        return r;
    }

    static int alphabeta(Game game, Board state, int player,  boolean maximize, Action returnAction, int ai, int currentDepth,int alpha, int beta)
    {
        int h = (int)(state.evaluate(player, ai));
        if(currentDepth == 3)
            return h;

        if(maximize)
        {
            int v = -99999;
            ArrayList<Action> actions = game.getAllValidMoves(player, state);
            for(Action action : actions)
            {
                Board newState = game.applyAction(action, state, player);
                Action abAction = new Action();
                int newV = alphabeta(game, newState, 3-player, false, abAction, ai, currentDepth+1,alpha,beta);
                if(newV > v)
                {
                    v = newV;
                    returnAction.update(action);
                }
                alpha = Math.max(alpha, v);
                if(beta <= alpha)
                {
                    break;
                }
            }
            return v;                
        }
        else
        {
            int v = 99999;
            ArrayList<Action> actions = game.getAllValidMoves(player, state);
            for(Action action : actions)
            {
                Board newState = game.applyAction(action, state, player);
                Action abAction = new Action();
                int newV = alphabeta(game, newState, 3-player , true, abAction, ai, currentDepth+1,alpha,beta);
                if(newV < v)
                {
                    v = newV;
                    returnAction.update(action);
                }
                beta = Math.min(beta, v);
                if(beta <= alpha)
                {
                    break;
                }
            }
            return v;
        }
    }
}
