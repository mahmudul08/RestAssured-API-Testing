package Reqress;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

public class HTTPrequest {
	int id;

	@Test(priority = 0)
	void GetListOfuser() {
		given().when().get("https://reqres.in/api/users?page=2")

				.then().statusCode(200).log().all();
	}

	@Test(priority = 1)
	void GetSingleUser() {
		given().when().get("https://reqres.in/api/users/2").then().statusCode(200).log().all();
	}

	@Test(priority = 3)
	void GetSingleuserNotFound() {
		given().when().get("https://reqres.in/api/users/23").then().statusCode(404).log().all();
	}

	@Test(priority = 4)
	void GetListUser() {
		given().when().get("https://reqres.in/api/unknown").then().statusCode(200).log().all();
	}

	@Test(priority = 5)
	void GetSingleListUser() {
		given().when().get("https://reqres.in/api/unknown/2").then().statusCode(200).log().all();
	}

	@Test(priority = 6)
	void GetSingleListUserNotFound() {
		given().when().get("https://reqres.in/api/unknown/23").then().statusCode(404).log().all();
	}

	@Test(priority = 7)
	void PostCreateUser() {
		HashMap data = new HashMap();
		data.put("name", "Mahmudul");
		data.put("job", "SQA");

		id = given().contentType("application/json").body(data).when().post("https://reqres.in/api/users").jsonPath()
				.getInt("id");
//	  .then().statusCode(201).log().all();
	}

	@Test(priority = 8, dependsOnMethods = {"PostCreateUser"})
	void PostUpdateUser() {
		HashMap data = new HashMap();
		data.put("name", "Hasan");
		data.put("job", "Dev");

		given().contentType("application/json").body(data).when().put("https://reqres.in/api/users/" + id).then()
				.statusCode(200).log().all();
	}
	
	@Test(priority = 9)
	void DeleteUser() {

		given().when().delete("https://reqres.in/api/users" + id).then()
				.statusCode(204).log().all();
	}
	
	@Test(priority = 10)
	void PostRegisterSuccess() {
		HashMap reg = new HashMap();
		reg.put("email", "eve.holt@reqres.in");
		reg.put("password", "pistol");

		given().contentType("application/json").body(reg).when().post("https://reqres.in/api/register").then()
				.statusCode(200).log().all();
	}
	
	@Test(priority = 11)
	void PostRegisterUnSuccess() {
		HashMap reg = new HashMap();
		reg.put("email", "Hasan@gmail.com");
		reg.put("password", "");

		given().contentType("application/json").body(reg).when().post("https://reqres.in/api/register").then()
				.statusCode(400).log().all();
	}
	@Test(priority = 12)
	void PostLoginSuccess() {
		HashMap log = new HashMap();
		log.put("email", "eve.holt@reqres.in");
		log.put("password", "cityslicka");

		given().contentType("application/json").body(log).when().post("https://reqres.in/api/login").then()
				.statusCode(200).log().all();
	}
	@Test(priority = 13)
	void PostLoginUnsuccess() {
		HashMap log = new HashMap();
		log.put("email", "peter@klaven");

		given().contentType("application/json").body(log).when().post("https://reqres.in/api/login").then()
				.statusCode(400).log().all();
	}
}
