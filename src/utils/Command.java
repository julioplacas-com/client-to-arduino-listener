package utils;
public enum Command {
    NADA(" "),
    ENCENDER("e"),
    APAGAR("a"),
    OFF("off");

    private String command;

    Command(String command) {
        this.command = command;
    }

    public String getCommand() { return command; }
}
