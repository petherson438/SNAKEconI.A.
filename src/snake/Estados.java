/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

/**
 * Created by thomas on 25/04/2015.
 */
public class Estados
{
    private int numeroEstado, x, y, arribaX, arribaY, abajoX, abajoY, derechaX, derechaY, izquierdaX, izquierdaY;

    public Estados(int numeroEstado, int x, int y)
    {
        this.numeroEstado = numeroEstado;
        this.x = x;
        this.y = y;
        this.arribaX = 0;
        this.arribaY = 0;
        this.abajoX = 0;
        this.abajoY = 0;
        this.derechaX = 0;
        this.derechaY = 0;
        this.izquierdaX = 0;
        this.izquierdaY = 0;
    }
    //-----------------------------numeroEstaod
    public void setNumeroEstado(int n)
    {
        this.numeroEstado = n;
    }
    public int getNumeroEstado()
    {
        return this.numeroEstado;
    }
    //-----------------------------x y y
    public int getX()
    {
        return x;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public int getY()
    {
        return this.y;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    ///-----------------------------Arribaaa
    public int getArribaX()
    {
        return arribaX;
    }
    public void setArribaX(int x)
    {
        this.arribaX = x;
    }
    public int getArribaY()
    {
        return this.arribaY;
    }
    public void setArribaY(int y)
    {
        this.arribaY = y;
    }
    //------------------------------------Abajo
    public int getAbjoX ()
    {
        return this.abajoX;
    }
    public void setAbjoX(int x)
    {
        this.abajoX = x;
    }
    public int getAbajoY()
    {
        return this.abajoY;
    }
    public void setAbjoY(int y)
    {
        this.abajoY = y;
    }
    //--------------------------------------Derecha
    public void setDerechaX(int x)
    {
        this.derechaX = x;
    }
    public int getDerechaX()
    {
        return this.derechaX;
    }
    public void setDerechaY(int y)
    {
        this.derechaY = y;
    }
    public int getDerechaY()
    {
        return this.derechaY;
    }
    //------------------------------------------Izqurda
    public void setIzquierdaX(int x)
    {
        this.izquierdaX = x;
    }
    public int getIzquierdaX()
    {
        return this.izquierdaX;
    }
    public void setIzquierdaY(int y)
    {
        this.izquierdaY = y;
    }
    public int getIzquierdaY()
    {
        return this.izquierdaY;
    }
}
