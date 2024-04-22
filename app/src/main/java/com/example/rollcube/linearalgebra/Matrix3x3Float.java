package com.example.rollcube.linearalgebra;

import android.util.Log;


/**
 * Float 3x3 matrix.
 */
public class Matrix3x3Float {

	public float[][] m;


	/**
	 * Zero-matrix.
	 */
	public Matrix3x3Float() {
		m = new float[3][3];
	}

	/**
	 * Sets vectors as rows.
	 */
	public Matrix3x3Float(Vector3DFloat a0, Vector3DFloat a1, Vector3DFloat a2) {
		m = new float[][] {
							{a0.x, a0.y, a0.z},
							{a1.x, a1.y, a1.z},
							{a2.x, a2.y, a2.z}
						  };
	}
	
	public Matrix3x3Float(float a00, float a01, float a02,
						  float a10, float a11, float a12,
						  float a20, float a21, float a22 ) {
		m = new float[][] {
							{a00, a01, a02},
							{a10, a11, a12},
							{a20, a21, a22}
						  };
	}



	/**
	 * Method calculate determinant of the matrix.
	 */
	public float det() {
		float det;
		det =     m[0][0] * (m[1][1] * m[2][2] - m[1][2] * m[2][1]) +
			  	- m[0][1] * (m[1][0] * m[2][2] - m[1][2] * m[2][0]) +
			  	  m[0][2] * (m[1][0] * m[2][1] - m[2][0] * m[1][1]);
		return det;
	}

	/**
	 * Method product two matrix and returns matrix as NEW(!) object.
	 */
	public Matrix3x3Float product_AxB(Matrix3x3Float B) {
		Matrix3x3Float C = new Matrix3x3Float();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				float Cij = m[i][0] * B.m[0][j] + m[i][1] * B.m[1][j] + m[i][2] * B.m[2][j];
				C.m[i][j] = Cij;
			}
		}
		return C;
	}

	/**
	 * Method product matrix on the float vector and return vector as a NEW(!) object.
	 */
	public Vector3DFloat product_Ma(Vector3DFloat a) {
		float b0 = m[0][0] * a.x + m[0][1] * a.y + m[0][2] * a.z;
		float b1 = m[1][0] * a.x + m[1][1] * a.y + m[1][2] * a.z;
		float b2 = m[2][0] * a.x + m[2][1] * a.y + m[2][2] * a.z;
		return new Vector3DFloat(b0, b1, b2);
	}

	/**
	 * Sum of two matrix.
	 * @return Returns matrix as a NEW(!) object.
	 */
	public Matrix3x3Float matrixSum(Matrix3x3Float B) {
		Matrix3x3Float C = new Matrix3x3Float();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				float Cij = m[i][j] + B.m[i][j];
				C.m[i][j] = Cij;
			}
		}
		return C;
	}

	/**
	 * Method prints matrix in Logcat with tag "algebra".
	 */
	public void printMatrix() {
		Log.d("algebra", m[0][0] + " ,\t" + m[0][1] + " ,\t" + m[0][2] + "\n" +
								  m[1][0] + " ,\t" + m[1][1] + " ,\t" + m[1][2] + "\n" +
								  m[2][0] + " ,\t" + m[2][1] + " ,\t" + m[2][2] + "\n");
	}


}
