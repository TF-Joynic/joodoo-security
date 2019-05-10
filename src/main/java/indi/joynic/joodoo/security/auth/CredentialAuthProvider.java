package indi.joynic.joodoo.security.auth;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import java.security.AuthProvider;

/**
 * User who claimed {username} specified a credential
 */
public class CredentialAuthProvider extends AuthProvider {

    private CallbackHandler handler;

    protected CredentialAuthProvider(String name, String versionStr, String info) {
        super(name, versionStr, info);
    }

    @Override
    public void login(Subject subject, CallbackHandler handler) throws LoginException {
        
    }

    @Override
    public void logout() throws LoginException {

    }

    @Override
    public void setCallbackHandler(CallbackHandler handler) {
        this.handler = handler;
    }
}