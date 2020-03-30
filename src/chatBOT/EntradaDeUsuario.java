/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatBOT;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Created by Thomas Yussef on 01/03/2015.
 */
public class EntradaDeUsuario
{
    //Propiedades...
    private String nombre;
    private String fraseFinal;
    private String fraseDeUsuarioArchivo;
    private String cadenaFrase;
    private String palabraCalve;
    private String paalabraCalveArchivo;
    private String [] fraseDividida;
    private boolean genero;
    private boolean fin;
    private ArrayList<String> frases;
    private IOLeerYEscrituraListaDeArchivos leeOEscribe;

    /*
        Constructor en el se inicializan las pripiedades de la clase
     */
    public EntradaDeUsuario(String cadena)
    {
        System.out.println("******************************************************\n" +
                "******************************************************\n" +
                "EntradaDeUsuario(): Dividiendo cadena y inicializando...");
        this.fraseDividida = cadena.split("\\s");
        this.cadenaFrase =cadena;
        this.fraseDeUsuarioArchivo = "";
        this.genero = true;
        this.fin = false;
        this.fraseFinal = "";
        this.palabraCalve = "";
        this.paalabraCalveArchivo = null;
        this.nombre = null;
        this.frases = new ArrayList <String>();
        this.leeOEscribe = new IOLeerYEscrituraListaDeArchivos();
    }
    /*
        Este metodo retorna el contenido de cadenafrase
     */
    public String getCadenaFrase()
    {
        System.out.println("getCadenaFrase(): Retornando Cadena...");
        return this.cadenaFrase;
    }
    /*
         Devuelve el contenido de frase final
     */
    public String getFraseFinal()
    {
        System.out.println("getGraseFinal(): Retornando Frase Final...");
        return this.fraseFinal;
    }
    //devuelve el contenido de nombre
    public String getNombre()
    {
        System.out.println("getNombre(): Retornando Nombre...");
        return this.nombre;
    }
    //devuelve el contenido de fin.
    public boolean getFin()
    {
        System.out.println("getFin(): Retornando la propiedad fin...");
        return this.fin;
    }

    public void preparandoAnalisis()
    {
        System.out.println("preparandoAnalisis(): Comenzado El Analisis...");
        if(this.archivoGuardado() == false)//busca si el archivo save esta guardado
            this.identificarNombreYGenero();//si no esta guardado busca nombres y genero para ser alamacenados en save
        else if(this.buscarPalabrasClaves("Palabras"))//si no, entoces busca palabras claves
            this.formarFraseDeRespuesta(false);//si encuentra generara una respues
        else if(this.buscarPalabrasClaves("Preguntas"))//si no busca palabras claves a posibles preguntas
        {
            this.formarFraseDeRespuesta(false);//si  encuentra entoces genera una frase de respuestas
            this.fraseDeContinuacion(true);// mas aparte una de continuidad
        }else this.fraseDeContinuacion(false);//de lo contrario seleccionara una frase de condicion
    }
    private boolean archivoGuardado()
    {
        System.out.println("achivoGuardado(): Buscando Archivo...");
        if(this.leeOEscribe.existeArchivoGuardado())//verifica si el archivo esta guardado
        {
            this.genero = leeOEscribe.getGenero();//se obtiene genero
            this.nombre = leeOEscribe.getNombre();//se obtiene nombre
            this.fraseDeUsuarioArchivo = leeOEscribe.getFraseActual();// se obtiene la frase
            this.paalabraCalveArchivo = leeOEscribe.getClave();// se obtiene la clave
            System.out.println(this.paalabraCalveArchivo);
            return true;//re vuelve verdadero si la condicion se cumple
        } else return false;// si no false
    }

    private void identificarNombreYGenero()
    {
        System.out.println("identificandoNombreYGenero(): Buscando nombres...");
        if(this.buscarNombre(leeOEscribe.archivo("Masculino.txt"),true)
                || this.buscarNombre(leeOEscribe.archivo("Femenino.txt"),false)) //se busca nombres masculinos y femeninos
        {
            this.formarFraseDeRespuesta(true);// si uno de los dos es verdadero se genera una frase de saludo
        }
        this.leeOEscribe.guardarArchivo(this.nombre, this.genero,this.palabraCalve, this.fraseDeUsuarioArchivo);//se guarda
        this.leeOEscribe.iniciarLista();// se iniciliza la lista de IOlectura y escritura
    }
    /*
         Este metodo utiliza un for par busca los nombre en la lista
         si encuentra se rompe el ciclo y se almacena el nombre y retorna
         verdadero si no falso
     */
    private boolean buscarNombre(ArrayList<String> nombreM, boolean g)
    {
        boolean t =false;
        String cadena= "";
        System.out.println("buscarNombre(): Buscando nombre en las listas...");
        for(int contador1 = 0, contador2  = 0 ;contador1 < this.fraseDividida.length; contador2++)
        {
            System.out.println("...");
            if(contador2 == nombreM.size())
            {
                contador2 = -1;
                contador1++;
            }else if(this.fraseDividida[contador1].equalsIgnoreCase(nombreM.get(contador2)))
            {
                t = true;
                this.genero = g;
                this.nombre = nombreM.get(contador2);
                System.out.println(this.nombre+" "+this.genero);
                break;
            }else if(contador1 == this.fraseDividida.length-1 && this.nombre == null && contador2 == nombreM.size()-1)
            {
                do{
                    cadena = JOptionPane.showInputDialog(null,"Como te llamas?(:");
                }while(cadena.equalsIgnoreCase(""));
                this.cadenaFrase = cadena;
                this.fraseDividida = cadena.split("\\s");
                contador2 = -1;
                contador1 = 0;
            }
        }
        return t;
    }
    /*
          Frase de respuesta recibe un parametro booleano que si es verdadero
          indica que es un saludo y genra una frase saludando al usuario, y si es
           falso seleccionara una de las respuestas dependiendo del genero.
     */
    private void formarFraseDeRespuesta(boolean saludo)
    {
        System.out.println("formarFraseDeRespuesta(): Formando frase de respuesta...");
        if(saludo)
        {
            this.fraseFinal = "Hola muscho gusto "+this.nombre+", ¿Como estas?";
        }else seleccionaFrase();
    }
    /*
         busca palabras claves de un archivo el cual el nombre de ese archivo lo recibe como
         parametro, si encuentra devulve verdadero y si no false
     */
    private boolean buscarPalabrasClaves(String c)
    {
        System.out.println("buscandoPalabrasClaves(): Buscando paabras en la listas "+c+"....");
        boolean t = false;
        ArrayList<String> lista = this.leeOEscribe.archivo(c+".txt");
        for(int contador1 = 0, contador2 = 0; contador1 < this.fraseDividida.length; contador2++)
        {
            System.out.println("...");
            if(contador2 == lista.size())
            {
                contador1++;
                contador2 = 0;
            }else if(this.fraseDividida[contador1].trim().equalsIgnoreCase(lista.get(contador2).trim())) {
                this.palabraCalve = lista.get(contador2);
                if(this.palabraCalve.equalsIgnoreCase("adios") || this.palabraCalve.equalsIgnoreCase("bye"))
                    this.fin = true;
                System.out.println("Plabra: "+this.palabraCalve);
                t = true;
            }
        }
        this.leeOEscribe.iniciarLista();
        return t;
    }
    /*
         Este metodo genera una frase de continuacion si es que no se encuentra una
         palabra clave.
     */
    private void fraseDeContinuacion(boolean t)
    {
        System.out.println("fraseDeContinuacion(): Formando frase de continuacion...");
        ArrayList<String> lista;
        if(t)// esta condicion es para evaluar si se le hizo una pregunta a la maquina
        {
            //si lo hizo...
            lista = leeOEscribe.archivo("Continuidad.txt");//generara una lista del arachivo de continuidad
            this.frases = lista;
            this.leeOEscribe.iniciarLista();
            this.seleccionFormaAleatoria(this.fraseDeUsuarioArchivo,true);//seleccionara una frase de la lista de forma
            //aleatoria, pasandole como true indicando que se ha hecho una pregunta a la maquina
        }else if(this.palabraCalve != null)//si hay palabra clave en el archivo save
        {
            this.palabraCalve = this.paalabraCalveArchivo;
            //se buscara el documento con el nombre de la palabra clave para generar una frase de continuacion
            //un poco mas acertada.
            lista = leeOEscribe.archivo(this.paalabraCalveArchivo+".txt");
            this.buscarEnLista(this.paalabraCalveArchivo, lista);
            this.leeOEscribe.iniciarLista();
            this.seleccionFormaAleatoria(this.fraseDeUsuarioArchivo,false);// se leccionando frase de forma aleatoria
        }else
        {
            // obtener lista del archivo de continuidad, no hay palabra clave en save
            lista = leeOEscribe.archivo("Continuidad.txt");
            this.frases = lista;
            this.leeOEscribe.iniciarLista();
            this.seleccionFormaAleatoria(this.fraseDeUsuarioArchivo,false);
        }
    }
    /*
         Seleccionar frase, antes de seleccionar llama a los archivos respuestaM O repuestasF
         el cual esto va dependen de genero. se busca en la lista obtenida la palabra clave para generar
         una respuesta.
     */
    private void seleccionaFrase()
    {
        System.out.println("seleccionaFrase(): Seleccionando lista para la frase");
        ArrayList<String> lis;
        if(this.genero)lis = this.leeOEscribe.archivo("RespuestasM.txt");
        else lis = this.leeOEscribe.archivo("RespuestasF.txt");
        this.buscarEnLista(this.palabraCalve,lis);
        this.seleccionFormaAleatoria(this.fraseDeUsuarioArchivo,false);//de las frases seleccionadas se elije una aleatoreamente
        this.leeOEscribe.iniciarLista();
    }
    /*
         Se busca en la lista la palabra clave.
     */
    private void buscarEnLista(String palabra, ArrayList<String> lista)
    {
        System.out.println("buscandoEnLista(): Buscando palabras claves en lista...");
        String [] auxiliar = new String[1];
        String cadena="";
        for(int contador1 = 0, contador2 = -1; contador1 < lista.size(); contador2++)
        {
            System.out.println("....");
            if(contador2 == auxiliar.length)
            {
                contador1++;
                contador2 = -2;
            }else if(contador2 == -1)
            {
                cadena =lista.get(contador1).replace("¿", "").replace("?","").replace(",","").replace(".","");
                auxiliar = cadena.split("\\s");
            }else if(palabra.equalsIgnoreCase(auxiliar[contador2]))
            {
                this.frases.add(lista.get(contador1));
                contador2=-2;
                contador1++;
            }
        }
    }
    /*
        Seleccion de forma aleatorea,
     */
    private void seleccionFormaAleatoria(String frase, boolean t)
    {
        System.out.println("seleccionFormaAleatoria():Seleccionando frase ....");
        int numero;
        String cadena = "";
        do
        {
            numero = (int)(Math.random()*(this.frases.size()-1)+0);
            cadena = this.frases.get(numero);
            System.out.println("Se selecciono: "+cadena);
        }while(cadena.equalsIgnoreCase(frase));//el ciclo se repite si la frase seleccionada es igual a la que se encuentra en el archivo save
        //si se le hizo una pregunta a la maquina almacena el contenido de la frase final con la que se lecciono
        if(t)this.fraseFinal +=".\n"+cadena;//de tal forma que responda la pregunta y haga una nueva.
        else this.fraseFinal = cadena;
        this.fraseDeUsuarioArchivo = cadena;
        this.leeOEscribe.guardarArchivo(this.nombre, this.genero, this.palabraCalve, this.fraseDeUsuarioArchivo);
    }
}