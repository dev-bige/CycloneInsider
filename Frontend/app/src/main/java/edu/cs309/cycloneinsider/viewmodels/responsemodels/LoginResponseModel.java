package edu.cs309.cycloneinsider.viewmodels.responsemodels;

import androidx.annotation.StringRes;

import retrofit2.Response;

public class LoginResponseModel {
    private Response<Void> rawResponse;
    private boolean error;
    @StringRes
    private int stringError;

    private LoginResponseModel(Response<Void> rawResponse, boolean error, @StringRes int stringError) {
        this.rawResponse = rawResponse;
        this.error = error;
        this.stringError = stringError;
    }

    public static LoginResponseModel success(Response<Void> response) {
        return new LoginResponseModel(response, false, 0);
    }

    public static LoginResponseModel error(@StringRes int stringError) {
        return new LoginResponseModel(null, true, stringError);
    }

    public Response<Void> getRawResponse() {
        return rawResponse;
    }

    public boolean isError() {
        return error;
    }

    public int getStringError() {
        return stringError;
    }
}
