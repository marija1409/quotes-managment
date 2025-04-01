package app;

import http.Request;
import http.response.HtmlResponse;
import http.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuoteController extends Controller {

    public static List<String> quotes = new ArrayList<>(){
        {
            add("Winston Churchill: " + "\"Success is not final, failure is not fatal: it is the courage to continue that counts.\"");
            add("Theodore Roosevelt: " + "\"Do what you can, with what you have, where you are.\"");
            add("Maya Angelou: " + "\"If you’re always trying to be normal, you will never know how amazing you can be.\"");
            add("Steve Jobs: " + "\"Your time is limited, so don’t waste it living someone else’s life.\"");
            add("Nelson Mandela: " + "\"It always seems impossible until it’s done.\"");
            add("Oprah Winfrey: " + "\"The biggest adventure you can take is to live the life of your dreams.\"");
            add("Albert Einstein: " + "\"Life is like riding a bicycle. To keep your balance, you must keep moving.\"");
            add("Dwayne “The Rock” Johnson: " + "\"Success isn’t always about greatness. It’s about consistency. Consistent hard work leads to success. Greatness will come.\"");
        }
    };

    public QuoteController(Request request) {
        super(request);
    }

    @Override
    public Response doGet() {
        Random rand = new Random();
        String quote = quotes.get(rand.nextInt(quotes.size()));
        return new HtmlResponse(quote);
    }

    @Override
    public Response doPost() {
        return null;
    }
}
