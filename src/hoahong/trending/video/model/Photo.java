package hoahong.trending.video.model;

public class Photo {
	private String url;
	private float width;
	private float height;
	
	
	
	
	public Photo(String url, float width, float height) {
		super();
		this.url = url;
		this.width = width;
		this.height = height;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	
	
}
