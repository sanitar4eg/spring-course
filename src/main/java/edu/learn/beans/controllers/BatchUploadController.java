package edu.learn.beans.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.learn.beans.models.Event;
import edu.learn.beans.models.User;
import edu.learn.beans.services.BatchImportService;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("upload")
public class BatchUploadController {

	private final ObjectMapper objectMapper;

	private final BatchImportService batchImportService;

	@Autowired
	public BatchUploadController(ObjectMapper objectMapper, BatchImportService batchImportService) {
		this.objectMapper = objectMapper;
		this.batchImportService = batchImportService;
	}

	@RequestMapping
	public String upload(Model model) {
		model.addAttribute("message", "");
		return "upload";
	}

	@PostMapping("users")
	public String usersUpload(@RequestParam("file") MultipartFile file) throws IOException {
		User[] users = readValues(file.getInputStream(), User[].class);
		batchImportService.importUsers(Arrays.asList(users));

		return "upload";
	}

	@PostMapping("events")
	public String eventsUpload(@RequestParam("file") MultipartFile file) throws IOException {
		Event[] events = readValues(file.getInputStream(), Event[].class);
		batchImportService.importEvents(Arrays.asList(events));

		return "upload";
	}

	private <T> T readValues(InputStream stream, Class<T> clazz) throws IOException {
		return objectMapper.readValue(stream, clazz);
	}


}
