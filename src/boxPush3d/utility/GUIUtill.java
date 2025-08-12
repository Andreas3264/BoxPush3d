package boxPush3d.utility;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2d;

import boxPush3d.global.Objects;
import boxPush3d.graphics.Texture;

public class GUIUtill {
	
	public static void drawString(String s, float xx, float yy, float height)
	{
		Objects.glyphAtlas.glBind();
		glBegin(GL_QUADS);
		
		for(int i = 0; i < s.length(); i++)
		{
			Glyph glyph = Glyph.getGlyph(s.charAt(i));
			
			float ux = glyph.uv.x * Glyph.tileSize;
			float uy = glyph.uv.y * Glyph.tileSize;
			float de = Glyph.tileSize;
			float xSize = height * (6f/8f);
			float ySize = height ;
			float x = xx + i * xSize;
			float y = yy;
			
			glTexCoord2f(ux, uy + de);
			glVertex2d(x, y);
		
			glTexCoord2f(ux, uy );
			glVertex2d(x, y + ySize);
		
			glTexCoord2f(ux + de, uy );
			glVertex2d(x + xSize, y + ySize);
		
			glTexCoord2f(ux + de, uy + de);
			glVertex2d(x + xSize, y);
		}
		
		glEnd();
		Objects.glyphAtlas.glUnbind();
	}
	
	public static void drawSquare(float x, float y, float size, Texture texture)
	{
		texture.glBind();
		glBegin(GL_QUADS);
		
		glTexCoord2f(0, 1);
		glVertex2d(x, y);
	
		glTexCoord2f(0, 0 );
		glVertex2d(x, y + size);
	
		glTexCoord2f(1, 0 );
		glVertex2d(x + size, y + size);
	
		glTexCoord2f(1, 1);
		glVertex2d(x + size, y);
		
		glEnd();
		texture.glUnbind();
	}
	
	public static void drawSquare(float x, float y, float size, Texture texture, Vector2f uv, float tileSize)
	{
		texture.glBind();
		glBegin(GL_QUADS);
		
		float ux = uv.x * tileSize;
		float uy = uv.y * tileSize;
		float de = tileSize;
		
		glTexCoord2f(ux, uy + de);
		glVertex2d(x, y);
	
		glTexCoord2f(ux, uy);
		glVertex2d(x, y + size);
	
		glTexCoord2f(ux + de, uy);
		glVertex2d(x + size, y + size);
	
		glTexCoord2f(ux + de, uy + de);
		glVertex2d(x + size, y);
		
		glEnd();
		texture.glUnbind();
	}

}
