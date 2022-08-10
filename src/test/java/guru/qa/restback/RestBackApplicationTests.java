package guru.qa.restback;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest
class RestBackApplicationTests {

	@Test
	@Severity(SeverityLevel.BLOCKER)
	@DisplayName("setSalaryById - positive test : right userId")
	void setSalaryByIdTestPositive() {
		given()
				.formParam("userId","1")
				.when()
				.log().all()
				.get("user/getSalary")

				.then()
				.log().all()
				.statusCode(200)
		;
	}

}
