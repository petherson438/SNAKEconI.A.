
package NeuronaArtificialBasica;
//Una simple neurona artificial para desarrollar habilidades en este paradigma relacionado con la programación en paralelo.
//Si sabes programar nodos, si sabes de redes entonces puedes crear o simular una red neuronal con esta neurona.
//Neurona artificial basica.
//Autor Rafael Angel Montero Fernández.
//A ver como me sale, no soy super en esto de la inteligencia artificial. Pero me gusta el tema.
//Las entradas y salidas de la neurona sirven para conectar con otras neuronas y asi se forma una red neuronal artificial.
//Hay que ser bueno en logica para informaticos para avanzar en esta tecnologia.
//En lugar de un tipo de datos primitivo double se ha usado double porque es más estable con los numeros.
//No soy experto en esto, solo soy amante de este tema de la inteligencia artificial.
 
public class Neurona_artificial
{
   private double vPeso1, vPeso2, vPeso3;//Lo correcto es usar un vector o una matriz, ya que los pesos son las sinapsis de la neurona y una neurona puede tener N cantidad de sinapsis. Pero para este ejemplo y como aun no domino las matrices en Java, mejor algo basico.
   private double vTasa_de_aprendizaje;//Es el nivel de aprendizage de la neurona artificial.
   private double vUmbral_de_activacion;//El umbral o maximo o minimo nivel de activacion de la neurona al recivir algun estimulo.
 
   public Neurona_artificial(double nuevo_peso1,double  nuevo_peso2,double  nuevo_peso3)//Debo aclarar que el codigo original esta en c++ y yo estoy haciendo una traduccion y unas modificasiones a mi modo.
   {//En caso de usarse un vector para los pesos entonces, el parametro del cosntructor debe ser un vector llamado Vector mPesos.
 
      //Valores predefinidos para arrancar pero si conoces más de la tecnologia Neuroredes Artificiales puedes poner otros valores.
      this.vPeso1=nuevo_peso1;
      this.vPeso2=nuevo_peso2;
      this.vPeso3=nuevo_peso3;
 
      this.vUmbral_de_activacion=0;
      this.vTasa_de_aprendizaje=0.1;
   }//Fin del constructor.
 
 
 
   public int fSalida (double nueva_entrada1,double  nueva_entrada2,double  nueva_entrada3)//Por nomenclatura se ha usado la f para indicar que se trata de una funcion capaz de retornar datos.
   {
      double vSumatoria=  (nueva_entrada1 * this.vPeso1) + (nueva_entrada2 * this.vPeso2) +  (nueva_entrada3 * this.vPeso3);
 
      return (vSumatoria > this.vUmbral_de_activacion ? 1:0); //Esta linea a retornar es un if pero en modo nivel de maquina, un poco más efectivo que el if normal.
   }//Fin de fSalida
 
 
   //Funcion principal de esta neurona. Aunque cuando se quiere hacer una red, se conecta una primera fila con las entradas para el usuario y las filas secundarias tienen su entrada conectada con la salida de la primera fila, la neurona de salida tiene su salida libre para mostrar los datos en pantalla.
   public int fEntrenamiento_supervisado(double nueva_entrada1,double  nueva_entrada2,double  nueva_entrada3, int nueva_salida_deseada_por_el_usuario)//La salida deseada por el usuario, es un valor que el usuario o programador pone en modo ejecusion o cuando hace una instancia de la neurona, si la neurona lanza una salida igual a la deseada se dice que a aprendido y aprovado el entrenamiento.
   {//Las neuronas solo se programan una vez y cada vez que se quiera que realice una tarea diferente entonces se le entrena. La red si debe ser configurada de acuerdo a la tarea pero eso se puede definir para que sea realizable durante la ejecucion del programa.
      double vResultado = fSalida ( nueva_entrada1,  nueva_entrada2, nueva_entrada3);
      int vRespuesta=0;//Salida de la funcion.
 
      if (vResultado != nueva_salida_deseada_por_el_usuario)
      {
 
         if (vResultado > nueva_salida_deseada_por_el_usuario)
         {
            //Actualizacion de los pesos (Sinapsis)
            this.vPeso1 = this.vPeso1 - (this.vTasa_de_aprendizaje * nueva_entrada1);
            this.vPeso2= this.vPeso2 - (this.vTasa_de_aprendizaje * nueva_entrada2);
            this.vPeso3= this.vPeso3 - (this.vTasa_de_aprendizaje * nueva_entrada3);
            //--------------------------------------------------------
 
            //Actualizacion del umbral de activacion
            this.vUmbral_de_activacion +=  this.vTasa_de_aprendizaje;
 
         }//Fin del if >
         else
         {
            //Actualizacion de los pesos (Sinapsis)
            this.vPeso1 = this.vPeso1 + (this.vTasa_de_aprendizaje * nueva_entrada1);
            this.vPeso2= this.vPeso2 + (this.vTasa_de_aprendizaje * nueva_entrada2);
            this.vPeso3= this.vPeso3 + (this.vTasa_de_aprendizaje * nueva_entrada3);
            //--------------------------------------------------------  
            //Actualizacion del umbral de activacion
            this.vUmbral_de_activacion -=  this.vTasa_de_aprendizaje;
 
         }//Fin del else anidado >
 
         vRespuesta=1;//Neurona activada y respuesta positiva.
 
         }//Fin del if !=
         else
         {
            vRespuesta=0;//Neurona desactivada y respuesta negativa.
         }//Fin del else !=
 
      return vRespuesta;
   }//Fin de fEntrenamiento_supervisado
 
   public double getUmbral_de_activacion()
   {
      return this.vUmbral_de_activacion;
   }//Fin de getUmbral_de_activacion
 
   public double getTasa_de_aprendizaje()
   {
      return this.vTasa_de_aprendizaje;
   }//Fin de getTasa_de_aprendizaje
 
   public String getPesos()
   {
      /*
       * Se ha usado un String para retornar los tres valores de los pesos ceparados con
       * saltos de linea.
       * Pero en lugar del String para quienes dominen los vectores, va un vector.
       * Quedaría así:
       * public Vector getPesos(){...}
       */
      return "" +  this.vPeso1 + "\n" + this.vPeso2 + "\n" + this.vPeso3;
   }//Fin de getPesos
 
   //Conclusiones: Esta neurona artificial es parte de una red más grande con diferentes diseños de neuronas.
}//Fin del class