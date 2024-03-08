/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cifradorecuperacion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.Key;

/**
 *
 * @author raulc
 */
public class DescifradorDESSimple {
    public static void main(String[] args) {
        //Declaramos el nombre del fichero donde se va a guardar el mensaje y la contraseña que vamos a utilizar.
        final String NOMBRE_FICHERO = "mensaje_cifrado_des.txt";
        final String PASSWORD = "aprobado";
        
        try { 
            // Nos creamos un objeto de tipo file con el nombre del fichero
            File file = new File (NOMBRE_FICHERO) ;
            // Obtenemos la clave de descifrado a partir de la contraseña
            Key clave = DESSimpleManager.obtenerClave(PASSWORD) ;
            //Leemos el mensaje cifrado con un buffer
            BufferedReader br = new BufferedReader (new FileReader(file));
            String textoCifrado = br.readLine();
            // Desciframos el mensaje utilizando la clave obtenida
            String textoEnClaro = DESSimpleManager.descifrar(textoCifrado,clave);
            br.close();
            
            System.out.println ("El texto cifrado es: " + textoCifrado);
            System.out.println ("El texto descifrado es: " + textoEnClaro);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
