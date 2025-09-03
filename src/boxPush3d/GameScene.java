package boxPush3d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import boxPush3d.entity.*;
import boxPush3d.global.Settings;
import boxPush3d.input.Input;
import boxPush3d.utility.TypeColor;
import boxPush3d.utility.Vector3d;

public class GameScene {
	
	public static GameScene scene;
	
	Camera camera;
	
	public Collection<Entity> entities = new ArrayList<Entity>();
	public TypeColor selectedColor = TypeColor.MAGENTA;
	
	private Collection<State> lastStableState;
	private Collection<State> firstState;
	
	public double loadTimer = Settings.sceneLoadTime;
	
	public GameScene(List<String> entityLoad) 
	{
		Map<TypeColor, Rotator> rotatorMap = new TreeMap<TypeColor, Rotator>();
		Map<TypeColor, Snake> snakeMap = new TreeMap<TypeColor, Snake>();
		
		for(String str : entityLoad)
		{
			if(str.equals(""))
				continue;
			
			String[] parts = str.split(" ");
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);
			int z = Integer.parseInt(parts[3]);
			Vector3d pos = new Vector3d(x, y, z);
			
			// s-re s-gr s-bl s-cy s-ma s-ye r-re r-gr r-bl r-cy r-ma r-ye gnd end dth 
			TypeColor selectedColor = TypeColor.RED;
			if(parts[0].contains("-re")) {
				selectedColor = TypeColor.RED;
			} else if(parts[0].contains("-gr")) {
				selectedColor = TypeColor.GREEN;
			} else if(parts[0].contains("-bl")) {
				selectedColor = TypeColor.BLUE;
			} else if(parts[0].contains("-cy")) {
				selectedColor = TypeColor.CYAN;
			} else if(parts[0].contains("-ma")) {
				selectedColor = TypeColor.MAGENTA;
			} else if(parts[0].contains("-ye")) {
				selectedColor = TypeColor.YELLOW;
			}
			
			if(parts[0].contains("r-")) {
				Rotator rotator = rotatorMap.get(selectedColor);
				if(rotator == null)
				{
					rotator = new Rotator(selectedColor);
					rotatorMap.put(selectedColor, rotator);
					entities.add(rotator);
				}
				rotator.addBox(pos);
				
			} else if(parts[0].contains("s-")) {
				Snake snake = snakeMap.get(selectedColor);
				if(snake == null)
				{
					snake = new Snake(selectedColor);
					snakeMap.put(selectedColor, snake);
					entities.add(snake);
				}
				snake.addBox(pos);
			} else if(parts[0].contains("gnd")) {
				entities.add(new Ground(pos));
			} else if(parts[0].contains("end")) {
				entities.add(new Goal(pos));
			} else if(parts[0].contains("dth")) {
				entities.add(new Death(pos));
			}
		}
		
		scene = this;
		camera = new Camera(4);
		camera.position = (new Vector3d(0, 999, -10)).rotate(Vector3d.right, Math.toRadians(45)).rotate(Vector3d.up, Math.toRadians(30));
		camera.forward = (new Vector3d(0, 0, 1)).rotate(Vector3d.right, Math.toRadians(45)).rotate(Vector3d.up, Math.toRadians(30));
		camera.up = (new Vector3d(0, 1, 0)).rotate(Vector3d.right, Math.toRadians(45)).rotate(Vector3d.up, Math.toRadians(30));
		entities.add(camera);
		
		rotateColor();
		lastStableState = getState();
		firstState = getState();
	}
	
	public void update()
	{
		if (loadTimer > 0 && !isLevelCompleted()) // level intro
		{
			double amp = 15;
			double disp = Math.pow(loadTimer / Settings.sceneLoadTime, 3) * amp;
			camera.position = new Vector3d(0, disp, 0);
			loadTimer -= Settings.targetFrameTime;
			return;
		}
		else if(isLevelCompleted()) // level outro
		{
			double amp = 15;
			double disp = Math.pow(loadTimer / Settings.sceneLoadTime, 3) * amp;
			camera.position = new Vector3d(0, disp, 0);
			loadTimer += Settings.targetFrameTime;
			return;
		}
		
		boolean entityMoving = false;
		for(Entity entity : entities)
			entityMoving |= (entity.moving);
		
		if(!entityMoving)
		{
			boolean fail = false;
			for(Entity entity : entities)
			{
				fail |= entity.isGameFailState();
			}
			
			if(fail)
				setState(lastStableState);
			
			if(Input.space.isButtonPress())
			{
				rotateColor();
			}
			
			if(Input.restart.isButtonPress())
			{
				setState(firstState);
			}
			
			lastStableState = getState();
			Action action = new Action();
					
			for(Entity entity : entities)
				entity.notify(action);
		}
		else
		{
			boolean allive = false;
			for(Entity entity : entities)
			{
				allive |= (entity.isAlive() && entity.moving);
			}
			if(!allive)
			{
				setState(lastStableState);
			}
		}
		
		for(Entity entity : entities)
			entity.update();
	}
	
	public boolean isLevelCompleted()
	{
		boolean entityMoving = false;
		for(Entity entity : entities)
			entityMoving |= (entity.moving);
		
		if(entityMoving)
			return false;
		
		for(Entity entity : entities)
			if(entity.isGameFailState())
				return false;
		
		for(Entity entity : entities)
			if(!entity.isGameWinState())
				return false;
		
		return true;
	}
	
	private Collection<State> getState()
	{
		Collection<State> ws = new ArrayList<State>();
		for(Entity entity : entities)
			ws.add(entity.getState());
		return ws;
	}
	
	private void setState(Collection<State> ws)
	{
		Iterator<State> wsi = ws.iterator();
		Iterator<Entity> ei = entities.iterator();
		
		while(wsi.hasNext())
			ei.next().setState(wsi.next());
	}
	
	public void render()
	{
		for(Entity entity : entities)
			entity.render();
	}
	
	private void rotateColor()
	{
		setNextColor();
		while(!allowedColor(selectedColor))
			setNextColor();
	}
	
	private boolean allowedColor(TypeColor color)
	{
		for(Entity entity : entities)
			if(entity.isOfColor(color))
				return true;
		return false;
	}
	
	private void setNextColor()
	{
		switch(selectedColor)
		{
		case RED:
			selectedColor = TypeColor.GREEN;
			break;
		case GREEN:
			selectedColor = TypeColor.BLUE;
			break;
		case BLUE:
			selectedColor = TypeColor.CYAN;
			break;
		case CYAN:
			selectedColor = TypeColor.YELLOW;
			break;
		case YELLOW:
			selectedColor = TypeColor.MAGENTA;
			break;
		case MAGENTA:
			selectedColor = TypeColor.RED;
			break;
		}
	}
	
	private static final float colorFactor = 0.2f;
	public float getSkyRed()
	{
		if(selectedColor == TypeColor.RED || selectedColor == TypeColor.MAGENTA || selectedColor == TypeColor.YELLOW)
			return colorFactor;
		return 0;
	}
	
	public float getSkyGreen()
	{
		if(selectedColor == TypeColor.GREEN || selectedColor == TypeColor.CYAN || selectedColor == TypeColor.YELLOW)
			return colorFactor;
		return 0;
	}
	
	public float getSkyBlue()
	{
		if(selectedColor == TypeColor.BLUE || selectedColor == TypeColor.CYAN || selectedColor == TypeColor.MAGENTA)
			return colorFactor;
		return 0;
	}

}
