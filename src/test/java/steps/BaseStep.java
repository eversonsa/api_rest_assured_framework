package steps;

import configuration.BaseEndpoint;
import io.cucumber.java.pt.Dado;


public class BaseStep {

	private BaseEndpoint be;
	
	public BaseStep(String getUrlApiPath) {
		this.be = new BaseEndpoint();
		this.be.setBaseUrl(getUrlApiPath);
	}

	@Dado("Verificar se a API está disponivel")
	public void verificar_se_a_api_está_disponivel() {
		String url = be.getBaseUrl() + "swagger.json";
		be.sendRequest(null, BaseEndpoint.GET_REQUEST, url, null).then().statusCode(200);
	}

}
