//package fm.businessLogic.common;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//
//
//
//public class DbUtil {
//	
//	private static DbUtil dbUtil = new DbUtil();
//	
//	//外部から new させない為、プライベートのコンストラクタ―を表記している
//	private DbUtil() {}
//	
//	public static DbUtil getInstance() {
//		return dbUtil;
//	}
//	
//	//当クラスは、専用のSQLセッションを持つ（漫画のページングで使用している）　漫画データ検索以外で使用しない事
//	SqlSession sqlSession;
//
//	String systemPath = FileUtil.getInstance().getSystemPath();
//
//	public SqlSession getSqlSession(){
//		if (sqlSession == null) {
//			sqlSession = getNewSqlSession();
//		}
//		return sqlSession;
//	}
//    
//	public SqlSession getSqlSession(String xmlFileName){
//		if (sqlSession == null) {
//			sqlSession = getNewSqlSession(xmlFileName);
//		}
//		return sqlSession;
//	}
//	
//    public SqlSession getNewSqlSession(){
////		    	return getNewSqlSession("mybatis-config14.xml");
//    	return getNewSqlSession("mybatis-config15.xml");
//	}
//    
//    public SqlSession getNewSqlSession(String xmlFileName){
//
//    	SqlSession session = null;
//    	
//		try {
//			var in = new FileInputStream(systemPath + "/" + Const.WORKSPACE_NAME + "/skdb/src/main/java/jp/co/fm/data/" + xmlFileName);
//	        session = new SqlSessionFactoryBuilder().build(in).openSession();
//	        session.getConfiguration().addMapper(DbToolMapper.class);
//	        
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//        
//        return session;
//	}
//
//	/**
//	 * 文字列をハッシュ化して返す（MySQLの関数を利用する)
//	 * @param moji
//	 * @return
//	 */
//	public String sha512(String moji){
//	    var sql = "SELECT SHA2('" + moji + "', 512) AS " + Const.HASH_DATA;
//
//        return (String) select(getSqlSession(), sql).get(0).get(Const.HASH_DATA);
//	}
//
//	public List<Map<String, Object>> select(SqlSession sqlSession, String sql) {
//		return sqlSession.getMapper(DbToolMapper.class).select(sql);
//	}
//	
//	public List select(Connection connection, String sql) throws SQLException {
//		var list = new ArrayList<Map>();
//
//		PreparedStatement statement4 = connection.prepareStatement(sql);
//		ResultSet rs = statement4.executeQuery();
//        
//		Map<String, Object> map = null;
//        while(rs.next()){
//          map = new HashMap<String, Object>();
//        	
//          ResultSetMetaData metaData = rs.getMetaData();
//          int nColumns = metaData.getColumnCount();
//          
//          for (int i = 1; i <= nColumns; ++i) {
//        	  map.put(metaData.getColumnName(i), rs.getString(i));
//          }
//          list.add(map);
//        }
//        return list;
//	}
//
//	public Map<String, Object> selectOne(SqlSession sqlSession, String sql) {
//		return sqlSession.getMapper(DbToolMapper.class).selectOne(sql);
//	}
//
//	/**
//	 * レコード件数取得
//	 * @param sqlSession
//	 * @param inObject
//	 * @param sql
//	 * @return
//	 */
//	public Long selectCount(SqlSession sqlSession, String sql) {
//		return sqlSession.getMapper(DbToolMapper.class).selectCount(sql).longValue();
//	}
//	
//	public Integer insert(SqlSession sqlSession, String sql) {
//		return sqlSession.getMapper(DbToolMapper.class).insert(sql);
//	}
//	
//	public Integer update(SqlSession sqlSession, String sql) {
//		return sqlSession.getMapper(DbToolMapper.class).update(sql);
//	}
//	
//	public String getWhereKu(Map<String, Object> map){
//    	var sb = new StringBuffer(" where ");
//        
//    	var pmKeySet = Set.of(map.get(SystemConst.PK_KEY).toString().split(","));
//
//        for (var key : map.keySet()) {
//        	if(map.get(key)==null) {
//        		continue;
//        	}
//
//			if(key.equals(SystemConst.TABLE_NAME) || key.equals(SystemConst.PK_KEY)) {
//				continue;
//			}
//			//プライマリーキーで無い場合、読み飛ばす
//			if(! pmKeySet.contains(key)) {
//				continue;
//			}
//
//			var add = "";
//
//			if (map.get(key).getClass().getTypeName().equals("java.lang.String")) {
//				add = "'";
//			}
//			
//			sb.append(key);
//			sb.append("=");
//			sb.append(add);
//			sb.append(map.get(key));
//			sb.append(add);
//			sb.append(" and ");
//		}		        
//        
//		sb.delete(sb.length() - 5, sb.length());
//
//        return sb.toString();
//	}
//    
//	public String getSelectSql(Map<String, Object> map) {
//
//    	var rtnSt = """ 
//		    			select %s
//		    			from %s
//		    			%s;
//    				""";
//
//    	var st1 = map.get(SystemConst.TABLE_ITEMS).toString();
//    	var st2 = map.get(SystemConst.TABLE_NAME).toString();
//    	var st3 = getWhereKu(map);
//    	
//        return String.format(rtnSt, st1, st2, st3);
//    }	
//    /**
//     * 有れば更新無ければ追加
//     * @param sqlSession
//     * @param object
//     * @param primaryKey
//     * @return
//     */
//	public Integer upsert(SqlSession sqlSession, Map<String, Object> map) throws Exception{
//    	var sql = getSelectCountSql(map);
//    	
//    	if(selectCount(sqlSession, sql) > 0) {
//    		return update(sqlSession, getUpdateSql(map));
//	   	} else {
//	   		return insert(sqlSession, getInsertSql(map));
//    	}
//    }
//
//	public String getUpdateSql(Map<String, Object> map){
//
//    	var sb = new StringBuffer("update ");
//        sb.append(map.get(SystemConst.TABLE_NAME).toString());
//        sb.append(" set ");
//
//        for (var key : map.keySet()) {
//			if(key.equals(SystemConst.TABLE_NAME) || key.equals(SystemConst.PK_KEY)) {
//				continue;
//			}
//
//			var add = "";
//			
//			var typeName = map.get(key).getClass().getTypeName();
//			
//			if (typeName.equals("java.lang.String") || typeName.equals("java.sql.Timestamp")) {
//				add = "'";
//			}
//			
//			sb.append(key);
//			sb.append("=");
//			sb.append(add);
//			sb.append(map.get(key));
//			sb.append(add);
//			sb.append(",");
//		}		        
//        
//		sb.delete(sb.length() - 1, sb.length());
//
//        sb.append(getWhereKu(map));
//
//        return sb.toString();
//	}
//
//	public String getInsertSql(Map<String, Object> map) {
//		var sb = new StringBuffer("insert into ");
//        sb.append(map.get(SystemConst.TABLE_NAME).toString());
//        
//        var sb2 = new StringBuffer(" (");
//        var sb3 = new StringBuffer(" VALUES (");
//
//        for (var key : map.keySet()) {
//			if(key.equals(SystemConst.TABLE_NAME) || 
//					key.equals(SystemConst.PK_KEY)) {
//				continue;
//			}
//
//			var add = "";
//			
//			if (map.get(key).getClass().getTypeName().equals("java.lang.String")) {
//				add = "'";
//			}
//			
//			sb2.append(key);
//			sb2.append(",");
//
//			sb3.append(add);
//			sb3.append(map.get(key));
//			sb3.append(add);
//			sb3.append(",");
//		}		        
//        
//		sb2.delete(sb2.length() - 1, sb2.length());
//		sb3.delete(sb3.length() - 1, sb3.length());
//
//		sb2.append(")");
//		sb3.append(")");
//
//        sb.append(sb2.toString());
//        sb.append(sb3.toString());
//
//        return sb.toString();
//	}
//
//	public String getDeleteSql(Map<String, Object> map) {
//		var sb = new StringBuffer("delete from ");
//        sb.append(map.get(SystemConst.TABLE_NAME).toString());
//        sb.append(getWhereKu(map));
//        return sb.toString();
//	}
//	
//	public String getSelectCountSql(Map<String, Object> map) {
//		var sb = new StringBuffer("select count(*) from ");
//        sb.append(map.get(SystemConst.TABLE_NAME).toString());
//        sb.append(getWhereKu(map));
//        return sb.toString();
//	}
//	
//    public Connection getConnection() throws SQLException {
//    	Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testdb15", "postgres", "postgres");
//    	return connection;
//    }
//}
