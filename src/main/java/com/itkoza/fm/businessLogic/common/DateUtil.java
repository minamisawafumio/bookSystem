package com.itkoza.fm.businessLogic.common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DateUtil {
	
	private static DateUtil dateUtil = new DateUtil();

	//外部から new させない為、プライベートのコンストラクタ―を表記している
	private DateUtil() {}

	public static DateUtil getInstance() {
		return dateUtil;
	}
	
	/**
	 * 00時以降の時間変更
	 * (例:030505 → 270505)
	 * @param hhmmss
	 * @return
	 */
	public String changeHhMmssForBeforDay(String hhmmss) {
		var hh = Integer.parseInt(hhmmss.substring(0, 2)) + 24;
		var mm = Integer.parseInt(hhmmss.substring(2, 4));
		var dd = Integer.parseInt(hhmmss.substring(4, 6));
		return getDayFormat(hh, mm, dd);
	}

	/**
	 * システム日付を取得する
	 * @return
	 */
	public Date getSystemDate() {
		return SystemConst.getCalendar().getTime();
	}

	/**
	 * Timestamp 変換
	 *
	 * @param str
	 * @return
	 */
	public Timestamp getTimestamp(String str) {
		return new Timestamp(getDate(str).getTime());
	}

	/**
	 * 日付フォーマット変換
	 * @param yyyymmdd
	 * @return
	 */
	public String changeFormatDate(String yyyymmdd){
		var sb = new StringBuilder();
		sb.append(yyyymmdd.substring(0, 4));
		sb.append("年");
		sb.append(yyyymmdd.substring(4, 6));
		sb.append("月");
		sb.append(yyyymmdd.substring(6, 8));
		sb.append("日");
		return sb.toString();
	}

	/**
	 * 指定日からＮか月後の日付を取得する
	 * @param inDate    YYYYMMDD
	 * @param addMonth  月数
	 * @return
	 */
	public String getAddMonthDate(String inDate, Integer addMonth) {

		var date = getDate(inDate);

		var cal = dateToCalendar(date);

		var addYera   = 0;
		var addDate   = 0;
		var addHour   = 0;
		var addMinute = 0;
		var addSecond = 0;
		var addMillisecond = 0;

	   	var calendar = addDay(cal, addYera, addMonth, addDate, addHour, addMinute, addSecond, addMillisecond);

	   	return getYyyyMMddHHmmssSSS(calendar);
	}

	/**
	 * 指定日数後の日付
	 * 指定日からＮ日後の日付を取得する
	 * 例 addDay("2004/02/29", 15)
	 * @param date
	 * @param addDate
	 * @return YyyyMMddHHmmssSSS
	 */
	public String getInDesignateDays(String inYyyyMMddHHmmssSSS, Integer addDate){

		var cal = stToCalendar(inYyyyMMddHHmmssSSS);

		var addYera   = 0;
		var addMonth  = 0;
		var addHour   = 0;
		var addMinute = 0;
		var addSecond = 0;
		var addMillisecond = 0;

	   	var calendar = addDay(cal, addYera, addMonth, addDate, addHour, addMinute, addSecond, addMillisecond);

	   return getYyyyMMddHHmmssSSS(calendar);
	}

	/**
	 * 経過日数計算
	 * @param startYyyyMmDd
	 * @param endYyyyMmDd
	 * @return
	 */
	public Long getIntervalDayCount(String startYyyyMmDd ,String endYyyyMmDd) {

		var startDate = stToDate(startYyyyMmDd);

		var endDate   = stToDate(endYyyyMmDd);

		return getIntervalDayCount(startDate, endDate);
	}

	/**
	 * 経過日数計算
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Long getIntervalDayCount(Date startDate, Date endDate){

		var timeDiff = difference(startDate, endDate);

	    // 差分の日数を算出します。
		var dayDiff = timeDiff / (1000 * 60 * 60 * 24 );

		return dayDiff;
	}

	public Long difference(Date startDate, Date endDate){
		long startLong = startDate.getTime();
		long endLong   = endDate.getTime();
		return endLong - startLong;
	}

	/**
	* 経過ミリ秒数を取得する (SimpleDateFormatはスレッドセーフではないので注意)
	* @param startYyyyMMddHHmmssSSS
	* @param endYyyyMMddHHmmssSSS
	* @return
	*/
	public Long difference(String startYyyyMMddHHmmssSSS, String endYyyyMMddHHmmssSSS){
		var dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		try {
			var startDate = dateFormat.parse(startYyyyMMddHHmmssSSS);
			var endDate   = dateFormat.parse(endYyyyMMddHHmmssSSS);

		    return difference(startDate, endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 経過時間取得
	 * @param startYyyyMMddHHmmssSSS
	 * @param endYyyyMMddHHmmssSSS
	 * @return 経過時間の配列(時、分、秒、ミリ秒)
	 */
	public Integer[] differenceArray(String startYyyyMMddHHmmssSSS, String endYyyyMMddHHmmssSSS){
		Long diff   = difference(startYyyyMMddHHmmssSSS, endYyyyMMddHHmmssSSS);
		Long hour   = diff   / (1000 * 60 * 60);
		Long amari  = diff   - (1000 * 60 * 60 * hour);
		Long min    = amari  / (1000 * 60);
		Long amari2 = amari  - (1000 * 60 * min);
		Long sec    = amari2 / (1000);
		Long amari3 = amari2 - (1000 * sec);

		Integer trnInt[] = {hour.intValue(), min.intValue(), sec.intValue(), amari3.intValue()};
		return trnInt;
	}

	/**
	 * 経過時間を文字列で返す
	 * @param startYyyyMMddHHmmssSSS
	 * @param endYyyyMMddHHmmssSSS
	 * @return
	 */
	public String getDifferenceHHMMDDSSS(String startYyyyMMddHHmmssSSS, String endYyyyMMddHHmmssSSS){
		Integer intDay[] = differenceArray(startYyyyMMddHHmmssSSS, endYyyyMMddHHmmssSSS);
		return intDay[0] + "時間 " + intDay[1] + "分 " + intDay[2] + "秒 " + intDay[3] +"ミリ秒";
	}

	/**
	 * 日付文字列指定でDate型を生成
	 * @param yyyyMMddHHmmssSSS
	 * @return
	 */
	public Date getDate(String yyyyMMddHHmmssSSS) {
    	//(SimpleDateFormatはスレッドセーフではないので注意)
    	var dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        try {
        	var utilDate = dateFormat.parse(yyyyMMddHHmmssSSS);
        	return utilDate;
        } catch ( ParseException e ) {
            return null;
        }
    }

	/**
	 * 指定日を数値配列に変換する
	 * @param yyyymmdd
	 * @return 0:年 1:月 2:日
	 */
	public Integer[] getIntDay(String yyyymmdd) {

		var stringYear  = yyyymmdd.substring(0, 4);
		var stringMonth = yyyymmdd.substring(4, 6);
		var stringDay   = yyyymmdd.substring(6, 8);

		Integer []rtnInt = {Integer.parseInt(stringYear),
				            Integer.parseInt(stringMonth),
				            Integer.parseInt(stringDay)};
		return rtnInt;
	}

	/**
	 * 指定日のカレンダーを取得する
	 * @param _year
	 * @param _month
	 * @param _day
	 * @return
	 */
	public Calendar getWeekCalendar(Integer _year, Integer _month, Integer _day) {
		var day = getDayFormat(_year, _month, _day);

		return dateToCalendar(getDate(day));
	}

	/**
	 * 指定日の曜日を取得する
	 * @param _year
	 * @param _month
	 * @param _day
	 * @return 1:日 2:月 3:火 4:水 5:木 6:金 7:土
	 */
	public Integer getWeekInteger(Integer _year, Integer _month, Integer _day) {
		var cal = getWeekCalendar(_year, _month, _day);
		var week = cal.get(Calendar.DAY_OF_WEEK);
		return week;
	}

	/**
	 * カレンダー
	 * @return
	 */
	public String getYyyyMMddHHmmssSSS(Calendar calendar) {

		var ye = calendar.get(Calendar.YEAR);
		var mo = calendar.get(Calendar.MONTH) + 1;
		var da = calendar.get(Calendar.DAY_OF_MONTH);
		var ho = calendar.get(Calendar.HOUR_OF_DAY);
		var mi = calendar.get(Calendar.MINUTE);
		var se = calendar.get(Calendar.SECOND);
		var ms = calendar.get(Calendar.MILLISECOND);

		return getDayFormat(ye, mo, da, ho, mi, se, ms);
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public String getYyyyMMddHHmmssSSS(Date date) {
		var calendar = dateToCalendar(date);
		return getYyyyMMddHHmmssSSS(calendar);
	}

	/**
	 *
	 * @param date
	 * @return
	 */
	public String getYyyyMMdd(Date date) {
		String yyyyMMddHHmmssSSS = getYyyyMMddHHmmssSSS(date);
		return yyyyMMddHHmmssSSS.substring(0, 8);
	}

	/**
	* 戻り値のフォーマット YYYYMMDD
	*/
	public String getDayFormat(Integer _year, Integer _month, Integer _day) {
		var rtn = new StringBuffer(Integer.toString(_year));

        if (_month < 10){
        	rtn.append("0" + Integer.toString(_month));
		}else{
			rtn.append(Integer.toString(_month));
		}

        if (_day < 10){
        	rtn.append("0" + Integer.toString(_day));
		}else{
			rtn.append(Integer.toString(_day));
		}
        return rtn.toString();
	}

	/**
	* 戻り値のフォーマット yyyyMMddHHmmssSSS
	*/
	public String getDayFormat(Integer _Ye, Integer _Ma, Integer _Da,Integer _Ho, Integer _Mi, Integer _Se, Integer _Ms) {
		var aa = new StringBuffer(getDayFormat(_Ye, _Ma, _Da));

		aa.append(getTimeFormat(_Ho, _Mi, _Se,  _Ms));

		return aa.toString();
	}

	/**
	 * 時分秒変換
	 * @param _ho
	 * @param _mi
	 * @param _se
	 * @return
	 */
	public String getTimeFormat(Integer _ho,Integer _mi,Integer _se) {
        String strSdayHHMM = getTimeFormat(_ho, _mi);

        if (_se < 10){
        	strSdayHHMM = strSdayHHMM + "0" + Integer.toString(_se);
		}else{
			strSdayHHMM = strSdayHHMM + Integer.toString(_se);
		}

        return strSdayHHMM;
	}

	public String getTimeFormat(Integer _ho,Integer _mi,Integer _se, Integer _ms) {
		var tt = new StringBuffer(getTimeFormat(_ho, _mi, _se));

		if (_ms < 10){
			tt.append("00" + Integer.toString(_ms));
		}else if (_ms < 100){
			tt.append("0"  + Integer.toString(_ms));
		} else {
			tt.append(Integer.toString(_ms));
		}
		return tt.toString();
	}

	/**
	 * 時分変換
	 * @param _ho
	 * @param _mi
	 * @return
	 */
	public String getTimeFormat(Integer _ho,Integer _mi) {
		var rtn = new StringBuffer();
        if (_ho < 10){
			rtn.append("0" + Integer.toString(_ho));
		}else{
			rtn.append(Integer.toString(_ho));
		}

        if (_mi < 10){
        	rtn.append("0" + Integer.toString(_mi));
		}else{
			rtn.append(Integer.toString(_mi));
		}

        return rtn.toString();
	}

	/**
	* ミリ秒
	*/
	public String getMillisecondFormat(Integer intMillisecond){

		String strMillisecond;

	    if (intMillisecond < 10){
			strMillisecond = "00" + Integer.toString(intMillisecond);
		}else if (intMillisecond < 100){
			strMillisecond = "0"  + Integer.toString(intMillisecond);
		}else{
			strMillisecond =        Integer.toString(intMillisecond);
		}

		return strMillisecond;
	}

	/**
	 * 指定日付(YYYYMMDD)の妥当性チェック
	 * @param inputDate
	 * @return
	 */
	public Boolean checkDate(String yyyymmdd){
		// 文字列長が8でない場合
		if (yyyymmdd.length() != 8) {
			return false;
		}

		Integer[]intDay =  getIntDay(yyyymmdd);

		Boolean rtnBool = true;

		Calendar cal = new GregorianCalendar();
		cal.setLenient(false);
		// 0:年, 1:月, 2:日
		cal.set(intDay[0], intDay[1] - 1, intDay[2]);

		try {
			cal.getTime();
		} catch (IllegalArgumentException iae) {
			rtnBool = false;
		}

		return rtnBool;
	}

	/**
	 * 指定月の最終日を取得する
	 * @param yearMonth
	 * @return
	 */
	public Integer getMonthEndDay(String yearMonth){
		//指定月の一日
		String firstDay  = yearMonth + "01";
		//一ヵ月後の年月日
		String nextMonthYyyyMmDd = getAddMonthDate(firstDay, 1);
		//月の最終日
		String monthEndDay = getInDesignateDays(nextMonthYyyyMmDd, - 1);
		return Integer.parseInt(monthEndDay.substring(6, 8));
	}

	/**
	 * 指定カレンダーインスタンスに日時を加算して、インスタンスを返す
	 * @param cal
	 * @param addYera
	 * @param addMonth
	 * @param addDate
	 * @param addHour
	 * @param addMinute
	 * @param addSecond
	 * @param addMillisecond
	 * @return
	 */
	private Calendar addDay(Calendar cal, Integer addYera,Integer addMonth,Integer addDate,
			Integer addHour,Integer addMinute,Integer addSecond, Integer addMillisecond) {
		cal.add(Calendar.YEAR, addYera);
		cal.add(Calendar.MONTH, addMonth);
		cal.add(Calendar.DATE, addDate);
		cal.add(Calendar.HOUR_OF_DAY, addHour);
		cal.add(Calendar.MINUTE, addMinute);
		cal.add(Calendar.SECOND, addSecond);
		cal.add(Calendar.MILLISECOND, addMillisecond);
		return cal;
	}

	/**
	 * 現在の曜日を返します
	 * @return	現在の曜日
	 */
	public String getDayOfTheWeek(Calendar cal) {
	    switch (cal.get(Calendar.DAY_OF_WEEK)) {
	        case Calendar.SUNDAY:   return "日";
	        case Calendar.MONDAY:   return "月";
	        case Calendar.TUESDAY:  return "火";
	        case Calendar.WEDNESDAY:return "水";
	        case Calendar.THURSDAY: return "木";
	        case Calendar.FRIDAY:   return "金";
	        case Calendar.SATURDAY: return "土";
	    }
	    throw new IllegalStateException();
	}

	/**
	 * 現在の曜日を返します
	 * @param yyyymmdd
	 * @return
	 */
	public String getDayOfTheWeek(String yyyymmdd) {
		Integer[]day = getIntDay(yyyymmdd);
		Calendar cal = getWeekCalendar(day[0], day[1], day[2]);
		return getDayOfTheWeek(cal);
	}

	/**
	 * java.util.Date から java.sql.Dateに変換する
	 * @param date
	 * @return
	 */
	public java.sql.Date changeDate(Date date){
		Calendar cal = SystemConst.getCalendar();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new java.sql.Date(cal.getTimeInMillis());
	}

	/**
	 * 経過時間時間チェック
	 * （現在時が指定した時間を超えた場合、例外を発生する）
	 * @param startTime
	 * @param passSecond
	 * @throws Exception
	 */
	public void checkTime(String startTime, Integer passSecond) throws Exception {
		//現在時取得
		String endTime = getComDateTime("{0, date,yyyyMMddHHmmssSSS}");

		Long passTime  = difference(startTime, endTime) / 1000;

		//ブランチ作成に消費する時間を超えた場合、例外を発生する
		if(passTime > passSecond){
			throw new Exception();
		}
	}

    /**
     * コンピュータ日付を取得する
     * @return
     */
	public Timestamp getComTimestamp(){
		return stToTimestamp(getComDateSt());
	}

    /**
     * コンピュータ日付を取得する
     * @return
     */
	public Calendar getComCalendar(){
		return stToCalendar(getComDateSt());
	}

    /**
     * コンピュータ日付を取得する
     * @return
     */
	public Date getComDate(){
		return stToDate(getComDateSt());
	}

    /**
     * コンピュータ日付を取得する
     * @return
     */
	public String getComDateSt() {
    	return getComDateTime("{0, date,yyyyMMddHHmmssSSS}");
    }

    /**
     * コンピュータ日付を取得する(フォーマット)
     * フォーマットは、getSystemDateTime の内容を参照
     * @return
     */
	public String getComDateTime(String format) {
    	var mf = new MessageFormat(format);
        Object[] objs = {SystemConst.getCalendar().getTime()};
        return mf.format(objs);
    }

    /**
     * 文字列からにDate変換  (SimpleDateFormatはスレッドセーフではないので注意)
     * @param yyyyMMddHHmmssSSS
     * @return
     */
	public Date stToDate(String yyyyMMddHHmmssSSS) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

        try {
            Date utilDate = dateFormat.parse(yyyyMMddHHmmssSSS);
            return utilDate;
        } catch ( ParseException e ) {
            return null;
        }
    }

    /**
     * Dateから文字列に変換  (SimpleDateFormatはスレッドセーフではないので注意)
     * @param date
     * @return
     */
	public String dateToSt(Date date) {
    	DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    	return df.format(date);
    }

    /**
     * DateからTimestampに変換
     * @param date
     * @return
     */
	public Timestamp dateToTimestamp(Date date){
        return new Timestamp(date.getTime());
    }

    /**
     * DateからCalendarに変換
     * @param date
     * @return
     */
	public Calendar dateToCalendar(Date date){
		return stToCalendar(dateToSt(date));
    }

	/**
	 * タイムスタンプからDateに変換
	 * @param timestamp
	 * @return
	 */
	public Date timestampToDate(Timestamp timestamp) {
    	return new java.util.Date(timestamp.getTime());
    }

	public Calendar timestampToCalendar(Timestamp timestamp) {
    	return dateToCalendar(timestampToDate(timestamp));
    }

    /**
     * 文字列からタイムスタンプに変換
     * @param yyyyMMddHHmmssSSS
     * @return
     */
	public Timestamp stToTimestamp(String yyyyMMddHHmmssSSS) {
    	return dateToTimestamp(stToDate(yyyyMMddHHmmssSSS));
    }

    /**
     * タイムスタンプから文字列に変換
     * @param timestamp
     * @return
     */
	public String timestampToSt(Timestamp timestamp) {
		return dateToSt(timestampToDate(timestamp));
	}

    /**
     * カレンダーから文字列に変換
     * @param calendar
     * @return
     */
	public String calendarToSt(Calendar calendar) {
		var aa = new StringBuffer();

        aa.append(String.format("%04d", calendar.get(Calendar.YEAR)));
        aa.append(String.format("%02d", calendar.get(Calendar.MONTH) + 1));
        aa.append(String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)));
        aa.append(String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)));
        aa.append(String.format("%02d", calendar.get(Calendar.MINUTE)));
        aa.append(String.format("%02d", calendar.get(Calendar.SECOND)));
        aa.append(String.format("%03d", calendar.get(Calendar.MILLISECOND)));

        return aa.toString();
	}

	/**
	 * 文字列からカレンダーに変換
	 * @param str
	 * @return
	 */
	public Calendar stToCalendar(String str) {
		var cal = SystemConst.getCalendar();

        cal.set(Calendar.YEAR, Integer.valueOf(str.substring( 0,  4)));
        cal.set(Calendar.MONTH, Integer.valueOf(str.substring( 4,  6)) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(str.substring( 6,  8)));
        cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(str.substring( 8, 10)));
        cal.set(Calendar.MINUTE, Integer.valueOf(str.substring(10, 12)));
        cal.set(Calendar.SECOND, Integer.valueOf(str.substring(12, 14)));
        cal.set(Calendar.MILLISECOND, Integer.valueOf(str.substring(14, 17)));

    	return cal;
    }

	/**
	 * カレンダーからDateに変換
	 * @param calendar
	 * @return
	 */
	public Date calendarToDate(Calendar calendar) {
    	return calendar.getTime();
    }

	/**
	 * ZonedDateTime　→ Date 変換
	 */
    public Date zonedDateTimeToDate(ZonedDateTime zonedDateTime){
    	return Date.from(zonedDateTime.toInstant());
    }

	/**
	 *  Date → ZonedDateTime 変換
	 */
	public ZonedDateTime dateToZonedDateTime(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault());
	}

	/**
	 * LocalDateTime　→ Date 変換
	 */
    public Date localDateTimeToDate(LocalDateTime localDateTime){
    	return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

	/**
	 * Date → LocalDateTime 変換
	 */
    public LocalDateTime dateToLocalDateTime(Date date){
    	return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

	
	/**
	 * カレンダーからTimestampに変換
	 * @param calendar
	 * @return
	 */
	public Timestamp calendarToTimestamp(Calendar calendar) {
		var timestamp = new Timestamp(calendar.getTimeInMillis());
    	return timestamp;
    }

    /**
     * 日付加算処理
     * カレンダーインスタンスに指定時間を加算して、インスタンスを返す
     * @param cal
     * @param yyyy
     * @param mm
     * @param dd
     * @param hh
     * @param mi
     * @param se
     * @param ms
     * @return
     */
	public Calendar addCalendar(Calendar cal, Integer yy,Integer mm,Integer dd, Integer hh,Integer mi,
    		                            Integer se, Integer ms) {
        cal.add(Calendar.YEAR, yy);
        cal.add(Calendar.MONTH, mm);
        cal.add(Calendar.DATE, dd);
        cal.add(Calendar.HOUR, hh);
        cal.add(Calendar.MINUTE, mi);
        cal.add(Calendar.SECOND, se);
        cal.add(Calendar.MILLISECOND, ms);
        return cal;
    }

    /**
     * Dateからjava.sql.Date変換処理
     * @param date
     * @return
     */
	public java.sql.Date dateToSqlDate(Date date){
		return calendarToSqlDate(dateToCalendar(date));
    }

	/**
     * カレンダーからjava.sql.Dateに変換する
     * @param date
     * @return
     */
	public java.sql.Date calendarToSqlDate(Calendar calendar){
    	var now = new java.sql.Date(calendar.getTimeInMillis());
        return now;
    }

    /**
     * sqlDateをカレンダーに変換する
     * @param date
     * @return
     */
	public Calendar sqlDateToCalendar(java.sql.Date date){
		var cal = SystemConst.getCalendar();
		cal.setTime(date);
		return cal;
	}

	/**
	 * sqlDateを文字列に変換する
	 * @param date
	 * @return
	 */
	public String sqlDateToSt(java.sql.Date date){
    	return calendarToSt(sqlDateToCalendar(date));
	}

	/**
	 * 指定年月の最終日を取得する。
	 * @param year
	 * @param month
	 * @return
	 */
	public Integer getLastDay(Integer year, Integer month){
		var aa = new StringBuffer();

        aa.append(String.format("%04d", year));
        aa.append(String.format("%02d", month));
        // 月に1日目を設定
        aa.append(String.format("%02d", 1));
        aa.append("000000000");
        // 指定日のカレンダーを取得する
        var cal = stToCalendar(aa.toString());

		// 指定月（の１日目）に１か月後を取得する
		cal = addCalendar(cal, 0, 1,  0, 0, 0, 0, 0);
		// １日前を取得する
		cal = addCalendar(cal, 0, 0, -1, 0, 0, 0, 0);
		String st = calendarToSt(cal);

		// 日付文字列を整数にして返す
		return Integer.parseInt(st.substring(6, 8));
	}

	// format = "{0, date,yyyy/MM/dd HH:mm:ss.SSS}"
	public String getSysDate(String format) {
        Object[] objs = {Calendar.getInstance().getTime()};
        return new MessageFormat(format).format(objs);
	}

	/**
	 * 曜日取得
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public Integer getWeek(Integer year,Integer month, Integer day) {
		if(month == 1 || month == 2){
			year = year - 1;
			month = month + 13;
		}else{
			month = month + 1;
		}

		int aa = (int)(year * 365.25);

		int bb = (int)(month * 30.6);

		int cc = year / 400;

		int dd = year / 100;

		int ee = aa + bb + cc + day - dd - 429;

		int x1 = ee / 7;

		int ff = x1 * 7;

	    return ee - ff;
	}
}
