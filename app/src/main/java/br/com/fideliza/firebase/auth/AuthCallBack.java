package br.com.fideliza.firebase.auth;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class AuthCallBack {
    public void onSuccess(FirebaseUser user)
    {}
    public void onFailure(Exception exception)
    {}
    public void createSuccess(AuthResult result)
    {}
    public void resetPasswordSuccess()
    {}
    public void verifyEmailSuccess()
    {}
}
