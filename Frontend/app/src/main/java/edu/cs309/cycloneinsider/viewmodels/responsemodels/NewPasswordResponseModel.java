package edu.cs309.cycloneinsider.viewmodels.responsemodels;

import androidx.annotation.StringRes;

import retrofit2.Response;

public class NewPasswordResponseModel {
    private Response<Void> rawResponse;
    private boolean error;
    @StringRes
    private int stringError;

    private NewPasswordResponseModel(Response<Void> rawResponse, boolean error, @StringRes int stringError) {
        this.rawResponse = rawResponse;
        this.error = error;
        this.stringError = stringError;
    }

    public static NewPasswordResponseModel success(Response<Void> response) {
        return new NewPasswordResponseModel(response, false, 0);
    }

    public static NewPasswordResponseModel error(@StringRes int stringError) {
        return new NewPasswordResponseModel(null, true, stringError);
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
