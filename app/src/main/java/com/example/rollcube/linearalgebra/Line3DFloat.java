package com.example.rollcube.linearalgebra;


/**
 * Line with parallel vector and some point on the one.
 */
public class Line3DFloat {

	public Vector3DFloat r0; // Any point on the line
	public Vector3DFloat a;  // Any parallel vector
	

	Line3DFloat(Vector3DFloat r0, Vector3DFloat a) {
		this.r0 = r0;
		this.a = a;
	}



	/**
	 * Returns array of coefficients in equation system.
	 * a00 * x + a01 * y + a02 * z = a03,
	 * a10 * x + a11 * y + a12 * z = a13.
	 */
	public float[][] getLineEquations() {
		
		float[][] equas = new float[2][4];
		
		equas[0][0] = a.z;
		equas[0][1] = 0;
		equas[0][2] = -a.x;
		equas[0][3] = r0.x * a.z - r0.z * a.x;
		
		equas[1][0] = 0;
		equas[1][1] = a.z;
		equas[1][2] = -a.y;
		equas[1][3] = r0.y * a.z - r0.z * a.y;
		
		return equas;
	}
	
	
	



	
}
