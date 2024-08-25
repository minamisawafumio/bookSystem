package com.itkoza.fm.businessLogic.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;



public class JsonUtil {
	
	private static JsonUtil jsonUtil = new JsonUtil();

	//外部から new させない為、プライベートのコンストラクタ―を表記している
	private JsonUtil() {}
	
	public static JsonUtil getInstance() {
		return jsonUtil;
	}

	private ObjectMapper mapper;
	
	public ObjectMapper getObjectMapper() {
		if (mapper == null) {
			mapper = new ObjectMapper();
		}
		return mapper;
	}

	public JsonNode makeJsonStringToJsonNode(String jsonSt) {
		
		var om = getObjectMapper();

		JsonNode node = null;
		
		try {
			node = om.readTree(jsonSt);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return node;
	}

	public Map<String, Object> makeMapFromJsonSt(String jsonSt) {

		Map<String, Object> map = null;

         try {
			map = getObjectMapper().readValue(jsonSt, new TypeReference<Map<String, Object>>(){});
		} catch (IOException e) {
			e.printStackTrace();
		}

        return map;
	}

	public Map<String, Object> makeMapFromJsonSt(StringBuffer jsonSt) {
		return makeMapFromJsonSt(changeString(jsonSt.toString()));
	}

	public List<String[]> makeListFromJsonSt(String jsonSt) {
		var rtnList = new ArrayList<String[]>();

		var jsonNode = makeJsonStringToJsonNode(jsonSt);

		var sssss = jsonNode.fields();
        while (sssss.hasNext()) {
        	var fieldName = sssss.next();
        	String[] data = {fieldName.getKey().toString(), fieldName.getValue().toString()};
        	rtnList.add(data);
        }

        return rtnList;
	}

	public List<String> makeListFromJsonSt2(String jsonStr) throws IOException {
		List<String> todoList = null;

		try {
			todoList = Arrays.asList(getObjectMapper().readValue(jsonStr, String[].class));
		} catch (IOException e) {
			throw e;
		}
		return todoList;
	}
	
	public List<Object> makeObjectListFromJsonSt(String jsonStr) {
		List<Object> todoList = null;

		try {
			todoList = Arrays.asList(getObjectMapper().readValue(jsonStr, Object[].class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return todoList;
	}

	/**
	 *
	 * @param object
	 * @param stringObject
	 * @return
	 */
//	public Object makeObjectFromJsonString(Class inClass, String stringObject) {
//
//		Object rtnObject = null;
//		
//		try {
//			var name = inClass.getCanonicalName();
//			var newObject = Class.forName(name).getDeclaredConstructor().newInstance();
//			rtnObject = JSON.decode(stringObject, newObject.getClass());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return rtnObject;
//	}

	/**
	 * 
	 */
//	public Object copyBean(Object object) {
//    	return makeObjectFromJsonString(object.getClass(), makeJsonStringFromObject(object));
//    }

	/**
	 *
	 * @param beanObject
	 * @return
	 */
	public String makeJsonStringFromObject(Object beanObject){

		String jsonString = null;

		try {
			jsonString = getObjectMapper().writeValueAsString(beanObject);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return jsonString;
	}

	/**
	 *　整形して返却する
	 * @param beanObject
	 * @return
	 */
	public String makeFormatJsonStringFromObject(Object beanObject){
		
		String jsonString = null;

		try {
			jsonString = new ObjectMapper()
					.enable(SerializationFeature.INDENT_OUTPUT)
					.writeValueAsString(beanObject);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return jsonString;
	}

	public String makeJsonStringFromObject2(Object beanObject){
		return makeJsonStringFromObject(beanObject).replaceAll("\"","'");
	}

	/**
	 * オブジェクトをコピーする
	 * @param copyMoto
	 * @return
	 * @throws JsonProcessingException
	 */
//	public Object objectCopy(Object copyMoto){
//		Object rtnObject = null;
//
//    	try {
//    		var JsonString = getObjectMapper().writeValueAsString(copyMoto);
//    		rtnObject = JSON.decode(JsonString, copyMoto.getClass());
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//
//    	return rtnObject;
//	}

	/**
	 *
	 * @param dto
	 * @param json
	 * @return
	 */
	public <T> T parse(Class<T> dto, String json){
        try{
             return getObjectMapper().readValue(json, dto);
        }catch(IOException e){
             return null;
        }
	}

	public String changeString(String jsonStr) {
		return new StringBuffer(jsonStr.replaceAll("'", "\"")).toString();
	}
	
	
	public String getJsonSt(String jsonStr, Object []objectArray) {
		var moji = StringUtil.getInstance().bindMoji(jsonStr, objectArray);
        moji = JsonUtil.getInstance().changeString(moji);
        return format(moji);
	}
	
	
	/**
	 * JSON整形
	 */
	public String format(String jsonStr) {
		var result = makeMapFromJsonSt(jsonStr);
        return JsonUtil.getInstance().makeFormatJsonStringFromObject(result);
	}
}
