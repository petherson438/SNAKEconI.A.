/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snake;

import java.awt.Point;
import java.util.ArrayList;
/**
 * Created by thomas on 25/04/2015.
 */
public class IA extends Snake
{
    private Estados estadoInicial;
    private Estados estadoFinal;
    private ArrayList<Estados> estados;
    private ArrayList<Estados> camino;

    public IA(int ancho, int alto)
    {
        this.estados = new ArrayList<Estados>();
        this.camino = new ArrayList<Estados>();
        this.generarListaDeEstados(ancho, alto);
    }
    public void buscarComida(Point comida)
    {
        ArrayList<Estados> auxiliar1 = new ArrayList<Estados>();
        Estados auxEstado;
        this.estadoInicial = this.buscarEstado(this.getCuerpo().get(0));
        this.estadoFinal = this.buscarEstado(comida);
        auxiliar1.add(this.estadoInicial);
        while(auxiliar1 != null)
        {
            do
            {
                auxEstado = auxiliar1.get(auxiliar1.size() - 1);
                auxiliar1.remove(auxiliar1.size() - 1);
            }while(this.comprobarEstadoNoEsteEnCamino(auxEstado));
            if(!(auxEstado.equals(this.estadoFinal)))
            {
                this.camino.add(auxEstado);
                auxiliar1 = this.obtenerEstadosDelEstadoActual(auxEstado, auxiliar1);
            } else if(auxEstado.equals(this.estadoFinal))
            {
                this.camino.add(auxEstado);
                break;
            }
        }
        this.camino.remove(0);
    }
    @Override
    public void moverSnake()
    {
        if(this.camino.size() -1 >= 0)
        {
            for(int i = this.getCuerpo().size()-1; i > 0; i--)
            {
                this.getCuerpo().get(i).setLocation(this.getCuerpo().get(i - 1));
            }
            this.getCuerpo().get(0).x = this.camino.get(0).getX();
            this.getCuerpo().get(0).y = this.camino.get(0).getY();
            this.camino.remove(0);
        }    
        
    }
    private boolean comprobarEstadoNoEsteEnCamino(Estados x)
    {
        System.out.println("comprobarEstadoNoEsteEnCamino()... Comprobar Estados");
        boolean t = false;
        for(Estados e: this.camino)
        {
            if(e.getNumeroEstado() == x.getNumeroEstado())
            {
                t = true;
                break;
            }
            else t = false;
        }

        return t;
    }
    private Estados buscarEstado(Point ob)
    {
        System.out.println("buscarEstado()... Buscando Estado De Comida");
        Estados estado = null;
        for(Estados e: this.estados)
        {
            if(e.getX() == ob.x && e.getY() == ob.y)
            {
                estado = e;
                break;
            }
        }
        return estado;
    }
    private Estados buscarEstado(int xx, int yy)
    {
        System.out.println("buscarEstado()... Buscando Estado De Comida");
        Estados estado = null;
        for(Estados e: this.estados)
        {
            if(e.getX() == xx && e.getY() == yy)
            {
                estado = e;
                break;
            }
        }
        return estado;
    }
    private ArrayList<Estados> obtenerEstadosDelEstadoActual(Estados e, ArrayList<Estados> lista)
    {
        ArrayList<Estados> aux = new ArrayList<Estados>();
        aux.add(this.buscarEstado(e.getArribaX(),e.getArribaY()));
        aux.add(this.buscarEstado(e.getAbjoX(), e.getAbajoY()));
        aux.add(this.buscarEstado(e.getDerechaX(),e.getDerechaY()));
        aux.add(this.buscarEstado(e.getIzquierdaX(),e.getIzquierdaY()));
        aux = this.ordenamientoPorSeleccion(aux);
        return juntarListaOrdenadaConAuxiliar(lista, aux);
    }
    private ArrayList<Estados> juntarListaOrdenadaConAuxiliar(ArrayList<Estados> lista, ArrayList<Estados> aux)
    {
        for(int i = aux.size()-1 ; i >= 0 ; i--)
        {
            lista.add(aux.get(i));
        }
        return  lista;
    }
    private ArrayList<Estados> ordenamientoPorSeleccion(ArrayList<Estados> lista)
    {
        System.out.println("ordenamientoPorSeleccion()...");
        int minimo = 0, valorMinimo1 = 0, valorMinimo2 = 0 ; Estados aux = null;
        for(int i = 0; i < lista.size(); i++)
        {
            minimo = i;
            for(int j = i+1; j < lista.size(); j++)
            {
                valorMinimo1 = this.estadoFinal.getNumeroEstado() - lista.get(minimo).getNumeroEstado();
                if(valorMinimo1 < 0) valorMinimo1 *= -1;
                valorMinimo2 = this.estadoFinal.getNumeroEstado() - lista.get(j).getNumeroEstado();
                if(valorMinimo2 < 0) valorMinimo2 *= -1;
                if(valorMinimo2 < valorMinimo1) minimo = j;
            }
            aux =  lista.get(i);
            lista.add(i, lista.get(minimo));
            lista.remove(i + 1);
            lista.add(minimo, aux);
            lista.remove(minimo + 1);
        }
        return lista;
    }


    public void generarListaDeEstados(int ancho, int alto)
    {
        System.out.println("generarListaDeEstados()... Generando Estados");
        int auxiliarAncho = 0, auxiliarAlto = 0, totalEstados = (alto/10)*(ancho/10);
        for(int n = 0; n < totalEstados; n++)
        {
            Estados estado = new Estados(n,auxiliarAncho, auxiliarAlto);
            if(auxiliarAncho + 10 == ancho)
            {
                estado.setDerechaX(0);
                estado.setDerechaY(auxiliarAlto);

            }else
            {
                estado.setDerechaX(auxiliarAncho + 10);
                estado.setDerechaY(auxiliarAlto);
            }
            if(auxiliarAncho -10 == -10)
            {
                estado.setIzquierdaX(ancho - 10);
                estado.setIzquierdaY(auxiliarAlto);
            }else
            {
                estado.setIzquierdaX(auxiliarAncho - 10);
                estado.setIzquierdaY(auxiliarAlto);
            }
            if(auxiliarAlto -10 == -10)
            {
                estado.setArribaX(auxiliarAncho);
                estado.setArribaY(alto-10);
            }else
            {
                estado.setArribaX(auxiliarAncho);
                estado.setArribaY(auxiliarAlto -10);

            }
            if(auxiliarAlto+10 == alto)
            {
                estado.setAbjoX(auxiliarAncho);
                estado.setAbjoY(0);
            }else
            {
                estado.setAbjoX(auxiliarAncho);
                estado.setAbjoY(auxiliarAlto +10);
            }
            auxiliarAncho += 10;
            if(auxiliarAncho == ancho)
            {
                auxiliarAncho = 0;
                auxiliarAlto += 10;
            }
            this.estados.add(estado);
        }
    }
}