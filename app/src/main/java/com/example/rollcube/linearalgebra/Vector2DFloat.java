package com.example.rollcube.linearalgebra;

import android.util.Log;


/**
 *  2-dimensional float vector.
 */
public class Vector2DFloat {
	
	public float x;
	public float y;


	public Vector2DFloat() {
		x = 0f;
		y = 0f;
	}

	public Vector2DFloat(Vector2DFloat vect) {
		this.x = vect.x;
		this.y = vect.y;
	}
	
	public Vector2DFloat(float x, float y) {
		this.x = x;
		this.y = y;
	}



	/**
	 * Method returns sum of two vectors.
	 * @return Returns a new vector as NEW(!) object.
	 */
	public Vector2DFloat sum(Vector2DFloat vect) {
		return new Vector2DFloat(x + vect.x, y + vect.y);
	}


	/**
	 * Method returns difference of two vectors: currentVect - vect.
	 * @param vect Vector that is subtracted from current vector.
	 * @return Returns a new vector as NEW(!) object.
	 */
	public Vector2DFloat diff(Vector2DFloat vect) {
		return new Vector2DFloat(x - vect.x, y - vect.y);
	}


	/**
	 * The method normalize current vector to 1.0
	 * @return Returns a new vector as NEW(!) object.
	 */
	public Vector2DFloat norm() {
		return new Vector2DFloat(x, y).prod(1/length());
	}


	/**
	 * The method multiplies current vector by a number k.
	 * @return Returns a new vector as NEW(!) object.
	 */
	public Vector2DFloat prod(float k) {
		return new Vector2DFloat(k * x, k * y);
	}


	/**
	 * Method returns scalar product of two vectors.
	 * @param vect Vector that is producted with current vector.
	 */
	public float scalarProd(Vector2DFloat vect) {
		return x * vect.x + y * vect.y;
	}


	/**
	 * Method returns length of this vector.
	 */
	public float length() {
		return (float) Math.sqrt(length2());
	}


	/**
	 * Method returns length^2 of this vector, or x^2 + y^2 in other words.
	 */
	public float length2() {
		return scalarProd(this);
	}


	/**
	 * Method prints vector in Logcat with tag "algebra".
	 * Something like "Cube Coords:   (1.463, 0.812)"
	 * @param name You can name the vector.
	 */
	public void printVect(String name) {
		Log.d("algebra", name + ":   (" + x + ", " + y + ")");
	}
	
	
	

}
