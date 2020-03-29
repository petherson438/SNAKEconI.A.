/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by thomas on 12/04/2015.
 */
public class Snake
{
    private ArrayList<Point> cuerpo;
    private Graphics snake;
    private int direccionX;
    private int direccionY;

    public Snake()
    {
        this.direccionX = 10;
        this.direccionY = 0;
        this.cuerpo = new ArrayList<Point>();
    }
    public void cambiar(int n, int y)
    {
        this.cuerpo.add(new Point(n,y));
        this.cuerpo.add(new Point(n-10,y));
        this.cuerpo.add(new Point(n-10,y));
    }
    
    public void setGraphicsSnake(Graphics g)
    {
        this.snake = g;
    }
    public ArrayList<Point> getCuerpo()
    {
        return this.cuerpo;
    }
    public void pintarSnake()
    {
        for(int i = 0; i < this.cuerpo.size(); i++)
        {
            Point p = this.cuerpo.get(i);
            this.snake.setColor(Color.WHITE);
            this.snake.fillRect(p.x,p.y,10,10);
        }
    }
    public void moverSnake()
    {
        for(int i = this.cuerpo.size()-1; i > 0; i--)
        {
            this.cuerpo.get(i).setLocation(this.cuerpo.get(i-1));
        }
        this.cuerpo.get(0).x += this.direccionX;
        this.cuerpo.get(0).y += this.direccionY;

    }
    public void crecerSnake()
    {
        int yAux = this.cuerpo.get(this.cuerpo.size()-1).y;
        int xAux = this.cuerpo.get(this.cuerpo.size()-1).x;
        this.cuerpo.add(new Point(xAux,yAux));
    }
    public void dirigirSnake( int n1, int n2)
    {
        this.direccionX = n1;
        this.direccionY = n2;
    }


}