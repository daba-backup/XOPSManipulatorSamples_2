package com.github.dabasan.sample.xops.g2.mif;

import java.io.IOException;

import com.github.dabasan.xops.mif.MIFList;

public class MIFListXML {
	public static void main(String[] args) {
		MIFList list;
		try {
			list = new MIFList("./Data/addon", "Shift-JIS");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		list.WriteXML("./Data/mif_list.xml", MIFList.ALL);
	}
}
