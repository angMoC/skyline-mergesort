/*
 * Programa: Skyline
 * Descripción: Resuelve el problema del skyline utilizando un algoritmo de mergesort.
 * Uso: java Skyline [-t] [-h] [fichero_entrada] [fichero_salida]
 *   -t: Habilita trazas de llamadas recursivas.
 *   -h: Muestra la ayuda.
 *   fichero_entrada: Archivo con los datos de entrada (conjunto de edificios de la ciudad).
 *   fichero_salida: Archivo de salida con la secuencia que representa el skyline de la ciudad.
 */

import java.io.*;
import java.util.*;

public class Skyline {

    // Declaración de estructuras de datos y variables para almacenar
    // información sobre edificios, trazas y otros parámetros del algoritmo.

    static List<String[]> listaEdificios = new ArrayList<>();
    static List<Edificio> listaObjetosEdificios = new ArrayList<>();
    static List<String> listaTrazas = new ArrayList<>();

    static int x_izquierda;
    static int x_derecha;
    static int altura;
    static int fin_edificio = 0;

    static boolean trazas_activas = false;
    static String datosSalida = null;

    public static void main (String args[]) {

        // Verifica la cantidad de argumentos de la línea de comandos.
        // Si no hay argumentos, muestra un mensaje de error indicando que se necesitan datos de entrada.
        // Si hay más de 4 argumentos, muestra un mensaje de error indicando que hay demasiados datos de entrada.

        if(args.length == 0){
            System.out.println("Se necesitan datos de entrada. -h para mas info.");
            return;
        } else if (args.length > 4){
            System.out.println("Demasiados datos de entrada.");
            return;
        }

        String datosEntrada = null;

        // Itera sobre los argumentos de la línea de comandos para procesar cada uno.
        for (int i = 0; i < args.length; i++) {
            // Si se encuentra el argumento "-h", muestra la ayuda del programa.
            if (args[i].equals("-h")) {
                System.out.println("SINTAXIS: skyline [-t][-h] [fichero entrada] [fichero salida]");
                System.out.println("    -t Traza cada llamada recursiva y sus parámetros");
                System.out.println("    -h Muestra esta ayuda");
                System.out.println("    [fichero con datos entrada] Conjunto de edificios de la ciudad");
                System.out.println("    [fichero de salida] Secuencia que representan el skyline de la ciudad");
                return;
                // Si se encuentra el argumento "-t", activa las trazas de llamadas recursivas.
            } else if (args[i].equals("-t")) {
                trazas_activas = true;
                // Si aún no se ha asignado el nombre del archivo de entrada, asigna el valor actual a datosEntrada.
            } else if (datosEntrada == null) {
                datosEntrada = args[i];
                // Si ya se ha asignado el nombre del archivo de entrada, asigna el valor actual a datosSalida.
            } else {
                datosSalida = args[i];
            }
        }

        // Verifica si se ha proporcionado un nombre de archivo de entrada.
        // Si no se ha proporcionado, muestra un mensaje de error.
        if(datosEntrada == null){
            System.out.println("Se necesitan datos de entrada. -h para mas info.");
            return;
        }

        // Verifica si se ha proporcionado un nombre de archivo de salida.
        // Si se ha proporcionado, imprime un mensaje indicando el nombre del archivo de salida.
        if (datosSalida != null) {
            System.out.println("El resultado se almacena en " + datosSalida);
        }

        try {
            // Intenta abrir el archivo de entrada especificado.
            File skyline_datos = new File(datosEntrada);
            Scanner myReader = new Scanner(skyline_datos);

            // Lee cada línea del archivo y agrega sus datos a la lista de edificios.
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                listaEdificios.add(data.split(","));
            }
            // Cierra el lector de archivos.
            myReader.close();

        } catch (FileNotFoundException e) {
            // Si el archivo de entrada no se encuentra, muestra un mensaje de error.
            System.out.println("No existe el fichero seleccionado");
            return;
        }

        // Itera sobre cada conjunto de datos de edificio en la lista de edificios.
        for (String[] edificio : listaEdificios) {
            // Crea un nuevo objeto Edificio utilizando los puntos clave del edificio actual y lo agrega a la lista de objetos de edificios.
            listaObjetosEdificios.add(new Edificio(getPuntosClave(edificio)));
        }

        // Divide los edificios representados por los objetos en listaObjetosEdificios utilizando el algoritmo de división.
        Edificio test = dividir(listaObjetosEdificios);

        // Si no se hay un archivo de salida, imprime el skyline resultante en la consola.
        if (datosSalida == null){
            System.out.println(test);
        }

        // Verifica si se ha proporcionado un nombre de archivo de salida.
        if (datosSalida != null) {
            try {
                // Verifica si el archivo de salida ya existe.
                boolean existeFichero = new File(datosSalida).isFile();
                // Si el archivo no existe, crea un nuevo archivo de salida y escribe el skyline en él.
                if(!existeFichero) {
                    FileOutputStream file = new FileOutputStream(datosSalida);
                    byte[] array;
                    // Si las trazas están activadas, obtiene la representación del skyline con las trazas.
                    if(trazas_activas){
                        array = test.toString(listaTrazas).getBytes();
                    }else{ // Si las trazas no están activadas, obtén la representación del skyline estándar.
                        array = test.toString().getBytes();
                    }
                    file.write(array);
                    file.close();
                } else { // Si el archivo ya existe, muestra un mensaje de error.
                    System.out.println("El fichero de salida ya existe. No se puede sobreescribir.");
                }
            }
            // Captura cualquier excepción que pueda ocurrir durante el proceso.
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método estático que convierte un conjunto de coordenadas de un edificio en una lista de puntos clave.
     * Los puntos clave consisten en las coordenadas izquierda (x_izquierda, altura) y derecha (x_derecha, fin_edificio) del edificio.
     * @param coordenadas Un array de cadenas que contiene las coordenadas del edificio en el formato [x_izquierda, x_derecha, altura].
     * @return Una lista de objetos Coordenadas que representan los puntos clave del edificio.
     */
    public static List<Coordenadas> getPuntosClave(String[] coordenadas) {

        List<Coordenadas> listaPuntos= new ArrayList<>();

        x_izquierda = Integer.parseInt(coordenadas[0]);
        x_derecha = Integer.parseInt(coordenadas[1]);
        altura = Integer.parseInt(coordenadas[2]);

        Coordenadas punto1 = new Coordenadas(x_izquierda, altura);
        Coordenadas punto2 = new Coordenadas(x_derecha, fin_edificio);

        listaPuntos.add(punto1);
        listaPuntos.add(punto2);

        return listaPuntos;
    }

    /**
     * Método estático que divide la lista de edificios en subconjuntos más pequeños y los combina recursivamente.
     * Si las trazas están activas, registra información sobre la llamada recursiva.
     * @param listaEdificios La lista de edificios a dividir y combinar.
     * @return El edificio combinado resultante de la división y combinación recursiva.
     */
    public static Edificio dividir(List<Edificio> listaEdificios) {

        if (trazas_activas && (null != datosSalida)) {
            // Registra información sobre la llamada recursiva si las trazas están activas y se proporciona un archivo de salida.
            listaTrazas.add("Se ha llamado al método recursivo. \n Los parámetros son: " + listaEdificios.toString() + "\n");
        } else if (trazas_activas && (datosSalida==null)) {
            System.out.println("Se ha llamado al método recursivo.");
            System.out.println("Los parámetros son: " + listaEdificios.toString());
        }

        // Divide la lista de edificios en dos mitades.
        List<Edificio> primera_mitad;
        List<Edificio> segunda_mitad;
        int n  = listaEdificios.size();

        // Caso base: si solo hay un edificio en la lista, devuelve ese edificio.
        if (n == 1) {
            return listaEdificios.get(0);
        } else {
            // Divide la lista de edificios en dos mitades.
            primera_mitad = listaEdificios.subList(0, n/2);
            segunda_mitad = listaEdificios.subList(n/2, n);
            // Combina recursivamente las mitades divididas.
            return combinar(dividir(primera_mitad), dividir(segunda_mitad));
        }
    }

    /**
     * Método estático que combina dos edificios representados por dos skylines.
     * @param skyline1 El primer skyline a combinar.
     * @param skyline2 El segundo skyline a combinar.
     * @return El edificio resultante de combinar los dos skylines.
     */
    public static Edificio combinar(Edificio skyline1, Edificio skyline2) {
        // Obtiene la longitud de cada skyline y extrae las abscisas y alturas de cada uno.
        int longitud_skyline1 = skyline1.size();
        int longitud_skyline2 = skyline2.size();
        int[] abcisa_skyline1 = skyline1.extraerAbcisas();
        int[] altura_skyline1 = skyline1.extraerAlturas();
        int[] abcisa_skyline2 = skyline2.extraerAbcisas();
        int[] altura_skyline2 = skyline2.extraerAlturas();

        // Inicializa variables para el proceso de combinación.
        int indice_skyline1 = 0;
        int indice_skyline2 = 0;
        int indice_res = 0;
        int x_actual = 0;
        int altura_actual = 0;
        int ultima_altura = 0;
        int ultima_altura_de_A = 0;
        int ultima_altura_de_B = 0;
        List<Coordenadas> lista_respuestas = new ArrayList<>();
        Coordenadas res_coordenadas;
        Edificio res_edificio = null;
        int[] res_x = new int[(longitud_skyline1+longitud_skyline2)*2];
        int[] res_altura = new int[(longitud_skyline1+longitud_skyline2)*2];

        // Comienza el proceso de combinación de los dos skylines.
        while (indice_skyline1 < longitud_skyline1 || indice_skyline2 < longitud_skyline2) {
            // Determina la próxima coordenada a considerar.
            if (indice_skyline1 >= longitud_skyline1 && indice_skyline2 < longitud_skyline2) {
                x_actual = abcisa_skyline2[indice_skyline2];
                altura_actual = altura_skyline2[indice_skyline2];
                indice_skyline2++;
            } else if (indice_skyline1 < longitud_skyline1 && indice_skyline2 >= longitud_skyline2) {
                x_actual = abcisa_skyline1[indice_skyline1];
                altura_actual = altura_skyline1[indice_skyline1];
                indice_skyline1++;
            } else {
                x_actual = Math.min(abcisa_skyline1[indice_skyline1], abcisa_skyline2[indice_skyline2]);
                if ((abcisa_skyline1[indice_skyline1] < abcisa_skyline2[indice_skyline2])) {
                    altura_actual = Math.max(altura_skyline1[indice_skyline1], ultima_altura_de_B);
                    ultima_altura_de_A = altura_skyline1[indice_skyline1];
                    indice_skyline1++;
                } else if (abcisa_skyline1[indice_skyline1] > abcisa_skyline2[indice_skyline2]) {
                    altura_actual = Math.max(ultima_altura_de_A, altura_skyline2[indice_skyline2]);
                    ultima_altura_de_B = altura_skyline2[indice_skyline2];
                    indice_skyline2++;
                } else {
                    int nueva_altura_max = Math.max(altura_skyline1[indice_skyline1], altura_skyline2[indice_skyline2]);
                    ultima_altura_de_A = altura_skyline1[indice_skyline1];
                    ultima_altura_de_B = altura_skyline2[indice_skyline2];
                    altura_actual = nueva_altura_max;
                    indice_skyline1++;
                    indice_skyline2++;
                }
            }
            // Si la altura actual es diferente a la última altura registrada, agrega la coordenada a la lista de respuestas.
            if(altura_actual != ultima_altura){
                ultima_altura = altura_actual;
                res_x[indice_res] = x_actual;
                res_altura[indice_res] = altura_actual;
                indice_res++;
            }
        }
        // Crea las coordenadas y el edificio resultante a partir de las respuestas generadas.
        for(int i = 0; i < indice_res; i++) {
            res_coordenadas = new Coordenadas(res_x[i], res_altura[i]);
            lista_respuestas.add(res_coordenadas);
            res_edificio = new Edificio(lista_respuestas);
        }
        // Retorna el edificio resultante de la combinación.
        return res_edificio;
    }
}
