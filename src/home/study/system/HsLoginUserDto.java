 /*
  * System-Name    :NCU (NITTSU COMMON UTILITY)
  * Subsystem-Name :COM
  *
  * Notes          :
  *
  * Copyright:
  * All Rights Reserved Copyright (C) NIPPON EXPRESS  2015
  *
  */
package home.study.system;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Business DTO class
 * @version 1.0.0.8 2016/04/28
 * @author  CITS
 * @see  
 */
public class HsLoginUserDto implements Serializable {

	private static final long serialVersionUID = 6916730892930411862L;
	
	private String companyCode = "";
	private String companyName = "";
	private String branchCode = "";
	private String branchName = "";
	private String cntlBrCd = "";
	private String divisionCode = "";
	private String divisionName = "";
	private String countryCode = "";
	private String countryName = "";
	private String userCode = "";
	private String localName = "";
	private String englishName = "";
	private String cityCd = "";
	private String cityName = "";
	private String loginId = "";
	private String sessionId = "";
	private String conversationId = "";
	private String browserLocale = "";
	private String osLocale = "";
	private String clientIP = "";
	private Date loginDateTime = null;
	private Date procDate=null;
	private String browserAgent = "";
	private String userLocaleCode = "";
	private String dateFormat = "";
	private String dateTimeFormat = "";
	private String numberLocaleCode = "";
	private Locale numberLocale = null;
	private String numberSeparator = "";
	private String userKind = "";
	private String userOriginCode = "";
	private String loginKind = "";
	private String groupKind="";
	private String wwCode="";
	private String userTZCode = "";
	private String userTimeZone = "";
	private String programId = "";
	private String fullProgramId = "";
	private Locale locale = null;
	private TimeZone timeZone = null;
	private List<String> userAuthList = null;
	private String organizationCode1="";
	private String organizationName1="";
	private String organizationCode2="";
	private String organizationName2="";
	private String organizationCode3="";
	private String organizationName3="";
	private String organizationCode4="";
	private String organizationName4="";
	private String organizationCode5="";
	private String organizationName5="";
	private String organizationCode6="";
	private String organizationName6="";
	private String organizationCode7="";
	private String organizationName7="";
	private String organizationCode8="";
	private String organizationName8="";
	private String organizationCode9="";
	private String organizationName9="";
	private String organizationCode10="";
	private String organizationName10="";
	private String mailAddress = "";
	private String screenId = "";
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getCntlBrCd() {
		return cntlBrCd;
	}
	public void setCntlBrCd(String cntlBrCd) {
		this.cntlBrCd = cntlBrCd;
	}
	public String getDivisionCode() {
		return divisionCode;
	}
	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getLocalName() {
		return localName;
	}
	public void setLocalName(String localName) {
		this.localName = localName;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getCityCd() {
		return cityCd;
	}
	public void setCityCd(String cityCd) {
		this.cityCd = cityCd;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getConversationId() {
		return conversationId;
	}
	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}
	public String getBrowserLocale() {
		return browserLocale;
	}
	public void setBrowserLocale(String browserLocale) {
		this.browserLocale = browserLocale;
	}
	public String getOsLocale() {
		return osLocale;
	}
	public void setOsLocale(String osLocale) {
		this.osLocale = osLocale;
	}
	public String getClientIP() {
		return clientIP;
	}
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	public Date getLoginDateTime() {
		return loginDateTime;
	}
	public void setLoginDateTime(Date loginDateTime) {
		this.loginDateTime = loginDateTime;
	}
	public Date getProcDate() {
		return procDate;
	}
	public void setProcDate(Date procDate) {
		this.procDate = procDate;
	}
	public String getBrowserAgent() {
		return browserAgent;
	}
	public void setBrowserAgent(String browserAgent) {
		this.browserAgent = browserAgent;
	}
	public String getUserLocaleCode() {
		return userLocaleCode;
	}
	public void setUserLocaleCode(String userLocaleCode) {
		this.userLocaleCode = userLocaleCode;
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public String getDateTimeFormat() {
		return dateTimeFormat;
	}
	public void setDateTimeFormat(String dateTimeFormat) {
		this.dateTimeFormat = dateTimeFormat;
	}
	public String getNumberLocaleCode() {
		return numberLocaleCode;
	}
	public void setNumberLocaleCode(String numberLocaleCode) {
		this.numberLocaleCode = numberLocaleCode;
	}
	public Locale getNumberLocale() {
		return numberLocale;
	}
	public void setNumberLocale(Locale numberLocale) {
		this.numberLocale = numberLocale;
	}
	public String getNumberSeparator() {
		return numberSeparator;
	}
	public void setNumberSeparator(String numberSeparator) {
		this.numberSeparator = numberSeparator;
	}
	public String getUserKind() {
		return userKind;
	}
	public void setUserKind(String userKind) {
		this.userKind = userKind;
	}
	public String getUserOriginCode() {
		return userOriginCode;
	}
	public void setUserOriginCode(String userOriginCode) {
		this.userOriginCode = userOriginCode;
	}
	public String getLoginKind() {
		return loginKind;
	}
	public void setLoginKind(String loginKind) {
		this.loginKind = loginKind;
	}
	public String getGroupKind() {
		return groupKind;
	}
	public void setGroupKind(String groupKind) {
		this.groupKind = groupKind;
	}
	public String getWwCode() {
		return wwCode;
	}
	public void setWwCode(String wwCode) {
		this.wwCode = wwCode;
	}
	public String getUserTZCode() {
		return userTZCode;
	}
	public void setUserTZCode(String userTZCode) {
		this.userTZCode = userTZCode;
	}
	public String getUserTimeZone() {
		return userTimeZone;
	}
	public void setUserTimeZone(String userTimeZone) {
		this.userTimeZone = userTimeZone;
	}
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public String getFullProgramId() {
		return fullProgramId;
	}
	public void setFullProgramId(String fullProgramId) {
		this.fullProgramId = fullProgramId;
	}
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public TimeZone getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}
	public List<String> getUserAuthList() {
		return userAuthList;
	}
	public void setUserAuthList(List<String> userAuthList) {
		this.userAuthList = userAuthList;
	}
	public String getOrganizationCode1() {
		return organizationCode1;
	}
	public void setOrganizationCode1(String organizationCode1) {
		this.organizationCode1 = organizationCode1;
	}
	public String getOrganizationName1() {
		return organizationName1;
	}
	public void setOrganizationName1(String organizationName1) {
		this.organizationName1 = organizationName1;
	}
	public String getOrganizationCode2() {
		return organizationCode2;
	}
	public void setOrganizationCode2(String organizationCode2) {
		this.organizationCode2 = organizationCode2;
	}
	public String getOrganizationName2() {
		return organizationName2;
	}
	public void setOrganizationName2(String organizationName2) {
		this.organizationName2 = organizationName2;
	}
	public String getOrganizationCode3() {
		return organizationCode3;
	}
	public void setOrganizationCode3(String organizationCode3) {
		this.organizationCode3 = organizationCode3;
	}
	public String getOrganizationName3() {
		return organizationName3;
	}
	public void setOrganizationName3(String organizationName3) {
		this.organizationName3 = organizationName3;
	}
	public String getOrganizationCode4() {
		return organizationCode4;
	}
	public void setOrganizationCode4(String organizationCode4) {
		this.organizationCode4 = organizationCode4;
	}
	public String getOrganizationName4() {
		return organizationName4;
	}
	public void setOrganizationName4(String organizationName4) {
		this.organizationName4 = organizationName4;
	}
	public String getOrganizationCode5() {
		return organizationCode5;
	}
	public void setOrganizationCode5(String organizationCode5) {
		this.organizationCode5 = organizationCode5;
	}
	public String getOrganizationName5() {
		return organizationName5;
	}
	public void setOrganizationName5(String organizationName5) {
		this.organizationName5 = organizationName5;
	}
	public String getOrganizationCode6() {
		return organizationCode6;
	}
	public void setOrganizationCode6(String organizationCode6) {
		this.organizationCode6 = organizationCode6;
	}
	public String getOrganizationName6() {
		return organizationName6;
	}
	public void setOrganizationName6(String organizationName6) {
		this.organizationName6 = organizationName6;
	}
	public String getOrganizationCode7() {
		return organizationCode7;
	}
	public void setOrganizationCode7(String organizationCode7) {
		this.organizationCode7 = organizationCode7;
	}
	public String getOrganizationName7() {
		return organizationName7;
	}
	public void setOrganizationName7(String organizationName7) {
		this.organizationName7 = organizationName7;
	}
	public String getOrganizationCode8() {
		return organizationCode8;
	}
	public void setOrganizationCode8(String organizationCode8) {
		this.organizationCode8 = organizationCode8;
	}
	public String getOrganizationName8() {
		return organizationName8;
	}
	public void setOrganizationName8(String organizationName8) {
		this.organizationName8 = organizationName8;
	}
	public String getOrganizationCode9() {
		return organizationCode9;
	}
	public void setOrganizationCode9(String organizationCode9) {
		this.organizationCode9 = organizationCode9;
	}
	public String getOrganizationName9() {
		return organizationName9;
	}
	public void setOrganizationName9(String organizationName9) {
		this.organizationName9 = organizationName9;
	}
	public String getOrganizationCode10() {
		return organizationCode10;
	}
	public void setOrganizationCode10(String organizationCode10) {
		this.organizationCode10 = organizationCode10;
	}
	public String getOrganizationName10() {
		return organizationName10;
	}
	public void setOrganizationName10(String organizationName10) {
		this.organizationName10 = organizationName10;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getScreenId() {
		return screenId;
	}
	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}
}
