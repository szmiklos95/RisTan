package gameLogic;

class OccupyFreeTileEvent extends Event {
	@Override
	boolean satisfies(Action action) {
		return action instanceof OccupyFreeTileAction;
	}
}
