package lecture07.serialization;

public class Watcher extends User {

    public Watcher(String name, String email) {
        super(name, email);
    }

    @Override
    boolean mayEdit() {
        return true;
    }

    @Override
    boolean mayWatch() {
        return true;
    }
}
