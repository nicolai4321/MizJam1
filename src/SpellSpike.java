import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import nWiweEngine.EditorObject;
import nWiweEngine.GameController;
import nWiweEngine.GameObject;
import nWiweEngine.GameObjectMoving;
import nWiweEngine.GraphicCanvas;
import nWiweEngine.Sprite;
import nWiweEngine.SpriteBasic;

public class SpellSpike extends GameObjectMoving {
	private Sprite sprite;
	private BufferedImage sprites;
	private GameObject owner;
	private float dx;
	private float dy;
	
	public SpellSpike(GameController gameController, float posX, float posY, BufferedImage sprites, GameObject owner, float dx, float dy) {
		super(gameController, posX, posY, 16, 16);
		this.sprites = sprites;
		this.owner = owner;
		this.dy = dy;
		this.dx = dx;

		int w = 8;
		int h = 8;
		int degree;
		if(dx>0 && dy<0) {
			degree = 180+((int) Math.toDegrees(Math.atan2(Math.abs(dx), Math.abs(dy))));
		} else if(dx>0 && dy>0) {
			degree = 360-((int) Math.toDegrees(Math.atan2(Math.abs(dx), Math.abs(dy))));
		} else if(dx<0 && dy<0) {
			degree = 180-((int) Math.toDegrees(Math.atan2(Math.abs(dx), Math.abs(dy))));
		} else {
			degree = ((int) Math.toDegrees(Math.atan2(Math.abs(dx), Math.abs(dy))));
		}
		
		BufferedImage image = Sprite.getSprite(sprites, 372, 67, w, h);
		BufferedImage rotated = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = rotated.createGraphics();
		g.rotate(Math.toRadians(degree), w/2, h/2);
		g.drawRenderedImage(image, null);
		g.dispose();
		image = rotated;
		sprite = new SpriteBasic(gameController, this, image);
		
		if(owner instanceof Player) {
		} else {
			addSolidClass((new Player(gameController, 0, 0, sprites)).getClass());			
		}
		addSolidClass((new Tree(gameController, 0, 0, sprites)).getClass());
		setIgnoreBorder(true);
	}

	
	
	@Override
	public void construct() {}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public EditorObject initEditorObject() {
		return null;
	}

	@Override
	public void kill() {}

	@Override
	public void restartLevel() {
		levelController.removeGameObject(this);
	}

	@Override
	public void spawn() {}

	@Override
	public String toString() {
		return "Spell spike";
	}

	@Override
	public void update() {
		float[] newPos = move(dx, dy, 0);
		if(newPos[2] == 1.0f) {
			levelController.removeGameObject(this);
		}
	}
}
