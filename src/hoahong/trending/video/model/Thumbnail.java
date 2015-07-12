package hoahong.trending.video.model;

import com.google.gson.annotations.SerializedName;

public class Thumbnail {
	@SerializedName("default")
	private Photo defaultSize;
	
	@SerializedName("medium")
	private Photo mediumSize;
	
	@SerializedName("medium")
	private Photo highSize;
	
	public Thumbnail () {
		super();
	}

	public Photo getDefaultSize() {
		return defaultSize;
	}

	public void setDefaultSize(Photo defaultSize) {
		this.defaultSize = defaultSize;
	}

	public Photo getMediumSize() {
		return mediumSize;
	}

	public void setMediumSize(Photo mediumSize) {
		this.mediumSize = mediumSize;
	}

	public Photo getHighSize() {
		return highSize;
	}

	public void setHighSize(Photo highSize) {
		this.highSize = highSize;
	} 

	
}
