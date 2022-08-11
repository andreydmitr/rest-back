package guru.qa.restback;

import guru.qa.restback.domain.ReturnInfo;
import guru.qa.restback.domain.SalaryInfo;
import guru.qa.restback.domain.ShowUserSalaryInfo;
import guru.qa.restback.domain.UserInfo;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;

import static guru.qa.restback.data.UserData.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RestBackApplicationTests {
    // random generator
    static SecureRandom randomGen;

    @BeforeAll
    static void beforeAllTests() {
        //System.out.println("*** BEFOREALL");

        // init random
        byte byteArray[] = {
                (byte) System.currentTimeMillis(),
                (byte) System.currentTimeMillis()
        };
        randomGen = new SecureRandom(byteArray);

        RestAssured.baseURI = "http://localhost:8080";
    }

    private RequestSpecification spec = with()
            .basePath("/")
            .contentType(ContentType.JSON);


    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("getSalaryById - positive test : right userId")
    void getSalaryByIdTestPositive() {

        int li = randomGen.nextInt(userInfo.size());
        UserInfo userInfo1 = userInfo.get(li);
        step("Выбор случайного пользователя", () -> {
            assertNotNull(userInfo1.getUserId());
        });

        final StringBuffer showUserSalaryInfo= new StringBuffer();
        step("Получим salary для userId: " + userInfo1.getUserId(), () -> {
            ObjectMapper mapper = new ObjectMapper();
            showUserSalaryInfo.append(
                    mapper.writeValueAsString(given()
                    .spec(spec)
                    .formParam("userId", userInfo1.getUserId().toString())
                    .when()
                    .log().all()
                    .get("user/getSalary")

                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract().as(ShowUserSalaryInfo.class)));
        });

        step("Проверим правильность полученных данных", () -> {
            ObjectMapper mapper = new ObjectMapper();
            ShowUserSalaryInfo showUserSalaryInfo1 = mapper.readValue(
                    showUserSalaryInfo.toString(),
                            ShowUserSalaryInfo.class);


            assertEquals(userInfo1.getBirthDay(), showUserSalaryInfo1.getBirthDay());

            assertEquals(userInfo1.getName(), showUserSalaryInfo1.getName());
            assertEquals(userInfo1.getSerName(), showUserSalaryInfo1.getSerName());

            SalaryInfo salaryInfo1 = getSalaryInfoById(userInfo1.getUserId());
            assertEquals(salaryInfo1.getReturnInfo().getCode(), ReturnInfo.codes.Ok);
            assertEquals(salaryInfo1.getSalaryUsd(), showUserSalaryInfo1.getSalaryUsd());

        });
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("getSalaryById - negative test : false userId")
    void getSalaryByIdTestNegative() {

        int li = randomGen.nextInt(userInfo.size());
        UserInfo userInfo1 = userInfo.get(li);
        step("Выбор случайного пользователя", () -> {
            assertNotNull(userInfo1.getUserId());
        });
        step("Получим salary для userId: " + userInfo1.getUserId(), () -> {
            ShowUserSalaryInfo showUserSalaryInfo = given()
                    .spec(spec)
                    .formParam("userId", userInfo1.getUserId().toString())
                    .when()
                    .log().all()
                    .get("user/getSalary")

                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract().as(ShowUserSalaryInfo.class);

            assertEquals(userInfo1.getBirthDay(), showUserSalaryInfo.getBirthDay());
            assertEquals(userInfo1.getName(), showUserSalaryInfo.getName());
            assertEquals(userInfo1.getSerName(), showUserSalaryInfo.getSerName());

            SalaryInfo salaryInfo1 = getSalaryInfoById(userInfo1.getUserId());
            assertEquals(salaryInfo1.getReturnInfo().getCode(), ReturnInfo.codes.Ok);
            assertEquals(salaryInfo1.getSalaryUsd(), showUserSalaryInfo.getSalaryUsd());

        });


    }

}
