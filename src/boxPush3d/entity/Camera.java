package boxPush3d.entity;

import boxPush3d.global.Objects;
import boxPush3d.utility.Vector3d;

public class Camera extends Entity {
	
	public double height;
	
	public Vector3d position = new Vector3d(-2, 2, -2);
	public Vector3d forward = new Vector3d(1, -0.5, 1).normalized();
	public Vector3d up = new Vector3d(1, 2, 1).normalized();
	
	private double[] projection = new double[16];
	private double[] view       = new double[16];
	private double[] result     = new double[16];
	
	
	//private double nearClippingPlane = 0.1;
	//private double farClippingPlane = 500;
	
	public Camera(double height)
	{
		this.height = height;
	}
	
	public double[] getProjectionMatrix()
	{
		
	    projection[0] = 1/(height * Objects.window.getAspectRatio());
	    projection[1] = 0;
	    projection[2] = 0;
	    projection[3] = 0;
	    
	    projection[4] = 0;
	    projection[5] = 1/height;
	    projection[6] = 0;
	    projection[7] = 0;
	    
	    projection[8]  = 0;
	    projection[9]  = 0;
	    projection[10] = -0.01;
	    projection[11] = 0;
	    
	    projection[12] = 0;
	    projection[13] = 0;
	    projection[14] = 0;
	    projection[15] = 1;
		
	    //inverted view matrix? might not be correct, janky math
	    view[0]  = (up.y*forward.z - up.z*forward.y);
	    view[4]  = (up.z*forward.x - up.x*forward.z);
	    view[8]  = (up.x*forward.y - up.y*forward.x);
	    view[12] = ((view[0] ) * -position.x + (view[4]) * -position.y + (view[8]) * -position.z);
	    
	    view[1]  = up.x;
	    view[5]  = up.y;
	    view[9]  = up.z;
	    view[13] = -(up.x * position.x + up.y * position.y + up.z * position.z);
	    
	    view[2]  = -forward.x;
	    view[6]  = -forward.y; 
	    view[10] = -forward.z; 
	    view[14] = (forward.x * position.x + forward.y * position.y + forward.z * position.z); 
	    
	    view[15] = 1;
	    
	    //multiply projection and view matrix
	    for(int i = 0; i < 4; i++)
	    {
	    	for(int j = 0; j < 4; j++)
		    {
	    		result[i + j*4] = 0;
	    		for(int k = 0; k < 4; k++)
	    	    {
	    	    	result[i + j*4] += projection[i + k*4] * view[k + j*4];
	    	    }
		    }
	    }
	    
		return result;
	}
}
