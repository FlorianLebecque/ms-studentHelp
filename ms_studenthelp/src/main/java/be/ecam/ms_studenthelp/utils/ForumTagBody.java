package be.ecam.ms_studenthelp.utils;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForumTagBody {

    private @Nullable String tag;


    public ForumTagBody(@Nullable String tag) {
        this.tag = tag;

    }
    public static ForumTagBody fromBody(String body) {
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String,Object> body_data = springParser.parseMap(body);

        return new ForumTagBody((String) body_data.get("tag"));
    }

    public @Nullable String getTag(){
        return tag;
    }
}
