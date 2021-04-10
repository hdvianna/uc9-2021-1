package lecture07.serialization;

public class Moderator extends User {

    public Moderator(String name, String email) {
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
