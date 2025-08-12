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

public class Snake extends Entity {
	
	public final TypeColor color;
	private Texture t;
	
	private List<Vector3d> boxes = new ArrayList<Vector3d>();
	
	private Vector3d moveDirection = Vector3d.zero;
	private State startState;
	
	public Snake(TypeColor color)
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
		
		rotTime = 0;
		falling = false;
		startState = getState();
		moveDirection = action.getMoveDirection();
		
		if(!overlappsWorld(getGlobalPosition(boxes.get(0)).add(moveDirection), true))
		{
			moving = true;
		}
	}
	
	private boolean overlappsWorld(Vector3d point, boolean includeSelfe)
	{
		for(Entity entity : GameScene.scene.entities)
		{
			if(entity.equals(this) && !includeSelfe) 
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
	
	private boolean isGrounded()
	{
		for(Vector3d box : boxes)
		{
			Vector3d point = box;
			if(overlappsWorld(getGlobalPosition(point).add(Vector3d.down), false))
			{
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public void setState(State state)
	{
		moving = false;
		falling = false;
		this.forward = state.forward;
		this.up = state.up;
		this.position = state.position;
		this.boxes = (List<Vector3d>) state.misc;
	}
	
	public State getState()
	{
		return new State(forward, up, position, boxes);
	}
	
	private boolean falling = false;
	private int fallenDistance = 0;
	private double rotTime = 0;
	
	@SuppressWarnings("unchecked")
	public void update()
	{
		if(moving && !falling)
		{
			rotTime = Math.min(rotTime + Settings.targetFrameTime * Settings.snakeSpeed, 1);
			
			List<Vector3d> sources = ((List<Vector3d>)startState.misc);
			List<Vector3d> newBoxes = new ArrayList<Vector3d>();
			Vector3d target = sources.get(0).add(moveDirection);
			
			for(Vector3d source : sources)
			{
				newBoxes.add( (target.multiply(rotTime)).add(source.multiply(1.0 - rotTime)) );
				target = source;
			}
			boxes = newBoxes;
			
			
			if(rotTime == 1)
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
		double dist = 0;
		for(Vector3d vec : boxes)
		{
			double c = Math.cos(anim * Math.PI * 2 * Settings.pulseSpeed - dist);
			c = Math.pow(c, Settings.pulseExp);
			double f = 1 + (c)*0.05 - dist * 0.01;
			
			if(GameScene.scene.selectedColor != color)
				f = 1;
			
	        GeometryUtility.drawBox(getGlobalPosition(vec), right.multiply(f), up.multiply(f), forward.multiply(f), t);
	        dist += ((0.5 * Math.PI)) / boxes.size();
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
