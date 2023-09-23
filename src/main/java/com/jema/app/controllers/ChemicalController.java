/* 
*  Project : JemaApp
*  Author  : Raj Khatri
*  Date    : 20-May-2023
*
*/

package com.jema.app.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jema.app.dto.ChemicalDTO;
import com.jema.app.dto.ChemicalListView;
import com.jema.app.dto.PageRequestDTO;
import com.jema.app.dto.PageResponseDTO;
import com.jema.app.entities.Chemical;
import com.jema.app.repositories.ChemicalRepository;
import com.jema.app.response.GenericResponse;
import com.jema.app.response.ResponseErrorChemical;
import com.jema.app.service.ChemicalService;
import com.jema.app.utils.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Chemical Controller")
@RestController

public class ChemicalController extends ApiController {

	protected Logger logger = LoggerFactory.getLogger(ChemicalController.class);

	@Autowired
	ChemicalService chemicalService;

	@Autowired
	ChemicalRepository chemicalRepository;

	/*
	 * ======================== Chemical ADD ==================================
	 */
	
	@CrossOrigin
	@PostMapping(value = CHEMICAL_ADD, produces = "application/json")
	public ResponseEntity<?> add(@Valid @RequestBody ChemicalDTO chemicalDTO) {
		logger.info("Request: In Chemical Controller for Add Chemical: {}", chemicalDTO);
		GenericResponse genericResponse = new GenericResponse();

		Chemical chemical = new Chemical();
		BeanUtils.copyProperties(chemicalDTO, chemical);
		try {
			chemical.setCreateTime(new Date());
			chemical.setUpdateTime(new Date());

			Long id = chemicalService.save(chemical);
			chemicalDTO.setId(id);

			return new ResponseEntity<GenericResponse>(
					genericResponse.getResponse(chemicalDTO, "Chemical successfully added", HttpStatus.OK),
					HttpStatus.OK);
		} catch (ResponseStatusException e) {
			ResponseErrorChemical errorResponse = new ResponseErrorChemical();
			errorResponse.setStatus(HttpStatus.CONFLICT.value());
			errorResponse.setError(HttpStatus.CONFLICT.getReasonPhrase());
			errorResponse.setMessage(e.getReason());
			errorResponse.setTimestamp(new Date());
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		}
	}

	/*
	 * ======================== Chemical Edit/Update ========================
	 */
	
	@CrossOrigin
	@PutMapping(value = CHEMICAL_UPDATE, produces = "application/json")
	public ResponseEntity<?> update(@PathVariable(name = "id", required = true) Long id,
			@Valid @RequestBody ChemicalDTO chemicalDTO) {
		logger.info("Request: In Chemical Controller for Update Chemical: {}", chemicalDTO);

		try {
			Chemical existingChemical = chemicalService.findById(id);

			if (existingChemical == null) {
				return ResponseEntity.notFound().build();
			}

			chemicalService.updateChemicalFields(existingChemical, chemicalDTO);
			return ResponseEntity.ok().build();
		} catch (ResponseStatusException ex) {
			if (ex.getStatus() == HttpStatus.CONFLICT) {
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(new GenericResponse().getResponse(null, ex.getReason(), HttpStatus.CONFLICT));
			} else {
				throw ex; // Re-throw if it's not a conflict status
			}
		}
	}

	/*
	 * ======================== Get All Chemicals ==================================
	 */

	@ApiOperation(value = "Get All Chemical with Pagination", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Fetched."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PostMapping(value = CHEMICAL_FIND_ALL, produces = "application/json")
	public ResponseEntity<GenericResponse> getAllChemical(@Valid @RequestBody PageRequestDTO pageRequestDTO) {
		logger.info("Rest request to get all Chemical");

		Long recordsCount = 0l;

		List<ChemicalListView> dataList = chemicalService.findAll(pageRequestDTO);

		try {
			recordsCount = dataList.get(0).getTotal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object obj = (new PageResponseDTO()).getRespose(dataList, recordsCount);

		return onSuccess(obj, Constants.CHEMICAL_FETCHED);
	}

	/*
	 * ======================== Find One Chemical ========================
	 */

	@ApiOperation(value = "Find Chemical By Id", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Data"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@GetMapping(value = CHEMICAL_FIND_ONE, produces = "application/json")
	public ResponseEntity<GenericResponse> findById(@PathVariable(name = "id") Long id) {
		logger.info("Request: In Chemical Controller Find Chemical By Id :{} ", id);

		GenericResponse response = new GenericResponse();
		Chemical chemical = chemicalService.findById(id);
		String msg = chemical != null ? "Chemical Found" : "No Chemical found";
		logger.info("Response:details:of id     :{}  :{}  ", id, msg);

		return new ResponseEntity<>(response.getResponse(chemical,
				chemical != null ? "Chemical Found" : "No Chemical found", HttpStatus.OK), HttpStatus.OK);
	}

	
	/*
	 * ======================== Update Chemical Status========================
	 */

	@ApiOperation(value = "Update Chemical Status", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Updated."),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@PutMapping(value = CHEMICAL_STATUS, produces = "application/json")
	public ResponseEntity<GenericResponse> updateStatus(@PathVariable Long id, @PathVariable Integer status) {
		log.info("REST Request In Chemical Controller to update status {} {},", id, status);
		int res = chemicalService.updateStatus(id, status);
		GenericResponse response = new GenericResponse();
		if (res > 0) {
			logger.info("Response:details:of id :{}  :{}  ", id, "Chemical Successfully updated");

			return new ResponseEntity<>(response.getResponse("", "Chemical successfully updated", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", id, "Invalid Chemical");

			return new ResponseEntity<>(response.getResponse("", "Invalid Chemical", HttpStatus.OK), HttpStatus.OK);
		}
	}

	/*
	 * ======================== Delete Chemical ========================
	 */

	@ApiOperation(value = "Delete Chemical", response = Iterable.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully deleted"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@CrossOrigin
	@DeleteMapping(value = CHEMICAL_DELETE, produces = "application/json")
	public ResponseEntity<GenericResponse> delete(@RequestBody List<Long> idsArrays) {
		logger.info("Request: In Chemical Controller Delete Chemical of ids  :{} ", idsArrays);

		int status = chemicalService.delete(idsArrays);
		GenericResponse response = new GenericResponse();
		if (status > 0) {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Chemical Successfully deleted");

			return new ResponseEntity<>(response.getResponse("", "Chemical successfully deleted", HttpStatus.OK),
					HttpStatus.OK);
		} else {
			logger.info("Response:details:of id :{}  :{}  ", idsArrays, "Invalid Chemical");

			return new ResponseEntity<>(response.getResponse("", "Invalid Chemical", HttpStatus.OK), HttpStatus.OK);
		}
	}

}
