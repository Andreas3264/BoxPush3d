package boxPush3d.physics;


public class Collision {
	
	public static boolean overlap(Box b1, Box b2)
	{
		if(b1 == null || b2 == null) {return false;}
		
		return (   Math.abs(b1.x - b2.x) < (b1.xSize*0.5 + b2.xSize * 0.5)
				&& Math.abs(b1.y - b2.y) < (b1.ySize*0.5 + b2.ySize * 0.5)
				&& Math.abs(b1.z - b2.z) < (b1.zSize*0.5 + b2.zSize * 0.5));
	}

}
