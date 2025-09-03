package boxPush3d;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL40.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import boxPush3d.global.Objects;
import boxPush3d.global.Settings;
import boxPush3d.input.Input;

public class GameInstance {
	
	GameScene gs;
	private List<String> levels;
	private int levelIndex = 0;
	
	public GameInstance() throws IOException
	{
		levels = Files.readAllLines(Paths.get("resources/levels.txt"), StandardCharsets.UTF_8);
		
		Objects.create();
		loadLevel();
	}
	
	private void loadLevel()
	{
		try {
			gs = new GameScene(Files.readAllLines(Paths.get("resources/levels/" + levels.get(levelIndex) + ".txt"), StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		try
		{
			while(Objects.window.isOpen())
			{
				loop();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Objects.window.destroy();
	}
	
	private void loop()
	{
		update();
		draw();
		
		glFlush();
		glFinish();
		
		glfwPollEvents();
		glfwSwapBuffers(Objects.window.getID());
	}
	
	private void draw()
	{
		glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDepthRange(0, 1);
        glDepthFunc(GL_LEQUAL);
        
        glAlphaFunc(GL_GREATER, 0.5f);
        glEnable(GL_ALPHA_TEST);
        
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
		
        glMatrixMode(GL_PROJECTION);
        glLoadMatrixd(gs.camera.getProjectionMatrix());
       
        glMatrixMode (GL_MODELVIEW);
        glLoadIdentity();
        
        glClearColor(gs.getSkyRed(), gs.getSkyGreen(), gs.getSkyBlue(), 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
		gs.render();
        
		glDisable(GL_ALPHA_TEST);
	}
	
	private void update()
	{
		Input.update();
		gs.update();
		if(gs.isLevelCompleted() && gs.loadTimer > Settings.sceneLoadTime)
		{
			levelIndex++;
			if(levelIndex >= levels.size())
			{
				System.out.println("Win!");
				System.exit(0);
			}
			loadLevel();
		}
	}

}
