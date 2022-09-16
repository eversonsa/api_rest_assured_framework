package steps;

import configuration.BaseEndpoint;
import configuration.OlaMundoEndpoint;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;

public class OlaMundoStep implements BaseStep{
	
	private OlaMundoEndpoint olaMundoEndpoint = new OlaMundoEndpoint();
	

	@Override
	@Dado("Verificar se a API WC está disponivel")
	public void verificar_se_a_api_está_disponivel() {
		olaMundoEndpoint.setBaseUrl("https://restapi.wcaquino.me/ola");
		String url = olaMundoEndpoint.getBaseUrl();
		olaMundoEndpoint.sendRequest(null, BaseEndpoint.GET_REQUEST, url, null).then().statusCode(200);
	}
	
	@Entao("retonar com a mensagem {string} no status da resposta")
	public void retonar_com_a_mensagem_no_status_da_resposta(String mensagemRetorno) {
		olaMundoEndpoint.verifyResponseKeyValues("Ola Mundo!", mensagemRetorno );
	}

}
