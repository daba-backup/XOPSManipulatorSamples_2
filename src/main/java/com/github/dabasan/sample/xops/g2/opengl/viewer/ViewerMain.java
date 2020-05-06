package com.github.dabasan.sample.xops.g2.opengl.viewer;

import com.github.dabasan.joglf.gl.window.JOGLFWindowInterface;

public class ViewerMain {
	public static void main(String[] args) {
		new ViewerMain();
	}
	public ViewerMain() {
		JOGLFWindowInterface window = new Viewer2();
		window.SetTitle("Viewer");
	}
}
