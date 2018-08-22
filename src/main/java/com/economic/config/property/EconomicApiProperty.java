package com.economic.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("economic")
public class EconomicApiProperty {

	private final Security security = new Security();

	private String originPermitida = "http://localhost:4200";

	
	public Security getSecurity() {
		return security;
	}

	public String getOriginPermitida() {
		return originPermitida;
	}

	public void setOriginPermitida(String originPermitida) {
		this.originPermitida = originPermitida;
	}

	public static class Security {

		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}

	}

}