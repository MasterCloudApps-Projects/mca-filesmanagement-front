package mca.filesmanagement.front.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mca.filesmanagement.front.FrontService;

@Controller
public class FrontFilesListController extends AbstractController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(FrontFilesListController.class);
	
	@Autowired
	private FrontService frontService;
	
	public FrontFilesListController() {
		super();
	}
	
	@RequestMapping(value="/fileslist",method=RequestMethod.GET)
	public String fileList(Model model) {
		LOGGER.info("usuario autenticado --> " + this.getToken());
		model.addAttribute("fileslist", frontService.search());
		return "fileslist";
	}
}