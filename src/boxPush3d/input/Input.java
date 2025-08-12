package boxPush3d.input;

import static org.lwjgl.glfw.GLFW.*;

import boxPush3d.global.Objects;

public class Input {
	
	public static boolean mouseVisible = true;
	
	public static ButtonState forward = new ButtonState();
	public static ButtonState back = new ButtonState();
	public static ButtonState left = new ButtonState();
	public static ButtonState right = new ButtonState();
	public static ButtonState up = new ButtonState();
	public static ButtonState down = new ButtonState();
	
	
	public static ButtonState KEY_Q = new ButtonState();
	public static ButtonState KEY_E = new ButtonState();
	
	public static ButtonState space = new ButtonState();
	public static ButtonState restart = new ButtonState();
	
	public static void init()
	{
		addCallback();
	}
	
	public static void update()
	{
		mouseH = mouseX - lastX;
		lastX = mouseX;
		mouseV = mouseY - lastY;
		lastY = mouseY;
		
		forward.updateState(glfwGetKey(Objects.window.getID(), GLFW_KEY_W) == GLFW_PRESS);
		back.updateState(glfwGetKey(Objects.window.getID(), GLFW_KEY_S) == GLFW_PRESS);
		right.updateState(glfwGetKey(Objects.window.getID(), GLFW_KEY_D) == GLFW_PRESS);
		left.updateState(glfwGetKey(Objects.window.getID(), GLFW_KEY_A) == GLFW_PRESS);
		up.updateState(glfwGetKey(Objects.window.getID(), GLFW_KEY_UP) == GLFW_PRESS);
		down.updateState(glfwGetKey(Objects.window.getID(), GLFW_KEY_DOWN) == GLFW_PRESS);

		KEY_Q.updateState(glfwGetKey(Objects.window.getID(), GLFW_KEY_Q) == GLFW_PRESS);
		KEY_E.updateState(glfwGetKey(Objects.window.getID(), GLFW_KEY_E) == GLFW_PRESS);
		
		space.updateState(glfwGetKey(Objects.window.getID(), GLFW_KEY_SPACE) == GLFW_PRESS);
		restart.updateState(glfwGetKey(Objects.window.getID(), GLFW_KEY_R) == GLFW_PRESS);
	}
	
	
	public static float getMouseX()
	{
		return mouseH;
	}
	public static float getMouseY()
	{
		return mouseV;
	}
	
	public static float getNormalMouseX()
	{
		return (mouseX*2 - Objects.window.width) / Objects.window.height;
	}
	
	public static float getNormalMouseY()
	{
		return -(mouseY*2 - Objects.window.height) / Objects.window.height;
	}
	
	private static float mouseX, mouseY, lastX, lastY, mouseH, mouseV;
	
	private static void addCallback()
	{
		glfwSetCursorPosCallback(Objects.window.getID(), (_window, xpos, ypos) -> {
            //if (glfwGetInputMode(Objects.window.getID(), GLFW_CURSOR) == GLFW_CURSOR_DISABLED) 
            {
                //if (firstMouse) 
                {
                    //glfwSetCursorPos(Objects.window.getID(), lastX, lastY);
                    // lastX = (float) xpos;
                    // lastY = (float) ypos;
                    //firstMouse = false;
                }

            	mouseX = (float) xpos;
            	mouseY = (float) ypos;
               
            }
        });
	}

}
