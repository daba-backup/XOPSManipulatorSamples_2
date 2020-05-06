package com.github.dabasan.sample.xops.g2.opengl.first_person;

import static com.github.dabasan.basis.vector.VectorFunctions.*;

import javax.vecmath.Vector3f;

import com.bulletphysics.collision.dispatch.PairCachingGhostObject;
import com.bulletphysics.collision.shapes.ConvexShape;
import com.bulletphysics.dynamics.character.KinematicCharacterController;
import com.bulletphysics.linearmath.Transform;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.joglf.gl.front.CameraFront;
import com.github.dabasan.joglf.gl.window.WindowCommonInfo;
import com.github.dabasan.tool.MathFunctions;

class KinematicCamera extends KinematicCharacterController {
	private float v_rotate;
	private float h_rotate;

	private float translate_speed;// m/frame
	private float rotate_speed;// rad/frame

	private float y_offset;

	private static final float VROT_MIN = MathFunctions.DegToRad(-80.0f);
	private static final float VROT_MAX = MathFunctions.DegToRad(80.0f);

	public KinematicCamera(PairCachingGhostObject ghost_object,
			ConvexShape convex_shape, float step_height) {
		super(ghost_object, convex_shape, step_height);

		Transform transform = new Transform();
		ghost_object.getWorldTransform(transform);

		Vector camera_position = VGet(transform.origin.x, transform.origin.y,
				transform.origin.z);
		Vector initial_target = VGet(0.0f, 0.0f, 0.0f);
		Vector direction = VSub(initial_target, camera_position);
		direction = VNorm(direction);
		v_rotate = VAngleV(direction);
		h_rotate = VAngleH(direction);

		int fps = WindowCommonInfo.GetFPS();
		translate_speed = 0.8f / fps;
		rotate_speed = 0.5f / fps;

		y_offset = 0.0f;
	}

	/**
	 * 移動の速さを設定する。
	 * 
	 * @param translate_speed
	 *            移動の速さ(m/s)
	 */
	public void SetTranslateSpeed(float translate_speed_per_sec) {
		int fps = WindowCommonInfo.GetFPS();
		this.translate_speed = translate_speed_per_sec / fps;
	}
	/**
	 * 回転の速さを設定する。
	 * 
	 * @param rotate_speed
	 *            回転の速さ(rad/s)
	 */
	public void SetRotateSpeed(float rotate_speed_per_sec) {
		int fps = WindowCommonInfo.GetFPS();
		this.rotate_speed = rotate_speed_per_sec / fps;
	}

	public void SetYOffset(float y_offset) {
		this.y_offset = y_offset;
	}

	public void Translate(int front, int back, int right, int left, int jump) {
		Vector translate = VGet(0.0f, 0.0f, 0.0f);

		Vector front_vec = VGetFromAngles(v_rotate, h_rotate);
		Vector right_vec = VCross(front_vec, VGet(0.0f, 1.0f, 0.0f));
		right_vec = VNorm(right_vec);

		if (front > 0) {
			translate = VAdd(translate, front_vec);
		}
		if (back > 0) {
			translate = VAdd(translate, VScale(front_vec, -1.0f));
		}
		if (right > 0) {
			translate = VAdd(translate, right_vec);
		}
		if (left > 0) {
			translate = VAdd(translate, VScale(right_vec, -1.0f));
		}

		if (VSize(translate) > 1.0E-8f) {
			translate = VNorm(translate);
			translate = VScale(translate, translate_speed);
			this.setWalkDirection(new Vector3f(translate.GetX(),
					translate.GetY(), translate.GetZ()));
		} else {
			this.setWalkDirection(new Vector3f(0.0f, 0.0f, 0.0f));
		}

		if (jump > 0) {
			this.jump();
		}
	}
	public void Rotate(int diff_x, int diff_y) {
		h_rotate += rotate_speed * (-diff_x);
		v_rotate += rotate_speed * (-diff_y);

		if (h_rotate > Math.PI) {
			h_rotate -= 2.0f * Math.PI;
		} else if (h_rotate < -Math.PI) {
			h_rotate += 2.0f * Math.PI;
		}

		if (v_rotate < VROT_MIN) {
			v_rotate = VROT_MIN;
		} else if (v_rotate > VROT_MAX) {
			v_rotate = VROT_MAX;
		}
	}

	public void Update() {
		Transform transform = new Transform();
		this.ghostObject.getWorldTransform(transform);

		Vector camera_position = VGet(transform.origin.x,
				transform.origin.y + y_offset, transform.origin.z);

		CameraFront.SetCameraPositionAndAngle(camera_position, v_rotate,
				h_rotate, 0.0f);
	}
}
