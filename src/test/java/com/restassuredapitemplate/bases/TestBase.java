package com.restassuredapitemplate.bases;

import com.github.javafaker.Faker;
import com.restassuredapitemplate.GlobalParameters;
import com.restassuredapitemplate.utils.ExtentReportsUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
import java.util.Locale;

public abstract class TestBase {
    public Faker faker;

    @BeforeSuite
    public void beforSuite(){
        new GlobalParameters();
        ExtentReportsUtils.createReport();
        //AutenticacaoSteps.gerarToken(GlobalParameters.AUTHENTICATOR_USER, GlobalParameters.AUTHENTICATOR_PASSWORD); ==> caso a geração de token deva ser feita quando iniciar a bateria de testes
    }

    @BeforeMethod
    public void beforeTest(Method method){
        faker = new Faker(new Locale("pt-BR"));
        ExtentReportsUtils.addTest(method.getName(), method.getDeclaringClass().getSimpleName());
        //AutenticacaoSteps.gerarToken(GlobalParameters.AUTHENTICATOR_USER, GlobalParameters.AUTHENTICATOR_PASSWORD); ==> caso a geração de token deva ser feita antes de iniciar cada teste
    }

    @AfterMethod
    public void afterTest(ITestResult result){
        ExtentReportsUtils.addTestResult(result);
    }

    @AfterSuite
    public void afterSuite(){
        ExtentReportsUtils.generateReport();
    }
}
