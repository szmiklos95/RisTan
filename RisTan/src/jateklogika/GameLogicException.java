package jateklogika;

//An exception from the internal game logic
public class GameLogicException extends Exception {
	private static final long serialVersionUID=1L;

	public GameLogicException(String message) {
		super(message);
	}
}
