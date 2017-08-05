package com.example.myapplication.api.result;

/**
 * @author Muhammad Umar Farisi
 * @created 06/08/2017
 */

public class BaseResult<R> {
    private R response;

    public BaseResult(R response) {
        this.response = response;
    }

    public BaseResult(){

    }

    public R getResponse() {
        return response;
    }

    public void setResponse(R response) {
        this.response = response;
    }
}
