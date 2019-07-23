package com.cn.miao.security.access.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.io.IOException;

/**
 * @title: SpelDeserializer
 * @description:
 * @author: dengmiao
 * @create: 2019-07-22 16:19
 **/
public class SpelDeserializer extends StdDeserializer<Expression> {

    ExpressionParser elParser = new SpelExpressionParser();

    public SpelDeserializer(){
        this(null);
    }

    protected SpelDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Expression deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String expresionString = jsonParser.getCodec().readValue(jsonParser, String.class);
        return elParser.parseExpression(expresionString);
    }
}
