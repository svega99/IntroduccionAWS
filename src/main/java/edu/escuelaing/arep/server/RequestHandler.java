/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arep.server;


import static edu.escuelaing.arep.server.HttpServer.clientSocket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author santiago.vega-r
 */
public class RequestHandler extends Thread{
    
    PrintWriter out;
    BufferedReader in;
    public RequestHandler(Socket clientSocket) throws IOException {
        out = new PrintWriter(
            clientSocket.getOutputStream(), true);
        in = new BufferedReader(
            new InputStreamReader(clientSocket.getInputStream()));
    }
    
    
    @Override
    public void run(){
        try {
            startServer();
        } catch (IOException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * 
     * @throws IOException 
     */
    public void startServer() throws IOException{
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
    }
    
     /**
     * Devuelve en la pagina la solicitud
     *
     * @param req archivo solicitado
     * @throws IOException
     */
  public void returnRequest(String req) throws IOException {
	  
	  
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
	      else {
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
}
