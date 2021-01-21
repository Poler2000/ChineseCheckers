package tp.client.network;

public class ReplayRequest {
	private String content;
	
	public ReplayRequest() {
		content = "{ \"type\": \"replayRequest\"}";
	}
	
	public ReplayRequest(Integer id) {
		content = "{ \"type\": \"loadReplay\", \"id\": " + id.toString() + "}";
	}
	
	public String toString() {
		return content;
	}
}
