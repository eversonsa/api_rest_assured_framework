package files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import configuration.BaseEndpoint;
import io.restassured.RestAssured;

public class Files {
	
	private BaseEndpoint baseEndpoint = new BaseEndpoint();
	

	public Files(String getUrlApiPath ) {
		this.baseEndpoint.setBaseUrl(getUrlApiPath);
	}
	
	public OutputStream donwloadFile() throws IOException {
		byte[] image = RestAssured.given()
				                       .log().all()
				                  .when().get(this.baseEndpoint.getBaseUrl())
								  .then()
								       .statusCode(200)
								       .extract().asByteArray();
		
		String urlSave = adicionaBaseUrlImage();
        
		File imagens = new File(urlSave);
		OutputStream out = new FileOutputStream(imagens);
		out.write(image);
		out.close();
		return out;
	}

	private String adicionaBaseUrlImage() {
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
		String baseUrlPath = "src/test/resources/images/";
		String urlSave =  baseUrlPath + formatador.format(LocalDateTime.now()) + ".jpg";
		return urlSave;
	}
	
}
