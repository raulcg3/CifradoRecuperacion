# Cifrado DES
## Introducción
### ¿Qué es y qué hace DES?
DES es un algoritmo de encriptación inventado por IBM en 1970 bajo el nombre "Lucifer". 
En 1977 fue adoptado por el NIST (National Institute of Standards and Technology) y por la NSA (National Security Agency) como su estándar nacional.
En 1981 este algoritmo fue adoptado también por ANSI. Cifra un bloque con tamaño de 64 bits es decir 8 bytes. Para cada bloque de 64 bits de entrada se obtienen 64 bits a la salida, es decir, el tamaño del fichero resultante no crece ni decrece.
### ¿Cómo funciona su algoritmo?
El algoritmo aplica sustituciones (cambios de unos valores por otros) y permutaciones (cambios en la posición que ocupan los bits). 
El proceso de encriptación se realiza en 16 rounds, y cada round lleva a cabo la misma serie de sustituciones y permutaciones.
### ¿Cómo de seguro es?
El punto débil de DES no está en el algoritmo, sino en el tamaño de la clave que da lugar a demasiadas pocas claves posibles y 
es susceptible de sufrir un ataque. Actualmente un algoritmo de clave secreta se considera fuerte si tiene una clave de 128 bits o mayor. 
## Explicación del proyceto
El proyecto cuenta con tres clases:
### - DESSimpleManager.java
Esta clase contiene los métodos necesarios para el cifrado y descifrado.
```java
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
```
### - CifradorDESSimple.java
Esta clase contiene un main que cifra un mensaje.
```java
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
```
### - DescifradorDESSimple.java
Esta clase contiene un main que descifra un mensaje.
```java
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
```
