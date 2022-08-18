package com.purebasicv2.app.utils;

/**
 * Created by forhad.naim on 3/4/2015.
 */
public class EmailCheck {

    public static String emailPattern = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";

    public static boolean emailCheck(String email) {
        if (email.matches(emailPattern) && email.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

}
