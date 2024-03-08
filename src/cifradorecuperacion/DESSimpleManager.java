/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cifradorecuperacion;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author raulc
 */
//Clase con los métodos para poder cifrar y descifrar mensajes con DES.
public class DESSimpleManager {
    /**
     * Obtiene una clave DES a partir de una contraseña.
     * La longitud de la contraseña debe ser de 8 bytes, que es lo permitido por DES.
     *
     * @param password Contraseña para generar la clave.
     * @return La clave generada.
     */
    public static Key obtenerClave(String password) {
        // Convertimos la contraseña a bytes.
        byte[] keyData = password.getBytes();
        // Truncamos la clave si es mayor a 8 bytes para que pueda ser utilizada.
        byte[] truncatedKey = new byte[8];
        System.arraycopy(keyData, 0, truncatedKey, 0, Math.min(keyData.length, 8));
        // Creamos una clave DES a partir de la clave truncada.
        Key clave = new SecretKeySpec(truncatedKey, "DES");
        return clave;
    }
    /**
     * Cifra un texto en claro utilizando una clave DES.
     *
     * @param textoEnClaro Texto a cifrar.
     * @param key Clave utilizada para cifrar.
     * @return Texto cifrado en hexadecimal.
     * @throws Exception Por si ocurre un error durante el cifrado.
     */
    public static String cifrar(String textoEnClaro, Key key) throws Exception {
        // Obtenemos una instancia del cifrador DES.
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        // Inicializamos el cifrador en modo de cifrado con la clave proporcionada
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // Ciframos el texto en bytes
        byte[] cipherText = cipher.doFinal(textoEnClaro.getBytes());
        //Convertimos lo bytes a hexadecimal
        return bytesToHex(cipherText);
    }
    /**
     * Descifra un texto cifrado utilizando una clave DES.
     *
     * @param textoCifrado Texto cifrado en hexadecimal.
     * @param key Clave utilizada para descifrar.
     * @return Texto descifrado.
     * @throws Exception Por si ocurre un error durante el descifrado.
     */
    public static String descifrar(String textoCifrado, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] cipherText = hexToBytes(textoCifrado);
        byte[] plainText = cipher.doFinal(cipherText);
        return new String(plainText);
    }
    // Método auxiliar para convertir un array de bytes a una cadena hexadecimal
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02X", b));
        }
        return result.toString();
    }
    // Método auxiliar para convertir una cadena hexadecimal a un array de bytes
    private static byte[] hexToBytes(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
}
