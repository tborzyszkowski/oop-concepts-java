package inheritance.t08;

interface MessageSender {
    void send(String to, String text);
}

class ConsoleSender implements MessageSender {
    public void send(String to, String text) {
        System.out.println("To " + to + ": " + text);
    }
}

class NotificationService {
    private final MessageSender sender;

    NotificationService(MessageSender sender) {
        this.sender = sender;
    }

    void notifyStudent(String name) {
        sender.send(name, "Nowe materialy dostepne");
    }
}

public class DiAbstractionDemo {
    public static void main(String[] args) {
        NotificationService service = new NotificationService(new ConsoleSender());
        service.notifyStudent("Jan");
    }
}

