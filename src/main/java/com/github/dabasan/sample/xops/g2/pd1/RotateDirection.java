package com.github.dabasan.sample.xops.g2.pd1;

import java.io.IOException;

import com.github.dabasan.tool.MathFunctions;
import com.github.dabasan.xops.pd1.PD1Manipulator;

public class RotateDirection {
	public static void main(String[] args) {
		PD1Manipulator manipulator;
		try {
			manipulator = new PD1Manipulator("./Data/point.pd1");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		float rad = MathFunctions.DegToRad(45.0f);
		manipulator.RotateDirection(rad);

		manipulator.Write("./Data/point_direction.pd1");
	}
}
