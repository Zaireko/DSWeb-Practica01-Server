/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dsweb.practica01.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asus
 */
public class ClienteSocket extends Thread {
    private Socket cliente;
    InputStreamReader isr = null;
    BufferedReader reader = null;
    OutputStreamWriter osw = null;
    BufferedWriter writer = null;
    public ClienteSocket (Socket cliente){
        try {
            this.cliente = cliente;
            isr = new InputStreamReader(cliente.getInputStream());
            reader = new BufferedReader(isr);
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        } 
//        finally {
//            try {
//                isr.close();
//            } catch( IOException ex) {
//                Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        
        
        try {
            osw = new OutputStreamWriter(cliente.getOutputStream());
            writer = new BufferedWriter(osw);
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        } 
//        finally {
//            try {
//                osw.close();
//            } catch (IOException ex) {
//                Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        
    }
    @Override
    public void run () {
        while(true) {
            try {

                String operacion = reader.readLine();
                System.out.println("Operacion--> " + operacion);
                String resultado = "";
                
                
                int indexSuma = operacion.indexOf("+");
                int indexResta = operacion.indexOf("-");
                int indexMulti = operacion.indexOf("*");
                int indexDivision = operacion.indexOf("/");
                
                if (indexSuma > 0) {
                    String a = operacion.substring(0, indexSuma);
                    String b = operacion.substring(indexSuma +1, operacion.length());
                    
                    int cVal = Integer.parseInt(a) + Integer.parseInt(b);
                    String c = String.valueOf(cVal);
                    resultado = "a: "+ a + " mas" + " b: " + b + " Resultado: " + c;
                    
                } else if (indexResta > 0) {
                    String a = operacion.substring(0, indexResta);
                    String b = operacion.substring(indexResta +1, operacion.length());
                    
                    int cVal = Integer.parseInt(a) - Integer.parseInt(b);
                    String c = String.valueOf(cVal);
                    resultado = "a: "+ a + " menos" + " b: " + b + " Resultado: " + c;
                } else if (indexMulti > 0) {
                    String a = operacion.substring(0, indexMulti);
                    String b = operacion.substring(indexMulti +1, operacion.length());
                    
                    int cVal = Integer.parseInt(a) * Integer.parseInt(b);
                    String c = String.valueOf(cVal);
                    resultado = "a: "+ a + " por" + " b: " + b + " Resultado: " + c;
                } else if (indexDivision > 0) {
                    String a = operacion.substring(0, indexDivision);
                    String b = operacion.substring(indexDivision +1, operacion.length());
                    
                    int cVal = Integer.parseInt(a) / Integer.parseInt(b);
                    String c = String.valueOf(cVal);
                    resultado = "a: "+ a + " entre" + " b: " + b + " Resultado: " + c;
                } else {
                    resultado = "Error . . .";
                }
                
                writer.write(resultado);
                writer.flush();
                
                //System.out.println("Hola mundo...");
                //sleep(1000);
            } catch (IOException ex) {
                Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
