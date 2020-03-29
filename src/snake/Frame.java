/*
 *este es el cuadro que contiene el main y ejecuta el algoritmo de inteligencia artificial
es un juego de culebra y se ejecuta solo
 */
package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 * Created by thomas on 12/04/2015.
 */
public class Frame extends JFrame
{
    private Lienzo lienzo;
    public Frame()
    {
        super("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new FlowLayout());
        this.canvas();
        this.setVisible(true);
        this.pack();
    }
    private void canvas()
    {
        this.lienzo = new Lienzo();
        this.add(lienzo);
        this.lienzo.inicio();
    }
//--------------------------------MAIN
    public static void main(String [] x)
    {
        new Frame();
    }

}