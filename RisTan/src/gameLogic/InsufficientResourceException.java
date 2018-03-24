package gameLogic;

//The player has not enough resources for something
public class InsufficientResourceException extends GameLogicException {
	private static final long serialVersionUID=1L;
	
	public InsufficientResourceException(String message) {
		super(message);
	}
}