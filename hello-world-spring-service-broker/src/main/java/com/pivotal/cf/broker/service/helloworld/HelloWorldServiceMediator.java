package com.pivotal.cf.broker.service.helloworld;

import com.pivotal.cf.broker.config.Env;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import com.pivotal.cf.broker.model.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.client.ClientHttpRequestInterceptor;

@Service
public class HelloWorldServiceMediator {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HelloWorldServiceMediator.class);

    private static final String HWINSTANCES_PATH_SEGMENT = "/hwinstances";
    private static final String ACCOUNTS_PATH_SEGMENT = "/accounts";
    private static final String AUTHORIZATIONS_PATH_SEGMENT = "/authorizations";

    //@Autowired
    private RestTemplate restTemplate;

    private String helloworldBaseServiceURL;
    private String helloworldHWInstancesServiceURL;
    private String helloworldAccountsServiceURL;
    private String helloworldAuthorizationsServiceURL;

    @Autowired
    public HelloWorldServiceMediator(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;

        String helloworldServiceURLTemplate = "http://%s:%s";
        helloworldBaseServiceURL = String.format(helloworldServiceURLTemplate,
                Env.serviceHost,
                Env.servicePort);

        helloworldHWInstancesServiceURL = helloworldBaseServiceURL + HWINSTANCES_PATH_SEGMENT;
        helloworldAccountsServiceURL = helloworldBaseServiceURL + ACCOUNTS_PATH_SEGMENT;
        helloworldAuthorizationsServiceURL = helloworldBaseServiceURL + AUTHORIZATIONS_PATH_SEGMENT;

        // provide Basic Auth capability
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new BasicAuthInterceptor(Env.serviceAdminUserName, Env.serviceAdminPassword));
        restTemplate.setInterceptors(interceptors);
    }

    // === Hello World Service Instances ===

    // TODO / nice to have
    public List<String> getAllHWInstanceIds() {

        logger.info("getAllHWInstanceIds");
        return null;
    }

    public String getHWInstanceId(String hwInstanceId) {
        logger.info("getHWInstanceId: " + hwInstanceId);

        Map<String,String> urlVars = new HashMap<String,String>();
        urlVars.put("hwInstanceId", hwInstanceId);

        String url = helloworldHWInstancesServiceURL + "/{hwInstanceId}";
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(url, String.class, urlVars);

        logger.info("getHWIntance RESULT: " + responseEntity.toString());
        return hwInstanceId;
    }

    public void createHWInstance(String hwInstanceId) {
        logger.info("createHWInstance: " + hwInstanceId);

        String url = helloworldHWInstancesServiceURL + "/{hwInstanceId}";
        logger.info("createHWInstance: URL " + url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        Map<String,String> urlVars = new HashMap<String,String>();
        urlVars.put("hwInstanceId", hwInstanceId);
        restTemplate.put(url, entity, urlVars);

        logger.info("createHWInstance DONE");
    }

    public void destroyHWInstance(String hwInstanceId) {
        logger.info("destroyHWInstance: " + hwInstanceId);

        String url = helloworldHWInstancesServiceURL + "/{hwInstanceId}";
        logger.info("destroyHWInstance: URL " + url);
        Map<String,String> urlVars = new HashMap<String,String>();
        urlVars.put("hwInstanceId", hwInstanceId);
        restTemplate.delete(url, urlVars);

        logger.info("destroyHWInstance DONE");
    }

    //=== Hello World Service Accounts ===

    // TODO / nice to have
    public List<String> GetAllAccountIds() {
        logger.info("GetAllAccountIds");
        return null;
    }

    public void createAccount(String id, String pwd) {
        logger.info("createAccount: " + id);

        String url = helloworldAccountsServiceURL + "/{id}?password={password}";
        logger.info("createAccount: URL " + url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        Map<String,String> urlVars = new HashMap<String,String>();
        urlVars.put("id", id);
        urlVars.put("password", pwd);
        restTemplate.put(url, entity, urlVars);
        logger.info("createAccount DONE");
    }

    public void destroyAccount(String id) {
        logger.info("destroyAccount: " + id);

        String url = helloworldAccountsServiceURL + "/{id}";
        logger.info("destroyAccount: URL " + url);
        Map<String,String> urlVars = new HashMap<String,String>();
        urlVars.put("id", id);
        restTemplate.delete(url, urlVars);

        logger.info("destroyAccount DONE");
    }

    // === Hello World Authorizations ====

    public void createAuthorization(String id, String accountId, String hwInstanceId) {
        logger.info("createAuthorization: " + id + " / " + accountId + " / " + hwInstanceId);

        String url = helloworldAuthorizationsServiceURL + "/{id}?accountId={accountId}&hwInstanceId={hwInstanceId}";
        logger.info("createAuthorization: URL " + url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        Map<String,String> urlVars = new HashMap<String,String>();
        urlVars.put("id", id);
        urlVars.put("accountId", accountId);
        urlVars.put("hwInstanceId", hwInstanceId);
        restTemplate.put(url, entity, urlVars);
        logger.info("createAuthorization DONE");

    }

    public void destroyAuthorization(String id) {
        logger.info("destroyAuthorization: " + id);

        String url = helloworldAuthorizationsServiceURL + "/{id}";
        logger.info("destroyAuthorization: URL " + url);
        Map<String,String> urlVars = new HashMap<String,String>();
        urlVars.put("id", id);
        restTemplate.delete(url, urlVars);

        logger.info("destroyAuthorization DONE");
    }

    public Authorization getAuthorization(String id) {
        logger.info("getAuthorization: " + id);

        Map<String,String> urlVars = new HashMap<String,String>();
        urlVars.put("id", id);

        String url = helloworldAuthorizationsServiceURL + "/{id}";
        ResponseEntity<Authorization> responseEntity =
                restTemplate.getForEntity(url, Authorization.class, urlVars);

        logger.info("getAuthorization RESULT: " + responseEntity.toString());

        return responseEntity.getBody();
    }
}
