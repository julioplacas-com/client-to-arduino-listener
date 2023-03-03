public class Compartido {
    private Command command = Command.NADA;

    public synchronized Command getCommand() {
        return command;
    }

    public synchronized Command getAndClearCommand() {
        Command result = command;
        command = Command.NADA;
        return result;
    }

    public synchronized void setCommand(Command command) {
        this.command = command;
    }
}
