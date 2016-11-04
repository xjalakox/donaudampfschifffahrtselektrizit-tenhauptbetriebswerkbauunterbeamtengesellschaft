package enviroment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RadialGradientPaint;

public class Lighting {

	public Lighting(int x, int y, Graphics2D g2d) {
		Point mpoint = new Point();
		mpoint.x = x - x / 2;
		mpoint.y = y - y / 2;

		Paint paint = new Color(0, 0, 0, 0);
		if (mpoint != null) {
			paint = new RadialGradientPaint(mpoint, 200, new float[] { 0, 1f },
					new Color[] { new Color(0, 0, 0, 0), new Color(0, 0, 0, 255) });
		}

		g2d.setPaint(paint);
		g2d.fillRect(0, 0, x, y);
	}

}
