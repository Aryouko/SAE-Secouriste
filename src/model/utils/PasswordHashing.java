package model.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashing {

    private static final int COST = 12; // you can tweak this

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(COST));
    }

    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
