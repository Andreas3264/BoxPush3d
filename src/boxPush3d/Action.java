package boxPush3d;

import boxPush3d.input.Input;
import boxPush3d.utility.Vector3d;

public class Action {
	
	public final boolean up, down, fwd, bck, left, right, rotateRight, rotateLeft;
	
	public Action()
	{
		fwd = Input.forward.isButtonPress();
		bck = Input.back.isButtonPress();
		
		left = Input.left.isButtonPress();
		right = Input.right.isButtonPress();
		
		up = Input.up.isButtonPress();
		down = Input.down.isButtonPress();
		
		rotateRight = Input.KEY_E.isButtonPress();
		rotateLeft = Input.KEY_Q.isButtonPress();
	}
	
	public Vector3d getMoveDirection()
	{
		Vector3d z = Vector3d.forward;
		Vector3d x = Vector3d.right;
		Vector3d y = Vector3d.up;
		
		if(fwd && onlyOneButton())
			return z;
		
		if(bck && onlyOneButton())
			return z.multiply(-1);
		
		if(right && onlyOneButton())
			return x;
		
		if(left && onlyOneButton())
			return x.multiply(-1);
		
		if(up && onlyOneButton())
			return y;
		
		if(down && onlyOneButton())
			return y.multiply(-1);
		
		return Vector3d.zero;
	}
	
	private boolean onlyOneButton()
	{
		int c = 0;
		if(up) {c++;}
		if(down) {c++;}
		if(fwd) {c++;}
		if(bck) {c++;}
		if(left) {c++;}
		if(right) {c++;}
		if(rotateRight) {c++;}
		if(rotateLeft) {c++;}
		return c == 1;
	}

}
