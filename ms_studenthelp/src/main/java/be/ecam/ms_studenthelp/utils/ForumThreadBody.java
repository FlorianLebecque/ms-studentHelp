package be.ecam.ms_studenthelp.utils;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForumThreadBody {
    private @Nullable String title;
    private @Nullable List<String> tags;
    private @Nullable String category;
    private boolean answered;
    private @Nullable PostBody firstPost;

    public ForumThreadBody(@Nullable String title,
                           @Nullable List<String> tags,
                           @Nullable String category,
                           boolean answered,
                           @Nullable PostBody firstPost) {
        this.title = title;
        this.tags = tags;
        this.category = category;
        this.answered = answered;
        this.firstPost = firstPost;
    }

    /*
        Constructor from a string body.
        Example:
            {
                "title": "Test thread",
                "category": "Mathematics",
                "firstPost": {
                    "authorId": "d66b3f8c-2271-4afb-a348-e370effff",
                    "content": "Fisrt post man"
                }
            }
     */
    public ForumThreadBody(String body) {
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String,Object> body_data = springParser.parseMap(body);

        title = (String) body_data.get("title");
        tags = (List<String>) body_data.get("tags");
        category = (String) body_data.get("category");
        answered = body_data.get("answered") != null && (Boolean) body_data.get("answered");

        if (body_data.get("firstPost") != null) {
            firstPost = new PostBody((HashMap<String, String>) body_data.get("firstPost"));
        } else {
            firstPost = null;
        }
    }

    public @Nullable String getTitle() {
        return title;
    }

    public @Nullable List<String> getTags() {
        return tags;
    }


    public @Nullable String getCategory() {
        return category;
    }

    public @Nullable PostBody getFirstPost() {
        return firstPost;
    }

    public boolean isAnswered() {
        return answered;
    }
}
