package com.product.automatization.Utils;

public class EmailUtilities {

    public static void formatEmail(String email){
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if(email.matches(regex)){
            System.out.println("El correo electrónico es válido.");
        } else {
            System.out.println("El correo electrónico no es válido.");
        }
    }

    public static String sendContextBienvenidaEmailString(String tipoContext){
        // Implementación para enviar correo de bienvenida
        // Utilizar el contexto para personalizar el mensaje del correo
        if(tipoContext.equals("BIENVENIDA")){
            return "¡Bienvenido a nuestro servicio!";
        }
        return null;
    }
}