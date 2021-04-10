package lecture07.serialization;

import java.io.Serializable;

public abstract class User implements Serializable {

    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    abstract boolean mayEdit();
    abstract boolean mayWatch();
}
