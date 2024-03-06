public class Placement {

	private boolean active = false;
	private boolean markerPlaced = false;
	private final int x;
	private final int y;
	private final int xIndex;
	private final int yIndex;
	private final int width;
	private final int height;
	
	public Placement(int x, int y, int xIndex, int yIndex, int width, int height) {
		this.x = x;
		this.y = y;
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		this.width = width;
		this.height = height;
	}

	public void checkCollision(int x, int y) {
		if(markerPlaced) {
			return;
		}

		active = x > this.x && x < this.x + width &&
				y > this.y && y < this.y + height;
	}
	

	public int getxIndex() {
		return xIndex;
	}

	public int getyIndex() {
		return yIndex;
	}

	public boolean isActive() {
		return active;
	}
	
	public void set(boolean isSet) {
		markerPlaced = isSet;

		if(isSet) {
			active = false;
		}
	}

}
