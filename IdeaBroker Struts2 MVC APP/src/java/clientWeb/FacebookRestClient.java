package clientWeb;

import objectos.Ideia;
import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;

public class FacebookRestClient {
//    private static final String NETWORK_NAME = "Facebook";
//    private static final Token EMPTY_TOKEN = null;

    private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me";
    public final Token EMPTY_TOKEN = null;
    String apiKey = "1428945757336662";
    String apiSecret = "d3137ee961cc67d2905683d2a0fbb05d";
    String ver = "CAAUTnlxE9FYBAOTJAItt2llzch3NDlf3VGPsrT77sZCEAFv4Lzj415bMaGwQiF1hzZAAeV5ZAKwZBJUwWxVhHM83rOs22TXq4vYhWXwYHYaTyr0Wd4oqPiOwzCvVqL8K49LZCBqlL4wZBazYpiQncZBVjGuikliZAeFTcjA8QORNkvHyNT8kAmUS";
    private Token accessToken = new Token("CAAUTnlxE9FYBAOTJAItt2llzch3NDlf3VGPsrT77sZCEAFv4Lzj415bMaGwQiF1hzZAAeV5ZAKwZBJUwWxVhHM83rOs22TXq4vYhWXwYHYaTyr0Wd4oqPiOwzCvVqL8K49LZCBqlL4wZBazYpiQncZBVjGuikliZAeFTcjA8QORNkvHyNT8kAmUS", "");
    private OAuthService service;

    public FacebookRestClient() {
        service = new ServiceBuilder()
                .provider(FacebookApi.class)
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .callback("http://eden.dei.uc.pt/~amaf/echo.php") // Do not change this.
                .scope("publish_stream")
                .build();
    }

    public void addIdeia(Ideia ideia) {
        String msg = ideia.getNome() + "-" + ideia.getDescricao();
        OAuthRequest request = new OAuthRequest(Verb.POST, PROTECTED_RESOURCE_URL + "/feed");
        service.signRequest(accessToken, request);
        request.addBodyParameter("message",msg);
        service.signRequest(accessToken, request);
        Response response = request.send();
        String body = response.getBody();
        System.out.println(body);
        
    }

    public void deleteIdeia(String id) {
        OAuthRequest request = new OAuthRequest(Verb.DELETE, PROTECTED_RESOURCE_URL + "/{100006286360431_1440571746162357}");
        service.signRequest(accessToken, request);
        //request.addBodyParameter("id", id);
        Response response = request.send();
        System.out.println("Got it! Lets see what we found...");
        System.out.println();
        System.out.println(response.getCode());
        System.out.println(response.getBody());

    }
//    public void replyIdeia(String id, Reply reply){
//    	Verifier verifier = new Verifier(ver);
//    	accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
//    	OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.facebook.com/method/stream.addComment?post_id="+id+"&comment="+reply.getTexto().replace(" ", "%20"));
//    	service.signRequest(accessToken, request);
//    	Response response = request.send();
//        System.out.println("Got it! Lets see what we found...");
//        System.out.println();
//        System.out.println(response.getCode());
//        System.out.println(response.getBody());
//
//    }

    public static void main(String[] args) {
        FacebookRestClient restClient = new FacebookRestClient();
        Ideia ideia = new Ideia();
        ideia.setNome("Java");
        ideia.setDescricao("Este é um teste de integração com o facebook");
        //restClient.addIdeia(ideia);
        restClient.deleteIdeia("100006286360431_1440571746162357");
//        // Replace these with your own api key and secret
//        String apiKey = "1428945757336662";
//        String apiSecret = "d3137ee961cc67d2905683d2a0fbb05d";
//
//
//        OAuthService service = new ServiceBuilder()
//                .provider(FacebookApi.class)
//                .apiKey(apiKey)
//                .apiSecret(apiSecret)
//                .callback("http://eden.dei.uc.pt/~amaf/echo.php") // Do not change this.
//                .scope("publish_stream")
//                .build();
//        Scanner in = new Scanner(System.in);
//
//        System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
//        System.out.println();
//
//        // Obtain the Authorization URL
//        System.out.println("Fetching the Authorization URL...");
//        String authorizationUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
//        System.out.println("Got the Authorization URL!");
//        System.out.println("Now go and authorize Scribe here:");
//        System.out.println(authorizationUrl);
//        System.out.println("And paste the authorization code here");
//        System.out.print(">>");
//        Verifier verifier = new Verifier(in.nextLine());
//        System.out.println();
//
//        // Trade the Request Token and Verfier for the Access Token
//        System.out.println("Trading the Request Token for an Access Token...");
//        Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
//        System.out.println("Got the Access Token!");
//        System.out.println("(if your curious it looks like this: " + accessToken + " )");
//        System.out.println();
//
//        // Now let's go and ask for a protected resource!
//        System.out.println("Now we're going to access a protected resource...");
//        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
//        service.signRequest(accessToken, request);
//        Response response = request.send();
//        System.out.println("Got it! Lets see what we found...");
//        System.out.println();
//        System.out.println(response.getCode());
//        System.out.println(response.getBody());


        System.out.println();
        System.out.println("Thats it man! Go and build something awesome with Scribe! :)");

    }
}
