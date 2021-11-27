package tarea2;

import java.util.Arrays;
import java.util.ArrayList;
import java.lang.Math;

/**
 *
 * @author Sheibu
 */

public class Tarea2 {

    /**
     * @param args the command line arguments
     */
    
    static private SRFLP problema;
    static private AlgoritmoGenetico algoritmo;
    
    public static void main(String[] args) {
        String nombreProblema;
        ArrayList<int[]> soluciones = new ArrayList<>();
        
        /**
         * Numero de pruebas que se desean realizar
         */
        int nPruebas = 10;
        
        double[] valorSol = new double[nPruebas];
        
        /**
         * Se instancia el problema a resolver
         */
        nombreProblema = "S8";
        //nombreProblema = "SRFLP_Rubio_AnKeVa_500_03.txt";
        //nombreProblema = "SRFLP2_N_110_L_5_14_F_0_100.txt";
        //nombreProblema = "QAP_sko56_04_n";
        //nombreProblema = "test.txt";
        
        problema = new SRFLP(nombreProblema);
        algoritmo = new AlgoritmoGenetico(problema, 10, 5);
        
        System.out.println("Problema actual: "+nombreProblema+"\n");
        System.out.println("Iniciando "+nPruebas+" pruebas:");
        System.out.println("======================================================================================================");
        
        for(int i=0;i<nPruebas;i++){
            System.out.println("======================================================================================================");
            System.out.println("Prueba n°"+(i+1)+":");
            
            soluciones.add(algoritmo.runAlgoritmo());
            valorSol[i] = problema.getObjectiveFunction(soluciones.get(i));
            
            System.out.println("Mejor solucion final obtenida: ");
            System.out.println(Arrays.toString(soluciones.get(i)));
            System.out.println("Valor mejor solucion obtenida: ");
            System.out.println(problema.getObjectiveFunction(soluciones.get(i)));
            System.out.println("======================================================================================================");
        }
        
        System.out.println("Las soluciones obtenidas en las "+nPruebas+" iteraciones son:");
        for(int i=0;i<nPruebas;i++){
            System.out.println(Arrays.toString(soluciones.get(i))+" Valor = "+problema.getObjectiveFunction(soluciones.get(i)));
        }
        System.out.println();
        
        /**
         * Promedio
         */
        double promedio = 0.0;
        for(int i=0;i<nPruebas;i++){
            promedio = promedio + valorSol[i];
        }
        promedio = promedio/nPruebas;
        
        System.out.println("Promedio: "+promedio);
        
        /**
         * Desviacion estandar
         */
        double desvStd = 0.0;
        for(int i=0;i<nPruebas;i++){
            desvStd = desvStd + Math.pow(valorSol[i]-promedio,2);
        }
        desvStd = desvStd/(nPruebas-1);
        desvStd = Math.sqrt(desvStd);
        System.out.println("Desviacion estandar: "+desvStd);
        
    }
}
