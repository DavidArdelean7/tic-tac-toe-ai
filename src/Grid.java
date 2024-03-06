import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Grid {
	
	private final ArrayList<Placement> placements = new ArrayList<>(Main.SIZE);
	private final Marker[][] markers;

	private int markerIndex = 0;
	private boolean gameEnd = false;
	private int winType = -1;
	
	public Grid() {
		
		markers = new Marker[Main.ROWS][Main.ROWS];
		
		for (int i = 0; i < Main.SIZE; i++) {
			int xIndex = i % Main.ROWS;
			int yIndex = i / Main.ROWS;
			int size = Main.WIDTH / Main.ROWS;
			
			placements.add(new Placement(xIndex * size, yIndex * size, xIndex, yIndex, size, size));
		}
		
		reset();
	}



	public void render(Graphics2D graphicsRender) {

		for (Marker[] marker : markers) {
			for (int y = 0; y < markers.length; y++) {
				if (marker[y] == null) {
					continue;
				}

				marker[y].render(graphicsRender);
			}
		}
		
		renderGrid(graphicsRender);
		
	}

	private void renderGrid(Graphics2D graphicsRender) {
		graphicsRender.setColor(new Color(0x111));
		
		int rowSize = Main.WIDTH / Main.ROWS;
		for (int x = 0; x < Main.ROWS + 1; x++) {
			int gridThickness = 10;
			graphicsRender.fillRect(x * rowSize - (gridThickness / 2), 0, gridThickness, Main.WIDTH);
			for (int y = 0; y < Main.ROWS + 1; y++) {
				graphicsRender.fillRect(0, y * rowSize - (gridThickness / 2), Main.WIDTH, gridThickness);
			}
		}
		
		graphicsRender.setColor(Color.white);
		
		if(gameEnd) {
			drawEndGameOverlay(graphicsRender);
		}
	}

	private void drawEndGameOverlay(Graphics2D graphicsRender) {
		graphicsRender.setColor(new Color(0, 0, 0, (int)(225 * 0.5f)));
		
		graphicsRender.fillRect(0, Main.HEIGHT/3, Main.WIDTH, Main.HEIGHT/3);
		graphicsRender.setColor(Color.white);
		
		if(winType == -1) {
			// tie!
			graphicsRender.drawString("It's a tie!",  295, 335);
		} else {
			// won!
			graphicsRender.drawString((winType == 0 ? "X" : "O") + " has won!",  275, 335);
		}

		graphicsRender.drawString("Press anywhere to restart!",  185, 360);
		
	}

	public void mouseMoved(MouseEvent e) {
		if(gameEnd) {
			return;
		}

		for (Placement placement : placements) {
			placement.checkCollision(e.getX(), e.getY() - 30);
		}
	}

	public void mouseReleased(MouseEvent e) {
		for (Placement placement : placements) {
			if(placement.isActive()) {
				placement.set(true);

				int x = placement.getxIndex();
				int y = placement.getyIndex();
				placeMarker(x, y);
			}
		}
	}

	public void placeMarker(int moveIndex) {
		placeMarker(moveIndex % Main.ROWS, moveIndex / Main.ROWS);
	}

	private void placeMarker(int x, int y) {
		markers[x][y] = new Marker(x, y, markerIndex);
		
		markerIndex ++;
		
		ArrayList<Marker> winLine = Checker.checkWin(markers);
		
		if(winLine != null) {
			winLine.forEach(marker -> marker.setWon(true));
			winType = winLine.get(0).getType();
			gameEnd = true;
			
		} else if(markerIndex >= Main.SIZE) {
			gameEnd = true;
		}
	}
	
	public void reset() {
		for (int x = 0; x < markers.length; x++) {
			for (int y = 0; y < markers.length; y++) {
				markers[x][y] = null;
			}
		}


		for (Placement placement : placements) {
			placement.set(false);
		}

		gameEnd = false;
		winType = -1;
		markerIndex = 0;
	}
	
	public boolean isGameEnd() {
		return gameEnd;
	}

	public int getTurn() {
		return markerIndex % 2;
	}

	public Marker[][] getMarkers() {
		return markers;
	}

}
