package by.zhuk.buber.command;

public class Router {
    private TransitionType type;
    private String transitionResource ;

    public Router(TransitionType type, String transitionResource) {
        this.type = type;
        this.transitionResource = transitionResource;
    }

    public TransitionType getType() {
        return type;
    }

    public String getTransitionResource() {
        return transitionResource;
    }
}