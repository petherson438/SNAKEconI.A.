/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TRIQUI;

import java.awt.*;

import java.awt.image.*;

import java.net.*;

import java.applet.*;


// ==========================================================================
// Esta clase es la que se encarga de jugar
// ==========================================================================

class Busqueda {

  public final static int VACIO          =  0;
  public final static int COMP           =  1;
  public final static int JUGADOR        =  2;
  public final static int JUEGO_CONTINUA =  0;
  public final static int ALGUIEN_GANO   =  1;
  public final static int TABLERO_LLENO  = -1;
  
// Guarda el tablero actual
  private int tablero[] = new int[7];
  // Profundidad maxima. Cambialo si quieres, pero que sea de 2 en 2
  private int profundidadMaxima = 6;
  // Estado actual del juego (si ha ganado alguien, etc..)
  private int             estadoJuego;
  public  int             EstadoJuego() { return estadoJuego; }

  // ------------------------------------------------------------------------
  // Comprueba si una columna de un tablero cualquiera admite mas fichas
  // ------------------------------------------------------------------------

  private boolean _EstaVacio(int columna, int tab[]) {

    return (tab[columna]<=1023);

  }



  // ------------------------------------------------------------------------

  // Comprueba si una columna del tablero de juego admite mas fichas

  // ------------------------------------------------------------------------

    public boolean EstaVacio(int columna) {

    if(columna>=0 && columna<7)

      return _EstaVacio(columna,tablero);

    else

      return false;

  }



  // ------------------------------------------------------------------------

  // Devuelve el estado de ocupacion (ordenador, jugador o vacio) de una

  // casilla de un tablero

  // ------------------------------------------------------------------------

  private int _Casilla(int columna, int fila, int tab[]) {

    return (tab[columna]>>(fila*2))%4;

  }



  // ------------------------------------------------------------------------

  // Devuelve el estado de ocupacion de una casilla del tablero de juego

  // ------------------------------------------------------------------------

  public int Casilla(int columna, int fila) {

    return _Casilla(columna,fila,tablero);

  }



  // ------------------------------------------------------------------------

  // Mete una ficha del jugador dado en la casilla mas baja disponible en

  // la columna dada de un tablero

  // ------------------------------------------------------------------------

  private int _Mover(int jugador, int columna, int tab[])  {

    int fila;

    for (fila=0; fila<6; fila++)

      if (_Casilla(columna,fila,tab)==VACIO)

        break;

    tab[columna] += jugador<<(2*fila);

    return fila;

  }



  // ------------------------------------------------------------------------

  // Mete una ficha del jugador dado en la casilla mas baja disponible en

  // la columna dada del tablero de juego

  // ------------------------------------------------------------------------

  public int Mover(int jugador, int columna) {

    int fila = -1;

    if(EstaVacio(columna))

       fila = _Mover(jugador, columna, tablero);

    ExaminarJuego();

    return fila;

  }



  // ------------------------------------------------------------------------

  // Funcion de evaluacion. Basicamente se puede decir que suma 10 por cada

  // posible proyecto en el que coincidan ya 3 fichas y uno por cada uno de

  // 2 fichas. No esta muy depurado y por eso suma 20 (por ejemplo), en este

  // caso:

  //                 XXX <-Esa casilla esta libre, luego sumamos

  //                 OXO

  //                 XOO

  // cuando seria mas correcto que sumara 10. Sigue el mismo criterio para

  // restar puntos por los proyectos del adversario.

  // No cuenta ni los 4 en raya ni los posibles empates porque de eso se

  // encarga el Negamax con ayuda de una funcion especifica.

  // ------------------------------------------------------------------------

  private int Evaluar (int tab[]) {

    int columna,fila,i;

    int casilla, parcialComp, parcialJug, total=0;

    boolean salComp=false, salJug=false;

    for (columna=0 ; columna<4 ; columna++)

      for (fila=0 ; fila<6 ; fila++) {

        salComp = salJug = false;

        parcialComp = parcialJug = 0;

        for (i=0 ; i<4 ; i++) {

          casilla = _Casilla(columna+i,fila,tab);

          if (casilla==COMP) {

            parcialComp++;

            salJug=true; }

          if (casilla==JUGADOR) {

            parcialJug++;

            salComp=true; }

        }

        total += (!salComp && parcialComp>1) ? Math.pow(10,parcialComp-2) : 0;

        total -= (!salJug && parcialJug>1) ? Math.pow(10,parcialJug-2) : 0;

        if (fila<3) {

          salComp = salJug = false;

          parcialComp = parcialJug = 0;

          for (i=0 ; i<4 ; i++) {

            casilla = _Casilla(columna+i,fila+i,tab);

            if (casilla==COMP) {

              parcialComp++;

              salJug=true; }

            if (casilla==JUGADOR) {

              parcialJug++;

              salComp=true; }

          }

          total += (!salComp && parcialComp>1) ? Math.pow(10,parcialComp-2) : 0;

          total -= (!salJug && parcialJug>1) ? Math.pow(10,parcialJug-2) : 0;

          salComp = salJug = false;

          parcialComp = parcialJug = 0;

          for (i=0 ; i<4 ; i++) {

            casilla = _Casilla(columna,fila+i,tab);

            if (casilla==COMP) {

              parcialComp++;

              salJug=true; }

            if (casilla==JUGADOR) {

              parcialJug++;

              salComp=true; }

          }

          total += (!salComp && parcialComp>1) ? Math.pow(10,parcialComp-2) : 0;

          total -= (!salJug && parcialJug>1) ? Math.pow(10,parcialJug-2) : 0;

        }

        else {

          salComp = salJug = false;

          parcialComp = parcialJug = 0;

          for (i=0 ; i<4 ; i++) {

            casilla = _Casilla(columna+i,fila-i,tab);

            if (casilla==COMP) {

              parcialComp++;

              salJug=true; }

            if (casilla==JUGADOR) {

              parcialJug++;

              salComp=true; }

          }

          total += (!salComp && parcialComp>1) ? Math.pow(10,parcialComp-2) : 0;

          total -= (!salJug && parcialJug>1) ? Math.pow(10,parcialJug-2) : 0;

        }

      }

    return total;

  }



  // ------------------------------------------------------------------------

  // Negamax con poda alfabeta: Espero que funcione ;-). Aqui se incluye solo

  // el algoritmo a partir del segundo nivel, para asi ocultar su

  // funcionamiento interno a la clase externa y no tener ifs por todos lados

  // debido a que, mientras normalmente el negamax debe devolver la

  // maxima puntuacion obtenida, el nodo raiz debe devolver el numero del

  // nodo que le da la maxima puntuacion.

  // A cada nodo que genera lo primero que comprueba es si corresponde a un

  // final de la partida, devolviendo el valor que corresponda (-100.000 en

  // caso de que gane el jugador, 100.000 en caso que gane la maquina y

  // 0 en caso de empate). Para averiguarlo llama al metodo ExaminarJuego.

  // ------------------------------------------------------------------------

  private int Negamax(int tab[], int profundidad, int alfa, int beta) {

    int nodo, valor_escogido, posible_valor, i, jugador;

    int tab_aux[] = new int[7];

    jugador = (profundidad%2==0) ? COMP: JUGADOR;

    if (profundidad==1)

      return -Evaluar(tab);

    valor_escogido = alfa;

    for (nodo=0; nodo<7; nodo++) {

      for (i=0 ; i<7 ; i++)

        tab_aux[i]=tab[i];

      if (_EstaVacio(nodo,tab_aux)) {

        _Mover(jugador,nodo,tab_aux);

        switch (_ExaminarJuego(tab_aux)) {

          case TABLERO_LLENO:

              posible_valor=0;

              break;

          case COMP:

              if (jugador==COMP)

                posible_valor=100000;

              else

                posible_valor=-100000;

              break;

          case JUGADOR:

              if (jugador==JUGADOR)

                posible_valor=100000;

              else

                posible_valor=-100000;

              break;

          case JUEGO_CONTINUA:

          default:

            posible_valor = -Negamax(tab_aux,profundidad-1,-beta,-valor_escogido);

        }

        if (posible_valor>valor_escogido)

           valor_escogido=posible_valor;

        if (valor_escogido>=beta)

           return valor_escogido;

      }

    }

    return valor_escogido;

  }



  // ------------------------------------------------------------------------

  // Metodo encargado de inicializar el Negamax

  // ------------------------------------------------------------------------

  public int MuevoYo(int valor[]) {

    int nodo, valor_escogido, posible_valor, nodo_escogido, i, jugador;

    int beta;

    int tab_aux[] = new int[7];

    posible_valor = valor_escogido = Integer.MIN_VALUE+10;

    beta = Integer.MAX_VALUE-10;

    nodo_escogido = -1;

    for (nodo=0; nodo<7; nodo++) {

      for (i=0 ; i<7 ; i++)

        tab_aux[i]=tablero[i];

      if (_EstaVacio(nodo,tab_aux)) {

        _Mover(COMP,nodo,tab_aux);

        switch (_ExaminarJuego(tab_aux)) {

          case TABLERO_LLENO:

              posible_valor=0;

              break;

          case COMP:

              return nodo;

          case JUGADOR:

              posible_valor=-100000;

              break;

          case JUEGO_CONTINUA:

          default:

            posible_valor = -Negamax(tab_aux,profundidadMaxima-1,-beta,-valor_escogido);

        }

        if (posible_valor>valor_escogido) {

          nodo_escogido=nodo;

          valor_escogido=posible_valor;

        }

      }

    }

    valor[0]=valor_escogido;

    return nodo_escogido;

  }



  // ------------------------------------------------------------------------

  // Examina un tablero para ver si el mismo refleja ua partida ya terminada

  // y especifica como ha terminado.

  // ------------------------------------------------------------------------

  private int _ExaminarJuego(int tab[]) {

    int columna,fila,i,j,jugador,res;

    res=TABLERO_LLENO;



    // en este bucle comprobamos que el tablero no esta lleno

    for (columna=0 ; columna<7 ; columna++)

      if (_EstaVacio(columna,tab))

        res=JUEGO_CONTINUA;



    // ahora comprueba si alguien ha hecho un 4 en Raya vertical o

    // diagonal

    for (columna=0 ; (columna<7 && res==JUEGO_CONTINUA); columna++)

      for (fila=0 ; (fila<3 && res==JUEGO_CONTINUA) ; fila++) {

        jugador=_Casilla(columna,fila,tab);

        if (jugador!=VACIO) {

          res=jugador;

          for (i=1 ; i<4 ; i++)

            res = (_Casilla(columna,fila+i,tab)==jugador) ? res : JUEGO_CONTINUA;

        }

        if (jugador!=VACIO && res==JUEGO_CONTINUA && columna<4) {

          res=jugador;

          for (i=1 ; i<4 ; i++)

            res = (_Casilla(columna+i,fila+i,tab)==jugador) ? res : JUEGO_CONTINUA;

        }

        if (jugador!=VACIO && res==JUEGO_CONTINUA && columna>2) {

          res=jugador;

          for (i=1 ; i<4 ; i++)

            res = (_Casilla(columna-i,fila+i,tab)==jugador) ? res : JUEGO_CONTINUA;

        }

      }



    // ahora comprueba si alguien ha hecho un 4 en Raya horizontal

    for (fila=0 ; (fila<6 && res==JUEGO_CONTINUA); fila++)

      for (columna=0 ; (columna<4 && res==JUEGO_CONTINUA) ; columna++) {

        jugador=_Casilla(columna,fila,tab);

        if (jugador!=VACIO) {

          res=jugador;

          for (j=columna+1 ; j<columna+4 ; j++)

            res = (_Casilla(j,fila,tab)==jugador) ? res : JUEGO_CONTINUA;

        }

      }



    return res;

  }



  // ------------------------------------------------------------------------

  // Examina el tablero de juego para ver si este ha finalizado y como lo

  // ha hecho.

  // ------------------------------------------------------------------------

  public void ExaminarJuego() {

    int res=_ExaminarJuego(tablero);

    estadoJuego = (res==COMP || res==JUGADOR) ? ALGUIEN_GANO : res;

  }



  // ------------------------------------------------------------------------

  // Constructor de la clase, pone en blanco el tablero.

  // ------------------------------------------------------------------------

  public Busqueda() {

    int i;

    for(i=0 ; i<7 ; i++)

      tablero[i] = 0;

  }



};





// ==========================================================================

// Esta clase es el applet encargado de interactuar con el usuario.

// Todas las cosas importantes del mismo son del applet de Sven Wiebus

// ==========================================================================



public class CuatroEnRaya extends Applet {

  // ------------------------------------------------------------------------

  public final static int ESPERANDO_QUE_MUEVAS = 0;

  public final static int PENSANDO             = 1;

  public final static int VOLVER_A_EMPEZAR     = 2;



  public final static int ANCHURA_BORDE        = 5;



  // ------------------------------------------------------------------------

  Busqueda c4k;



  int            filaActual;

  int            valor[]                    = new int[1];

  int            res                        = Busqueda.JUEGO_CONTINUA;

  int            ultimaColumnaFlechaComp    = -1;

  int            ultimaColumnaFlechaJugador = -1;

  boolean        ultimaFlechaJugadorVacia   = false;



  int            currFontSize         = 0;

  Font           font;

  StringBuffer   estadoActual;



  Graphics       g                    = null;

  Panel          panel                = null;

  int            modo                 = -1;



  // ------------------------------------------------------------------------

  // Inicializa la clase, creando una instancia de Busqueda y poniendo a

  // su valor inicial diversas variables

  // ------------------------------------------------------------------------

  public void init() {

    c4k = new Busqueda();

    ultimaColumnaFlechaJugador = -1;

    ultimaColumnaFlechaComp  = -1;

    ultimaFlechaJugadorVacia  = true;

    estadoActual = new StringBuffer("Empieza a jugar");

    if(g==null)

      g = getGraphics();

    PonerModo(ESPERANDO_QUE_MUEVAS);

  }



  // ------------------------------------------------------------------------

  // Especifica el momento que esta pasando en el juego

  // ------------------------------------------------------------------------

  public void PonerModo(int m) { modo = m; }



  // ------------------------------------------------------------------------

  // Consulta que esta pasando en el juego

  // ------------------------------------------------------------------------

  public boolean ConsultarModo(int m) { return modo==m; }



  // ------------------------------------------------------------------------

  // Escribe el texto de estado del juego

  // ------------------------------------------------------------------------

  public void EscribirEstado(String str) {

    Dimension d  = size();

    int       dx = (d.width - 2*ANCHURA_BORDE) / 7;

    int       dy = d.height / 8;

    int       i;

    Font      f;



    // clear status display area

    g.setColor(Color.white);

    g.fillRect(ANCHURA_BORDE, 7*dy+1, 7*dx, dy-ANCHURA_BORDE-1);



    if(currFontSize!=dy) {



      currFontSize = dy;

      font = new Font("Dialog", 0, (dy*5)/10);

    }



    g.setFont(font);



    estadoActual = new StringBuffer(str);



    g.setColor(Color.black);

    g.drawString(estadoActual.toString(), ANCHURA_BORDE + 10, 7*dy+(dy*5)/8);



  }



  // ------------------------------------------------------------------------

  // Dibuja una ficha

  // ------------------------------------------------------------------------

  public void DibujarFicha(int c, int r) {

    Color color;



    Dimension d = size();

    int dx = (d.width - 2*ANCHURA_BORDE) / 7;

    int dy = d.height / 8;

    int xk = ANCHURA_BORDE + dx*c + dx/8;

    int yk = d.height - (r+2)*dy + dy/8;

    int xs = (dx*3)/4;

    int ys = (dy*3)/4;



    switch(c4k.Casilla(c, r)) {

      case Busqueda.COMP:

        color = Color.green;

        break;

      case Busqueda.JUGADOR:

        color = Color.red;

        break;

      case Busqueda.VACIO:

      default:

        color = Color.white;

        break;

    }

    g.setColor(color);

    g.fillOval(xk, yk, xs, ys);

  }



  // ------------------------------------------------------------------------

  // paint es un metodo de applet que se encarga de poner en pantalla todo lo

  // que haya que poner

  // ------------------------------------------------------------------------

  public void paint(Graphics g)  {

    Dimension d  = size();

    int       dx = (d.width - 2*ANCHURA_BORDE) / 7;

    int       dy = d.height / 8;

    int       r, c;



    // limpiar

    g.setColor(Color.white);

    g.fillRect(0, 0, 7*dx+2*ANCHURA_BORDE-1, dy*8-1);



    // dibujar

    g.setColor(Color.blue.darker().darker());

    g.fillRect(ANCHURA_BORDE, dy, dx*7, dy*6);

    for(r = 0 ; r < 6 ; r++)

      for(c = 0 ; c < 7 ; c++)

        DibujarFicha(c, r);

    EscribirEstado(estadoActual.toString());

    g.setColor(Color.gray);

    for(r=0 ; r<ANCHURA_BORDE ; r++) {

      g.drawLine(r, r, r, dy*8-r-1);

      g.drawLine(r+1, r, dx*7+2*ANCHURA_BORDE-1, r);

    }

    g.setColor(Color.darkGray);

    for(r=0 ; r<ANCHURA_BORDE ; r++) {

      g.drawLine(dx*7-r+2*ANCHURA_BORDE-1, r+1, dx*7-r+2*ANCHURA_BORDE-1, dy*8-r-1);

      g.drawLine(r, dy*8-r-1, dx*7+2*ANCHURA_BORDE-1, dy*8-r-1);

    }

  }



  // ------------------------------------------------------------------------

  // Llama al metodo Mover de Busqueda y dibuja la ficha y la flecha

  // correspondientes.

  // ------------------------------------------------------------------------

  public int Mover(int jugador, int columna) {

    int fila = c4k.Mover(jugador, columna);



    DibujarFicha(columna, fila);

    if(jugador==Busqueda.JUGADOR)

      ultimaColumnaFlechaComp  = -1;

    else

      ultimaColumnaFlechaJugador = -1;

    DibujarFlechas(columna, jugador);

    return fila;

  }



  // ------------------------------------------------------------------------

  // La funcion mas importante. Decide lo que sucede cuando el usuario deja

  // de pulsar el boton del raton. Contiene la secuencia de juego.

  // ------------------------------------------------------------------------

  public boolean mouseUp(Event evt, int x, int y) {

    int c;



    if(ConsultarModo(VOLVER_A_EMPEZAR)) {

      init();

      repaint();

      return true;

    }

    PonerModo(PENSANDO);

  juego:

    if(c4k.EstadoJuego()==Busqueda.JUEGO_CONTINUA) {

      // get columna by position of mouse click

      Dimension d = size();

      c = ((x-ANCHURA_BORDE) * 7) / d.width;

      if(c<0 || c>6)

        break juego;

      if(c4k.EstaVacio(c)) {

        filaActual = Mover(Busqueda.JUGADOR, c);

        switch(c4k.EstadoJuego()) {

          case Busqueda.TABLERO_LLENO:     // Caso imposible si comienza el

            EscribirEstado("Hemos empatado");  // jugador, incluido por si lo

            PonerModo(VOLVER_A_EMPEZAR);              // cambio

            break;

          case Busqueda.ALGUIEN_GANO:

            EscribirEstado("Ganaste!");

            PonerModo(VOLVER_A_EMPEZAR);

            break juego;

          case Busqueda.JUEGO_CONTINUA:

            res = c4k.MuevoYo(valor);

            filaActual = Mover(Busqueda.COMP, res);

            switch (c4k.EstadoJuego()) {

              case Busqueda.TABLERO_LLENO:

                EscribirEstado("Hemos empatado");

                PonerModo(VOLVER_A_EMPEZAR);

                break;

              case Busqueda.ALGUIEN_GANO:

                EscribirEstado("Gane!");

                PonerModo(VOLVER_A_EMPEZAR);

                break;

              case Busqueda.JUEGO_CONTINUA:

                switch(valor[0]) {

                  case 100000:

                    EscribirEstado("Te voy a aplastar");

                    break;

                  case -100000:

                    EscribirEstado("Eres una mala persona");

                    break;

                  default:

                    EscribirEstado("Tu mueves");

                }

            }

        }

      }

    }

    if(ConsultarModo(PENSANDO))

      PonerModo(ESPERANDO_QUE_MUEVAS);

    return true;

  }



  // ------------------------------------------------------------------------

  // Dibuja una flecha.

  // ------------------------------------------------------------------------

  void DibujarFlecha(Graphics g, int dx, int dy, int c, Color color, boolean fill) {

    int       xa[] = new int[8];

    int       ya[] = new int[8];



    g.setColor(color);

    xa[0] = ANCHURA_BORDE + c*dx+dx/2;      ya[0] = (dy*3)/4;

    xa[1] =                xa[0] + dx/5;   ya[1] = ya[0] - dy/4;

    xa[2] =                xa[0] + dx/10;  ya[2] = ya[1];

    xa[3] =                xa[2];          ya[3] = ya[0] - dy/2;

    xa[4] =                xa[0] - dx/10;  ya[4] = ya[3];

    xa[5] =                xa[4];          ya[5] = ya[1];

    xa[6] =                xa[0] - dx/5;   ya[6] = ya[1];

    xa[7] =                xa[0];          ya[7] = ya[0];



    if(fill)

      g.fillPolygon(xa, ya, 8);

    else

      g.drawPolygon(xa, ya, 8);

  }



  // ------------------------------------------------------------------------

  // Dibuja la flecha correspondiente a una columna y un jugador.

  // ------------------------------------------------------------------------

  void DibujarFlechas(int columna, int jugador) { 

    if(columna>=0 && columna<7) {

      boolean empty = c4k.EstaVacio(columna);



      Color     color;

      Dimension d      = size(); 

      int       dx     = (d.width - 2*ANCHURA_BORDE) / 7;

      int       dy     = d.height / 8;



      // limpiar el area donde se va a dibujar la flecha

      g.setColor(Color.white);

      g.fillRect(ANCHURA_BORDE, ANCHURA_BORDE, 7*dx, dy-ANCHURA_BORDE);



      if(jugador==Busqueda.JUGADOR) {

        ultimaColumnaFlechaJugador = columna;

        ultimaFlechaJugadorVacia  = empty;

      }

      else if(jugador==Busqueda.COMP)

          ultimaColumnaFlechaComp = columna;

        else {

          ultimaColumnaFlechaJugador = -1;

          ultimaColumnaFlechaComp  = -1;

        }

      if(ultimaColumnaFlechaJugador!=-1)  {

        if(ultimaFlechaJugadorVacia)

          color = Color.red;

        else

          color = Color.gray;

        DibujarFlecha(g, dx, dy, ultimaColumnaFlechaJugador, color, true);

      }

      if(ultimaColumnaFlechaComp!=-1) {

        color = Color.green;

        DibujarFlecha(g, dx, dy, ultimaColumnaFlechaComp, color, ultimaColumnaFlechaComp!=ultimaColumnaFlechaJugador);

      }

    }

  }



  // ------------------------------------------------------------------------

  // Evento que se dispara cuando el usuario mueve el raton para comprobar

  // si tiene que volver a dibujar las flechas.

  // ------------------------------------------------------------------------

  public boolean mouseMove(Event evt, int x, int y) {

    if(ConsultarModo(ESPERANDO_QUE_MUEVAS)) {

      Dimension d = size();

      int       c;

      // examinar columna

      c = ((x-ANCHURA_BORDE) * 7) / d.width;

      if(c!=ultimaColumnaFlechaJugador)

        DibujarFlechas(c, Busqueda.JUGADOR);

    }

    return true;

  }



  // ------------------------------------------------------------------------

  // Informacion del applet.

  // ------------------------------------------------------------------------

  public String getAppletInfo() {

    return "Applet de 4 en raya, por Multivac (4/1/98), basado en un applet de Sven Wiebus";

  }

}
