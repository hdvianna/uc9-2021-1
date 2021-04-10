package lecture07.serialization;

import java.io.*;

public class Serialization {
    public static void main(String[] args) {
        try (FileOutputStream fileOutputStream = new FileOutputStream("./user.ser");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            User user = new Watcher("Ron Obvious", "robvious@example.com");
            objectOutputStream.writeObject(user);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
