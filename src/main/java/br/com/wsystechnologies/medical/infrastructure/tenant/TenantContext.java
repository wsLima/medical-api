package br.com.wsystechnologies.medical.infrastructure.tenant;

public class TenantContext {
    private static final ThreadLocal<String> CURRENT = new ThreadLocal<>();
    public static void setCurrentTenant(String t) { CURRENT.set(t); }
    public static String getCurrentTenant() { return CURRENT.get(); }
    public static void clear() { CURRENT.remove(); }
}
