package boxPush3d.utility;

public class Color {
	
	public static final Color white = new Color(1f, 1f, 1f);
	public static final Color black = new Color(0f, 0f, 0f);
	
	public final float r;
	public final float g;
	public final float b;
	
	public Color(float r, float g, float b)
	{
		this.r = r;
		this.b = b;
		this.g = g;
	}
	
	public boolean equals(Color c)
	{
		return r == c.r && g == c.g && b == c.b;
	}
	
	public Color multiply(float f)
	{
		return new Color(r * f, g * f, b * f);
	}

}
