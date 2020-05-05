package com.github.dabasan.sample.xops.g2.bd1;

import static com.github.dabasan.basis.matrix.MatrixFunctions.*;
import static com.github.dabasan.basis.vector.VectorFunctions.*;

import java.io.IOException;

import com.github.dabasan.basis.matrix.Matrix;
import com.github.dabasan.xops.bd1.BD1Manipulator;

public class MatrixOperation {
	public static void main(String[] args) {
		BD1Manipulator manipulator;
		try {
			manipulator = new BD1Manipulator("./Data/map.bd1");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		Matrix translate = MGetTranslate(VGet(100.0f, 100.0f, 100.0f));
		manipulator.SetMatrix(translate);
		manipulator.WriteAsBD1("./Data/map_matrix.bd1");
	}
}
