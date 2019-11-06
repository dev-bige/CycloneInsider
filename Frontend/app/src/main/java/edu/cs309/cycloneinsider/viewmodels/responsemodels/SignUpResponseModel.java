package edu.cs309.cycloneinsider.viewmodels.responsemodels;

import androidx.annotation.StringRes;

import retrofit2.Response;

public class SignUpResponseModel {
    private Response<Void> rawResponse;
    private boolean error;
    @StringRes
    private int stringError;

    public SignUpResponseModel(Response<Void> rawResponse, boolean error, @StringRes int stringError) {
        this.rawResponse = rawResponse;
        this.error = error;
        this.stringError = stringError;
    }

    public static SignUpResponseModel success(Response<Void> response) {
        return new SignUpResponseModel(response, false, 0);
    }

    public static SignUpResponseModel error(@StringRes int stringError) {
        return new SignUpResponseModel(null, true, stringError);
    }

    public int getStringError() {
        return stringError;
    }

    public Response<Void> getRawResponse() {
        return rawResponse;
    }

    public boolean isError() {
        return error;
    }
}
