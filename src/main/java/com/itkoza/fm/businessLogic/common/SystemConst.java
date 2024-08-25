package com.itkoza.fm.businessLogic.common;
import java.util.Calendar;

public class SystemConst {
	public static final String[] PM_KEY_T_0001 = {"key1", "key2", "key3"};
	/** T_1010 ( corpCd , delFlg , recCd , startYmd , item01 ) */
	public static final String[] PM_KEY_T_1010 = {"corpCd","delFlg","recCd","startYmd","item01"};
	public static final String[] PM_KEY_T_0160 = {"corpCd","userId","delFlg"};
	public static final String[] PM_KEY_T_0161 = {"corpCd","userId","userIdSerialNo","userKey","delFlg"};

	public static final String PK_T_1010 = "corp_cd,del_flg,rec_cd,start_ymd,item01";
	public static final String PK_T_0160 = "corp_cd,user_id,del_flg";
	public static final String PK_T_0001 = "key1,key2,key3";

	public static final String TABLE_NAME  = "tableName";
	public static final String TABLE_ITEMS = "tableItems";
	public static final String PK_KEY      = "pmKey";
	public static final String MYBATIS_CONFIG = "mybatis-config15.xml";
	public static final String SQL_T_0001_01 = """
			select
			 key3, value
			 from
			 T_0001
				where
			 key1='1' and
			 key2='?' and
			 key3 in (?)			
			""";

	public static final String SQL_T_1010_01 = """
			 select					
			 item02, item04, item05	
			 from						
			 T_1010		
				where	
			 item01='%s' and
			 corp_cd='01' and
			 rec_kbn='4' and
			 rec_cd='014' and
			 del_flg='0' limit 1
				""";
	
	public static final String SQL_T_0160_01 = """
			 select *
			 from T_0160
				where
			 corp_cd='01' and
			 user_id='?' and
			 del_flg='0' limit 1		
			""";

	public static final int mangaPageLimit = 100;
	static Calendar ca;

	//検索キー作成　ここまで
	public static final String SQL_T_1010_02 = """
 			 WITH ABC AS (
 			select item01, item02, item03, item16 from T_1010
 			 where
 				  corp_cd ='01'
 			 and del_flg ='0'
 			 and rec_cd  ='014'
 			 and rec_kbn ='4'
 			 %s
 			) select * from ABC where
 			 %s
 			""";

	public static Calendar getCalendar() {
    	if (ca == null) {
    		ca = Calendar.getInstance();
    	}
    	return ca;
	}
}
