package boxPush3d.global;


import boxPush3d.graphics.Texture;
import boxPush3d.graphics.Window;
import boxPush3d.input.Input;

public class Objects {
	
	
	public static Window window;
	public static Texture glyphAtlas;
	public static Texture redBox;
	public static Texture greenBox;
	public static Texture blueBox;
	public static Texture cyanBox;
	public static Texture yellowBox;
	public static Texture magentaBox;
	
	public static Texture ground;
	public static Texture goal;
	public static Texture death;

	public static void create()
	{
		window = new Window("Window", 1080, 720);
		glyphAtlas = Texture.get("resources/textures/glyphs.png");
		
		redBox = Texture.get("resources/textures/Red box.png");
		greenBox = Texture.get("resources/textures/Green box.png");
		blueBox = Texture.get("resources/textures/Blue box.png");
		cyanBox = Texture.get("resources/textures/Cyan box.png");
		yellowBox = Texture.get("resources/textures/Yellow box.png");
		magentaBox = Texture.get("resources/textures/MAgenta box.png");
		
		ground = Texture.get("resources/textures/Ground.png");
		goal = Texture.get("resources/textures/Goal.png");
		death = Texture.get("resources/textures/Death.png");
		
		Input.init();
	}
	
}
