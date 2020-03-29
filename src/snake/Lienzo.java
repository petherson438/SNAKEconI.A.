/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/**
 * Created by thomas on 12/04/2015.
 */
public class Lienzo extends Canvas implements KeyListener, Runnable
{
    private IA snake;
    private Snake gam1;
    private Comida comida;
    private boolean controlJuego;
    private Thread proceso;
    private BufferedImage imagenBuffer;
    private Graphics g_imagenBuffer;
    public Lienzo()
    {
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(500, 500));
        this.addKeyListener(this);
        System.out.println(this.getPreferredSize().getWidth()+" "+ this.getPreferredSize().getHeight());
        this.proceso = new Thread(this,"Snake");

    }
    @Override
    public void paint(Graphics g)
    {
        this.update(g);
    }
    @Override
    public void update(Graphics g)
    {
        this.imagenBuffer = new BufferedImage((int)this.getPreferredSize().getWidth(),(int) this.getPreferredSize().getHeight(), BufferedImage.TYPE_INT_RGB);
        this.g_imagenBuffer = this.imagenBuffer.getGraphics();
        this.snake.setGraphicsSnake(this.g_imagenBuffer);
        this.snake.pintarSnake();

        this.gam1.setGraphicsSnake(this.g_imagenBuffer);
        this.gam1.pintarSnake();
        this.comida.setGraphicsComida(this.g_imagenBuffer);
        this.comida.pintarComida();
        g.drawImage(this.imagenBuffer,0,0,this);
    }
    public void inicio()
    {
        this.inicioComponentes();
        this.proceso.start();
    }

    @Override
    public void run()
    {
        try
        {
            while(this.controlJuego)
            {
                this.snake.moverSnake();
                this.gam1.moverSnake();
                this.muerte();
                this.colisionBorde();
                this.colisionComida();
                this.repaint();
                this.proceso.sleep(40);
            }
        }catch (Exception e)
        {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
    private void inicioComponentes()
    {
        this.snake = new IA(500,500);
        this.gam1 = new Snake();
        this.snake.cambiar(10, 10);
        this.gam1.cambiar(30,30);

        this.comida = new Comida(this.snake, this.gam1);
        this.snake.buscarComida(this.comida.getPoint());
        this.controlJuego = true;
    }
    private void colisionBorde()
    {
        if(this.snake.getCuerpo().get(0).x <= -10)
        {
            this.snake.getCuerpo().get(0).setLocation(490, this.snake.getCuerpo().get(0).y);
        }else if(this.snake.getCuerpo().get(0).x >= 500)
        {
            this.snake.getCuerpo().get(0).setLocation(0, this.snake.getCuerpo().get(0).y);
        }else if(this.snake.getCuerpo().get(0).y <= -10)
        {
            this.snake.getCuerpo().get(0).setLocation(this.snake.getCuerpo().get(0).x, 490);
        }else if(this.snake.getCuerpo().get(0).y >= 500)
        {
            this.snake.getCuerpo().get(0).setLocation(this.snake.getCuerpo().get(0).x, 0);
        }
        if(this.gam1.getCuerpo().get(0).x <= -10)
        {
            this.gam1.getCuerpo().get(0).setLocation(490, this.gam1.getCuerpo().get(0).y);
        }else if(this.gam1.getCuerpo().get(0).x >= 500)
        {
            this.gam1.getCuerpo().get(0).setLocation(0, this.gam1.getCuerpo().get(0).y);
        }else if(this.gam1.getCuerpo().get(0).y <= -10)
        {
            this.gam1.getCuerpo().get(0).setLocation(this.gam1.getCuerpo().get(0).x, 490);
        }else if(this.gam1.getCuerpo().get(0).y >= 500)
        {
            this.gam1.getCuerpo().get(0).setLocation(this.gam1.getCuerpo().get(0).x, 0);
        }
    }
    private void colisionComida()
    {
        if(this.snake.getCuerpo().get(0).equals(this.comida.getPoint()))
        {
            this.snake.crecerSnake();
            this.comida.nuevaComida(this.snake, this.gam1);
            this.snake.buscarComida(this.comida.getPoint());
        }
        if(this.gam1.getCuerpo().get(0).equals(this.comida.getPoint()))
        {
            this.gam1.crecerSnake();
            this.comida.nuevaComida(this.snake, this.gam1);
            this.snake.buscarComida(this.comida.getPoint());
            
        }

    }
    private void muerte()
    {
        for(int n = 0; n < this.snake.getCuerpo().size();n++)
        {
            if(this.gam1.getCuerpo().get(0).equals(this.snake.getCuerpo().get(n)))
            {
                this.inicioComponentes();
            }
        }
    }
    //--------------------------------------- EVENTOS

    @Override
    public void keyTyped(KeyEvent evento)
    {    }
    @Override
    public void keyPressed(KeyEvent evento)
    {
        
        switch(evento.getKeyCode())
        {
            case KeyEvent.VK_UP:
                System.out.println("KeyEvent(): Arrinba");
                this.gam1.dirigirSnake(0,-10);
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("KeyEvent(): Abajo");
                this.gam1.dirigirSnake(0,10);
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("KeyEvent(): Izquirda");
                this.gam1.dirigirSnake(-10,0);
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("KeyEvent(): Derecha");
                this.gam1.dirigirSnake(10,0);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent evento)
    {    }
}