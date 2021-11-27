package tarea2;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Sheibu
 * Clase creada para generar poblaciones aleatorias
 */
public class Poblacion {
    /**
     * Genera una poblacion aleatoria de tamaño 'sizePoblacion', con individuos
     * de tamaño 'sizeIndividuo'
     * @param sizePoblacion Tamaño de la poblacion deseada
     * @param sizeIndividuo Tamaño de los individuos de la poblacion
     * @return Retorna una poblacion aleatoria
     */
    
    public static ArrayList<int[]> generarPoblacionRandom(int sizePoblacion, int sizeIndividuo){
        ArrayList<int[]> poblacion = new ArrayList<>();
        for(int i=0;i<sizePoblacion;i++){
            poblacion.add(Poblacion.crearIndividuoRandom(sizeIndividuo));
        }
        return poblacion;
    }
    
    /**
     * Crea un individuo aleatorio de tamaño 'size'
     * @param size Tamaño del individuo
     * @return 
     */
    
    public static int[] crearIndividuoRandom(int size){
        int[] poblacion = new int[size];
        ArrayList<Integer> auxPoblacion = new ArrayList<>();
        for(int i=0;i<size;i++){
            auxPoblacion.add(i);
        }
        Collections.shuffle(auxPoblacion);
        for(int i=0;i<size;i++){
            poblacion[i] = auxPoblacion.get(i);
        }
        return poblacion;
    }
    
}

