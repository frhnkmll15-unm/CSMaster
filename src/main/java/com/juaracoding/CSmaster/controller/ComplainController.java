package com.juaracoding.CSmaster.controller;
/*
Created By IntelliJ IDEA 2022.1.3 (Community Edition)
Build #IC-221.5921.22, built on June 21, 2022
@Author Farhan a.k.a Muhammad Farhan Kamil
Java Developer
Created On 15/03/2023 12:44
@Last Modified 15/03/2023 12:44
Version 1.0
*/


import com.juaracoding.CSmaster.configuration.OtherConfig;
import com.juaracoding.CSmaster.dto.ComplainDTO;
import com.juaracoding.CSmaster.dto.DemoDTO;
import com.juaracoding.CSmaster.model.Category;
import com.juaracoding.CSmaster.model.Complain;
import com.juaracoding.CSmaster.model.Demo;
import com.juaracoding.CSmaster.service.CategoryService;
import com.juaracoding.CSmaster.service.ComplainService;
import com.juaracoding.CSmaster.service.DemoService;
import com.juaracoding.CSmaster.utils.ConstantMessage;
import com.juaracoding.CSmaster.utils.ManipulationMap;
import com.juaracoding.CSmaster.utils.MappingAttribute;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/item")
public class ComplainController {

	private ComplainService complainService;

	@Autowired
	private ModelMapper modelMapper;

	private Map<String,Object> objectMapper = new HashMap<String,Object>();
	private Map<String,String> mapSorting = new HashMap<String,String>();

	private List<Complain> lsCPUpload = new ArrayList<Complain>();

	private String [] strExceptionArr = new String[2];

	private MappingAttribute mappingAttribute = new MappingAttribute();

	@Autowired
	public ComplainController(ComplainService complainService) {
		strExceptionArr[0] = "ComplainController";
		mapSorting();
		this.complainService = complainService;
	}

	private void mapSorting()
	{
		mapSorting.put("id","idComplain");
		mapSorting.put("nama","namaBarang");
	}

	@GetMapping("/v1/complain/new")
	public String createComplain(Model model, WebRequest request)
	{
		if(OtherConfig.getFlagSessionValidation().equals("y"))
		{
			mappingAttribute.setAttribute(model,objectMapper,request);//untuk set session
			if(request.getAttribute("USR_ID",1)==null){
				return "redirect:/api/check/logout";
			}
		}
		model.addAttribute("complain", new ComplainDTO());
		return "complain/create_complain";
	}

	@GetMapping("/v1/complain/edit/{id}")
	public String editComplain(Model model, WebRequest request, @PathVariable("id") Long id)
	{
		if(OtherConfig.getFlagSessionValidation().equals("y"))
		{
			mappingAttribute.setAttribute(model,objectMapper,request);//untuk set session
			if(request.getAttribute("USR_ID",1)==null){
				return "redirect:/api/check/logout";
			}
		}
		objectMapper = complainService.findById(id,request);
		ComplainDTO complainDTO= (objectMapper.get("data")==null?null:(ComplainDTO) objectMapper.get("data"));
		if((Boolean) objectMapper.get("success"))
		{
			ComplainDTO complainDTOForSelect = (ComplainDTO) objectMapper.get("data");
			model.addAttribute("complain", complainDTO);
			return "complain/edit_complain";

		}
		else
		{
			model.addAttribute("complain", new ComplainDTO());
			return "redirect:/api/item/complain/default";
		}
	}
	@PostMapping("/v1/complain/new")
	public String newComplain(@ModelAttribute(value = "category")
							  @Valid ComplainDTO complainDTO
			, BindingResult bindingResult
			, Model model
			, WebRequest request
	)
	{
		if(OtherConfig.getFlagSessionValidation().equals("y"))
		{
			mappingAttribute.setAttribute(model,objectMapper,request);//untuk set session
			if(request.getAttribute("USR_ID",1)==null){
				return "redirect:/api/check/logout";
			}
		}

		/* START VALIDATION */
		if(bindingResult.hasErrors())
		{
			model.addAttribute("complain",complainDTO);
			model.addAttribute("status","error");

			return "complain/create_complain";
		}
		Boolean isValid = true;

		if(!isValid)
		{
			model.addAttribute("complain",complainDTO);
			return "complain/create_complain";
		}
		/* END OF VALIDATION */

		Complain complain = modelMapper.map(complainDTO, new TypeToken<Complain>() {}.getType());
		objectMapper = complainService.saveComplain(complain,request);
		if(objectMapper.get("message").toString().equals(ConstantMessage.ERROR_FLOW_INVALID))//AUTO LOGOUT JIKA ADA PESAN INI
		{
			return "redirect:/api/check/logout";
		}

		if((Boolean) objectMapper.get("success"))
		{
			mappingAttribute.setAttribute(model,objectMapper);
			model.addAttribute("message","DATA BERHASIL DISIMPAN");
			Long idDataSave = objectMapper.get("idDataSave")==null?1:Long.parseLong(objectMapper.get("idDataSave").toString());
			return "redirect:/api/item/v1/complain/fbpsb/0/asc/idComplain?columnFirst=idComplain&valueFirst="+idDataSave+"&sizeComponent=5";//LANGSUNG DITAMPILKAN FOKUS KE HASIL EDIT USER TADI
		}
		else
		{
			mappingAttribute.setErrorMessage(bindingResult,objectMapper.get("message").toString());
			model.addAttribute("complain",new ComplainDTO());
			model.addAttribute("status","error");
			return "complain/create_complain";
		}
	}

	@PostMapping("/v1/complain/edit/{id}")
	public String doRegis(@ModelAttribute("complain")
						  @Valid ComplainDTO complainDTO
			, BindingResult bindingResult
			, Model model
			, WebRequest request
			, @PathVariable("id") Long id
	)
	{
		if(OtherConfig.getFlagSessionValidation().equals("y"))
		{
			mappingAttribute.setAttribute(model,objectMapper,request);//untuk set session
			if(request.getAttribute("USR_ID",1)==null){
				return "redirect:/api/check/logout";
			}
		}
		/* START VALIDATION */
		if(bindingResult.hasErrors())
		{
			model.addAttribute("complain",complainDTO);
			return "complain/edit_complain";
		}
		Boolean isValid = true;

		if(!isValid)
		{
			model.addAttribute("complain",complainDTO);

			return "complain/edit_complain";
		}
		/* END OF VALIDATION */

		Complain complain = modelMapper.map(complainDTO, new TypeToken<Complain>() {}.getType());
		objectMapper = complainService.updateComplain(id,complain,request);
		if(objectMapper.get("message").toString().equals(ConstantMessage.ERROR_FLOW_INVALID))//AUTO LOGOUT JIKA ADA PESAN INI
		{
			return "redirect:/api/check/logout";
		}

		if((Boolean) objectMapper.get("success"))
		{
			mappingAttribute.setAttribute(model,objectMapper);
			model.addAttribute("complain",new ComplainDTO());
			return "redirect:/api/item/v1/complain/fbpsb/0/asc/idComplain?columnFirst=idComplain&valueFirst="+id+"&sizeComponent=5";//LANGSUNG DITAMPILKAN FOKUS KE HASIL EDIT USER TADI
		}
		else
		{
			mappingAttribute.setErrorMessage(bindingResult,objectMapper.get("message").toString());
			model.addAttribute("complain",new ComplainDTO());
			return "complain/edit_complain";
		}
	}


	@GetMapping("/v1/complain/default")
	public String getDefaultData(Model model,WebRequest request)
	{
		if(OtherConfig.getFlagSessionValidation().equals("y"))
		{
			mappingAttribute.setAttribute(model,objectMapper,request);//untuk set session
			if(request.getAttribute("USR_ID",1)==null){
				return "redirect:/api/check/logout";
			}
		}
		Pageable pageable = PageRequest.of(0,5, Sort.by("idComplain"));
		objectMapper = complainService.findAllComplain(pageable,request);
		mappingAttribute.setAttribute(model,objectMapper,request);

		model.addAttribute("complain",new ComplainDTO());
		model.addAttribute("sortBy","idComplain");
		model.addAttribute("currentPage",1);
		model.addAttribute("asc","asc");
		model.addAttribute("columnFirst","");
		model.addAttribute("valueFirst","");
		model.addAttribute("sizeComponent",5);
		return "/complain/complain";
	}

	@GetMapping("/v1/complain/fbpsb/{page}/{sort}/{sortby}")
	public String findByComplain(
			Model model,
			@PathVariable("page") Integer pagez,
			@PathVariable("sort") String sortz,
			@PathVariable("sortby") String sortzBy,
			@RequestParam String columnFirst,
			@RequestParam String valueFirst,
			@RequestParam String sizeComponent,
			WebRequest request
	){
		sortzBy = mapSorting.get(sortzBy);
		sortzBy = sortzBy==null?"idComplain":sortzBy;
		Pageable pageable = PageRequest.of(pagez==0?pagez:pagez-1,Integer.parseInt(sizeComponent.equals("")?"5":sizeComponent), sortz.equals("asc")?Sort.by(sortzBy):Sort.by(sortzBy).descending());
		objectMapper = complainService.findByPage(pageable,request,columnFirst,valueFirst);
		mappingAttribute.setAttribute(model,objectMapper,request);
		model.addAttribute("complain",new ComplainDTO());
		model.addAttribute("currentPage",pagez==0?1:pagez);
		model.addAttribute("sortBy", ManipulationMap.getKeyFromValue(mapSorting,sortzBy));
		model.addAttribute("columnFirst",columnFirst);
		model.addAttribute("valueFirst",valueFirst);
		model.addAttribute("sizeComponent",sizeComponent);

		return "/complain/complain";
	}
	@GetMapping("/v1/complain/delete/{id}")
	public String doRegis(Model model
			, WebRequest request
			, @PathVariable("id") Long id
	)
	{
		if(OtherConfig.getFlagSessionValidation().equals("y"))
		{
			mappingAttribute.setAttribute(model,objectMapper,request);//untuk set session
			if(request.getAttribute("USR_ID",1)==null){
				return "redirect:/api/check/logout";
			}
		}
		objectMapper = complainService.deleteComplain(id,request);
		if(objectMapper.get("message").toString().equals(ConstantMessage.ERROR_FLOW_INVALID))//AUTO LOGOUT JIKA ADA PESAN INI
		{
			return "redirect:/api/check/logout";
		}

		if((Boolean) objectMapper.get("success"))
		{
			mappingAttribute.setAttribute(model,objectMapper);
			model.addAttribute("complain",new ComplainDTO());
			return "redirect:/api/item/v1/complain/fbpsb/0/asc/idComplain?columnFirst=idComplain&valueFirst="+id+"&sizeComponent=5";//LANGSUNG DITAMPILKAN FOKUS KE HASIL EDIT USER TADI
		}
		else
		{
//            mappingAttribute.setErrorMessage(bindingResult,objectMapper.get("message").toString());
			model.addAttribute("complain",new ComplainDTO());
			return "/complain/complain";
		}
	}
}
