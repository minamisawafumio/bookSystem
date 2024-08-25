package com.itkoza.fm.businessLogic.common;

public class MailUtil {
	
	private static MailUtil mailUtilFactory = new MailUtil();
	
	//外部から new させない為、プライベートのコンストラクタ―を表記している
	private MailUtil() {}
	
	static MailUtil getInstance() {
		return mailUtilFactory;
	}

	private String host; // ホストアドレス
	private String user; // アカウン�?
	private String password; // パスワー�?

//	private Folder folder = null;
//	private Store  store = null;
	
	/**
	 *
	 * @param count
	 * @return
	 */

//	public Map<String, Object> getMailBean(Integer count){
//
//		var message = getMessage(count);
//
//		if (message == null) {
//			return null;
//		}
//
//		var mailBean = new HashMap<String, Object>();
//
//	    try{
//	    	var address = message.getFrom();
//
//		    // 差出人
//		    if (address != null) {
//		    	var fromList = new ArrayList<>();
//		    	for (var j = 0; j < address.length; j++) {
//		    		fromList.add(MimeUtility.decodeText(address[j].toString()));
//		    	}
//		    	mailBean.put("FromList", fromList);
//		    }
//
//		    address = message.getRecipients(RecipientType.TO);
//
//		    //
//		    if (address != null) {
//		    	var toList = new ArrayList<>();
//		    	for (var j = 0; j < address.length; j++) {
//		    		toList.add(MimeUtility.decodeText(address[j].toString()));
//		    	}
//
//		    	mailBean.put("ToList", toList);
//		    }
//
//		    // 題名
//		    mailBean.put("Subject", message.getSubject());
//
//
//		    // 日�?
//		    var date = DateUtil.getInstance().changeDate(message.getSentDate());
//
//		    if(date != null){
//		    	var dayFormat = DateUtil.getInstance().getYyyyMMdd(date);
//		    	mailBean.put("SentDate", dayFormat);
//
//		    }
//		    // サイズ
//		    mailBean.put("Size", message.getSize());
//
//		    // �?容
//		    mailBean.put("Content", message.getContent());
//	    }catch(Exception e){
//	    	e.printStackTrace();
//            return null;
//	    }
//
//		return mailBean;
//	}

	/**
	 *
	 * @param count
	 * @return
	 */
//	public Message getMessage(int count){
//
//		try {
//			var message = folder.getMessage(count);
//			return message;
//		} catch (MessagingException e) {
//
//		}
//		return null;
//	}

	/**
	 * メ�?セージ件数取�?
	 * @return
	 */
//	public Integer getMessageCount(){
//		try {
//			return folder.getMessageCount();
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		}
//		return 0;
//	}

	/**
	 *
	 */
//	public Message[] getMessage(){
//		Message[] messages = null;
//
//		try {
//			messages = folder.getMessages();
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		}
//		return messages;
//	}

	/**
	 *
	 * @return
	 */
//	public Boolean open(){
//		boolean rtnBool = false;
//		// 接続しま�?
//		var session = Session.getDefaultInstance(System.getProperties(), null);
//
//		try {
//			store = session.getStore("pop3");
//			store.connect(host, -1, user, password);
//			folder = store.getFolder("INBOX");
//			// フォル�?ーを開きま�?
//			folder.open(Folder.READ_ONLY);
//			rtnBool = true;
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		}
//		return rtnBool;
//	}

	/**
	 *
	 * @return
	 */
//	public boolean close() {
//		var rtnBool = false;
//
//		try {
//			folder.close(false);
//			store.close();
//			rtnBool = true;
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		}
//
//		return rtnBool;
//	}

	/**
	 * メールアドレスチェ�?ク
	 * @param mailAddress
	 * @return
	 */
//	public boolean checkMailAddress(String mailAddress) {
//		try {
//			new InternetAddress(mailAddress, true);
//		}
//		catch (AddressException e) {
//			return false;
//		}
//		return true;
//	}
}
