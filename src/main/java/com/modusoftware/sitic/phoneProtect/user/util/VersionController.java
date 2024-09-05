package com.modusoftware.sitic.phoneProtect.user.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VersionController {
	
	@Autowired
	private BuildProperties buildProperties;

	@GetMapping(value = {"/version", "/service-info"})
	public String greetingForm(Model model) {
		model.addAttribute("version", this.buildProperties.getVersion());
		model.addAttribute("name", this.buildProperties.getGroup() + "." + this.buildProperties.getArtifact());
		return "version";
	}
}
