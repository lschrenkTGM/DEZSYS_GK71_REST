package rest.warehouse;

import rest.model.WarehouseData;
import rest.model.PartyVotes;
import rest.model.PreferredVote;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class WarehouseSimulation {

	public WarehouseData getData(String inID) {
		WarehouseData data = new WarehouseData();
		data.setRegionID(inID);
		data.setRegionName("Linz Bahnhof");
		data.setRegionAddress("Bahnhofsstrasse 27/9");
		data.setRegionPostalCode("Linz");
		data.setFederalState("Austria");
		data.setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

		List<PartyVotes> votes = Arrays.asList(
				new PartyVotes("OEVP", getRandomInt(200, 400), generatePreferredVotes()),
				new PartyVotes("SPOE", getRandomInt(200, 270), generatePreferredVotes()),
				new PartyVotes("FPOE", getRandomInt(280, 350), generatePreferredVotes()),
				new PartyVotes("GRUENE", getRandomInt(80, 120), generatePreferredVotes()),
				new PartyVotes("NEOS", getRandomInt(100, 190), generatePreferredVotes())
		);

		data.setCountingData(votes);
		return data;
	}

	private List<PreferredVote> generatePreferredVotes() {
		List<PreferredVote> preferredVotes = new ArrayList<>();
		preferredVotes.add(new PreferredVote(1, "Max Mustermann", getRandomInt(40, 50)));
		preferredVotes.add(new PreferredVote(2, "Anna Musterfrau", getRandomInt(20, 30)));
		preferredVotes.add(new PreferredVote(3, "John Doe", getRandomInt(10, 20)));
		return preferredVotes;
	}

	private int getRandomInt(int min, int max) {
		return (int) ((Math.random() * (max - min + 1)) + min);
	}
}
