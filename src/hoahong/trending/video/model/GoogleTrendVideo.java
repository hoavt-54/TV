package hoahong.trending.video.model;

public class GoogleTrendVideo implements Video {
/*
 * 		"id": "84OT0NLlqfM",
        "title": "شو�? الناس بقلبك �?ي رمضان |  Remove labels this Ramadan",
        "url": "https://www.youtube.com/watch?v\u003d84OT0NLlqfM",
        "imgUrl": "https://i.ytimg.com/vi/84OT0NLlqfM/mqdefault.jpg",
        "dailyViewCount": "500,000",
        "totalViewCount": "4,306,530",
        "commentCount": "442",
        "username": "Coca-Cola Middle East",
        "length": "2:48",
        "shareUrl": "https://www.google.com/trends/hotvideos?svt\u003d%D8%B4%D9%88%D9%81+%D8%A7%D9%84%D9%86%D8%A7%D8%B3+%D8%A8%D9%82%D9%84%D8%A8%D9%83+%D9%81%D9%8A+%D8%B1%D9%85%D8%B6%D8%A7%D9%86+%7C++Remove+labels+this+Ramadan\u0026hvd\u003d20150711\u0026geo\u003dUS#a\u003d84OT0NLlqfM",
        "publishedTime": "today",
        "channelUrl": "https:/
 * */
	
	private String id;
	private String title;
	private String url;
	private String imgUrl;
	private String dailyViewCount;
	private String totalViewCount;
	private String commentCount;
	private String username;
	private String length; 
	private String publishedTime;
	
	public GoogleTrendVideo(String id, String title, String url, String imgUrl,
			String dailyViewCount, String totalViewCount, String commentCount,
			String username, String length, String publishedTime) {
		super();
		this.id = id;
		this.title = title;
		this.url = url;
		this.imgUrl = imgUrl;
		this.dailyViewCount = dailyViewCount;
		this.totalViewCount = totalViewCount;
		this.commentCount = commentCount;
		this.username = username;
		this.length = length;
		this.publishedTime = publishedTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getDailyViewCount() {
		return dailyViewCount;
	}

	public void setDailyViewCount(String dailyViewCount) {
		this.dailyViewCount = dailyViewCount;
	}

	public String getTotalViewCount() {
		return totalViewCount;
	}

	public void setTotalViewCount(String totalViewCount) {
		this.totalViewCount = totalViewCount;
	}

	public String getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(String commentCount) {
		this.commentCount = commentCount;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getPublishedTime() {
		return publishedTime;
	}

	public void setPublishedTime(String publishedTime) {
		this.publishedTime = publishedTime;
	}
	
	
	
	
}
