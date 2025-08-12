package boxPush3d.entity;

import java.util.ArrayList;
import java.util.List;

import boxPush3d.Action;
import boxPush3d.GameScene;
import boxPush3d.global.Objects;
import boxPush3d.global.Settings;
import boxPush3d.graphics.Texture;
import boxPush3d.utility.GeometryUtility;
import boxPush3d.utility.TypeColor;
import boxPush3d.utility.Vector3d;

public class Rotator extends Entity {
	
	public final TypeColor color;
	private Texture t;
	
	private List<Vector3d> boxes = new ArrayList<Vector3d>();
	
	private Vector3d pivotPoint = Vector3d.zero;
	private Vector3d moveDirection = Vector3d.zero;
	private State startState;
	
	public Rotator(TypeColor color)
	{
		this.color = color;
		switch(this.color)
		{
		case RED:
			t = Objects.redBox;
			break;
		case GREEN:
			t = Objects.greenBox;
			break;
		case BLUE:
			t = Objects.blueBox;
			break;
		case CYAN:
			t = Objects.cyanBox;
			break;
		case YELLOW:
			t = Objects.yellowBox;
			break;
		case MAGENTA:
			t = Objects.magentaBox;
			break;
		}
	}
	
	public void addBox(Vector3d box)
	{
		boxes.add(box);
	}
	
	public void notify(Action action)
	{
		fallenDistance = 0;
		if(GameScene.scene.selectedColor != color)
			return;
		
		if(action.getMoveDirection().equals(Vector3d.zero))
			return;
		
		if(action.getMoveDirection().y != 0)
			return;
		
		
		rotTime = 0;
		returning = false;
		falling = false;
		startState = getState();
		
		Vector3d dir = action.getMoveDirection();
		Vector3d best = dir.multiply(-999999f);
		
		for(Vector3d box : boxes)
		{
			Vector3d point = getGlobalPosition(box);
			
			if(overlappsWorld(point.add(Vector3d.down))
			|| overlappsWorld(point.add(dir).add(Vector3d.down)))
			{
				moving = true;
				best = newBestPivot(best, point, dir).discretize(2);
			}
		}
		pivotPoint = best.add( dir.add(Vector3d.down).multiply(0.5) );
		moveDirection = dir;
	}
	
	private Vector3d newBestPivot(Vector3d o, Vector3d n, Vector3d dir)
	{
		Vector3d dif = n.subtract(o);
		
		if(dif.dot(dir) > 0)
		{
			return n;
		}
		else if(dif.y > 0 && dif.dot(dir) == 0)
		{
			return n;
		}
		return o;
	}
	
	private boolean overlappsWorld(Vector3d point)
	{
		for(Entity entity : GameScene.scene.entities)
		{
			if(entity.equals(this)) 
			{
				continue;
			}
			if(entity.overlapsPoint(point))
			{
				return true;
			}
		}
		return false;
	}
	
	private boolean intersectsWorld()
	{
		for(Vector3d box : boxes)
		{
			for(double x = -1; x <= 1; x += 1)
			{
				for(double y = -1; y <= 1; y += 1)
				{
					for(double z = -1; z <= 1; z += 1)
					{
						Vector3d point = box.add((new Vector3d(x,y,z)).multiply(0.499));
						if(overlappsWorld(getGlobalPosition(point)))
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private boolean isGrounded()
	{
		for(Vector3d box : boxes)
		{
			Vector3d point = box;
			if(overlappsWorld(getGlobalPosition(point).add(Vector3d.down)))
			{
				return true;
			}
		}
		return false;
	}
	
	public void setState(State state)
	{
		super.setState(state);
		moving = false;
		falling = false;
		fallenDistance = 0;
	}
	
	private boolean returning = false;
	private boolean falling = false;
	private double fallenDistance = 0;
	private double rotTime = 0;
	public void update()
	{
		if(moving && !falling)
		{
			if(returning)
			{
				rotTime = Math.max(rotTime - Settings.targetFrameTime * Settings.rotationSpeed, 0);
			}
			else
			{
				rotTime = Math.min(rotTime + Settings.targetFrameTime * Settings.rotationSpeed, 1);
			}
			
			position = startState.position.rotateAroundPoint(pivotPoint, Vector3d.up.cross(moveDirection), rotTime * Math.PI * 0.5);
			forward = startState.forward.rotate(Vector3d.up.cross(moveDirection), rotTime * Math.PI * 0.5);
			up = startState.up.rotate(Vector3d.up.cross(moveDirection), rotTime * Math.PI * 0.5);
			
			if(!returning && intersectsWorld())
			{
				returning = true;
			}
			
			if(rotTime == 1 && !returning)
			{
				position = position.discretize(2);
				forward = forward.discretize(2);
				up = up.discretize(2);
				if (!isGrounded())
				{
					falling = true;
					moving = true;
					rotTime = 0;
					startState = getState();
				}
				else
				{
					moving = false;
				}
			}
			else if(rotTime == 0 && returning)
			{
				position = startState.position;
				forward = startState.forward;
				up = startState.up;
				moving = false;
			}
		}
		
		if(!isGrounded() && !falling && !moving)
		{
			falling = true;
			moving = true;
			rotTime = -Settings.vibrationTime * Settings.fallingSpeed;
			startState = getState();
		}
		
		if(falling)
		{
			rotTime = Math.min(rotTime + Settings.targetFrameTime * Settings.fallingSpeed, 1);
			if(rotTime < 0)
			{
				double cof = Math.sin(-rotTime * Settings.vibratSpeed / Settings.fallingSpeed) * Settings.vibrateAmplitude;
				position = startState.position.add(Vector3d.down.multiply(cof));
			}
			else
			{
				position = startState.position.add(Vector3d.down.multiply(rotTime));
			}
			
			if(rotTime == 1)
			{
				fallenDistance += 1;
				position = position.discretize(2);
				startState = getState();
				rotTime = 0;
				
				if (!isGrounded())
				{
					falling = true;
				}
				else
				{
					falling = false;
					moving = false;
				}
			}
		}
	}
	
	public boolean isAlive()
	{
		return fallenDistance < 10;
	}
	
	public boolean isOfColor(TypeColor color)
	{
		return color == this.color;
	}
	
	private double anim = 0;
	public void render()
	{
		anim += Settings.targetFrameTime;
		Vector3d right = up.cross(forward);
		
		double c = Math.cos(anim * Math.PI * 2 * Settings.pulseSpeed);
		c = Math.pow(c, Settings.pulseExp);
		double f = 1 + (c)*0.05;
		
		if(GameScene.scene.selectedColor != color)
			f = 1;
		
		for(Vector3d vec : boxes)
		{
	        GeometryUtility.drawBox(getGlobalPosition(vec), right.multiply(f), up.multiply(f), forward.multiply(f), t);
		}
	}
	
	public boolean overlapsPoint(Vector3d point)
	{
		for(Vector3d box : boxes)
		{
			if(getGlobalPosition(box).subtract(point).magnitude() < 0.5)
			{
				return true;
			}
		}
		return false;
	}
	
	private Vector3d getGlobalPosition(Vector3d local)
	{
		Vector3d right = up.cross(forward);
		Vector3d pos = position;
		pos = pos.add(right.multiply(local.x));
		pos = pos.add(up.multiply(local.y));
		pos = pos.add(forward.multiply(local.z));
		return pos;
	}

}
