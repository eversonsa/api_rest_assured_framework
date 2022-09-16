package steps;

import configuration.BaseEndpoint;
import configuration.PetEndpoint;
import contextCDI.RequisicaoCDI;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import models.Pet;


public class PetStep implements BaseStep{
	
	private PetEndpoint petEndpoint = new PetEndpoint();
	private RequisicaoCDI requisicao;
    private Pet pet = new Pet();
    
	public PetStep(RequisicaoCDI requisicao) {
		this.requisicao = requisicao;
	}
	
	@Override
	@Dado("Verificar se a API está disponivel")
	public void verificar_se_a_api_está_disponivel() {
		petEndpoint.setBaseUrl("https://petstore.swagger.io/v2/");
		String url = petEndpoint.getBaseUrl() + "swagger.json";
		petEndpoint.sendRequest(null, BaseEndpoint.GET_REQUEST, url, null).then().statusCode(200);
	}
	
	@Quando("add um Pet no sistema")
	public void add_um_pet_no_sistema() {
	    petEndpoint.addPet(requisicao, pet);
	}
	
	@Então("retonar com a mensagem {int} no status da resposta")
	public void retonar_com_a_mensagem_no_status_da_resposta(Integer rc) {
		petEndpoint.verifyResponseStatusValue(requisicao.getResponse(), rc.intValue());
	}


}
