package com.github.dabasan.sample.xops.g2.properties.xms;

import java.io.IOException;

import com.github.dabasan.xops.properties.entity.character.CharacterData;
import com.github.dabasan.xops.properties.xms.xcs.XCSManipulator;

public class LoadXCS {
	public static void main(String[] args) {
		XCSManipulator manipulator;
		try {
			manipulator = new XCSManipulator("./Data/characters.xcs");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		CharacterData[] character_data = manipulator.GetCharacterData();
		for (int i = 0; i < character_data.length; i++) {
			System.out.printf("[%d]\n", i);
			System.out.println(character_data[i]);
		}
	}
}
