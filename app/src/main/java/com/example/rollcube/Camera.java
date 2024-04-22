package com.example.rollcube;

import android.opengl.Matrix;

import com.example.rollcube.gameobjects.MainBackground;
import com.example.rollcube.gameobjects.cells.CellObject;
import com.example.rollcube.managers.MainManager;
import com.example.rollcube.linearalgebra.PlaneFloat;
import com.example.rollcube.linearalgebra.Point2DFloat;
import com.example.rollcube.linearalgebra.Vector3DFloat;


public class Camera {
	public enum AnimationZoomType { BACK, FORWARD }

	private final MainManager mainManager;

	/**
	 * Shifting of the cube relative to the camera.
	 */
	private CellObject.CellCoords cubeShifting = new CellObject.CellCoords(0, 0);

	private final AnimationZoomData animationZoomData = new AnimationZoomData();
	private final AnimationXZData animationXZData = new AnimationXZData();

	/**
	 * The point of camera rotation.
 	 */
	private Vector3DFloat center;

	/**
	 * Eye of the camera.
 	 */
	private Vector3DFloat eye;

	/**
	 * |eye - center|
	 */
	private float R;
	/**
	 * latitude
	 */
	private float fi;
	/**
	 * longitude
	 */
	private float xi;

	// Parameters of the projection plane and render volume.
	private float left;
	private float right;
	private float bottom;
	private float top;
	private float near;
	private float far;
	
	
	public Camera(MainManager mainManager, float fi, int level, float cameraRatio, int cameraZoom) {
		this.mainManager = mainManager;
		setStartParameters(fi, level, cameraRatio, cameraZoom);
		setProjectionMatrix();
		setViewMatrix();
	}



	private void setStartParameters(float fi, int level, float cameraRatio, int cameraZoom) {
		R = 3f;
		this.fi = fi;
		xi = (float) -(Math.PI / 2 - level * Math.PI / 8);

		float cameraSize = 0.11f * calcCameraSize(cameraZoom);
		left = - cameraSize;
		right = cameraSize;
		bottom = - cameraSize * cameraRatio;
		top = cameraSize * cameraRatio;
		near = 0.3f;
		far = 4f;

		center = new Vector3DFloat(
				GameBoard.CENTER_POINT.x + GameBoard.CELL_SIDE / 2,
				0f,
				GameBoard.CENTER_POINT.y + GameBoard.CELL_SIDE / 2 );
		eye = new Vector3DFloat();
		setEye();
	}

	public void setProjectionMatrix() {
		// Projection without geometry perspective.
		//Matrix.orthoM(mainManager.PROJ_matrix, 0, left, right, bottom, top, near, far);

		// Projection with geometry perspective.
		Matrix.frustumM(mainManager.PROJ_matrix, 0, left, right, bottom, top, near, far);
	}


	public void setViewMatrix() {
		Matrix.setLookAtM(mainManager.VIEW_matrix, 0, eye.x, eye.y, eye.z, center.x, center.y, center.z, 0, 1, 0);
	}

	public float calcCameraSize(int cameraZoom) {
		return 0.725f - Math.signum(cameraZoom) * (float)(cameraZoom*cameraZoom) / 200;
	}


	private void updateViewMatrix() {
		setEye();
		setViewMatrix();
	}

	private void setEye() {
		eye.x = center.x + R * (float) (Math.cos(fi) * Math.sin(xi));
		eye.y = center.y + R * (float) Math.cos(xi);
		eye.z = center.z - R * (float) (Math.sin(fi) * Math.sin(xi));
	}

	public Vector3DFloat getEye() {
		return eye;
	}

	public Vector3DFloat getCenter() {
		return center;
	}

	/**
	 * @return Method returns 4 corners of the camera rectangle.
	 */
	public Vector3DFloat[] findCorners() {
		float RfiX = (float) (-Math.sin(fi) * Math.sin(xi));
		float RfiY = 0;
		float RfiZ = (float) (-Math.cos(fi) * Math.sin(xi));

		float RxiX = (float) (Math.cos(fi) * Math.cos(xi));
		float RxiY = (float) (-Math.sin(xi));
		float RxiZ = (float) (-Math.sin(fi) * Math.cos(xi));

		Vector3DFloat Rfi_norm = new Vector3DFloat(RfiX, RfiY, RfiZ).norm();
		Vector3DFloat Rxi_norm = new Vector3DFloat(RxiX, RxiY, RxiZ).norm();

		Vector3DFloat Rfi = Rfi_norm.prod(left);
		Vector3DFloat Rxi = Rxi_norm.prod(top);

		Vector3DFloat topLeft = eye.diff(Rxi).sum(Rfi);
		Vector3DFloat bottomLeft = eye.sum(Rxi).sum(Rfi);
		Vector3DFloat topRight = eye.diff(Rxi).diff(Rfi);
		Vector3DFloat bottomRight = eye.sum(Rxi).diff(Rfi);

		return new Vector3DFloat[] {topLeft, bottomLeft, topRight, bottomRight};
	}

	/**
	 * @return Method returns plane of the camera.
	 */
	public PlaneFloat getPlane() {
		Vector3DFloat r0 = new Vector3DFloat(eye.x, eye.y, eye.z);
		Vector3DFloat n = center.diff(r0);  // n is NOT normal!
		return new PlaneFloat(r0, n);
	}

	public Vector3DFloat getN_norm() {
		return getCenter().diff(getEye()).norm();
	}



	public void setAnimationZoomParams(AnimationZoomType zoomType) {
		animationZoomData.setAnimationParams(zoomType);
	}

	public void setAnimationConnectingParams(Point2DFloat cubeCoordsFloat) {
		cubeShifting = new CellObject.CellCoords(0, 0);
		animationXZData.setConnectingAnimationParams(cubeCoordsFloat);
	}

	public void setAnimationMoveParams(CubeController.Queue.MoveDirection moveDirection, MainBackground mainBackground) {
		switch (moveDirection) {
			case LEFT:
				if (cubeShifting.n < 0) {
					animationXZData.setAnimationMoveParams(moveDirection);
					mainBackground.move(moveDirection);
				} else {cubeShifting.n--;}
				break;
			case RIGHT:
				if (cubeShifting.n > 0) {
					animationXZData.setAnimationMoveParams(moveDirection);
					mainBackground.move(moveDirection);
				} else {cubeShifting.n++;}
				break;
			case BACK:
				if (cubeShifting.m > 0) {
					animationXZData.setAnimationMoveParams(moveDirection);
					mainBackground.move(moveDirection);
				} else {cubeShifting.m++;}
				break;
			case FORWARD:
				if (cubeShifting.m < 0) {
					animationXZData.setAnimationMoveParams(moveDirection);
					mainBackground.move(moveDirection);
				} else {cubeShifting.m--;}
				break;
		}
	}

	public void moveAnimation() {
		animationZoomData.moveAnimation();
		animationXZData.moveAnimation();
	}

	public boolean isAnimating() {
		return animationXZData.isAnimating ||
				animationZoomData.isAnimating;
	}



	public abstract static class AnimationData extends Animation {
		protected float frameNumber;

		@Override
		public abstract void moveAnimation();
	}


	public class AnimationZoomData extends AnimationData {
		private AnimationZoomType zoomType;
		private final float zoomCoeff = 0.97f;

		public AnimationZoomData() {
			frameNumber = 10;
		}

		@Override
		public void moveAnimation() {
			if (isAnimating) {
				if (frameCounter < frameNumber) {
					switch(zoomType) {
						case FORWARD:
							left *= zoomCoeff;
							right *= zoomCoeff;
							top *= zoomCoeff;
							bottom *= zoomCoeff;
							break;
						case BACK:
							left /= zoomCoeff;
							right /= zoomCoeff;
							top /= zoomCoeff;
							bottom /= zoomCoeff;
							break;
					}
					frameCounter++;
					setProjectionMatrix();
				}
				else {
					isAnimating = false;
				}
			}
		}

		public void setAnimationParams(AnimationZoomType zoomType) {
			if (!isAnimating) {
				frameCounter = 0;
				this.zoomType = zoomType;
				isAnimating = true;
			}
		}
	}


	public class AnimationXZData extends AnimationData {
		private float signX = 0;
		private float signZ = 0;

		private final float dt;
		private float Sx = 0;
		private float Sz = 0;
		private float dSx(float t) {
			return (3f/4) * (1f - t*t) * dt * Sx;
		}
		private float dSz(float t) {
			return (3f/4) * (1f - t*t) * dt * Sz;
		}

		public AnimationXZData() {
			frameNumber = 15;
			dt = 2f / frameNumber;
		}

		@Override
		public void moveAnimation() {
			if (isAnimating) {
				if (frameCounter < frameNumber) {
					float dSx = dSx(- 1 + dt * frameCounter);
					float dSz = dSz(- 1 + dt * frameCounter);
					center.x += signX * dSx;
					center.z += signZ * dSz;
					frameCounter++;
					updateViewMatrix();
				}
				else {
					isAnimating = false;
				}
			}
		}

		public void setConnectingAnimationParams(Point2DFloat cubeCoordsFloat) {
			if (!isAnimating) {
				frameCounter = 0;
				signX = 1;
				signZ = 1;
				Sx = cubeCoordsFloat.x - center.x;
				Sz = cubeCoordsFloat.y - center.z;
				isAnimating = true;
			}
		}

		public void setAnimationMoveParams(CubeController.Queue.MoveDirection moveDirection) {
			if (!isAnimating) {
				Sx = GameBoard.CELL_SIDE;
				Sz = GameBoard.CELL_SIDE;
				switch (moveDirection) {
					case LEFT:
						signX = - 1;
						signZ = 0;
						break;
					case RIGHT:
						signX = 1;
						signZ = 0;
						break;
					case FORWARD:
						signX = 0;
						signZ = - 1;
						break;
					case BACK:
						signX = 0;
						signZ = 1;
						break;
				}
				frameCounter = 0;

				isAnimating = true;
			}
		}

	}



}
