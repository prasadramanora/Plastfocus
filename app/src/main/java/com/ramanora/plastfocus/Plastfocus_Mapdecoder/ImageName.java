package com.ramanora.plastfocus.Plastfocus_Mapdecoder;

//import com.theappguruz.R;


import com.ramanora.plastfocus.R;

import java.util.ArrayList;
import java.util.Random;

public class ImageName {

	private Random randNo;
	private ArrayList<Integer> imageName;

	public ImageName() {
		imageName = new ArrayList<Integer>();
		imageName.add(R.mipmap.ic_launcher);

	}

	public int getImageId() {
		randNo = new Random();
		return imageName.get(randNo.nextInt(imageName.size()));
	}
}
