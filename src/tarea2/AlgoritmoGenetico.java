package tarea2;

import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;
/**
 *
 * @author Sheibu
 */

public class AlgoritmoGenetico {
    
    ArrayList<int[]> poblacion;
    SRFLP problema;
    int[] mejorSolucion;
    int mu;
    int lambda;
    int numPadres;
    double prob_mut;
    
    public AlgoritmoGenetico(SRFLP problema, int mu, int lambda){
        this.problema = problema;
        //this.poblacion = poblaciones;
        this.mu = mu;
        this.lambda = lambda;
        this.numPadres = 2;
        this.prob_mut = 0.2;
    }
    
    /**
     * Ejecuta el algoritmo con los datos ingresados al crear la clase
     * @return Retorna el arreglo con la mejor solucion obtenida
     */
    public int[] runAlgoritmo(){
        int intentos;
        System.out.println("Generando poblacion aleatoria...");
        poblacion = Poblacion.generarPoblacionRandom(mu, problema.getProblemSize());
        System.out.println("Obteniendo mejor solucion de la poblacion inicial...");
        mejorSolucion = mejorSolucion(poblacion);
        System.out.println("Mejor solucion inicial obtenida: ");
        System.out.println(Arrays.toString(mejorSolucion));
        System.out.print("Valor solucion inicial obtenida: ");
        System.out.println(problema.getObjectiveFunction(mejorSolucion));
        ArrayList<int[]> padres;
        ArrayList<int[]> hijos = new ArrayList<>();
        System.out.println("Iniciando algoritmo...\n");
        intentos = 0;
        while(intentos<100){
            ArrayList<int[]> nuevaPoblacion = new ArrayList<>();
            while(nuevaPoblacion.size()<mu){
                padres = seleccionarPadres(poblacion);
                hijos = cruzarIndividuos(padres.get(0),padres.get(1));
                for(int i=0;i<hijos.size();i++){
                    if(Math.random()<prob_mut){
                        mutar(hijos.get(i));
                    }
                }
                nuevaPoblacion.addAll(hijos);
            }
            poblacion = seleccionarPoblacion(poblacion,nuevaPoblacion);
            if(problema.getObjectiveFunction(mejorSolucion)==problema.getObjectiveFunction(mejorSolucion(hijos, poblacion))){
                intentos++;
            }else{
                intentos = 0;
            }
            mejorSolucion = mejorSolucion(hijos, poblacion);
            //System.out.println(problema.getObjectiveFunction(mejorSolucion));
            
        }
        
        return mejorSolucion;
    }
    
    /**
     * Selecciona dos padres desde la poblacion ingresada
     * @param poblacion Es la poblacion desde la cual se desea obtener los padres
     * @return Retorna un ArrayList con los padres seleccionados
     */
    public ArrayList<int[]> seleccionarPadres(ArrayList<int[]> poblacion){
        double[] auxProb = new double[poblacion.size()];
        double total = 0.0;
        ArrayList<int[]> padres = new ArrayList<>();
        for(int i=0;i<poblacion.size();i++){
            total += problema.getObjectiveFunction(poblacion.get(i));
            if(i==0){
                auxProb[i]=problema.getObjectiveFunction(poblacion.get(i));
                continue;
            }
            auxProb[i]=auxProb[i-1]+problema.getObjectiveFunction(poblacion.get(i));
        }
        for(int i=0;i<auxProb.length;i++){
            auxProb[i]=auxProb[i]/total;
        }
        for(int cont=0;cont<numPadres;){
            double num = Math.random();
            int i=0;
            while(i==0||auxProb[i]<=num){
                i++;
                if(i==auxProb.length){
                    break;
                }
            }
            if(!padres.contains(poblacion.get(i-1))){
                padres.add(poblacion.get(i-1));
                cont++;
            }
        }
        return padres;
    }
    
    /**
     * Cruza dos padres para obtener dos individuos nuevos
     * @param p1 Es el primer padre
     * @param p2 Es el segundo padre
     * @return Retorna un ArrayList con los dos hijos generados
     */
    public ArrayList<int[]> cruzarIndividuos(int[] p1, int[] p2){
        int[] h1 = new int[p1.length];
        int[] h2 = new int[p1.length];
        ArrayList<int[]> hijos = new ArrayList<>();
        Arrays.fill(h1, -1);
        Arrays.fill(h2, -1);
        int lim1, lim2;
        
        lim2 = -1;
        lim1 = (int) (Math.random() * p1.length);
        while(lim1==p1.length-1){
            lim1 = (int) (Math.random() * p1.length);
        }
        while(lim1>=lim2){
            lim2 = (int) (Math.random() * p1.length);
        }
        for(int i=lim1;i<=lim2;i++){
            h1[i]=p1[i];
            h2[i]=p2[i];
        }
        int j=0;
        for(int i=0;i<h1.length;i++){
            if(j!=h1.length){
                if(h1[j]!=-1){
                    while(h1[j]!=-1){
                        j++;
                        if(j==h1.length){
                            j--;
                            break;
                        }
                    }
                }
            }
            if(!contains(h1,p2[i])){
                h1[j]=p2[i];
                j++;
            }
        }
        j=0;
        for(int i=0;i<h2.length;i++){
            if(j!=h2.length){
                if(h2[j]!=-1){
                    while(h2[j]!=-1){
                        j++;
                        if(j==h2.length){
                            j--;
                            break;
                        }
                    }
                }
            }
            if(!contains(h2,p1[i])){
                h2[j]=p1[i];
                j++;
            }
        }
        hijos.add(h1);
        hijos.add(h2);
        return hijos;
    }
    
    /**
     * Aplica una mutacion swap al individuo ingresado
     * @param individuo Es el individuo al que se desea aplicar la mutacion
     * @return Retorna un nuevo array con el individuo mutado
     */
    public int[] mutar(int[] individuo){
        int[] newIndividuo = individuo.clone();
        int lim1 = (int) (Math.random() * newIndividuo.length);
        int lim2 = lim1;
        while(lim1==lim2){
            lim2 = (int) (Math.random() * newIndividuo.length);
        }
        int aux;
        aux = newIndividuo[lim1];
        newIndividuo[lim1] = newIndividuo[lim2];
        newIndividuo[lim2] = aux;
        
        return newIndividuo;
    }
    
    /**
     * Selecciona una nueva poblacion de tamaño 'mu' utilizando una seleccion elitista
     * a partir de dos conjuntos de individuos
     * @param poblacion Es la poblacion original, de la cual fueron seleccionados los padres
     * @param hijos Son los hijos generados por los padres obtenidos de 'poblacion'
     * @return Retorna una nueva poblacion de tamaño 'mu'
     */
    public ArrayList<int[]> seleccionarPoblacion(ArrayList<int[]> poblacion, ArrayList<int[]> hijos){
        ArrayList<int[]> preseleccion = new ArrayList<>();
        ArrayList<int[]> nuevaPoblacion = new ArrayList<>();
        int[] auxSolucion;
        preseleccion.addAll(poblacion);
        preseleccion.addAll(hijos);
        
        for(int i=0;i<mu;i++){
            auxSolucion = mejorSolucion(preseleccion);
            preseleccion.remove(auxSolucion);
            nuevaPoblacion.add(auxSolucion);
        }
        
        return nuevaPoblacion;
    }
    
    /**
     * Obtiene el individuo con la mejor solucion de la poblacion
     * @param poblacion Es la poblacion en la cual se desea buscar la mejor solucion
     * @return Retorna el individuo con la mejor solucion
     */
    public int[] mejorSolucion(ArrayList<int[]> poblacion){
        int[] mejorSolucionAux = null;
        for(int i=0;i<poblacion.size();i++){
            if(mejorSolucionAux == null){
                mejorSolucionAux = poblacion.get(i);
                continue;
            }
            if(problema.getObjectiveFunction(poblacion.get(i))<problema.getObjectiveFunction(mejorSolucionAux)){
                mejorSolucionAux = poblacion.get(i);
            }
        }
        return mejorSolucionAux;
    }
    
    /**
     * Obtiene la mejor solucion entre dos poblaciones
     * @param poblacion Es la poblacion de la cual se obtuvieron los padres
     * @param poblacionHijos Es la poblacion hija obtenida de la poblacion anterior
     * @return Retorna el individuo con la mejor solucion
     */
    public int[] mejorSolucion(ArrayList<int[]> poblacion, ArrayList<int[]> poblacionHijos){
        ArrayList<int[]> auxArray = new ArrayList<>();
        auxArray.addAll(poblacion);
        auxArray.addAll(poblacionHijos);
        
        return mejorSolucion(auxArray);
    }
    
    /**
     * Verifica si un array contiene o no un valor
     * @param array Es el array en el que se desea verificar la existencia del valor
     * @param key Es el valor que se desea saber si pertence al array
     * @return Retorna 'true' si 'array' contiene a 'key', en caso contrario retorna 'false'
     * 
     * Esta función fue tomada de:
     * https://stackoverflow.com/questions/12020361/java-simplified-check-if-int-array-contains-int
     */
    public static boolean contains(final int[] array, final int key){
        return Arrays.stream(array).anyMatch(n->n==key);
    }
    
    
}
