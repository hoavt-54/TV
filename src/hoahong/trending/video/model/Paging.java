package hoahong.trending.video.model;

public class Paging {
	private int totalResults;
	private int resultsPerPage;
	
	public Paging () {}

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}

	public int getResultsPerPage() {
		return resultsPerPage;
	}

	public void setResultsPerPage(int resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}
	
	
}
