package com.example.rollcube.linearalgebra;


public class LinearAlgebra {

	/**
	 * Method projects a point on a plane and returns projected point as NEW(!) object.
	 */
	public static Vector3DFloat getPointProjectionOnPlain(PlaneFloat plain, Vector3DFloat point) {
		float x = point.x;
		float y = point.y;
		float z = point.z;

		float a = plain.n.x;
		float b = plain.n.y;
		float c = plain.n.z;

		float d = plain.r0.x;
		float e = plain.r0.y;
		float f = plain.r0.z;

		float t = (a*d - a*x + b*e - b*y + c*f - c*z) / (a*a + b*b + c*c);

		float projX = x + t*a;
		float projY = y + t*b;
		float projZ = z + t*c;

		return new Vector3DFloat(projX, projY, projZ);
	}


}
