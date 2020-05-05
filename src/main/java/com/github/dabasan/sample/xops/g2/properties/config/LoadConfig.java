package com.github.dabasan.sample.xops.g2.properties.config;

import java.io.IOException;

import com.github.dabasan.xops.properties.config.Config;
import com.github.dabasan.xops.properties.config.ConfigManipulator;

public class LoadConfig {
	public static void main(String[] args) {
		ConfigManipulator manipulator;
		try {
			manipulator = new ConfigManipulator("./Data/config.dat");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		Config config = manipulator.GetConfig();
		System.out.println(config);
	}
}
