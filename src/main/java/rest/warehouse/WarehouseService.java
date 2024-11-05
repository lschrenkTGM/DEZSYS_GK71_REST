package rest.warehouse;

import org.springframework.stereotype.Service;
import rest.model.WarehouseData;

@Service
public class WarehouseService {

    public WarehouseData getWarehouseData(String inID) {
        WarehouseSimulation simulation = new WarehouseSimulation();
        return simulation.getData(inID);
    }
}
