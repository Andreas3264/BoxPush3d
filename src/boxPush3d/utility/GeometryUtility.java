package boxPush3d.utility;

import static org.lwjgl.opengl.GL40.*;

import boxPush3d.graphics.Texture;

public class GeometryUtility {
	
	public static final float[][] uv = new float[][]
	{
		{1f,1f},
		{1f,0f},
		{0f,0f},
		{0f,1f}
	};
	
	public static final double[][] box = new double[][]
	{
		generateFace(new Vector3d(1,0,0), Vector3d.back, Vector3d.up),
		generateFace(new Vector3d(-1,0,0), Vector3d.forward, Vector3d.up),
		
		generateFace(new Vector3d(0,1,0), Vector3d.left, Vector3d.forward),
		generateFace(new Vector3d(0,-1,0), Vector3d.right, Vector3d.forward),
		
		generateFace(new Vector3d(0,0,1), Vector3d.right, Vector3d.up),
		generateFace(new Vector3d(0,0,-1), Vector3d.left, Vector3d.up),
		
	};
	
	public static void drawBox(Vector3d position, Vector3d x, Vector3d y, Vector3d z, Texture tex)
	{
		drawBox(position, x, y, z, new Texture[] {tex, tex, tex, tex, tex, tex});
	}
	
	public static void drawBox(Vector3d position, Vector3d x, Vector3d y, Vector3d z, Texture[] texs)
	{
		for(int f = 0; f < box.length; f++)
		{
			texs[f].glBind();
			glBegin(GL_QUADS);
			for(int v = 0; v < box[f].length; v+=3)
			{
				double xp = position.x + (box[f][v+0] * x.x + box[f][v+1] * y.x + box[f][v+2] * z.x);
				double yp = position.y + (box[f][v+0] * x.y + box[f][v+1] * y.y + box[f][v+2] * z.y);
				double zp = position.z + (box[f][v+0] * x.z + box[f][v+1] * y.z + box[f][v+2] * z.z);
				
				glTexCoord2f(uv[v/3][0], uv[v/3][1]);
				glVertex3d(xp, yp, zp);
			}
			glEnd();
			texs[f].glUnbind();
		}
	}
	
	private static double[] generateFace(Vector3d position, Vector3d right, Vector3d up)
	{
		double[] vertices = new double[4*3];
		
		double[][] biases = new double[][]
		{
			{-1f,-1f},
			{-1f,1f},
			{1f,1f},
			{1f,-1f}
		};
		
		for(int i = 0; i < biases.length; i++)
		{
			double xp = (position.x + biases[i][0] * right.x + biases[i][1] * up.x)*0.5;
			double yp = (position.y + biases[i][0] * right.y + biases[i][1] * up.y)*0.5;
			double zp = (position.z + biases[i][0] * right.z + biases[i][1] * up.z)*0.5;
			vertices[i*3 + 0] = xp;
			vertices[i*3 + 1] = yp;
			vertices[i*3 + 2] = zp;
		}
		return vertices;
	}

}
