package com.shoppertrak.web;
  
import java.util.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shoppertrak.domain.TrafficRecord;
import com.shoppertrak.domain.TrafficRecordShort;
import com.shoppertrak.exception.RecordNotFound;
import com.shoppertrak.exception.TimeError;
import com.shoppertrak.service.TrafficService;


@RestController
@RequestMapping("api/v1.0/traffic")
public class TrafficResource {


        private Logger logger = Logger.getLogger(this.getClass());

        @Autowired
        TrafficService service;

        @ApiOperation(value = "Get Traffic Record")
        @ApiResponses(value = {@ApiResponse(code = 404, message = "Record is not found") })
        @RequestMapping(value = "{id}", method = RequestMethod.GET)
        public @ResponseBody TrafficRecord get(@ApiParam(value = "Record Id", required = true) @PathVariable int id) {

                TrafficRecord  rec = service.get(id);
                if (rec == null) {
                        logger.info("Record is not found for macAddress:" + id);
                        throw new RecordNotFound(id);
                }
                return rec;
        }

        @ApiOperation(value = "Delete Traffic Record")
        @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
        public void delete(@ApiParam(value = "Record Id", required = true) @PathVariable int id) {
                service.delete(id);
        }
 @ApiOperation(value = "Delete Traffic Record")
        @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
        public void delete(@ApiParam(value = "Record Id", required = true) @PathVariable int id) {
                service.delete(id);
        }

        @ApiOperation(value = "Get All Traffic Records")
        @RequestMapping(method = RequestMethod.GET)
        public @ResponseBody Collection<TrafficRecord> getAllRecords() {
                return service.getAll();
        }

        @ApiOperation(value = "Save Traffic Records")
        @RequestMapping(method = RequestMethod.POST)
        public void save(@RequestBody TrafficRecord record) {
                service.save(record);
        }

        @ApiOperation(value = "Get Traffic Record by Client and Date Range")
        @ApiResponses(value = {@ApiResponse(code = 404, message = "Invalid date") })
        @RequestMapping(value = "/client/{clientId}/startTime/{startTime}/endTime/{endTime}", method = RequestMethod.GET, produces = "application/json")
        public @ResponseBody LinkedHashMap<String, Object> getByClient(
                @ApiParam(value = "Client Id", required = true) @PathVariable int clientId,
                @ApiParam(value = "Start Time", required = true) @PathVariable String startTime,
                @ApiParam(value = "End Time", required = true) @PathVariable String endTime) {

                if (!service.verifyDateInput(startTime)){
                        logger.info("Invalid parameter startTime:" + startTime);
                        throw new TimeError("startTime:" + startTime);
                }
                if (!service.verifyDateInput(endTime)){
                        logger.info("Invalid parameter endTime:" + endTime);
                        throw new TimeError("endTime:" + endTime);
                                        }

                return service.getByStoreId(clientId, -1, startTime, endTime);
        }

        @ApiOperation(value = "Get Traffic Record by Client, Store, and Date Range")
        @ApiResponses(value = {@ApiResponse(code = 404, message = "Invalid date") })
        @RequestMapping(value = "/client/{clientId}/store/{storeId}/startTime/{startTime}/endTime/{endTime}", method = RequestMethod.GET, produces = "application/json")
        public @ResponseBody LinkedHashMap<String, Object> getByClient(
                @ApiParam(value = "Client Id", required = true) @PathVariable int clientId,
                @ApiParam(value = "Store Id", required = true) @PathVariable int storeId,
                @ApiParam(value = "Start Time", required = true) @PathVariable String startTime,
                @ApiParam(value = "End Time", required = true) @PathVariable String endTime) {

                if (!service.verifyDateInput(startTime)){
                        logger.info("Invalid parameter startTime:" + startTime);
                        throw new TimeError("startTime:" + startTime);
                }
                if (!service.verifyDateInput(endTime)){
                        logger.info("Invalid parameter endTime:" + endTime);
                        throw new TimeError("endTime:" + endTime);

                }
                return service.getByStoreId(clientId, storeId, startTime, endTime);
        }
}

