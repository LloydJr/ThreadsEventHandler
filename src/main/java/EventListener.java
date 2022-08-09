public class EventListener extends Thread implements EventHandler{

    private String messageToListenFor;
    private String messageToReplyWith;
    private Tracker eventTracker;

    public EventListener(String message, String reply) {
        this.messageToListenFor = message;
        this.messageToReplyWith = reply;
        this.eventTracker = EventTracker.getInstance();
    }

    public EventListener(String message, String reply, Tracker tracker) {
        this.messageToListenFor = message;
        this.messageToReplyWith = reply;
        this.eventTracker = tracker;
    }

    public void run() {
        while (!readyToQuit()) {
            if (shouldReply()) {
                reply();
            }
        }
    }

    public Boolean readyToQuit() {
        return eventTracker.has("quit");
    }

    public Boolean shouldReply() {
        if (eventTracker.has(messageToListenFor))
            return true;
        return null;
    }

    public void reply() {
        eventTracker.handle(messageToReplyWith, this::handle);
    }
    @Override
    public void handle() {
        System.out.println(messageToReplyWith);
    }
}