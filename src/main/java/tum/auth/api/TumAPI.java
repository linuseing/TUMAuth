package tum.auth.api;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class TumAPI {
    private final OkHttpClient client;
    private final String name;

    public TumAPI(String applicationName) {
        client = new OkHttpClient();
        name = applicationName;
    }

    public String requestToken(String tumID) throws IOException, InvalidTumIdException {
        HttpUrl url = getBuilder()
                .addPathSegment("wbservicesbasic.requestToken")
                .addQueryParameter("pUsername", tumID)
                .addQueryParameter("pTokenName", name)
                .build();

        Request request = new Request.Builder().get().url(url).build();

        Response response = client.newCall(request).execute();
        try {
            TokenResponse tokenResponse = new XmlMapper().readValue(response.body().bytes(), TokenResponse.class);
            return tokenResponse.getToken();
        } catch (MismatchedInputException exception) {
            throw new InvalidTumIdException();
        }
    }

    public boolean verifyToken(String token) throws IOException {
        HttpUrl url = getBuilder()
                .addPathSegment("wbservicesbasic.id")
                .addQueryParameter("pToken", token)
                .build();

        Response response = executeRequest(url);

        try {
            ErrorResponse errorResponse = new XmlMapper().readValue(response.body().bytes(), ErrorResponse.class);
            return !errorResponse.getMessage().contains("Token ist nicht bestätigt!");
        } catch (Exception ignored) {}
        return true;
    }

    private Response executeRequest(HttpUrl url) throws IOException {
        Request request = new Request.Builder().get().url(url).build();
        return client.newCall(request).execute();
    }

    private HttpUrl.Builder getBuilder() {
        return HttpUrl.parse("https://campus.tum.de/tumonline").newBuilder();
    }

    public String getName() {
        return name;
    }
}
