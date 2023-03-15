package com.juaracoding.CSmaster.service;
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
import com.juaracoding.CSmaster.handler.ResourceNotFoundException;
import com.juaracoding.CSmaster.handler.ResponseHandler;
import com.juaracoding.CSmaster.model.Category;
import com.juaracoding.CSmaster.model.Complain;
import com.juaracoding.CSmaster.model.Demo;
import com.juaracoding.CSmaster.repo.CategoryRepo;
import com.juaracoding.CSmaster.repo.ComplainRepo;
import com.juaracoding.CSmaster.repo.DemoRepo;
import com.juaracoding.CSmaster.utils.ConstantMessage;
import com.juaracoding.CSmaster.utils.LoggingFile;
import com.juaracoding.CSmaster.utils.TransformToDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    KODE MODUL 11
 */
@Service
@Transactional
public class ComplainService {

	private ComplainRepo complainRepo;

	private String[] strExceptionArr = new String[2];
	@Autowired
	private ModelMapper modelMapper;

	private Map<String, Object> objectMapper = new HashMap<String, Object>();

	private TransformToDTO transformToDTO = new TransformToDTO();

	private Map<String, String> mapColumnSearch = new HashMap<String, String>();
	private Map<Integer, Integer> mapItemPerPage = new HashMap<Integer, Integer>();
	private String[] strColumnSearch = new String[2];
	private java.lang.String String;

	@Autowired
	public ComplainService(ComplainRepo complainRepo) {
		mapColumn();
//        listItemPerPage();
		strExceptionArr[0] = "ComplainService";
		this.complainRepo = complainRepo;
	}


	public Map<String, Object> saveComplain(Complain complain, WebRequest request) {
		String strMessage = ConstantMessage.SUCCESS_SAVE;
		Object strUserIdz = request.getAttribute("USR_ID", 1);

		try {
			if (strUserIdz == null) {
				return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_FLOW_INVALID,
						HttpStatus.NOT_ACCEPTABLE, null, "FV03001", request);
			}
			complain.setDescription(Integer.parseInt(strUserIdz.toString()));
			complain.setNoInvoice("");
			complainRepo.save(complain);
		} catch (Exception e) {
			strExceptionArr[1] = "saveComplain(Complain complain, WebRequest request) --- LINE 67";
			LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfig.getFlagLogging());
			return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_SAVE_FAILED,
					HttpStatus.BAD_REQUEST,
					transformToDTO.transformObjectDataEmpty(objectMapper, mapColumnSearch),
					"FE03001", request);
		}
		return new ResponseHandler().generateModelAttribut(strMessage,
				HttpStatus.CREATED,
				transformToDTO.transformObjectDataSave(objectMapper, complain.getIdComplain(), mapColumnSearch),
				null, request);
	}

	public Map<String, Object> updateComplain(Long idComplain, Complain complain, WebRequest request) {
		String strMessage = ConstantMessage.SUCCESS_SAVE;
		Object strUserIdz = request.getAttribute("USR_ID", 1);

		try {
			Complain nextComplain = complainRepo.findById(idComplain).orElseThrow(
					() -> null
			);

			if (nextComplain == null) {
				return new ResponseHandler().generateModelAttribut(ConstantMessage.WARNING_COMPLAIN_NOT_EXISTS,
						HttpStatus.NOT_ACCEPTABLE,
						transformToDTO.transformObjectDataEmpty(objectMapper, mapColumnSearch),
						"FV03002", request);
			}
			if (strUserIdz == null) {
				return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_FLOW_INVALID,
						HttpStatus.NOT_ACCEPTABLE,
						null,
						"FV03003", request);
			}

			nextComplain.setNamaBarang(complain.getNamaBarang());
			nextComplain.setDescription(Integer.parseInt(strUserIdz.toString()));
			nextComplain.setNoInvoice("");

		} catch (Exception e) {
			strExceptionArr[1] = "updateCategory(Long idCategory,Category category, WebRequest request) --- LINE 92";
			LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfig.getFlagLogging());
			return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_SAVE_FAILED,
					HttpStatus.BAD_REQUEST,
					transformToDTO.transformObjectDataEmpty(objectMapper, mapColumnSearch),
					"FE03002", request);
		}
		return new ResponseHandler().generateModelAttribut(strMessage,
				HttpStatus.CREATED,
				transformToDTO.transformObjectDataEmpty(objectMapper, mapColumnSearch),
				null, request);
	}

	public Map<String, Object> deleteComplain(Long idComplain, WebRequest request) {
		String strMessage = ConstantMessage.SUCCESS_SAVE;
		Object strUserIdz = request.getAttribute("USR_ID", 1);

		try {
			Complain nextComplain = complainRepo.findById(idComplain).orElseThrow(
					() -> null
			);

			if (nextComplain == null) {
				return new ResponseHandler().generateModelAttribut(ConstantMessage.WARNING_COMPLAIN_NOT_EXISTS,
						HttpStatus.NOT_ACCEPTABLE,
						transformToDTO.transformObjectDataEmpty(objectMapper, mapColumnSearch),
						"FV03002", request);
			}
			if (strUserIdz == null) {
				return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_FLOW_INVALID,
						HttpStatus.NOT_ACCEPTABLE,
						null,
						"FV03003", request);
			}

			nextComplain.setImage((String));
			nextComplain.setDescription(Integer.parseInt(strUserIdz.toString()));
			nextComplain.setNoInvoice("");

		} catch (Exception e) {
			strExceptionArr[1] = "updateComplain(Long idComplain,Complain complain, WebRequest request) --- LINE 92";
			LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfig.getFlagLogging());
			return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_SAVE_FAILED,
					HttpStatus.BAD_REQUEST,
					transformToDTO.transformObjectDataEmpty(objectMapper, mapColumnSearch),
					"FE03002", request);
		}
		return new ResponseHandler().generateModelAttribut(strMessage,
				HttpStatus.CREATED,
				transformToDTO.transformObjectDataEmpty(objectMapper, mapColumnSearch),
				null, request);
	}

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> saveUploadFileComplain(List<Complain> listComplain,
													  MultipartFile multipartFile,
													  WebRequest request) throws Exception {
		List<Complain> listComplainResult = null;
		String strMessage = ConstantMessage.SUCCESS_SAVE;

		try {
			listComplainResult = complainRepo.saveAll(listComplain);
			if (listComplainResult.size() == 0) {
				strExceptionArr[1] = "saveUploadFile(List<Complain> listComplain, MultipartFile multipartFile, WebRequest request) --- LINE 82";
				LoggingFile.exceptionStringz(strExceptionArr, new ResourceNotFoundException("FILE KOSONG"), OtherConfig.getFlagLogging());
				return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_EMPTY_FILE + " -- " + multipartFile.getOriginalFilename(),
						HttpStatus.BAD_REQUEST, null, "FV03004", request);
			}
		} catch (Exception e) {
			strExceptionArr[1] = "saveUploadFile(List<Complain> listComplain, MultipartFile multipartFile, WebRequest request) --- LINE 88";
			LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfig.getFlagLogging());
			return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_SAVE_FAILED,
					HttpStatus.BAD_REQUEST, null, "FE03002", request);
		}
		return new ResponseHandler().
				generateModelAttribut(strMessage,
						HttpStatus.CREATED,
						transformToDTO.transformObjectDataEmpty(objectMapper, mapColumnSearch),
						null,
						request);
	}

	public Map<String, Object> findAllComplain(Pageable pageable, WebRequest request) {
		List<ComplainDTO> listComplainDTO = null;
		Map<String, Object> mapResult = null;
		Page<Complain> pageComplain = null;
		List<Complain> listComplain = null;

		try {
			pageComplain = complainRepo.findByIsDelete(pageable, (byte) 1);
			listComplain = pageComplain.getContent();
			if (listComplain.size() == 0) {
				return new ResponseHandler().
						generateModelAttribut(ConstantMessage.WARNING_DATA_EMPTY,
								HttpStatus.OK,
								transformToDTO.transformObjectDataEmpty(objectMapper, pageable, mapColumnSearch),//HANDLE NILAI PENCARIAN
								"FV03005",
								request);
			}
			listComplainDTO = modelMapper.map(listComplain, new TypeToken<List<ComplainDTO>>() {
			}.getType());
			mapResult = transformToDTO.transformObject(objectMapper, listComplainDTO, pageComplain, mapColumnSearch);

		} catch (Exception e) {
			strExceptionArr[1] = "findAllComplain(Pageable pageable, WebRequest request) --- LINE 178";
			LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfig.getFlagLogging());
			return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_INTERNAL_SERVER,
					HttpStatus.INTERNAL_SERVER_ERROR,
					transformToDTO.transformObjectDataEmpty(objectMapper, pageable, mapColumnSearch),//HANDLE NILAI PENCARIAN
					"FE03003", request);
		}

		return new ResponseHandler().
				generateModelAttribut(ConstantMessage.SUCCESS_FIND_BY,
						HttpStatus.OK,
						mapResult,
						null,
						null);
	}

	public Map<String, Object> findByPage(Pageable pageable, WebRequest request, String columFirst, String valueFirst) {
		Page<Complain> pageComplain = null;
		List<Complain> listComplain = null;
		List<ComplainDTO> listComplainDTO = null;
		Map<String, Object> mapResult = null;

		try {
			if (columFirst.equals("id")) {
				try {
					Long.parseLong(valueFirst);
				} catch (Exception e) {
					strExceptionArr[1] = "findByPage(Pageable pageable,WebRequest request,String columFirst,String valueFirst) --- LINE 209";
					LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfig.getFlagLogging());
					return new ResponseHandler().
							generateModelAttribut(ConstantMessage.WARNING_DATA_EMPTY,
									HttpStatus.OK,
									transformToDTO.transformObjectDataEmpty(objectMapper, pageable, mapColumnSearch),//HANDLE NILAI PENCARIAN
									"FE03004",
									request);
				}
			}
			pageComplain = getDataByValue(pageable, columFirst, valueFirst);
			listComplain = pageComplain.getContent();
			if (listComplain.size() == 0) {
				return new ResponseHandler().
						generateModelAttribut(ConstantMessage.WARNING_DATA_EMPTY,
								HttpStatus.OK,
								transformToDTO.transformObjectDataEmpty(objectMapper, pageable, mapColumnSearch),//HANDLE NILAI PENCARIAN EMPTY
								"FV03006",
								request);
			}
			listComplainDTO = modelMapper.map(listComplain, new TypeToken<List<ComplainDTO>>() {
			}.getType());
			mapResult = transformToDTO.transformObject(objectMapper, listComplainDTO, pageComplain, mapColumnSearch);
			System.out.println("LIST DATA => " + listComplainDTO.size());
		} catch (Exception e) {
			strExceptionArr[1] = "findByPage(Pageable pageable,WebRequest request,String columFirst,String valueFirst) --- LINE 237";
			LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfig.getFlagLogging());
			return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_FLOW_INVALID,
					HttpStatus.INTERNAL_SERVER_ERROR,
					transformToDTO.transformObjectDataEmpty(objectMapper, pageable, mapColumnSearch),
					"FE03005", request);
		}
		return new ResponseHandler().
				generateModelAttribut(ConstantMessage.SUCCESS_FIND_BY,
						HttpStatus.OK,
						mapResult,
						null,
						request);
	}

	public Map<String, Object> findById(Long id, WebRequest request) {
		Complain complain = complainRepo.findById(id).orElseThrow(
				() -> null
		);
		if (complain == null) {
			return new ResponseHandler().generateModelAttribut(ConstantMessage.WARNING_COMPLAIN_NOT_EXISTS,
					HttpStatus.NOT_ACCEPTABLE,
					transformToDTO.transformObjectDataEmpty(objectMapper, mapColumnSearch),
					"FV03005", request);
		}
		ComplainDTO complainDTO = modelMapper.map(complain, new TypeToken<ComplainDTO>() {
		}.getType());
		return new ResponseHandler().
				generateModelAttribut(ConstantMessage.SUCCESS_FIND_BY,
						HttpStatus.OK,
						complainDTO,
						null,
						request);
	}
	private void mapColumn() {
		mapColumnSearch.put("id","ID COMPLAIN");
		mapColumnSearch.put("nama","NAMA COMPLAIN");
	}

	private Page<Complain> getDataByValue(Pageable pageable, String paramColumn, String paramValue) {
		if (paramValue.equals("")) {
			return complainRepo.findByIsDelete(pageable, (byte) 1);
		}
		if (paramColumn.equals("id")) {
			return complainRepo.findByIsDeleteAndIdComplainContainsIgnoreCase(pageable, (byte) 1, Long.parseLong(paramValue));
		} else if (paramColumn.equals("nama")) {
			return complainRepo.findByIsDeleteAndNamaBarangContainsIgnoreCase(pageable, (byte) 1, paramValue);
		}

		return complainRepo.findByIsDelete(pageable, (byte) 1);
	}
}


