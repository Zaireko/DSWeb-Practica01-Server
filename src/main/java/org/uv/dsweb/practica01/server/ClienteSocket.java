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
    private BufferedReader reader = null;
    private BufferedWriter writer = null;

    public ClienteSocket(Socket cliente) {
        this.cliente = cliente;
        InputStreamReader isr = null;

        try {
            isr = new InputStreamReader(this.cliente.getInputStream());
            reader = new BufferedReader(isr);
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

        OutputStreamWriter osw = null;

        try {
            osw = new OutputStreamWriter(cliente.getOutputStream());
            writer = new BufferedWriter(osw);
        } catch (IOException ex) {
            Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    boolean correr = true;

    @Override
    public void run() {
        while (correr) {
            try {
                String operacion = reader.readLine();
                System.out.println("Operación recibida: " + operacion);

                int indexSuma = operacion.indexOf("+");
                int indexResta = operacion.indexOf("-");
                int indexMulti = operacion.indexOf("*");
                int indexDivision = operacion.indexOf("/");

                String resultado = "";
                if (indexSuma != -1) {
                    String a = operacion.substring(0, indexSuma);
                    String b = operacion.substring(indexSuma + 1, operacion.length());

                    int cVal = Integer.parseInt(a) + Integer.parseInt(b);
                    resultado = "a: " + a + " más " + "b: " + b + " = " + cVal;

                } else if (indexResta != -1) {
                    String a = operacion.substring(0, indexResta);
                    String b = operacion.substring(indexResta + 1, operacion.length());

                    int cVal = Integer.parseInt(a) - Integer.parseInt(b);
                    resultado = "a: " + a + " menos " + "b: " + b + " = " + cVal;

                } else if (indexMulti != -1) {
                    String a = operacion.substring(0, indexMulti);
                    String b = operacion.substring(indexMulti + 1, operacion.length());

                    int cVal = Integer.parseInt(a) * Integer.parseInt(b);
                    resultado = "a: " + a + " por " + "b: " + b + " = " + cVal;

                } else if (indexDivision != -1) {
                    String a = operacion.substring(0, indexDivision);
                    String b = operacion.substring(indexDivision + 1, operacion.length());

                    int cVal = Integer.parseInt(a) / Integer.parseInt(b);
                    resultado = "a: " + a + " entre " + "b: " + b + " = " + cVal;
                } else {
                    resultado = "Error en la operación...";
                }

                // Mostrar operación y resultado en el servidor
                System.out.println("Resultado: " + resultado);

                // Enviar el resultado al cliente
                writer.write(resultado + "\n");
                writer.flush();

            } catch (IOException ex) {
                correr = false;
                Logger.getLogger(ClienteSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
