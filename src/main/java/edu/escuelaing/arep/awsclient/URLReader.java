package edu.escuelaing.arep.awsclient;
import java.io.*; 
import java.net.*; 
/**
 *
 * @author santiago.vega-r
 */

public class URLReader extends Thread{ 
  public static void main(String[] args) throws Exception { 
      URL url = new URL(args[0]); 
      URLReader[] clientes = new URLReader[20];
      
      try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(url.openStream()))) { 
            String inputLine = null; 
            while ((inputLine = reader.readLine()) != null) { 
                  System.out.println(inputLine); 
             } 
       } catch (IOException x) { 
               System.err.println(x); 
       } 
    } 
}