package indi.joynic.joodoo.security.auth;

public class Authentication {
    private static class AuthenticationHolder {
        private static final Authentication INSTANCE = new Authentication();
    }

    public Authentication getInstance() {
        return AuthenticationHolder.INSTANCE;
    }


}