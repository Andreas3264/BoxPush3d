package boxPush3d.utility;

import java.util.TreeMap;

public class Glyph {
	
	public static float tileSize = 1f/10f;
	
	private static TreeMap<Character, Glyph> glyphs = new  TreeMap<Character, Glyph>();
	
	public static Glyph zero = new Glyph(new Vector2f(0, 6), '0');
	public static Glyph one = new Glyph(new Vector2f(1, 6), '1');
	public static Glyph two = new Glyph(new Vector2f(2, 6), '2');
	public static Glyph three = new Glyph(new Vector2f(3, 6), '3');
	public static Glyph four = new Glyph(new Vector2f(4, 6), '4');
	public static Glyph five = new Glyph(new Vector2f(5, 6), '5');
	public static Glyph six = new Glyph(new Vector2f(6, 6), '6');
	public static Glyph seven = new Glyph(new Vector2f(7, 6), '7');
	public static Glyph eight = new Glyph(new Vector2f(8, 6), '8');
	public static Glyph nine = new Glyph(new Vector2f(9, 6), '9');
	
	public static Glyph space = new Glyph(new Vector2f(6, 2), ' ');
	public static Glyph unknown = new Glyph(new Vector2f(7, 2), '\0');
	
	public static Glyph a = new Glyph(new Vector2f(0, 3), 'a');
	public static Glyph b = new Glyph(new Vector2f(1, 3), 'b');
	public static Glyph c = new Glyph(new Vector2f(2, 3), 'c');
	public static Glyph d = new Glyph(new Vector2f(3, 3), 'd');
	public static Glyph e = new Glyph(new Vector2f(4, 3), 'e');
	public static Glyph f = new Glyph(new Vector2f(5, 3), 'f');
	public static Glyph g = new Glyph(new Vector2f(6, 3), 'g');
	public static Glyph h = new Glyph(new Vector2f(7, 3), 'h');
	public static Glyph i = new Glyph(new Vector2f(8, 3), 'i');
	public static Glyph j = new Glyph(new Vector2f(9, 3), 'j');
	public static Glyph k = new Glyph(new Vector2f(0, 4), 'k');
	public static Glyph l = new Glyph(new Vector2f(1, 4), 'l');
	public static Glyph m = new Glyph(new Vector2f(2, 4), 'm');
	public static Glyph n = new Glyph(new Vector2f(3, 4), 'n');
	public static Glyph o = new Glyph(new Vector2f(4, 4), 'o');
	public static Glyph p = new Glyph(new Vector2f(5, 4), 'p');
	public static Glyph q = new Glyph(new Vector2f(6, 4), 'q');
	public static Glyph r = new Glyph(new Vector2f(7, 4), 'r');
	public static Glyph s = new Glyph(new Vector2f(8, 4), 's');
	public static Glyph t = new Glyph(new Vector2f(9, 4), 't');
	public static Glyph u = new Glyph(new Vector2f(0, 5), 'u');
	public static Glyph v = new Glyph(new Vector2f(1, 5), 'v');
	public static Glyph w = new Glyph(new Vector2f(2, 5), 'w');
	public static Glyph x = new Glyph(new Vector2f(3, 5), 'x');
	public static Glyph y = new Glyph(new Vector2f(4, 5), 'y');
	public static Glyph z = new Glyph(new Vector2f(5, 5), 'z');
	
	
	public Vector2f uv;
	public char ch;
	public Glyph(Vector2f uv, char ch)
	{
		this.ch = ch;
		this.uv = uv;
		glyphs.put(ch, this);
	}

	public static Glyph getGlyph(char ch)
	{
		Glyph glyph = glyphs.get(ch);
		return glyph != null ? glyph : unknown;
	}
	
}
