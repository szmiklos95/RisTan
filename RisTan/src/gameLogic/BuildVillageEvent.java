package gameLogic;

class BuildVillageEvent extends Event {
	@Override
	boolean satisfies(Action action) {
		return action instanceof BuildVillageAction;
	}
}
