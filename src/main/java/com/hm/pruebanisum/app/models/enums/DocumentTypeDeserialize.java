package com.hm.pruebanisum.app.models.enums;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class DocumentTypeDeserialize extends JsonDeserializer<DocumentTypeEnum> {

    @Override
    public DocumentTypeEnum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return DocumentTypeEnum.fromString(p.getValueAsString());
    }

}
