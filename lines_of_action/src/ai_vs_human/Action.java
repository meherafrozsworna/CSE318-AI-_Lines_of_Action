package ai_vs_human;

import java.awt.*;

public class Action{
    public Point a,b;
    
    public Action()
    {
        a = b = new Point(0,0);
    }
    
    public Action(Point a, Point b)
    {
        this.a = a;
        this.b = b;
    }
    
    public void update(Action x)
    {
        this.a = x.a;
        this.b = x.b;
    }

}
