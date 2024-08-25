package com.itkoza.fm.businessLogic.common;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CollectionUtil {
	
	private static CollectionUtil collectionUtil = new CollectionUtil();

	//外部から new させない為、プライベートのコンストラクタ―を表記している
	private CollectionUtil() {}
	
	public static CollectionUtil getInstance() {
		return collectionUtil;
	}
	
	/**
	* Listの内容を System.out に出力する
	*/
	public void listPrintln(List<String> l){
		for(int i=0;i<l.size(); i++){
			String s = l.get(i);
			System.out.println(s);
		}
	}

	public void mapPrintln(Map<String, Object> map){
        map.forEach((mapKey, value) -> {
			System.out.println( "key   = " + mapKey );
			System.out.println( "value = " + value);
        });
	}

	
	/**
	* Mapのキー（文字列）を ソートしてListで返す
	*/
	public List<String> sort(Map iMap){
		
		Object[] mapkey = iMap.keySet().toArray();

        Arrays.sort(mapkey);
		
		List<String> list1 = new ArrayList<String>();
		
		for (Object nKey :mapkey) {
			list1.add(nKey.toString());
        }

		return list1;
	}
	
	
	/**
	 * 文字列リストを昇順ソートする
	 * @param _list
	 * @return
	 */
	public List<String> sort(List<String> _list){
		Collections.sort(_list);
		return _list;
	}

	public List<String> sort(String []stArray){
		return sort(List.of(stArray));
	}

	/**
	 * 文字列リストを降順ソートする
	 * @param _list
	 * @return
	 */
	public List<String> sortDown(List<String> _list){
        Collections.reverse(_list);
		return _list;
	}

	/**
	 * 文字列配列を降順ソートする
	 * @param _list
	 * @return
	 */
	public List<String> sortDown(String []stArray){
		return sortDown(List.of(stArray));
	}
	
	/**
	 * 文字列配列をSetに変換する
	 * @param data
	 * @return
	 */
	public Set<String> toSet(String []data){
		return Set.of(data);
	}
	
	
	/**
	 * MapをTreeMapに変換する
	 */
    public <K, V> TreeMap<K, V> changeTreeMap(Map<K, V> hashMap){
		TreeMap<K, V> treeMap = hashMap.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> newValue,
                        TreeMap::new));
 
        return treeMap;
    }

    public Map<String, String> toMap(String csv) throws Exception{
    	var rtnMap = new HashMap<String, String>();

    	var stArray = csv.split(",");

		try {
			for(var i = 0; i < stArray.length; i += 2 ) {
				rtnMap.put(stArray[i], stArray[i + 1]);
			}
		} catch(Exception e) {

			
			throw new Exception(e);
		}
    	return rtnMap;
    }
}
