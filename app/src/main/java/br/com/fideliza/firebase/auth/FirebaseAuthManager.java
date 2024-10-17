package br.com.fideliza.firebase.auth;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthManager {
    // instanciando o FireBase Auth
    private FirebaseAuth mAuth;

    // Constructor
    public FirebaseAuthManager() {
        this.mAuth =FirebaseAuth.getInstance();
    }

    // Login Methode
    public void login(String email, String password, final AuthCallBack callBack) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        callBack.onSuccess(user);
                    } else {
                        callBack.onFailure(task.getException());
                    }
                });
    }

    // Logout Methode
    public void logout() {
        mAuth.signOut();
    }

    // Create Account Methode
    public void  createAccount(String email, String password, final AuthCallBack callback) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.createSuccess(task.getResult());
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    public void recoverPassword(String email, final AuthCallBack callback) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() ) {
                        callback.resetPasswordSuccess();
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    public void verifyEmail (@NonNull FirebaseUser user, final AuthCallBack callback) {
        user.sendEmailVerification()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.verifyEmailSuccess();
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

}
