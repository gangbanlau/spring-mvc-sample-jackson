package com.com.com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO 后续优化 and performance benchmark
 * 
 * http://wiki.fasterxml.com/JacksonBestPracticesPerformance
 * 
 * Jackson Best Practices: Performance
 * 
 * Jackson is designed to add minimum amount of overhead for your JSON
 * processing. But just like other high-performance engines, actual real-life
 * performance is dependant on usage patterns. Here are some things you can do
 * to ensure efficient operation.
 * 
 * 1. Reuse when appropriate
 * 
 * Some objects that Jackson package use are either expensive to create, or can
 * reuse other things that are expensive to create. This is specifically true
 * for following objects:
 * 
 * ObjectMapper: object mappers cache serializers and deserializers that are
 * created first time handler is needed for given type (or more precisely,
 * mapper holds a reference to Provider objects that do). If you do not reuse
 * mappers, new serializers and deserializers need to be created each time: and
 * these are expensive operations due to amount of introspection and annotation
 * processing involved. JsonFactory: all JsonParser and JsonGenerator instances
 * used for Streaming API and Data Binding are constructed by JsonFactory. And
 * heavy-weight objects like symbol tables are reused by these factories. As
 * such it is beneficial to reuse these factories. (NOTE: ObjectMapper's hold
 * their own references to JsonFactory instances) Fortunately reuse of these
 * objects is very easy: they are thread-safe provided that configuration is
 * done before any use (and from a single thread). After initial configuration
 * use is fully thread-safe and does not need to be explicitly synchronized.
 * 
 * Some other commonly used objects may be reused, but where benefits are much
 * fewer:
 * 
 * ObjectReader/ObjectWriter: these are light-weight immutable objects that can
 * be safely shared between threads (and thus reused) as well; however, there
 * are no performance drawbacks from creating new instances, beyond basic
 * construction of objects (which itself simply populates fields and is very
 * cheap)
 * 
 *
 */
public class JsonObjectConverter {
	private static final Logger logger = LoggerFactory.getLogger(JsonObjectConverter.class);
	
	// Instances are fully thread-safe when "configure-then-use" pattern is used (i.e. configuration is not thread-safe but usage is). And when using ObjectWriter and ObjectReader instances even configuration is thread safe.
	static ObjectMapper objm = null;
	
	public static synchronized ObjectMapper getObjectMapperInstance() {
		if (objm == null)
			objm = new ObjectMapper();
		
		return objm;
	}
	
	/**
	 * 将InputStream转换成Object
	 * @param stream
	 * @param clazz
	 * @return Object
	 * @throws IOException
	 */
	public static Object convertToObject(InputStream stream, Class<?> clazz) throws IOException {
		//logger.info("Execute convertToObject!");
		
		StringBuffer entityBuffer = new StringBuffer();
		BufferedReader bufferedReader=new BufferedReader(
				new InputStreamReader(stream, "UTF-8"), 8*1024);
	    String line = null;
		while((line = bufferedReader.readLine()) != null){
			entityBuffer.append(line);
		}
		
		return getObjectMapperInstance().readValue(entityBuffer.toString(), clazz);
	}
	
	/**
	 * 将Json字符串转换成Object
	 * @param json
	 * @param clazz
	 * @return Object
	 * @throws IOException
	 */
	public static Object convertToObject(String json, Class<?> clazz) throws IOException {
		//logger.info("Execute convertToObject!");
		
		//return new ObjectMapper().readValue(json, clazz);
		return getObjectMapperInstance().readValue(json, clazz);
	}
	
	/**
	 * 将Object转换成Json字符串
	 * @param obj
	 * @return String
	 * @throws IOException
	 */
	public static String convertToJsonString(Object obj) throws IOException {
		//logger.info("Execute convertToJsonString!");
		
		//return new ObjectMapper().writeValueAsString(obj);
		return getObjectMapperInstance().writeValueAsString(obj);
	}
	
}
