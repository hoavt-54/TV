package hoahong.trending.video.model;

public class YoutubeVideo implements Video{
	private String publishedAt;
	private String channelId;
	private String title;
	private String description;
	private Thumbnail thumbnails;
	private int position;
	private YoutubeResourceId resourceId;
	
	public YoutubeVideo(String publishedAt, String channelId, String title,
			String description, Thumbnail thumbnails, int position,
			YoutubeResourceId resourceId) {
		super();
		this.publishedAt = publishedAt;
		this.channelId = channelId;
		this.title = title;
		this.description = description;
		this.thumbnails = thumbnails;
		this.position = position;
		this.resourceId = resourceId;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Thumbnail getThumbnails() {
		return thumbnails;
	}

	public void setThumbnails(Thumbnail thumbnails) {
		this.thumbnails = thumbnails;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public YoutubeResourceId getResourceId() {
		return resourceId;
	}

	public void setResourceId(YoutubeResourceId resourceId) {
		this.resourceId = resourceId;
	}
	
	
	
	
}
