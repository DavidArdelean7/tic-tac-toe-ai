public class AI {

	private Minimax minimax;
	private Grid grid;


	private boolean aiMoving;
	
	public AI(Grid grid) {
		this.grid = grid;
		minimax = new Minimax();
	}
	
	public void makeMove() {
		aiMoving = true;
	}

	public void update() {
		if(!aiMoving) {
			return;
		}

		grid.placeMarker(minimax.getBestMove(grid.getMarkers(), grid.getTurn()));
		aiMoving = false;

	}


	public boolean isMoving() {
		return aiMoving;
	}

}
