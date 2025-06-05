package neoaura;

import java.sql.*;
import java.util.*;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Database {
    private static final String DB_URL = "YOURDATABASELINK";
    private static final String DB_USER = "YOURDATABASENAME";
    private static final String DB_PASSWORD = "YOURDATABASEPASSWORD";
    public Database() {
        createPollTable();
    }

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public void createPollTable() {
        String sql = "CREATE TABLE IF NOT EXISTS polls (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "question TEXT NOT NULL," +
                "option1 TEXT NOT NULL," +
                "option2 TEXT NOT NULL," +
                "option3 TEXT NOT NULL," +
                "option4 TEXT NOT NULL," +
                "correctOption INT NOT NULL" +
                ")";
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addPoll(String question, String o1, String o2, String o3, String o4, int correctOption) {
        String sql = "INSERT INTO polls(question, option1, option2, option3, option4, correctOption) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, question);
            pstmt.setString(2, o1);
            pstmt.setString(3, o2);
            pstmt.setString(4, o3);
            pstmt.setString(5, o4);
            pstmt.setInt(6, correctOption);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePoll(int id) {
        String sql = "DELETE FROM polls WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Poll> getAllPolls() {
        List<Poll> polls = new ArrayList<>();
        String sql = "SELECT * FROM polls";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String question = rs.getString("question");
                List<String> options = new ArrayList<>();
                options.add(rs.getString("option1"));
                options.add(rs.getString("option2"));
                options.add(rs.getString("option3"));
                options.add(rs.getString("option4"));

                int correctIndex = rs.getInt("correctOption") - 1;

                Poll poll = new Poll(id, question, options, correctIndex);
                polls.add(poll);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return polls;
    }
    public static boolean signupUser(String fname, String lname, String email, String mobile, String password) {
        try (Connection conn = connect()) {
            String hashed = hashPassword(password);
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users (firstname, lastname, email, mobile, password) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, fname);
            ps.setString(2, lname);
            ps.setString(3, email);
            ps.setString(4, mobile);
            ps.setString(5, hashed);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean loginUser(String email, String password) {
        try (Connection conn = connect()) {
            String query = "SELECT password, status, firstname, lastname FROM users WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                String status = rs.getString("status");

                if (!status.equalsIgnoreCase("Active")) {
                    System.out.println("User is blocked or inactive.");
                    return false;
                }

                if (storedHash.equals(hashPassword(password))) {
                    String firstname = rs.getString("firstname");
                    String lastname = rs.getString("lastname");
                    User.setUser(firstname, lastname, email);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    public static boolean loginAdmin(String email, String password) {
        try (Connection conn = connect()) {
            PreparedStatement ps = conn.prepareStatement("SELECT password FROM admin WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password");
                return storedHash.equals(hashPassword(password));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkUserForOTP(String email, String mobile) {
        try (Connection conn = connect()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE email = ? AND mobile = ?");
            ps.setString(1, email);
            ps.setString(2, mobile);
            return ps.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updatePassword(String email, String newPassword) {
        try (Connection conn = connect()) {
            String hashed = hashPassword(newPassword);
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET password = ? WHERE email = ?");
            ps.setString(1, hashed);
            ps.setString(2, email);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String generateOTP() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public static boolean sendOTP(String to, String otp) {
        final String from = "icegaming377@gmail.com";
        final String pass = "ojilyakrdmgrsyot";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, pass);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Your OTP Code");
            message.setText("Your OTP is: " + otp);
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
    public static boolean isEmailExists(String email) {
        try (Connection conn = connect()) {
            PreparedStatement ps = conn.prepareStatement("SELECT email FROM users WHERE email = ?");
            ps.setString(1, email);
            return ps.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean isMobileExists(String mobile) {
        try (Connection conn = connect()) {
            PreparedStatement ps = conn.prepareStatement("SELECT mobile FROM users WHERE mobile = ?");
            ps.setString(1, mobile);
            return ps.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public static boolean isNameExists(String fname, String lname) {
        try (Connection conn = connect()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE firstname = ? AND lastname = ?");
            ps.setString(1, fname);
            ps.setString(2, lname);
            return ps.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }

    }
    public List<String[]> getAllUsers() {
        List<String[]> users = new ArrayList<>();
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT email, status FROM users")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(new String[]{rs.getString("email"), rs.getString("status")});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public void updateUserStatus(String email, String newStatus) {
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("UPDATE users SET status = ? WHERE email = ?")) {
            stmt.setString(1, newStatus);
            stmt.setString(2, email);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getTotalUsers() {
        int count = 0;
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM users");
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getActiveUsers() {
        int count = 0;
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE status = 'Active'");
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getBlockedUsers() {
        int count = 0;
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE status = 'Inactive'");
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getTotalPolls() {
        int count = 0;
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM polls");
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    public int getTotalChats() {
        int count = 0;
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM chats");
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

}
