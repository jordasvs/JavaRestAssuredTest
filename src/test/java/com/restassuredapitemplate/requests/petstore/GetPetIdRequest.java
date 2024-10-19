package com.restassuredapitemplate.requests.petstore;

import com.restassuredapitemplate.bases.RequestRestBase;
import io.restassured.http.Method;


public class GetPetIdRequest extends RequestRestBase {

    public GetPetIdRequest(int petId){
        requestService = "/pet/" +petId;
        method = Method.GET;
    }
}



