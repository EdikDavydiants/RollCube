package com.example.rollcube.linearalgebra;

/**
 * Plane with normal and some point on the one.
 */
public class PlaneFloat {

	public Vector3DFloat r0;  // Any point on the plane
	public Vector3DFloat n;   // Normal to the plane
	
	
	public PlaneFloat(Vector3DFloat r0, Vector3DFloat n) {
		this.r0 = r0;
		this.n = n;
	}



	public float[] getPlaneEquation() {
		boolean cond1 = n.x == 0;
		boolean cond2 = n.y == 0;
		boolean cond3 = n.z == 0;
		if (cond1 && cond2 && cond3) {
			return null;
		}
		else {
			float rightPart = r0.scalarProd(n);
			return new float[] {n.x, n.y, n.z, rightPart};
		}
	}
	
	
	public Vector3DFloat findProjection(Vector3DFloat a) {
		Vector3DFloat vectorDiff = r0.diff(a);
		Vector3DFloat normVect = n.norm();
		float k = vectorDiff.scalarProd(normVect);
		return a.sum(normVect.prod(k));
	}
	


	
}
