package mca.filesmanagement.front;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import mca.filesmanagement.apigateway.messaging.FileInfo;
import mca.filesmanagement.front.controller.FileUpdatedRequest;

@Service
public class FrontService {
	
	@Value("${gateway.url}")
	private String gatewayUrl;
	
	private RestTemplate restTemplate = new RestTemplate();

	public FrontService() {
		super();
	}
	
	public List<IndexFileResponse> search(){
		String uriTemplate = this.gatewayUrl + "/api/index";
		URI uri = UriComponentsBuilder.fromUriString(uriTemplate).build().toUri();
		ResponseEntity<IndexFileResponse[]> response =
				  restTemplate.getForEntity(
				  uri,
				  IndexFileResponse[].class);
		return Arrays.asList(response.getBody());
	}
	
	public FileInfo findByCode(String token, String code) {
		String uriTemplate = this.gatewayUrl + "/api/files/" + code;
		URI uri = UriComponentsBuilder.fromUriString(uriTemplate).build().toUri();

		RequestEntity<Void> requestEntity = RequestEntity.get(uri)
		        .header("Authorization", "Bearer " + token)
		        .build();
		ResponseEntity<FileInfo> response =  this.restTemplate.exchange(requestEntity, FileInfo.class);
		return response.getBody();
	}
	
	public void nextPhase(String token, String processCode, String phaseCode) {
		String uriTemplate = this.gatewayUrl + "/api/bpm/";
		
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("procesCode", processCode);
        body.add("phaseCode", phaseCode);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        
        this.restTemplate.exchange(
        		uriTemplate,
                HttpMethod.POST,
                requestEntity,
                Void.class);
	}
	
	public void update(String token, FileInfo file) {
		String uriTemplate = this.gatewayUrl + "/api/files" ;
		URI uri = UriComponentsBuilder.fromUriString(uriTemplate).build().toUri();
		
		FileUpdatedRequest fileUpdated = new FileUpdatedRequest();
		fileUpdated.setCode(file.getCode());
		fileUpdated.setDescription(file.getDescription());
		fileUpdated.setInitialOption(file.getInitOption());
		
		RequestEntity<FileUpdatedRequest> requestEntity = RequestEntity.put(uri)
				.header("Authorization", "Bearer " + token)
				.body(fileUpdated)
		        ;
        
		this.restTemplate.exchange(requestEntity, Void.class);
	}
	
	public List<PhaseResponse> availablePhases(String token, String code){
		String uriTemplate = this.gatewayUrl + "/api/bpm/availablePhases/" + code;
		URI uri = UriComponentsBuilder.fromUriString(uriTemplate).build().toUri();
		
		RequestEntity<Void> requestEntity = RequestEntity.get(uri)
		        .header("Authorization", "Bearer " + token)
		        .build()
		;
		
		ResponseEntity<PhaseResponse[]> response =  this.restTemplate.exchange(requestEntity, PhaseResponse[].class);
		return Arrays.asList(response.getBody());
	}
	
	public List<InitialOptionResponse> initialoptions(String token){
		String uriTemplate = this.gatewayUrl + "/api/files/initialoptions/all";
		URI uri = UriComponentsBuilder.fromUriString(uriTemplate).build().toUri();
		
		RequestEntity<Void> requestEntity = RequestEntity.get(uri)
		        .header("Authorization", "Bearer " + token)
		        .build()
		;
		
		ResponseEntity<InitialOptionResponse[]> response =  this.restTemplate.exchange(requestEntity, InitialOptionResponse[].class);
		return Arrays.asList(response.getBody());
	}
	
    public void createFile(String token, Long initialoption, String code, MultipartFile file) {
    	try {
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	        headers.setBearerAuth(token);
	        
	        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
	        ContentDisposition contentDisposition = ContentDisposition
	                .builder("form-data")
	                .name("file")
	                .filename(file.getName())
	                .build();
	        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
	        HttpEntity<byte[]> fileEntity = new HttpEntity<>(file.getBytes(), fileMap);
	
	        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
	        body.add("file", fileEntity);
	        body.add("code", code);
	        body.add("initialoption", initialoption);
	        
	        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
	        
            this.restTemplate.exchange(
            		this.gatewayUrl + "/api/files",
                    HttpMethod.POST,
                    requestEntity,
                    String.class);
        } 
    	catch (Exception e) {
            e.printStackTrace();
        }
    }
}
