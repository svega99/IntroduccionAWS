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
/**
 * HTTP Server que recibe archivos
 * 
 * @author santiago.vega-r
 */
public class HttpServer {
	static ServerSocket serverSocket = null;
	
	static Socket clientSocket = null;
	
	static PrintWriter out;
	static BufferedReader in;
	
	
  public static void main(String[] args) throws IOException {
	  
	  
	   serverSocket = null;
	   try { 
	      serverSocket = new ServerSocket(getPort());
	   } catch (IOException e) {
	      System.err.println("Could not listen on port: 35000.");
	      System.exit(1);
	   }
	   while(true) {
	   
	
		   
		   try {
		       System.out.println("Listo para recibir ...");
		       clientSocket = serverSocket.accept();
		   } catch (IOException e) {
		       System.err.println("Accept failed.");
		       System.exit(1);
		   }
		   out = new PrintWriter(
		                         clientSocket.getOutputStream(), true);
		   in = new BufferedReader(
		                         new InputStreamReader(clientSocket.getInputStream()));
		   String inputLine, outputLine;
		   
		   StringBuilder stringBuilder = new StringBuilder();
		   
		   Pattern pattern = Pattern.compile("GET /([^\\s]+)");
	       Matcher matcher = null;
		   
		   while ((inputLine = in.readLine()) != null) {
		      System.out.println("Recibí: " + inputLine);
		      stringBuilder.append(inputLine);
		      if (!in.ready()) {
		    	  matcher = pattern.matcher(stringBuilder.toString());
	              if (matcher.find()) {
	                  String req = matcher.group().substring(5);
	                  System.out.println("VALUE: " + req);
	                  returnRequest(req);
	              }
		    	  
		    	  break; }
		   }
		  
		    out.close(); 
		    in.close(); 
		    clientSocket.close(); 
		    //serverSocket.close();
	   }
  }
  
  /**
     * Devuelve en la pagina la solicitud
     *
     * @param req archivo solicitado
     * @throws IOException
     */
  public static void returnRequest(String req) throws IOException {
	  
	  
	  String path = "src/main/resources/";
      String ext = FilenameUtils.getExtension(req);
      if (ext.equals("js")) {
    	  path=path+"js/";
    	  
      }else if (ext.equals("png") || ext.equals("jpg")) {
    	  path=path+"img/";
      }
      
      System.out.println(path+req);
      File file = new File(path+req);
      
      if (file.exists() && !file.isDirectory()) {
	      if (ext.equals("png") || ext.equals("jpg")) {
	    	  	
	    	  	
				FileInputStream fis = new FileInputStream(file);
				byte[] data = new byte[(int) file.length()];
				fis.read(data);
				fis.close();
	                      
	             // Cabeceras con la info de la imágen
				DataOutputStream binaryOut = new DataOutputStream(clientSocket.getOutputStream());
				binaryOut.writeBytes("HTTP/1.0 200 OK\r\n");
				binaryOut.writeBytes("Content-Type: image/"+ext+"\r\n");
				binaryOut.writeBytes("Content-Length: " + data.length);
				binaryOut.writeBytes("\r\n\r\n");
				binaryOut.write(data);
	
				binaryOut.close();
	    	  
	      }
	      else {/*
	    	  DataOutputStream binaryOut = new DataOutputStream(clientSocket.getOutputStream());
				binaryOut.writeBytes("HTTP/1.0 200 OK\r\n");
				binaryOut.writeBytes("Content-Type: text/html");
				binaryOut.writeBytes("\r\n\r\n");
				
				binaryOut.close();*/
				  out.println("HTTP/1.1 200 \r\nContent-Type: text/html\r\n\r\n");
		    	  BufferedReader br = new BufferedReader(new FileReader(file));
	
	              StringBuilder stringBuilder = new StringBuilder();
	              String st;
	              while ((st = br.readLine()) != null) {
	                  stringBuilder.append(st);
	              }
	              out.println(stringBuilder.toString());
	              br.close();
	      }
      }
      else {
    	  out.println("HTTP/1.1 404 \r\n\r\n<html><body><h1>ERROR 404: NOT FOUND</h1></body></html>");
    	  
      }
	  
  }
  
  static int getPort() {
      if (System.getenv("PORT") != null) {
          return Integer.parseInt(System.getenv("PORT"));
      }        
         
      return 4567; //returns default port if heroku-port isn't set(i.e. on localhost)    }
  }
  
  
  
  
}