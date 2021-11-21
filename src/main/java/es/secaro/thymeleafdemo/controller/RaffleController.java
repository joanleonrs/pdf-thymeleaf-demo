package es.secaro.thymeleafdemo.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.secaro.thymeleafdemo.dto.Question;
import es.secaro.thymeleafdemo.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.lowagie.text.DocumentException;

import es.secaro.thymeleafdemo.dto.Raffle;
import es.secaro.thymeleafdemo.service.PdfGenarator;

@Controller
public class RaffleController {

	@Autowired
	private PdfGenarator pdfGenarator;

	private String templateName = "templatePDF.html";
	private String fileName = "reaffle.pdf";

	@GetMapping("/raffle")
	public String raffleForm(final Model model) {
		model.addAttribute("raffle", new Raffle());
		return "raffle";
	}

	@PostMapping("/raffle")
	public String raffleSubmit(@ModelAttribute final Raffle raffle) {
		List<Response> winners = getResponseItems();
		return "result";
	}

	@GetMapping("/raffle/pdf")
	public ResponseEntity<ByteArrayResource> rafflePDF(@ModelAttribute final Raffle raffle, final HttpServletRequest request,
			final HttpServletResponse response) throws DocumentException {

		List<Response> questionnaire = getResponseItems();
		Map<String, Object> mapParameter = new HashMap<String, Object>();
		String titulo01 = "Valor del title 01";

		/* Set mapParameter here to read from the Pdf template */
		mapParameter.put("name", "Proyecto SERNANP");
		mapParameter.put("titleOne", titulo01);
		mapParameter.put("questionnaire", questionnaire);

		ByteArrayOutputStream byteArrayOutputStreamPDF = pdfGenarator.createPdf(templateName, mapParameter, request, response);
		ByteArrayResource inputStreamResourcePDF = new ByteArrayResource(byteArrayOutputStreamPDF.toByteArray());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName).contentType(MediaType.APPLICATION_PDF)
				.contentLength(inputStreamResourcePDF.contentLength()).body(inputStreamResourcePDF);

	}

	private List<Response> getResponseItems() {
		List<Response> dataArray = new ArrayList<Response>() {};
		Question questionItem1 = new Question(1, "¿De que lugar eres?");
		Response responseItem1 = new Response(1, questionItem1, "Soy de Nanria.", "Activo");

		Question questionItem2 = new Question(2, "¿Que edad tienes?");
		Response responseItem2 = new Response(2, questionItem2, "la edad solo es un número sin importancia.", "Inactivo");

		Question questionItem3 = new Question(3, "¿Te gusta programar?");
		Response responseItem3 = new Response(3, questionItem3, "creo que si me gusta desarrollar.", "Activo");

		Question questionItem4 = new Question(4, "¿cual es tu lenguaje de programación favorito?");
		Response responseItem4 = new Response(4, questionItem4, "Javascript es lo mejor que hay.", "Activo");

		Question questionItem5 = new Question(5, "¿another question something or whaterver 05?");
		Response responseItem5 = new Response(5, questionItem5, "Another response, something or whatever 05.", "Activo");

		Question questionItem6 = new Question(6, "¿another question something or whaterver 06?");
		Response responseItem6 = new Response(6, questionItem6, "Another response, something or whatever 06.", "Inactivo");
		
		Question questionItem7 = new Question(7, "¿another question something or whaterver 07?");
		Response responseItem7 = new Response(7, questionItem7, "Another response, something or whatever 07.", "Activo");
		
		Question questionItem8 = new Question(8, "¿another question something or whaterver 08?");
		Response responseItem8 = new Response(8, questionItem8, "Another response, something or whatever 08.", "Activo");
		
		Question questionItem9 = new Question(9, "¿another question something or whaterver 09?");
		Response responseItem9 = new Response(9, questionItem9, "Another response, something or whatever 09.", "Activo");
		
		Question questionItem10 = new Question(10, "¿another question something or whaterver 010?");
		Response responseItem10 = new Response(10, questionItem10, "Another response, something or whatever 010.", "Inactivo");
		
		Question questionItem11 = new Question(11, "¿another question something or whaterver 011?");
		Response responseItem11 = new Response(11, questionItem11, "Another response, something or whatever 011.", "Activo");

		dataArray.add(responseItem1);
		dataArray.add(responseItem2);
		dataArray.add(responseItem3);
		dataArray.add(responseItem4);
		dataArray.add(responseItem5);
		dataArray.add(responseItem6);
		dataArray.add(responseItem7);
		dataArray.add(responseItem8);
		dataArray.add(responseItem9);
		dataArray.add(responseItem10);
		dataArray.add(responseItem11);
		
		return dataArray;
	}

}
