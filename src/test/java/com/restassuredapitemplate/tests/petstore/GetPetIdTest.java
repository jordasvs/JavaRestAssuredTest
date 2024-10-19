package com.restassuredapitemplate.tests.petstore;

import com.restassuredapitemplate.bases.TestBase;
import com.restassuredapitemplate.requests.petstore.GetPetIdRequest;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

public class GetPetIdTest extends TestBase {

    @Test
    public void ObterDadosDoPetComSucesso(){
        //Arrange
        int petId = 10;
        int statusCodeEsperado = HttpStatus.SC_OK;

        //Act
        GetPetIdRequest getPetIdRequest = new GetPetIdRequest(petId);
        ValidatableResponse response = getPetIdRequest.executeRequest();

        //Assert
        response.statusCode(statusCodeEsperado);
    }

    @Test
    public void ObterDadosDoPetComIdInvalido(){
        //Arrange
        int petId = 999999;
        int statusCodeEsperado = HttpStatus.SC_NOT_FOUND;

        //Act
        GetPetIdRequest getPetIdRequest = new GetPetIdRequest(petId);
        ValidatableResponse response = getPetIdRequest.executeRequest();

        //Assert
        response.statusCode(statusCodeEsperado);
    }

}
