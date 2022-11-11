package be.ecam.ms_studenthelp.JsonSerializers;

import be.ecam.ms_studenthelp.Object.Post;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class PostJsonSerializer extends JsonSerializer<Post> {
    public PostJsonSerializer() {
        this(null);
    }

    public PostJsonSerializer(Class<Post> t) {
        super();
    }

    @Override
    public void serialize(Post value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        String parent = value.getParent() != null ? value.getParent().getId() : null;

        gen.writeStartObject();
        gen.writeStringField("id", value.getId());
        gen.writeStringField("content", value.getContent());
        gen.writeNumberField("upVotes", value.getUpvotes());
        gen.writeNumberField("downVotes", value.getDownvotes());
        gen.writeStringField("datePosted", value.getDatePosted().toString());
        gen.writeStringField("dateModified", value.getDateModified().toString());
        gen.writeStringField("authorId", value.getAuthor().getId());
        gen.writeStringField("parent", parent);
        gen.writeEndObject();
    }
}
