package com.equinix.appops.dart.portal.constant;

import java.util.Arrays;
import java.util.List;

public final class DAOConstants {
	
	
	private DAOConstants(){
		 
	}
	public static final String LOWER_IBX ="lower(IBX) = LOWER(TRIM(?)) AND LOWER(ASSETNAME) = LOWER(TRIM(?))";
	public static final String EQUIPMENT_NAME="equipmentName";
	public static final String ENITY_NAME="entityName";
	public static final String WORKFLOW = "workFlow";
	public static final String QUOTE_ID = "quoteId";
	public static final String REQUEST_ID = "requestId";
	public static final String QUOTE_LINE_ITEM_ID= "quoteLineItemId";
	public static final String QUOTE_ID_CAPS = "quoteID";
	public static final String QUOTE_LINE_ITEM_ID_CAPS = "quoteLineItemID";
	public static final String IBX = "ibx";
	public static final String LOCKED_STATUS = "lockedStatus";
	public static final String POF = "pof";
	public static final String SRC_CAGE_USID = "sCageUNQSPCID";
	public static final String POF_ID = "pofId";
	public static final String POF_ID2 = "pofID";
	public static final String NETWORK_PRODUCT = "networkProduct";
	public static final String PORT_SPEED = "portSpeed";
	public static final String PORT_INTERFACE_TYPE = "portInterfaceType";
	public static final String PORT_TYPE_ID = "porttypeID";
	public static final String CAGE_NUM = "cageNum";
	public static final String CAB_NUM = "cabNum";
	public static final String CUSTOMER_ID = "customerID";
	public static final String BCTR_NUMBER = "BCTRNumber";
	public static final String USID_BCTR = "uniqueSpaceIDBCTR";	
	public static final String FLOOR = "floor";
	public static final String BCTR_ID =  "bctrId";
	public static final String BCTR =  "bctr";
	public static final String CUSTOMERID1 = "customerId1";
	public static final String CUSTOMERID2 = "customerId2";
	public static final String CUSTOMERID3 =  "customerId3";
	public static final String CUSTOMERID4 = "customerId4";
	public static final String CAGE = "cage";
	public static final String CABINET_NUM = "cabinetNum";
	public static final String CABINET_ID =  "cabinetId";
	public static final String CABINET_DRAW_CAP =  "cabinetDrawCap";
	public static final String CAB_ID = "cabId";
	public static final String POSITION = "position";
	public static final String CAPACITY_STATUS_ID = "capacityStatusId";
	public static final String CUSTOMER = "customer";
	public static final String UNIQUE_SPACE_ID = "uniqueSpaceId";
	public static final String AUTO_CAD_TAG = "autocadTag";
	public static final String CAGE_ID = "CAGEID";
	public static final String CAPACITYSTATUSID="CAPACITYSTATUSID";
	public static final String CABINETNUM = "CABINETNUM";
	public static final String CAGE_ID_SMALL_CASE = "cageId";
	public static final String PRODUCT_TYPE_ID = "productTypeId";
	public static final String PRODUCT_ID  = "productID";
	public static final String CUSTOMER_ID_SMALL_CASE = "customerId";
	public static final String CUSTOMER_NAME = "customerName";
	public static final String ACTUAL_CABINET = "actualCabinet"; 
	public static final String  DRAW_CAP_KVA = "drawCapKVA";
	public static final String CAGE_AREA_SQ_FEET = "cageAreaSqFeet";
	public static final String CAGE_AREA_SQ_METER = "cageAreaSqMeter";
	public static final String CUSTOMER_SEG_ID =  "customerSegID";
	public static final String PHASE = "phase";
	public static final String IBX_TYPE_SYS_PARAM = "ibxTypeSysParam";
	public static final String SOLD = "Sold";
	public static final String CAPACITY_STATUS = "Capacity Status";
	public static final String CAP_STATUS = "CAPSTATUS";
	public static final String CAP_STATUS_SMALL_CASE = "capstatus";
	public static final String IBX_ID = "IBXID";
	public static final String IBX_ID_SMALL_CASE = "ibxId";
	public static final String DRAW_CAP_KV = "DRAWCAPKV";
	public static final String DRAW_CAP_KV_SMALL_CASE = "drawcapkv";
	public static final String ACT_CAB = "ACTCAB";
	public static final String PEND_DATE = "PEND_DATE";
	public static final String AMPS = "amps";
	public static final String VOLTS = "volts";
	public static final String CKT_TYPE = "cktType";
	public static final String TYPE_ID = "typeId";
	public static final String CUSTOMER_UCM_ID = "customerUCMID";
	public static final String FILE_STATUS = "fileStatus";
	public static final String FILE_STATUS_ID = "fileStatusId";
	public static final String UPLOAD_STATUS = "uploadStatus";
	public static final String FLOOR_NAME = "floorName";
	public static final String METRO_DOMAIN = "metroDomain";
	public static final String REGION_DOMAIN = "regionDomain";
	public static final String PRIORITY = "priority";
	public static final String FLR_NAME = "flrName";
	public static final String LOAD_TRACK =  "loadTrack";
	public static final String COUNTRY_ID = "countryId";
	public static final String PHASE_NAME = "phaseName";
	public static final String PRODUCT_SPEED_ID = "productSpeedID";
	public static final String REGION_NAME = "regionName";
	public static final String CATEGORY = "category";
	public static final String REPORT_NAME =  "reportName";
	public static final String RESERVATION_ID = "reservationId";
	public static final String RESERVATION = "reservation";
	public static final String ROOM_NAME = "roomName";
	public static final String ATTR_LIST = "attrList";
	public static final String PK_ID = "pkId";
	public static final String GROUP_NAME = "groupName";
	public static final String NAME = "name";
	public static final String WORKFLOW_SECURITY_ID = "workflowSecurityID";
	public static final String SECURITY_GROUP_NAME = "securityGroupName";
	public static final String WORKFLOW_MASTER = "workflowMaster";
	public static final String GROUP_CATEGORY = "groupCategory";
	public static final String REGION = "region";
	public static final String SUB_REGION = "subRegion";
	public static final String COUNTRY = "country";
	public static final String WORKFLOW_SECURITY = "workflowSecurity";
	public static final String WORKSTATION_NUMBER = "workStationNumber";
	public static final String WORKSTATION_ID = "workStationID";
	public static final String CUSTOMER_ID1_CAPS = "customerID1";
	public static final String CUSTOMER_ID2_CAPS = "customerID2";
	public static final String CUSTOMER_ID3_CAPS = "customerID3";
	public static final String CUSTOMER_ID4_CAPS = "customerID4";
	public static final String ROOM = "room";
	public static final String WORKFLOW_ID = "workFlowID";
	public static final String ROOM_ID = "roomId";
	public static final String FLOOR_ID = "floorId";
	public static final String PHASE_ID = "phaseId";
	public static final String IBX_ID_OF_SEG = "ibxId";
	public static final String ROOM_SEGMENT_ID = "roomSegmentId";
	public static final String IBX_SEGMENT_ID = "ibxSegmentId";
	public static final String FLOOR_SEGMENT_ID = "floorSegmentId";
	public static final String PHASE_SEGMENT_ID = "phaseSegmentId";
	public static final String CFR_COLO_WRKFLW_ID="coloCFRworkflow";
	public static final String SIEBEL_TASK_ID="siebelTaskID";
	public static final String BCTR_ROOM_TYPE="bctrRoomTypeSysParam";
	public static final String IBX_CODE = "ibxCode";
	public static final String AVAILABILITY_STATUS="availabilityStatus";
	public static final String PRODUCT_TYPE="productTypeId";
	public static final String PRODUCTTYPEID="PRODUCT_TYPE_ID";
	public static final String PRODUCT_ID_BUSINESS_SUITE="6";
	public static final String XLS=".xls";
	public static final String XLSX=".xlsx";
	public static final String PDF=".pdf";
	public static final String CONTENT_DISPOSITION="Content-Disposition";
	public static final String ATTACHMENT_FILENAME="attachment;filename=";
	public static final String IS_SOFT_DELETE="isSoftDelete";
	public static final String FROM_RANGE="fromRange";
	public static final String TO_RANGE="toRange";
	public static final String SELECT_NAME_ALIAS_INSTANCEID_ASSETTYPE_FROM="select NAME,ALIAS,INSTANCEID,ASSETTYPE,POSITION from ";
	public static final String PARENTINSTANCEID="PARENTINSTANCEID = ?";
	public static final String NAME1="NAME";
	public static final String INSTANCEID="INSTANCEID";
	public static final String SHELF_NOT_NULL=" AND SHELFID IS NOT NULL";
	public static final String SHELF_IS_NULL=" AND SHELFID IS NULL";
	public static final String SLOT_NOT_NULL=" AND SLOTID IS NOT NULL";
	public static final String SLOT_IS_NULL=" AND SLOTID IS NULL";
	public static final String MODULE_NOT_NULL=" AND MODULEID IS NOT NULL";
	public static final String MODULE_IS_NULL=" AND MODULEID IS NULL";
	public static final String CIRCUIT_INFO_NULL ="list of circuit info is null";
	public static final String CIRCUIT_INFO_NOT_NULL ="list of circuit info is not null";
	public static final String DESCRIPTION="DESCRIPTION";
	public static final String ASSETNAME="ASSETNAME";
	public static final String ASSETTYPE="ASSETTYPE";
	public static final String IBX1="IBX";
	public static final String KVARATING="KVARATING";
	public static final String PARENTCIINSTANCEID="PARENTCIINSTANCEID";
	public static final String KWRATING="KWRATING";

	public static final String BCTR_WRKFLW_ID="bctrCFRWorkflowId";
	public static final String BCTR_CFR_IDF_CAGE="idfCageId";

	public static final String CAGE_STATUS = "status";
	public static final String CAGE_DRAW_CAP_KVA = "drawCapKVA";
	public static final String CAGE_ACTUAL_CAB = "actualCabinet";

	public static final String CAPACITY_STATUS_TYPE="capacityStatusId";

	public static final String LAST_UPDATED_DATE="lastUpdatedDate";

	public static final int ZERO = 0;
	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;
	public static final int SIX = 6;
	public static final int SEVEN = 7;
	public static final int EIGHT = 8;
	public static final int NINE = 9;
	public static final int TEN = 10;
	public static final int ELEVEN = 11;
	public static final int TWELVE = 12;
	public static final int THIRTEEN = 13;
	public static final int FOURTEEN = 14;
	public static final int FIFTEEN = 15;
	public static final int SIXTEEN = 16;
	public static final int SEVENTEEN = 17;
	public static final int EIGHTEEN = 18;
	public static final int NINETEEN = 19;
	public static final int TWENTY = 20;
	public static final int TWENTY_ONE = 21;
	public static final int TWENTY_TWO = 22; 
	public static final int TWENTY_THREE = 23;
	public static final int TWENTY_FOUR = 24;
	public static final int TWENTY_FIVE = 25;
	public static final int TWENTY_SIX = 26;
	public static final int TWENTY_SEVEN = 27;
	public static final int TWENTY_EIGHT = 28;
	public static final int TWENTY_NINE = 29;
	public static final int THIRTY = 30;
	public static final int THIRTY_ONE = 31;
	public static final int THIRTY_TWO = 32;
	public static final int THIRTY_THREE = 33;
	public static final int THIRTY_FOUR = 34;
	public static final int THIRTY_FIVE = 35;
	public static final int THIRTY_SIX = 36;
	public static final int THIRTY_SEVEN = 37;
	public static final int THIRTY_EIGHT= 38;
	public static final int THIRTY_NINE= 39;
	public static final int FORTY = 40;
	public static final int FORTY_ONE = 41;
	public static final int FORTY_TWO = 42;
	public static final int FORTY_THREE = 43;
	public static final int FORTY_FOUR = 44;
	public static final int FORTY_FIVE = 45;
	public static final int FORTY_SIX = 46;
	public static final int FORTY_SEVEN = 47;
	public static final int FORTY_EIGHT = 48;


	//Secure Cabinet
	public static final String CONTIGUOUS_NUMBER = "contiguousNum";
	public static final String CABINET_TYPE = "cabinetType";
	public static final String PENDING_AVAILABLE = "Pending available";
	public static final String AVAILABLE = "Available";
	public static final String PENDING_AVAILABLE_DATE = "pendingAvailableDate";

	public static final String ORDER_LINE_ITEM_ID= "orderLineItemId";
	public static final String ORDER_ID = "orderId";

	//BCM Draw 
	public static final String IBX_ID_CAPS = "ibxID";
	public static final String CAGE_ID_CAPS = "cageID";
	public static final String CABINET_ID_CAPS = "cabinetID";

	public static final String REGION_ID_SMALL_CASE = "regionId";
	public static final String STATUS = "status";
	public static final String IBX_STATUS = "ibxStatusSysParam";
	public static final String IBX_TYPE = "ibxType";
	public static final String KVA_PER_CAB = "kvaPerCab";
	public static final String CAGE_OR_BCTR_ID = "cageOrBctrId";
	public static final String COUNTRY_NAME = "countryName";
	public static final String METRO_NAME = "metroName";
	public static final String SALES_REP_NAME = "salesRepName";
	public static final String SALES_REP_EMAIL = "salesRepEmail";
	
	public static final String SUCCESS="SUCCESS";

	public static final String CMDB_DEVICEID="cmdbDeviceId";
	//AppOps Admin Constants
	public static final String NOTIFICATION_KEY_LIST= "NOTIFICATION_KEY_LIST";
	public static final List<String> NOTIFICATION_KEYS=Arrays.asList("NOTIFICATION_MAIL_SERVER_HOST","NOTIFICATION_EMAIL_FROM","NOTIFICATION_FATAL_EMAILID","NOTIFICATION_SEVERE_EMAILID","NOTIFICATION_ERROR_EMAILID","NOTIFICATION_GENERAL_EMAILID","NOTIFICATION_ENVIRONMENT","NOTIFICATION_EMAIL_TEMPLATE");
	public static final String  DRAW_CAP = "drawCap";
	public static final String  PEC_CAP = "pecCap";
	public static final String  CAGE_TYPE = "cageType";
	public static final String  NO_OF_CAB_OR_WORKSTATIONS = "noOfCabsOrWorkstations";
	public static final String  POWER_DRAW_CAP = "powerDrawCap";
	public static final String  AREA_SQR_FEET ="areaSqFeet";
	public static final String  AREA_SQR_METER = "areaSqMeter";
	public static final String  CAGE_BCTR_NUM = "cageOrBctrNumber";
	public static final String  CAPACITY_STATUS_CRITERIA = "capacityStatus";
	public static final String  PROD_TYPE = "productType";
	public static final String  POWER_PEC_CAP = "powerPecCap";
	public static final String  COUNTRY_DOMAIN = "countryDomain";
	public static final String  MM_DD_YYYY = "mm/dd/yyyy";
	public static final String CUSTOMER_UCMID_CAP="Customer UCMID";
	public static final String CUSTOMER_NAME_CAP="Customer Name";
	public static final String REPORT_PATTERN_ID="reportPatternId";
	public static final String CUSTOMER_ACCOUNT_NUMBER="customerAccountNumber";
	public static final String CUSTOMER_ACCOUNT_NUMBER_DEFAULT_LABEL="Customer Account Number";
	
	public static final String COLO_RQST_ID="coloReqId";
	
	public static final String PRODUCT_TYPE_CAGE = "CAGE";
	public static final String PRODUCT_TYPE_CABINET = "CABINET";
	public static final String PRODUCT_TYPE_BCTR = "BCTR";
	public static final String PRODUCT_TYPE_WORKSTATION = "WORKSTATION";
	
	public static final String 	SCHEDULER_NAME="schedulerName";
	public static final String FROM_DATE = "fromDate";
	public static final String TO_DATE = "toDate";
	public static final String WORKSPACE_TYPE_ID = "workspaceTypeId";
	public static final String LEGACY_SYSTEM_NAME = "legacySystemName";
	public static final String DEVICE_NAME = "switchName";
	public static final String DEVICE_ID = "devices";
	public static final String TIME_ZONE = "timeZoneDescription";
	public static final String USER_PREF = "userPref";
	public static final String USER_ID = "userId";
	public static final String  OPPORTUNITY_ID = "opportunityId";
	public static final String WORKSPACE_TYPE = "workSpaceType";
	public static final String IS_AUTOCAD_DIAG_EXISTS = "isAutocadDiagExists";
	public static final String COLO_CHECK_CAP_REQ_ID = "coloCheckCapRequestId";
	public static final String CAGE_STATUS_ID = "statusId";
	public static final String COMMA = ",";
	
	public static final String XML_GROUP_CATEGORY = "xmlGroup";
	public static final String FILE_NAME = "fileName";
	public static final String FILE_PATH = "filePath";
	public static final String USER_INFO = "userInfo";	
	public static final String IS_VIRTUAL_CAGE = "isVirtualCage";
	public static final String LINKED_PHYSICAL_CAGE = "linkedPhysicalCage";
	public static final String LINKED_PHYSICAL_CAGE_ID = "linkedPhysicalCageId";
	public static final String VIRTUAL_CAGE = "virtualCage";
	
	//IN PROGRESS ORDER CR
	public static final String EQX_IN_PROGRESS_ORDER_LINES = "EQIX.EQX_AP_IN_PROGRESS_ORDER_LINES ";
	public static final String ACTION_CODE = "ACTION_CODE";
	public static final String POE = "POE";
	public static final String PRODUCT_TYPE_CAPS = "PRODUCT_TYPE";
	public static final String CUSTOMER_CAPS = "CUSTOMER";
	public static final String ASSET_CAB_US_ID = "ASSET_CAB_USID";
	
	public static final String VALIDATION_STATUS = "validStatus";
	public static final String ACTIVE = "Active";
	public static final String SEQNUM = "seqNum";
	public static final String COMPLETED = "Completed";
	public static final String IBXPRODTHRESHOLD_PRODUCT_ID = "productId";
	public static final String ELIGIBLE_FOR_MULTIPLE_RESERVATION="eligibleForMultipleReservation";
	public static final String UCMID = "UCMID";
	public static final String SOURCE_SYSTEM="SOURCESYSTEM";
	public static final String SOURCE_SYSTEM_LINEID="SOURCESYSTEMLINEID";
	public static final String SOURCE_SYSTEM_LINEITEMID="SOURCESYSTEMLINEITEMID";
	public static final String PORTUSAGESTATUS="PORTUSAGESTATUS";
	//public static final String SOURCE_SYSTEM_LINEITEMID="SOURCESYSTEMLINEITEMID";
	
	//Multiple Reservation CR
	public static final String NOTES = "notes";
	public static final String ELIGIBLE_FR_MUL_RESERVATION = "eligibleForMultipleReservation";
	
	//BCTR corrections
	public static final String IS_WORKSTATION = "isWrokstation";
	public static final String AVAILABILITY_STATUS2 = "availabilityStatus2";
	public static final String AVAILABILITY_STATUS3 = "availabilityStatus3";
	public static final String AVAILABILITY_STATUS4 = "availabilityStatus4";
	public static final String CUSTOMER_NAME2 = "customerName2";
	public static final String CUSTOMER_NAME3 = "customerName3";
	public static final String CUSTOMER_NAME4 = "customerName4";
	public static final String AVAILABILITY_STATUS_ID2 = "availabilityStatusId2";
	public static final String AVAILABILITY_STATUS_ID3 = "availabilityStatusId3";
	public static final String AVAILABILITY_STATUS_ID4 = "availabilityStatusId4";
	public static final String CUSTOMER_ID2= "customerId2";
	public static final String CUSTOMER_ID3 = "customerId3";
	public static final String CUSTOMER_ID4 = "customerId4";
	
	public static final String SELECTED_CAGE_CAPS="SELECTED_CAGE";
	public static final String QUOTE_NUMBER = "quoteNumber";
	public static final String CFR_TYPE = "cfrType";
	public static final String POF_NAME = "pofName";
	public static final String Y_CHARACTER = "Y";
	public static final String IS_MACD_REQUEST = "isMacdRequest";
	public static final Object N_CHARACTER = "N";
	public static final String CAGEID = "CAGE_ID";
	
	public static final String BCTR_ROOMTYPE_ID = "bctrRoomTypeId";
	public static final String BCTR_ROOMTYPE = "bctrRoomType";
	public static final String EQUINIX_CAGE_TYPE_ID = "equinixCageTypeId";
	public static final String EQUINIX_CAGE_TYPE = "equinixCageType";
	public static final String EQUINIX_CABINET_TYPE_ID = "equinixCabinetTypeId";
	public static final String EQUINIX_CABINET_TYPE = "equinixCabinetType";
	public static final String SRC_CAGE_NUMBER = "sCageNum";
	public static final String ACTION_TYPE="poeAction";
	public static final String PART_NUMBER="partNum";
	public static final String CAB_PART_NUMBER="CAB00001.ELEM";
	public static final String DELETE="Delete";
	public static final String REFNUMBER = "refNumber";
	public static final String CREATED_DATE = "createdDate";
	public static final String ADD = "ADD";
	public static final String SELECTED_CAGE = "selectedCage";
	public static final String ACT_TYPE="actionType";
	public static final String PROD_FAMILY = "productFamily";
	public static final String MSG_STATUS = "messageStatus";
	
}