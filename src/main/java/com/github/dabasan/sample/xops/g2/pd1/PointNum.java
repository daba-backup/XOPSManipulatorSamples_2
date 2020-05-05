package com.github.dabasan.sample.xops.g2.pd1;

import java.io.IOException;

import com.github.dabasan.xops.pd1.PD1Manipulator;

public class PointNum {
	public static void main(String[] args) {
		PD1Manipulator manipulator;
		try {
			manipulator = new PD1Manipulator("./Data/point.pd1");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		int point_num = manipulator.GetPointNum();
		int character_num = manipulator.GetPointNum(1)
				+ manipulator.GetPointNum(6);
		System.out.printf("point_num=%d character_num=%d\n", point_num,
				character_num);
	}
}
