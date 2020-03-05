package edu.escuelaing.arep.awsclient;
import java.io.*; 
import java.net.*; 
/**
 *
 * @author santiago.vega-r
 */

public class URLReader extends Thread{ 
    
    
  private static URL url;
  
  
  private static int numHilos = 20;
  
  String contenido;
  
  public URLReader(){
  
  }
  
  @Override
  public void run(){
       try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(url.openStream()))) { 
            contenido = "";
            String inputLine = null; 
            while ((inputLine = reader.readLine()) != null) { 
                  contenido=contenido+inputLine;
             } 
       } catch (IOException x) { 
               System.err.println(x); 
       }
  }
  
  public static void main(String[] args) throws Exception { 
      url = new URL(args[0]); 
      numHilos = Integer.parseInt(args[1]); 
      URLReader[] clientes = new URLReader[numHilos];
      
      
        for (int i = 0; i < numHilos; i++) {
            clientes[i] = new URLReader();
        }
        long startTime = System.nanoTime();
       for (int i = 0; i < numHilos; i++) {
            clientes[i].start();
        }
      
       
        for (int i = 0; i < numHilos; i++) {
            clientes[i].join();
            System.out.println("Contenido cliente #" + (i+1) + "\n" + clientes[i].contenido);
            
        }
       long elapsedTime = System.nanoTime() - startTime;
        System.out.println("Tiempo total: "+ (double) elapsedTime/1000000000);

    }
}

   
