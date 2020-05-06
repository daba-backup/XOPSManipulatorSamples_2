package com.github.dabasan.sample.xops.g2.opengl.first_person;

import static com.github.dabasan.basis.vector.VectorFunctions.*;

import java.util.List;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.dispatch.GhostPairCallback;
import com.bulletphysics.collision.dispatch.PairCachingGhostObject;
import com.bulletphysics.collision.shapes.CapsuleShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.ConvexShape;
import com.bulletphysics.collision.shapes.TriangleShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.Transform;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.joglf.gl.draw.DrawFunctions3D;
import com.github.dabasan.joglf.gl.front.CameraFront;
import com.github.dabasan.joglf.gl.front.FogFront;
import com.github.dabasan.joglf.gl.input.keyboard.KeyboardEnum;
import com.github.dabasan.joglf.gl.input.mouse.MouseEnum;
import com.github.dabasan.joglf.gl.model.Model3DFunctions;
import com.github.dabasan.joglf.gl.shape.Triangle;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;
import com.github.dabasan.joglf.gl.window.WindowCommonInfo;
import com.github.dabasan.tool.MathFunctions;

class FirstPersonViewer extends JOGLFWindow {
	private float seconds_per_frame;

	private DiscreteDynamicsWorld dynamics_world;
	private KinematicCamera camera;

	private int model_handle;

	@Override
	public void Init() {
		seconds_per_frame = 1.0f / WindowCommonInfo.GetFPS();

		// DynamicsWorldの作成
		BroadphaseInterface broadphase = new DbvtBroadphase();
		DefaultCollisionConfiguration collision_configuration = new DefaultCollisionConfiguration();
		CollisionDispatcher dispatcher = new CollisionDispatcher(
				collision_configuration);

		SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();

		dynamics_world = new DiscreteDynamicsWorld(dispatcher, broadphase,
				solver, collision_configuration);
		dynamics_world.setGravity(new Vector3f(0.0f, -9.81f, 0.0f));

		// マップの作成
		final float MODEL_SCALE = 1.7f / 20.0f;
		model_handle = Model3DFunctions
				.LoadModel("./Data/Model/BD1/map0/temp.bd1");
		Model3DFunctions.RescaleModel(model_handle,
				VGet(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE));

		List<Triangle> faces = Model3DFunctions.GetModelFaces(model_handle);
		for (Triangle face : faces) {
			Vector3f[] vertex_positions = new Vector3f[3];
			for (int i = 0; i < 3; i++) {
				Vector vtemp = face.GetVertex(i).GetPos();
				vertex_positions[i] = new Vector3f(vtemp.GetX(), vtemp.GetY(),
						vtemp.GetZ());
			}

			CollisionShape collision_shape = new TriangleShape(
					vertex_positions[0], vertex_positions[1],
					vertex_positions[2]);
			DefaultMotionState motion_state = new DefaultMotionState(
					new Transform(
							new Matrix4f(new Quat4f(0.0f, 0.0f, 0.0f, 1.0f),
									new Vector3f(0.0f, 0.0f, 0.0f), 1.0f)));
			RigidBodyConstructionInfo ci = new RigidBodyConstructionInfo(0.0f,
					motion_state, collision_shape,
					new Vector3f(0.0f, 0.0f, 0.0f));

			RigidBody rb = new RigidBody(ci);
			rb.setRestitution(0.0f);

			dynamics_world.addRigidBody(rb);
		}

		// カメラの作成
		final float CAMERA_WIDTH = 0.3f;
		final float CAMERA_HEIGHT = 1.7f;
		final float CAMERA_HALF_HEIGHT = CAMERA_HEIGHT * 0.5f;

		final Vector camera_initial_position = VGet(-10.0f, 50.0f, -10.0f);

		ConvexShape camera_shape = new CapsuleShape(CAMERA_WIDTH,
				CAMERA_HEIGHT);
		PairCachingGhostObject ghost_object = new PairCachingGhostObject();
		ghost_object.setWorldTransform(
				new Transform(new Matrix4f(new Quat4f(0.0f, 0.0f, 0.0f, 1.0f),
						new Vector3f(camera_initial_position.GetX(),
								camera_initial_position.GetY(),
								camera_initial_position.GetZ()),
						1.0f)));
		dynamics_world.getPairCache()
				.setInternalGhostPairCallback(new GhostPairCallback());
		ghost_object.setCollisionShape(camera_shape);
		camera = new KinematicCamera(ghost_object, camera_shape, 0.3f);
		dynamics_world.addCollisionObject(ghost_object);
		dynamics_world.addAction(camera);

		camera.setGravity(0.3f);
		camera.setJumpSpeed(1.5f);
		camera.setMaxSlope(MathFunctions.DegToRad(50.0f));
		camera.SetYOffset(CAMERA_HALF_HEIGHT * 0.8f);

		CameraFront.SetCameraNearFar(0.3f, 100.0f);
		FogFront.SetFogStartEnd(50.0f, 100.0f);
	}

	@Override
	public void Update() {
		int w_pressing_count = this
				.GetKeyboardPressingCount(KeyboardEnum.KEY_W);
		int s_pressing_count = this
				.GetKeyboardPressingCount(KeyboardEnum.KEY_S);
		int d_pressing_count = this
				.GetKeyboardPressingCount(KeyboardEnum.KEY_D);
		int a_pressing_count = this
				.GetKeyboardPressingCount(KeyboardEnum.KEY_A);
		int space_pressing_count = this
				.GetKeyboardPressingCount(KeyboardEnum.KEY_SPACE);
		camera.Translate(w_pressing_count, s_pressing_count, d_pressing_count,
				a_pressing_count, space_pressing_count);

		if (this.GetMousePressingCount(MouseEnum.MOUSE_MIDDLE) > 0) {
			int diff_x = this.GetCursorDiffX();
			int diff_y = this.GetCursorDiffY();

			camera.Rotate(diff_x, diff_y);
		}

		// シミュレーションの実行
		dynamics_world.stepSimulation(1.0f, 10, seconds_per_frame);

		// カメラの更新
		camera.Update();
	}

	@Override
	public void Draw() {
		Model3DFunctions.DrawModel(model_handle);
		DrawFunctions3D.DrawAxes(100.0f);
	}
}
