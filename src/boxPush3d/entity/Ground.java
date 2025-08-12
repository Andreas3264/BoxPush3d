package boxPush3d.entity;

import boxPush3d.global.Objects;
import boxPush3d.utility.GeometryUtility;
import boxPush3d.utility.Vector3d;

public class Ground extends Entity {
	
	public Ground(Vector3d pos)
	{
		this.position = pos;
	}
	
	public void render()
	{
		GeometryUtility.drawBox(position, Vector3d.right, Vector3d.up, Vector3d.forward, Objects.ground);
	}
	
	public boolean overlapsPoint(Vector3d point)
	{
		return (position.subtract(point).magnitude() < 0.5);
	}
	
	public void setState(State state)
	{
		
	}
	
	public State getState()
	{
		return null;
	}

}
