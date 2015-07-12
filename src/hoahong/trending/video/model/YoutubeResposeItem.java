package hoahong.trending.video.model;

public class YoutubeResposeItem {
	private String kind;
	private String id;
	private String etag;
	private YoutubeVideo snippet;
	
	public YoutubeResposeItem(String kind, String id, String etag,
			YoutubeVideo snippet) {
		super();
		this.kind = kind;
		this.id = id;
		this.etag = etag;
		this.snippet = snippet;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEtag() {
		return etag;
	}

	public void setEtag(String etag) {
		this.etag = etag;
	}

	public YoutubeVideo getSnippet() {
		return snippet;
	}

	public void setSnippet(YoutubeVideo snippet) {
		this.snippet = snippet;
	}
	
	
}
