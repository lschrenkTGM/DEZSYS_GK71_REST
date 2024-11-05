package rest.model;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "electionData") // Wichtig für XML-Serialisierung
public class WarehouseData {

	private String regionID;
	private String regionName;
	private String regionAddress;
	private String regionPostalCode;
	private String federalState;
	private String timestamp;
	private List<PartyVotes> countingData;

	@XmlElement
	public String getRegionID() { return regionID; }
	public void setRegionID(String regionID) { this.regionID = regionID; }

	@XmlElement
	public String getRegionName() { return regionName; }
	public void setRegionName(String regionName) { this.regionName = regionName; }

	@XmlElement
	public String getRegionAddress() { return regionAddress; }
	public void setRegionAddress(String regionAddress) { this.regionAddress = regionAddress; }

	@XmlElement
	public String getRegionPostalCode() { return regionPostalCode; }
	public void setRegionPostalCode(String regionPostalCode) { this.regionPostalCode = regionPostalCode; }

	@XmlElement
	public String getFederalState() { return federalState; }
	public void setFederalState(String federalState) { this.federalState = federalState; }

	@XmlElement
	public String getTimestamp() { return timestamp; }
	public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

	@XmlElement(name = "party") // Name des XML-Elements für jedes Listenelement
	public List<PartyVotes> getCountingData() { return countingData; }
	public void setCountingData(List<PartyVotes> countingData) { this.countingData = countingData; }
}
