package app;

import com.google.gson.Gson;
import http.Request;
import http.ServerThread;
import http.response.HtmlResponse;
import http.response.RedirectResponse;
import http.response.Response;
import java.net.URLDecoder;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class QuoteController extends Controller {
    List<String> quotes = ServerThread.quotes;
    private BufferedReader in;
    private PrintWriter out;
    private String qod, requestLine;
    private String qod2 = "";
    Gson gson = new Gson();

    public QuoteController(Request request) {
        super(request);
    }

    @Override
    public Response doGet() {
        String htmlBody = "" +
                "<form method=\"POST\" action=\"/apply\" class=\"quote-form\">" +
                "<label for=\"author\">Author: </label><input name=\"author\" id=\"author\" type=\"text\"><br><br>" +
                "<label for=\"quote\">Quote: </label><input name=\"quote\" id=\"quote\" type=\"text\"><br><br>" +
                "<button class=\"submit-btn\">Submit</button>" +
                "</form>" +
                "<h2>Quote of the Day</h2>";

        try {
            Socket socket = new Socket("localhost", 8081);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            qod = "GET /qod";
            out.println(gson.toJson(qod, String.class));

            requestLine = in.readLine();
            do {
                requestLine = in.readLine();
            } while (!requestLine.trim().equals(""));

            if (ServerThread.qod == "") {
                ServerThread.qod = gson.fromJson(in.readLine(), String.class);
                htmlBody += "<p class=\"qod\">" + ServerThread.qod + "</p>";
            } else {
                htmlBody += "<p class=\"qod\">" + ServerThread.qod + "</p>";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        htmlBody += "<h2>Saved Quotes:</h2><div class=\"quote-list\">";

        for (String s : quotes) {
            try {
                String decodedQuote = URLDecoder.decode(s, "UTF-8");
                htmlBody += "<p class=\"quote\">" + decodedQuote + "</p>";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        htmlBody += "</div>";

        String content = "<html><head><title>Quotes</title>" +
                "<meta charset=\"UTF-8\">" +
                "<style>" +
                "body { font-family: Arial, sans-serif; line-height: 1.6; margin: 0; padding: 0; display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #ffe6f1; }" +
                "h2 { color: #ff66b2; }" +
                ".container { width: 80%; max-width: 600px; text-align: center; }" +
                ".quote-form { margin-bottom: 20px; padding: 20px; background: #ffd9e6; border-radius: 5px; width: 100%; box-sizing: border-box; }" +
                ".quote-form label { font-weight: bold; color: #ff66b2; }" +
                ".quote-form input { width: 100%; padding: 8px; margin: 8px 0; border-radius: 4px; border: 1px solid #ff66b2; }" +
                ".submit-btn { background-color: #ff66b2; color: white; border: none; padding: 10px 15px; border-radius: 4px; cursor: pointer; }" +
                ".submit-btn:hover { background-color: #ff3385; }" +
                ".qod { font-style: italic; color: #cc4d73; }" +
                ".quote-list { margin-top: 20px; }" +
                ".quote { background: #ffd9e6; padding: 10px; margin-bottom: 10px; border-radius: 4px; color: #ff66b2; }" +
                "</style>" +
                "</head><body>" +
                "<div class=\"container\">" +
                htmlBody +
                "</div>" +
                "</body></html>";

        return new HtmlResponse(content);
    }

    @Override
    public Response doPost() {
        return new RedirectResponse("/quotes");
    }
}
