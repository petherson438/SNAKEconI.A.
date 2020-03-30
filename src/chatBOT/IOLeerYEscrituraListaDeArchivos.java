/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatBOT;

/**
 *
 * @author DELL-BLACK
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Tomas Yussef on 01/03/2015.
 */
public class IOLeerYEscrituraListaDeArchivos
{
    private File doc;
    private FileReader leerArchivo;
    private FileWriter escribirArchivo;
    private PrintWriter escribir;
    private BufferedReader leer;
    private ArrayList<String> texto;
    private boolean genero;
    private String fraseActual;
    private String nombre;
    private String clave;



    /*
          Los siguientes metodos utilizan las siguientes clase
          class1 File donde guarda de forma abstracta el nombre del directorio
          donde se encuentra un archivo.
          class2 FileReader prepara la lectura de un flujos de caracteres
          de un archivo especificado.
          class3 BufferedReader lee flujo de caracteres en forma de linea o array.
          class4 FileWriter: prepara a los objetos de tipo file para la escritura de
          un flujo de caracteres.
          class5 PrintWrite: esta clase imprime el contenido de los objetos en texto a salida
          como por ejemplo el documento Save.txt el cual esta clase es utilizada para imprimir
          contenido en este documento.
          Estas clases son usadas con la finalidad leer archivos
          ubicados en la carpeta de ficheros.
     */

    /*
           El metodo archivo retorona una lista donde se almacena
           todo el contenido del archivo.
     */
    public ArrayList archivo(String nombreArchivo)
    {
        try
        {
            this.texto = new ArrayList<String>();
            String cadena = "";
            System.out.println("archivo(): Buscando el archivo "+nombreArchivo+"...");
            this.doc = new File("C:\\Users\\Tomas\\Documents\\GitHub\\Evidencia---escolaridad\\java\\Evelyn\\src\\ficheros\\"+nombreArchivo);
            this.leerArchivo = new FileReader(this.doc);
            this.leer = new BufferedReader(this.leerArchivo);
            while((cadena = this.leer.readLine()) != null)
                this.texto.add(cadena);
            this.leerArchivo.close();
            this.leer.close();
        }catch(Exception e)
        {
            System.out.println(e.getLocalizedMessage()+"\n------------------Error Archivo--------------");
        }finally
        {
            return this.texto;
        }
    }
    /*
        cada ves que se llama archivo tiene que llamarce lista para poder
        almacenar el contenido de otro archivo si tener el cotenido del
        anterior archivo
     */
    public void iniciarLista()
    {
        this.texto = null;
    }
    /*
        devuelve el contenido de genero
     */
    public boolean getGenero()
    {
        System.out.println("getGenero(): Devolviendo genero...");
        return this.genero;
    }
    /*
        devuelve el contenido de nombre
     */
    public String getNombre()
    {
        System.out.println("getNombre(): Devolviendo nombre...");
        return this.nombre;
    }
    /*
        devuelve el vontenido de frase
     */
    public String getFraseActual()
    {
        System.out.println("getFraseActual(): Devolviendo frase actual...");
        return this.fraseActual;
    }
    /*
         devuelve el contenido de clave
     */
    public String getClave()
    {
        System.out.println("getClave(): Devolviendo clave...");
        return this.clave;
    }
    /*
         Busca el archivo Save, si lo encuentra en el directorio
         devuelve true si no false.
     */
    public boolean existeArchivoGuardado()
    {
        boolean t = false;
        try
        {
            System.out.println("existeArchivoGuardado(): Buscando el archivo guardado...");
            String [] nombres = new String[0];
            this.doc = new File("C:\\Users\\Tomas\\Documents\\GitHub\\Evidencia---escolaridad\\java\\Evelyn\\src\\ficheros\\");
            nombres = this.doc.list();
            for(String cadena: nombres)
            {
                if(cadena.equalsIgnoreCase("Save.txt"))
                {
                    System.out.println("existeArchivoGuardado(): Se encontro archivo, preparando para leer...");
                    this.leerArchivoGuardado();
                    t = true;
                    break;
                }else
                {
                    System.out.println("existeArchivoGuardado(): Buscando archivo...");
                    t = false;
                }
            }

        }catch(Exception e)
        {
           //System.out.println(e.getLocalizedMessage()+"\n------------------Error Archivo--------------");
        }finally
        {

            return t;
        }
    }
    /*
         Este metodo crea el archivo Save y en el almacenar los parametros que
          son recividos por el metodo
     */
    public void guardarArchivo(String nombre, boolean genero,String pClave, String frase)
    {
        try
        {
            System.out.println("guardarArchivo(): Guardando datos en el archivo Save...");
            this.doc = new File("C:\\Users\\Tomas\\Documents\\GitHub\\Evidencia---escolaridad\\java\\Evelyn\\src\\ficheros\\Save.txt");
            this.escribirArchivo = new FileWriter(this.doc);
            this.escribir = new PrintWriter(this.escribirArchivo);
            this.escribir.println(nombre);
            this.escribir.println(genero);
            this.escribir.println(pClave);
            this.escribir.println(frase);;
            this.escribir.close();
            this.escribirArchivo.close();
            System.out.println("guardarArchivo(): Guardando...");
        }catch(Exception e)
        {
            System.out.println(e.getLocalizedMessage()+"\n------------------Error Archivo--------------");
        }
    }
    /*
           Este metodo lo que hace es borrar el archivo save al finalizar las conversaciones
     */
    public void save()
    {
        try
        {
            System.out.println("save(): eliminando archivo...");
            this.doc = new File("C:\\Users\\Tomas\\Documents\\GitHub\\Evidencia---escolaridad\\java\\Evelyn\\src\\ficheros\\Save.txt");
            if(this.doc.delete())
                System.out.println("Fin...");
        }catch (Exception e)
        {
            System.out.println("No se encontro save\n------------------------------error--------------------------");
        }
    }
    /*
          Este metodo lee las propiedades del archivo Save para posterior
          mente se devueltas.
     */
    private void leerArchivoGuardado()
    {
        try
        {
            System.out.println("leerArchivoGuardado(): Leyendo propiedades del archivo Save.txt...");
            this.doc = new File("C:\\Users\\Tomas\\Documents\\GitHub\\Evidencia---escolaridad\\java\\Evelyn\\src\\ficheros\\Save.txt");
            this.leerArchivo = new FileReader(this.doc);
            this.leer = new BufferedReader(this.leerArchivo);
            this.nombre = this.leer.readLine();
            if(this.leer.readLine().equals("true")) this.genero = true;
            else this.genero = false;
            this.clave = this.leer.readLine();
            this.fraseActual = this.leer.readLine();
            this.leerArchivo.close();
            this.leer.close();
        }catch(Exception e)
        {
            System.out.println(e.getLocalizedMessage()+" "+e.getMessage()+"\n------------------Error Archivo--------------");
        }
    }

}