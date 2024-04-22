package com.example.rollcube.linearalgebra;

import android.util.Log;


/**
 * 3-dimensional float vector
 */
public class Vector3DFloat {

	public float x;
	public float y;
	public float z;

	
	public Vector3DFloat() {
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Vector3DFloat(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}


	/**
	 * Method returns scalar product of two vectors.
	 * @param vect Vector that is producing with current vector.
	 */
	public float scalarProd(Vector3DFloat vect) {
		return x * vect.x + y * vect.y + z * vect.z;
	}

	/**
	 * Method returns vector product of two vectors as NEW(!) object.
	 * @param vect Vector that is producing with current vector.
	 */
	public Vector3DFloat vectorProd(Vector3DFloat vect) {
		float x =   this.y * vect.z - this.z * vect.y;
		float y = - this.x * vect.z + this.z * vect.x;
		float z =   this.x * vect.y - this.y * vect.x;
		return new Vector3DFloat(x, y, z);
	}

	/**
	 * Method returns diff of two vectors as NEW(!) object.
	 * @param vect Vector that is differing from current vector.
	 */
	public Vector3DFloat diff(Vector3DFloat vect) {
		return new Vector3DFloat(x - vect.x, y - vect.y, z - vect.z);
	}

	/**
	 * Method returns sum of two vectors as NEW(!) object.
	 * @param vect Vector that is summing with current vector.
	 */
	public Vector3DFloat sum(Vector3DFloat vect) {
		return new Vector3DFloat(x + vect.x, y + vect.y, z + vect.z);
	}

	/**
	 * The method multiplies current vector by a number k.
	 * @return Returns a new vector as NEW(!) object.
	 */
	public Vector3DFloat prod(float k) {
		return new Vector3DFloat(k * x, k * y, k * z);
	}

	public Vector3DFloat rotateOX(float fi) {
		Matrix3x3Float Mrot = new Matrix3x3Float(1, 0, 0,
										0,(float) Math.cos(fi), (float) -Math.sin(fi),
										0,(float) Math.sin(fi), (float) Math.cos(fi)) ;

		return Mrot.product_Ma(this);
	}

	public Vector3DFloat rotateOY(float fi) {
		Matrix3x3Float Mrot = new Matrix3x3Float((float) Math.cos(fi), 0, (float) Math.sin(fi),
														0, 1, 0,
										(float) -Math.sin(fi), 0, (float) Math.cos(fi)) ;

		return Mrot.product_Ma(this);
	}

	public Vector3DFloat rotateOZ(float fi) {
		Matrix3x3Float Mrot = new Matrix3x3Float( (float) Math.cos(fi),(float) -Math.sin(fi),0,
										(float) Math.sin(fi),(float) Math.cos(fi),0,
													0,0,1);

		return Mrot.product_Ma(this);
	}

	public Vector3DFloat rotateX(float y_sh, float fi) {
		return new Vector3DFloat (  x,
								 	(float) (y * Math.cos(fi) - z * Math.sin(fi) - y_sh * Math.cos(fi) + y_sh),
					  			 	(float) (y * Math.sin(fi) + z * Math.cos(fi) - y_sh * Math.sin(fi))
		);
	}

	public Vector3DFloat rotateY(float x_sh, float fi) {
		return new Vector3DFloat (  (float) (x * Math.cos(fi) + z * Math.sin(fi) - x_sh * Math.cos(fi) + x_sh),
									y,
									(float) (-x * Math.sin(fi) + z * Math.cos(fi) + x_sh * Math.sin(fi))
		);
	}

	public void rotateX_this(float y_sh, float fi) {
		float y_ = (float) (y * Math.cos(fi) - z * Math.sin(fi) - y_sh * Math.cos(fi) + y_sh);
		float z_ = (float) (y * Math.sin(fi) + z * Math.cos(fi) - y_sh * Math.sin(fi));
		y = y_;
		z = z_;
	}

	public void rotateY_this(float x_sh, float fi) {
		float x_ = (float) (x * Math.cos(fi) + z * Math.sin(fi) - x_sh * Math.cos(fi) + x_sh);
		float z_ = (float) (-x * Math.sin(fi) + z * Math.cos(fi) + x_sh * Math.sin(fi));
		x = x_;
		z = z_;
	}


	/**
	 * The method normalize current vector to 1.0
	 * @return Returns a new vector as NEW(!) object.
	 */
	public Vector3DFloat norm() {
		return new Vector3DFloat(x, y, z).prod(1/length());
	}

	/**
	 * Method returns length of this vector.
	 */
	public float length() {
		return (float) Math.sqrt(length2());
	}

	/**
	 * Method returns length^2 of this vector, or x^2 + y^2 + z^2 in other words.
	 */
	public float length2() {
		return scalarProd(this);
	}

	/**
	 * Method sets fields from Polar coords.
	 * @param r radius
	 * @param fi latitude
	 * @param xi longitude
	 */
	public void setFromPolar(float r, float fi, float xi) {
		x = (float) (r * Math.sin(xi) * Math.cos(fi));
		y = (float) (r * Math.sin(xi) * Math.sin(fi));
		z = (float) (r * Math.cos(xi));
	}

	/**
	 * Method prints vector in Logcat with tag "algebra".
	 * Something like "Camera position:   (1.463, 0.812, 0.294)"
	 * @param name You can name the vector.
	 */
	public void printVect(String name) {
		Log.d("algebra", name + "   (" + x + ", " + y + ", " + z + ")");
	}
	


	
	
}
