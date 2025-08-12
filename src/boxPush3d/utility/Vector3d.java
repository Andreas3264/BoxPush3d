package boxPush3d.utility;

public class Vector3d {
	
	public static final Vector3d right = new Vector3d(1,0,0);
	public static final Vector3d left = new Vector3d(-1,0,0);
	public static final Vector3d up = new Vector3d(0,1,0);
	public static final Vector3d down = new Vector3d(0,-1,0);
	public static final Vector3d forward = new Vector3d(0,0,1);
	public static final Vector3d back = new Vector3d(0,0,-1);
	
	public static final Vector3d zero = new Vector3d(0,0,0);
	
	public final double x, y, z;
	
	public Vector3d(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double sqrMagnitude()
	{
		return x*x + y*y + z*z;
	}
	
	public double dot(Vector3d o)
	{
		return x*o.x + y*o.y + z*o.z;
	}
	
	public double magnitude()
	{
		return Math.sqrt(sqrMagnitude());
	}
	
	public Vector3d discretize(double size)
	{
		double xx = (Math.round(x * size))/size;
		double yy = (Math.round(y * size))/size;
		double zz = (Math.round(z * size))/size;
		
		return new Vector3d(xx, yy, zz);
	}
	
	public Vector3d add(Vector3d v)
	{
		return new Vector3d(x + v.x, y + v.y, z + v.z);
	}
	
	public Vector3d subtract(Vector3d v)
	{
		return new Vector3d(x - v.x, y - v.y, z - v.z);
	}
	
	public Vector3d multiply(double d)
	{
		return new Vector3d(x * d, y * d, z * d);
	}
	
	public Vector3d normalized()
	{
		return this.multiply(1d / (this.magnitude() + 0.000001d));
	}
	
	public Vector3d cross(Vector3d other)
	{
		return new Vector3d(
				y*other.z - z*other.y,
				z*other.x - x*other.z,
				x*other.y - y*other.x
				);
	}
	
	public Vector3d rotate(Vector3d axis, double angle)
	{
		double c = Math.cos(angle);
		double s = Math.sin(angle);
		double u = 1.0 - c;
		
		double nx = x * (u*axis.x*axis.x + c) + y * (u*axis.x*axis.y - s*axis.z) + z * (u*axis.x*axis.z + s*axis.y);
		double ny = x * (u*axis.x*axis.y + s*axis.z) + y * (u*axis.y*axis.y + c) + z * (u*axis.y*axis.z - s*axis.x);
		double nz = x * (u*axis.x*axis.z - s*axis.y) + y * (u*axis.y*axis.z + s*axis.x) + z * (u*axis.z*axis.z + c);
		
		return new Vector3d(nx, ny, nz);
	}
	
	public Vector3d rotateAroundPoint(Vector3d point, Vector3d axis, double angle)
	{
		Vector3d dir = this.subtract(point);
		dir = dir.rotate(axis, angle);
		dir = dir.add(point);
		return dir;
	}

}
