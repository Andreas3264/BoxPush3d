package boxPush3d.entity;

import boxPush3d.Action;
import boxPush3d.utility.TypeColor;
import boxPush3d.utility.Vector3d;

public class Entity {
	
	public boolean moving;
	
	public Vector3d forward = Vector3d.forward;
	public Vector3d up = Vector3d.up;
	public Vector3d position = Vector3d.zero;
	
	
	public void notify(Action action)
	{
		
	}
	
	public void update()
	{
		
	}
	
	public void render()
	{
		
	}
	
	public boolean overlapsPoint(Vector3d point)
	{
		return false;
	}
	
	public boolean isAlive()
	{
		return true;
	}
	
	public boolean isOfColor(TypeColor color)
	{
		return false;
	}
	
	public boolean isGameWinState()
	{
		return true;
	}
	
	public boolean isGameFailState()
	{
		return false;
	}
	
	public void setState(State state)
	{
		this.forward = state.forward;
		this.up = state.up;
		this.position = state.position;
	}
	
	public State getState()
	{
		return new State(forward, up, position, null);
	}

}
