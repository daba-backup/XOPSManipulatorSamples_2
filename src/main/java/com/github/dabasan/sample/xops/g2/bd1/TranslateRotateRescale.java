package com.github.dabasan.sample.xops.g2.bd1;

import static com.github.dabasan.basis.vector.VectorFunctions.*;

import java.io.IOException;

import com.github.dabasan.tool.MathFunctions;
import com.github.dabasan.xops.bd1.BD1Manipulator;

public class TranslateRotateRescale {
	public static void main(String[] args) {
		BD1Manipulator manipulator;
		try {
			manipulator = new BD1Manipulator("./Data/map.bd1");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		float rad = MathFunctions.DegToRad(30.0f);

		manipulator.Translate(VGet(100.0f, 100.0f, 100.0f));
		manipulator.Rotate(VGet(rad, rad, rad));
		manipulator.Rescale(VGet(1.5f, 1.0f, 1.5f));

		manipulator.WriteAsBD1("./Data/map_trr.bd1");
	}
}
