package boxPush3d.entity;

import boxPush3d.GameScene;
import boxPush3d.global.Objects;
import boxPush3d.utility.GeometryUtility;
import boxPush3d.utility.Vector3d;

public class Goal extends Entity {
	
	public Goal(Vector3d pos)
	{
		this.position = pos;
	}
	
	public void render()
	{
		GeometryUtility.drawBox(position, Vector3d.right, Vector3d.up, Vector3d.forward, Objects.goal);
	}
	
	public boolean overlapsPoint(Vector3d point)
	{
		return (position.subtract(point).magnitude() < 0.5);
	}
	
	public boolean isGameWinState()
	{
		for(Entity entity : GameScene.scene.entities)
		{
			if(entity.overlapsPoint(position.add(Vector3d.up)))
			{
				return true;
			}
		}
		return false;
	}
	
	public void setState(State state)
	{
		
	}
	
	public State getState()
	{
		return null;
	}

}
