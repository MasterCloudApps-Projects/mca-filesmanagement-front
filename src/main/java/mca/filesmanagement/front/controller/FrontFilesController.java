package mca.filesmanagement.front.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import mca.filesmanagement.apigateway.messaging.FileInfo;
import mca.filesmanagement.front.FrontService;

@Controller
public class FrontFilesController extends AbstractController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(FrontFilesController.class);

	@Autowired
	private FrontService frontService;
	
	public FrontFilesController() {
		super();
	}

	@RequestMapping(value="/files/{code}",method=RequestMethod.GET)
	public String fileDetail(@PathVariable(name = "code", required = true) String code, Model model) {
		LOGGER.info("usuario autenticado --> " + this.getToken());
		
		System.out.println("FrontFilesController.fileDetail:" + code);
		
		FileInfo fileInfo = this.frontService.findByCode(this.getToken(), code);
		FileForm fileForm = new FileForm();
		fileForm.setFile(fileInfo);
		fileForm.setAvailablesPhases(frontService.availablePhases(this.getToken(), fileInfo.getPhaseCode()));
		fileForm.setInitialOptions(this.frontService.initialoptions(this.getToken()));
		
		model.addAttribute("fileForm", fileForm);
		return "files";
	}
	
	@PostMapping("/files/save")
	public String submit(@ModelAttribute FileForm fileForm, Model model) {
		LOGGER.info("usuario autenticado --> " + this.getToken());

		this.frontService.update(this.getToken(), fileForm.getFile());
		this.waits(3000);
		
		if (this.hasChanged(fileForm)) {
			LOGGER.debug("hasChanged --> " + fileForm.getPhaseSelected());
			this.frontService.nextPhase(this.getToken(), fileForm.getProcesCode(), fileForm.getPhaseSelected());
			this.waits(3000);
		}
		
		return this.fileDetail(fileForm.getCode(), model);
	}
	
	private boolean hasChanged(FileForm fileForm) {
		return StringUtils.hasLength(fileForm.getPhaseSelected());
	}
	
	private void waits(long miliseconds) {
		try {Thread.sleep(miliseconds);} catch (InterruptedException e) {}
	}
	
	@RequestMapping(value="/filecreate", method=RequestMethod.GET)
	public String createFile(Model model){
		model.addAttribute("initialoptions", this.frontService.initialoptions(this.getToken()));
		return "filecreate";
	}
	
	@RequestMapping(value="/files/create", method=RequestMethod.POST)
	public String create(@RequestParam (required = true , value = "file") MultipartFile file, 
						@RequestParam (required = true , value = "code") String code,
						@RequestParam (required = true , value = "initialoption") Long initialoption) {
		LOGGER.info("usuario autenticado --> " + this.getToken());
		LOGGER.info("Code --> " + code);
		LOGGER.info("file --> "+ file);
		LOGGER.info("initialoption --> "+ initialoption);
		this.frontService.createFile(this.getToken(), initialoption, code, file);
		
		this.waits(3000);
		
		return "redirect:/fileslist";
	}
}