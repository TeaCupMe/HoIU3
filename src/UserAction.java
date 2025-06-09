import java.util.concurrent.Callable;

public abstract class UserAction {
    String name;
    UserAction(String name) {
        this.name = name;
    }
    abstract public void act();
}
