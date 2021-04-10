package lecture07.serialization;

import java.io.*;

public class Deserialization {
    public static void main(String[] args) {
        File file = new File("./user.ser");
        if (file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

                User user = (User) objectInputStream.readObject();
                System.out.println("Name: " + user.getName());
                System.out.println("Mail: " + user.getEmail());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("user.ser does not exist");
        }
    }
}
