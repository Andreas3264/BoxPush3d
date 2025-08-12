package boxPush3d.graphics;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.GL;

public class Window {
	
	private String title;
    public int width, height;
    private long windowID;
	
    //private GLFWVidMode vidMode;
    
	public Window(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        create();
    } 

	private void create() 
	{
        if(!glfwInit()) {
            throw new IllegalStateException("GLFW couldn't be initialized!");
        }
        
        windowID = glfwCreateWindow(width, height, title, 0, 0);

        if(windowID == 0) {
           throw new IllegalStateException("GLFW failed to create a window!");
        }
        
        //vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        glfwSwapInterval(1);
        
        glfwMakeContextCurrent(windowID);
        GL.createCapabilities();
        
        glfwSetFramebufferSizeCallback(windowID, (window, width, height) -> {
            //glViewport(0, 0, width, height);
            this.width = width;
            this.height = height;
        });
    }
	
	public boolean isOpen()
	{
		return !glfwWindowShouldClose(windowID);
	}
	
	public void destroy()
	{
	    glfwDestroyWindow(windowID);
	    glfwTerminate();
	}
	
	public double getAspectRatio()
	{
		return (double)width / (double)height;
		//return (double)vidMode.width() / (double)vidMode.height();
	}
	
	private double[] normalMatrix = new double[16];
	public double[] getNormalMatrix()
	{
		normalMatrix[12] = 0;
		normalMatrix[13] = 0;
		normalMatrix[14] = 0;
		
		normalMatrix[0] = 1d/getAspectRatio();
		normalMatrix[5] = 1;
		normalMatrix[10] = 1;
		normalMatrix[15] = 1;
		return normalMatrix;
	}
	
	public long getID()
	{
		return windowID;
	}
}
