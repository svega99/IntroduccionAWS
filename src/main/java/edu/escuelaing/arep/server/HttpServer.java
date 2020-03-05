/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arep.server;

import org.apache.commons.io.FilenameUtils;

import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * HTTP Server que recibe archivos
 * 
 * @author santiago.vega-r
 */
public class HttpServer {
	static ServerSocket serverSocket = null;
	private static final int MAX_THREADS = 30;
	static Socket clientSocket = null;
	
	
        private static ExecutorService ex;

	
	
  public static void main(String[] args) throws IOException {
	  boolean running=true;
	  
	   serverSocket = null;
	   try { 
	      serverSocket = new ServerSocket(getPort());
	   } catch (IOException e) {
	      System.err.println("Could not listen on port: 35000.");
	      System.exit(1);
	   }
           
           ex = Executors.newFixedThreadPool(MAX_THREADS);
	   while(running) {
		   try {
		       System.out.println("Listo para recibir ...");
		       clientSocket = serverSocket.accept();
		   } catch (IOException e) {
		       System.err.println("Accept failed.");
		       System.exit(1);
		   }
                   RequestHandler rh = new RequestHandler(clientSocket);
                    ex.execute(rh);
                 
	   }
           ex.shutdown();
            serverSocket.close();
           
            
  }
  
 
  
  static int getPort() {
      if (System.getenv("PORT") != null) {
          return Integer.parseInt(System.getenv("PORT"));
      }        
         
      return 4567; //returns default port if heroku-port isn't set(i.e. on localhost)    }
  }
  
  
  
  
}