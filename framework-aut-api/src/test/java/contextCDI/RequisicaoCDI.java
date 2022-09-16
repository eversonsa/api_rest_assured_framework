package contextCDI;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RequisicaoCDI {
	
	private Response response;
	private RequestSpecification request;
	

	public void setResponse(Response response) {
		this.response = response;
	}
	
	public Response getResponse() {
		return this.response;
	}
	
	public void setRequest(RequestSpecification request) {
		this.request = request;
	}

	public RequestSpecification getRequest() {
		return this.request;
	}

}
