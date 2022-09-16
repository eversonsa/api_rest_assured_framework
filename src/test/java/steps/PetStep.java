package steps;

import configuration.PetEndpoint;
import contextCDI.RequisicaoCDI;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;


public class PetStep extends BaseStep{
	
	private PetEndpoint petEndpoint = new PetEndpoint();
	private RequisicaoCDI requisicao;

	public PetStep(RequisicaoCDI requisicao) {
		super("https://petstore.swagger.io/v2/");
		this.requisicao = requisicao;
	}
	
	@Quando("add um Pet no sistema")
	public void add_um_pet_no_sistema() {
	    petEndpoint.addPet(requisicao);
	}
	
	@Então("retonar com a mensagem {int} no status da resposta")
	public void retonar_com_a_mensagem_no_status_da_resposta(Integer rc) {
		petEndpoint.verifyResponseStatusValue(requisicao.getResponse(), rc.intValue());
	}


}
