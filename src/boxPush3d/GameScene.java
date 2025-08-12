package boxPush3d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import boxPush3d.entity.*;
import boxPush3d.input.Input;
import boxPush3d.utility.TypeColor;
import boxPush3d.utility.Vector3d;

public class GameScene {
	
	public static GameScene scene;
	
	Camera camera = new Camera(4);
	
	public Collection<Entity> entities = new ArrayList<Entity>();
	public TypeColor selectedColor = TypeColor.MAGENTA;
	
	private Collection<State> lastStableState;
	private Collection<State> firstState;
	
	public GameScene() 
	{
		scene = this;
		
		camera.position = (new Vector3d(0, 0, -10)).rotate(Vector3d.right, Math.toRadians(45)).rotate(Vector3d.up, Math.toRadians(30));
		camera.forward = (new Vector3d(0, 0, 1)).rotate(Vector3d.right, Math.toRadians(45)).rotate(Vector3d.up, Math.toRadians(30));
		camera.up = (new Vector3d(0, 1, 0)).rotate(Vector3d.right, Math.toRadians(45)).rotate(Vector3d.up, Math.toRadians(30));
		entities.add(camera);
		
		Rotator red = new Rotator(TypeColor.RED);
		red.addBox(new Vector3d(0, 0, 0));
		red.addBox(new Vector3d(0, 1, 0));
		
		Snake cyan = new Snake(TypeColor.CYAN);
		for(int i = 1; i <= 3; i++)
		{
			cyan.addBox(new Vector3d(i, 0, 0));
		}
		
		entities.add(red);
		entities.add(cyan);
		
		entities.add(new Ground(new Vector3d(0, -1, 0)));
		entities.add(new Ground(new Vector3d(1, -1, 0)));
		entities.add(new Ground(new Vector3d(2, -1, 0)));
		entities.add(new Ground(new Vector3d(3, -1, 0)));
		
		entities.add(new Ground(new Vector3d(0, -1, 1)));
		entities.add(new Ground(new Vector3d(1, -1, 1)));
		entities.add(new Ground(new Vector3d(2, -1, 1)));
		entities.add(new Goal(new Vector3d(3, -1, 1)));
		entities.add(new Death(new Vector3d(-1, -1, 1)));
		
		rotateColor();
		lastStableState = getState();
		firstState = getState();
	}
	
	public void update()
	{
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
