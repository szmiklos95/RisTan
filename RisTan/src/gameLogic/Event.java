package gameLogic;

//a pattern of Action
abstract class Event {
	abstract boolean satisfies(Action action);
}
