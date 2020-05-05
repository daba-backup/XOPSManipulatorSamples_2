package com.github.dabasan.sample.xops.g2.pd1;

import static com.github.dabasan.basis.matrix.MatrixFunctions.*;
import static com.github.dabasan.basis.vector.VectorFunctions.*;

import java.io.IOException;

import com.github.dabasan.basis.matrix.Matrix;
import com.github.dabasan.xops.pd1.PD1Manipulator;

public class MatrixOperation {
	public static void main(String[] args) {
		PD1Manipulator manipulator;
		try {
			manipulator = new PD1Manipulator("./Data/point.pd1");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		Matrix translate = MGetTranslate(VGet(100.0f, 100.0f, 100.0f));
		manipulator.SetMatrix(translate);

		manipulator.Write("./Data/point_matrix.pd1");
	}
}
