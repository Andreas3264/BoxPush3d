package boxPush3d.entity;

import boxPush3d.utility.Vector3d;

public class State {
	
	public final Vector3d forward;
	public final Vector3d up;
	public final Vector3d position;
	public final Object misc;
	
	public State(Vector3d forward, Vector3d up, Vector3d position, Object misc)
	{
		this.forward = forward;
		this.up = up;
		this.position = position;
		this.misc = misc;
	}

}
