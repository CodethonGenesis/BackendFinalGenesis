package com.basebackend.base_backend.config;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class TruncatedInstantSerializer extends StdSerializer<Instant> {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.systemDefault());

    public TruncatedInstantSerializer() {
        super(Instant.class);
    }

    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(FORMATTER.format(value));
    }
}