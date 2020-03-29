/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.*;
import java.util.Random;

/**
 * Created by thomas on 12/04/2015.
 */
public class Comida
{
    private Point comida;
    private Graphics dibujar;
    private Random n;
    private int x;
    private int y;

    public Comida(Snake ia, Snake game1)
    {
        this.n = new Random();
        this.comida = new Point();
        this.nuevaComida(ia, game1);
    }
    public void setGraphicsComida(Graphics g)
    {
        this.dibujar = g;
    }
    public void pintarComida()
    {
        this.dibujar.setColor(Color.RED);
        this.dibujar.fillOval(comida.x,comida.y,10,10);
    }
    public void nuevaComida(Snake ia, Snake game1)
    {
        do
        {
            int aux = n.nextInt(5)*100;
            int aux2 = (n.nextInt(9)*10)+aux;
            this.comida.x = aux2;
            this.comida.y = aux2+10;
        }while (esIgual(ia) || esIgual(game1));

    }
    public Point getPoint()
    {
        return this.comida;
    }

    private boolean esIgual(Snake obj)
    {
        for(int contador = 0; contador < obj.getCuerpo().size(); contador++)
        {
            if(obj.getCuerpo().get(contador).getX() == comida.x && obj.getCuerpo().get(contador).getY() == comida.y)
                return true;
        }
        return false;
    }
}