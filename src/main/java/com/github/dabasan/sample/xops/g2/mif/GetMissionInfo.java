package com.github.dabasan.sample.xops.g2.mif;

import java.io.IOException;

import com.github.dabasan.xops.mif.MIFManipulator;
import com.github.dabasan.xops.mif.MissionInfo;

public class GetMissionInfo {
	public static void main(String[] args) {
		MIFManipulator manipulator;
		try {
			manipulator = new MIFManipulator("./Data/mission.mif", "Shift-JIS");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		MissionInfo mif = manipulator.GetMissionInfo();
		System.out.println(mif);
	}
}
