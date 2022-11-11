package be.ecam.ms_studenthelp.JsonSerializers;

import be.ecam.ms_studenthelp.Object.Reaction;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class ReactionJsonSerializer extends JsonSerializer<Reaction> {
    public ReactionJsonSerializer() {
        this(null);
    }

    public ReactionJsonSerializer(Class<Reaction> t) {
        super();
    }

    @Override
    public void serialize(Reaction value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("post", value.getPost().getId());
        gen.writeStringField("authorId", value.getAuthor().getId());
        gen.writeNumberField("value", value.getValue());
        gen.writeEndObject();
    }
}
