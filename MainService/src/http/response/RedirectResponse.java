package http.response;

public class RedirectResponse extends Response{
    private final String target;

    public RedirectResponse(String target) {
        this.target = target;
    }

    @Override
    public String getResponseString() {
        String response = "HTTP/1.1 301 OK\r\nLocation: " + this.target + "\r\n\r\n";
        return response;
    }
}
