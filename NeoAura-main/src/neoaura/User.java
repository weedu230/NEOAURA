package neoaura;

public class User {
    private static String email;
    private static String fullName;

    public static void setUser(String firstName, String lastName, String userEmail) {
        email = userEmail;
        fullName = firstName + " " + lastName;
    }

    public static String getEmail() {
        return email;
    }

    public static String getFullName() {
        return fullName;
    }

    public static void clearSession() {
        email = null;
        fullName = null;
    }
}
