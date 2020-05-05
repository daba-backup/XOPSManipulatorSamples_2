package com.github.dabasan.sample.xops.g2.bd1;

import java.io.IOException;

import com.github.dabasan.xops.bd1.BD1Manipulator;

public class InvertZ {
	public static void main(String[] args) {
		BD1Manipulator manipulator;
		try {
			manipulator = new BD1Manipulator("./Data/map.bd1");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		manipulator.InvertZ();
		manipulator.WriteAsBD1("./Data/map_inverted.bd1");
	}
}
