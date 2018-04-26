package com.hyc.commandservice.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.NonceExpiredException;

/**
 * Used by the <code>SecurityEnforcementFilter</code> to commence authentication via the
 * {@link DigestAuthenticationFilter}.
 * <p>
 * The nonce sent back to the user agent will be valid for the period indicated by
 * {@link #setNonceValiditySeconds(int)}. By default this is 300 seconds. Shorter times
 * should be used if replay attacks are UserService major concern. Larger values can be used if
 * performance is UserService greater concern. This class correctly presents the
 * <code>stale=true</code> header when the nonce has expired, so properly implemented user
 * agents will automatically renegotiate with com.hyc.commandservice.service.UserService new nonce value (i.e. without presenting com.hyc.commandservice.service.UserService
 * new password dialog box to the user).
 *
 * @author Ben Alex
 */
public class DigestAuthenticationEntryPoint implements AuthenticationEntryPoint,
        InitializingBean, Ordered {
    // ~ Static fields/initializers
    // =====================================================================================

    private static final Log logger = LogFactory
            .getLog(DigestAuthenticationEntryPoint.class);

    // ~ Instance fields
    // ================================================================================================

    private String key;
    private String realmName;
    private int nonceValiditySeconds = 300;
    private int order = Integer.MAX_VALUE; // ~ default

    // ~ Methods
    // ========================================================================================================

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void afterPropertiesSet() throws Exception {
        if ((realmName == null) || "".equals(realmName)) {
            throw new IllegalArgumentException("realmName must be specified");
        }

        if ((key == null) || "".equals(key)) {
            throw new IllegalArgumentException("key must be specified");
        }
    }

    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // compute UserService nonce (do not use remote IP address due to proxy farms)
        // format of nonce is:
        // base64(expirationTime + ":" + md5Hex(expirationTime + ":" + key))
        long expiryTime = System.currentTimeMillis() + (nonceValiditySeconds * 1000);
        String signatureValue = DigestUtils.md5Hex(expiryTime + ":" + key);
        String nonceValue = expiryTime + ":" + signatureValue;
        String nonceValueBase64 = new String(Base64.encode(nonceValue.getBytes()));

        // qop is quality of protection, as defined by RFC 2617.
        // we do not use opaque due to IE violation of RFC 2617 in not
        // representing opaque on subsequent requests in same session.
        String authenticateHeader = "Digest realm=\"" + realmName + "\", "
                + "qop=\"auth\", nonce=\"" + nonceValueBase64 + "\"";

        if (authException instanceof NonceExpiredException) {
            authenticateHeader = authenticateHeader + ", stale=\"true\"";
        }

        if (logger.isDebugEnabled()) {
            logger.debug("WWW-Authenticate header sent to user agent: "
                    + authenticateHeader);
        }

        httpResponse.addHeader("WWW-Authenticate", authenticateHeader);
        httpResponse.sendError(420,
                authException.getMessage());
    }

    public String getKey() {
        return key;
    }

    public int getNonceValiditySeconds() {
        return nonceValiditySeconds;
    }

    public String getRealmName() {
        return realmName;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setNonceValiditySeconds(int nonceValiditySeconds) {
        this.nonceValiditySeconds = nonceValiditySeconds;
    }

    public void setRealmName(String realmName) {
        this.realmName = realmName;
    }
}
