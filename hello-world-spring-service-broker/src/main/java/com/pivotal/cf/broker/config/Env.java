package com.pivotal.cf.broker.config;

// TODO: assignment: replace with a 'user defined service'
public class Env {

    public static String serviceHost;
    public static String servicePort;
    public static String serviceAdminUserName;
    public static String serviceAdminPassword;

    static {
        serviceHost = System.getenv("HW_SERVICE_HOST");
        servicePort = System.getenv("HW_SERVICE_PORT");
        serviceAdminUserName = System.getenv("HW_SERVICE_ADMIN_USERNAME");
        serviceAdminPassword = System.getenv("HW_SERVICE_ADMIN_PASSWORD");
    }
}
