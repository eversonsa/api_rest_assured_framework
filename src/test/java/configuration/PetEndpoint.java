package configuration;

import contextCDI.RequisicaoCDI;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Pet;
import models.Tag;

public class PetEndpoint extends BaseEndpoint{
	
	private final String ENDPOINT = "https://petstore.swagger.io/v2/";
	private final String PET_ENDPOINT_PATH = "pet/";
	private Pet defaultPet;

	public PetEndpoint() {
		super.setBaseUrl(ENDPOINT);
		defaultPet = new Pet();
	}

	public String getPath() {
		return this.PET_ENDPOINT_PATH;
	}

	public Pet getDefaultPet() {
		return this.defaultPet;
	}

	public Response addPetWithBody(RequestSpecification request, String body) {
		String url =  getBaseUrl() + this.getPath();
		request.body(body);
		return sendRequest(request, BaseEndpoint.POST_REQUEST, url, null);
	}
	
	public void addPet(RequisicaoCDI requisicao) {
		requisicao.setRequest(getRequestWithJSONHeaders());
		requisicao.setResponse(addPet(requisicao.getRequest()));
	}
	
	public void addPet(RequisicaoCDI requisicao, Pet pet) {
		requisicao.setPet(pet);
		requisicao.setRequest(getRequestWithJSONHeaders());
		requisicao.setResponse(addPet(requisicao.getRequest(), pet));
	}

	public Response addPet(RequestSpecification request) {
		return addPet(request, getDefaultPet());
	}

	public Response addPet(RequestSpecification request, Pet pet) {
		String url = getBaseUrl() + this.getPath();
		return sendRequest(request, BaseEndpoint.POST_REQUEST, url, pet);
	}

	public Response deletePet(RequestSpecification request) {
		return deletePet(request, getDefaultPet());
	}

	public Response deletePet(RequestSpecification request, Pet pet) {

		String id = pet.getId().toString();
		String url = getBaseUrl() + this.getPath() + id;
		return sendRequest(request, BaseEndpoint.DELETE_REQUEST, url, null);
	}

	public Response getPetById(RequestSpecification request) {
		return getPetById(request, getDefaultPet().getId().toString());
	}

	public Response getPetById(RequestSpecification request, String id) {
		String url = getBaseUrl() + this.getPath() + id;
		return sendRequest(request, BaseEndpoint.GET_REQUEST, url, null);
	}

	public void verifyPetValuesAreAsExpected(Response response, Pet pet) {
		String expectedId = pet.getId().toString();
		String expectedCategoryId = pet.getCategory().getId().toString();
		String expectedCategoryName = pet.getCategory().getName();
		String expectedName = pet.getName();
		String expectedPhotoUrls[] = pet.getPhotoUrls();
		Tag expectedTags[] = pet.getTag();
		String expectedStatus = pet.getStatus();

		// Busca as Tags e os nomes
		String[] expectedTagIds = new String[expectedTags.length];
		String[] expectedTagNamess = new String[expectedTags.length];
		for (int i = 0; i < expectedTags.length; i++) {
			expectedTagIds[i] = expectedTags[i].getId().toString();
			expectedTagNamess[i] = expectedTags[i].getName();
		}

		verifyResponseKeyValues("id", expectedId, response);
		verifyNestedResponseKeyValues("category", "id", expectedCategoryId, response);
		verifyNestedResponseKeyValues("category", "name", expectedCategoryName, response);
		verifyResponseKeyValues("name", expectedName, response);
		verifyNestedArrayValueResponseKeyValues("photoUrls", expectedPhotoUrls, response);
		verifyNestedArrayMapResponseKeyValues("tags", "id", expectedTagIds, response);
		verifyNestedArrayMapResponseKeyValues("tags", "name", expectedTagNamess, response);
		verifyResponseKeyValues("status", expectedStatus, response);
	}
	
	public void verifyPetHasAnId(Response response) {
		String idVal = response.jsonPath().getString("id");
		verifyTrue(idVal != null);
		verifyTrue(!idVal.equalsIgnoreCase(""));
		verifyTrue(Long.parseLong(idVal) > 0);
	}
}
