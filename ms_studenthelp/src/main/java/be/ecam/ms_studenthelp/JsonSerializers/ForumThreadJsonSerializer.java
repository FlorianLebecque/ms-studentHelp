package be.ecam.ms_studenthelp.JsonSerializers;

import be.ecam.ms_studenthelp.Object.ForumThread;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class ForumThreadJsonSerializer extends JsonSerializer<ForumThread> {
    public ForumThreadJsonSerializer() {
        this(null);
    }

    public ForumThreadJsonSerializer(Class<ForumThread> t) {
        super();
    }

    @Override
    public void serialize(ForumThread value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", value.getId());
        gen.writeStringField("title", value.getTitle());
        gen.writeBooleanField("answered", value.isAnswered());
        gen.writeStringField("category", value.getCategory().getTitle());
        gen.writeStringField("datePosted", value.getDatePosted().toString());
        gen.writeStringField("dateModified", value.getDateModified().toString());
        gen.writeStringField("firstPost", value.getFirstPost().getId());

        // Create a list with every tag title
        gen.writeArrayFieldStart("tags");
        value.getTags().forEach(tag -> {
            try {
                gen.writeString(tag.getTitle());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        gen.writeEndArray();
        gen.writeEndObject();
    }
}
