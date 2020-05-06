package com.github.dabasan.sample.xops.g2.opengl.viewer;

import static com.github.dabasan.basis.coloru8.ColorU8Functions.*;
import static com.github.dabasan.basis.vector.VectorFunctions.*;

import java.io.IOException;
import java.util.List;

import com.github.dabasan.basis.coloru8.ColorU8;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.joglf.gl.draw.DrawFunctions3D;
import com.github.dabasan.joglf.gl.drawer.DynamicSegmentsDrawer;
import com.github.dabasan.joglf.gl.input.keyboard.KeyboardEnum;
import com.github.dabasan.joglf.gl.input.mouse.MouseEnum;
import com.github.dabasan.joglf.gl.model.Model3DFunctions;
import com.github.dabasan.joglf.gl.shape.Vertex3D;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;
import com.github.dabasan.tool.MathFunctions;
import com.github.dabasan.xops.pd1.PD1Manipulator;
import com.github.dabasan.xops.pd1.PD1Point;

//マップとポイントを描画する。
class Viewer2 extends JOGLFWindow {
	private FreeCamera camera;
	private int model_handle;

	private DynamicSegmentsDrawer drawer;

	@Override
	public void Init() {
		// カメラの作成
		camera = new FreeCamera();
		camera.SetPosition(VGet(50.0f, 50.0f, 50.0f));

		final float INITIAL_VROT = MathFunctions.DegToRad(-30.0f);
		final float INITIAL_HROT = MathFunctions.DegToRad(180.0f);
		camera.SetRotation(INITIAL_VROT, INITIAL_HROT);

		// マップの読み込み
		final float MODEL_SCALE = 1.7f / 20.0f;
		model_handle = Model3DFunctions
				.LoadModel("./Data/Model/BD1/map2/temp.bd1");
		Model3DFunctions.RescaleModel(model_handle,
				VGet(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE));

		// ポイントの読み込み
		PD1Manipulator manipulator;
		try {
			manipulator = new PD1Manipulator("./Data/Model/BD1/map2/ext.pd1");
		} catch (IOException e) {
			e.printStackTrace();
			this.CloseWindow();
			return;
		}
		manipulator.Rescale(VGet(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE));
		manipulator.InvertZ();

		List<PD1Point> points = manipulator.GetPoints();

		// ポイントを線分で表示するための準備
		drawer = new DynamicSegmentsDrawer();

		final float SEGMENT_HALF_LENGTH = 1.0f;
		final ColorU8 SEGMENT_COLOR = GetColorU8(0.0f, 1.0f, 1.0f, 1.0f);

		int count = 0;
		for (PD1Point point : points) {
			Vector center = point.GetPosition();

			Vector[] positions = new Vector[6];
			// X軸に平行な線分
			positions[0] = VAdd(center, VGet(-SEGMENT_HALF_LENGTH, 0.0f, 0.0f));
			positions[1] = VAdd(center, VGet(SEGMENT_HALF_LENGTH, 0.0f, 0.0f));
			// Y軸に平行な線分
			positions[2] = VAdd(center, VGet(0.0f, -SEGMENT_HALF_LENGTH, 0.0f));
			positions[3] = VAdd(center, VGet(0.0f, SEGMENT_HALF_LENGTH, 0.0f));
			// Z軸に平行な線分
			positions[4] = VAdd(center, VGet(0.0f, 0.0f, -SEGMENT_HALF_LENGTH));
			positions[5] = VAdd(center, VGet(0.0f, 0.0f, SEGMENT_HALF_LENGTH));

			Vertex3D[] vertices = new Vertex3D[6];
			for (int i = 0; i < 6; i++) {
				vertices[i] = new Vertex3D();
				vertices[i].SetPos(positions[i]);
				vertices[i].SetDif(SEGMENT_COLOR);
			}

			drawer.AddSegment(count, vertices[0], vertices[1]);
			drawer.AddSegment(count + 1, vertices[2], vertices[3]);
			drawer.AddSegment(count + 2, vertices[4], vertices[5]);
			count += 3;
		}
		drawer.UpdateBuffers();
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
		camera.Translate(w_pressing_count, s_pressing_count, d_pressing_count,
				a_pressing_count);

		if (this.GetMousePressingCount(MouseEnum.MOUSE_MIDDLE) > 0) {
			int diff_x = this.GetCursorDiffX();
			int diff_y = this.GetCursorDiffY();

			camera.Rotate(diff_x, diff_y);
		}

		camera.Update();
	}

	@Override
	public void Draw() {
		Model3DFunctions.DrawModel(model_handle);
		DrawFunctions3D.DrawAxes(100.0f);
		drawer.Draw();
	}
}
