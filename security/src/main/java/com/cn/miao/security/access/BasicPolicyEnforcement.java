package com.cn.miao.security.access;

import com.cn.miao.security.access.model.PolicyRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.expression.EvaluationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @title: BasicPolicyEnforcement
 * @description:
 * @author: dengmiao
 * @create: 2019-07-22 16:14
 **/
@Slf4j
@Component
public class BasicPolicyEnforcement implements PolicyEnforcement {

    @Autowired
    @Qualifier("jsonFilePolicyDefinition")
    private PolicyDefinition policyDefinition;

    @Override
    public boolean check(Object subject, Object resource, Object action, Object environment) {
        //Get all policy rules
        List<PolicyRule> allRules = policyDefinition.getAllPolicyRules();
        //Wrap the context
        SecurityAccessContext cxt =
                new SecurityAccessContext()
                .setSubject(subject)
                .setResource(resource)
                .setAction(action)
                .setEnvironment(environment);
        //Filter the rules according to context.
        List<PolicyRule> matchedRules = filterRules(allRules, cxt);
        //finally, check if any of the rules are satisfied, otherwise return false.
        return checkRules(matchedRules, cxt);
    }

    private List<PolicyRule> filterRules(List<PolicyRule> allRules, SecurityAccessContext cxt) {
        List<PolicyRule> matchedRules = new ArrayList<>();
        for(PolicyRule rule : allRules) {
            try {
                if(rule.getTarget().getValue(cxt, Boolean.class)) {
                    matchedRules.add(rule);
                }
            } catch(EvaluationException ex) {
                log.error("An error occurred while evaluating PolicyRule.", ex);
            }
        }
        return matchedRules;
    }

    private boolean checkRules(List<PolicyRule> matchedRules, SecurityAccessContext cxt) {
        for(PolicyRule rule : matchedRules) {
            try {
                if(rule.getCondition().getValue(cxt, Boolean.class)) {
                    return true;
                }
            } catch(EvaluationException ex) {
                log.error("An error occurred while evaluating PolicyRule.", ex);
            }
        }
        return false;
    }
}
