package rest.warehouse;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import rest.model.WarehouseData;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseService service;

    @GetMapping(value = "/{inID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public WarehouseData getWarehouseDataJson(@PathVariable String inID) {
        return service.getWarehouseData(inID);
    }

    @GetMapping(value = "/{inID}/xml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public WarehouseData getWarehouseDataXml(@PathVariable String inID) {
        return service.getWarehouseData(inID);
    }

    @GetMapping(value = "/test/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public WarehouseData getTestXml() {
        WarehouseData data = new WarehouseData();
        data.setRegionID("001");
        data.setRegionName("Test Location");
        data.setRegionAddress("Test Address");
        data.setRegionPostalCode("12345");
        data.setFederalState("Test State");
        data.setTimestamp("2024-11-02 20:00:00");
        return data;
    }
}
