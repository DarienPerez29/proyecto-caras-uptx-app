package com.example.proyectocarasuptx;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


public class ValidateEmptyFields {
    private static final String EMPTY_ERR = "Este campo no puede estar vacío";

    // Verify all data
    public static boolean dataFields(TextInputLayout userLayout, TextInputLayout passLayout) {
        String user = Objects.requireNonNull(userLayout.getEditText()).getText().toString().trim();
        String pass = Objects.requireNonNull(passLayout.getEditText()).getText().toString().trim();

        return user.equals("user") && pass.equals("pass");
    }

    // Verify if fields are empty
    public static boolean emptyFields(TextInputLayout userLayout, TextInputLayout passLayout) {
        String user = Objects.requireNonNull(userLayout.getEditText()).getText().toString().trim();
        String pass = Objects.requireNonNull(passLayout.getEditText()).getText().toString().trim();

        if (!user.isEmpty() && !pass.isEmpty()) return true;

        if (user.isEmpty()) userLayout.setError(EMPTY_ERR);
        else userLayout.setErrorEnabled(false);

        if (pass.isEmpty()) passLayout.setError(EMPTY_ERR);
        else passLayout.setErrorEnabled(false);

        return false;
    }

    // Clear error watcher
    public static void clearEmptyErrorWatcher(TextInputLayout layout) {
        String content = Objects.requireNonNull(layout.getEditText()).getText().toString().trim();
        if (!content.isEmpty()) layout.setErrorEnabled(false);
    }

}
