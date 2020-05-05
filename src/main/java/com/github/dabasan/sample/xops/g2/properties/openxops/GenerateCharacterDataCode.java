package com.github.dabasan.sample.xops.g2.properties.openxops;

import java.io.IOException;
import java.util.List;

import com.github.dabasan.tool.FileFunctions;
import com.github.dabasan.xops.properties.entity.character.CharacterData;
import com.github.dabasan.xops.properties.openxops.CharacterDataCodeOutputter;
import com.github.dabasan.xops.properties.xms.xcs.XCSManipulator;

public class GenerateCharacterDataCode {
	public static void main(String[] args) {
		XCSManipulator manipulator;
		CharacterDataCodeOutputter outputter;

		try {
			manipulator = new XCSManipulator("./Data/characters.xcs");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		CharacterData[] character_data = manipulator.GetCharacterData();
		outputter = new CharacterDataCodeOutputter(character_data);
		List<String> code = outputter.GetCode();

		try {
			FileFunctions.CreateTextFile("./Data/character_code.txt", "UTF-8",
					code);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
}
