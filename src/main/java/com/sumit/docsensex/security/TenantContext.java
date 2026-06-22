package com.sumit.docsensex.security;

public class TenantContext {
    private static final ThreadLocal<String> TENANT_ID = new ThreadLocal<>();
    public static String getTenantId() {
        return TENANT_ID.get();
    }
    public static void setTenantId(String tenantId) {
        TENANT_ID.set(tenantId);
    }
    public static void clearTenantId() {
        TENANT_ID.remove();
    }
}
