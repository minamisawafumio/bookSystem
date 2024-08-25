//package fm.businessLogic.common;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class DbMongoUtil {
//	
//	private static DbMongoUtil dbMongoUtil = new DbMongoUtil();
//
//	//外部から new させない為、プライベートのコンストラクタ―を表記している
//	private DbMongoUtil() {}
//	
//	public static DbMongoUtil getInstance() {
//		return dbMongoUtil;
//	}
//		    
//	MongoClient client = new MongoClient("localhost", 27017);
//	
//	MongoCollection<Document> coll = getMongoClient().getDatabase("db0003").getCollection("documents");
//	
//	public MongoCollection<Document> getMongoCollection() {
//    	if (coll == null) {
//    		coll = getMongoClient().getDatabase("db0003").getCollection("documents");
//    	}
//    	return coll;
//	}
//
//
//	public MongoClient getMongoClient() {
//    	if (client == null) {
//    		client = new MongoClient("localhost", 27017);
//    	}
//    	return client;
//	}
//
//
//	public List<Object> getDataList(MongoCursor<?> cursor) {
//    	var list = new ArrayList<Object>();
//    	while (cursor.hasNext()) {
//    		list.add(cursor.next());
//    	}
//    	return list;
//	}
//}
