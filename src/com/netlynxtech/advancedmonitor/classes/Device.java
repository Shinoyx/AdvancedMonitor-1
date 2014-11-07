package com.netlynxtech.advancedmonitor.classes;

import java.io.Serializable;

public class Device implements Serializable {

	String version, deviceID, description, temperature, humidity, voltage, input1, descriptionInput1, input2, descriptionInput2, output1, descriptionOutput1, output2, descriptionOutput2, timestamp,
			enableTemperature, enableHumidity, enableInput1, enableInput2, enableOutput1, enableOutput2, temperatureHi, temperatureLo, humidityHi, humidityLo, reverseLogicInput1, reverseLogicInput2,
			reverseLogicOutput1, reverseLogicOutput2, temperatureState, humidityState, latitude, longitude;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getVoltage() {
		return voltage;
	}

	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}

	public String getInput1() {
		return input1;
	}

	public void setInput1(String input1) {
		this.input1 = input1;
	}

	public String getDescriptionInput1() {
		return descriptionInput1;
	}

	public void setDescriptionInput1(String descriptionInput1) {
		this.descriptionInput1 = descriptionInput1;
	}

	public String getInput2() {
		return input2;
	}

	public void setInput2(String input2) {
		this.input2 = input2;
	}

	public String getDescriptionInput2() {
		return descriptionInput2;
	}

	public void setDescriptionInput2(String descriptionInput2) {
		this.descriptionInput2 = descriptionInput2;
	}

	public String getOutput1() {
		return output1;
	}

	public void setOutput1(String output1) {
		this.output1 = output1;
	}

	public String getDescriptionOutput1() {
		return descriptionOutput1;
	}

	public void setDescriptionOutput1(String descriptionOutput1) {
		this.descriptionOutput1 = descriptionOutput1;
	}

	public String getOutput2() {
		return output2;
	}

	public void setOutput2(String output2) {
		this.output2 = output2;
	}

	public String getDescriptionOutput2() {
		return descriptionOutput2;
	}

	public void setDescriptionOutput2(String descriptionOutput2) {
		this.descriptionOutput2 = descriptionOutput2;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getEnableTemperature() {
		return enableTemperature;
	}

	public void setEnableTemperature(String enableTemperature) {
		this.enableTemperature = enableTemperature;
	}

	public String getEnableHumidity() {
		return enableHumidity;
	}

	public void setEnableHumidity(String enableHumidity) {
		this.enableHumidity = enableHumidity;
	}

	public String getEnableInput1() {
		return enableInput1;
	}

	public void setEnableInput1(String enableInput1) {
		this.enableInput1 = enableInput1;
	}

	public String getEnableInput2() {
		return enableInput2;
	}

	public void setEnableInput2(String enableInput2) {
		this.enableInput2 = enableInput2;
	}

	public String getEnableOutput1() {
		return enableOutput1;
	}

	public void setEnableOutput1(String enableOutput1) {
		this.enableOutput1 = enableOutput1;
	}

	public String getEnableOutput2() {
		return enableOutput2;
	}

	public void setEnableOutput2(String enableOutput2) {
		this.enableOutput2 = enableOutput2;
	}

	public String getTemperatureHi() {
		return temperatureHi;
	}

	public void setTemperatureHi(String temperatureHi) {
		this.temperatureHi = temperatureHi;
	}

	public String getTemperatureLo() {
		return temperatureLo;
	}

	public void setTemperatureLo(String temperatureLo) {
		this.temperatureLo = temperatureLo;
	}

	public String getHumidityHi() {
		return humidityHi;
	}

	public void setHumidityHi(String humidityHi) {
		this.humidityHi = humidityHi;
	}

	public String getHumidityLo() {
		return humidityLo;
	}

	public void setHumidityLo(String humidityLo) {
		this.humidityLo = humidityLo;
	}

	public String getReverseLogicInput1() {
		return reverseLogicInput1;
	}

	public void setReverseLogicInput1(String reverseLogicInput1) {
		this.reverseLogicInput1 = reverseLogicInput1;
	}

	public String getReverseLogicInput2() {
		return reverseLogicInput2;
	}

	public void setReverseLogicInput2(String reverseLogicInput2) {
		this.reverseLogicInput2 = reverseLogicInput2;
	}

	public String getReverseLogicOutput1() {
		return reverseLogicOutput1;
	}

	public void setReverseLogicOutput1(String reverseLogicOutput1) {
		this.reverseLogicOutput1 = reverseLogicOutput1;
	}

	public String getReverseLogicOutput2() {
		return reverseLogicOutput2;
	}

	public void setReverseLogicOutput2(String reverseLogicOutput2) {
		this.reverseLogicOutput2 = reverseLogicOutput2;
	}

	public String getTemperatureState() {
		return temperatureState;
	}

	public void setTemperatureState(String temperatureState) {
		this.temperatureState = temperatureState;
	}

	public String getHumidityState() {
		return humidityState;
	}

	public void setHumidityState(String humidityState) {
		this.humidityState = humidityState;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
