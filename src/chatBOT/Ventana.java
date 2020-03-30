/*
el principal
ojo en este programa hay que direccionar las rutas a los archivos txt que contienen la informacion
que utilizara el bot para las respuestas
 */
package chatBOT;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

/**
 * Created by Tomas Yussef on 01/03/2015.
 */
public class Ventana extends JFrame implements ActionListener, KeyListener, WindowListener
{

    private JButton entrada;
    private JTextArea areaDeSalida;
    private JTextField campoDeEntrada;
    private boolean t;

    public Ventana() {
        //propiedades del Frame
        super("Evelyn");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(350, 400);
        this.setResizable(false);
        //propiedades del textArea
        this.areaDeSalida = new JTextArea();
        this.areaDeSalida.setEditable(false);
        this.areaDeSalida.setLineWrap(true);
        this.areaDeSalida.setWrapStyleWord(true);
        Font font = new Font("Arial", Font.BOLD, 15);
        this.areaDeSalida.setFont(font);
        //propiedades de JSCROLLPANE
        JScrollPane scroll = new JScrollPane(this.areaDeSalida);
        this.add(scroll,BorderLayout.CENTER);
        //panel-------------------Propiedades
        JPanel pane = new JPanel();
        //pripiedades TextFile
        this.campoDeEntrada = new JTextField(20);
        pane.add(this.campoDeEntrada);
        //Propiedades del Boton
        entrada = new JButton(" Enviar ");
        pane.add(this.entrada);
        this.add(pane, BorderLayout.SOUTH);
        //Fin------------ propiedades del panel
        this.setVisible(true);
        //fin propiedades del Frame
        //eventos
        this.entrada.addActionListener(this);
        this.campoDeEntrada.addKeyListener(this);
        this.entrada.addKeyListener(this);
        this.addWindowListener(this);
        //agregar texto  desagregue este mensaje
       /* JOptionPane.showMessageDialog(null,"Para poder iniciar, inicie con un saludo y su nombre,\n" +
                " y para poder finalizar despidace con un adios o bye.\n" +
                "Este programa reconoce palabras como Tarea, Examen, Exposicione, Reporte,\n" +
                "Reprobar y Evaluacion, la conversacion se hace en torno a esas palabras,\n" +
                "trate de hacer concreto en su respuestas o preguntas de tal forma\n" +
                "que contenga algunas de estas palabras claves.");
        //-------*/
        this.t = false;
    }

    /*
           Eventos-----------------------------------
     */

    @Override
    public void actionPerformed(ActionEvent evt)
    {
        //para poder hacer el analisis el jtextfiel debe contener caracteres
        if(t == false && !this.campoDeEntrada.getText().equals(""))
        {
            String cadena = this.campoDeEntrada.getText();//En esta variable se alamacena el contenido de campo
            EntradaDeUsuario user = new EntradaDeUsuario(cadena);// se crea una instancia de EntradaDeUsuario
            IOLeerYEscrituraListaDeArchivos borrar = new IOLeerYEscrituraListaDeArchivos();// se crea una instancia de IOLeerYEscrituraListaDeArchivos
            user.preparandoAnalisis();//Se llama el metodo preparando analisis de la instancia user
            this.areaDeSalida.append(user.getNombre()+" >> "+ user.getCadenaFrase()+"\n");//se muestra en el JTextArea el contenido del usuario
            if(user.getFin())//se analiza si la conversacion a finalizado
            {
                //si finaliza
                this.areaDeSalida.append("Evelyn >> " + user.getFraseFinal() + "\n");//muestra la ultima frase al usuario
                this.campoDeEntrada.setEnabled(false);//inabilita jtextfield para no poder ingresar nada
                this.campoDeEntrada.setText("");//se limpia jtextfiel
                borrar.save();//se borra archivos guardados en el proceso
                this.entrada.setText("Iniciar");//se cambia el boton para poder iniciar otra conversacion
                this.repaint();//re vuelve a iniciar los componentes
                t = true;

            }else
            {
                //pero si la conversacion no ha finalizado
                this.areaDeSalida.append("Evelyn>> " + user.getFraseFinal() + "\n");//agrega lo que analizo
                this.campoDeEntrada.setText("");//inicializa jtextfield
            }
        }else
        {
            this.areaDeSalida.setText("");//inicializa el jTEXTAREA
            this.entrada.setText(" Enviar ");//el boton se le cambia el nombre
            this.campoDeEntrada.setEnabled(true);// habilita el jtextfiel
            this.repaint();//inicializa de nuevo los componentes
            t = false;
        }

    }

    @Override
    public void keyPressed(KeyEvent evt)
    {    }

    @Override
    public void keyTyped(KeyEvent e)
    {    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        //Condicion la cual verifica si la tecla Enter es precionada
        // y analiza en campo de entrada  el cual verifica si contiene caracteres o no
        if(e.getKeyCode() == e.VK_ENTER && !this.campoDeEntrada.getText().equals(""))
        {
            if(t == false)
            {
                String cadena = this.campoDeEntrada.getText();//En esta variable se alamacena el contenido de campo
                EntradaDeUsuario user = new EntradaDeUsuario(cadena);// se crea una instancia de EntradaDeUsuario
                IOLeerYEscrituraListaDeArchivos borrar = new IOLeerYEscrituraListaDeArchivos();// se crea una instancia de IOLeerYEscrituraListaDeArchivos
                user.preparandoAnalisis();//Se llama el metodo preparando analisis de la instancia user
                this.areaDeSalida.append(user.getNombre()+" >> "+ user.getCadenaFrase()+"\n");//se muestra en el JTextArea el contenido del usuario
                if(user.getFin())//se analiza si la conversacion a finalizado
                {
                    //si finaliza
                    this.areaDeSalida.append("Evelyn >> "+user.getFraseFinal()+"\n");//muestra la ultima frase al usuario
                    this.campoDeEntrada.setEnabled(false);//inabilita jtextfield para no poder ingresar nada
                    this.campoDeEntrada.setText("");//se limpia jtextfiel
                    borrar.save();//se borra archivos guardados en el proceso
                    this.entrada.setText("Iniciar");//se cambia el boton para poder iniciar otra conversacion
                    this.repaint();//re vuelve a iniciar los componentes
                    t = true;

                }else
                {
                    //pero si la conversacion no ha finalizado
                    this.areaDeSalida.append("Evelyn>> "+user.getFraseFinal()+"\n");//agrega lo que analizo
                    this.campoDeEntrada.setText("");//inicializa jtextfield
                }
            }else
            {
                this.areaDeSalida.setText("");//inicializa el jTEXTAREA
                this.entrada.setText(" Enviar ");//el boton se le cambia el nombre
                this.campoDeEntrada.setEnabled(true);// habilita el jtextfiel
                this.repaint();//inicializa de nuevo los componentes
                t = false;
            }

        }
    }
    @Override
    public void windowClosing(WindowEvent evt)
    {
        /*
            Se crea una instacia de IOLeerYEscrituraListaDeArchivos para
            poder usar el metodo de save() el cual borra todos los archivos creados
            en el proceso.
         */
        IOLeerYEscrituraListaDeArchivos borrar = new IOLeerYEscrituraListaDeArchivos();
        borrar.save();
    }
    @Override
    public void windowClosed(WindowEvent evt)
    {    }
    @Override
    public void windowOpened(WindowEvent evt)
    {    }
    @Override
    public void windowIconified(WindowEvent evt)
    {    }
    @Override
    public void windowDeiconified(WindowEvent evt)
    {    }
    @Override
    public void windowActivated(WindowEvent evt)
    {    }
    @Override
    public void windowDeactivated(WindowEvent evt)
    {    }

    /*
        ------------------------ Metodo Principal
    */

    public static void main(String[] args) {
        new Ventana();
    }
}
