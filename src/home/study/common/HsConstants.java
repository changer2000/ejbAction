package home.study.common;

//It's better to move the initial definition into properties files, 
//then it can be called anywhere in the project
public class HsConstants {
	public static final String KEY_FORMAT_DATE = "yyyy/MM/dd";
	public static final String KEY_FORMAT_DATE_UNDERSCORE = "yyyy-MM-dd";
	public static final String KEY_FORMAT_DATE_NO_SPLIT = "yyyyMMdd";
	public static final String KEY_FORMAT_DATE_MD = "MM/dd";
	public static final String KEY_FORMAT_TIME = "HH:mm:ss";
	public static final String KEY_FORMAT_TIME5 = "HH:mm";
	public static final String KEY_FORMAT_TIME4 = "HHmm";
	public static final String KEY_FORMAT_TIMESTAMP = "yyyy/MM/dd HH:mm:ss";

	public static final String ISO_DB_FORMAT_TIMESTAMP ="YYYY-MM-DD HH24:MI:SS";
	public static final String ISO_SIMPLE_DATE_FORMAT ="yyyy-MM-dd HH:mm:ss";
		
	public static final String FORMAT_INT = "#,##0";
	public static final String FORMAT_FLOAT2 = "#,##0.00";
	public static final String FORMAT_FLOAT3 = "#,##0.000";
	public static final String FORMAT_FLOAT6 = "#,##0.000000";
	
	/* LOCK KEY SPARATOR */
	public static final String LOCK_KEY_SEPARATOR="||";
	
	/* LOCK ERROR MESSAGE ID */
	public static final String ERROR_LOCK_INVALID = "INF0002";
	
	/* SESSION KEY SPARATOR */
	public static final String SESSION_KEY_SEPARATOR="||";
	
    /* HELPER SPARATOR */
    public static final String HELPER_JS_SEPARATOR="$$";
    
	public static final String COMMA_SEPARATOR=",";
	public static final String DATE_SEPARATOR="/";
	public static final String APPLICATION_WILDCARD = "*";
	public static final String DB_WILDCARD = "%";
	
	public static final String BLANK_DATA = " ";
	public static final String DATA_NOT_EXIST = "data not exist";
	
	// page mode 
	public static final String MOD_NEW ="NEW";
	public static final String MOD_MODIFY ="MOD";
	public static final String MOD_DISPLAY = "DSP";
	public static final String MOD_ORIGNAL = "";

	//flag
	public static final String FLAG_YES ="1";
	public static final String FLAG_NO ="0";
	
	public static final String CSS_ERROR = "error";
	public final static String CSS_SELECT_LIST_LABEL_ERROR = CSS_ERROR;

	
	/* view id constants in the flash */
	public static final String SEA_CURRENT_VIEW_ID="SEA_CURRENT_VIEW_ID";
	public static final String REDIRECT = "?faces-redirect=true";
	
    //Constant value never will change
    /* log recourse file */
    //public static final String LOG_RESOURCES = "LogUtil";

    /* SQL recourse file */
    public static final String SQL_RESOURCES = "SQL.properties";
    
	public static final String  CONSTANTS_RESOURCES_DIR="constants";

    
	/* Constants recourse file path */
	public static final String CONSTANTS_RESOURCES_PATH = CONSTANTS_RESOURCES_DIR+"/";
	
	public static final String PROTERTIES = ".properties";
	
	/* Constants recourse file */
	 public static final String CONSTANTS_RESOURCES = CONSTANTS_RESOURCES_PATH+"header.properties";
 	

	/* Constants menu resource file */
	public static final String MENU_RESOURCES = "menu-config.xml";
	
	public static final String COMPLEXED_MENU_RESOURCES = "complexed-menu-config.xml";

	
	/* Constants application resource file */
	public static final String APPLICATION_RESOURCES = "ApplicationResources";
	
	public static final String UI_BUTTON_NORECORD = "ui-button-norecord";
	
	public static final String SCRN_ROUTE_ID = "SCRN_ROUTE_ID";
	
}
