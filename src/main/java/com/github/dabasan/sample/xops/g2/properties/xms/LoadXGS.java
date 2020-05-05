package com.github.dabasan.sample.xops.g2.properties.xms;

import java.io.IOException;

import com.github.dabasan.xops.properties.entity.weapon.WeaponData;
import com.github.dabasan.xops.properties.xms.xgs.XGSManipulator;

public class LoadXGS {
	public static void main(String[] args) {
		XGSManipulator manipulator;
		try {
			manipulator = new XGSManipulator("./Data/weapons.xgs");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		WeaponData[] weapon_data = manipulator.GetWeaponData();
		for (int i = 0; i < weapon_data.length; i++) {
			System.out.printf("[%d]\n", i);
			System.out.println(weapon_data[i]);
		}
	}
}
