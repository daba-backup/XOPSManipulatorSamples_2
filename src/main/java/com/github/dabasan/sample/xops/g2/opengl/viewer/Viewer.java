package com.github.dabasan.sample.xops.g2.opengl.viewer;

import static com.github.dabasan.basis.vector.VectorFunctions.*;

import com.github.dabasan.joglf.gl.draw.DrawFunctions3D;
import com.github.dabasan.joglf.gl.input.keyboard.KeyboardEnum;
import com.github.dabasan.joglf.gl.input.mouse.MouseEnum;
import com.github.dabasan.joglf.gl.model.Model3DFunctions;
import com.github.dabasan.joglf.gl.window.JOGLFWindow;
import com.github.dabasan.tool.MathFunctions;

//マップのみを描画する。
class Viewer extends JOGLFWindow {
	private FreeCamera camera;

	private int model_handle;

	@Override
	public void Init() {
		camera = new FreeCamera();
		camera.SetPosition(VGet(50.0f, 50.0f, 50.0f));

		final float INITIAL_VROT = MathFunctions.DegToRad(-30.0f);
		final float INITIAL_HROT = MathFunctions.DegToRad(180.0f);
		camera.SetRotation(INITIAL_VROT, INITIAL_HROT);

		final float MODEL_SCALE = 1.7f / 20.0f;
		model_handle = Model3DFunctions
				.LoadModel("./Data/Model/BD1/map2/temp.bd1");
		Model3DFunctions.RescaleModel(model_handle,
				VGet(MODEL_SCALE, MODEL_SCALE, MODEL_SCALE));
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
	}
}
