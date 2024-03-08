/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cifradorecuperacion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.Key;

/**
 *
 * @author raulc
 */
public class CifradorDESSimple {
    public static void main(String[] args) {
        //Declaramos el nombre del fichero donde se va a guardar el mensaje y la contraseña que vamos a utilizar.
        final String NOMBRE_FICHERO = "mensaje_cifrado_des.txt";
        final String CONTRASENA = "aprobado";
        
        try{
            //Lllamamos al método para obtener la clave
            Key clave = DESSimpleManager.obtenerClave(CONTRASENA);
            //Creamos un buffer para leer el mensaje
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Mensaje a cifrar: ");
            String textoEnClaro = br.readLine();
            // Ciframos el mensaje utilizando la clave que hemos obtenido
            String textoCifrado = DESSimpleManager.cifrar (textoEnClaro,clave);
            // Escribimos el mensaje cifrado en el archivo
            PrintWriter pw = new PrintWriter(NOMBRE_FICHERO) ;
            pw.write(textoCifrado);
            pw.close();
            
            System.out.println("Mensaje Cifrado: " + textoCifrado);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
