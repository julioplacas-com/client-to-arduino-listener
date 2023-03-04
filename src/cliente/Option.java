package cliente;

import utils.Command;

public enum Option {
	ENCENDER_RELE("Encender rele", Command.ENCENDER),
	APAGAR_RELE("Apagar rele", Command.APAGAR),
	APAGAR_ARDUINO("Apagar arduino", Command.OFF),
	APAGAR_CLIENTE("Apagar / parar este programa cliente", null);
	
	public String text;
	public Command command;
	
	Option(String text, Command command) {
		this.text = text;
		this.command = command;
	}
}
