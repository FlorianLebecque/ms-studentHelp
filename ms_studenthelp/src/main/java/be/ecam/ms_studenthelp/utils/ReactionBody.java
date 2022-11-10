package be.ecam.ms_studenthelp.utils;

import be.ecam.ms_studenthelp.Object.Reaction;
import org.jetbrains.annotations.Nullable;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.lang.NonNull;

import java.util.Map;

public class ReactionBody {
    private @Nullable Integer value;
    private @Nullable String authorId;

    public ReactionBody(int value, @Nullable String authorId) {
        this.value = value;
        this.authorId = authorId;
    }

    public ReactionBody(@NonNull String body) {
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String,Object> body_data = springParser.parseMap(body);

        value = (Integer) body_data.get("value");
        authorId = (String) body_data.get("authorId");
    }

    public @Nullable Integer getValue() {
        return value;
    }

    public @Nullable String getAuthorId() {
        return authorId;
    }
}
