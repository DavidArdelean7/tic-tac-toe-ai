import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Marker {
	
	private BufferedImage marker;
	
	private int x;
	private int y;
	private int type;
	
	private boolean won = false;
	private float alpha = 1;
	
	public Marker(int x, int y, int type) {
		this.x = x;
		this.y = y;
		
		this.type = type % 2;
		String markerType = this.type == 0 ? "x" : "o";
		
		try {
			marker = ImageIO.read(new File("assets/" + markerType + ".png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public Marker(int type) {
		this.type = type % 2;
	}

	public void render(Graphics2D graphicsRender) {
		
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		graphicsRender.setComposite(ac);
		
		int size = Main.WIDTH / Main.ROWS;
		graphicsRender.drawImage(marker, x * size, y * size, size, size, null);

		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
		graphicsRender.setComposite(ac);
	}


	public int getType() {
		return type;
	}
	
	public void setWon(boolean won) {
		this.won = won;
	}

}
