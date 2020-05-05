package com.github.dabasan.sample.xops.g2.properties.xms;

import java.io.IOException;

import com.github.dabasan.xops.properties.entity.weapon.WeaponData;
import com.github.dabasan.xops.properties.xms.ids.IDSManipulator;

public class LoadIDS {
	public static void main(String[] args) {
		IDSManipulator manipulator;
		try {
			manipulator = new IDSManipulator("./Data/mp5.ids");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		WeaponData weapon_data = manipulator.GetWeaponData();
		System.out.println(weapon_data);
	}
}
