package com.cn.miao.security.access.json;

import com.cn.miao.security.access.model.PolicyRule;
import com.cn.miao.security.access.PolicyDefinition;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.expression.Expression;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @title: JsonFilePolicyDefinition
 * @description:
 * @author: dengmiao
 * @create: 2019-07-22 16:16
 **/
@Component("jsonFilePolicyDefinition")
public class JsonFilePolicyDefinition implements PolicyDefinition {

    private static String DEFAULT_POLICY_FILE_NAME = "default-policy.json";

    @Override
    public List<PolicyRule> getAllPolicyRules() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Expression.class, new SpelDeserializer());
        mapper.registerModule(module);

        PolicyRule[] rulesArray = new PolicyRule[0];
        try {
            rulesArray = mapper.readValue(getClass().getResourceAsStream(DEFAULT_POLICY_FILE_NAME), PolicyRule[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.asList(rulesArray);
    }
}
