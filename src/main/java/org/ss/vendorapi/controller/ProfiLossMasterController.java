package org.ss.vendorapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.entity.ProfitLossMasterEntity;
import org.ss.vendorapi.modal.ProfitLossMasterDTO;
//import org.ss.vendorapi.logging.UPPCLLogger;
import org.ss.vendorapi.modal.ProfitLossRequestDTO;
import org.ss.vendorapi.service.ProfitLossMasterService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.StatusMessageConstants;
import org.ss.vendorapi.util.UtilValidate;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v2/api")
public class ProfiLossMasterController {

    private static final Class<?> CLASS_NAME = UserMasterController.class;
//    private static UPPCLLogger logger = UPPCLLogger.getInstance(UPPCLLogger.MODULE_REGISTRATION, CLASS_NAME.toString());

    @Autowired
    private Environment env;

    @Autowired
    private ProfitLossMasterService profitLossMasterService;

    @PostMapping("/addProfitLoss")
    public ResponseEntity<?> addProfitLoss(@RequestBody ProfitLossRequestDTO profitLossRequestDTO, HttpServletRequest request) {
        String methodName = request.getRequestURI();
//        logger.logMethodStart(methodName);

        Map<String, Object> statusMap = new HashMap<>();
        List<ProfitLossMasterEntity> savedEntities = new ArrayList<>();

        try {
            for (ProfitLossMasterDTO profitLossMasterDTO : profitLossRequestDTO.getProfitLoss()) {
                if (UtilValidate.isEmpty(profitLossMasterDTO.getSrNo()) ||
                        UtilValidate.isEmpty(profitLossMasterDTO.getDescription()) ||
                        profitLossMasterDTO.getGstPercent() == null ||
                        profitLossMasterDTO.getClientBillIncludeGst() == null ||
                        profitLossMasterDTO.getClientGstAmount() == null ||
                        profitLossMasterDTO.getClientBillExcludeGst() == null ||
                        profitLossMasterDTO.getVendorBillIncludeGst() == null ||
                        profitLossMasterDTO.getVendorGstAmount() == null ||
                        profitLossMasterDTO.getVendorBillExcludeGst() == null ||
                        profitLossMasterDTO.getMarginPercent() == null ||
                        profitLossMasterDTO.getMargin() == null) {
                    return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
                }

                ProfitLossMasterEntity profitLossCreationEntityObj = new ProfitLossMasterEntity();

                profitLossCreationEntityObj.setSrNo(profitLossMasterDTO.getSrNo());
                profitLossCreationEntityObj.setClientName(profitLossRequestDTO.getClientName());
                profitLossCreationEntityObj.setProjectName(profitLossRequestDTO.getProjectName());
                profitLossCreationEntityObj.setDescription(profitLossMasterDTO.getDescription());
                profitLossCreationEntityObj.setGstPercent(profitLossMasterDTO.getGstPercent());
                profitLossCreationEntityObj.setClientBillIncludeGst(profitLossMasterDTO.getClientBillIncludeGst());
                profitLossCreationEntityObj.setClientGstAmount(profitLossMasterDTO.getClientGstAmount());
                profitLossCreationEntityObj.setClientBillExcludeGst(profitLossMasterDTO.getClientBillExcludeGst());
                profitLossCreationEntityObj.setVendorBillIncludeGst(profitLossMasterDTO.getVendorBillIncludeGst());
                profitLossCreationEntityObj.setVendorGstAmount(profitLossMasterDTO.getVendorGstAmount());
                profitLossCreationEntityObj.setVendorBillExcludeGst(profitLossMasterDTO.getVendorBillExcludeGst());
                profitLossCreationEntityObj.setMarginPercent(profitLossMasterDTO.getMarginPercent());
                profitLossCreationEntityObj.setMargin(profitLossMasterDTO.getMargin());

                try {
                    profitLossCreationEntityObj = profitLossMasterService.save(profitLossCreationEntityObj);
                    savedEntities.add(profitLossCreationEntityObj);

                } catch (Exception ex) {
//                    logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ Failed to save user in DB: " + ex.getMessage());
                    statusMap.put(Parameters.statusMsg, env.getProperty("common.api.error"));
                    statusMap.put(Parameters.statusCode, Constants.SVD_USR);
                    statusMap.put(Parameters.status, Constants.FAIL);
                    return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            if (!savedEntities.isEmpty()) {
                statusMap.put(Parameters.statusMsg,  StatusMessageConstants.PROFIT_LOSS_GENERATED_SUCCESSFULLY);
                statusMap.put(Parameters.status, Constants.SUCCESS);
                statusMap.put(Parameters.statusCode, "RU_200");
                return new ResponseEntity<>(statusMap, HttpStatus.OK);
            } else {
                statusMap.put(Parameters.statusMsg, StatusMessageConstants.PROFIT_LOSS__NOT_GENERATED);
                statusMap.put(Parameters.status, Constants.FAIL);
                statusMap.put(Parameters.statusCode, "RU_301");
                return new ResponseEntity<>(statusMap, HttpStatus.EXPECTATION_FAILED);
            }
        } catch (Exception ex) {
//            if (logger.isErrorLoggingEnabled()) {
//                logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ Exception when processing service list: " + ex.getMessage());
//            }
            return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
//    ************************************************************************************************************************************************************************
//    *****************************************************get api ***********************************************************************************************************
 
    
    @GetMapping("/getAllProfitLoss")    
    public ResponseEntity<?> getAllProfitLoss() {
	    try {
	        List<ProfitLossMasterEntity> users = profitLossMasterService.findAll();
	        return new ResponseEntity<>(users, HttpStatus.OK);
	    } catch (Exception ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
    
    
    
}