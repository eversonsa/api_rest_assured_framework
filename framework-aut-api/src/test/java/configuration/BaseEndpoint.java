package configuration;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseEndpoint {

	public static final int SUCCESS_STATUS_CODE = 200;

	public static final int GET_REQUEST = 0;
	public static final int POST_REQUEST = 1;
	public static final int DELETE_REQUEST = 2;
	public static final int PUT_REQUEST = 3;

	private String ENDPOINT = null;
	protected  final String PATH = "/api/";
	protected  final Integer PORT = 80;// https porta 443
	protected  final Long MAX_TIMEOUT = 1000L;
	
	
	public void verifyResponseKeyValues(String key, String val, Response response) {
		String keyValue = response.jsonPath().getString(key);
		assertThat(keyValue, is(val));
	}
	
	public void verifyResponseKeyValues(String key, String val) {
		assertThat(key, is(val));
	}
	
	public void verifyTrue(boolean val) {
		assertTrue(val);
	}

	public void verifyFalse(boolean val) {
		assertFalse(val);
		;
	}

	public void verifyResponseStatusValue(Response response, int expectedCode) {
		assertThat(response.getStatusCode(), is(expectedCode));
	}
	public void setBaseUrl(String nomeApi) {
		this.ENDPOINT = nomeApi;
	}
	
	public String getBaseUrl() {
		return this.ENDPOINT;
	}

	public void verifyNestedResponseKeyValues(String nestTedCompnent, String key, String val, Response response) {
		Map<String, String> nestedJSON = response.jsonPath().getMap(nestTedCompnent);
		String actual = String.valueOf(nestedJSON.get(key));
		assertThat(actual, is(val));
	}

	public void verifyNestedArrayValueResponseKeyValues(String nestTedCompnent, String[] val, Response response) {

		ArrayList<Object> nestedArray = (ArrayList<Object>) response.jsonPath().getList(nestTedCompnent);

		String actual;

		for (int i = 0; i < nestedArray.size(); i++) {
			actual = (String) nestedArray.get(i);
			assertThat(actual, is(val[i]));
		}
	}

	public void verifyNestedArrayMapResponseKeyValues(String nestTedCompnent, String key, String[] val, Response response) {
		ArrayList<Object> nestedArray = (ArrayList<Object>) response.jsonPath().getList(nestTedCompnent);

		String actual;
		for (int i = 0; i < nestedArray.size(); i++) {
			Map<String, String> map = ((Map<String, String>) nestedArray.get(i));
			actual = String.valueOf(map.get(key));
			assertThat(actual, is(val[i]));
		}
	}

	public RequestSpecification getRequestWithJSONHeaders() {
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.header("Content-Type", "application/json");
		return requestSpecification;
	}

	public RequestSpecification getRequestWithXMLHeaders() {
		RequestSpecification requestSpecification = RestAssured.given();
		requestSpecification.header("Content-Type", "application/xml");
		return requestSpecification;
	}

	protected JSONObject createJSONPayload(Object pojo) {
		return new JSONObject(pojo);
	}


	public Response sendRequest(RequestSpecification request, int requestType, String url, Object pojo) {
		Response response = null;

		// Adiciona o Json ao corpo da requisição
		if (null != pojo) {
			String payload = createJSONPayload(pojo).toString();
			request.body(payload);
		}

		// Precisa adicionar um tipo de requisição
		switch (requestType) {
		case BaseEndpoint.GET_REQUEST:
			if (null == request) {
				response = RestAssured.when().get(url);
			} else {
				response = request.get(url);
			}
			break;
		case BaseEndpoint.POST_REQUEST:
			if (null == request) {
				response = RestAssured.when().post(url);
			} else {
				response = request.post(url);
			}
			break;
		case BaseEndpoint.DELETE_REQUEST:
			if (null == request) {
				response = RestAssured.when().delete(url);
			} else {
				response = request.delete(url);
			}
			break;
		case BaseEndpoint.PUT_REQUEST:
			if (null == request) {
				response = RestAssured.when().put(url);
			} else {
				response = request.put(url);
			}
			break;
		default:
			if (null == request) {
				response = RestAssured.when().post(url);
			} else {
				response = request.post(url);
			}
			response = request.post(url);
			break;
		}
		return response;
	}

}
