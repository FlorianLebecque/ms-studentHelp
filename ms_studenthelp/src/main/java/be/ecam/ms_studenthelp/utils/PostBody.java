package be.ecam.ms_studenthelp.utils;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.lang.Nullable;

import java.util.Map;

/*
    Class used to store the content of the body for a Post
 */
public class PostBody {
    @Nullable private String authorId;
    @Nullable private String content;

    public PostBody(@Nullable String authorId, @Nullable String content) {
        this.authorId = authorId;
        this.content = content;
    }

    /*
        Constructor from a string body.
        Example:
            {
                "authorId": "d66b3f8c-2271-4afb-a348-e370ef9990",
                "content": "Post test"
            }
     */
    public PostBody(String body) {
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String,Object> body_data = springParser.parseMap(body);

        authorId = body_data.get("authorId").toString();
        content = body_data.get("content").toString();
    }

    public @Nullable String getAuthorId() {
        return authorId;
    }

    public @Nullable String getContent() {
        return content;
    }
}
