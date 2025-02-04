package sw.util;

import arc.graphics.Color;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.graphics.Pal;

public class SWDraw {
  public static final Color heatPal = Pal.accent.cpy();

  public static TextureRegion[] getRegions(TextureRegion base, int width, int height, int size) {
    int arraySize = width * height;
    TextureRegion[] out = new TextureRegion[arraySize];
    for (int i = 0; i < arraySize; i++) {
      TextureRegion n = new TextureRegion(base);
      float ix = i % width;
      float iy = Mathf.floor((float) i/width);

      n.width = n.height = size;
      n.u = Mathf.map(ix + 0.02f, 0, width, base.u, base.u2);
      n.u2 = Mathf.map(ix + 0.98f, 0, width, base.u, base.u2);
      n.v = Mathf.map(iy + 0.02f, 0, height, base.v, base.v2);
      n.v2 = Mathf.map(iy + 0.98f, 0, height, base.v, base.v2);

      out[i] = n;
    }
    return out;
  }
  public static TextureRegion getRegion(TextureRegion base, int width, int height, int size, int index) {
    return getRegions(base, width, height, size)[index];
  }

  public static void square(Color color, float x, float y, float rad, float rot) {
    float oldStroke = Lines.getStroke();
    Lines.stroke(oldStroke * 3f, Pal.gray);
    Lines.square(x, y, rad, rot);
    Lines.stroke(oldStroke, color);
    Lines.square(x, y, rad, rot);
  }
  public static void square(Color color, float sx, float sy, float rad) {
    float oldStroke = Lines.getStroke();
    float x = sx - rad, y = sy - rad;
    Lines.stroke(oldStroke * 3f, Pal.gray);
    Lines.line(x, y, x + rad*2f, y);
    Lines.line(x + rad*2f, y, x + rad*2f, y +  rad*2f);
    Lines.line(x + rad*2f, y +  rad*2f, x, y +  rad*2f);
    Lines.line(x, y +  rad*2f, x, y);

    Lines.stroke(oldStroke, color);
    Lines.line(x, y, x + rad*2f, y);
    Lines.line(x + rad*2f, y, x + rad*2f, y +  rad*2f);
    Lines.line(x + rad*2f, y +  rad*2f, x, y +  rad*2f);
    Lines.line(x, y +  rad*2f, x, y);
  }
  public static void line(Color color, float x, float y, float angle, float length) {
    float oldStroke = Lines.getStroke();
    Lines.stroke(Lines.getStroke() * 3f, Pal.gray);
    Lines.lineAngle(x, y, angle, length);
    Lines.stroke(oldStroke, color);
    Lines.lineAngle(x, y, angle, length);
  }
  public static void linePoint(Color middle, Color base, float x, float y, float x2, float y2) {
    float oldStroke = Lines.getStroke();
    Lines.stroke(Lines.getStroke() * 3f, base);
    Lines.line(x, y, x2, y2);
    Lines.stroke(oldStroke, middle);
    Lines.line(x, y, x2, y2);
  }
}
