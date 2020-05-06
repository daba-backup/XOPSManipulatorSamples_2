package com.github.dabasan.sample.xops.g2.opengl.first_person;

import com.github.dabasan.joglf.gl.window.JOGLFWindowInterface;
import com.github.dabasan.joglf.gl.window.WindowCommonInfo;

public class FirstPersonMain {
	public static void main(String[] args) {
		new FirstPersonMain();
	}
	public FirstPersonMain() {
		WindowCommonInfo.SetFPS(60);

		JOGLFWindowInterface window = new FirstPersonViewer();
		window.SetTitle("First-person Viewer");
	}
}
